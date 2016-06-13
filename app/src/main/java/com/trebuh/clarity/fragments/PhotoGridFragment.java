package com.trebuh.clarity.fragments;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.trebuh.clarity.EndlessRecyclerViewScrollListener;
import com.trebuh.clarity.PhotoFetcher;
import com.trebuh.clarity.R;
import com.trebuh.clarity.adapters.PhotoGridAdapter;
import com.trebuh.clarity.models.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class PhotoGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "PhotoGridFragment";
    private static int GRID_SPAN_COUNT = 2;
    private static final int MINIMUM_ITEM_COUNT = 2;

    private static final String ARG_FEATURE = "feature";
    private static final String ARG_SORT_BY = "sort_by";
    private static final String ARG_SEARCH_TERM = "search_term";
    private static final String ARG_IS_SEARCH_INSTANCE = "is_search_instance";

    private String paramFeature;
    private String paramSortBy;
    private String paramSearchTerm;
    private boolean paramIsSearchInstance;

    private PhotoGridAdapter adapter;
    private PhotoGridFragmentListener listener;

    private SwipeRefreshLayout swipeContainer;

    private RecyclerView gridRecyclerView;

    private ArrayList<Photo> photos;
    private AppCompatButton refreshButton;

    public PhotoGridFragment() {
        // Required empty public constructor
    }

    public static PhotoGridFragment newInstance(String paramFeature, String paramSortBy) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FEATURE, paramFeature);
        args.putString(ARG_SORT_BY, paramSortBy);
        args.putBoolean(ARG_IS_SEARCH_INSTANCE, false);
        fragment.setArguments(args);
        return fragment;
    }

    public static PhotoGridFragment newSearchInstance(String searchQuery) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_TERM, searchQuery);
        args.putBoolean(ARG_IS_SEARCH_INSTANCE, true);
        args.putString(ARG_FEATURE, PhotoFetcher.NO_FEATURE);
        args.putString(ARG_SORT_BY, PhotoFetcher.NO_SORT_METHOD);
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
            paramIsSearchInstance = getArguments().getBoolean(ARG_IS_SEARCH_INSTANCE);
            if (paramIsSearchInstance) {
                paramSearchTerm = getArguments().getString(ARG_SEARCH_TERM);
            } else {
                paramFeature = getArguments().getString(ARG_FEATURE);
                paramSortBy = getArguments().getString(ARG_SORT_BY);
            }
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
        initRefreshButton(view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onRefresh() {
        if (adapter != null) {
            adapter.removeAllItems();
            adapter.addItemRange(loadNewPhotos(0));
        }
    }

    private void initSwipeContainer(View view) {
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.photoGridSwipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
    }

    private void initRecView(View view) {
        gridRecyclerView = (RecyclerView) view.findViewById(R.id.rec_view_photos);
        photos = loadNewPhotos(0);
        adapter = new PhotoGridAdapter(photos);
        adapter.setItemOnClickListener(new PhotoGridAdapter.PhotoGridItemOnClickListener() {
            @Override
            public void onPhotoGridItemClick(PhotoGridAdapter.PhotoGridItemHolder caller, CharSequence text) {
                listener.onPhotoGridItemClick(caller, (String) text);
            }
        });
        gridRecyclerView.setAdapter(adapter);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), GRID_SPAN_COUNT);
        gridRecyclerView.setLayoutManager(layoutManager);

        // Handle loading more items after scrolling to end of page
        gridRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount > MINIMUM_ITEM_COUNT) {
                    Log.d(TAG, "loading more items");
                    // (page + 1) - don't download the first page twice
                    adapter.addItemRange(loadNewPhotos(page + 1));
                }
            }
        });

        gridRecyclerView.setItemAnimator(new SlideInUpAnimator((new OvershootInterpolator(1f))));
        gridRecyclerView.getItemAnimator().setAddDuration(250);
        gridRecyclerView.getItemAnimator().setRemoveDuration(250);
    }


    private void initRefreshButton(View view) {
        refreshButton = (AppCompatButton) view.findViewById(R.id.network_error_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos = loadNewPhotos(0);
            }
        });
    }

    private ArrayList<Photo> loadNewPhotos(int page) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
            FetchPhotosTaskParams params;
            if (paramIsSearchInstance) {
                params = new FetchPhotosTaskParams(
                        page,
                        PhotoFetcher.NO_FEATURE,
                        PhotoFetcher.NO_SORT_METHOD,
                        paramSearchTerm);
            } else {
                params = new FetchPhotosTaskParams(
                        page,
                        paramFeature,
                        paramSortBy,
                        PhotoFetcher.NO_SEARCH_QUERY);
            }
            photos = new FetchPhotosTask().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Failed to fetch new photos", e);
        }
        return photos;
    }

    public void sortAndReplaceItems(String sortBy) {
        this.paramSortBy = sortBy;
        onRefresh();
    }

    public ArrayList<Photo> performSearch(String searchQuery) {
        ArrayList<Photo> photos = new ArrayList<>();
        try {
            FetchPhotosTaskParams params = new FetchPhotosTaskParams(
                    PhotoFetcher.FIRST_PAGE, PhotoFetcher.NO_FEATURE, PhotoFetcher.NO_SORT_METHOD,
                    searchQuery);
            photos = new FetchPhotosTask().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Failed to fetch new photos", e);
        }
        return photos;
    }

    public void transitionToNewFeature(String feature) {
        this.paramFeature = feature;
        this.paramSortBy = PhotoFetcher.SORT_METHOD_DEFAULT;
        onRefresh();
    }

    public RecyclerView getPhotosContainer() {
        return gridRecyclerView;
    }

    public ArrayList<Photo> getPhotoList() {
        return photos;
    }

    public void newSearch(String searchTerm) {
        paramSearchTerm = searchTerm;
        onRefresh();
    }

    public interface PhotoGridFragmentListener {
        void onPhotoGridItemClick(PhotoGridAdapter.PhotoGridItemHolder caller, String url);
        void onAppBarShow();
    }

    private class FetchPhotosTask extends AsyncTask<FetchPhotosTaskParams, Void, ArrayList<Photo>> {
        boolean isNetworkError = false;

        @Override
        protected void onPreExecute() {
            if (swipeContainer != null) {
                swipeContainer.setRefreshing(true);
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Photo> doInBackground(FetchPhotosTaskParams... params) {
            String photosJsonResponse = null;
            try {
                photosJsonResponse = PhotoFetcher.fetchPhotosJSON(
                        params[0].page,
                        params[0].feature,
                        params[0].sortBy,
                        params[0].searchQuery);
            } catch (IOException | NetworkErrorException e) {
                isNetworkError = true;
                e.printStackTrace();
            }
            return PhotoFetcher.parsePhotoItems(photosJsonResponse);
        }

        @Override
        protected void onPostExecute(ArrayList<Photo> photos) {
            if (refreshButton != null) {
                if (isNetworkError) {
                    refreshButton.setVisibility(View.VISIBLE);
                } else {
                    refreshButton.setVisibility(View.GONE);
                }
            }
            if (swipeContainer != null) {
                swipeContainer.setRefreshing(false);
            }
            super.onPostExecute(photos);
        }
    }

    private static class FetchPhotosTaskParams {
        int page;
        String feature;
        String sortBy;
        String searchQuery;

        FetchPhotosTaskParams(int page, String feature, String sortBy, String searchQuery) {
            this.page = page;
            this.feature = feature;
            this.sortBy = sortBy;
            this.searchQuery = searchQuery;
        }
    }
}