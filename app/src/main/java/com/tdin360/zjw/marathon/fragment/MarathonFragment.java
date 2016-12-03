package com.tdin360.zjw.marathon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.activity.MarathonDetailActivity;
import com.tdin360.zjw.marathon.activity.SearchActivity;
import com.tdin360.zjw.marathon.adapter.MarathonListViewAdapter;
import com.tdin360.zjw.marathon.model.MarathonInfo;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**马拉松赛事列表
 * Created by Administrator on 2016/8/9.
 */
public class MarathonFragment extends Fragment implements RefreshListView.OnRefreshListener,AdapterView.OnItemClickListener {
    private static MarathonFragment marathonFragment;
    private List<MarathonInfo>list = new ArrayList<>();
    private RefreshListView listView;
    private MarathonListViewAdapter marathonListViewAdapter;
    public static MarathonFragment newInstance(){
        if(marathonFragment==null){
            marathonFragment=new MarathonFragment();
        }
        return marathonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.marathon_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        for(int i=0;i<5;i++){
            MarathonInfo marathonInfo = new MarathonInfo();

            Date d1  =new Date(System.currentTimeMillis());
            SimpleDateFormat f  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  d2 = null;
            try {
                d2 = f.parse("2017-07-01 8:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long now =d2.getTime()-d1.getTime();
            marathonInfo.setTime(now*(i+1));
            list.add(marathonInfo);
        }
         this.listView= (RefreshListView) view.findViewById(R.id.listView);
         marathonListViewAdapter = new MarathonListViewAdapter(getActivity(),list);
        this.listView.setAdapter(marathonListViewAdapter);
         this.listView.setOnRefreshListener(this);
         this.listView.setOnItemClickListener(this);
        //搜索
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    private Handler handler = new Handler();
    @Override
    public void onDownPullRefresh() {
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                listView.hideHeaderView();
             }
         },500);
    }

    @Override
    public void onLoadingMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.hideFooterView();
            }
        },500);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), MarathonDetailActivity.class);
        intent.putExtra("title","贵阳国际马拉松");
        startActivity(intent);
    }

}
