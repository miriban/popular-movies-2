package com.firexweb.android.popularmovies.network;

import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.HandlerThread;

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
    public static final String MOST_POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    public static final String TOP_RATED_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated";
    private static final String IMAGE_MOVIE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private static final String API_KEY_QUERY = "api_key";
    private static final String API_KEY = ""; // TODO : please put your own key here!

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

}
