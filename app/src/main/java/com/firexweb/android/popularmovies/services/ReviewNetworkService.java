package com.firexweb.android.popularmovies.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.firexweb.android.popularmovies.data.content.MovieContract;
import com.firexweb.android.popularmovies.data.tables.ReviewTable;
import com.firexweb.android.popularmovies.utilities.JSONUtility;

/**
 * Created by wello on 2/15/17.
 */

public class ReviewNetworkService extends NetworkService
{
    public ReviewNetworkService()
    {
        super("review-network-service");
    }
    @Override
    public void insertData(String base_url, String jsonData)
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
        values.put(ReviewTable.Entry.COLUMN_MOVIE_ID, id);
        values.put(ReviewTable.Entry.COLUMN_REVIEW_JSON_DATA,jsonData);
        Uri targetUri = MovieContract.BASE_CONTENT_URI.buildUpon().appendPath(ReviewTable.PATH_REVIEWS)
                .appendPath(Integer.toString(id)).build();
        contentResolver.insert(targetUri, values);

    }
}
