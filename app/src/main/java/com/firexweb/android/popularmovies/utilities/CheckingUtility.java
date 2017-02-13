package com.firexweb.android.popularmovies.utilities;

import android.content.ContentValues;

import com.firexweb.android.popularmovies.data.MovieContract;

/**
 * Created by wello on 2/8/17.
 */

public class CheckingUtility
{
    public static boolean isColumnSetToOne(ContentValues values,String columnName)
    {
        int value = values.getAsInteger(columnName);
        if(value == 1)
            return true;
        return false;
    }

    public static boolean isValueLong(ContentValues values,String columnName)
    {
        try
        {
            long value = values.getAsLong(columnName);
            return true;
        }
        catch (NullPointerException ex)
        {
            return false;
        }
    }
}
