package com.firexweb.android.popularmovies.gui.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by root on 1/21/17.
 */

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder
{
    public ViewHolder(View view)
    {
        super(view);
    }

    public abstract void bind(T object);
}
