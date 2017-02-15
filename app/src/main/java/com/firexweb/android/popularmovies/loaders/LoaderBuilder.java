package com.firexweb.android.popularmovies.loaders;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;

/**
 * Created by wello on 2/14/17.
 */

public class LoaderBuilder
{
    private LoaderManager manager;
    private int loaderId;
    private Bundle bundle;
    private LoaderManager.LoaderCallbacks callbacks;

    public LoaderBuilder(LoaderManager manager)
    {
        this.manager = manager;
    }

    public LoaderBuilder buildUpon()
    {
        return this;
    }

    public LoaderBuilder setLoaderId(int loaderId)
    {
        this.loaderId = loaderId;
        return this;
    }

    public LoaderBuilder setBundle(Bundle bundle)
    {
        this.bundle = bundle;
        return this;
    }

    public LoaderBuilder setLoaderCallBacks(LoaderManager.LoaderCallbacks callBacks)
    {
        this.callbacks = callBacks;
        return this;
    }

    public void run()
    {
        if(this.manager == null)
            this.manager.initLoader(this.loaderId,this.bundle,this.callbacks);
        else
            this.manager.restartLoader(this.loaderId,this.bundle,this.callbacks);
    }

}
