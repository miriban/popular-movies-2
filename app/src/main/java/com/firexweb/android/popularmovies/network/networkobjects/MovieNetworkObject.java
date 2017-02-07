package com.firexweb.android.popularmovies.network.networkobjects;

import android.app.Activity;
import android.content.Context;

import com.firexweb.android.popularmovies.gui.activities.MainActivity;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.utilities.JSONUtility;

/**
 * Created by root on 1/21/17.
 */

public class MovieNetworkObject extends NetworkObject
{
    public MovieNetworkObject(Context context)
    {
        super(context);
    }

    @Override
    public void onTaskStarted()
    {
        MainActivity mainActivity = (MainActivity) super.getContext();
        mainActivity.showProgressBar();
    }

    @Override
    public void populateResult(String result)
    {
        MainActivity mainActivity = (MainActivity) super.getContext();
        mainActivity.hideProgressBar();
        Movie movies[] = JSONUtility.getMoviesFromJSONString(result);
        if(movies == null)
        {
            mainActivity.showErrorMessage();
            return;
        }

        if(movies.length == 0)
        {
            mainActivity.showErrorMessage();
            return;
        }
        mainActivity.populateMoviesToList(movies);
    }
}
