package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tdin360.zjw.marathon.ui.fragment.EventStateFragment;

/**
 * Created by jeffery on 2017/8/4.
 * tablayout动态添加文字的adapter
 */

public class EventTabLayoutAdapter extends FragmentPagerAdapter{
    private String[] titles={"进行中","已结束","全部"};
    public EventTabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                EventStateFragment eventStateFragment2 = EventStateFragment.newInstance("true");
                return eventStateFragment2;
            case 1:
                EventStateFragment eventStateFragment3 = EventStateFragment.newInstance("false");
                return eventStateFragment3;
            case 2:
                EventStateFragment eventStateFragment1 = EventStateFragment.newInstance("");
                return eventStateFragment1;

        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
