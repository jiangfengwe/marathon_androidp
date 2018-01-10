package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tdin360.zjw.marathon.ui.fragment.EventStateFragment;
import com.tdin360.zjw.marathon.ui.fragment.HotelStateFragment;

/**
 * Created by jeffery on 2017/8/4.
 * tablayout动态添加文字的adapter
 */

public class HotelOrderTabLayoutAdapter extends FragmentPagerAdapter{
    private String[] titles={"全部","待支付","待使用","待评价","退款"};
    public HotelOrderTabLayoutAdapter(FragmentManager fm) {
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
                HotelStateFragment hotelStateFragment1 = HotelStateFragment.newInstance("");
                return hotelStateFragment1;
            case 1:
                HotelStateFragment hotelStateFragment2 = HotelStateFragment.newInstance("1");
                return hotelStateFragment2;
            case 2:
                HotelStateFragment hotelStateFragment3 = HotelStateFragment.newInstance("3");
                return hotelStateFragment3;
            case 3:
                HotelStateFragment hotelStateFragment4 = HotelStateFragment.newInstance("4");
                return hotelStateFragment4;
            case 4:
                HotelStateFragment hotelStateFragment5 = HotelStateFragment.newInstance("6");
                return hotelStateFragment5;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
