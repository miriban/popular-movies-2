package com.firexweb.android.popularmovies.items;

import android.database.Cursor;

/**
 * Created by wello on 2/15/17.
 */

public class Review
{
    private int movieID;
    private String author;
    private String content;
    private String url;

    public final static String PROJECTION[] =
                {
                    "id",
                    "author",
                    "content",
                    "url"
                };
    public final static int INDEX_ID = 0;
    public final static int INDEX_AUTHOR = 1;
    public final static int INDEX_CONTENT = 2;
    public final static int INDEX_URL = 3;

    public Review(int movieID,String author,String content,String url)
    {
        this.movieID = movieID;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public Review(Cursor cursor)
    {
        this.movieID = cursor.getInt(INDEX_ID);
        this.author = cursor.getString(INDEX_AUTHOR);
        this.content = cursor.getString(INDEX_CONTENT);
        this.url = cursor.getString(INDEX_URL);
    }

    public int getMovieID()
    {
        return movieID;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getContent()
    {
        return content;
    }

    public String getUrl()
    {
        return url;
    }
}
