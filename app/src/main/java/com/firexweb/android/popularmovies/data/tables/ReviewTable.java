package com.firexweb.android.popularmovies.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wello on 2/14/17.
 */

public class ReviewTable implements TableInterface
{
    public static final String PATH_REVIEWS = "reviews";

    public static final class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_REVIEWS_TABLE = "CREATE TABLE "+ Entry.TABLE_NAME+" ("+
                Entry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                Entry.COLUMN_AUTHOR+" TEXT NOT NULL, "+
                Entry.COLUMN_CONTENT+" TEXT NOT NULL"+
                ");";
        db.execSQL(CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
    }

    @Override
    public Cursor query(SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Override
    public Uri insert(SQLiteDatabase db, Uri uri, ContentValues values)
    {
        return null;
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
