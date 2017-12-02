package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;


import org.xutils.view.annotation.ViewInject;

/**
 *  意见反馈列表
 *
 */
public class FeedbackListActivity extends BaseActivity  implements View.OnClickListener{
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;


    @ViewInject(R.id.layout_feedback_add)
    private LinearLayout layoutAdd;
    @ViewInject(R.id.btn_feedback_sure)
    private Button btnFeedback;

//implements PullToRefreshLayout.OnRefreshListener
   /* @ViewInject(R.id.listView)
    private ListView listView;
    private List<FeedBackModel>list = new ArrayList<>();
    private int totalPages;
    private MyAdapter myAdapter;
    private int pageNumber=1;
    public static String ACTION="reLoad";
    @ViewInject(R.id.pull_Layout)
    private PullToRefreshLayout pullToRefreshLayout;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*showBackButton();
        setToolBarTitle("意见反馈");
        initView();
        register();*/
        initToolbar();
        initView();

    }

    private void initView() {
        layoutAdd.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.feedback_title);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }


   /* private void initView() {

        this.pullToRefreshLayout.setOnRefreshListener(this);
        this.myAdapter = new MyAdapter(this,list,R.layout.feedback_list_item);
        this.listView.setAdapter(myAdapter);


        *//**
         * 加载失败点击重试
         *//*
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:

                        httpRequest(true);
                        break;

                }
            }
        });

        pullToRefreshLayout.autoRefresh();
    }*/




//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        menu.setHeaderTitle("操作");
//        menu.add(0,1,0,"删除选项");
//        menu.add(0,2,0,"全部删除");
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//
//            case 1:
//                break;
//            case 2:
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }

    @Override
    public int getLayout() {
        return R.layout.activity_list_feedback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_feedback_add:
                //添加图片
                ToastUtils.show(getApplicationContext(),"添加图片");
                break;
            case R.id.btn_feedback_sure:
                //反馈意见提交
                ToastUtils.show(getApplicationContext(),"反馈意见提交");
                break;
        }


    }


    //添加新的意见反馈
  /*  public void add(View view) {

        Intent intent  = new Intent(FeedbackListActivity.this,AddFeedbackActivity.class);
       startActivity(intent);
    }


    *//**
     * 获取网络数据
     *//*
    private void httpRequest(final boolean isRefresh){


        RequestParams params  =new RequestParams(HttpUrlUtils.FEED_LIST);
        params.addQueryStringParameter("phone", SharedPreferencesManager.getLoginInfo(FeedbackListActivity.this).getName());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("PageNumber",pageNumber+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {

                    if(isRefresh){

                        list.clear();
                    }
                    JSONObject json = new JSONObject(result);

                    totalPages = json.getInt("TotalPages");

                    JSONArray messageList = json.getJSONArray("FeedbackMessageList");

                    for(int i=0;i<messageList.length();i++){

                        JSONObject obj = messageList.getJSONObject(i);
                        String content = obj.getString("FeedbackContent");
                        String createTimeStr = obj.getString("CreateTimeStr");
                        String replyContent = obj.getString("ReplyContent");
                        String timeStr = obj.getString("FeedbackTimeStr");
                        list.add(new FeedBackModel(createTimeStr,content,timeStr,replyContent));
                    }

                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }

                    if(list.size()<=0){

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

                if(list.size()<=0) {
                    mErrorView.show(pullToRefreshLayout, "加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                }
                ToastUtils.show(getBaseContext(),"网络不给力,连接服务器异常!");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                myAdapter.update(list);

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
    *//**
     * 展示反馈列表以及回复列表
     *//*
    private class MyAdapter extends CommonAdapter<FeedBackModel>{


        public MyAdapter(Context context, List<FeedBackModel> list, @LayoutRes int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        protected void onBind(ViewHolder holder,  FeedBackModel  model) {

             int width=30;
            ImageView imageView =  holder.getViewById(R.id.imageView);
            LoginModel info = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(width), DensityUtil.dip2px(width))//图片大小
                    //.setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setRadius(width)
                    .setLoadingDrawableId(R.drawable.signup_photo)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.signup_photo)//加载失败后默认显示图片
                    .build();
            x.image().bind(imageView,info.getImageUrl(),imageOptions);

            holder.setText(R.id.time,model.getTime());
            holder.setText(R.id.content,model.getContent());


            //如果答复就显示答复内容，否则不显示
            if(!model.getFromContent().equals("null")&&!model.equals("")){

                holder.setText(R.id.answerContent,model.getFromContent());
                holder.setText(R.id.answerTime,model.getFromTime());
                holder.getViewById(R.id.isAnswer).setVisibility(View.VISIBLE);

            }

        }
    }


    *//**
     * 用广播接收更新通知
     *//*
    private MyBroadcastReceiver receiver;
    private void register(){this.receiver = new MyBroadcastReceiver();

        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver,filter);

    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            list.clear();
            httpRequest(true);
        }
    }*/

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }*/
}
