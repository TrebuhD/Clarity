package com.trebuh.clarity.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ClarityPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public ClarityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        fragments.remove(position);
        fragmentTitles.remove(position);
        notifyDataSetChanged();
    }

    @Override
    // Yet another bug in FragmentStatePagerAdapter that destroyItem is called on fragment that hasnt been added. Need to catch
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            super.destroyItem(container, position, object);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void setNewTitle(int i, String searchTerm) {
        fragmentTitles.set(i, searchTerm);
    }
}
