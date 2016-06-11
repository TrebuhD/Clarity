package com.trebuh.clarity;

import android.app.SearchManager;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.trebuh.clarity.adapters.ClarityPagerAdapter;
import com.trebuh.clarity.fragments.DownloadsFragment;
import com.trebuh.clarity.fragments.PhotoGridFragment;

public class ClarityActivity extends AppCompatActivity
        implements DownloadsFragment.OnFragmentInteractionListener, PhotoGridFragment.PhotoGridFragmentListener {
    private static final String TAG = "ClarityActivity";

    private static final int FRAGMENT_DOWNLOADS = 0;
    private static final int FRAGMENT_PHOTOS = 1;
    private static final int FRAGMENT_PHOTO_DETAILS = 1;

    // Container for fragments
    private ClarityPagerAdapter adapter;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;

    private AppBarLayout appBar;
    private Toolbar toolbar;

    private SearchView searchView;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarity);

        initToolbar();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            initViewpager();
        }

        initDrawer();
        initFab();
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

    private void initViewpager() {
        adapter = new ClarityPagerAdapter(getSupportFragmentManager());

        // order important
        adapter.addItem(DownloadsFragment.newInstance("", ""), "Downloads");
        addPhotoGridFragment(
                PhotoFetcher.FEATURE_POPULAR,
                PhotoFetcher.SORT_METHOD_COMMENTS_COUNT,
                "Popular Photos");

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

                // hide fab in gallery view
                if (fab != null) {
                    if (position == FRAGMENT_PHOTOS) {
                        fab.hide();
                    } else {
                        fab.show();
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        transitionToFragment(FRAGMENT_PHOTOS);
    }

    private void addPhotoGridFragment(String feature, String sortMethod, String gridTitle) {
        adapter.addItem(PhotoGridFragment.newInstance(feature, sortMethod), gridTitle);
    }

    private void addDetailsFragment(String photoId) {

    }

    private void initDrawer() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    drawerLayout.closeDrawers();
                    item.setChecked(true);
                    switch (item.getItemId()) {
                        //                    TODO Replace with proper actions
                        case R.id.navigation_item_home:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            break;
                        case R.id.navigation_item_downloads:
                            transitionToFragment(FRAGMENT_DOWNLOADS);
                            break;
                        case R.id.navigation_item_settings:
                            Snackbar.make(coordinatorLayout, "Settings button clicked",
                                    Snackbar.LENGTH_LONG)
                                    .show();
                            break;
                        case R.id.drawer_bookmark_editors:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_editors);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_EDITORS);
                            break;
                        case R.id.drawer_bookmark_upcoming:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_upcoming);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_UPCOMING);
                            break;
                        case R.id.drawer_bookmark_highest_rated:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_highest_rated);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_HIGHEST_RATED);
                            break;
                        case R.id.drawer_bookmark_fresh_today:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_today);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_FRESH_TODAY);
                            break;
                        case R.id.drawer_bookmark_fresh_week:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_week);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_FRESH_WEEK);
                            break;
                        case R.id.drawer_bookmark_fresh_yesterday:
                            transitionToFragment(FRAGMENT_PHOTOS);
                            getSupportActionBar().setTitle(R.string.drawer_bookmark_fresh_yesterday);
                            ((PhotoGridFragment) getCurrentFragment()).transitionToNewFeature(
                                    PhotoFetcher.FEATURE_FRESH_YESTERDAY);
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
                searchView.setQuery("test", false);
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

    @Override
    public void onPhotoGridItemPressed(String url) {
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
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
            if (viewPager.getCurrentItem() == FRAGMENT_DOWNLOADS) {
                menu.findItem(R.id.menu_action_refresh).setVisible(false);
                menu.findItem(R.id.menu_action_search).setVisible(false);
                menu.findItem(R.id.menu_action_sortby_comments).setVisible(false);
                menu.findItem(R.id.menu_action_sortby_new).setVisible(false);
                menu.findItem(R.id.menu_action_sortby_times_viewed).setVisible(false);
                menu.findItem(R.id.menu_action_sortby_votes).setVisible(false);
                menu.findItem(R.id.menu_action_sortby_rating).setVisible(false);
            } else {
                menu.findItem(R.id.menu_action_refresh).setVisible(true);
                menu.findItem(R.id.menu_action_search).setVisible(true);
                menu.findItem(R.id.menu_action_sortby_comments).setVisible(true);
                menu.findItem(R.id.menu_action_sortby_new).setVisible(true);
                menu.findItem(R.id.menu_action_sortby_times_viewed).setVisible(true);
                menu.findItem(R.id.menu_action_sortby_votes).setVisible(true);
                menu.findItem(R.id.menu_action_sortby_rating).setVisible(true);
            }
        }

        searchMenuItem = menu.findItem(R.id.menu_action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Query: " + query, Toast.LENGTH_SHORT).show();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
            case R.id.menu_action_search:
                // TODO add search functionality
                SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//                searchManager.startSearch();
                break;
            case R.id.menu_action_sortby_comments:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_COMMENTS_COUNT);
                break;
            case R.id.menu_action_sortby_rating:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_RATING);
                break;
            case R.id.menu_action_sortby_times_viewed:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_TIMES_VIEWED);
                break;
            case R.id.menu_action_sortby_votes:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_VOTES_COUNT);
                break;
            case R.id.menu_action_sortby_new:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_CREATED_AT);
            case R.id.menu_action_sortby_favorites:
                ((PhotoGridFragment) getCurrentFragment()).sortAndReplaceItems(
                        PhotoFetcher.SORT_METHOD_FAVORITES_COUNT);
        }
        return true;
    }

    private Fragment getCurrentFragment() {
        int currItem = viewPager.getCurrentItem();
        Fragment currFragment = adapter.getItem(currItem);
        if (currFragment instanceof PhotoGridFragment) {
            return currFragment;
        } else {
            return currFragment;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
