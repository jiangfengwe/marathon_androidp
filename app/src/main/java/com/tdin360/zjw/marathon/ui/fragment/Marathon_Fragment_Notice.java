package com.tdin360.zjw.marathon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.activity.ShowHtmlActivity;
import com.tdin360.zjw.marathon.adapter.NoticeListViewAdapter;
import com.tdin360.zjw.marathon.model.NoticeModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 赛事公告
 * Created by Administrator on 2016/6/17.
 */
     @ContentView(R.layout.marathon_fragment_notice_center)
public class Marathon_Fragment_Notice extends BaseFragment implements RefreshListView.OnRefreshListener{
     @ViewInject(R.id.loading)
    private LinearLayout loadingView;
    @ViewInject(R.id.fail)
    private LinearLayout fail;
    @ViewInject(R.id.listView)
    private RefreshListView refreshListView;
    private List<NoticeModel> noticeModelList =new ArrayList<>();
    private NoticeListViewAdapter noticeListViewAdapter;
    private String typeName;
    private int totalPages;
    private int pageNumber=1;
    @ViewInject(R.id.tipNotData)
    private LinearLayout tipNotData;


    public static Marathon_Fragment_Notice newInstance(String typeName){
        Marathon_Fragment_Notice fragment_marathon_newsCenter=new Marathon_Fragment_Notice();
        Bundle bundle=new Bundle();

        bundle.putString("typeName",typeName);
        fragment_marathon_newsCenter.setArguments(bundle);
        return fragment_marathon_newsCenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle arguments = this.getArguments();
        this.typeName = arguments.getString("typeName");


        hotRecommend();
        httpGetList();
        /**
         * 加载失败点击重新加载
         */
        view.findViewById(R.id.loadFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                hotRecommend();
                httpGetList();

            }
        });
    }

//    设置新闻、公告列表
    private void hotRecommend() {


            this.noticeListViewAdapter  =new NoticeListViewAdapter(noticeModelList,getActivity());
            this.refreshListView.setAdapter(noticeListViewAdapter);
         refreshListView.setOnRefreshListener(this);
         refreshListView.setOnItemClickListener(new MyListener());
    }


    /**
     * 请求网络数据
     */
    private void httpGetList(){


        loadingView.setVisibility(View.VISIBLE);
        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARATHON_NewsOrNotice);
        requestParams.addQueryStringParameter("eventId", MarathonDataUtils.init().getEventId());
        requestParams.addQueryStringParameter("newsOrNoticeName",typeName);
        requestParams.addQueryStringParameter("PageNumber",pageNumber+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                try {
                    /*
                     * 获取新闻、公告数据列表
                      */
                    JSONObject json  = new JSONObject(result);
                    Log.d("------------>>>", "onSuccess: "+json);

                     totalPages = json.getInt("TotalPages");
                    JSONArray eventMessageList = json.getJSONArray("eventMessageList");
                    for(int i=0;i<eventMessageList.length();i++){
                        JSONObject o =  eventMessageList.getJSONObject(i);
                        int id = o.getInt("Id");
                        noticeModelList.add(new NoticeModel(id, o.getString("MessageName"),o.getString("CreateTimeStr"),HttpUrlUtils.EVENT_NEWS_OR_NOTICE_DETAILS+id));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadFail();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                //判断是否有数据
                if(noticeModelList.size()==0){

                    tipNotData.setVisibility(View.VISIBLE);
                }else {
                    tipNotData.setVisibility(View.GONE);
                }
                refreshListView.hideHeaderView();
                refreshListView.hideFooterView();
              loadingView.setVisibility(View.GONE);
              noticeListViewAdapter.updateListView(noticeModelList);



            }
        });

    }

    /**
     * 加载失败
     */
    private void loadFail(){
        loadingView.setVisibility(View.GONE);
        fail.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownPullRefresh() {
        //刷新
        noticeModelList.clear();

         pageNumber=1;

        httpGetList();
    }

    @Override
    public void onLoadingMore() {
         //下拉加载更多

            if (pageNumber < totalPages) {

                pageNumber++;
                httpGetList();
            } else {

                Toast.makeText(getActivity(), "亲，到底了~", Toast.LENGTH_SHORT).show();
                refreshListView.hideFooterView();
            }


    }

    /**
     * 新闻、公告列表点击事件
     */
    private class MyListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NoticeModel noticeModel = noticeModelList.get(position-1);
            Intent intent = new Intent(x.app(), ShowHtmlActivity.class);
            intent.putExtra("noticeModel", noticeModel);
            intent.putExtra("type","2");
            startActivity(intent);

        }
    }
}
