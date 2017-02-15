package com.firexweb.android.popularmovies.data.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.firexweb.android.popularmovies.data.tables.TableInterface;


/**
 * Created by Mohammed on 2/7/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "movies_db";
    private static final int DB_VERSION = 1;

    private TableInterface tables[];

    public MovieDbHelper(Context context,TableInterface tables[])
    {
        super(context,DB_NAME,null,DB_VERSION);
        this.tables = tables;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for(TableInterface table:tables)
        {
            table.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for(TableInterface table:tables)
        {
            table.onUpgrade(db,oldVersion,newVersion);
        }
        onCreate(db);
    }

    public TableInterface[] getTables()
    {
        return this.tables;
    }
}
