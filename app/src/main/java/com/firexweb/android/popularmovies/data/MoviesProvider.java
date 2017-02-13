package com.firexweb.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.utilities.CheckingUtility;


/**
 * Created by wello on 2/8/17.
 */

public class MoviesProvider extends ContentProvider
{
    public static final int CODE_TOP_RATED_MOVIES = 100;
    public static final int CODE_MOST_POPULAR_MOVIES = 200;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIES+"/"+MovieContract.PATH_TOP_RATED,CODE_TOP_RATED_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH_MOVIES+"/"+MovieContract.PATH_MOST_POPULAR,CODE_MOST_POPULAR_MOVIES);
        return uriMatcher;
    }

    private MovieDbHelper mDbHelper;

    @Override
    public boolean onCreate()
    {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor returnedCursor;

        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                selection = MovieContract.MoviesEntry.COLUMN_IS_POPULAR+"=?";
                selectionArgs = new String[]{"1"};
                returnedCursor = db.query(MovieContract.MoviesEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case CODE_TOP_RATED_MOVIES:
                selection = MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED+"=?";
                selectionArgs = new String[]{"1"};
                returnedCursor = db.query(MovieContract.MoviesEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        returnedCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnedUri;
        long id;

        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,MovieContract.MoviesEntry.COLUMN_IS_POPULAR))
                    throw new UnsupportedOperationException("Column: "+MovieContract.MoviesEntry.COLUMN_IS_POPULAR+" must be 1");

                if(!CheckingUtility.isValueLong(values,MovieContract.MoviesEntry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+MovieContract.MoviesEntry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                id = insertOrUpdate(db,uri,MovieContract.MoviesEntry.TABLE_NAME,null,values);
                if( id > 0)
                {
                    returnedUri = ContentUris.withAppendedId(uri,values.getAsInteger(MovieContract.MoviesEntry.COLUMN_MOVIE_ID));
                }
                else
                {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;

            case CODE_TOP_RATED_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED))
                    throw new UnsupportedOperationException("Column: "+MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED+" must be 1");

                if(!CheckingUtility.isValueLong(values,MovieContract.MoviesEntry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+MovieContract.MoviesEntry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                id = insertOrUpdate(db,uri,MovieContract.MoviesEntry.TABLE_NAME,null,values);
                if( id > 0)
                {
                    returnedUri = ContentUris.withAppendedId(uri,values.getAsInteger(MovieContract.MoviesEntry.COLUMN_MOVIE_ID));
                }
                else
                {
                    throw new SQLException("Failed to insert row into "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
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
            String selection = MovieContract.MoviesEntry.COLUMN_MOVIE_ID+"=?";
            String selectingArgs[] = new String[]{values.getAsInteger(MovieContract.MoviesEntry.COLUMN_MOVIE_ID).toString()};
            int numberOfRows = update(uri, values, selection,selectingArgs);
            if (numberOfRows == 0)
                throw e;
            returnedID = values.getAsInteger(MovieContract.MoviesEntry.COLUMN_MOVIE_ID);
        }
        return returnedID;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int numberOfRows;

        switch (sUriMatcher.match(uri))
        {
            case CODE_MOST_POPULAR_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,MovieContract.MoviesEntry.COLUMN_IS_POPULAR))
                    throw new UnsupportedOperationException("Column: "+MovieContract.MoviesEntry.COLUMN_IS_POPULAR+" must be 1");

                if(!CheckingUtility.isValueLong(values,MovieContract.MoviesEntry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+MovieContract.MoviesEntry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                numberOfRows = db.update(MovieContract.MoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                break;

            case CODE_TOP_RATED_MOVIES:
                if(!CheckingUtility.isColumnSetToOne(values,MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED))
                    throw new UnsupportedOperationException("Column: "+MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED+" must be 1");

                if(!CheckingUtility.isValueLong(values,MovieContract.MoviesEntry.COLUMN_RELEASE_DATE))
                    throw new UnsupportedOperationException(("Column: "+MovieContract.MoviesEntry.COLUMN_RELEASE_DATE
                            +" must be converted into long!"));
                numberOfRows = db.update(MovieContract.MoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return numberOfRows;
    }
}
