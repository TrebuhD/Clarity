//package com.trebuh.clarity.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.trebuh.clarity.R;
//
//import java.util.ArrayList;
//
//public class SearchHistoryFragment extends Fragment {
//
//    private static final String ARG_PAST_SEARCH_TERMS_LIST = "past_search_terms_list";
//
//    private OnFragmentInteractionListener mListener;
//
//    private ArrayList<String> pastSearchTerms;
//    private ListView photosListView;
//
//    public SearchHistoryFragment() {
//        // Required empty public constructor
//    }
//
//    public static SearchHistoryFragment newInstance(ArrayList<String> pastSearchTerms) {
//        SearchHistoryFragment fragment = new SearchHistoryFragment();
//        Bundle args = new Bundle();
//        args.putStringArrayList(ARG_PAST_SEARCH_TERMS_LIST, pastSearchTerms);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            pastSearchTerms = getArguments().getStringArrayList(ARG_PAST_SEARCH_TERMS_LIST);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_search_history, container, false);
//
//        photosListView = (ListView) rootView.findViewById(R.id.search_history_list_view);
//
//        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
//                R.layout.search_list_item,
//                R.id.past_search_tv,
//                pastSearchTerms);
//
//        photosListView.setAdapter(adapter);
//
//        photosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                onButtonPressed((String) photosListView.getAdapter().getItem(position));
//            }
//        });
//
//        return rootView;
//    }
//
//    private void onButtonPressed(String searchString) {
//        if (mListener != null) {
//            mListener.onPastSearchItemClick(searchString);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement PhotoGridFragmentListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public void addToList(String searchTerm) {
//        pastSearchTerms.add(searchTerm);
//    }
//
//    public interface OnFragmentInteractionListener {
//        void onPastSearchItemClick(String searchString);
//    }
//
//}
