package com.trebuh.clarity.adapters;

import android.content.Context;
import android.os.Debug;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trebuh.clarity.R;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.network.ApiConstants;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoGridItemHolder> {
    private static final String TAG = "PhotoGridAdapter";
    private List<Photo> photoList;
    private PhotoGridItemOnClickListener itemOnClickListener;
    private PhotoGridItemHolder photoViewHolder;

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

        int photoId = photo.getId();
        String photoTitle = photo.getName();
        String authorName = photo.getUser().getUsername();
        String photoUrl = photo.getImageUrl();

        viewHolder.setUsernameText(authorName);
        viewHolder.setPhotoImage(photoUrl);
        viewHolder.setPhotoTitle(photoTitle);
        viewHolder.setHiddenUrlTextView(photoUrl);
        viewHolder.setListener(itemOnClickListener);

        // Each transition has to have an unique transaction name.
        ViewCompat.setTransitionName(viewHolder.photoImageView, String.valueOf(photoId) + "_image");
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
        int newImagesSize = photos.size();
        int currentSize = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(currentSize, newImagesSize);
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

    public static class PhotoGridItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // imageView for shared element transition
        final ImageView photoImageView;
        private final TextView authorNameTextView;
        private final TextView photoTitleTextView;
        private final TextView hiddenUrlTextView;
        private final ContentLoadingProgressBar progressBar;

        private PhotoGridItemOnClickListener listener;

        PhotoGridItemHolder(View itemView) {
            super(itemView);

            progressBar = (ContentLoadingProgressBar) itemView.findViewById(R.id.photo_item_progress_bar);
            progressBar.setVisibility(View.VISIBLE);

            this.authorNameTextView = (TextView) itemView.findViewById(R.id.photo_grid_item_author_name_tv);
            this.photoTitleTextView = (TextView) itemView.findViewById(R.id.photo_grid_item_title_tv);
            this.hiddenUrlTextView = (TextView) itemView.findViewById(R.id.photo_grid_hidden_url_tv);

            this.photoImageView = (ImageView) itemView.findViewById(R.id.photo_grid_item_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onPhotoGridItemClick(this, hiddenUrlTextView.getText());
            }
        }

        void setListener(PhotoGridItemOnClickListener listener) {
            this.listener = listener;
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

        void setUsernameText(CharSequence text) {
            authorNameTextView.setText(text);
        }

        void setHiddenUrlTextView(String url) {
            hiddenUrlTextView.setText(url);
        }

        void setPhotoTitle(String photoTitle) {
            photoTitleTextView.setText(photoTitle);
        }
    }

    public interface PhotoGridItemOnClickListener {
        void onPhotoGridItemClick(PhotoGridItemHolder caller, CharSequence text);
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
