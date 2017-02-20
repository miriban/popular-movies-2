package com.firexweb.android.popularmovies.data.content;

import android.net.Uri;

/**
 * Created by Mohammed on 2/7/17.
 */

public class MovieContract
{
    public static final String AUTHORITY = "com.firexweb.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
}
