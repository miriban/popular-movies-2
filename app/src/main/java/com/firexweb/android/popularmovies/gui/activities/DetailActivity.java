package com.firexweb.android.popularmovies.gui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.network.NetworkUtility;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity
{
    private Movie movie;

    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView overview;

    private ImageView thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.tv_movie_title);
        year = (TextView) findViewById(R.id.tv_movie_year);
        rating = (TextView) findViewById(R.id.tv_movie_rate);
        overview = (TextView) findViewById(R.id.tv_movie_overview);

        thumb = (ImageView) findViewById(R.id.iv_movie_detail_thumb);

        if(getIntent().hasExtra(Movie.MOVIE_OBJECT))
        {
            this.movie = (Movie) getIntent().getParcelableExtra(Movie.MOVIE_OBJECT);
            title.setText(movie.getTitle());
            year.setText(Integer.toString(movie.getReleaseDate().getYear()));
            rating.setText(movie.getRating()+"/10");
            overview.setText(movie.getOverview());
            Picasso.with(this).load(NetworkUtility.buildUrlForImage(movie.getThumbUrl()).toString())
            .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(this.thumb);
        }

    }
}
