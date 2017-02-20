package com.firexweb.android.popularmovies.data.tables;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.utilities.CheckingUtility;

/**
 * Created by wello on 2/14/17.
 */

public class MovieTable implements TableInterface
{
    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOST_POPULAR = "popular";
    public static final String PATH_TOP_RATED = "top_rated";
    public static final String PATH_FAVOURITE = "favourite";

    public static final class Entry
    {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_IS_POPULAR = "is_popular";
        public static final String COLUMN_IS_TOP_RATED = "is_top_rated";
        public static final String COLUMN_IS_FAVOURITE = "is_favourite";

    }

    private static UriMatcher sUriMatcher = buildUriMatcher();
    // use special cases for every table
    public static final int CODE_TOP_RATED_MOVIES = 100;
    public static final int CODE_MOST_POPULAR_MOVIES = 101;
    public static final int CODE_FAVOURITE_MOVIES = 102;
    public static final int CODE_CERTAIN_FAVOURITE_MOVIE = 103;

    private static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY,PATH_MOVIES+"/"+PATH_TOP_RATED,CODE_TOP_RATED_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY,PATH_MOVIES+"/"+PATH_MOST_POPULAR,CODE_MOST_POPULAR_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY,PATH_MOVIES+"/"+PATH_FAVOURITE,CODE_FAVOURITE_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY,PATH_MOVIES+"/"+PATH_FAVOURITE+"/#",CODE_CERTAIN_FAVOURITE_MOVIE);
        return uriMatcher;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_MOVIES_TABLE = "CREATE TABLE "+Entry.TABLE_NAME+" ("+
                Entry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                Entry.COLUMN_TITLE+" TEXT NOT NULL, "+
                Entry.COLUMN_POSTER_URL+" TEXT NOT NULL, "+
                Entry.COLUMN_OVERVIEW+" TEXT NOT NULL, "+
                Entry.COLUMN_RELEASE_DATE+" TEXT NOT NULL, "+
                Entry.COLUMN_RATING+" FLOAT NOT NULL, "+
                Entry.COLUMN_IS_POPULAR+" INTEGER NOT NULL DEFAULT 0, "+
                Entry.COLUMN_IS_TOP_RATED+" INTEGER NOT NULL DEFAULT 0, "+
                Entry.COLUMN_IS_FAVOURITE+" INTEGER NIT NULL DEFAULT 0"+
                ");";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
    }

    @Override
    public Cursor query(SQLiteDatabase db,Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor returnedCursor = null;
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                selection = Entry.COLUMN_IS_POPULAR + "=?";
                selectionArgs = new String[]{"1"};
                returnedCursor = db.query(Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case CODE_TOP_RATED_MOVIES:
                selection = Entry.COLUMN_IS_TOP_RATED + "=?";
                selectionArgs = new String[]{"1"};
                returnedCursor = db.query(Entry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case CODE_FAVOURITE_MOVIES:
                selection = Entry.COLUMN_IS_FAVOURITE + "=?";
                selectionArgs = new String[]{"1"};
                returnedCursor = db.query(Entry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
        }
        return returnedCursor;
    }

    @Override
    public Uri insert(SQLiteDatabase db,Uri uri, ContentValues values)
    {
        Uri returnedUri = null;
        long id;
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,Entry.COLUMN_IS_POPULAR))
                    throw new UnsupportedOperationException("Column: "+Entry.COLUMN_IS_POPULAR+" must be 1");

                if(!CheckingUtility.isValueLong(values,Entry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+Entry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                id = insertOrUpdate(db,uri,Entry.TABLE_NAME,null,values);
                if( id > 0)
                {
                    returnedUri = ContentUris.withAppendedId(uri,values.getAsInteger(Entry.COLUMN_MOVIE_ID));
                }
                else
                {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;

            case CODE_TOP_RATED_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,Entry.COLUMN_IS_TOP_RATED))
                    throw new UnsupportedOperationException("Column: "+Entry.COLUMN_IS_TOP_RATED+" must be 1");

                if(!CheckingUtility.isValueLong(values,Entry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+Entry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                id = insertOrUpdate(db,uri,Entry.TABLE_NAME,null,values);
                if( id > 0)
                {
                    returnedUri = ContentUris.withAppendedId(uri,values.getAsInteger(Entry.COLUMN_MOVIE_ID));
                }
                else
                {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;

        }
        return returnedUri;
    }

    private long insertOrUpdate(SQLiteDatabase db,Uri uri,String table,String columnHack,ContentValues values) throws SQLException
    {
        long returnedID;

        try
        {
            returnedID =  db.insertOrThrow(table,columnHack,values);
        }
        catch (SQLiteConstraintException e)
        {
            String selection = Entry.COLUMN_MOVIE_ID+"=?";
            String selectingArgs[] = new String[]{values.getAsInteger(Entry.COLUMN_MOVIE_ID).toString()};
            int numberOfRows = update(db,uri, values, selection,selectingArgs);
            if (numberOfRows == 0)
                throw e;
            returnedID = values.getAsInteger(Entry.COLUMN_MOVIE_ID);
        }
        return returnedID;
    }

    @Override
    public int update(SQLiteDatabase db,Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        int numberOfRows;
        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,Entry.COLUMN_IS_POPULAR))
                    throw new UnsupportedOperationException("Column: "+Entry.COLUMN_IS_POPULAR+" must be 1");

                if(!CheckingUtility.isValueLong(values,Entry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+Entry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                numberOfRows = db.update(Entry.TABLE_NAME,values,selection,selectionArgs);
                break;

            case CODE_TOP_RATED_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,Entry.COLUMN_IS_TOP_RATED))
                    throw new UnsupportedOperationException("Column: "+Entry.COLUMN_IS_TOP_RATED+" must be 1");

                if(!CheckingUtility.isValueLong(values,Entry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+Entry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                numberOfRows = db.update(Entry.TABLE_NAME,values,selection,selectionArgs);
                break;

            case CODE_CERTAIN_FAVOURITE_MOVIE:
                selection = Entry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{uri.getPathSegments().get(2)};
                numberOfRows = db.update(Entry.TABLE_NAME,values,selection,selectionArgs);
                break;

            default:
                return -1;
        }

        return numberOfRows;
    }

    @Override
    public int delete(SQLiteDatabase db,Uri uri, String selection, String[] selectionArgs)
    {
        return -1;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }
}
