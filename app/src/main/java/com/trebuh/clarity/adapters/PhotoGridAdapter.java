package com.trebuh.clarity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trebuh.clarity.R;
import com.trebuh.clarity.models.Photo;

import java.util.List;

public class PhotoGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Photo> photoList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item,
                parent, false);
        return PhotoGridItemHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PhotoGridItemHolder holder = (PhotoGridItemHolder) viewHolder;
        String titleText = photoList.get(position).getName();
        holder.setPhotoTitle(titleText);
    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }
}
