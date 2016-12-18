package com.trebuh.clarity.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.trebuh.clarity.DetailsActivity;
import com.trebuh.clarity.FullscreenPictureActivity;
import com.trebuh.clarity.R;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.network.ApiConstants;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetailsFragment extends Fragment {
    public static final String TAG = DetailsFragment.class.getSimpleName();

    private static final String ARG_IMAGE_POSITION = "arg_photo_image_position";
    private static final String ARG_STARTING_IMAGE_POSITION = "arg_starting_photo_image_position";
    private static final String ARG_PHOTOS_URL = "arg_photos_array_url";

    private ArrayList<Photo> photos;

    private ImageView mainPic;
    private ImageView avatarPic;
    private int startingPosition;

    private int photoPosition;
    private OnFragmentInteractionListener mListener;
    private String photoUrl;
    private String avatarUrl;
    private String uncroppedPhotoUrl;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(int position, int startingPosition, String photosFilename) {
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_POSITION, position);
        args.putInt(ARG_STARTING_IMAGE_POSITION, startingPosition);
        args.putString(ARG_PHOTOS_URL, photosFilename);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (getArguments() != null) {
            photos = ((DetailsActivity) getActivity()).getPhotoList();
            startingPosition = getArguments().getInt(ARG_STARTING_IMAGE_POSITION);
            photoPosition = getArguments().getInt(ARG_IMAGE_POSITION);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        if (photos == null) {
            photos = ((DetailsActivity) getActivity()).getPhotoList();
        }

        initPhotoAndDescription(rootView);
        initDetails(rootView);
        initPictures();

        return rootView;
    }

    private void initPictures() {
        final RequestCreator photoRequest = Picasso.with(getActivity()).load(photoUrl).fit().noFade().centerCrop();
        RequestCreator avatarRequest = Picasso.with(getActivity()).load(avatarUrl).noFade().fit().centerCrop();

        photoRequest.into(mainPic, new Callback() {
            @Override
            public void onSuccess() {
                startPostponedEnterTransition();
                Picasso.with(mainPic.getContext())
                        .load(uncroppedPhotoUrl)
                        .fit()
                        .centerCrop()
                        .placeholder(mainPic.getDrawable())
                        .into(mainPic);
            }

            @Override
            public void onError() {
                startPostponedEnterTransition();
            }
        });
        avatarRequest.into(avatarPic, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        mainPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 21) {
                    Intent fullscreenPhotoIntent = new Intent(getActivity(), FullscreenPictureActivity.class);
                    fullscreenPhotoIntent.putExtra(FullscreenPictureActivity.MIDRES_IMG_URL, photoUrl);
                    fullscreenPhotoIntent.putExtra(FullscreenPictureActivity.HIRES_IMG_URL,
                            getCurrentPhoto().getImages().get(ApiConstants.IMAGE_SIZE_UNCROPPED_LARGE).getUrl());
                    startActivity(fullscreenPhotoIntent);
                } else {
                    Intent intent = new Intent(getActivity(), FullscreenPictureActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(getActivity(), view, getString(R.string.transition_fullscreen_pic));
                    intent.putExtra(FullscreenPictureActivity.MIDRES_IMG_URL, uncroppedPhotoUrl);
                    intent.putExtra(FullscreenPictureActivity.HIRES_IMG_URL,
                            getCurrentPhoto().getImages().get(ApiConstants.IMAGE_SIZE_UNCROPPED_LARGE).getUrl());
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }

    private void initDetails(View rootView) {
        // todo: use butterknife ;D
        TextView isoTV = (TextView) rootView.findViewById(R.id.details_iso_value);
        TextView apertureTV = (TextView) rootView.findViewById(R.id.details_aperture_value);
        TextView favoritesTV = (TextView) rootView.findViewById(R.id.details_favcount_value);
        TextView shutterSpeedTV = (TextView) rootView.findViewById(R.id.details_shutter_speed_value);
        TextView ratingTV = (TextView) rootView.findViewById(R.id.details_rating_value);
        TextView lensTV = (TextView) rootView.findViewById(R.id.details_lens_value);
        TextView cameraTV = (TextView) rootView.findViewById(R.id.details_camera_value);
        TextView viewsTV = (TextView) rootView.findViewById(R.id.details_times_viewed_value);

        isoTV.setText(String.valueOf(getCurrentPhoto().getIso()));
        apertureTV.setText(String.valueOf(getCurrentPhoto().getAperture()));
        favoritesTV.setText(String.valueOf(getCurrentPhoto().getVotesCount()));
        shutterSpeedTV.setText(String.valueOf(getCurrentPhoto().getShutterSpeed()));
        ratingTV.setText(String.valueOf(getCurrentPhoto().getRating()));
        lensTV.setText(String.valueOf(getCurrentPhoto().getLens()));
        cameraTV.setText(String.valueOf(getCurrentPhoto().getCamera()));
        viewsTV.setText(String.valueOf(getCurrentPhoto().getTimesViewed()));

        HashMap<TextView, ImageView> infoAtoms = new HashMap<>();
        infoAtoms.put(apertureTV, (ImageView) rootView.findViewById(R.id.details_aperture_label));
        infoAtoms.put(favoritesTV, (ImageView) rootView.findViewById(R.id.details_favorites_label));
        infoAtoms.put(isoTV, (ImageView) rootView.findViewById(R.id.details_iso_label));
        infoAtoms.put(shutterSpeedTV, (ImageView) rootView.findViewById(R.id.details_shutter_speed_label));
        infoAtoms.put(ratingTV, (ImageView) rootView.findViewById(R.id.details_rating_label));
        infoAtoms.put(viewsTV, (ImageView) rootView.findViewById(R.id.details_times_viewed_label));

        // Hide elements if there's no available info
        Iterator i = (Iterator) infoAtoms.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            TextView tv = (TextView) me.getKey();
            if (tv.getText().equals("null") || tv.getText().toString().equals("")) {
                ImageView iv = (ImageView) me.getValue();
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
            }
        }
        for (TextView tv : new TextView[]{lensTV, cameraTV}) {
            if (tv.getText().equals("null") || tv.getText().toString().equals("")) {
                tv.setVisibility(View.GONE);
            }
        }
    }

    private void initPhotoAndDescription(View rootView) {
        View bodyContainer = rootView.findViewById(R.id.details_body_container);
        mainPic = (ImageView) rootView.findViewById(R.id.details_photo_view);
        avatarPic = (ImageView) rootView.findViewById(R.id.details_profile_pic_iv);
        TextView photoTitleText = (TextView) bodyContainer.findViewById(R.id.details_photo_title_tv);
        TextView authorNameText = (TextView) bodyContainer.findViewById(R.id.details_author_name_tv);
        HtmlTextView photoDescriptionText = (HtmlTextView) bodyContainer.findViewById(R.id.details_photo_description_tv);
        photoUrl = getCurrentPhoto().getImages().get(ApiConstants.IMAGE_SIZE_CROPPED_LARGE).getUrl();
        uncroppedPhotoUrl = getCurrentPhoto().getImages().get(ApiConstants.IMAGE_SIZE_UNCROPPED_SMALL).getUrl();
        avatarUrl = getCurrentPhoto().getUser().getUserpicUrl();
        String photoName = getCurrentPhoto().getName();
        String authorName = getCurrentPhoto().getUser().getUsername();

        photoTitleText.setText(photoName);
        authorNameText.setText(authorName);
        String tempDescription = getCurrentPhoto().getDescription();
        String photoDescription = tempDescription == null ? "No description" : tempDescription;
        photoDescriptionText.setHtmlFromString(photoDescription, new HtmlTextView.RemoteImageGetter());
    }

    private Photo getCurrentPhoto() {
        return photos.get(photoPosition);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPic = (ImageView) view.findViewById(R.id.details_photo_view);

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
        void onFragmentInteraction(Uri uri);
    }
}
