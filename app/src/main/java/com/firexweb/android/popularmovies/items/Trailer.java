package com.firexweb.android.popularmovies.items;

import android.database.Cursor;

/**
 * Created by wello on 2/13/17.
 */

public class Trailer
{
    private int movieID;
    private String name;
    private String source;

    public final static String[] PROJECTION =
            {
                    "id",
                    "name",
                    "source"
            };
    public final static int INDEX_ID = 0;
    public final static int INDEX_NAME = 1;
    public final static int INDEX_SOURCE = 2;

    public Trailer(int movieID,String name,String source)
    {
        this.movieID = movieID;
        this.name = name;
        this.source = source;
    }

    public Trailer(Cursor cursor)
    {
        this.movieID = cursor.getInt(INDEX_ID);
        this.name = cursor.getString(INDEX_NAME);
        this.source = cursor.getString(INDEX_SOURCE);
    }

    public int getMovieID()
    {
        return this.movieID;
    }

    public String getName()
    {
        return name;
    }

    public String getSource()
    {
        return source;
    }
}
