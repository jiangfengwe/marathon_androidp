package com.tdin360.zjw.marathon.ui.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.NewsListViewAdapter;
import com.tdin360.zjw.marathon.model.NewsModel;
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

/**
 * 赛事新闻
 * @author zhangzhijun
 */
public class MarathonNewsListActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private LinearLayout loading;
    private TextView loadFail;
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
        initView();

    }

    private void initView() {

        this.loading = (LinearLayout) this.findViewById(R.id.loading);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.refreshListView  = (RefreshListView) this.findViewById(R.id.listView);
        this.refreshListView.setOnRefreshListener(this);
        this.refreshListView.setOnItemClickListener(new MyListener());
        this.tipNotData = (LinearLayout) this.findViewById(R.id.tipNotData);
        this.newsListViewAdapter  =new NewsListViewAdapter(newsModelList,this);
        this.refreshListView.setAdapter(this.newsListViewAdapter);

         //加载数据
        loadData();


    }
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {

        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
            httpRequest();
        } else {

            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据
            loading.setVisibility(View.GONE);

            //如果缓存数据不存在则需要用户打开网络设置

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("网络不可用，是否打开网络设置");
            alert.setCancelable(false);
            alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //打开网络设置

                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

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
    public int getLayout() {
        return R.layout.activity_marathon_news;
    }


    /**
     * 请求网络数据
     *
     * 获取新闻数据列表
     */
    private void httpRequest(){

        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARATHON_NewsOrNotice);
        requestParams.addQueryStringParameter("eventId","1");
        requestParams.addQueryStringParameter("newsOrNoticeName","赛事新闻");
        requestParams.addQueryStringParameter("PageNumber",pageNumber+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                try {

                    JSONObject json  = new JSONObject(result);


                    totalPages = json.getInt("TotalPages");
                    JSONArray eventMessageList = json.getJSONArray("eventMessageList");

                    for(int i=0;i<eventMessageList.length();i++){
                        JSONObject o = (JSONObject) eventMessageList.get(i);


                        int id = o.getInt("Id");

                        newsModelList.add(new NewsModel(id, o.getString("MessageName"), o.getString("MessagePicture"),HttpUrlUtils.EVENT_NEWS_OR_NOTICE_DETAILS+id, o.getString("CreateTimeStr")));

                    }
                    //加载成功隐藏
                    loadFail.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if(ex instanceof HttpException){

                    Toast.makeText(MarathonNewsListActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();
                }
                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


                //判断是否有数据
                if(newsModelList.size()>0){

                    tipNotData.setVisibility(View.GONE);
                }else {
                    tipNotData.setVisibility(View.VISIBLE);
                }

                loading.setVisibility(View.GONE);
                refreshListView.hideHeaderView();
                refreshListView.hideFooterView();
                newsListViewAdapter.updateListView(newsModelList);

            }
        });

    }

    @Override
    public void onDownPullRefresh() {
        //刷新
        newsModelList.clear();
        pageNumber=1;
        httpRequest();

    }

    @Override
    public void onLoadingMore() {
        //下拉加载更多
        if(pageNumber<totalPages){

            pageNumber++;
            httpRequest();
        }else {


            Toast.makeText(this, "亲，到底了~", Toast.LENGTH_SHORT).show();
            refreshListView.hideFooterView();
        }
    }

    /**
     * 新闻列表点击事件
     */
    private class MyListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NewsModel newsModel = newsModelList.get(position-1);
            Intent intent = new Intent(x.app(), ShowHtmlActivity.class);
            intent.putExtra("title","赛事新闻");
            intent.putExtra("url",newsModel.getDetailUrl());
            startActivity(intent);

        }
    }
}
