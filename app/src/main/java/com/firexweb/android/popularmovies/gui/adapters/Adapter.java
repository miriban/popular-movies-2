package com.firexweb.android.popularmovies.gui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firexweb.android.popularmovies.gui.adapters.viewholders.ViewHolder;
import com.firexweb.android.popularmovies.items.Movie;

/**
 * Created by root on 1/17/17.
 */

public class Adapter<T extends ViewHolder> extends RecyclerView.Adapter<ViewHolder>
{
    private int item_layout_id;
    private Class viewHolder;
    private Cursor mCursor;

    public Adapter(int item_layout_id,Cursor cursor,Class viewHolder)
    {
        this.item_layout_id = item_layout_id;
        this.mCursor = cursor;
        this.viewHolder = viewHolder;
    }

    @Override
    public ViewHolder<Movie> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int movie_item_layout_id = this.item_layout_id;
        boolean shouldAttachToParentImmediatly = false;
        View view = inflater.inflate(movie_item_layout_id,parent,shouldAttachToParentImmediatly);

        ViewHolder viewHolder = null;

        try
        {
            viewHolder = (T) this.viewHolder.getConstructor(View.class).newInstance(view);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        mCursor.moveToPosition(position);
        holder.bind(mCursor);
    }

    @Override
    public int getItemCount()
    {
        if( this.mCursor == null)
            return 0;
        return this.mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor)
    {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
