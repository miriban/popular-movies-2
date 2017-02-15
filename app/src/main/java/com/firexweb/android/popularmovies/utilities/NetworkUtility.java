package com.firexweb.android.popularmovies.utilities;

import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.HandlerThread;

import com.firexweb.android.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by root on 1/17/17.
 */

public final class NetworkUtility
{
    public static final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOST_POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    public static final String TOP_RATED_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated";
    private static final String IMAGE_MOVIE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public static final String PATH_TRAILERS = "trailers";

    private static final String API_KEY_QUERY = "api_key";
    private static final String API_KEY = BuildConfig.MovieDBKey;



    public static URL buildUrl(String basePath)
    {
        Uri builtUri = Uri.parse(basePath).buildUpon()
                .appendQueryParameter(API_KEY_QUERY,API_KEY).build();
        URL url = null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException ex)
        {
            ex.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlForImage(String imageName)
    {
        Uri builtUri = Uri.parse(IMAGE_MOVIE_BASE_URL).buildUpon()
                .appendPath(imageName).build();
        URL url = null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException ex)
        {
            ex.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHTTPUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput)
                return scanner.next();
            else
                return null;
        }
        finally
        {
            urlConnection.disconnect();
        }
    }

    public static String getTrailerURL(int movieID)
    {
        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon().appendPath(Integer.toString(movieID))
                .appendPath(PATH_TRAILERS).appendQueryParameter(API_KEY_QUERY,API_KEY).build();
        return uri.toString();
    }

    public static Uri getYoutubeURL(String source)
    {
        Uri uri = Uri.parse("https://www.youtube.com/watch")
                .buildUpon()
                .appendQueryParameter("v",source)
                .build();
        return uri;
    }

}
