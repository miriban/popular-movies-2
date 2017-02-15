package com.firexweb.android.popularmovies.data.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by wello on 2/14/17.
 */

public interface TableInterface
{
    public void onCreate(SQLiteDatabase db);
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    public Cursor query(SQLiteDatabase db,Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);
    public Uri insert(SQLiteDatabase db,Uri uri, ContentValues values);
    public int update(SQLiteDatabase db,Uri uri, ContentValues values, String selection, String[] selectionArgs);
    public int delete(SQLiteDatabase db,Uri uri, String selection, String[] selectionArgs);
    public String getType(Uri uri);
}
