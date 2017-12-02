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
    private String[] titles={"全部","待付款","待评价","已完成","退款"};
    public HotelOrderTabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
       /* switch (position){
            case 0:
                OrderFoodAllFragment orderFoodAllFragment1 = OrderFoodAllFragment.newInstance("all");
                return orderFoodAllFragment1;
            case 1:
                OrderFoodAllFragment orderFoodAllFragment2 = OrderFoodAllFragment.newInstance("notPay");
                return orderFoodAllFragment2;
            case 2:
                OrderFoodAllFragment orderFoodAllFragment3 = OrderFoodAllFragment.newInstance("hasPay");
                return orderFoodAllFragment3;
            case 3:
                OrderFoodAllFragment orderFoodAllFragment4 = OrderFoodAllFragment.newInstance("hasFinish");
                return orderFoodAllFragment4;
        }*/
        return new HotelStateFragment();
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
