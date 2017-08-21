package com.tdin360.zjw.marathon.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CircleTabTitleAdapter;

/**
 * 圈子
 * @author zhangzhijun
 * Created by admin on 17/3/9.
 */

public class CircleFragment extends BaseFragment{



    public static CircleFragment newInstance(){


        return new CircleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_circle,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout layout = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        viewPager.setAdapter(new CircleTabTitleAdapter(getChildFragmentManager()));
        layout.setupWithViewPager(viewPager);



    }
}
