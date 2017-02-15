package com.firexweb.android.popularmovies.gui.activities;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.gui.adapters.Adapter;
import com.firexweb.android.popularmovies.gui.adapters.viewholders.TrailerHolder;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.items.Trailer;
import com.firexweb.android.popularmovies.loaders.DBLoader;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.utilities.JSONUtility;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;
import com.firexweb.android.popularmovies.utilities.ProjectionUtility;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements DBLoader<Cursor>,NetworkReceiver.Receiver
{
    public static final String BUNDLE_DB_URI_KEY = "db-uri-key";
    public static final int LOADER_TRAILER = 1;
    private Movie movie;

    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView overview;

    private ImageView thumb;

    private RecyclerView trailers_recyclerview;
    private Adapter<TrailerHolder> trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        trailers_recyclerview = (RecyclerView) findViewById(R.id.rv_trailers);
        trailerAdapter = new Adapter<>(R.layout.trailer_item,null,TrailerHolder.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trailers_recyclerview.setLayoutManager(layoutManager);
        trailers_recyclerview.setHasFixedSize(true);
        trailers_recyclerview.setAdapter(trailerAdapter);

        // get trailers
        MovieController.getInstance().getTrailers(this,movie.getId());

    }

    @Override
    public void showData(Cursor cursor)
    {
        trailerAdapter.swapCursor(cursor);
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
                String uriString = args.getString(DetailActivity.BUNDLE_DB_URI_KEY);
                Uri uri = Uri.parse(uriString);
                return MovieController.getInstance().getTrailersFromDB(this.getContext(),uri);
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        cursor.moveToPosition(0);
        int id = cursor.getInt(ProjectionUtility.INDEX_TRAILER_MOVIE_ID);
        String jsonData = cursor.getString(ProjectionUtility.INDEX_TRAILER_JSON_DATA);
        Trailer trailers[] = JSONUtility.getTrailersFromJSONString(jsonData);
        MatrixCursor newCursor = new MatrixCursor(Trailer.PROJECTION);
        for(Trailer trailer:trailers)
        {
            String values[] = new String[]{Integer.toString(trailer.getMovieID()),
                    trailer.getName(),
                    trailer.getSource()
                    };
            newCursor.addRow(values);
        }
        Log.e("Count of Cursor","Length => "+newCursor.getCount());

        showData(newCursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        MovieController.getInstance().getTrailersWithLoader(this,this.movie.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
