package com.trebuh.clarity;

import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.trebuh.clarity.fragments.DownloadsFragment;
import com.trebuh.clarity.fragments.OnFragmentInteractionListener;
import com.trebuh.clarity.fragments.PhotoGridFragment;

public class ClarityActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarity);

        // initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init_drawer();

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer);
        // Hamburger icon won't show up without this
        drawerToggle.syncState();
    }

    private void init_drawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.content_frame_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                item.setChecked(true);
                switch (item.getItemId()) {
//                    TODO Replace with proper actions
                    case R.id.navigation_item_home:
                        Snackbar.make(coordinatorLayout, "Home button clicked", Snackbar.LENGTH_LONG)
                                .show();
                        // Transition to Photo grid fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame_layout, PhotoGridFragment.newInstance("lol", "lol2"))
                                .commit();
                        break;
                    case R.id.navigation_item_downloads:
                        Snackbar.make(coordinatorLayout, "Downloads button clicked", Snackbar.LENGTH_LONG)
                                .show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame_layout, DownloadsFragment.newInstance("lo", "hi"))
                                .commit();
                        break;
                    case R.id.navigation_item_settings:
                        Snackbar.make(coordinatorLayout, "Settings button clicked", Snackbar.LENGTH_LONG)
                                .show();
                }
                return true;
            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
