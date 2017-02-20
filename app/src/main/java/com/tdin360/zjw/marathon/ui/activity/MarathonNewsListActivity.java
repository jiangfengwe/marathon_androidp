package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.NewsListViewAdapter;
import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事新闻
 */
public class MarathonNewsListActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private LinearLayout loadingView;
    private LinearLayout fail;
    private RefreshListView refreshListView;
    private List<NewsModel> newsModelList=new ArrayList<>();
    private NewsListViewAdapter newsListViewAdapter;
    private int pageNumber=1;
    private int totalPages;
    private LinearLayout tipNotData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("赛事新闻");
        showBackButton();
        showShareButton(null);
        initView();

    }

    private void initView() {

        this.loadingView = (LinearLayout) this.findViewById(R.id.loading);
        this.fail = (LinearLayout) this.findViewById(R.id.fail);
        this.refreshListView  = (RefreshListView) this.findViewById(R.id.listView);
        this.refreshListView.setOnRefreshListener(this);
        this.refreshListView.setOnItemClickListener(new MyListener());
        this.tipNotData = (LinearLayout) this.findViewById(R.id.tipNotData);
        this.newsListViewAdapter  =new NewsListViewAdapter(newsModelList,this);
        this.refreshListView.setAdapter(this.newsListViewAdapter);
         onDownPullRefresh();



    }

    @Override
    public int getLayout() {
        return R.layout.activity_marathon_news;
    }


    /**
     * 请求网络数据
     */
    private void httpGetList(){

        loadingView.setVisibility(View.VISIBLE);
        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARATHON_NewsOrNotice);
        requestParams.addQueryStringParameter("eventId", MarathonDataUtils.init().getEventId());
        requestParams.addQueryStringParameter("newsOrNoticeName","赛事新闻");
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
                if(newsModelList.size()>0){

                    tipNotData.setVisibility(View.GONE);
                     newsListViewAdapter.notifyDataSetChanged();
                }else {
                    tipNotData.setVisibility(View.VISIBLE);
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


            Toast.makeText(this, "亲，到底了~", Toast.LENGTH_SHORT).show();
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
