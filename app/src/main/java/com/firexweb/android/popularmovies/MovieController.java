package com.firexweb.android.popularmovies;

import android.content.Context;
import android.widget.Toast;

import com.firexweb.android.popularmovies.network.NetworkAsyncTask;
import com.firexweb.android.popularmovies.network.networkobjects.MovieNetworkObject;
import com.firexweb.android.popularmovies.network.networkobjects.NetworkObject;
import com.firexweb.android.popularmovies.network.NetworkUtility;


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

    public void getPopularMovies(Context context)
    {
        NetworkObject movieNetworkObject = new MovieNetworkObject(context);
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(movieNetworkObject);
        networkAsyncTask.execute(NetworkUtility.MOST_POPULAR_MOVIES_BASE_URL);
    }

    public void getTopRankedMovies(Context context)
    {
        NetworkObject movieNetworkObject = new MovieNetworkObject(context);
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(movieNetworkObject);
        networkAsyncTask.execute(NetworkUtility.TOP_RATED_MOVIES_BASE_URL);
    }

    public void showToast(Context context,String text)
    {
        if(this.mToast != null)
            this.mToast.cancel();

        this.mToast = Toast.makeText(context,text,Toast.LENGTH_LONG);
        this.mToast.show();
    }
}
