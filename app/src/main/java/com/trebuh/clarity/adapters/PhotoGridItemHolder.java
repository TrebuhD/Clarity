package com.trebuh.clarity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trebuh.clarity.R;

public class PhotoGridItemHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView;
    private final ImageView photoImageView;

    public PhotoGridItemHolder(View itemView, TextView titleTextView, ImageView photoImageView) {
        super(itemView);
        this.titleTextView = titleTextView;
        this.photoImageView = photoImageView;
    }

    public static PhotoGridItemHolder newInstance(View parent) {
        TextView titleTextView = (TextView) parent.findViewById(R.id.photo_grid_item_tv);
        ImageView photoImageView = (ImageView) parent.findViewById(R.id.photo_grid_item_iv);
        return new PhotoGridItemHolder(parent, titleTextView, photoImageView);
    }

    public void setPhotoTitle(CharSequence text) {
        titleTextView.setText(text);
    }
}
