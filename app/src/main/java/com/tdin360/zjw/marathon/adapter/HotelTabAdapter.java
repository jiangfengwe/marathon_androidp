package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.ui.fragment.HotelTabFragment;

/**
 * Created by admin on 17/7/26.
 */

public class HotelTabAdapter extends FragmentPagerAdapter{

    private String[] titles={"全部","经济","舒适","豪华"};
    public HotelTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HotelTabFragment.newInstance(titles[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


    }
}
