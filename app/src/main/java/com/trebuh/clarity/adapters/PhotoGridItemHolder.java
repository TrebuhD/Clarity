package com.trebuh.clarity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trebuh.clarity.R;

public class PhotoGridItemHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView;

    public PhotoGridItemHolder(View itemView, TextView titleTextView) {
        super(itemView);
        this.titleTextView = titleTextView;
    }

    public static PhotoGridItemHolder newInstance(View parent) {
        TextView titleTextView = (TextView) parent.findViewById(R.id.photo_grid_item_title_tv);
        return new PhotoGridItemHolder(parent, titleTextView);
    }

    public void setPhotoTitle(CharSequence text) {
        titleTextView.setText(text);
    }
}
