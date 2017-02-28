package com.tdin360.zjw.marathon.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.wxapi.Constants;
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


    }


    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void toAliPay(){


        //获取支付订单
         RequestParams params = new RequestParams("http://byydtk.oicp.net:26211/eventInfo/AliPay1");

//         params.addQueryStringParameter("orderNomber","52212419891010481820170217135731");
//         params.addQueryStringParameter("payMethod","zfb");
         x.http().get(params, new Callback.CommonCallback<String>() {
             @Override
             public void onSuccess(final String result) {


                 Log.d("-------->", "onSuccess: "+result);

             try {
                   JSONObject json = new JSONObject(result);
//
//
//                     Log.d("----------->>>>", "onSuccess: "+result);
//                     JSONObject eventMobileMessage = json.getJSONObject("EventMobileMessage");
//                     boolean success = eventMobileMessage.getBoolean("Success");
//
                final String reason = json.getString("Reason");
//
//                     if(success){
//
//
//                         JSONObject data = json.getJSONObject("RequestData");
//
//
//                         String partner = data.getString("Partner");
//                         String input_charset = data.getString("_input_charset");
//                         String sign_type = data.getString("Sign_type");
//                         String notify_url = data.getString("Notify_url");
//                         String out_trade_no = data.getString("Out_trade_no");
//                         String subject = data.getString("Subject");
//                         String service = data.getString("Service");
//                         String payment_type = data.getString("Payment_type");
//                         String seller_id = data.getString("Seller_id");
//                         String total_fee = data.getString("Total_fee");
//                         String body = data.getString("Body");
//                         String sgin = data.getString("Sgin");
//
//
//                         /**
//                          * 完整的符合支付宝参数规范的订单信息
//                          **/
//                         final String orderInfo = getOrderInfo(partner,seller_id,out_trade_no,subject,body,total_fee,notify_url,service,payment_type,input_charset,sgin,sign_type);
//
//                         Log.d("-----order---->>", "onSuccess: "+orderInfo);
                         Runnable payRunnable = new Runnable() {

                             @Override
                             public void run() {
                                 // 构造PayTask 对象
                                 PayTask alipay = new PayTask(PayActivity.this);
                                 // 调用支付接口，获取支付结果
                                 String result = alipay.pay (reason, true);

                                 Message msg = new Message();
                                 msg.what = SDK_PAY_FLAG;
                                 msg.obj = result;
                                 mHandler.sendMessage(msg);

                             }
                         };

                         // 必须异步调用
                         Thread payThread = new Thread(payRunnable);
                         payThread.start();

//                     }else {
//                Toast.makeText(PayActivity.this,reason,Toast.LENGTH_SHORT).show();
//
//                     }

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
//                case SDK_PAY_FLAG: {
//
//
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//
//
//
//
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
            }
        }

    };



    /**
     *
     * create the order info. 创建 支付宝订单信息
     * @param partner  签约合作者身份ID
     * @param seller_id 签约卖家支付宝账号
     * @param out_trade_no 商户网站唯一订单号
     * @param subject 商品名称
     * @param body 商品详情
     * @param price 商品金额
     * @param notify_url 服务器异步通知
     * @param service 服务接口名称
     * @param payment_type 支付类型
     * @param _input_charset  参数编码
     * @param sin  签名
     * @param sign_type 签名类型
     * @return
     */
    private String getOrderInfo(String partner,String seller_id,
                                String out_trade_no,String subject, String body,
                                String price,String notify_url,String service,
                                String payment_type,String _input_charset,String sin,String sign_type) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" +partner+ "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" +seller_id+ "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" +out_trade_no+ "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径http://notify.msp.hk/notify.htm
        orderInfo += "&notify_url=" + "\"" +notify_url+ "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service="+"\""+service+"\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type="+"\""+payment_type+"\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset="+"\""+_input_charset+"\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        //orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        //签名
        orderInfo+="&sign="+"\""+sin+"\"";
//        签名类型
        orderInfo+="&sign_type="+"\""+sign_type+"\"";

        return orderInfo;
    }




    //微信支付

    private void toWxPay(){


        String url = "http://byydtk.oicp.net:26211/eventInfo/Pay";
        Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();

        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10*1000);
        params.setMaxRetryCount(1);
        params.addQueryStringParameter("orderNomber","52212419891010481820170217135731");
        params.addQueryStringParameter("payMethod","wx");
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

                            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();


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

            toAliPay();
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

    /**
     * 选择支付方式
     * @param view
     */
    public void onSelect(View view) {


        switch (view.getId()){

            case R.id.select1:

                zhifubao.setChecked(true);
                weixin.setChecked(false);
                yinlian.setChecked(false);
                break;
            case R.id.select2:
                zhifubao.setChecked(false);
                weixin.setChecked(true);
                yinlian.setChecked(false);
                break;

            case R.id.select3:
                zhifubao.setChecked(false);
                weixin.setChecked(false);
                yinlian.setChecked(true);
                break;
        }
    }
}
