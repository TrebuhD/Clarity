package com.trebuh.clarity.adapters;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trebuh.clarity.R;
import com.trebuh.clarity.models.Photo;

import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoGridItemHolder> {
    private List<Photo> photoList;
    private PhotoGridItemOnClickListener itemOnClickListener;

    public PhotoGridAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public PhotoGridItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View photoView = inflater.inflate(R.layout.photo_grid_item, parent, false);
        return new PhotoGridItemHolder(photoView);
    }

    @Override
    public void onBindViewHolder(PhotoGridItemHolder viewHolder, int position) {
        Photo photo = photoList.get(position);

        String authorName = photo.getUsername();
        String photoUrl = photo.getUrl();
        String photoTitleText = photo.getName();

        viewHolder.setUsernameText(authorName);
        viewHolder.setPhotoImage(photoUrl);
        viewHolder.setListener(itemOnClickListener);
    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    public void addItem(Photo photo, int position) {
        photoList.add(position, photo);
        notifyItemInserted(position);
    }

    public void addItemRange(List<Photo> photos) {
        int currentSize = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(currentSize, photos.size());
    }

    public void removeItem(int position) {
        photoList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAllItems() {
        int photoListLen = photoList.size();
        photoList.clear();
        notifyItemRangeRemoved(0, photoListLen);
    }

    public void setItemOnClickListener(PhotoGridItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    static class PhotoGridItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleTextView;
        private final ImageView photoImageView;
        private final ContentLoadingProgressBar progressBar;

        private PhotoGridItemOnClickListener listener;

        PhotoGridItemHolder(View itemView) {
            super(itemView);

            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.photo_item_progress_bar);
            progressBar.setVisibility(View.VISIBLE);

            this.titleTextView = (TextView) itemView.findViewById(R.id.photo_grid_item_tv);
            this.photoImageView = (ImageView) itemView.findViewById(R.id.photo_grid_item_iv);
            itemView.setOnClickListener(this);
        }

        void setUsernameText(CharSequence text) {
            titleTextView.setText(text);
        }

        void setPhotoImage(String url) {
            Picasso
                    .with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .into(photoImageView, new ImageLoadedCallback(progressBar) {
                        @Override
                        public void onSuccess() {
                            if (this.progressBar != null) {
                                this.progressBar.setVisibility(View.GONE);
                            }
                            super.onSuccess();
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onPhotoGridItemClick(v, titleTextView.getText());
            }
        }

        void setListener(PhotoGridItemOnClickListener listener) {
            this.listener = listener;
        }
    }

    public interface PhotoGridItemOnClickListener {
        void onPhotoGridItemClick(View caller, CharSequence text);
    }

    private static class ImageLoadedCallback implements Callback {
        ContentLoadingProgressBar progressBar;

        ImageLoadedCallback(ContentLoadingProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
