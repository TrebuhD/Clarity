package com.trebuh.clarity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trebuh.clarity.R;

class PhotoGridItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView titleTextView;
    private final ImageView photoImageView;
    private final PhotoGridItemOnClickListener listener;

    private PhotoGridItemHolder(View itemView, TextView titleTextView, ImageView photoImageView,
                                PhotoGridItemOnClickListener listener) {
        super(itemView);
        this.listener = listener;
        this.titleTextView = titleTextView;
        this.photoImageView = photoImageView;
        itemView.setOnClickListener(this);
    }

    static PhotoGridItemHolder newInstance(View parent, PhotoGridItemOnClickListener listener) {
        TextView titleTextView = (TextView) parent.findViewById(R.id.photo_grid_item_tv);
        ImageView photoImageView = (ImageView) parent.findViewById(R.id.photo_grid_item_iv);
        return new PhotoGridItemHolder(parent, titleTextView, photoImageView, listener);
    }

    void setPhotoTitle(CharSequence text) {
        titleTextView.setText(text);
    }

    @Override
    public void onClick(View v) {
        listener.onPhotoGridItemClick(v);
    }

    interface PhotoGridItemOnClickListener {
        void onPhotoGridItemClick(View caller);
    }
}
