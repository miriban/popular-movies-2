package com.firexweb.android.popularmovies.utilities;

import com.firexweb.android.popularmovies.items.Date;
import com.firexweb.android.popularmovies.items.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 1/17/17.
 */

public final class JSONUtility
{
    public static Movie[] getMoviesFromJSONString(String jsonData)
    {
        final String RESULTS = "results";

        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String ID = "id";
        final String TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";
        Movie movies[] = null;

        try
        {
            JSONObject moviesJSON = new JSONObject(jsonData);
            if(!moviesJSON.has(RESULTS))
                throw new JSONException("No movies where found!");
            JSONArray jsonMovies = moviesJSON.getJSONArray(RESULTS);

            movies = new Movie[jsonMovies.length()];

            for(int i=0;i<jsonMovies.length();i++)
            {
                JSONObject movieJson = jsonMovies.getJSONObject(i);

                int id = movieJson.getInt(ID);
                String title = movieJson.getString(TITLE);
                String thumbUrl = movieJson.getString(POSTER_PATH).replace("\\","").replace("/","");
                String overview = movieJson.getString(OVERVIEW);
                float rating = Float.parseFloat(movieJson.getString(VOTE_AVERAGE));
                Date releaseDate = new Date(movieJson.getString(RELEASE_DATE));
                movies[i] = new Movie(id,title,thumbUrl,overview,rating,releaseDate);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return movies;
    }
}
