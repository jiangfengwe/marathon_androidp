package com.tdin360.zjw.marathon.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.HotelTabAdapter;

import org.xutils.view.annotation.ViewInject;


/**
 * 酒店预订
 * Created by admin on 17/7/26.
 */


public class HotelFragment extends BaseFragment {


    @ViewInject(R.id.tabs)
    private TabLayout tabLayout;
    @ViewInject(R.id.mViewPager)
    private ViewPager viewPager;

    public static HotelFragment newInstance(){

        return new HotelFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_hotel_list,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewPager.setAdapter(new HotelTabAdapter(getChildFragmentManager()));
        this.tabLayout.setupWithViewPager(viewPager);


    }
}
