package com.trebuh.clarity.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.trebuh.clarity.R;
import com.trebuh.clarity.adapters.TransitionListenerAdapter;
import com.trebuh.clarity.models.Photo;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    public static final String TAG = DetailsFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGE_POSITION = "arg_photo_image_position";
    private static final String ARG_STARTING_IMAGE_POSITION = "arg_starting_photo_image_position";
    private static final String ARG_PHOTOS_ARRAY_LIST = "arg_photos_array_list";

    private final Callback photoCallback = new Callback() {
        @Override
        public void onSuccess() {
            startPostponedEnterTransition();
        }

        @Override
        public void onError() {
            startPostponedEnterTransition();
        }
    };

    private ArrayList<Photo> photos;

    private ImageView mainPicture;

    private int startingPosition;

    private int photoPosition;
    private boolean isTransitioning;
    private long backgroundImageFadeMillis;
    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(int position, int startingPosition, ArrayList<Photo> photos) {
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_POSITION, position);
        args.putInt(ARG_STARTING_IMAGE_POSITION, startingPosition);
        args.putParcelableArrayList(ARG_PHOTOS_ARRAY_LIST, photos);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photos = getArguments().getParcelableArrayList(ARG_PHOTOS_ARRAY_LIST);
            startingPosition = getArguments().getInt(ARG_STARTING_IMAGE_POSITION);
            photoPosition = getArguments().getInt(ARG_IMAGE_POSITION);

            isTransitioning = (savedInstanceState == null && startingPosition == photoPosition);
            backgroundImageFadeMillis = 1000;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        mainPicture = (ImageView) rootView.findViewById(R.id.details_photo_iv);
        final ImageView profilePicture = (ImageView) rootView.findViewById(R.id.details_profile_pic_iv);

        View textContainer = rootView.findViewById(R.id.details_body_container);
        TextView photoTitleText = (TextView) textContainer.findViewById(R.id.details_photo_title_tv);
        HtmlTextView photoDescriptionText = (HtmlTextView) textContainer.findViewById(R.id.details_photo_description_tv);

        String photoUrl = photos.get(photoPosition).getUrl();
        String profilePicUrl = photos.get(photoPosition).getAvatarUrl();
        String photoName = photos.get(photoPosition).getName();

        String tempDescription = photos.get(photoPosition).getDescription();
        String photoDescription = (tempDescription.equals("null") ? "No description" : tempDescription);

        photoTitleText.setText(photoName);
        photoDescriptionText.setHtmlFromString(photoDescription, new HtmlTextView.RemoteImageGetter());
        mainPicture.setTransitionName(photoName);

        RequestCreator photoRequest = Picasso.with(getActivity()).load(photoUrl).fit().centerCrop();
        RequestCreator profilePicRequest = Picasso.with(getActivity()).load(profilePicUrl);

//        if (isTransitioning) {
//            photoRequest.noFade();
//            profilePicRequest.noFade();
//            mainPicture.setAlpha(0f);
//            getActivity().getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
//                @Override
//                public void onTransitionEnd(Transition transition) {
//                    mainPicture.animate().setDuration(backgroundImageFadeMillis).alpha(1f);
//                }
//            });
//        }

        photoRequest.into(mainPicture, photoCallback);
        profilePicRequest.into(profilePicture);

        return rootView;
    }

    private void startPostponedEnterTransition() {
        if (photoPosition == startingPosition) {
            mainPicture.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onPreDraw() {
                    mainPicture.getViewTreeObserver().removeOnPreDrawListener(this);
                    getActivity().startPostponedEnterTransition();
                    return true;
                }
            });
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainPicture = (ImageView) view.findViewById(R.id.details_photo_iv);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ImageView getMainPhoto() {
        if (isViewInBounds(getActivity().getWindow().getDecorView(), mainPicture)) {
            return mainPicture;
        }
        return null;
    }


    /**
     * returns true if {@param view} is contained within {@param container}'s bounds
     */
    private static boolean isViewInBounds(@NonNull View container, @NonNull View view) {
        Rect containerBounds = new Rect();
        container.getHitRect(containerBounds);
        return view.getLocalVisibleRect(containerBounds);
    }

    public int getPhotoCount() {
        return photos.size();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
