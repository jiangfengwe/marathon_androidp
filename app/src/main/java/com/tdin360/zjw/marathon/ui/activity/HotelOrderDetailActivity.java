package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OrderHOtelDetailBean;
import com.tdin360.zjw.marathon.model.OrderHotelBean;
import com.tdin360.zjw.marathon.model.OrderHotelCancelBean;
import com.tdin360.zjw.marathon.model.OrderHotelDetailDecryptBean;
import com.tdin360.zjw.marathon.model.RefundHotelBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
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

import java.util.List;

/**
 * 我的订单中酒店订单详情
 */

public class HotelOrderDetailActivity extends BaseActivity implements View.OnClickListener {
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

    @ViewInject(R.id.tv_order_hotel_pay)
    private TextView tvPay;
    @ViewInject(R.id.tv_order_hotel_cancel)
    private TextView tvCancel;
    @ViewInject(R.id.order_hotel_pic)
    private ImageView ivPiv;
    @ViewInject(R.id.order_hotel_name)
    private TextView tvName;
    @ViewInject(R.id.order_hotel_phone)
    private TextView tvPhone;
    @ViewInject(R.id.order_hotel_count)
    private TextView tvCount;
    @ViewInject(R.id.order_hotel_in)
    private TextView tvIn;
    @ViewInject(R.id.order_hotel_out)
    private TextView tvOut;
    @ViewInject(R.id.order_hotel_number)
    private TextView tvNumber;
    @ViewInject(R.id.order_hotel_price)
    private TextView tvPrice;
    @ViewInject(R.id.order_hotel_room_name)
    private TextView tvRoomName;
    @ViewInject(R.id.order_hotel_order)
    private TextView tvOrder;
    @ViewInject(R.id.btn_hotel_order_detail)
    private Button btn;

    @ViewInject(R.id.order_hotel_detail_webview)
    private WebView webView;

    private OrderHotelDetailDecryptBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel=new OrderHotelDetailDecryptBean.ModelBean.BJHotelOrderModelBean();


    ImageOptions imageOptions;

