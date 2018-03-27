package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OrderHotelBean;
import com.tdin360.zjw.marathon.model.OrderTravelBean;
import com.tdin360.zjw.marathon.model.OrderTravelCancelBean;
import com.tdin360.zjw.marathon.model.OrderTravelDetailBean;
import com.tdin360.zjw.marathon.model.OrderTravelDetailDecryptBean;
import com.tdin360.zjw.marathon.model.RefundHotelBean;
import com.tdin360.zjw.marathon.model.TravelOrderInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



/**
 * 我的订单中旅游订单详情
 */

public class TravelOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;


    @ViewInject(R.id.travel_order_pic)
    private ImageView ivPic;
    @ViewInject(R.id.travel_order_name)
    private  TextView tvName;
    @ViewInject(R.id.travel_order_count)
    private TextView tvCount;
    @ViewInject(R.id.travel_order_price)
    private TextView tvPrice;
    @ViewInject(R.id.travel_order_time)
    private TextView tvTime;
    @ViewInject(R.id.travel_order_phone)
    private TextView tvPhone;
    @ViewInject(R.id.travel_order_number)
    private TextView tvNumber;

    @ViewInject(R.id.tv_order_travel_cancel)
     private TextView tvCancel;
    @ViewInject(R.id.tv_order_travel_pay)
    private TextView tvPay;
    @ViewInject(R.id.layout_order_travel_detail)
    private LinearLayout layoutShow;
    @ViewInject(R.id.btn_travel_order_detail)
    private Button btn;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private OrderTravelDetailDecryptBean.ModelBean.BJTravelOrderModelBean bjTravelOrderModel=new OrderTravelDetailDecryptBean.ModelBean.BJTravelOrderModelBean();

    ImageOptions imageOptions;

    @ViewInject(R.id.order_travel_detail_webview)
    private WebView webView;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;

    private String stringLike;
    private int highPrice;
    private int index;
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()== EnumEventBus.TRAVELCOMMENT){
            initNet();
            initWeb();
            EnumEventBus cancelTravel = EnumEventBus.TRAVELCOMMENTORDER;
            EventBus.getDefault().post(new EventBusClass(cancelTravel));
        }

    }


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
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initToolbar();
        initNet();
        initWeb();
    }

    private void initWeb() {
        Intent intent=getIntent();
        String orderId = intent.getStringExtra("orderId");
        String  url = HttpUrlUtils.TRAVEL_DETAIL_WEBVIEW+"?appKey="+HttpUrlUtils.appKey+"&orderId="+orderId;
        Log.d("orderIdurl", "onCreate: "+url);
        webView.getSettings().setUseWideViewPort(true);//内容适配，设置自适应任意大小的pc网页
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setWebChromeClient(new MyWebChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }
    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            progressBar.setProgress(i);
            if(i==100){
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            // titleTv.setText(s);
        }
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {

        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {

        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

            return true;
        }


    }
    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            progressBar.setVisibility(View.GONE);
        }
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return  false;
        }
    }

    private void iniData() {
        String orderId = getIntent().getStringExtra("orderId");
        Log.d("payordereeId", "onClick: "+orderId);
        RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL_ORDER_DETAIL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("orderId",orderId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("travelDetail", "onSuccess: "+result);
                Gson gson=new Gson();
                OrderTravelDetailBean orderTravelDetailBean = gson.fromJson(result, OrderTravelDetailBean.class);
                boolean state = orderTravelDetailBean.isState();
                if(state){
                    String orderSecretMessage = orderTravelDetailBean.getOrderSecretMessage();
                    String decrypt = AES.decrypt(orderSecretMessage);
                    Log.d("travelDetailbean", "onSuccess: "+decrypt);
                    OrderTravelDetailDecryptBean orderTravelDetailDecryptBean = gson.fromJson(decrypt, OrderTravelDetailDecryptBean.class);
                    OrderTravelDetailDecryptBean.ModelBean model = orderTravelDetailDecryptBean.getModel();
                    bjTravelOrderModel= model.getBJTravelOrderModel();
                    initView();
                }else{
                    ToastUtils.showCenter(getApplicationContext(),orderTravelDetailBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //ToastUtils.show(TravelOrderDetailActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                layoutLoading.setVisibility(View.GONE);
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
                        iniData();
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(getApplicationContext())){
            //加载网络数据
            iniData();
        }else {

            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
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
    //intent.putExtra("payOrder","travelorder");
    private void initView() {
        x.image().bind(ivPic,bjTravelOrderModel.getPictureUrl(),imageOptions);
        tvName.setText(bjTravelOrderModel.getStartPlace()+"——"+bjTravelOrderModel.getEndPlace());
        tvCount.setText(bjTravelOrderModel.getTravelNumber()+"天");
        tvPrice.setText(bjTravelOrderModel.getTotalMoney()+"");
        tvPhone.setText(bjTravelOrderModel.getPhone()+"");
        tvTime.setText(bjTravelOrderModel.getStartDatestr()+"~~"+bjTravelOrderModel.getEndDatestr());
        final String orderNo = bjTravelOrderModel.getOrderNo();
        final String orderId = bjTravelOrderModel.getId() + "";
        final String payMethod = bjTravelOrderModel.getPayMethod();
        String status = bjTravelOrderModel.getStatus();
        if(status.equals("2")){
            layoutShow.setVisibility(View.GONE);
        } else if(status.equals("3")){
            layoutShow.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
            btn.setText("申请退款");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRefund();
                }
                private void initRefund() {
                    final AlertDialog alert = new AlertDialog.Builder(TravelOrderDetailActivity.this).create();
                    View view = View.inflate(TravelOrderDetailActivity.this, R.layout.refund_dialog, null);
                    alert.setView(view);
                    alert.setCancelable(true);
                    RadioGroup rgRefund = (RadioGroup) view.findViewById(R.id.rg_refund);
                    final RadioButton rbOne = (RadioButton) view.findViewById(R.id.rb_refund_one);
                    final RadioButton rbTwo = (RadioButton) view.findViewById(R.id.rb_refund_two);
                    final RadioButton rbThree = (RadioButton) view.findViewById(R.id.rb_refund_three);
                    final RadioButton rbFour = (RadioButton) view.findViewById(R.id.rb_refund_four);
                    final RadioButton rbFive = (RadioButton) view.findViewById(R.id.rb_refund_five);
                    TextView tvSure = (TextView) view.findViewById(R.id.refund_sure);
                    rgRefund.check(index);
                    rgRefund.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            switch (checkedId){
                                case R.id.rb_refund_one:
                                    index=checkedId;
                                    stringLike = rbOne.getText().toString().trim();
                                    //Log.d("hotelprice", "initData: "+lowPrice+"price"+ highPrice);
                                    break;
                                case R.id.rb_refund_two:
                                    index=checkedId;
                                    stringLike = rbTwo.getText().toString().trim();
                                    break;
                                case R.id.rb_refund_three:
                                    index=checkedId;
                                    stringLike = rbThree.getText().toString().trim();
                                    break;
                                case R.id.rb_refund_four:
                                    index=checkedId;
                                    stringLike = rbFour.getText().toString().trim();
                                    break;
                                case R.id.rb_refund_five:
                                    index=checkedId;
                                    stringLike = rbFive.getText().toString().trim();
                                    break;
                            }
                            // Log.d("hotelprice", "initData: "+lowPrice+"price"+ highPrice);
                        }

                    });
                    tvSure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            if(NetWorkUtils.isNetworkAvailable(TravelOrderDetailActivity.this)){
                                //加载网络数据
                                try{
                                    byte[] mBytes=null;
                                    layoutLoading.setVisibility(View.VISIBLE);
                                    ivLoading.setBackgroundResource(R.drawable.loading_before);
                                    AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                                    background.start();
                                    if(TextUtils.isEmpty(stringLike)){
                                        ToastUtils.showCenter(getApplicationContext(),"退款原因不能为空");
                                        return;
                                    }
                                    String string="{\"orderNumber\":"+"\""+orderNo+"\",\"refundDesc\":"+"\""+stringLike+"\",\"payMethod\":"+"\""+payMethod+"\",\"type\":"+"\""+"travel"+"\",\"appKey\":\"BJYDAppV-2\"}";
                                    mBytes=string.getBytes("UTF8");
                                    String enString= AES.encrypt(mBytes);
                                    String replace = enString.replace("\n", "");
                                    RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_BACK_MONEY);
                                    params.addBodyParameter("secretMessage",replace);
                                    x.http().post(params, new Callback.CommonCallback<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            Log.d("loginfund", "onSuccess: "+result);
                                            Gson gson=new Gson();
                                            RefundHotelBean refundHotelBean = gson.fromJson(result, RefundHotelBean.class);
                                            boolean state = refundHotelBean.isState();
                                            if(state){
                                                //ToastUtils.show(getApplicationContext(),refundHotelBean.getMessage());
                                                bjTravelOrderModel.setIsPay(true);
                                                EnumEventBus travelrefund = EnumEventBus.TRAVELREFUND;
                                                EventBus.getDefault().post(new EventBusClass(travelrefund));
                                                //intent.putExtra("payOrder",payOrder);
                                                Intent intent=getIntent();
                                                String payOrder = intent.getStringExtra("payOrder");
                                                if(payOrder.equals("orderTravel")){
                                                    iniData();
                                                    //Intent intent1=new Intent(TravelOrderDetailActivity.this,)
                                                    finish();
                                                }else {
                                                    iniData();
                                                    finish();
                                                }
                                                if(payOrder.equals("travelpay")){
                                                    Intent intent2=getIntent();
                                                    Intent intent1=new Intent(TravelOrderDetailActivity.this,TravelDetailActivity.class);
                                                    String orderId = intent2.getStringExtra("orderId");
                                                    intent1.putExtra("orderId",orderId);
                                                    startActivity(intent1);
                                                    iniData();
                                                    finish();
                                                }else {
                                                    iniData();
                                                    finish();
                                                }
                                            }else{
                                                ToastUtils.show(getApplicationContext(),refundHotelBean.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable ex, boolean isOnCallback) {
                                            // mErrorView.show(tvPay,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                                            //ToastUtils.show(TravelOrderDetailActivity.this,"网络不给力,连接服务器异常!");
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

                                }catch(Exception e){
                                    mErrorView.show(tvCount,"服务器数据异常",ErrorView.ViewShowMode.ERROR);
                                }
                            }else{
                                layoutLoading.setVisibility(View.GONE);
                                //如果缓存数据不存在则需要用户打开网络设置
                                AlertDialog.Builder alert1 = new AlertDialog.Builder(TravelOrderDetailActivity.this);
                                alert1.setMessage("网络不可用，是否打开网络设置");
                                alert1.setCancelable(false);
                                alert1.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开网络设置
                                        startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));
                                    }
                                });
                                alert1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                    });
                    alert.show();
                }
            });
        }else if(status.equals("4")){
            btn.setVisibility(View.VISIBLE);
            btn.setText("去评价");
            //去评价
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=getIntent();
                    String orderId =intent1.getStringExtra("orderId");
                    Intent intent=new Intent(TravelOrderDetailActivity.this,TravelCommentActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }
            });
        }else if(status.equals("6")){
            btn.setVisibility(View.GONE);
        }else if(status.equals("7")){
            btn.setVisibility(View.GONE);
        } else if(status.equals("5")){
            layoutShow.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
        }else{
            layoutShow.setVisibility(View.VISIBLE);
        }
        tvNumber.setText(orderNo);

        //去支付
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelOrderDetailActivity.this,PayActivity.class);
                String orderNo = bjTravelOrderModel.getOrderNo();
                intent.putExtra("type","travel");
                intent.putExtra("orderNumber",orderNo);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payOrder","travelorder");
                startActivity(intent);

            }
        });
        //取消订单
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancel();
            }
            private void initCancel() {
                if(NetWorkUtils.isNetworkAvailable(TravelOrderDetailActivity.this)) {
                    //加载网络数据
                    layoutLoading.setVisibility(View.VISIBLE);
                    ivLoading.setBackgroundResource(R.drawable.loading_before);
                    AnimationDrawable background = (AnimationDrawable) ivLoading.getBackground();
                    background.start();
                    String orderId = bjTravelOrderModel.getId() + "";
                    RequestParams params = new RequestParams(HttpUrlUtils.TRAVEL_ORDER_CANCEL);
                    params.addBodyParameter("appKey", HttpUrlUtils.appKey);
                    params.addBodyParameter("orderId", orderId);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("canceltravel", "onSuccess: " + result);
                            Gson gson = new Gson();
                            OrderTravelCancelBean orderTravelCancelBean = gson.fromJson(result, OrderTravelCancelBean.class);
                            boolean state = orderTravelCancelBean.isState();
                            if (state) {
                                //ToastUtils.showCenter(getApplicationContext(),orderTravelCancelBean.getMessage());
                                EnumEventBus cancelTravel = EnumEventBus.ORDER;
                                EventBus.getDefault().post(new EventBusClass(cancelTravel));
                                finish();

                            } else {
                                ToastUtils.showCenter(getApplicationContext(), orderTravelCancelBean.getMessage());
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            //mErrorView.show(tvPay,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                            ToastUtils.show(TravelOrderDetailActivity.this,"网络不给力,连接服务器异常!");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {
                            //hud.dismiss();
                            layoutLoading.setVisibility(View.GONE);

                        }
                    });
                }else{
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    AlertDialog.Builder alert = new AlertDialog.Builder(TravelOrderDetailActivity.this);
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
        });

    }

    private void initToolbar() {
        viewline.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.back_black);
        Intent intent1=getIntent();
        String payOrder = intent1.getStringExtra("payOrder");
        if(!TextUtils.isEmpty(payOrder)&&payOrder.equals("travelpay")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayActivity.instance.finishActivity();
                    TravelOrderSubmitActivity.instance.finishActivity();
                    TravelOrderActivity.instance.finishActivity();
                    TravelDetailActivity.instance.finishActivity();
                    HotelActivity.instance.finishActivity();
                   /* Intent intent=new Intent(TravelOrderDetailActivity.this,TravelDetailActivity.class);
                    startActivity(intent);*/
                    finish();
                }
            });

        }else{
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        viewline.setVisibility(View.GONE);
        titleTv.setText("预定详情");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_travel_order_detail;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv_order_travel_pay:
               /* intent=new Intent(TravelOrderDetailActivity.this,PayActivity.class);
                startActivity(intent);*/
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
