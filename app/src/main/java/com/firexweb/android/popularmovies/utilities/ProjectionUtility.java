package com.firexweb.android.popularmovies.utilities;

import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.data.tables.MovieTable;
import com.firexweb.android.popularmovies.data.tables.TrailerTable;

/**
 * Created by wello on 2/8/17.
 */

public class ProjectionUtility
{
    /*
        Should be The same order as index below
     */
    public static final String[] MOVIE_PROJECTION = {
            MovieTable.Entry.COLUMN_MOVIE_ID,
            MovieTable.Entry.COLUMN_TITLE,
            MovieTable.Entry.COLUMN_POSTER_URL,
            MovieTable.Entry.COLUMN_OVERVIEW,
            MovieTable.Entry.COLUMN_RATING,
            MovieTable.Entry.COLUMN_RELEASE_DATE,
            MovieTable.Entry.COLUMN_IS_FAVOURITE
    };
    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_POSTER_URL = 2;
    public static final int INDEX_MOVIE_OVERVIEW = 3;
    public static final int INDEX_MOVIE_RATING = 4;
    public static final int INDEX_MOVIE_RELEASE_DATE = 5;
    public static final int INDEX_MOVIE_IS_FAVOURITE = 6;

    /*
        Should be the same order as index below
     */
    public static final String[] TRAILER_PROJECTION = {
            TrailerTable.Entry.COLUMN_MOVIE_ID,
            TrailerTable.Entry.COLUMN_TRAILER_JSON_DATA
    };

    public static final int INDEX_TRAILER_MOVIE_ID = 0;
    public static final int INDEX_TRAILER_JSON_DATA = 1;


}
