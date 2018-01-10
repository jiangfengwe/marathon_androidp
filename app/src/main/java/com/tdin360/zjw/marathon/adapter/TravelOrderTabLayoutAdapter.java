package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tdin360.zjw.marathon.ui.fragment.EventStateFragment;
import com.tdin360.zjw.marathon.ui.fragment.TravelStateFragment;

/**
 * Created by jeffery on 2017/8/4.
 * tablayout动态添加文字的adapter
 */

public class TravelOrderTabLayoutAdapter extends FragmentPagerAdapter{
    private String[] titles={"全部","待支付","待使用","待评价","退款"};
    public TravelOrderTabLayoutAdapter(FragmentManager fm) {
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
                TravelStateFragment travelStateFragment1 = TravelStateFragment.newInstance("");
                return travelStateFragment1;
            case 1:
                TravelStateFragment travelStateFragment2 = TravelStateFragment.newInstance("1");
                return travelStateFragment2;
            case 2:
                TravelStateFragment travelStateFragment3 = TravelStateFragment.newInstance("3");
                return travelStateFragment3;
            case 3:
                TravelStateFragment travelStateFragment4 = TravelStateFragment.newInstance("4");
                return travelStateFragment4;
            case 4:
                TravelStateFragment travelStateFragment5 = TravelStateFragment.newInstance("6");
                return travelStateFragment5;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
