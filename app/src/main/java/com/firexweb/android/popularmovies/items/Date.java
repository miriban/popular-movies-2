package com.firexweb.android.popularmovies.items;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by root on 1/17/17.
 */

public class Date implements Parcelable
{
    public static final Parcelable.Creator<Date> CREATOR = new Parcelable.Creator<Date>()
    {
        public Date createFromParcel(Parcel in)
        {
            return new Date(in);
        }

        public Date[] newArray(int size)
        {
            return new Date[size];
        }
    };

    private int year;
    private int month;
    private int day;

    public Date(int year,int month,int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(Parcel in)
    {
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
    }

    public Date(String dateFormat)
    {
        String dateComponents[] = dateFormat.split("-");
        try
        {
            this.year = Integer.parseInt(dateComponents[0]);
            this.month = Integer.parseInt(dateComponents[1]);
            this.day = Integer.parseInt(dateComponents[2]);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest,int flags)
    {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.day);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }


    public int getYear()
    {
        return this.year;
    }

    public int getMonth()
    {
        return this.month;
    }

    public int getDay()
    {
        return this.day;
    }

    @Override
    public String toString()
    {
        String yyyy = String.valueOf(this.year);
        String mm = month < 10 ? "0" + this.month : String.valueOf(this.month);
        String dd = day < 10 ? "0" + this.day : String.valueOf(this.day);

        return yyyy + "-" + mm + "-" + dd;
    }
}
