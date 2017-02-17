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
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.pay.alipay.PayResult;
import com.tdin360.zjw.marathon.pay.wxapi.Constants;
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
 */
public class PayActivity extends BaseActivity {
    private IWXAPI api;
    private RadioButton zhifubao,weixin,yinlian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("支付中心");
        showBackButton();
        initView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_pay;
    }

    private void initView() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        this.api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        this.zhifubao = (RadioButton) this.findViewById(R.id.zhifubao);
        this.weixin = (RadioButton) this.findViewById(R.id.weixin);
        this.yinlian = (RadioButton) this.findViewById(R.id.yinlian);
        this.zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    weixin.setChecked(false);
                    yinlian.setChecked(false);
                }
            }
        });
        this.weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    zhifubao.setChecked(false);
                    yinlian.setChecked(false);
                }
            }
        });
        this.yinlian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    zhifubao.setChecked(false);
                    weixin.setChecked(false);
                }
            }
        });


    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void toAliPay(){

        /**
         * 完整的符合支付宝参数规范的订单信息
         **/
//        String orderInfo = getOrderInfo(signUpInfo.getSubject(),signUpInfo.getBody(),signUpInfo.getTotal_fee());
//        final String payInfo = orderInfo+"\""+"&sign="+"\""+signUpInfo.getSign()+"\""+"&sign_type="+"\""+signUpInfo.getSign_type()+"\"";
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
//                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
                mHandler.sendMessage(msg);

            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
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
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

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

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" +""+ "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + ""+ "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" +""+ "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径http://notify.msp.hk/notify.htm
        orderInfo += "&notify_url=" + "\"" +""+ "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
//    private String getSignType() {
//        return "sign_type=\"RSA\"";
//    }



    //微信支付

    private void toWxPay(){


        String url = "http://byydtk.oicp.net:26211/home/AppPay";
        Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();

        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10*1000);
        params.setMaxRetryCount(1);

        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String s) {


                if(s!=null&&!s.equals("")){

                    try {
                        JSONObject obj = new JSONObject(s);


                        if(null != obj && !obj.has("retcode") ){

                            PayReq req = new PayReq();

                            Log.d("执行了===============>>>>", "onSuccess: ");
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

                            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText( PayActivity.this, "返回错误"+obj.getString("retmsg"), Toast.LENGTH_SHORT).show();

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

                Log.d("银联支付＝＝＝＝＝＝》》", "onSuccess: "+s);
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
        if(zhifubao.isChecked()){

           // toAliPay();
            Toast.makeText(this,"支付宝",Toast.LENGTH_SHORT).show();

          /**
           * 微信支付
          */
        }else if(weixin.isChecked()){

            Toast.makeText(this,"微信支付",Toast.LENGTH_SHORT).show();
            toWxPay();
          /**
           * 银联支付
           */
        }else if(yinlian.isChecked()){
            Toast.makeText(this,"银联",Toast.LENGTH_SHORT).show();
           toUnionpay();
        }
    }
}
