package com.firexweb.android.popularmovies.utilities;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.firexweb.android.popularmovies.items.Review;
import com.firexweb.android.popularmovies.items.Trailer;

/**
 * Created by wello on 2/15/17.
 */

public class CursorUtility
{
    public static Cursor getMatrixCursorForTrailers(Cursor cursor)
    {
        MatrixCursor newCursor = new MatrixCursor(Trailer.PROJECTION);
        int id = cursor.getInt(ProjectionUtility.INDEX_TRAILER_MOVIE_ID);
        String jsonData = cursor.getString(ProjectionUtility.INDEX_TRAILER_JSON_DATA);
        Trailer trailers[] = JSONUtility.getTrailersFromJSONString(jsonData);
        for(Trailer trailer:trailers)
        {
            String values[] = new String[]{Integer.toString(trailer.getMovieID()),
                    trailer.getName(),
                    trailer.getSource()
            };
            newCursor.addRow(values);
        }
        return newCursor;
    }

    public static MatrixCursor getMatrixCursorForReviews(Cursor cursor)
    {
        MatrixCursor newCursor = new MatrixCursor(Review.PROJECTION);
        int id = cursor.getInt(ProjectionUtility.INDEX_REVIEW_MOVIE_ID);
        String jsonData = cursor.getString(ProjectionUtility.INDEX_REVIEW_JSON_DATA);
        Review reviews[] = JSONUtility.getReviewsFromJSONString(jsonData);
        for(Review review:reviews)
        {
            String values[] = new String[]{Integer.toString(review.getMovieID()),
                    review.getAuthor(),
                    review.getContent(),
                    review.getUrl()
            };
            newCursor.addRow(values);
        }
        return newCursor;
    }
}
