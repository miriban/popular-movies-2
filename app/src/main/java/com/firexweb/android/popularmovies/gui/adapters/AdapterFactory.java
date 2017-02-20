package com.firexweb.android.popularmovies.gui.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wello on 2/15/17.
 */

public class AdapterFactory
{
    public static void makeAdapterWithLinearLayoutManager(RecyclerView recyclerView,Adapter adapter)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
