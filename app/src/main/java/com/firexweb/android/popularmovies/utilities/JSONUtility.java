package com.firexweb.android.popularmovies.utilities;

import com.firexweb.android.popularmovies.items.Date;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.items.Review;
import com.firexweb.android.popularmovies.items.Trailer;

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

    public static int getMovieIdFromJSONString(String jsonData)
    {
        final String ID = "id";
        int id = -1;
        try
        {
            JSONObject trailersJSON = new JSONObject(jsonData);
            if(!trailersJSON.has(ID))
                throw new JSONException("No Movie ID detected!");
            id = trailersJSON.getInt(ID);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        return id;
    }

    public static Trailer[] getTrailersFromJSONString(String jsonData)
    {
        final String ID = "id";
        final String YOUTUBE = "youtube";

        final String NAME = "name";
        final String SOURCE = "source";

        Trailer trailers[] = null;

        try
        {
            JSONObject trailersJSON = new JSONObject(jsonData);
            if(!trailersJSON.has(ID))
                throw new JSONException("No trailers where found!");

            JSONArray jsonTrailers = trailersJSON.getJSONArray(YOUTUBE);
            int id = trailersJSON.getInt(ID);


            trailers = new Trailer[jsonTrailers.length()];

            for(int i=0;i<jsonTrailers.length();i++)
            {
                JSONObject trailerJSON = jsonTrailers.getJSONObject(i);

                String name = trailerJSON.getString(NAME);
                String source = trailerJSON.getString(SOURCE);
                trailers[i] = new Trailer(id,name,source);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return trailers;
    }

    public static Review[] getReviewsFromJSONString(String jsonData)
    {
        final String ID = "id";
        final String RESULT = "results";

        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL = "url";

        Review reviews[] = null;

        try
        {
            JSONObject reviewJSON = new JSONObject(jsonData);
            if(!reviewJSON.has(ID))
                throw new JSONException("No reviews where found!");

            JSONArray jsonReviews = reviewJSON.getJSONArray(RESULT);
            int id = reviewJSON.getInt(ID);


            reviews = new Review[jsonReviews.length()];

            for(int i=0;i<jsonReviews.length();i++)
            {
                JSONObject reviewObj = jsonReviews.getJSONObject(i);

                String author = reviewObj.getString(AUTHOR);
                String content = reviewObj.getString(CONTENT);
                String url = reviewObj.getString(URL);
                reviews[i] = new Review(id,author,content,url);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return reviews;
    }
}
