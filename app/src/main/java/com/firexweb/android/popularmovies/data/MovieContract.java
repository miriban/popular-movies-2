package com.firexweb.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohammed on 2/7/17.
 */

public class MovieContract
{
    public static final String AUTHORITY = "com.firexweb.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOST_POPULAR = "popular";
    public static final String PATH_TOP_RATED = "top_rated";
    public static final String PATH_TRAILERS = "trailers";
    public static final String PATH_REVIEWS = "reviews";

    public static final class MoviesEntry
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_URL = "poster_url";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_IS_POPULAR = "is_popular";
        public static final String COLUMN_IS_TOP_RATED = "is_top_rated";
        public static final String COLUMN_IS_FAVOURITE = "is_favourite";

    }

    public static final class TrailersEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_TRAILERS).build();

        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_YOUTUBE_ID = "youtube_id";
    }

    public static final class ReviewsEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_REVIEWS).build();

        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";

    }
}
