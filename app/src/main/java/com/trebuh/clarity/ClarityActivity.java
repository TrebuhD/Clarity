package com.trebuh.clarity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.trebuh.clarity.adapters.ClarityPagerAdapter;
import com.trebuh.clarity.adapters.PhotoGridAdapter;
import com.trebuh.clarity.fragments.PhotoGridFragment;
import com.trebuh.clarity.fragments.SearchHistoryFragment;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.network.ApiConstants;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ClarityActivity extends AppCompatActivity
        implements SearchHistoryFragment.OnFragmentInteractionListener,
        PhotoGridFragment.PhotoGridFragmentListener {
    private static final String TAG = "ClarityActivity";

    private static final int FRAGMENT_SEARCH_HISTORY = 0;
    private static final int FRAGMENT_PHOTOS = 1;
    private static final int FRAGMENT_EXTRA_GRID = 2;

    private static final int SIZE_HAS_EXTRA_FRAGMENT = 3;
    private static final String STATE_PAST_SEARCHES_LIST = "state_past_searches_list";

    static final String EXTRA_STARTING_ALBUM_POSITION = "extra_starting_album_position";
    static final String EXTRA_CURRENT_ALBUM_POSITION = "extra_current_album_position";
    static final String EXTRA_PHOTO_ARRAY_PATH = "extra_photo_array_path";

    private static final String STATE_FRAGMENT_PHOTOS = "state_fragment_photos";
    private static final String STATE_FRAGMENT_EXTRA_GRID = "state_fragment_extra_grid";

    // Container for fragments
    private ClarityPagerAdapter adapter;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;

    private AppBarLayout appBar;
    private Toolbar toolbar;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    private String currentSortMethod = "";

    // Shared element transition stuff
    private RecyclerView recyclerView;
    private boolean isDetailsActivityStarted;

    private ArrayList<String> searchHistoryList;

    // for saving fragment state
    private Fragment retainedPhotoGridFragment;
    private Fragment extraGridFragment;
    private String transitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(ApiConstants.MAIN_FONT)
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_clarity);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).
                    inflateTransition(R.transition.activity_slide));
        }

        initToolbar();

        if (savedInstanceState != null) {
            searchHistoryList = savedInstanceState.getStringArrayList(STATE_PAST_SEARCHES_LIST);
            retainedPhotoGridFragment = getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT_PHOTOS);
            extraGridFragment = getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT_EXTRA_GRID);
        } else {
            searchHistoryList = new ArrayList<>();
            searchHistoryList.add("Cats");
            searchHistoryList.add("Guitars");
            searchHistoryList.add("Fandom");
        }

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            initViewPager();
        }

        initDrawer();
        initFab();
    }

    @Override
    protected void onStart() {
        recyclerView = ((PhotoGridFragment) getCurrentFragment()).getPhotosContainer();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isDetailsActivityStarted = false;
    }

    private void initToolbar() {
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle.syncState();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initViewPager() {
        adapter = new ClarityPagerAdapter(getSupportFragmentManager());

        // order important
        adapter.addItem(SearchHistoryFragment.newInstance(searchHistoryList), "Search history");

        PhotoGridFragment photoFragment = (retainedPhotoGridFragment == null) ?
                PhotoGridFragment.newInstance(ApiConstants.FEATURE_POPULAR,
                        ApiConstants.SORT_METHOD_COMMENTS_COUNT) : (PhotoGridFragment) retainedPhotoGridFragment;

        adapter.addItem(photoFragment, "Popular Photos");

        if (extraGridFragment != null) {
            adapter.addItem(extraGridFragment, "");
        }

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // call onCreateOptionsMenu()
                invalidateOptionsMenu();

                // show toolbar and change its title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            customFontStringWrapper(
                                    String.valueOf(adapter.getPageTitle(position))));
                }
                onAppBarShow();

                // hide fab and subtitle in gallery view
                if (fab != null) {
                    if (position == FRAGMENT_SEARCH_HISTORY) {
                        fab.show();
                        clearToolbarSubtitle();
                    } else {
                        fab.hide();
                        setToolbarSubtitle(currentSortMethod);
                    }
                }

                if (position == FRAGMENT_EXTRA_GRID) {
                    setToolbarSubtitle("Search results");
                } else {
                    setToolbarSubtitle(currentSortMethod);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        transitionToFragment(FRAGMENT_PHOTOS);
    }

    private void addOrReplaceExtraGridFragment(String searchTerm) {
        if (adapter.getCount() == SIZE_HAS_EXTRA_FRAGMENT) {
            adapter.deleteItem(FRAGMENT_EXTRA_GRID);
            adapter.addItem(PhotoGridFragment.newSearchInstance(searchTerm), searchTerm);
            transitionToFragment(FRAGMENT_EXTRA_GRID);
        } else {
            adapter.addItem(PhotoGridFragment.newSearchInstance(searchTerm), searchTerm);
        }
        adapter.notifyDataSetChanged();
    }

    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    drawerLayout.closeDrawers();
                    item.setChecked(true);
                    assert getSupportActionBar() != null;
                    switch (item.getItemId()) {
                        case R.id.navigation_item_home:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            break;
                        case R.id.navigation_item_past_searches:
                            transitionToFragment(FRAGMENT_SEARCH_HISTORY);
                            break;
//                        case R.id.navigation_item_settings:
//                            Snackbar.make(drawerLayout, "Settings button clicked",
//                                    Snackbar.LENGTH_LONG)
//                                    .show();
//                            break;
//                        case R.id.drawer_bookmark_editors:
//                            transitionToFragment(FRAGMENT_PHOTOS);
//                            getSupportActionBar().setTitle(R.string.drawer_bookmark_editors);
//                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
//                                    ApiConstants.FEATURE_EDITORS);
//                            break;
                        case R.id.drawer_bookmark_upcoming:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_upcoming);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_UPCOMING);
                            break;
                        case R.id.drawer_bookmark_highest_rated:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_highest_rated);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_HIGHEST_RATED);
                            break;
                        case R.id.drawer_bookmark_fresh_today:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_today);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_FRESH_TODAY);
                            break;
                        case R.id.drawer_bookmark_fresh_week:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_week);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_FRESH_WEEK);
                            break;
                        case R.id.drawer_bookmark_fresh_yesterday:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_yesterday);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_FRESH_YESTERDAY);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private SpannableString customFontStringWrapper(String string) {
        SpannableString s = new SpannableString(string);
        s.setSpan(new TypefaceSpan(ApiConstants.ACCENT_FONT), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                searchView.requestFocus();
                MenuItemCompat.expandActionView(searchMenuItem);
                searchMenuItem.expandActionView();
            }
        });
        fab.hide();
    }

    private void transitionToFragment(int fragmentType) {
        appBar.setExpanded(true);
        viewPager.setCurrentItem(fragmentType);
    }

    // todo make work in <Lollipop
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Bundle tmpReenterState = new Bundle(data.getExtras());

        int startingPosition = tmpReenterState.getInt(EXTRA_STARTING_ALBUM_POSITION);
        int currentPosition = tmpReenterState.getInt(EXTRA_CURRENT_ALBUM_POSITION);
        if (startingPosition != currentPosition) {
            recyclerView.scrollToPosition(currentPosition);
        }
    }

    @Override
    public void onPhotoGridItemClick(PhotoGridAdapter.PhotoGridItemHolder caller, String url) {
        int clickedItemPos = caller.getLayoutPosition();
        ArrayList<Photo> photos = ((PhotoGridFragment) getCurrentFragment()).getPhotoList();
        Photo clickedPhoto = photos.get(clickedItemPos);
        Log.d(TAG, "clicked photo: " + clickedPhoto.getName());

        // pass the imageView of clicked the grid item
        launchDetailActivity(clickedItemPos, caller.itemView);
    }

    private void launchDetailActivity(int clickedItemPos, View photoView) {
        if (!isDetailsActivityStarted) {
            isDetailsActivityStarted = true;
        }

        PhotoGridFragment currentFragment = (PhotoGridFragment) getCurrentFragment();
        String photoPath = currentFragment.getFilename();

        Log.d(TAG, "Clicked item pos: " + clickedItemPos);

        Intent intent = new Intent(ClarityActivity.this, DetailsActivity.class);
        intent.putExtra(EXTRA_STARTING_ALBUM_POSITION, clickedItemPos);
        intent.putExtra(EXTRA_PHOTO_ARRAY_PATH, photoPath);

        if (Build.VERSION.SDK_INT < 21) {
            startActivity(intent);
        } else {
//             array of items for shared element transition
            View statusBar = findViewById(android.R.id.statusBarBackground);
            View navigationBar = findViewById(android.R.id.navigationBarBackground);
            List<Pair<View, String>> pairs = new ArrayList<>();
//            String transitionName = getString(R.string.grid_to_details_transition);
            String transitionName = String.valueOf(currentFragment.getPhotoList().get(clickedItemPos));
            pairs.add((Pair.create(photoView.findViewById(R.id.photo_grid_item_iv), transitionName)));
//            pairs.add((Pair.create(photoView.findViewById(R.id.photo_grid_item_title_tv), getString(R.string.grid_to_details_transition_title))));
//             prevent null pointers on some devices
            if (navigationBar != null) {
                pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
            }
            if (statusBar != null) {
                pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
            }
            Bundle options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(ClarityActivity.this,
                            pairs.toArray(new Pair[pairs.size()])).toBundle();
//            Bundle options = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation(ClarityActivity.this,
//                            photoView,
//                            String.valueOf(R.string.grid_to_details_transition)).toBundle();
            startActivity(intent, options);
        }
    }

    @Override
    public void onAppBarShow() {
        appBar.setExpanded(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // handle hiding menu items on viewPager fragment change
        if (viewPager != null) {
            if (viewPager.getCurrentItem() == FRAGMENT_SEARCH_HISTORY) {
                menu.findItem(R.id.menu_action_refresh).setVisible(false);
                menu.findItem(R.id.menu_action_search).setVisible(false);
                hideSortButtons(menu, false);
            } else if (viewPager.getCurrentItem() == FRAGMENT_EXTRA_GRID) {
                menu.findItem(R.id.menu_action_refresh).setVisible(false);
                menu.findItem(R.id.menu_action_search).setVisible(false);
                hideSortButtons(menu, false);
            } else {
                menu.findItem(R.id.menu_action_refresh).setVisible(true);
                menu.findItem(R.id.menu_action_search).setVisible(true);
                hideSortButtons(menu, true);
            }
        }

        searchMenuItem = menu.findItem(R.id.menu_action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // collapse the search view
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                performSearch(query, true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void hideSortButtons(Menu menu, boolean visible) {
        menu.findItem(R.id.menu_action_sortby_comments).setVisible(visible);
        menu.findItem(R.id.menu_action_sortby_new).setVisible(visible);
        menu.findItem(R.id.menu_action_sortby_times_viewed).setVisible(visible);
        menu.findItem(R.id.menu_action_sortby_votes).setVisible(visible);
        menu.findItem(R.id.menu_action_sortby_rating).setVisible(visible);
        menu.findItem(R.id.menu_action_sortby_favorites).setVisible(visible);
    }

    private void performSearch(String searchTerm, boolean saveInHistory) {
        addOrReplaceExtraGridFragment(searchTerm);
        transitionToFragment(FRAGMENT_EXTRA_GRID);
        if (saveInHistory) {
            searchHistoryList.add(searchTerm);
        }
        toolbar.setTitle(customFontStringWrapper(searchTerm));
    }

    private void clearToolbarSubtitle() {
        if (toolbar != null) {
            toolbar.setSubtitle("");
        }
    }

    private void setToolbarSubtitle(String subtitle) {
        if (toolbar != null) {
            toolbar.setSubtitle(subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_refresh:
                Log.d(TAG, "Current fragment number: " + viewPager.getCurrentItem());
                if (viewPager.getCurrentItem() == FRAGMENT_PHOTOS) {
                    Log.d(TAG, "in fragment PHOTOS");
                    ((PhotoGridFragment) getCurrentFragment()).onRefresh();
                }
                break;
            case R.id.menu_action_sortby_comments:
                setToolbarSubtitle("Most comments");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_COMMENTS_COUNT);
                break;
            case R.id.menu_action_sortby_rating:
                setToolbarSubtitle("Top rated");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_RATING);
                break;
            case R.id.menu_action_sortby_times_viewed:
                setToolbarSubtitle("Most viewed");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_TIMES_VIEWED);
                break;
            case R.id.menu_action_sortby_votes:
                setToolbarSubtitle("Most votes");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_VOTES_COUNT);
                break;
            case R.id.menu_action_sortby_new:
                setToolbarSubtitle("Fresh");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_CREATED_AT);
                break;
            case R.id.menu_action_sortby_favorites:
                setToolbarSubtitle("Most favorited");
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        ApiConstants.SORT_METHOD_FAVORITES_COUNT);
                break;
        }
        return true;
    }

    private Fragment getCurrentFragment() {
        int currItem = viewPager.getCurrentItem();
        return adapter.getItem(currItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(STATE_PAST_SEARCHES_LIST, searchHistoryList);

        // save the fragment's instance
        if (viewPager.getCurrentItem() == FRAGMENT_PHOTOS) {
            if (getCurrentFragment().isAdded()) {
                getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT_PHOTOS, getCurrentFragment());
            }
        } else if (viewPager.getCurrentItem() == FRAGMENT_EXTRA_GRID) {
            getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT_EXTRA_GRID, getCurrentFragment());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        // load the fragment's instance
        if (viewPager.getCurrentItem() == FRAGMENT_PHOTOS) {
            if (getCurrentFragment().isAdded()) {
                getSupportFragmentManager().getFragment(inState, STATE_FRAGMENT_PHOTOS);
            }
        } else if (viewPager.getCurrentItem() == FRAGMENT_EXTRA_GRID) {
            getSupportFragmentManager().getFragment(inState, STATE_FRAGMENT_EXTRA_GRID);
        }

    }

    @Override
    public void onPastSearchItemClick(String searchString) {
        performSearch(searchString, false);
    }
}
