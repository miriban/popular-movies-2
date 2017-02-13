package com.firexweb.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Mohammed on 2/7/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "movies_db";
    private static final int DB_VERSION = 2;

    public MovieDbHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_MOVIES_TABLE = "CREATE TABLE "+MovieContract.MoviesEntry.TABLE_NAME+" ("+
                MovieContract.MoviesEntry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_TITLE+" TEXT NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_POSTER_URL+" TEXT NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_OVERVIEW+" TEXT NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_RELEASE_DATE+" INTEGER NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_RATING+" FLOAT NOT NULL, "+
                MovieContract.MoviesEntry.COLUMN_IS_POPULAR+" INTEGER NOT NULL DEFAULT 0, "+
                MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED+" INTEGER NOT NULL DEFAULT 0, "+
                MovieContract.MoviesEntry.COLUMN_IS_FAVOURITE+" INTEGER NIT NULL DEFAULT 0"+
                ");";

        final String CREATE_TRAILERS_TABLE = "CREATE TABLE "+MovieContract.TrailersEntry.TABLE_NAME+" ("+
                MovieContract.TrailersEntry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                MovieContract.TrailersEntry.COLUMN_YOUTUBE_ID+" TEXT NOT NULL"+
                ");";

        final String CREATE_REVIEWS_TABLE = "CREATE TABLE "+MovieContract.ReviewsEntry.TABLE_NAME+" ("+
                MovieContract.ReviewsEntry.COLUMN_MOVIE_ID+" INTEGER UNIQUE NOT NULL, "+
                MovieContract.ReviewsEntry.COLUMN_AUTHOR+" TEXT NOT NULL, "+
                MovieContract.ReviewsEntry.COLUMN_CONTENT+" TEXT NOT NULL"+
                ");";

        db.execSQL(CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_TRAILERS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TrailersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewsEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.TrailersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
