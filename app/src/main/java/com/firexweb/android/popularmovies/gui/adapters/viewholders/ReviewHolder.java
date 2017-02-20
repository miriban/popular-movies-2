package com.firexweb.android.popularmovies.gui.adapters.viewholders;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.firexweb.android.popularmovies.R;
import com.firexweb.android.popularmovies.items.Review;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by wello on 2/15/17.
 */

public class ReviewHolder extends ViewHolder<Review> implements View.OnClickListener
{
    private Review review;
    private TextView author_textview;
    private FlowTextView content_textview;

    public ReviewHolder(View view)
    {
        super(view);
        author_textview = (TextView) view.findViewById(R.id.tv_review_author);
        content_textview = (FlowTextView) view.findViewById(R.id.ftv_review_content);
        content_textview.setTypeface(Typeface.SANS_SERIF);
        content_textview.setTextPaint(new TextPaint(TextPaint.FILTER_BITMAP_FLAG));
        content_textview.setTextSize(30);
        author_textview.setOnClickListener(this);
    }

    @Override
    public void bind(Cursor cursor)
    {
        this.review = new Review(cursor);
        author_textview.setText(review.getAuthor());
        content_textview.setText(review.getContent());
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
        if(intent.resolveActivity(v.getContext().getPackageManager())!=null)
            v.getContext().startActivity(intent);
    }
}
