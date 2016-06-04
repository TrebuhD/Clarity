package com.trebuh.clarity.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trebuh.clarity.EndlessRecyclerViewScrollListener;
import com.trebuh.clarity.PhotoFetcher;
import com.trebuh.clarity.R;
import com.trebuh.clarity.adapters.PhotoGridAdapter;
import com.trebuh.clarity.models.Photo;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhotoGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "PhotoGridFragment";
    private static final int GRID_SPAN_COUNT = 2;

    private static final String ARG_FEATURE = "feature";
    private static final String ARG_SORT_BY = "sort_by";

    private String paramFeature;
    private String paramSortBy;

    private PhotoGridFragmentListener listener;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView gridRecyclerView;

    private ArrayList<Photo> photos;

    public PhotoGridFragment() {
        // Required empty public constructor
    }

    public static PhotoGridFragment newInstance(String paramFeature, String paramSortBy) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FEATURE, paramFeature);
        args.putString(ARG_SORT_BY, paramSortBy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PhotoGridFragmentListener) {
            listener = (PhotoGridFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PhotoGridFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramFeature = getArguments().getString(ARG_FEATURE);
            paramSortBy = getArguments().getString(ARG_SORT_BY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initSwipeContainer(view);
        initRecView(view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onRefresh() {
        new FetchPhotosTask().execute();
    }


    private void initRecView(View view) {
        gridRecyclerView = (RecyclerView) view.findViewById(R.id.recViewPhotos);
        photos = loadNewPhotos();
        final PhotoGridAdapter adapter = new PhotoGridAdapter(photos);
        adapter.setItemOnClickListener(new PhotoGridAdapter.PhotoGridItemOnClickListener() {
            @Override
            public void onPhotoGridItemClick(View caller, CharSequence text) {
                listener.onPhotoGridItemPressed((String) text);
            }
        });
        gridRecyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), GRID_SPAN_COUNT);
        gridRecyclerView.setLayoutManager(layoutManager);

        // All items are the same width and height, provides smoother scrolling
        gridRecyclerView.setHasFixedSize(true);

        // Handle loading more items after scrolling to end of page
        gridRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // TODO replace with API call - load next page
                // loadNextPage(currentPage);
                adapter.addItemRange(loadNewPhotos());
            }
        });
    }

    private ArrayList<Photo> loadNewPhotos() {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
            photos = new FetchPhotosTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Failed to fetch new photos", e);
        }
        return photos;
    }

    private void initSwipeContainer(View view) {
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.photoGridSwipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    public interface PhotoGridFragmentListener {
        void onPhotoGridItemPressed(String url);
        void onAppBarShow();
    }

    private class FetchPhotosTask extends AsyncTask<Void, Void, ArrayList<Photo>> {

        @Override
        protected ArrayList<Photo> doInBackground(Void... params) {
            String photosJsonResponse = PhotoFetcher.fetchPhotosJSON();
            return PhotoFetcher.parsePhotoItems(photosJsonResponse);
        }

        @Override
        protected void onPostExecute(ArrayList<Photo> photos) {
            swipeContainer.setRefreshing(false);
            super.onPostExecute(photos);
        }

    }
}