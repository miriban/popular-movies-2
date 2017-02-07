package com.firexweb.android.popularmovies.gui.adapters.viewholders;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firexweb.android.popularmovies.gui.activities.DetailActivity;
import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.network.NetworkUtility;
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
    public void bind(Movie movie)
    {
        this.movie = movie;
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
        v.getContext().startActivity(detailActivity);
    }
}
