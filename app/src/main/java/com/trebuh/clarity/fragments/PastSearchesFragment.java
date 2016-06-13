package com.trebuh.clarity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trebuh.clarity.R;

public class PastSearchesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PastSearchesFragment() {
        // Required empty public constructor
    }

    public static PastSearchesFragment newInstance() {
        PastSearchesFragment fragment = new PastSearchesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO fill method
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searches, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String searchString) {
        if (mListener != null) {
            mListener.onPastSearchItemClick(searchString);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PhotoGridFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onPastSearchItemClick(String searchString);
    }

}