    @ViewInject(R.id.layout_order_hotel_detail)
    private LinearLayout layoutShow;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private String stringLike;
    private int highPrice;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
        initNet();
        Intent intent=getIntent();
        String orderId = intent.getStringExtra("orderId");
        String  url = HttpUrlUtils.HOTEL_ORDER_WEBVIEW+"?appKey="+HttpUrlUtils.appKey+"&orderId="+orderId;
        Log.d("orderIdurl", "onCreate: "+url);
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
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initDetailData();
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(getApplicationContext())){
            //加载网络数据
            initDetailData();
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


    private void initDetailData() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        Intent intent=getIntent();
        String orderId = intent.getStringExtra("orderId");
        //String orderId = getIntent().getStringExtra("orderId");
        RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_DETAIL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("orderId",orderId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("hotelorderSresult", "onSuccess: "+result);
                Gson gson=new Gson();
                OrderHOtelDetailBean orderHOtelDetailBean = gson.fromJson(result, OrderHOtelDetailBean.class);
                boolean state = orderHOtelDetailBean.isState();
                if(state){
                   String orderSecretMessage = orderHOtelDetailBean.getOrderSecretMessage();
                    String decrypt = AES.decrypt(orderSecretMessage);
                    Log.d("hotelorderSresult", "onSuccess: "+decrypt);
                    OrderHotelDetailDecryptBean orderHotelDetailDecryptBean = gson.fromJson(decrypt, OrderHotelDetailDecryptBean.class);
                    OrderHotelDetailDecryptBean.ModelBean model = orderHotelDetailDecryptBean.getModel();
                    bjHotelOrderModel= model.getBJHotelOrderModel();
                    String payMethod = bjHotelOrderModel.getPayMethod();
                    initData(payMethod);
                    Log.d("hotelorderS", "onSuccess: "+bjHotelOrderModel.getStatus());

                }else{
                    ToastUtils.showCenter(getApplicationContext(),orderHOtelDetailBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // mErrorView.show(rvHotel,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                //ToastUtils.showCenter(getApplicationContext(),"网络不给力,连接服务器异常!");
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

    private void initData(final String payMethod) {
        final String orderNo = bjHotelOrderModel.getOrderNo();
        x.image().bind(ivPiv,bjHotelOrderModel.getHotelPictureUrl(),imageOptions);
        tvName.setText(bjHotelOrderModel.getHotelName());
        tvPrice.setText(bjHotelOrderModel.getTotalMoney()+"");
        tvCount.setText(bjHotelOrderModel.getRoomNumber()+"间");
        tvRoomName.setText(bjHotelOrderModel.getHotelRoomName());
        tvPhone.setText(bjHotelOrderModel.getPhone()+"");
        tvIn.setText(bjHotelOrderModel.getEnterDateStr());
        tvOut.setText(bjHotelOrderModel.getLeaveDateStr());
        tvNumber.setText(orderNo);
        String status = bjHotelOrderModel.getStatus();
        Log.d("orderbacksss", "initRefund: "+payMethod);
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
                    final AlertDialog alert = new AlertDialog.Builder(HotelOrderDetailActivity.this).create();
                    View view = View.inflate(HotelOrderDetailActivity.this, R.layout.refund_dialog, null);
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
                            if(NetWorkUtils.isNetworkAvailable(HotelOrderDetailActivity.this)){
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
                                    String string="{\"orderNumber\":"+"\""+orderNo+"\",\"refundDesc\":"+"\""+stringLike+"\",\"payMethod\":"+"\""+payMethod+"\",\"type\":"+"\""+"hotel"+"\",\"appKey\":\"BJYDAppV-2\"}";
                                    Log.d("loginfund22222222222", "onSuccess: "+string);
                                    mBytes=string.getBytes("UTF8");
                                    String enString= AES.encrypt(mBytes);
                                    RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_BACK_MONEY);
                                    params.addBodyParameter("secretMessage",enString);
                                    x.http().post(params, new Callback.CommonCallback<String>() {
                                        @Override
                                        public void onSuccess(String result) {
                                            Log.d("loginfund", "onSuccess: "+result);
                                            Gson gson=new Gson();
                                            RefundHotelBean refundHotelBean = gson.fromJson(result, RefundHotelBean.class);
                                            boolean state = refundHotelBean.isState();
                                            if(state){
                                                //ToastUtils.show(getApplicationContext(),refundHotelBean.getMessage());
                                                bjHotelOrderModel.setIsPay(true);
                                                EnumEventBus travelrefund = EnumEventBus.HOTELREFUND;
                                                EventBus.getDefault().post(new EventBusClass(travelrefund));
                                                Intent intent1=getIntent();
                                                String payOrder = intent1.getStringExtra("payOrder");
                                                if(payOrder.equals("hotelorder")){
                                                    finish();
                                                }
                                                //intent.putExtra("payOrder","hotelpay");
                                                if(payOrder.equals("hotelpay")){
                                                    Intent intent=new Intent(HotelOrderDetailActivity.this,HotelDetailsActivity.class);
                                                    Intent intent2=getIntent();
                                                    String hotelId = getIntent().getStringExtra("hotelId");
                                                    //intent.putExtra("orderId",hotelId);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                finish();
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
                                AlertDialog.Builder alert1 = new AlertDialog.Builder(HotelOrderDetailActivity.this);
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
        }else if(status.equals("6")){
        btn.setVisibility(View.GONE);
       }else if(status.equals("7")){
        btn.setVisibility(View.GONE);
       }  else{
            layoutShow.setVisibility(View.VISIBLE);
        }
        tvOrder.setText(bjHotelOrderModel.getOrderTimeStr());
        //去支付
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelOrderDetailActivity.this,PayActivity.class);
                String orderId = getIntent().getStringExtra("orderId");
                String orderNo = bjHotelOrderModel.getOrderNo();
                intent.putExtra("type","hotel");
                intent.putExtra("orderNumber",orderNo);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payOrder","hotelorder");
                startActivity(intent);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancel();
            }
            private void initCancel() {
                final KProgressHUD hud = KProgressHUD.create(HotelOrderDetailActivity.this);
                hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(1)
                        .setDimAmount(0.5f)
                        .show();
                String orderId =bjHotelOrderModel.getId() + "";
                RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_CANCEL);
                params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                params.addBodyParameter("orderId",orderId);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("cancelhotel", "onSuccess: "+result);
                        Gson gson=new Gson();
                        OrderHotelCancelBean orderHotelCancelBean = gson.fromJson(result, OrderHotelCancelBean.class);
                        boolean state = orderHotelCancelBean.isState();
                        if(state){
                            //ToastUtils.showCenter(getApplicationContext(),orderHotelCancelBean.getMessage());
                            EnumEventBus cancelHotel = EnumEventBus.ORDERHOTELCANCEL;
                            EventBus.getDefault().post(new EventBusClass(cancelHotel));
                            finish();
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),orderHotelCancelBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        hud.dismiss();

                    }
                });
            }
        });
    }

    private void initView() {
        //tvPay.setOnClickListener(this);
       /* String str="订单已完成，<font color='#ff621a'>再次预定</font>";
        tvOrderText.setText(Html.fromHtml(str));*/

    }

    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=getIntent();
                String payOrder = intent1.getStringExtra("payOrder");
                if(payOrder.equals("hotelorder")){
                    finish();
                }
                //intent.putExtra("payOrder","hotelpay");
                if(payOrder.equals("hotelpay")){
                    Intent intent=new Intent(HotelOrderDetailActivity.this,HotelDetailsActivity.class);
                    Intent intent2=getIntent();
                    String orderId = intent2.getStringExtra("orderId");
                    //intent.putExtra("orderId",orderId);
                    startActivity(intent);
                    finish();
                }
                //finish();
            }
        });
        viewline.setVisibility(View.GONE);
        titleTv.setText("预定详情");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_order_detail;
    }

    @Override
    public void onClick(View v) {
        /*Intent intent;
        switch (v.getId()){
            case R.id.tv_hotel_pay:
                intent=new Intent(HotelOrderDetailActivity.this,PayActivity.class);
                startActivity(intent);
                break;
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
