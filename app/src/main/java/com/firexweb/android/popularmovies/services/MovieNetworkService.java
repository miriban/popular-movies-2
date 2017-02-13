package com.firexweb.android.popularmovies.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.firexweb.android.popularmovies.data.MovieContract;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.utilities.JSONUtility;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;

/**
 * Created by wello on 2/12/17.
 */

public class MovieNetworkService extends NetworkService
{
    public MovieNetworkService()
    {
        super("movie-network-service");
    }
    public void insertData(String base_url,String result)
    {
        Movie movies[] = JSONUtility.getMoviesFromJSONString(result);
        if (movies == null) {
            return;
        }

        if (movies.length == 0) {
            return;
        }
        // insert into db
        ContentResolver contentResolver = getContentResolver();
        for (Movie movie : movies)
        {
            ContentValues values = new ContentValues();
            values.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, movie.getId());
            values.put(MovieContract.MoviesEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MovieContract.MoviesEntry.COLUMN_OVERVIEW, movie.getOverview());
            values.put(MovieContract.MoviesEntry.COLUMN_POSTER_URL, movie.getThumbUrl());
            values.put(MovieContract.MoviesEntry.COLUMN_RATING, movie.getRating());
            values.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate().getDateInLongFormat());

            Uri targetUri = null;

            if (base_url.equals(NetworkUtility.MOST_POPULAR_MOVIES_BASE_URL)) {
                values.put(MovieContract.MoviesEntry.COLUMN_IS_POPULAR, 1);
                targetUri = MovieContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(
                        MovieContract.PATH_MOST_POPULAR).build();
            }

            if (base_url.equals(NetworkUtility.TOP_RATED_MOVIES_BASE_URL)) {
                values.put(MovieContract.MoviesEntry.COLUMN_IS_TOP_RATED, 1);
                targetUri = MovieContract.MoviesEntry.CONTENT_URI.buildUpon().appendPath(
                        MovieContract.PATH_TOP_RATED).build();
            }

            contentResolver.insert(targetUri, values);
        }
    }
}
