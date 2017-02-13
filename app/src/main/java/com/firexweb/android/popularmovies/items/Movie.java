package com.firexweb.android.popularmovies.items;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.firexweb.android.popularmovies.utilities.ProjectionUtility;

/**
 * Created by root on 1/17/17.
 */

public class Movie implements Parcelable
{
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
    public static final String MOVIE_OBJECT = "movie_object";

    private int id;
    private String title;
    private String thumbUrl;
    private String overview;
    private float rating;
    private Date releaseDate;
    private int isFavourite;

    public Movie(int id,String title,String thumbUrl,String overview,float rating,Date releaseDate)
    {
        this.id = id;
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.isFavourite = 0;
    }

    public Movie(Cursor cursor)
    {
        this.id = cursor.getInt(ProjectionUtility.INDEX_MOVIE_ID);
        this.title = cursor.getString(ProjectionUtility.INDEX_MOVIE_TITLE);
        this.thumbUrl = cursor.getString(ProjectionUtility.INDEX_MOVIE_POSTER_URL);
        this.overview = cursor.getString(ProjectionUtility.INDEX_MOVIE_OVERVIEW);
        this.rating = cursor.getInt(ProjectionUtility.INDEX_MOVIE_RATING);
        this.releaseDate = new Date(cursor.getInt(ProjectionUtility.INDEX_MOVIE_RELEASE_DATE));
        this.isFavourite = cursor.getInt(ProjectionUtility.INDEX_MOVIE_IS_FAVOURITE);
    }

    public Movie(Parcel in)
    {
        this.id = in.readInt();
        this.title = in.readString();
        this.thumbUrl = in.readString();
        this.overview = in.readString();
        this.rating = in.readFloat();
        this.releaseDate = in.readParcelable(Date.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest,int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.overview);
        dest.writeFloat(this.rating);
        dest.writeParcelable(this.releaseDate,flags);
        dest.writeInt(this.isFavourite);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public int getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public String getOverview()
    {
        return overview;
    }

    public float getRating()
    {
        return rating;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public boolean isFavourite()
    {
        if( this.isFavourite == 1)
            return true;
        return false;
    }

    public String toString()
    {
        String data = "id => "+this.id;
        data += "\n";
        data += "title => "+this.title;
        return data;
    }
}
