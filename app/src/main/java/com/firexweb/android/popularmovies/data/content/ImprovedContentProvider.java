package com.firexweb.android.popularmovies.data.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.firexweb.android.popularmovies.data.tables.TableInterface;

/**
 * Created by wello on 2/14/17.
 */

public abstract class  ImprovedContentProvider extends ContentProvider
{
    protected MovieDbHelper mDbHelper;


    public abstract boolean onCreate();

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor returnedCursor = null;

        for(TableInterface table:mDbHelper.getTables())
        {
            Cursor cursor = table.query(db,uri,projection,selection,selectionArgs,sortOrder);
            if(cursor != null)
                returnedCursor = cursor;
        }

        if(returnedCursor == null)
            throw new SQLException("Unknown Uri "+uri);

        returnedCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        String returnedMIME = null;
        for(TableInterface table:mDbHelper.getTables())
        {
            String MIME = table.getType(uri);
            if(MIME != null)
                returnedMIME = MIME;
        }
        if(returnedMIME == null)
            throw new SQLException("Unknown Uri "+uri);
        return returnedMIME;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnedUri = null;

        for(TableInterface table:mDbHelper.getTables())
        {
            Uri insertUri = table.insert(db,uri,values);
            if(insertUri != null)
                returnedUri = insertUri;
        }

        if(returnedUri == null)
            throw new SQLException("Unknown Uri "+uri);

        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted = -1;
        for(TableInterface table:mDbHelper.getTables())
        {
            int rows = table.delete(db,uri,selection,selectionArgs);
            if(rows != -1)
                rowsDeleted = rows;
        }

        if(rowsDeleted == -1)
            throw new SQLException("Unknown Uri "+uri);

        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated = -1;
        for(TableInterface table:mDbHelper.getTables())
        {
            int rows = table.update(db,uri,values,selection,selectionArgs);
            if(rows != -1)
                rowsUpdated = rows;
        }

        if(rowsUpdated == -1)
            throw new SQLException("Unknown Uri "+uri);

        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}
