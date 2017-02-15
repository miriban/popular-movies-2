package com.firexweb.android.popularmovies.gui.adapters.viewholders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.items.Trailer;
import com.firexweb.android.popularmovies.utilities.NetworkUtility;

/**
 * Created by wello on 2/13/17.
 */

public class TrailerHolder extends ViewHolder<Trailer> implements View.OnClickListener
{
    private Trailer trailer;
    private TextView title_textview;

    public TrailerHolder(View view)
    {
        super(view);
        title_textview = (TextView) view.findViewById(R.id.tv_trailer_name);
        view.setOnClickListener(this);
    }

    @Override
    public void bind(Cursor cursor)
    {
        this.trailer = new Trailer(cursor);
        title_textview.setText(trailer.getName());
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, NetworkUtility.getYoutubeURL(this.trailer.getSource()));
        if(intent.resolveActivity(v.getContext().getPackageManager())!=null)
            v.getContext().startActivity(intent);
    }
}
