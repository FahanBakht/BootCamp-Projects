package com.example.farhan.campussystem.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/7/2018.
 */

public class SignUpPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;
    private String tabTitles[] = new String[] { "Student SignUp", "Company SignUp", "Admin SignUp"};

    public SignUpPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
        // Generate title based on item position
        return tabTitles[position];
    }
}
