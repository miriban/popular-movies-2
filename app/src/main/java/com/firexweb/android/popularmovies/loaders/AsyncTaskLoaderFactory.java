package com.firexweb.android.popularmovies.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.gui.activities.DetailActivity;

/**
 * Created by wello on 2/15/17.
 */

public class AsyncTaskLoaderFactory
{
    private static final String TRAILERS = "trailers";
    private static final String REVIEWS = "reviews";

    public static AsyncTaskLoader<Cursor> createAsyncTaskLoaderForTrailers(final Context context, final Bundle args)
    {
        return makeAsyncTask(TRAILERS,context,args);
    }

    public static AsyncTaskLoader<Cursor>  createAsyncTaskLoaderForReviews(final Context context,final Bundle args)
    {
        return makeAsyncTask(REVIEWS,context,args);
    }

    private static AsyncTaskLoader<Cursor>  makeAsyncTask(final String action, final Context context, final Bundle args)
    {
        return new AsyncTaskLoader<Cursor>(context)
        {
            private Cursor cached;

            @Override
            protected void onStartLoading()
            {
                super.onStartLoading();
                if(args == null)
                    return;

                if(cached != null)
                    deliverResult(cached);
                else
                    forceLoad();
            }

            @Override
            public Cursor loadInBackground()
            {
                String uriString = args.getString(DetailActivity.BUNDLE_DB_URI_KEY);
                Uri uri = Uri.parse(uriString);
                if(action.equals(TRAILERS))
                    return MovieController.getInstance().getTrailersFromDB(context,uri);
                else if(action.equals(REVIEWS))
                    return MovieController.getInstance().getReviewsFromDB(context,uri);
                return null;
            }

            @Override
            public void deliverResult(Cursor data)
            {
                cached = data;
                super.deliverResult(data);
            }
        };
    }
}
