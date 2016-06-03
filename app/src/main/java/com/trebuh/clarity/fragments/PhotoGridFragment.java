package com.trebuh.clarity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trebuh.clarity.R;

public class PhotoGridFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "PhotoGridFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private PhotoGridFragmentListener listener;

    private SwipeRefreshLayout swipeContainer;
    private ActionBar toolbar;
    private AppBarLayout appBarLayout;

    public PhotoGridFragment() {
        // Required empty public constructor
    }

    public static PhotoGridFragment newInstance(String param1, String param2) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.photoGridSwipeContainer);
        if (swipeContainer == null) {
            Log.e(TAG, "swipe container null");
        }
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onPhotoGridItemPressed(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

//        TODO remove
        if (toolbar == null) {
            Log.e(TAG, "Toolbar null");
        }

        if (context instanceof PhotoGridFragmentListener) {
            listener = (PhotoGridFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PhotoGridFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onRefresh() {
//        new MyTask().execute();
        if (listener != null) {
            listener.onAppBarShow();
        }
        Log.d(TAG, "lol");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface PhotoGridFragmentListener {
        // TODO: Update argument type and name
        void onPhotoGridItemPressed(Uri uri);
        void onAppBarShow();
    }
}
