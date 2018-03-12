package com.tdin360.zjw.marathon.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.HotelOrderTabLayoutAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * 酒店订单fragment
 */
public class HotelOrderFragment extends BaseFragment {
    @ViewInject(R.id.tabs_hotel_order)
    private TabLayout tabHotel;
    @ViewInject(R.id.vp_hotel_order)
    private ViewPager vpHotel;


    public HotelOrderFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vpHotel.setAdapter(new HotelOrderTabLayoutAdapter(getChildFragmentManager()));
        vpHotel.setOffscreenPageLimit(1);
        tabHotel.setupWithViewPager(vpHotel);
    }
}
