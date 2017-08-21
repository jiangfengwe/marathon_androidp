package com.tdin360.zjw.marathon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CircleListAdapter;
import com.tdin360.zjw.marathon.ui.activity.AddCircleActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by admin on 17/7/24.
 */


public class CircleTabFragment extends BaseFragment {

    private String name;
    @ViewInject(R.id.mRecyclerView)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.addBtn)
    private ImageView addButton;
    public static CircleTabFragment newInstance(String name){

        CircleTabFragment fragment = new CircleTabFragment();
        fragment.name=name;


        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.frament_tab_circle,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(name!=null&&name.equals("圈友")){


            addButton.setVisibility(View.VISIBLE);

        }else {

            addButton.setVisibility(View.GONE);
        }
         this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setAdapter(new CircleListAdapter(getContext()));

        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCircleActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
