package com.trebuh.clarity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.trebuh.clarity.fragments.DetailsFragment;
import com.trebuh.clarity.models.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.trebuh.clarity.ClarityActivity.EXTRA_PHOTOS_ARRAY_LIST;
import static com.trebuh.clarity.ClarityActivity.EXTRA_STARTING_ALBUM_POSITION;
import static com.trebuh.clarity.ClarityActivity.EXTRA_CURRENT_ALBUM_POSITION;

public class DetailsActivity extends AppCompatActivity
        implements DetailsFragment.OnFragmentInteractionListener {
    public static final String TAG = DetailsActivity.class.getSimpleName();

    private static final String STATE_CURRENT_ITEM_POSITION = "state_current_item_position";

    private DetailsFragment currentDetailsFragment;
    private int currentPosition;
    private int startingPosition;

    private ArrayList<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_details);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).
//                    inflateTransition(R.transition.activity_slide));
//        }

        // prevent statusBar blinking during enter transition
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            postponeEnterTransition();
//            final View decor = getWindow().getDecorView();
//            decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
//                    decor.getViewTreeObserver().removeOnPreDrawListener(this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        startPostponedEnterTransition();
//                    }
//                    return true;
//                }
//            });
//        }

        photoList = getIntent().getParcelableArrayListExtra(EXTRA_PHOTOS_ARRAY_LIST);
        Log.d(TAG, "onCreate(): photoList size: " + photoList.size());
        startingPosition = getIntent().getIntExtra(EXTRA_STARTING_ALBUM_POSITION, 0);
        if (savedInstanceState == null) {
            currentPosition = startingPosition;
        } else {
            currentPosition = savedInstanceState.getInt(STATE_CURRENT_ITEM_POSITION);
        }

        ViewPager pager = (ViewPager) findViewById(R.id.details_view_pager);
        pager.setAdapter(new DetailsFragmentPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(currentPosition);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(STATE_CURRENT_ITEM_POSITION, currentPosition);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private class DetailsFragmentPagerAdapter extends FragmentStatePagerAdapter {

        DetailsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailsFragment.newInstance(position, startingPosition, photoList);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            currentDetailsFragment = (DetailsFragment) object;
        }

        @Override
        public int getCount() {
            return (photoList == null ? 0 : photoList.size());
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
