package com.tdin360.zjw.marathon.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.alipay.PayResult;
import com.tdin360.zjw.marathon.model.Apay;
import com.tdin360.zjw.marathon.model.ApayAlipay;
import com.tdin360.zjw.marathon.model.HotelOrderBean;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.PayBean;
import com.tdin360.zjw.marathon.model.PayInfoBean;
import com.tdin360.zjw.marathon.model.PaySureBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.wxapi.Constants;
import com.tdin360.zjw.marathon.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 支付中心
 * @author zhangzhijun
 */
public class PayActivity extends BaseActivity implements WXPayEntryActivity.WXPAYResultListener,View.OnClickListener{
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

    @ViewInject(R.id.btn_pay_sure)
    private Button btnSure;

    @ViewInject(R.id.select1)
    private LinearLayout layoutAlipay;
    @ViewInject(R.id.select2)
    private LinearLayout layoutWeixinPay;
    @ViewInject(R.id.select3)
    private LinearLayout layoutUnionPay;

    @ViewInject(R.id.zhifubao)
    private RadioButton aliPay;
    @ViewInject(R.id.weixin)
    private RadioButton wXPay;
    @ViewInject(R.id.yinlian)
    private RadioButton yLPay;


    public static final String PAY_ACTION="PAY_OK";
    private IWXAPI api;
    //private RadioButton aliPay,wXPay,yLPay;
    //private String orderNo;
    //private String subject;
    //private String money;
    private boolean isFormDetail;//是否从详情进入支付
    private boolean isHotel;
    //private String url;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         initToolbar();
         initView();
         WXPayEntryActivity.listener=this;
    }
  private void initData() {
        try{
            String type = getIntent().getStringExtra("type");
            String orderNumber = getIntent().getStringExtra("orderNumber");
            byte[] mBytes=null;
            String string="{\"orderNumber\":"+"\""+orderNumber+"\",\"payMethod\":"+"\""+"zfb"+"\",\"type\":"+"\""+type+"\",\"appKey\":\"BJYDAppV-2\"}";
            Log.d("----------", "initData: "+string);
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.PAY);
            params.addBodyParameter("secretMessage",enString);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("hotelorderpAY", "onSuccess: "+result);
                    Gson gson=new Gson();
                    Apay apay = gson.fromJson(result, Apay.class);
                    boolean state = apay.isState();
                    if(state){
                        String orderSecretMessage = apay.getOrderSecretMessage();
                        String decrypt = AES.decrypt(orderSecretMessage);
                        Log.d("orderzfb", "onSuccess: "+decrypt);
                        ApayAlipay apayAlipay = gson.fromJson(decrypt, ApayAlipay.class);
                        ApayAlipay.ModelBean model = apayAlipay.getModel();
                        ApayAlipay.ModelBean.AppAliPayDataBean appAliPayData = model.getAppAliPayData();
                        String body = appAliPayData.getBody();
                        String decrypt1 = AES.decrypt(body);
                        Log.d("de", "onSuccess: "+decrypt1);
                        //String decrypt1 = AES.decrypt(body);
                       /* String str = "";
                        for(int i=0;i<decrypt1.length();i+=4){
                            String s = "";
                            for(int j=i;j<i+4;j++){
                                s+=String.valueOf(decrypt1.charAt(j));
                            }
                            str+=String.valueOf((char)Integer.valueOf(s, 16).intValue());
                        }
                        Log.d("str", "onSuccess: "+str);*/

                        //Log.d("orderzfb111", "onSuccess: "+s);


                    }else{
                        ToastUtils.showCenter(getApplicationContext(),apay.getMessage());
                    }

                  /*  Gson gson=new Gson();
                    PayBean payBean = gson.fromJson(result, PayBean.class);
                    boolean state = payBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),payBean.getMessage());
                        String orderSecretMessage = payBean.getOrderSecretMessage();
                        String decrypt = AES.decrypt(orderSecretMessage);
                        Log.d("order", "onSuccess: "+decrypt);
                        PayInfoBean payInfoBean = gson.fromJson(decrypt, PayInfoBean.class);
                        PayInfoBean.ModelBean model = payInfoBean.getModel();
                        PayInfoBean.ModelBean.AppWeiXinPayModelBean appWeiXinPayModel = model.getAppWeiXinPayModel();
                        PayInfoBean.ModelBean.AppAliPayDataBean appAliPayData = model.getAppAliPayData();

                        PayInfoBean.ModelBean.AppAliPayDataBean appAliPayData1 = model.getAppAliPayData();
                        String body = appAliPayData.getBody();

                        //支付宝支付
                        String decrypt1 = AES.decrypt(body);
                        Log.d("alipay", "onSuccess: "+decrypt1);
                       *//* String appId = appWeiXinPayModel.getAppId();
                        String nonceStr = appWeiXinPayModel.getNonceStr();
                        String packageValue = appWeiXinPayModel.getPackageValue();
                        String partnerId = appWeiXinPayModel.getPartnerId();
                        String prepayId = appWeiXinPayModel.getPrepayId();
                        String sign = appWeiXinPayModel.getSign();
                        String timeStamp = appWeiXinPayModel.getTimeStamp();
                        PayReq req = new PayReq();
                        req.appId=appId;
                        req.nonceStr=nonceStr;
                        req.packageValue=packageValue;
                        req.partnerId=partnerId;
                        req.prepayId=prepayId;
                        req.sign=sign;
                        req.timeStamp=timeStamp;
                        api.sendReq(req);*//*
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),payBean.getMessage());
                    }*/

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.d("error", "initData: ");

                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d("onCancelled", "initData: "+cex.getMessage());

                }

                @Override
                public void onFinished() {
                    Log.d("onFinished", "initData: ");
                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }
    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText("支付");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }
    @Override
    public int getLayout() {
        return R.layout.activity_pay;
    }
    private void initView() {
        layoutAlipay.setOnClickListener(this);
        layoutWeixinPay.setOnClickListener(this);
        layoutUnionPay.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        this.api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
       // Intent intent = getIntent();
        //this.orderNo = intent.getStringExtra("order");
       // this.subject = intent.getStringExtra("subject");
       // this.money = intent.getStringExtra("money");
        //this.isFormDetail = intent.getBooleanExtra("isFromDetail",false);
        //this.isHotel=intent.getBooleanExtra("isHotel",false);
       // url = isHotel ? HttpUrlUtils.HOTEL_PAY:HttpUrlUtils.PAY;
    }
    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void toAliPay(){
        this.delCommonPay("zfb");
    }
    //获取订单并支付
    private void delCommonPay(final String payMethod){
        //显示提示框
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
        try{
            String type = getIntent().getStringExtra("type");
            String orderNumber = getIntent().getStringExtra("orderNumber");
            byte[] mBytes=null;
            String string="{\"orderNumber\":"+"\""+orderNumber+"\",\"payMethod\":"+"\""+payMethod+"\",\"type\":"+"\""+type+"\",\"appKey\":\"BJYDAppV-2\"}";
            Log.d("----------", "initData: "+string);
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.PAY);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("hotelorderpAY", "onSuccess: "+result);
                    Gson gson=new Gson();
                    PayBean payBean = gson.fromJson(result, PayBean.class);
                    boolean state = payBean.isState();
                    if(state){
                        //ToastUtils.showCenter(getApplicationContext(),payBean.getMessage());
                        String orderSecretMessage = payBean.getOrderSecretMessage();
                        String decrypt = AES.decrypt(orderSecretMessage);
                        Log.d("order", "onSuccess: "+decrypt);
                        PayInfoBean payInfoBean = gson.fromJson(decrypt, PayInfoBean.class);
                        PayInfoBean.ModelBean model = payInfoBean.getModel();
                        //微信支付
                        if(payMethod.equals("wx")){
                            PayInfoBean.ModelBean.AppWeiXinPayModelBean appWeiXinPayModel = model.getAppWeiXinPayModel();
                            String appId = appWeiXinPayModel.getAppId();
                            String nonceStr = appWeiXinPayModel.getNonceStr();
                            String packageValue = appWeiXinPayModel.getPackageValue();
                            String partnerId = appWeiXinPayModel.getPartnerId();
                            String prepayId = appWeiXinPayModel.getPrepayId();
                            String sign = appWeiXinPayModel.getSign();
                            String timeStamp = appWeiXinPayModel.getTimeStamp();
                            PayReq req = new PayReq();
                            req.appId=appId;
                            req.nonceStr=nonceStr;
                            req.packageValue=packageValue;
                            req.partnerId=partnerId;
                            req.prepayId=prepayId;
                            req.sign=sign;
                            req.timeStamp=timeStamp;
                            api.sendReq(req);
                        }else{
                            //支付宝支付
                            PayInfoBean.ModelBean.AppAliPayDataBean appAliPayData = model.getAppAliPayData();
                            Log.d("error0", "initData: "+appAliPayData);
                            final String body = appAliPayData.getBody();
                            Log.d("error1", "initData: "+body);
                            //final String decrypt1 = AES.decrypt(body);
                            // Log.d("error2", "initData: "+decrypt1);
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    // 构造PayTask 对象
                                    PayTask alipay = new PayTask(PayActivity.this);

                                    // 调用支付接口，获取支付结果
                                    Map<String, String> result = alipay.payV2(body, true);
                                    String resultStatus = result.get("resultStatus");


                                    Log.d("----------------",resultStatus);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = resultStatus;
                                mHandler.sendMessage(msg);

                                }
                            };

                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }

                    }else{
                        ToastUtils.showCenter(getApplicationContext(),payBean.getMessage());
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(PayActivity.this,"网络不给力,连接服务器异常!");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d("onCancelled", "initData: "+cex.getMessage());

                }

                @Override
                public void onFinished() {
                    Log.d("onFinished", "initData: ");
                    hud.dismiss();
                    //layoutLoading.setVisibility(View.GONE);
                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    //PayResult payResult = new PayResult((String) msg.obj);
                    String obj = (String) msg.obj;
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    //String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    //String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(obj, "9000")) {

                        //向服务检查是否支付成功
                         checkedPayStatus();
//                        Intent intent = new Intent(PayActivity.this, PayResultActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(obj, "8000")) {
                            ToastUtils.show(getBaseContext(),"支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误

                            ToastUtils.show(getBaseContext(),"支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    //微信支付
    private void toWxPay(){
        this.delCommonPay("wx");
    }
    //银联支付
    private void toUnionpay(){
       //this.delCommonPay("wx");
    }

    /**
     * 微信支付成功回调
     */
    @Override
    public void onWXPaySuccess() {
        checkedPayStatus();
    }
    /**
     * 支付成功后直接弹出支持成功界面
     */
    private void showPaySuccessDialog(final String orderId, final String type){
            //从支付详情进入支付成功则去改变支付状态
           if(isFormDetail&&!isHotel) {
               Intent intent = new Intent(PAY_ACTION);
               //intent.putExtra("orderNo", orderNo);
               sendBroadcast(intent);
           }
           //酒店订单从详情进行支付
           if(isFormDetail&&isHotel){
//             改变支付状态
              EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageType.HOTEL_STATS_UPDATE));
           }
        final AlertDialog alert = new AlertDialog.Builder(this).create();
        View view = View.inflate(PayActivity.this, R.layout.pay_result_dialog, null);
        alert.setView(view);
        alert.setCancelable(true);
        view.findViewById(R.id.tv_cancel_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        //查看详情
        view.findViewById(R.id.btn_check_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(type.equals("travel")){
                   /* EnumEventBus cancelHotel = EnumEventBus.PAYTRAVEL;
                    EventBus.getDefault().post(new EventBusClass(cancelHotel));*/
                    intent =new Intent(PayActivity.this,TravelOrderDetailActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }else{
                   /* EnumEventBus cancelHotel = EnumEventBus.PAYHOTEL;
                    EventBus.getDefault().post(new EventBusClass(cancelHotel));*/
                    intent=new Intent(PayActivity.this,HotelOrderDetailActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }

              /*  if(!isHotel&&!isFormDetail){//来源于报名界面的支付
                    Intent intent = new Intent(PayActivity.this,MySigUpDetailActivity.class);
                    //intent.putExtra("orderNo",orderNo);
                    startActivity(intent);
                    finish();
                  //跳转到报名详细
                }else {
                    //返回更新
                    finish();
                }
                if(isHotel&&!isFormDetail){
                    //查看酒店预订详情
                }else {
                    finish();
                }

                alert.dismiss();*/
            }

        });
        alert.show();

    }
    /***
     * 向服务器检查支付是否成功
     */
    private void checkedPayStatus(){
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        try{
            final String type = getIntent().getStringExtra("type");
            final String orderNumber = getIntent().getStringExtra("orderNumber");
            byte[] mBytes=null;
            String string="{\"orderNumber\":"+"\""+orderNumber+"\",\"type\":"+"\""+type+"\",\"appKey\":\"BJYDAppV-2\"}";
            Log.d("----------", "initData: "+string);
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.PAY_SURE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("1111hotelorderpAY", "onSuccess: "+result);
                    Gson gson=new Gson();
                    PaySureBean paySureBean = gson.fromJson(result, PaySureBean.class);
                    boolean state = paySureBean.isState();
                    if(state){
                        String orderId = getIntent().getStringExtra("orderId");
                        showPaySuccessDialog(orderId,type);
                        if(type.equals("travel")){
                            EnumEventBus cancelHotel = EnumEventBus.PAYTRAVEL;
                            EventBus.getDefault().post(new EventBusClass(cancelHotel));
                        }else{
                            EnumEventBus cancelHotel = EnumEventBus.PAYHOTEL;
                            EventBus.getDefault().post(new EventBusClass(cancelHotel));
                        }
                        Log.d("payorderId", "onClick: "+orderId);
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),paySureBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(PayActivity.this,"网络不给力,连接服务器异常!");

                    Log.d("error", "initData: ");

                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d("onCancelled", "initData: "+cex.getMessage());

                }

                @Override
                public void onFinished() {
                    Log.d("onFinished", "initData: ");
                    hud.dismiss();
                    //layoutLoading.setVisibility(View.GONE);
                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }
    /**
     * 选择支付方式
     * @paramview
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pay_sure:
                //initData();
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    if(aliPay.isChecked()){
                        //支付宝支付
                        toAliPay();
                        //ToastUtils.showCenter(getApplicationContext(),"支付宝支付");
                    }else if(wXPay.isChecked()){
                        //微信支付
                        toWxPay();
                        //ToastUtils.showCenter(getApplicationContext(),"微信支付");
                    }else if(yLPay.isChecked()){
                        //银联支付
                        //ToastUtils.showCenter(getApplicationContext(),"银联支付");
                        toUnionpay();
                    }
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

                break;
            case R.id.select1:
                aliPay.setChecked(true);
                wXPay.setChecked(false);
                yLPay.setChecked(false);
                break;
            case R.id.select2:
                aliPay.setChecked(false);
                wXPay.setChecked(true);
                yLPay.setChecked(false);
                break;
            case R.id.select3:
                aliPay.setChecked(false);
                wXPay.setChecked(false);
                yLPay.setChecked(true);
                break;
        }
    }
}
