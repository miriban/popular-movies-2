package com.firexweb.android.popularmovies.data;

import com.firexweb.android.popularmovies.data.content.ImprovedContentProvider;
import com.firexweb.android.popularmovies.data.content.MovieDbHelper;
import com.firexweb.android.popularmovies.data.tables.MovieTable;
import com.firexweb.android.popularmovies.data.tables.ReviewTable;
import com.firexweb.android.popularmovies.data.tables.TableInterface;
import com.firexweb.android.popularmovies.data.tables.TrailerTable;


/**
 * Created by wello on 2/8/17.
 */

public class MoviesProvider extends ImprovedContentProvider
{
    @Override
    public boolean onCreate()
    {
        TableInterface tables[] = new TableInterface[3];
        tables[0] = new MovieTable();
        tables[1] = new TrailerTable();
        tables[2] = new ReviewTable();
        mDbHelper = new MovieDbHelper(getContext(),tables);
        return true;
    }

}
