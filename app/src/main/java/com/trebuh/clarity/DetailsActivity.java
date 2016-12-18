package com.trebuh.clarity;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.trebuh.clarity.fragments.DetailsFragment;
import com.trebuh.clarity.fragments.PhotoLoader;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.network.ApiConstants;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.trebuh.clarity.ClarityActivity.EXTRA_PHOTO_ARRAY_PATH;
import static com.trebuh.clarity.ClarityActivity.EXTRA_STARTING_ALBUM_POSITION;

public class DetailsActivity extends AppCompatActivity
        implements DetailsFragment.OnFragmentInteractionListener {
    public static final String TAG = DetailsActivity.class.getSimpleName();

    private static final String STATE_CURRENT_ITEM_POSITION = "state_current_item_position";

    private int currentPosition;
    private int startingPosition;

    private String photoListUrl;
    private ViewPager pager;

    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    private ArrayList<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up custom fonts
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(ApiConstants.MAIN_FONT)
                .setFontAttrId(R.attr.fontPath)
                .build());

        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_details);

        initSharedTransitions();
        initPhotoList(savedInstanceState);
        initViewPager();
    }

    //
    private void initSharedTransitions() {
        // prevent statusBar blinking during enter transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            final View decor = getWindow().getDecorView();
            decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    decor.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startPostponedEnterTransition();
                    }
                    return true;
                }
            });
        }
    }

    private void initPhotoList(Bundle savedInstanceState) {
        photoList = PhotoLoader.loadSavedPhotos(getApplicationContext(),
                getIntent().getStringExtra(EXTRA_PHOTO_ARRAY_PATH));
        startingPosition = getIntent().getIntExtra(EXTRA_STARTING_ALBUM_POSITION, 0);
        photoListUrl = getIntent().getStringExtra(EXTRA_PHOTO_ARRAY_PATH);
        if (savedInstanceState == null) {
            currentPosition = startingPosition;
        } else {
            currentPosition = savedInstanceState.getInt(STATE_CURRENT_ITEM_POSITION);
        }
    }
    private void initViewPager() {
        pager = (ViewPager) findViewById(R.id.details_view_pager);
        DetailsFragmentPagerAdapter adapter = new DetailsFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
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
        ((DetailsFragment)adapter.getItem(currentPosition)).setTransitionName(
                getResources().getString(R.string.grid_to_details_transition));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
            return DetailsFragment.newInstance(position, startingPosition, photoListUrl);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
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
