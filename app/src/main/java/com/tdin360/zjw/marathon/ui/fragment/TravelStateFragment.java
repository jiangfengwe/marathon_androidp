package com.tdin360.zjw.marathon.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.ui.activity.TravelOrderDetailActivity;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游订单状态fragment，动态生成
 */
public class TravelStateFragment extends BaseFragment {
    @ViewInject(R.id.rv_travel_state)
    private RecyclerView rvState;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();


    public TravelStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_state, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getContext(),list,R.layout.item_order_travel_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

            }
        };
        rvState.setAdapter(adapter);
        rvState.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), TravelOrderDetailActivity.class);
                startActivity(intent);

            }
        });


    }
}
