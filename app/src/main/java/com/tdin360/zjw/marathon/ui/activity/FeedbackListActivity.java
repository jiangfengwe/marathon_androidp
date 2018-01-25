package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.GridImageAdapter;
import com.tdin360.zjw.marathon.model.FeedbackBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 *  意见反馈列表
 *
 */
public class FeedbackListActivity extends BaseActivity  implements View.OnClickListener{
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;
    @ViewInject(R.id.navRightItemTitle)
    private TextView btnFeedback;


    @ViewInject(R.id.layout_feedback_add)
    private LinearLayout layoutAdd;
   /* @ViewInject(R.id.btn_feedback_sure)
    private Button btnFeedback;*/
    @ViewInject(R.id.et_feedback)
    private EditText etFeedback;
    @ViewInject(R.id.rv_feedback)
    private RecyclerView rvFeedback;
    private GridImageAdapter adapterPic;
    private List<LocalMedia> localMedias=new ArrayList<>();
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
        initRecyclerView();

    }
    private void initRecyclerView() {
        adapterPic=new GridImageAdapter(getContext(),onAddPicClickListener);
        adapterPic.setList(localMedias);
        rvFeedback.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvFeedback.setAdapter(adapterPic);
    }

    private void initView() {
        layoutAdd.setOnClickListener(this);
        //btnFeedback.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.feedback_title);
        btnFeedback.setText("提交");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);
        btnFeedback.setOnClickListener(this);

    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener=new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            initPic();
        }
    };
    private void initPic() {
        PictureSelector.create(FeedbackListActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo()// 是否可预览视频 true or false
                //.enablePreviewAudio() // 是否可播放音频 true or false
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressGrade(Luban.THIRD_GEAR)
                .glideOverride(130,130)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                //.cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                // .compressMaxKB(Luban.CUSTOM_GEAR)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                // .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                // .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                // .rotateEnabled() // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                // .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
                // .recordVideoSecond()//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    localMedias= PictureSelector.obtainMultipleResult(data);
                    adapterPic.setList(localMedias);
                    adapterPic.notifyDataSetChanged();
                    break;
            }
        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_list_feedback;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navRightItemTitle:
                //反馈意见提交
                //ToastUtils.show(getApplicationContext(),"反馈意见提交");
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initData();
                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                break;
        }


    }
    private void clearPic() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(FeedbackListActivity.this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(FeedbackListActivity.this);
                } else {
                    Toast.makeText(FeedbackListActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void initData() {
        LoginUserInfoBean.UserBean loginInfo =
                SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId() + "";
        String feedbackContent = etFeedback.getText().toString().trim();
            if(TextUtils.isEmpty(feedbackContent)&&localMedias.size()==0){
                ToastUtils.showCenter(getApplicationContext(),"反馈内容和图片不能同时为空！");
            }else{
                initSure(customerId, feedbackContent);
                return;
            }
            if(localMedias.size()==0&&TextUtils.isEmpty(feedbackContent)){
                ToastUtils.showCenter(getApplicationContext(),"反馈内容和图片不能同时为空！");
            }else{
                initSure(customerId, feedbackContent);
                return;
            }
    }

    private void initSure(String customerId, String feedbackContent) {
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
       /* layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        RequestParams params=new RequestParams(HttpUrlUtils.FEEDBACK);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("feedbackContent",feedbackContent);
        params.addBodyParameter("customerId",customerId);
        for(int i=0;i<localMedias.size();i++ ){
            params.addBodyParameter("file"+i,new File(localMedias.get(i).getCompressPath()),"image/jpeg",i+".jpg");
        }
        params.setConnectTimeout(5000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("feedback", "onSuccess: "+result);
                Gson gson=new Gson();
                FeedbackBean feedbackBean = gson.fromJson(result, FeedbackBean.class);
                boolean state = feedbackBean.isState();
                if(state){
                    ToastUtils.showCenter(getApplicationContext(),feedbackBean.getMessage());
                    clearPic();
                    finish();
                }else{
                    ToastUtils.showCenter(getApplicationContext(),feedbackBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // mErrorView.show(rvFeedback,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(getBaseContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapterPic.notifyDataSetChanged();
                //layoutLoading.setVisibility(View.GONE);
                hud.dismiss();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 10004:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    initPic();
                    //用户授权成功
                }else {
                    //用户没有授权
                    AlertDialog.Builder alert = new AlertDialog.Builder(FeedbackListActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("你需要设置权限才能使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(FeedbackListActivity.this);
                        }
                    });
                    alert.show();

                }
                break;
            default:
                break;
        }
    }
    /**
     * 设置权限界面
     * @param context
     */
    public  void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        context.startActivity(localIntent);
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
