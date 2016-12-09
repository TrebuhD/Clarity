package com.trebuh.clarity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
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
import com.trebuh.clarity.R;
import com.trebuh.clarity.adapters.PhotoGridAdapter;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.models.PhotosResponse;
import com.trebuh.clarity.network.ApiConstants;
import com.trebuh.clarity.network.FiveHundredPxService;
import com.trebuh.clarity.network.PhotosCallback;
import com.trebuh.clarity.network.RetrofitService;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "PhotoGridFragment";
    private static int GRID_SPAN_COUNT = 2;
    private static final int MINIMUM_ITEM_COUNT = 2;

    private static String ARG_FEATURE = "feature";
    private static String ARG_SORT_BY = "sort_by";
    private static String ARG_SEARCH_TERM = "search_term";
    private static String ARG_IS_SEARCH_INSTANCE = "is_search_instance";

    private static final String STATE_PHOTO_LIST = "state_photo_list";

    private static final String STATE_FEATURE = "state_feature";
    private static final String STATE_SORT_METHOD = "state_sort_method";
    private static final String STATE_IS_SEARCH_INSTANCE = "state_is_sort_instance";

    private String paramFeature;
    private String paramSortBy;
    private String paramSearchTerm;
    private boolean paramIsSearchInstance;

    private PhotoGridAdapter adapter;
    private PhotoGridFragmentListener listener;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView gridRecyclerView;

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

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
        args.putString(ARG_FEATURE, ApiConstants.NO_FEATURE);
        args.putString(ARG_SORT_BY, ApiConstants.NO_SORT_METHOD);
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
        View rootView = inflater.inflate(R.layout.fragment_photo_grid, container, false);
        if (savedInstanceState != null) {
            paramFeature = savedInstanceState.getString(STATE_FEATURE);
            paramSortBy = savedInstanceState.getString(STATE_SORT_METHOD);
            paramIsSearchInstance = savedInstanceState.getBoolean(STATE_IS_SEARCH_INSTANCE);
            photos = savedInstanceState.getParcelableArrayList(STATE_PHOTO_LIST);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initSwipeContainer(view);
        loadFirstPage();
//        initRecView(view);
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
            loadNewPhotos(ApiConstants.FIRST_PAGE, new PhotosCallbackHandler());
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

    private void loadFirstPage() {
        final PhotosCallbackHandler callbackHandler = new PhotosCallbackHandler();
        loadNewPhotos(ApiConstants.FIRST_PAGE, callbackHandler);
    }

    private void initRecView(View view) {
        gridRecyclerView = (RecyclerView) view.findViewById(R.id.rec_view_photos);
        if (photos == null) {
            Log.d(TAG, "Photos is null");
        }
        if (adapter == null) {
            adapter = new PhotoGridAdapter(photos);
            gridRecyclerView.setAdapter(adapter);
            final GridLayoutManager layoutManager = new WrapContentGridLayoutManager(getContext(), GRID_SPAN_COUNT);
            gridRecyclerView.setLayoutManager(layoutManager);

            adapter.setItemOnClickListener(new PhotoGridAdapter.PhotoGridItemOnClickListener() {
                @Override
                public void onPhotoGridItemClick(PhotoGridAdapter.PhotoGridItemHolder caller, CharSequence text) {
                    listener.onPhotoGridItemClick(caller, (String) text);
                }
            });

            // Handle loading more items after scrolling to end of page
            gridRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    if (totalItemsCount > MINIMUM_ITEM_COUNT) {
//                    ArrayList<Photo> morePhotos = loadNewPhotos(page + 1, callbackHandler);
//                    adapter.addItemRange(morePhotos);
//
//                    Handler handler = new Handler();
//                    Log.d(TAG, "loading more items, current page: " + page + ", current items: " +
//                            photos.size() + ", totalItemsCount:" + totalItemsCount + ", adding " + morePhotos.size() + " photos");
//                    handler.post(new NotifyItemsInsertedRunnable(photos.size(), morePhotos.size()));
                    }
                }
            });

            gridRecyclerView.setItemAnimator(new SlideInUpAnimator((new OvershootInterpolator(1f))));
            gridRecyclerView.getItemAnimator().setAddDuration(250);
            gridRecyclerView.getItemAnimator().setRemoveDuration(250);
        }
    }

    private void initRefreshButton(View view) {
        refreshButton = (AppCompatButton) view.findViewById(R.id.network_error_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewPhotos(ApiConstants.FIRST_PAGE, new PhotosCallbackHandler());
            }
        });
    }

    private void loadNewPhotos(int page, final PhotosCallbackHandler callbackHandler) {
        // Customize the call based on whether we're doing a search.
        String feature = paramIsSearchInstance ? ApiConstants.NO_FEATURE : paramFeature;
        String sortBy = paramIsSearchInstance ? ApiConstants.NO_SORT_METHOD : paramFeature;
        String searchQuery = paramIsSearchInstance ? paramSearchTerm : ApiConstants.NO_SEARCH_QUERY;

        FiveHundredPxService service = RetrofitService.getInstance().getService();

        Call<PhotosResponse> call = service.listPhotos(
                feature,
                sortBy,
                ApiConstants.DEFAULT_IMAGE_SIZE,
                searchQuery,
                ApiConstants.FIRST_PAGE,
                ApiConstants.DEFAULT_RESULTS_PER_PAGE
        );

        call.enqueue(new Callback<PhotosResponse>() {
            @Override
            public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                int statusCode = response.code();
                if(statusCode>= 400 && statusCode < 599)
                {
                    callbackHandler.onErrorCode(statusCode);
                }
                else
                {
                    callbackHandler.onSuccess(statusCode, response.body());
                }
//                setPhotos(photos);
//                adapter.addItemRange(photos);
            }

            @Override
            public void onFailure(Call<PhotosResponse> call, Throwable t) {
                callbackHandler.onError();
                Log.e("listPhotos(): ", "error, " + t.getMessage());
            }
        });

