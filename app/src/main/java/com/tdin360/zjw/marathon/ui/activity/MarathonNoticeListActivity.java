package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CommonAdapter;
import com.tdin360.zjw.marathon.model.NoticeModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;

import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeServiceImpl;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事公告
 * @author zhangzhijun
 */
public class MarathonNoticeListActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener {


    @ViewInject(R.id.listView)
    private ListView refreshListView;
    private List<NoticeModel> noticeModelList=new ArrayList<>();
    private NoticeAdapter adapter;
    private int pageNumber=1;
    private int totalPages;
    @ViewInject(R.id.pull_Layout)
    private PullToRefreshLayout pullToRefreshLayout;
    private NoticeServiceImpl service;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.service = new NoticeServiceImpl(getApplicationContext());
        setToolBarTitle("赛事公告");
        showBackButton();
        initView();

    }

    private void initView() {


        this.pullToRefreshLayout.setOnRefreshListener(this);
        this.refreshListView.setOnItemClickListener(new MyListener());
        this.adapter = new NoticeAdapter(this,noticeModelList,R.layout.notice_list_item);
        this.refreshListView.setAdapter(this.adapter);


        /**
         * 加载失败点击重试
         */
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:

                        loadData();
                        break;

                }
            }
        });

          loadData();



    }

    //加载数据(包括缓存数据和网络数据)
    private void loadData() {

        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
           pullToRefreshLayout.autoRefresh();
        } else {


            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据

            noticeModelList = service.getAllNotice(MarathonDataUtils.init().getEventId());
             adapter.update(noticeModelList);

            if(noticeModelList.size()==0) {

                mErrorView.show(pullToRefreshLayout,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
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
    }
    @Override
    public int getLayout() {
        return R.layout.activity_marathon_notice;
    }


    /**
     * 数据适配器
     */
    class NoticeAdapter extends CommonAdapter<NoticeModel> {


        public NoticeAdapter(Context context, List<NoticeModel> list, @LayoutRes int layoutId) {
            super(context, list, layoutId);
        }

        @Override
       protected void onBind(com.tdin360.zjw.marathon.adapter.ViewHolder holder, NoticeModel model) {

            holder.setText(R.id.title,model.getTitle()).setText(R.id.time,model.getTime());


        }
    }



    /**
     * 请求网络数据
     */
    private void httpRequest(final boolean isRefresh){


        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARATHON_NewsOrNotice);
        requestParams.addQueryStringParameter("eventId", MarathonDataUtils.init().getEventId());
        requestParams.addQueryStringParameter("newsOrNoticeName","赛事公告");
        requestParams.addQueryStringParameter("PageNumber",pageNumber+"");
        requestParams.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {




            @Override
            public void onSuccess(String result) {

                try {
                    /*
                     * 获取公告数据列表
                      */

                    service.deleteNotice(MarathonDataUtils.init().getEventId());
                    if(isRefresh){

                        noticeModelList.clear();
                    }
                    JSONObject json  = new JSONObject(result);


                    totalPages = json.getInt("TotalPages");
                    JSONArray eventMessageList = json.getJSONArray("eventMessageList");

                    for(int i=0;i<eventMessageList.length();i++){
                        JSONObject o = (JSONObject) eventMessageList.get(i);

                        int id = o.getInt("Id");
                        NoticeModel model = new NoticeModel(id, o.getString("MessageName"), o.getString("CreateTimeStr"), HttpUrlUtils.EVENT_NEWS_OR_NOTICE_DETAILS + id);
                        noticeModelList.add(model);

                        service.addNotice(model);
                    }


                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }

                    if(noticeModelList.size()<=0){

                        mErrorView.show(pullToRefreshLayout,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(pullToRefreshLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                    }

                    mErrorView.show(pullToRefreshLayout,"服务器数据异常",ErrorView.ViewShowMode.ERROR);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if (isRefresh){

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }else {
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }

                if(noticeModelList.size()<=0) {
                    mErrorView.show(pullToRefreshLayout, "加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                }
                ToastUtils.show(getBaseContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


                adapter.notifyDataSetChanged();

            }
        });

    }



    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


        pageNumber=1;
        httpRequest(true);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        //上拉加载更多
        if(pageNumber==totalPages){

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);

        }else if(pageNumber<totalPages){
            pageNumber++;
            httpRequest(false);

        }else {

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);
        }
    }

    /**
     * 公告列表点击事件
     */
    private class MyListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(noticeModelList.size()>0) {
                NoticeModel noticeModel = (NoticeModel) parent.getAdapter().getItem(position);
                Intent intent = new Intent(x.app(), ShowHtmlActivity.class);
                intent.putExtra("shareTitle",noticeModel.getTitle());
                intent.putExtra("shareImageUrl",MarathonDataUtils.init().getEventImageUrl());
                intent.putExtra("title", "赛事公告");
                intent.putExtra("url", noticeModel.getUrl());
                startActivity(intent);

            }
        }
    }
}
