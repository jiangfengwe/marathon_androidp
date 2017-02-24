package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.NoticeListViewAdapter;
import com.tdin360.zjw.marathon.model.NoticeModel;
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
 * 赛事公告
 * @author zhangzhijun
 */
public class MarathonNoticeListActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private LinearLayout loading;
    private TextView loadFail;
    private RefreshListView refreshListView;
    private List<NoticeModel> noticeModelList=new ArrayList<>();
    private NoticeListViewAdapter noticeListViewAdapter;
    private int pageNumber=1;
    private int totalPages;
    private LinearLayout tipNotData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("赛事公告");
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
        this.noticeListViewAdapter  =new NoticeListViewAdapter(noticeModelList,this);
        this.refreshListView.setAdapter(this.noticeListViewAdapter);

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
        return R.layout.activity_marathon_notice;
    }


    /**
     * 请求网络数据
     */
    private void httpRequest(){


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
                        noticeModelList.add(new NoticeModel(id, o.getString("MessageName"),o.getString("CreateTimeStr"),HttpUrlUtils.EVENT_NEWS_OR_NOTICE_DETAILS+id));

                    }

                    loadFail.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


              Toast.makeText(MarathonNoticeListActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();

                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                //判断是否有数据
                if(noticeModelList.size()>0){

                    tipNotData.setVisibility(View.GONE);
                    noticeListViewAdapter.updateListView(noticeModelList);
                }else {
                    tipNotData.setVisibility(View.VISIBLE);
                }

                loading.setVisibility(View.GONE);
                refreshListView.hideHeaderView();
                refreshListView.hideFooterView();


            }
        });

    }


    @Override
    public void onDownPullRefresh() {
        //刷新
        noticeModelList.clear();
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
     * 公告列表点击事件
     */
    private class MyListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NoticeModel noticeModel = noticeModelList.get(position-1);
            Intent intent = new Intent(x.app(), ShowHtmlActivity.class);
            intent.putExtra("title","赛事公告");
            intent.putExtra("url",noticeModel.getUrl());
            startActivity(intent);

        }
    }
}
