package com.tdin360.zjw.marathon.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.ui.activity.NewsAndNoticeDetailsActivity;
import com.tdin360.zjw.marathon.adapter.NewsListViewAdapter;
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
 * 新闻中心
 * Created by Administrator on 2016/6/17.
 */
     @ContentView(R.layout.marathon_fragment_news_center)
public class Marathon_Fragment_News extends BaseFragment implements RefreshListView.OnRefreshListener{
     @ViewInject(R.id.loading)
    private LinearLayout loadingView;
    @ViewInject(R.id.fail)
    private LinearLayout fail;
    @ViewInject(R.id.listView)
    private RefreshListView refreshListView;

    private List<NewsModel> newsModelList;
    private NewsListViewAdapter newsListViewAdapter;
    private String typeName;
    private int pageNumber=1;
    private int totalPages;
    @ViewInject(R.id.tipNotData)
    private LinearLayout tipNotData;

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


            this.newsModelList =new ArrayList<>();


        hotRecommend();
        httpGetList();
        /**
         * 加载失败点击重新加载
         */
        view.findViewById(R.id.loadFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpGetList();

            }
        });
    }

//    设置新闻、公告列表
    private void hotRecommend() {



            this.newsListViewAdapter  =new NewsListViewAdapter(newsModelList,getActivity());
            refreshListView.setAdapter(newsListViewAdapter);

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


                      totalPages = json.getInt("TotalPages");
                    JSONArray eventMessageList = json.getJSONArray("eventMessageList");

                    for(int i=0;i<eventMessageList.length();i++){
                        JSONObject o = (JSONObject) eventMessageList.get(i);

                        int id = o.getInt("Id");
                        newsModelList.add(new NewsModel(id, o.getString("MessageName"), o.getString("MessagePicture"),HttpUrlUtils.EVENT_NEWS_OR_NOTICE_DETAILS+id, o.getString("CreateTimeStr")));

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
                if(newsModelList.size()==0){

                    tipNotData.setVisibility(View.VISIBLE);
                }else {
                    tipNotData.setVisibility(View.GONE);
                }

                   loadingView.setVisibility(View.GONE);
                  refreshListView.hideHeaderView();
                   refreshListView.hideFooterView();
                    newsListViewAdapter.updateListView(newsModelList);

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
        newsModelList.clear();
        pageNumber=1;
        httpGetList();

    }

    @Override
    public void onLoadingMore() {
         //下拉加载更多
         if(pageNumber<totalPages){

             pageNumber++;
             httpGetList();
         }else {


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
            NewsModel newsModel = newsModelList.get(position-1);
            Intent intent = new Intent(x.app(), NewsAndNoticeDetailsActivity.class);
             intent.putExtra("newsModel", newsModel);
             intent.putExtra("type","1");
             startActivity(intent);

        }
    }
}
