package com.trebuh.clarity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.trebuh.clarity.adapters.ClarityPagerAdapter;
import com.trebuh.clarity.adapters.PhotoGridAdapter;
import com.trebuh.clarity.fragments.SearchHistoryFragment;
import com.trebuh.clarity.fragments.PhotoGridFragment;
import com.trebuh.clarity.models.Photo;
import com.trebuh.clarity.network.ApiConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    static final String EXTRA_PHOTOS_ARRAY_LIST = "extra_photos_array_list";

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
    private Bundle tmpReenterState;
    private boolean isDetailsActivityStarted;

    private ArrayList<Photo> photoList;
    private ArrayList<String> searchHistoryList;

    // for saving fragment state
    private Fragment retainedPhotoGridFragment;
    private Fragment extraGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarity);
        setExitSharedElementCallback(callback);

        initToolbar();

        if (savedInstanceState != null) {
            searchHistoryList = savedInstanceState.getStringArrayList(STATE_PAST_SEARCHES_LIST);
            retainedPhotoGridFragment = getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT_PHOTOS);
            extraGridFragment = getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT_EXTRA_GRID);
        } else {
            searchHistoryList = new ArrayList<>();
            searchHistoryList.add("Cats");
            searchHistoryList.add("Guitars");
            searchHistoryList.add("Family");
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
                            String.valueOf(adapter.getPageTitle(position)));
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
            transitionToFragment(FRAGMENT_EXTRA_GRID);
            ((PhotoGridFragment) getCurrentFragment()).newSearch(searchTerm);
            adapter.setNewTitle(SIZE_HAS_EXTRA_FRAGMENT - 1, searchTerm);
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
                public boolean onNavigationItemSelected(MenuItem item) {
                    drawerLayout.closeDrawers();
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        case R.id.navigation_item_home:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            break;
                        case R.id.navigation_item_past_searches:
                            transitionToFragment(FRAGMENT_SEARCH_HISTORY);
                            break;
                        case R.id.navigation_item_settings:
                            Snackbar.make(drawerLayout, "Settings button clicked",
                                    Snackbar.LENGTH_LONG)
                                    .show();
                            break;
                        case R.id.drawer_bookmark_editors:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_editors);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    ApiConstants.FEATURE_EDITORS);
                            break;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        tmpReenterState = new Bundle(data.getExtras());

        int startingPosition = tmpReenterState.getInt(EXTRA_STARTING_ALBUM_POSITION);
        int currentPosition = tmpReenterState.getInt(EXTRA_CURRENT_ALBUM_POSITION);
        photoList = tmpReenterState.getParcelableArrayList(EXTRA_PHOTOS_ARRAY_LIST);
        if (startingPosition != currentPosition) {
            recyclerView.scrollToPosition(currentPosition);
        }
        postponeEnterTransition();
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                // necessary to get a smooth transition
                recyclerView.requestLayout();
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public void onPhotoGridItemClick(PhotoGridAdapter.PhotoGridItemHolder caller, String url) {
        Intent intent = new Intent(this, DetailsActivity.class);
        int clickedItemPos = caller.getLayoutPosition();

        ArrayList<Photo> photos = ((PhotoGridFragment) getCurrentFragment()).getPhotoList();
        if (photos == null) {
            throw new IllegalStateException();
        }

        Photo clickedPhoto = photos.get(clickedItemPos);
        int startStrippedIndex = clickedItemPos < 15 ? 0 : clickedItemPos - 15;
        int endStrippedIndex = photos.size() > (clickedItemPos + 15) ? (clickedItemPos + 15) : photos.size();

        // strip the list to max 30 items
        ArrayList<Photo> strippedPhotos = new ArrayList<>(photos.subList(
                startStrippedIndex,
                endStrippedIndex
        ));

        int newPos = -1;
        for (int i = 0; i < strippedPhotos.size(); i++) {
            if ((strippedPhotos.get(i)).getId().equals(clickedPhoto.getId())) {
                newPos = i;
            }
        }

        intent.putExtra(EXTRA_STARTING_ALBUM_POSITION, newPos);
        intent.putExtra(EXTRA_PHOTOS_ARRAY_LIST, strippedPhotos);

        if (!isDetailsActivityStarted) {
            isDetailsActivityStarted = true;
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this,
//                        caller.photoImageView, caller.photoImageView.getTransitionName()).toBundle());
//            } else {
            startActivity(intent);
//            }
        }

//        DetailsFragment photoDetailsFragment = DetailsFragment.newInstance("1", url);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            photoDetailsFragment.setSharedElementEnterTransition(new DetailsTransition());
//            photoDetailsFragment.setEnterTransition(new Fade());
//            photoDetailsFragment.setExitTransition(new Fade());
//            photoDetailsFragment.setSharedElementReturnTransition(new DetailsTransition());
//        }
//
//        addOrReplaceExtraGridFragment("1", url, "Photo details");
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .addSharedElement(caller.photoImageView, "photo")
//                .replace(R.id.details_container, photoDetailsFragment)
//                .addToBackStack(null)
//                .commit();
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
                menu.findItem(R.id.menu_action_search).setVisible(true);
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
//        ((PhotoGridFragment) getCurrentFragment()).performSearch(searchTerm, PhotoFetcher.FIRST_PAGE);
        if (saveInHistory) {
            searchHistoryList.add(searchTerm);
        }
        toolbar.setTitle(searchTerm);
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

    private final SharedElementCallback callback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            if (tmpReenterState != null) {
                int startingPosition = tmpReenterState.getInt(EXTRA_STARTING_ALBUM_POSITION);
                int currentPosition = tmpReenterState.getInt(EXTRA_CURRENT_ALBUM_POSITION);
                if (startingPosition != currentPosition) {
                    // If startingPosition != currentPosition the user must have swiped to a
                    // different page in the DetailsActivity. We must update the shared element
                    // so that the correct one falls into place.
                    String newTransitionName = photoList.get(currentPosition).getName();
                    View newSharedElement = recyclerView.findViewWithTag(newTransitionName);
                    if (newSharedElement != null) {
                        names.clear();
                        names.add(newTransitionName);
                        sharedElements.clear();
                        sharedElements.put(newTransitionName, newSharedElement);
                    }
                }
                tmpReenterState = null;
            } else {
                // If tmpReenterState is null, then the activity is exiting.
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    View navigationBar = findViewById(android.R.id.navigationBarBackground);
                    View statusBar = findViewById(android.R.id.statusBarBackground);
                    if (navigationBar != null) {
                        names.add(navigationBar.getTransitionName());
                        sharedElements.put(navigationBar.getTransitionName(), navigationBar);
                    }
                    if (statusBar != null) {
                        names.add(statusBar.getTransitionName());
                        sharedElements.put(statusBar.getTransitionName(), statusBar);
                    }
                }
            }
        }
    };

    @Override
    public void onPastSearchItemClick(String searchString) {
        performSearch(searchString, false);
    }
}
