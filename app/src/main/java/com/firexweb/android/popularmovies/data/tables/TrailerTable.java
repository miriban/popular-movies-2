package com.firexweb.android.popularmovies.data.tables;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.firexweb.android.popularmovies.data.content.MovieContract;

/**
 * Created by wello on 2/14/17.
 */

public class TrailerTable implements TableInterface
{
    public static final String PATH_TRAILERS = "trailers";

    public static final class Entry implements BaseColumns
    {

        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER_JSON_DATA = "json_data";
    }

    private static UriMatcher sUriMatcher = buildUriMatcher();
    // cases
    public static final int CODE_TRAILER = 200;

    private static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY,PATH_TRAILERS+"/#",CODE_TRAILER);
        return uriMatcher;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_TRAILERS_TABLE = "CREATE TABLE "+ Entry.TABLE_NAME+" ("+
                Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Entry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                Entry.COLUMN_TRAILER_JSON_DATA+" TEXT NOT NULL "+
                ");";
        db.execSQL(CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor returnedCursor = null;
        switch (sUriMatcher.match(uri))
        {
            case CODE_TRAILER:
                selection = Entry.COLUMN_MOVIE_ID+"=?";
                selectionArgs = new String[]{uri.getPathSegments().get(1)};
                returnedCursor = db.query(Entry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
        }
        return returnedCursor;
    }

    @Override
    public Uri insert(SQLiteDatabase db, Uri uri, ContentValues values)
    {
        Uri returnedUri = null;
        long id;
        switch (sUriMatcher.match(uri))
        {
            case CODE_TRAILER:
                id = db.insert(Entry.TABLE_NAME,null,values);
                returnedUri = ContentUris.withAppendedId(uri,id);
                break;
        }
        return returnedUri;
    }

    @Override
    public int update(SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return -1;
    }

    @Override
    public int delete(SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs)
    {
        return -1;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }
}
