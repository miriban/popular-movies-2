package com.firexweb.android.popularmovies.gui.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firexweb.android.popularmovies.MovieController;
import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.gui.adapters.Adapter;
import com.firexweb.android.popularmovies.gui.adapters.AdapterFactory;
import com.firexweb.android.popularmovies.gui.adapters.viewholders.ReviewHolder;
import com.firexweb.android.popularmovies.gui.adapters.viewholders.TrailerHolder;
import com.firexweb.android.popularmovies.items.Movie;
import com.firexweb.android.popularmovies.loaders.AsyncTaskLoaderFactory;
import com.firexweb.android.popularmovies.loaders.DBLoader;
import com.firexweb.android.popularmovies.receivers.NetworkReceiver;
import com.firexweb.android.popularmovies.services.NetworkService;
import com.firexweb.android.popularmovies.utilities.CursorUtility;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;
import com.squareup.picasso.Picasso;

import uk.co.deanwild.flowtextview.FlowTextView;

public class DetailActivity extends AppCompatActivity implements DBLoader<Cursor>,NetworkReceiver.Receiver
{
    public static final String BUNDLE_DB_URI_KEY = "db-uri-key";
    public static final int LOADER_TRAILER = 1;
    public static final int LOADER_REVIEW = 2;

    private Toolbar toolbar;
    private Movie movie;

    private TextView year;
    private TextView rating;
    private FlowTextView overview;

    private ImageView thumb;

    private RecyclerView trailers_recyclerview;
    private Adapter<TrailerHolder> trailerAdapter;

    private RecyclerView reviews_recyclerview;
    private Adapter<ReviewHolder> reviewAdapter;

    private TextView trailers_title_textview;
    private TextView reviews_title_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.tb_detail);
        toolbar.setBackgroundColor(Color.BLACK);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        trailers_title_textview = (TextView) findViewById(R.id.tv_trailer_title);
        reviews_title_textview = (TextView) findViewById(R.id.tv_reviews_title);

        year = (TextView) findViewById(R.id.tv_movie_year);
        rating = (TextView) findViewById(R.id.tv_movie_rate);
        overview = (FlowTextView) findViewById(R.id.ftv_desc);

        thumb = (ImageView) findViewById(R.id.iv_movie_detail_thumb);

        if(getIntent().hasExtra(Movie.MOVIE_OBJECT))
        {
            this.movie = (Movie) getIntent().getParcelableExtra(Movie.MOVIE_OBJECT);
            getSupportActionBar().setTitle(movie.getTitle());
            year.setText(Integer.toString(movie.getReleaseDate().getYear()));
            rating.setText(movie.getRating()+"");
            overview.setTypeface(Typeface.SANS_SERIF);
            overview.setTextPaint(new TextPaint(TextPaint.FILTER_BITMAP_FLAG));
            overview.setTextSize(35);
            overview.setText(movie.getOverview());
            Picasso.with(this).load(NetworkUtility.buildUrlForImage(movie.getThumbUrl()).toString())
            .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(this.thumb);
        }

        trailers_recyclerview = (RecyclerView) findViewById(R.id.rv_trailers);
        trailerAdapter = new Adapter<>(R.layout.trailer_item,null,TrailerHolder.class);
        AdapterFactory.makeAdapterWithLinearLayoutManager(trailers_recyclerview,trailerAdapter);

        reviews_recyclerview = (RecyclerView) findViewById(R.id.rv_reviews);
        reviewAdapter = new Adapter<>(R.layout.review_item,null,ReviewHolder.class);
        AdapterFactory.makeAdapterWithLinearLayoutManager(reviews_recyclerview,reviewAdapter);

        // get trailers
        MovieController.getInstance().getTrailers(this,movie.getId());

        // get reviews
        MovieController.getInstance().getReviews(this,movie.getId());

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args)
    {
        if(id == LOADER_TRAILER)
            return AsyncTaskLoaderFactory.createAsyncTaskLoaderForTrailers(this,args);
        else if(id == LOADER_REVIEW)
            return AsyncTaskLoaderFactory.createAsyncTaskLoaderForReviews(this,args);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        cursor.moveToPosition(0);
        Cursor matrixCursor;
        if(loader.getId() == LOADER_TRAILER)
        {
            matrixCursor = CursorUtility.getMatrixCursorForTrailers(cursor);
            if(matrixCursor.getCount() == 0)
                trailers_title_textview.setVisibility(View.GONE);
            trailerAdapter.swapCursor(matrixCursor);
        }
        else if(loader.getId() == LOADER_REVIEW)
        {
            matrixCursor = CursorUtility.getMatrixCursorForReviews(cursor);
            if(matrixCursor.getCount() == 0)
                reviews_title_textview.setVisibility(View.GONE);
            reviewAdapter.swapCursor(matrixCursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        String url = resultData.getString(NetworkService.BUNDLE_URL_KEY);
        Uri uri = Uri.parse(url);
        if(uri.getPathSegments().get(3).equals(NetworkUtility.PATH_TRAILERS))
            MovieController.getInstance().getTrailersWithLoader(this,this.movie.getId());
        else if(uri.getPathSegments().get(3).equals(NetworkUtility.PATH_REVIEWS))
            MovieController.getInstance().getReviewsWithLoader(this,this.movie.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.movie_details_menu,menu);
        if(this.movie.isFavourite())
        {
            MenuItem item = menu.findItem(R.id.action_add_favourite);
            item.setIcon(R.drawable.favorite_on);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        if( item.getItemId() == R.id.action_add_favourite)
        {
            if(movie.isFavourite())
            {
                MovieController.getInstance().removeFromFavourite(this,movie);
                item.setIcon(R.drawable.favorite_off);
            }
            else
            {
                MovieController.getInstance().addToFavourite(this,movie);
                item.setIcon(R.drawable.favorite_on);
            }
        }
        return true;
    }
}
