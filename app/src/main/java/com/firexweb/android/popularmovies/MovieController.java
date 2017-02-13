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

import com.firexweb.android.popularmovies.data.MovieContract;
import com.firexweb.android.popularmovies.gui.activities.MainActivity;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.services.MovieNetworkService;
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
        startMovieNetworkService(mainActivity, NetworkUtility.MOST_POPULAR_MOVIES_BASE_URL);
    }

    public void getTopRankedMovies(MainActivity mainActivity)
    {
        mainActivity.hideRecyclerView();
        startMovieNetworkService(mainActivity, NetworkUtility.TOP_RATED_MOVIES_BASE_URL);
    }

    public void getMoviesWithLoader(MainActivity mainActivity, String path)
    {
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.BUNDLE_DB_PATH_KEY,path);
        startLoader(mainActivity.getSupportLoaderManager(),bundle, mainActivity);
    }

    private void startMovieNetworkService(MainActivity mainActivity, String url)
    {
        NetworkReceiver receiver = new NetworkReceiver(new Handler());
        receiver.setReceiver(mainActivity);
        Intent intent = new Intent(mainActivity, MovieNetworkService.class);
        intent.putExtra(NetworkReceiver.KEY_NETWORK_RECEIVER,receiver);
        intent.setAction(url);
        mainActivity.startService(intent);
    }

    private void startLoader(LoaderManager loaderManager,Bundle bundle,LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks)
    {
        Loader<Cursor> loader = loaderManager.getLoader(MainActivity.MOVIE_DB_LOADER);
        if(loader == null)
            loaderManager.initLoader(MainActivity.MOVIE_DB_LOADER,bundle,loaderCallbacks);
        else
            loaderManager.restartLoader(MainActivity.MOVIE_DB_LOADER,bundle,loaderCallbacks);
    }

    public void showToast(Context context,String text)
    {
        if(this.mToast != null)
            this.mToast.cancel();

        this.mToast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        this.mToast.show();
    }

    public Cursor getMoviesFromDB(Context context,Uri uri)
    {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(uri, ProjectionUtility.MOVIE_PROJECTION,null,null,
                MovieContract.MoviesEntry.COLUMN_MOVIE_ID);
    }
}
