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
import com.tdin360.zjw.marathon.adapter.TravelOrderTabLayoutAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * 旅游订单fragment
 */
public class TravelOrderFragment extends BaseFragment {
    @ViewInject(R.id.tabs_travel_order)
    private TabLayout tabTravel;
    @ViewInject(R.id.vp_travel_order)
    private ViewPager vpTravel;

    public TravelOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_order, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vpTravel.setAdapter(new TravelOrderTabLayoutAdapter(getChildFragmentManager()));
        tabTravel.setupWithViewPager(vpTravel);
    }
}
