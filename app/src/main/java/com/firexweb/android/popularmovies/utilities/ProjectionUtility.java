package com.firexweb.android.popularmovies.utilities;

import com.firexweb.android.popularmovies.data.MovieContract;

/**
 * Created by wello on 2/8/17.
 */

public class ProjectionUtility
{
    /*
        Should be The same order as index below
     */
    public static final String[] MOVIE_PROJECTION = {
            MovieContract.MoviesEntry.COLUMN_MOVIE_ID,
            MovieContract.MoviesEntry.COLUMN_TITLE,
            MovieContract.MoviesEntry.COLUMN_POSTER_URL,
            MovieContract.MoviesEntry.COLUMN_OVERVIEW,
            MovieContract.MoviesEntry.COLUMN_RATING,
            MovieContract.MoviesEntry.COLUMN_RELEASE_DATE,
            MovieContract.MoviesEntry.COLUMN_IS_FAVOURITE
    };
    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_POSTER_URL = 2;
    public static final int INDEX_MOVIE_OVERVIEW = 3;
    public static final int INDEX_MOVIE_RATING = 4;
    public static final int INDEX_MOVIE_RELEASE_DATE = 5;
    public static final int INDEX_MOVIE_IS_FAVOURITE = 6;


}
