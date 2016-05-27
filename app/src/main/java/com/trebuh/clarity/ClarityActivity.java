package com.trebuh.clarity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ClarityActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clarity);

        // initialize toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init_drawer();
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
                    case R.id.navigation_item_downloads:
                        Snackbar.make(coordinatorLayout, "Download clicked", Snackbar.LENGTH_LONG)
                                .show();
                        break;
                    case R.id.navigation_item_settings:
                        Snackbar.make(coordinatorLayout, "Settings button clicked", Snackbar.LENGTH_LONG)
                                .show();
                }
                return true;
            }
        });
    }
}
