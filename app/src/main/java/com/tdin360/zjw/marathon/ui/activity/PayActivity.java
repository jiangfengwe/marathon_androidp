package com.tdin360.zjw.marathon.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.alipay.PayResult;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.wxapi.Constants;
import com.tdin360.zjw.marathon.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 支付中心
 * @author zhangzhijun
 */
public class PayActivity extends BaseActivity implements WXPayEntryActivity.WXPAYResultListener{

    public static final String PAY_ACTION="PAY_OK";
    private IWXAPI api;
    private RadioButton aliPay,wXPay,yLPay;
    private String orderNo;
    private String subject;
    private String money;
    private String from;//支付来源（报名成功进入支付、我的报名中进入支付）
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("支付中心");
        showBackButton();
         initView();
         WXPayEntryActivity.listener=this;


    }

    @Override
    public int getLayout() {
        return R.layout.activity_pay;
    }

    private void initView() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        this.api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        this.aliPay = (RadioButton) this.findViewById(R.id.zhifubao);
        this.wXPay = (RadioButton) this.findViewById(R.id.weixin);
        this.yLPay = (RadioButton) this.findViewById(R.id.yinlian);
        TextView priceView = (TextView) this.findViewById(R.id.price);
        TextView subjectView = (TextView) this.findViewById(R.id.subject);

        Intent intent = getIntent();

        this.orderNo = intent.getStringExtra("order");
        this.subject = intent.getStringExtra("subject");
        this.money = intent.getStringExtra("money");
        this.from = intent.getStringExtra("from");

        //显示支付费用及描述
        priceView.setText("¥ "+money);
        subjectView.setText(subject);



    }


    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void toAliPay(){


        Toast.makeText(PayActivity.this,"获取订单中...",Toast.LENGTH_SHORT).show();
        //获取支付订单
         RequestParams params = new RequestParams(HttpUrlUtils.PAY);
         params.addBodyParameter("appKey",HttpUrlUtils.appKey);
         params.addQueryStringParameter("orderNomber",orderNo);
         params.addQueryStringParameter("payMethod","zfb");
         x.http().get(params, new Callback.CommonCallback<String>() {
             @Override
             public void onSuccess(final String result) {

             try {
                   JSONObject json = new JSONObject(result);
                     JSONObject eventMobileMessage = json.getJSONObject("EventMobileMessage");
                     boolean success = eventMobileMessage.getBoolean("Success");

               // final String reason = eventMobileMessage.getString("Reason");

                    if(success){


                        JSONObject aliPayData = json.getJSONObject("AliPayData");
                        final String body = aliPayData.getString("Body");

                        Runnable payRunnable = new Runnable() {

                             @Override
                             public void run() {
                                 // 构造PayTask 对象
                                 PayTask alipay = new PayTask(PayActivity.this);
                                 // 调用支付接口，获取支付结果
                                 String result = alipay.pay (body, true);

                                 Message msg = new Message();
                                 msg.what = SDK_PAY_FLAG;
                                 msg.obj = result;
                                 mHandler.sendMessage(msg);

                             }
                         };

                         // 必须异步调用
                         Thread payThread = new Thread(payRunnable);
                         payThread.start();



                  }

                 } catch (JSONException e) {
                     e.printStackTrace();
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

             }
         });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {

                        //向服务检查是否支付成功
                         checkedPayStatus();
//                        Intent intent = new Intent(PayActivity.this, PayResultActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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



        Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();

        RequestParams params = new RequestParams(HttpUrlUtils.PAY);
        params.setConnectTimeout(10*1000);
        params.setMaxRetryCount(1);
        params.addQueryStringParameter("orderNomber",orderNo);
        params.addQueryStringParameter("payMethod","wx");
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String s) {


                if(s!=null&&!s.equals("")){

                    try {
                        JSONObject json = new JSONObject(s);

                        JSONObject message = json.getJSONObject("EventMobileMessage");

                        boolean success = message.getBoolean("Success");
                        String reason = message.getString("Reason");
                        if(success){

                            JSONObject obj = json.getJSONObject("AppWeiXinPayModel");
                            PayReq req = new PayReq();
                            req.appId			= obj.getString("appId");
                            req.partnerId		= obj.getString("partnerId");
                            req.prepayId		= obj.getString("prepayId");
                            req.nonceStr		= obj.getString("nonceStr");
                            req.timeStamp		= obj.getString("timeStamp");
                            req.packageValue	= obj.getString("packageValue");
                            req.sign			= obj.getString("sign");
//                          req.extData			= obj.getString("extData"); // optional

                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

                            api.sendReq(req);



                        }else {

                            Toast.makeText(PayActivity.this,reason,Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }




    //    银联支付模式配置00表示生产环境，01表示测试环境
    private String mMode = "01";
    //银联支付
    private void toUnionpay(){


        String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
        RequestParams params = new RequestParams(TN_URL_01);
        params.setConnectTimeout(120000);
        params.setMaxRetryCount(1);

        x.http().post(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String s) {


                if ( s == null || s.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setTitle("错误提示");
                    builder.setMessage("网络连接失败,请重试!");
                    builder.setNegativeButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {

                    /*************************************************
                     * 步骤2：通过银联工具类启动支付插件
                     ************************************************/

                    UPPayAssistEx.startPay(PayActivity.this, null, null, s, mMode);
                }



            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                builder.setTitle("错误提示");
                builder.setMessage("网络连接失败,请重试!");
                builder.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {


            }
        });



    }
    //接收银联支付返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    //银联支付验证
    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }


    /**
     * 进入支付
     * @param view
     */
    public void toPay(View view) {

         /**
          * 支付宝支付
          */
        if(aliPay.isChecked()){

            toAliPay();


          /**
           * 微信支付
          */
        }else if(wXPay.isChecked()){


            toWxPay();
          /**
           * 银联支付
           */
        }else if(yLPay.isChecked()){

            Toast.makeText(PayActivity.this,"开发中...",Toast.LENGTH_SHORT).show();
           //toUnionpay();
        }
    }

    /**
     * 选择支付方式
     * @param view
     */
    public void onSelect(View view) {


        switch (view.getId()){

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
    private void showPaySuccessDialog(){


        //(通过广播来更新)来自与报名详情支付成功更改支付状态
        if(from.equals("signUpDetail")){

            Intent intent = new Intent(PAY_ACTION);
            sendBroadcast(intent);
        }

        final AlertDialog alert = new AlertDialog.Builder(this).create();
        View view = View.inflate(PayActivity.this, R.layout.pay_result_dialog, null);
        alert.setView(view);
        alert.setCancelable(false);
        TextView freeView = (TextView) view.findViewById(R.id.free);
        freeView.setText("¥ "+money);

        //返回主页
        view.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                alert.dismiss();
            }
        });

        //查看详情
        view.findViewById(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this,MySigUpDetailActivity.class);
                if(from.equals("signUp")){//来源于报名界面的支付

                    startActivity(intent);
                    finish();
                  //跳转到报名详细
                }else {

                    //返回更新
                    finish();
                }

                alert.dismiss();
            }

        });


        alert.show();

    }


    /***
     * 向服务器检查支付是否成功
     */
    private void checkedPayStatus(){

        RequestParams params = new RequestParams(HttpUrlUtils.CHECKED_PAY_STATUS);
        params.setConnectTimeout(5*1000);
        params.addBodyParameter("orderNo",orderNo);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject obj = new JSONObject(result);

                    boolean isPay = obj.getBoolean("isPay");

                    //支付成功
                    if(isPay){

                   showPaySuccessDialog();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
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

            }
        });

    }
}
