package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tdin360.zjw.marathon.ui.fragment.CircleTabFragment;

/**
 * 圈友顶部标题菜单
 * Created by admin on 17/7/24.
 */

public class CircleTabTitleAdapter extends FragmentPagerAdapter {


    private String[] titles={"圈友","附近","关注"};
    public CircleTabTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return CircleTabFragment.newInstance(titles[position]);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
