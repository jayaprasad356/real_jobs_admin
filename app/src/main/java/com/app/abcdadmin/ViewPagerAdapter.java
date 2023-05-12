package com.app.abcdadmin;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.realjobadmin.LoginForJoiningsFragment;
import com.app.realjobadmin.LoginForQueriesFragment;
import com.app.realjobadmin.SuperAdminFragment;

public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    public ViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new LoginForJoiningsFragment();
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Login For Joinings";

        return title;
    }
}
