package com.firexweb.android.popularmovies.network.networkobjects;

import android.content.Context;

/**
 * Created by root on 1/21/17.
 */

public abstract class NetworkObject
{
    private Context context;

    public NetworkObject(Context context)
    {
        this.context = context;
    }

    public abstract void onTaskStarted();

    public abstract void populateResult(String result);

    public Context getContext()
    {
        return this.context;
    }
}
