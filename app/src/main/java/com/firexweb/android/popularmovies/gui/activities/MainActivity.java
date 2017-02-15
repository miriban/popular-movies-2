package com.firexweb.android.popularmovies.gui.activities;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.data.tables.MovieTable;
import com.firexweb.android.popularmovies.gui.adapters.Adapter;
import com.firexweb.android.popularmovies.gui.adapters.viewholders.MovieHolder;
import com.firexweb.android.popularmovies.loaders.DBLoader;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.services.NetworkService;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;

public class MainActivity extends AppCompatActivity implements DBLoader<Cursor>,NetworkReceiver.Receiver
{
    public final static int MOVIE_DB_LOADER = 1;
    public final static String BUNDLE_DB_PATH_KEY = "path";


    private final static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView movie_list_recyclerView;
    private ProgressBar movies_progress_bar;
    private TextView movies_error_text_view;
    private Adapter<MovieHolder> movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movies_progress_bar = (ProgressBar) findViewById(R.id.pb_loading);
        this.movies_error_text_view = (TextView) findViewById(R.id.tv_error_msg);
        this.movie_list_recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        this.movieAdapter = new Adapter<>(R.layout.movie_item,null,MovieHolder.class);
        GridLayoutManager layoutManager = new GridLayoutManager(movie_list_recyclerView.getContext(),2);
        movie_list_recyclerView.setLayoutManager(layoutManager);
        movie_list_recyclerView.setHasFixedSize(true);
        movie_list_recyclerView.setAdapter(movieAdapter);


        // load popular movies
        MovieController.getInstance().getPopularMovies(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args)
    {
        return new AsyncTaskLoader<Cursor>(this)
        {
            private Cursor cachedCursor;

            @Override
            protected void onStartLoading()
            {
                super.onStartLoading();
                if(args == null)
                    return;

                if(cachedCursor != null)
                    deliverResult(cachedCursor);
                else
                    forceLoad();
            }

            @Override
            public Cursor loadInBackground()
            {
                String path = args.getString(MainActivity.BUNDLE_DB_PATH_KEY);
                Uri uri = MovieContract.BASE_CONTENT_URI.buildUpon().appendPath(MovieTable.PATH_MOVIES)
                .appendPath(path).build();
                return MovieController.getInstance().getMoviesFromDB(this.getContext(),uri);
            }

            @Override
            public void deliverResult(Cursor newCursor)
            {
                cachedCursor = newCursor;
                super.deliverResult(newCursor);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor)
    {
        showData(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        // nothing to do
    }


    @Override
    public void showData(Cursor cursor)
    {
        if(cursor.getCount()!=0)
        {
            movie_list_recyclerView.setVisibility(View.VISIBLE);
            this.movieAdapter.swapCursor(cursor);
        }
        else
        {
           showErrorMessage(getString(R.string.error_loading_date));
        }

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        // received from service
        String url = resultData.getString(NetworkService.BUNDLE_URL_KEY);
        if(url.equals(NetworkUtility.MOST_POPULAR_MOVIES_BASE_URL))
        {
            MovieController.getInstance().getMoviesWithLoader(this,MovieTable.PATH_MOST_POPULAR);
        }
        else if(url.equals(NetworkUtility.TOP_RATED_MOVIES_BASE_URL))
        {
            MovieController.getInstance().getMoviesWithLoader(this,MovieTable.PATH_TOP_RATED);
        }
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

    public void hideRecyclerView()
    {
        this.movie_list_recyclerView.setVisibility(View.GONE);
        this.showProgressBar();
    }

    public void hideProgressBar()
    {
        this.movies_progress_bar.setVisibility(View.GONE);

    }

    public void showProgressBar()
    {
        hideErrorMessage();
        this.movies_progress_bar.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String msg)
    {
        hideProgressBar();
        this.movies_error_text_view.setText(msg);
        this.movies_error_text_view.setVisibility(View.VISIBLE);
    }

    public void hideErrorMessage()
    {
        this.movies_error_text_view.setVisibility(View.GONE);
    }
}
