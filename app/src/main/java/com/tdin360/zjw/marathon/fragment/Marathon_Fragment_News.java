package com.tdin360.zjw.marathon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.activity.NewsAndNoticeDetailsActivity;
import com.tdin360.zjw.marathon.adapter.NewsListViewAdapter;
import com.tdin360.zjw.marathon.model.NewsItem;
import com.tdin360.zjw.marathon.model.utils.HttpUrlUtils;
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
 * 新闻中心
 * Created by Administrator on 2016/6/17.
 */
     @ContentView(R.layout.marathon_fragment_news_or_notice_center)
public class Marathon_Fragment_News extends BaseFragment implements RefreshListView.OnRefreshListener{
     @ViewInject(R.id.loading)
    private LinearLayout loadingView;
    @ViewInject(R.id.fail)
    private LinearLayout fail;
    @ViewInject(R.id.listView)
    private RefreshListView refreshListView;

    private List<NewsItem>newsItemList;
    private NewsListViewAdapter newsListViewAdapter;
    private String typeName;

    public static Marathon_Fragment_News newInstance(String typeName){
        Marathon_Fragment_News fragment_marathon_newsCenter=new Marathon_Fragment_News();
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

            this.newsItemList=new ArrayList<>();


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



            this.newsListViewAdapter  =new NewsListViewAdapter(null,getActivity());
            refreshListView.setAdapter(newsListViewAdapter);

         refreshListView.setOnRefreshListener(this);
         refreshListView.setOnItemClickListener(new MyListener());
    }


    /**
     * 请求网络数据
     */
    private void httpGetList(){
        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARATHON_ALL);
        requestParams.addQueryStringParameter("categoryName",typeName);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                try {
                    /*
                     * 获取新闻、公告数据列表
                      */
                    JSONArray array  = new JSONArray(result);

                    for(int i=0;i<array.length();i++){
                        JSONObject o = (JSONObject) array.get(i);


                            newsItemList.add(new NewsItem(o.getInt("Id"), o.getString("Title"), o.getString("PicUrl"), o.getString("Url"), o.getString("CreateTime")));

                    }
                    loadingView.setVisibility(View.GONE);
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

                    newsListViewAdapter.updateListView(newsItemList);

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
        newsItemList.clear();
        httpGetList();


            newsListViewAdapter.notifyDataSetChanged();


        refreshListView.hideHeaderView();
    }

    @Override
    public void onLoadingMore() {
         //下拉加载更多
        refreshListView.hideFooterView();
    }

    /**
     * 新闻、公告列表点击事件
     */
    private class MyListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NewsItem newsItem = newsItemList.get(position-1);
            Intent intent = new Intent(x.app(), NewsAndNoticeDetailsActivity.class);
             intent.putExtra("url",newsItem.getDetailUrl());
             intent.putExtra("type","新闻详情");
             startActivity(intent);

        }
    }
}
