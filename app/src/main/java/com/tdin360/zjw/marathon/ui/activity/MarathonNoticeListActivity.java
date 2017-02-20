package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
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
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事公告
 * @author zhangzhijun
 */
public class MarathonNoticeListActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private LinearLayout loadingView;
    private LinearLayout fail;
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
        this.noticeListViewAdapter  =new NoticeListViewAdapter(noticeModelList,this);
        this.refreshListView.setAdapter(this.noticeListViewAdapter);
         onDownPullRefresh();



    }

    @Override
    public int getLayout() {
        return R.layout.activity_marathon_notice;
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
                if(noticeModelList.size()>0){

                    tipNotData.setVisibility(View.GONE);
                    noticeListViewAdapter.updateListView(noticeModelList);
                }else {
                    tipNotData.setVisibility(View.VISIBLE);
                }

                loadingView.setVisibility(View.GONE);
                refreshListView.hideHeaderView();
                refreshListView.hideFooterView();


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
        if(pageNumber<totalPages){

            pageNumber++;
            httpGetList();
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
            Intent intent = new Intent(x.app(), NewsAndNoticeDetailsActivity.class);
            intent.putExtra("noticeModel", noticeModel);
            intent.putExtra("type","1");
            startActivity(intent);

        }
    }
}
