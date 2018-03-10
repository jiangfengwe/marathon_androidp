package com.tdin360.zjw.marathon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.ui.fragment.EventStateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffery on 2017/8/4.
 * tablayout动态添加文字的adapter
 */

public class EventTabLayoutAdapter extends FragmentPagerAdapter{
    private String[] titles={"进行中","已结束","全部"};
    List<Fragment> list=new ArrayList<>();
    FragmentManager fragmentManager;
    public EventTabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    public EventTabLayoutAdapter(FragmentManager fm, FragmentManager fragmentManager) {
        super(fm);
        this.fragmentManager = fragmentManager;
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
                list.add(eventStateFragment2);
                return eventStateFragment2;
            case 1:
                EventStateFragment eventStateFragment3 = EventStateFragment.newInstance("false");
                list.add(eventStateFragment3);
                return eventStateFragment3;
            case 2:
                EventStateFragment eventStateFragment1 = EventStateFragment.newInstance("");
                list.add(eventStateFragment1);
                return eventStateFragment1;

        }
        return null;
    }

  /*  @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        Fragment fragment = list.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();

    }*/

    @Override
    public int getCount() {
        return titles.length;
    }
}
