package com.firexweb.android.popularmovies.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.data.tables.TrailerTable;
import com.firexweb.android.popularmovies.items.Trailer;
import com.firexweb.android.popularmovies.utilities.JSONUtility;

/**
 * Created by wello on 2/13/17.
 */

public class TrailerNetworkService extends NetworkService
{
    public TrailerNetworkService()
    {
        super("trailer-network-service");
    }
    public void insertData(String base_url,String jsonData)
    {
        if (jsonData == null) {
            return;
        }

        if (jsonData.isEmpty()) {
            return;
        }

        int id = JSONUtility.getMovieIdFromJSONString(jsonData);

        if(id == -1)
        {
            return;
        }

        // insert into db
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(TrailerTable.Entry.COLUMN_MOVIE_ID, id);
        values.put(TrailerTable.Entry.COLUMN_TRAILER_JSON_DATA,jsonData);
        Uri targetUri = MovieContract.BASE_CONTENT_URI.buildUpon().appendPath(TrailerTable.PATH_TRAILERS)
                .appendPath(Integer.toString(id)).build();
        contentResolver.insert(targetUri, values);
    }
}
