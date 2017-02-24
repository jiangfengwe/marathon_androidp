package com.tdin360.zjw.marathon.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.activity.MarathonDetailsActivity;
import com.tdin360.zjw.marathon.ui.activity.SearchActivity;
import com.tdin360.zjw.marathon.adapter.MarathonListViewAdapter;
import com.tdin360.zjw.marathon.model.MarathonEventModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
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

    private List<MarathonEventModel>list = new ArrayList<>();
    private RefreshListView listView;
    private MarathonListViewAdapter marathonListViewAdapter;
    private int pageNumber=1;
    private int pageCount;
    private LinearLayout loading;
    private TextView loadFile;

    public static Marathon_MainFragment newInstance(){

        return new Marathon_MainFragment();
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
         this.loading = (LinearLayout) view.findViewById(R.id.loading);
         this.loadFile = (TextView) view.findViewById(R.id.loadFail);
         this.marathonListViewAdapter = new MarathonListViewAdapter(getActivity(),list);
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


        //加载数据
         loadData();
    }

    @Override
    public void onDownPullRefresh() {

         pageNumber=1;
         list.clear();
          httpRequest();
    }

    @Override
    public void onLoadingMore() {

        if(pageNumber<pageCount){

            pageNumber++;

             httpRequest();
        }else {
            listView.hideFooterView();
           Toast.makeText(getContext(),"亲,到底了~",Toast.LENGTH_SHORT).show();
        }
    }

    //加载数据(包括缓存数据和网络数据)
    private void loadData(){

        /**
         * 判断网络是否处于可用状态
         */
        if(NetWorkUtils.isNetworkAvailable(getActivity())){

            //加载网络数据
            loading.setVisibility(View.VISIBLE);
            httpRequest();
        }else {

            Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();
            loadFile.setVisibility(View.VISIBLE);
            //1获取缓存数据
            //如果获取得到缓存数据则加载本地数据
            loading.setVisibility(View.GONE);

            //2.如果缓存数据不存在则需要用户打开网络设置

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            alert.setMessage("网络不可用，是否打开网络设置");
            alert.setCancelable(false);
            alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //打开网络设置
                    startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MarathonEventModel eventInfo = list.get(position-1);
        //为单例成员赋值
        MarathonDataUtils.init().setEventId(eventInfo.getId()+"");
        Intent intent = new Intent(getActivity(), MarathonDetailsActivity.class);
        intent.putExtra("eventId",eventInfo.getId()+"");
        intent.putExtra("eventName",eventInfo.getName());
        startActivity(intent);
    }


    //加载网络数据

    private void httpRequest(){

        loadFile.setVisibility(View.GONE);
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


                    loadFile.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                  Toast.makeText(getActivity(),"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();
                  loadFile.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
             loading.setVisibility(View.GONE);
                //加载更多
            listView.hideFooterView();
            listView.hideHeaderView();
             marathonListViewAdapter.updateList(list);
            }
        });
    }
}
