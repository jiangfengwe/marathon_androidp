package com.tdin360.zjw.marathon.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.ui.activity.CommentActivity;
import com.tdin360.zjw.marathon.ui.activity.EventActivity;
import com.tdin360.zjw.marathon.ui.activity.WebActivity;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事状态fragment
 */
public class EventStateFragment extends BaseFragment {
    @ViewInject(R.id.rv_event_state)
    private RecyclerView rvEventState;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();


    public EventStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_state, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        for (int i = 0; i <10 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getContext(),list,R.layout.item_event_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                TextView apply = (TextView) holder.getViewById(R.id.tv_apply);
                apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), CommentActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        rvEventState.setAdapter(adapter);
        rvEventState.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ToastUtils.show(getContext(),"赛事");
                //Intent intent=new Intent(getActivity(), WebActivity.class);
                Intent intent=new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
            }
        });
    }
}
