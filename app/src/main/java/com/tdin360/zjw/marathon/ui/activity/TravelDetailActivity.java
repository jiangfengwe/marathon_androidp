package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.TravelDetailBean;
import com.tdin360.zjw.marathon.model.TravelPictureBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游详情
 */

public class TravelDetailActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.iv_travel_detail_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_travel_detail_share)
    private ImageView ivShare;
    @ViewInject(R.id.layout_travel_detail_head)
    private RelativeLayout layoutPic;
    @ViewInject(R.id.iv_travel_detail_pic)
    private ImageView ivPic;
    @ViewInject(R.id.tv_travel_detail_count)
    private TextView tvCount;
    @ViewInject(R.id.tv_travel_detail_name)
    private TextView tvName;
    @ViewInject(R.id.tv_travel_detail_price)
    private TextView tvPrice;
    @ViewInject(R.id.tv_travel_detail_consult)
    private TextView tvConsult;
    @ViewInject(R.id.tv_travel_detail_order)
    private TextView tvOrder;
    @ViewInject(R.id.layout_travel_detail)
    private LinearLayout layoutNull;
    @ViewInject(R.id.sl_travel)
    private ScrollView scrollView;


    private ShareAction action;
    @ViewInject(R.id.webView_travel)
    private WebView webView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    @ViewInject(R.id.rv_hotel_detail_foot)
    private RecyclerView rvDetail;

    private List<String> list=new ArrayList<>();

    @ViewInject(R.id.iv_comment_head_pic)
    private ImageView ivHeadPic;
    @ViewInject(R.id.tv_comment_name)
    private TextView tvCommentName;
    @ViewInject(R.id.tv_comment_time)
    private TextView tvCommentTime;
    @ViewInject(R.id.tv_comment_content)
    private TextView tvComment;
    private RecyclerViewBaseAdapter adapter;
    @ViewInject(R.id.tv_check_more_comment)
    private TextView tvMore;
    @ViewInject(R.id.tv_travel_comment_count)
    private TextView tvCommentCount;
    @ViewInject(R.id.tv_travel_comment_level)
    private TextView tvLevel;
    @ViewInject(R.id.layout_hotel_detail)
    private LinearLayout layout;

    public static TravelDetailActivity instance;
    public TravelDetailActivity() {
        instance=this;
        // Required empty public constructor
    }

    public void finishActivity(){
        finish();
    }

    private TravelDetailBean.ModelBean.BJTravelModelBean bjTravelModel=new TravelDetailBean.ModelBean.BJTravelModelBean();
    private List<TravelPictureBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel=new ArrayList<>();
    private List<TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean.BJTravelEvaluatePictureListModelBean>
            bjTravelEvaluatePictureListModel=new ArrayList<>();
    ImageOptions imageOptions,imageOptionsCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                //.setCrop(true).setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.event_bg) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.event_bg) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        imageOptionsCircle= new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        initNet();
        initView();
        initInfo();
        initPicture();
        initFoor();
        //http://www.baijar.com/EventAppApi/TravelDetailMessageView?appKey=BJYDAppV-2&travelId=1

    }

    private void initFoor() {
        for (int i = 0; i <7 ; i++) {
            list.add(""+i);
        }
        if(bjTravelEvaluatePictureListModel.size()<=0){
            layout.setVisibility(View.GONE);
           return;
        }else{
            layout.setVisibility(View.VISIBLE);
            final ArrayList<String> image= new ArrayList<>();
            for(int i=0;i<bjTravelEvaluatePictureListModel.size();i++){
                image.add(bjTravelEvaluatePictureListModel.get(i).getPictureUrl());
            }
            adapter=new RecyclerViewBaseAdapter<TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean.BJTravelEvaluatePictureListModelBean>
                    (getApplicationContext(),bjTravelEvaluatePictureListModel,R.layout.item_hotel_detail_pic) {
                @Override
                protected void onBindNormalViewHolder(NormalViewHolder holder, TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean.BJTravelEvaluatePictureListModelBean model) {
                    ImageView imageView = (ImageView) holder.getViewById(R.id.iv_comment_pic);
                    x.image().bind(imageView,model.getThumbPictureUrl(),imageOptions);
                }
            };

            adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ImageView imageView1=new ImageView(TravelDetailActivity.this);
                    MNImageBrowser.showImageBrowser(TravelDetailActivity.this,imageView1,position, image);
                }
            });
            rvDetail.setAdapter(adapter);
            rvDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        }

        //查看更多评价
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventId = SingleClass.getInstance().getEventId();
                String travelId = SingleClass.getInstance().getTravelId();
               // String travelId = getIntent().getStringExtra("travelId");
                Intent intent=new Intent(TravelDetailActivity.this,TravelMoreCommentActivity.class);
                intent.putExtra("eventId",eventId);
                intent.putExtra("travelId",travelId);
                startActivity(intent);

            }
        });
    }

    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initData();
                        break;
                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
           initData();
        }else {
            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
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
    }

    private void initPicture() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        String eventId = SingleClass.getInstance().getEventId();
        //String travelId = getIntent().getStringExtra("travelId");
        String travelId = SingleClass.getInstance().getTravelId();
        RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL_DETAIL_PICTURE);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("eventId",eventId);
        params.addBodyParameter("travelId",travelId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("travelpicture", "onSuccess: "+result);
                Gson gson=new Gson();
                TravelPictureBean travelPictureBean = gson.fromJson(result, TravelPictureBean.class);
                boolean state = travelPictureBean.isState();
                if(state){
                    TravelPictureBean.ModelBean model = travelPictureBean.getModel();
                    bjTravelPictureListModel= model.getBJTravelPictureListModel();
                    SingleClass.getInstance().setBjTravelPictureListModel(bjTravelPictureListModel);
                    tvCount.setText(bjTravelPictureListModel.size()+"");
                }else{
                    ToastUtils.showCenter(getApplicationContext(),travelPictureBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(webView,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(TravelDetailActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });
    }

    private void initInfo() {
        webView.getSettings().setUseWideViewPort(true);//内容适配，设置自适应任意大小的pc网页
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.getSettings().setDomStorageEnabled(true);
       // String travelId = getIntent().getStringExtra("travelId");
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient());
        String travelId = SingleClass.getInstance().getTravelId();
       // String url = HttpUrlUtils.TRAVEL_DETAIL_INFO + "?" + "appKey" + "=" + "BJYDAppV-2" + "&" + "travelId" + travelId;
        //String url="http://www.baijar.com/EventAppApi/TravelDetailMessageView?appKey=BJYDAppV-2&travelId="+travelId;
        String url= HttpUrlUtils.BASE+"TravelDetailMessageView?appKey=BJYDAppV-2&travelId="+travelId;
        webView.loadUrl(url);
        webView.addJavascriptInterface(TravelDetailActivity.this,"android");
    }

    private void initData() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        String eventId = SingleClass.getInstance().getEventId();
       // String travelId = getIntent().getStringExtra("travelId");
        String travelId = SingleClass.getInstance().getTravelId();
        RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL_DETAIL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("eventId",eventId);
        params.addBodyParameter("travelId",travelId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("traveldetail", "onSuccess: "+result);
                Gson gson=new Gson();
                TravelDetailBean travelDetailBean = gson.fromJson(result, TravelDetailBean.class);
                boolean state = travelDetailBean.isState();
                if(state){
                    TravelDetailBean.ModelBean model = travelDetailBean.getModel();
                   bjTravelModel= model.getBJTravelModel();
                    List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean> apiTravelMonthDateList = model.getApiTravelMonthDateList();
                    //tvCommentCount.setText("一共"+model.getEvaluationCount()+"条评论");
                    SingleClass.getInstance().setApiTravelMonthDateList(apiTravelMonthDateList);
                    x.image().bind(ivPic,bjTravelModel.getPictureUrl(),imageOptions);
                    tvPrice.setText(bjTravelModel.getPrice()+"");
                    tvName.setText(bjTravelModel.getStartPlace()+"——"+bjTravelModel.getEndPlace());
                   // tvLevel.setText(bjTravelModel.getScoring()+"");
                    List<TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean> bjTravelEvaluateListModel = model.getBJTravelEvaluateListModel();
                    if(bjTravelEvaluateListModel.size()<=0){
                        layout.setVisibility(View.GONE);
                       return;
                    }else{
                        layout.setVisibility(View.VISIBLE);
                        TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean bjTravelEvaluateListModelBean = bjTravelEvaluateListModel.get(0);
                    TravelDetailBean.ModelBean.BJTravelEvaluateListModelBean.EvaluationUserModelBean evaluationUserModel
                            = bjTravelEvaluateListModelBean.getEvaluationUserModel();
                    String headImg = evaluationUserModel.getHeadImg();
                    String nickName = evaluationUserModel.getNickName();
                    String evaluateContent = bjTravelEvaluateListModelBean.getEvaluateContent();
                    String evaluateTimeStr = bjTravelEvaluateListModelBean.getEvaluateTimeStr();
                    bjTravelEvaluatePictureListModel= bjTravelEvaluateListModelBean.getBJTravelEvaluatePictureListModel();
                    //x.image().bind(ivHeadPic,headImg,imageOptionsCircle);
                    //tvCommentName.setText(nickName);
                    //tvCommentTime.setText(evaluateTimeStr);
                   // tvComment.setText(evaluateContent);
                    Log.d("apiTravelMonthDateList", "onSuccess: "+apiTravelMonthDateList.size());
                    }
                }else{
                    ToastUtils.showCenter(getApplicationContext(),travelDetailBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(webView,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                layoutNull.setBackgroundColor(Color.parseColor("#ff621a"));
                ToastUtils.show(TravelDetailActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //adapter.update(bjTravelEvaluatePictureListModel);
                layoutLoading.setVisibility(View.GONE);
               // hud.dismiss();

            }
        });

    }
    private void initView() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        layoutPic.setOnClickListener(this);
        tvConsult.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("onScrollChange", "onScrollChange: "+(scrollY-oldScrollY));
                if((scrollY-oldScrollY)>1){
                    layoutNull.setBackgroundColor(Color.parseColor("#ff621a"));
                }else{
                    layoutNull.setBackgroundColor(Color.parseColor("#00000000"));
                }
            }
        });
    }

    @JavascriptInterface
    public void toTravelMoreComment(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String eventId = SingleClass.getInstance().getEventId();
                String travelId = getIntent().getStringExtra("travelId");
                Intent intent=new Intent(TravelDetailActivity.this,TravelMoreCommentActivity.class);
                intent.putExtra("eventId",eventId);
                intent.putExtra("travelId",travelId);
                //intent.putExtra("webview","1");
                startActivity(intent);
            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_travel_detail;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_travel_detail_back:
                //返回
                finish();
                break;
            case R.id.iv_travel_detail_share:
                //分享
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                    shareApp();
                }else {
                    if(ContextCompat.checkSelfPermission(TravelDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10001);
                    }else {
                        shareApp();
                    }
                }
                break;
            case R.id.layout_travel_detail_head:
                //图片浏览
                intent=new Intent(TravelDetailActivity.this,PictureActivity.class);
                intent.putExtra("picture","travelPic");
                startActivity(intent);
                break;
            case R.id.tv_travel_detail_consult:
                //客服咨询
                tellDialog();
                break;
            case R.id.tv_travel_detail_order:
                //立即预定
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String customerId = loginInfo.getId();
                if(TextUtils.isEmpty(customerId)){
                    intent=new Intent(TravelDetailActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                intent=new Intent(TravelDetailActivity.this,TravelOrderActivity.class);
                    String travelId = bjTravelModel.getId() + "";
                    double price = bjTravelModel.getPrice();
                    intent.putExtra("travelId",travelId);
                    intent.putExtra("price",price);
                    startActivity(intent);
                }
                break;
        }

    }

    private void tellDialog() {
        android.support.v7.app.AlertDialog.Builder normalDialog =new android.support.v7.app.AlertDialog.Builder(TravelDetailActivity.this);
        normalDialog.setMessage("是否拨打"+bjTravelModel.getPhone1());
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + bjTravelModel.getPhone1());
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     *  分享给好友
     */
    private void shareApp(){

           /*使用友盟自带分享模版*/
        action = new ShareAction(TravelDetailActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                UMWeb umWeb = new UMWeb(getString(R.string.shareDownLoadUrl));
                umWeb.setTitle("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。");
                umWeb.setDescription("佰家运动");
                UMImage image = new UMImage(TravelDetailActivity.this,R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(TravelDetailActivity.this).withText("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。")
                        .setPlatform(share_media)
                        .setCallback(new MyUMShareListener())
                        .withMedia(umWeb)
                        .share();
            }


        });


        action.open();
    }
    /**
     * 自定义分享结果监听器
     */
    private class MyUMShareListener implements UMShareListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(TravelDetailActivity.this,"正在打开分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(TravelDetailActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(TravelDetailActivity.this, platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(TravelDetailActivity.this, platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(TravelDetailActivity.this,"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }
}