//            photoList = new FetchPhotosTask().execute(params).get();

//        } catch (InterruptedException | ExecutionException e) {
//            Log.e(TAG, "Failed to fetch new photos", e);
//        }
//        return (ArrayList<Photo>) photoList;
    }

    public void sortAndReplaceItems(String sortBy) {
        this.paramSortBy = sortBy;
        onRefresh();
    }

    public void transitionToNewFeature(String feature) {
        this.paramFeature = feature;
        this.paramSortBy = ApiConstants.SORT_METHOD_DEFAULT;
        onRefresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_PHOTO_LIST, photos);
        outState.putBoolean(STATE_IS_SEARCH_INSTANCE, paramIsSearchInstance);
        outState.putString(STATE_FEATURE, paramFeature);
        outState.putString(STATE_SORT_METHOD, paramSortBy);
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

    private class PhotosCallbackHandler implements PhotosCallback {
        PhotosCallbackHandler() {}

        @Override
        public void onSuccess(int statusCode, PhotosResponse body) {
            Log.d("listPhotos(): ", "Response code: " + statusCode);
            ArrayList<Photo> photoList = (ArrayList<Photo>) body.getPhotos();
            setPhotos(photoList);
            if (photoList == null) {
                Log.d(TAG, "p" +
                        "photoList is null");
            }
            initRecView(getView());
            adapter.addItemRange(photoList);
        }

        @Override
        public void onError() {
            Log.e(TAG, "PhotosCallbackHandler error");
        }

        @Override
        public void onErrorCode(int statusCode) {
            Log.e(TAG, "PhotosCallbackHandler error: " + statusCode);
        }
    }

//    private class FetchPhotosTask extends AsyncTask<FetchPhotosTaskParams, Void, List<Photo>> {
//        boolean isNetworkError = false;
//
//        @Override
//        protected void onPreExecute() {
//            Log.d(TAG, "PhotoGridFragmentListener::OnPreExecute()");
//            if (swipeContainer != null) {
//                swipeContainer.setRefreshing(true);
//            }
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<Photo> photos) {
//            if (refreshButton != null) {
//                if (isNetworkError) {
//                    refreshButton.setVisibility(View.VISIBLE);
//                } else {
//                    refreshButton.setVisibility(View.GONE);
//                }
//            }
//            if (swipeContainer != null) {
//                swipeContainer.setRefreshing(false);
//                Log.d(TAG, "PhotoGridFragmentListener::OnPostExecute()");
//            }
////            super.onPostExecute(photos);
//        }
//    }

    private class NotifyItemsInsertedRunnable implements Runnable {
        private int startPos;
        private int numberOfItems;

        public NotifyItemsInsertedRunnable(int startPos, int numberOfItems) {
            this.startPos = startPos;
            this.numberOfItems = numberOfItems;
        }

        @Override
        public void run() {
            adapter.notifyItemRangeInserted(startPos, numberOfItems);
        }
    }

    // this has to exist due to an android bug:
    // http://stackoverflow.com/questions/35653439/recycler-view-inconsistency-detected-invalid-view-holder-adapter-positionviewh
    private class WrapContentGridLayoutManager extends GridLayoutManager {
        WrapContentGridLayoutManager(Context context, int gridSpanCount) {
            super(context, gridSpanCount);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e(TAG, "Error, Index out of bounds");
            }
        }
    }
}