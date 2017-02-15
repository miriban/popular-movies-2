package com.firexweb.android.popularmovies;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.data.tables.MovieTable;
import com.firexweb.android.popularmovies.data.tables.TrailerTable;
import com.firexweb.android.popularmovies.gui.activities.DetailActivity;
import com.firexweb.android.popularmovies.gui.activities.MainActivity;
import com.firexweb.android.popularmovies.items.Trailer;
import com.firexweb.android.popularmovies.loaders.LoaderBuilder;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.services.MovieNetworkService;
import com.firexweb.android.popularmovies.services.ServiceBuilder;
import com.firexweb.android.popularmovies.services.TrailerNetworkService;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;
import com.firexweb.android.popularmovies.utilities.ProjectionUtility;


/**
 * Created by root on 1/17/17.
 */

public class MovieController
{
    private static MovieController movieController = null;
    private static final String TAG = MovieController.class.getSimpleName();

    private Toast mToast = null;

    private MovieController()
    {

    }

    public static MovieController getInstance()
    {
        if(movieController == null)
        {
            movieController = new MovieController();
            return movieController;
        }
        return movieController;
    }

    public void getPopularMovies(MainActivity mainActivity)
    {
        mainActivity.hideRecyclerView();
        ServiceBuilder builder = new ServiceBuilder(mainActivity);
        builder.setIntent(MovieNetworkService.class)
                .setIntentExtra(NetworkReceiver.KEY_NETWORK_RECEIVER,new NetworkReceiver(new Handler()))
                .setIntentAction(NetworkUtility.MOST_POPULAR_MOVIES_BASE_URL)
                .setReceiverActivity(mainActivity)
                .startService();
    }

    public void getTopRankedMovies(MainActivity mainActivity)
    {
        mainActivity.hideRecyclerView();

        ServiceBuilder builder = new ServiceBuilder(mainActivity);
        builder.setIntent(MovieNetworkService.class)
                .setIntentExtra(NetworkReceiver.KEY_NETWORK_RECEIVER,new NetworkReceiver(new Handler()))
                .setIntentAction(NetworkUtility.TOP_RATED_MOVIES_BASE_URL)
                .setReceiverActivity(mainActivity)
                .startService();
    }

    public void getTrailers(DetailActivity detailActivity,int movieID)
    {
        ServiceBuilder builder = new ServiceBuilder(detailActivity);
        builder.setIntent(TrailerNetworkService.class)
                .setIntentExtra(NetworkReceiver.KEY_NETWORK_RECEIVER,new NetworkReceiver(new Handler()))
                .setIntentAction(NetworkUtility.getTrailerURL(movieID))
                .setReceiverActivity(detailActivity)
                .startService();
    }

    public void getMoviesWithLoader(MainActivity mainActivity, String path)
    {
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.BUNDLE_DB_PATH_KEY,path);

        LoaderBuilder builder = new LoaderBuilder(mainActivity.getSupportLoaderManager());
        builder.buildUpon()
                .setLoaderId(MainActivity.MOVIE_DB_LOADER)
                .setBundle(bundle)
                .setLoaderCallBacks(mainActivity)
                .run();
    }

    public void getTrailersWithLoader(DetailActivity detailActivity,int movieID)
    {
        Bundle bundle = new Bundle();
        Uri uri = MovieContract.BASE_CONTENT_URI.buildUpon().appendPath(TrailerTable.PATH_TRAILERS)
                .appendPath(Integer.toString(movieID)).build();
        bundle.putString(DetailActivity.BUNDLE_DB_URI_KEY,uri.toString());
        LoaderBuilder builder = new LoaderBuilder(detailActivity.getSupportLoaderManager());
        builder.buildUpon()
                .setLoaderId(DetailActivity.LOADER_TRAILER)
                .setBundle(bundle)
                .setLoaderCallBacks(detailActivity)
                .run();
    }

    public Cursor getMoviesFromDB(Context context,Uri uri)
    {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(uri, ProjectionUtility.MOVIE_PROJECTION,null,null,
                MovieTable.Entry.COLUMN_MOVIE_ID);
    }

    public Cursor getTrailersFromDB(Context context,Uri uri)
    {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(uri,ProjectionUtility.TRAILER_PROJECTION,null,null,
                TrailerTable.Entry._ID);
    }
}
