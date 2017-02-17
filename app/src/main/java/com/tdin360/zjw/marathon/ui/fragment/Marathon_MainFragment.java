package com.tdin360.zjw.marathon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.activity.MarathonDetailActivity;
import com.tdin360.zjw.marathon.ui.activity.SearchActivity;
import com.tdin360.zjw.marathon.adapter.MarathonListViewAdapter;
import com.tdin360.zjw.marathon.model.MarathonEventModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**马拉松赛事列表
 * Created by Administrator on 2016/8/9.
 */
public class Marathon_MainFragment extends Fragment implements RefreshListView.OnRefreshListener,AdapterView.OnItemClickListener {
    private static Marathon_MainFragment marathonFragment;
    private List<MarathonEventModel>list = new ArrayList<>();
    private RefreshListView listView;
    private MarathonListViewAdapter marathonListViewAdapter;
    private int pageNumber=1;
    private int pageCount;
    private boolean isLoadMore;
    private boolean isRefresh;
    private LinearLayout loadView,failView;

    public static Marathon_MainFragment newInstance(){
        if(marathonFragment==null){
            marathonFragment=new Marathon_MainFragment();
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


         this.listView= (RefreshListView) view.findViewById(R.id.listView);
         this.loadView = (LinearLayout) view.findViewById(R.id.loadView);
         this.failView = (LinearLayout) view.findViewById(R.id.failView);
         this.marathonListViewAdapter = new MarathonListViewAdapter(getActivity(),list);
         this.listView.setAdapter(marathonListViewAdapter);
         this.listView.setOnRefreshListener(this);
         this.listView.setOnItemClickListener(this);
        //加载失败点击重加载
          view.findViewById(R.id.loadFail).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  loadData();
              }
          });

        //加载数据
          loadData();
        //搜索
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDownPullRefresh() {
         isRefresh=true;
         pageNumber=1;
         list.clear();
          loadData();
    }

    @Override
    public void onLoadingMore() {

        if(pageNumber<pageCount){

            pageNumber++;
            isLoadMore=true;
            loadData();
        }else {
            listView.hideFooterView();
           Toast.makeText(getContext(),"亲,到底了~",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MarathonEventModel eventInfo = list.get(position-1);
        //为单例成员赋值
        MarathonDataUtils.init().setEventId(eventInfo.getId()+"");
        Intent intent = new Intent(getActivity(), MarathonDetailActivity.class);
        intent.putExtra("eventId",eventInfo.getId());
        intent.putExtra("eventName",eventInfo.getName());
        startActivity(intent);
    }


    //加载网路数据
    private void loadData(){

        failView.setVisibility(View.GONE);
        loadView.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams(HttpUrlUtils.Marath_ALL_Envent);
        params.addQueryStringParameter("pageNumber",pageNumber+"");
        params.setConnectTimeout(5*1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject obj = new JSONObject(s);

                    pageCount = obj.getInt("TotalPages");
                    JSONArray array = obj.getJSONArray("EventSystemMessageList");

                    for(int i=0;i<array.length();i++){

                        JSONObject object = array.getJSONObject(i);

                        int id = object.getInt("Id");
                        String eventName = object.getString("EventName");
                        String status = object.getString("Status");
                        String eventStartTime = object.getString("EventStartTimeStr");
                        long time = object.getLong("Timestamp");
                     list.add(new MarathonEventModel(id,eventName,status,"",eventStartTime,time));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    failView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                if(throwable instanceof HttpException){

                  Toast.makeText(getActivity(),"网路连接失败!",Toast.LENGTH_SHORT).show();
                }
                failView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                loadView.setVisibility(View.GONE);
                //加载更多
                if(isLoadMore){
                    listView.hideFooterView();
                    isLoadMore=false;

                }
//                刷新
                if(isRefresh){

                    listView.hideHeaderView();
                    isRefresh=false;
                }
              marathonListViewAdapter.updateList(list);
            }
        });
    }
}
