package com.firexweb.android.popularmovies.gui.adapters.viewholders;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.gui.activities.DetailActivity;
import com.firexweb.android.popularmovies.gui.activities.MainActivity;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by root on 1/17/17.
 */

public class MovieHolder extends ViewHolder<Movie> implements View.OnClickListener
{
    private static final String TAG = MovieHolder.class.getSimpleName();

    private Movie movie = null;
    private ImageView thumb;

    protected MovieHolder(View view)
    {
        super(view);
        thumb = (ImageView) view.findViewById(R.id.iv_movie_thumb);
        thumb.setOnClickListener(this);
    }

    @Override
    public void bind(Cursor cursor)
    {
        this.movie = new Movie(cursor);
        URL url = NetworkUtility.buildUrlForImage(movie.getThumbUrl());
        Log.d(TAG,"URL of Image is => "+url);
        Picasso.with(thumb.getContext()).load(url.toString()).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(this.thumb);
    }

    @Override
    public void onClick(View v)
    {
        // launch Detail Activity
        Intent detailActivity = new Intent(v.getContext(), DetailActivity.class);
        detailActivity.putExtra(Movie.MOVIE_OBJECT,this.movie);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String transitionName = v.getContext().getString(R.string.image_view_transition_name);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((MainActivity)v.getContext(), v, transitionName);
            v.getContext().startActivity(detailActivity,transitionActivityOptions.toBundle());
        }
        else
        {
            v.getContext().startActivity(detailActivity);
        }

    }
}
