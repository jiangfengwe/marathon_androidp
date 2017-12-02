package com.tdin360.zjw.marathon.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.ui.activity.HotelOrderDetailActivity;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店状态列表
 */
public class HotelStateFragment extends BaseFragment {
    @ViewInject(R.id.rv_hotel_state)
    private RecyclerView rvHotel;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();


    public HotelStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_state, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getContext(),list,R.layout.item_order_hotel_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

            }
        };
        rvHotel.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvHotel.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), HotelOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
