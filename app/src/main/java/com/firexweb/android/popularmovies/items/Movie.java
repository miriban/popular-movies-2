package com.firexweb.android.popularmovies.items;

import android.os.Parcel;
import android.os.Parcelable;

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

    public Movie(int id,String title,String thumbUrl,String overview,float rating,Date releaseDate)
    {
        this.id = id;
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
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

    public String toString()
    {
        String data = "id => "+this.id;
        data += "\n";
        data += "title => "+this.title;
        return data;
    }
}
