package com.trebuh.clarity;

import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trebuh.clarity.adapters.ClarityPagerAdapter;
import com.trebuh.clarity.fragments.DownloadsFragment;
import com.trebuh.clarity.fragments.PhotoGridFragment;

public class ClarityActivity extends AppCompatActivity
        implements DownloadsFragment.OnFragmentInteractionListener,
        PhotoGridFragment.OnFragmentInteractionListener {

    // Container for fragments
    private ViewPager viewPager;
    private ClarityPagerAdapter adapter;
    // Drawer
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        if (viewPager != null) {
            init_viewpager(viewPager);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // initialize toolbar
        init_toolbar();
        init_drawer();

        fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Snackbar lol", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void init_viewpager(ViewPager viewPager) {
        adapter = new ClarityPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new PhotoGridFragment(), "Top Authors");
        adapter.addItem(new DownloadsFragment(), "Downloads");
        viewPager.setAdapter(adapter);
    }

    private void init_toolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle.syncState();
    }

    private void init_drawer() {
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                item.setChecked(true);
                switch (item.getItemId()) {
//                    TODO Replace with proper actions
                    case R.id.navigation_item_home:
                        // Transition to Photo grid fragment
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_tab_layout,
//                                        PhotoGridFragment.newInstance("lol", "lol2"))
//                                .commit();
                        Snackbar.make(coordinatorLayout, "Home button clicked",
                                Snackbar.LENGTH_LONG)
                                .show();
                        break;
                    case R.id.navigation_item_downloads:
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.main_tab_layout,
//                                        DownloadsFragment.newInstance("lo", "hi"))
//                                .commit();
                        Snackbar.make(coordinatorLayout, "Downloads button clicked",
                                Snackbar.LENGTH_LONG)
                                .show();
                        break;
                    case R.id.navigation_item_settings:
                        Snackbar.make(coordinatorLayout, "Settings button clicked",
                                Snackbar.LENGTH_LONG)
                                .show();
                }
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_sortby_popular:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
