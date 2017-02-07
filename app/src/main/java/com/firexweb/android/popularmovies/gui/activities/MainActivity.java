package com.firexweb.android.popularmovies.gui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.gui.adapters.Adapter;
import com.firexweb.android.popularmovies.gui.adapters.viewholders.MovieHolder;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.utilities.JSONUtility;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView movie_list_recyclerView;
    private TextView error_msg_text_view;
    private ProgressBar loading_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie_list_recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        error_msg_text_view = (TextView) findViewById(R.id.tv_error_msg);
        loading_progress_bar = (ProgressBar) findViewById(R.id.pb_loading_movies);

        // load popular movies
        MovieController.getInstance().getPopularMovies(this);
    }

    public void populateMoviesToList(Movie movies[])
    {
        if(this.movie_list_recyclerView != null)
        {
            Adapter<MovieHolder> movieAdapter = new Adapter<>(R.layout.movie_item,movies,MovieHolder.class);
            GridLayoutManager layoutManager = new GridLayoutManager(movie_list_recyclerView.getContext(),2);
            movie_list_recyclerView.setLayoutManager(layoutManager);
            movie_list_recyclerView.setHasFixedSize(true);
            movie_list_recyclerView.setAdapter(movieAdapter);
        }
    }

    public void showProgressBar()
    {
        this.loading_progress_bar.setVisibility(View.VISIBLE);
        this.error_msg_text_view.setVisibility(View.INVISIBLE);
        this.movie_list_recyclerView.setVisibility(View.INVISIBLE);
    }


    public void hideProgressBar()
    {
        this.loading_progress_bar.setVisibility(View.INVISIBLE);
        this.movie_list_recyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage()
    {
        this.error_msg_text_view.setVisibility(View.VISIBLE);
        this.movie_list_recyclerView.setVisibility(View.INVISIBLE);
    }

    public void hideErrorMessage()
    {
        this.error_msg_text_view.setVisibility(View.INVISIBLE);
        this.movie_list_recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.movie_thumbs_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_popular_movies)
            MovieController.getInstance().getPopularMovies(this);

        if(item.getItemId() == R.id.action_top_ranked_movies)
            MovieController.getInstance().getTopRankedMovies(this);

        return true;
    }
}
