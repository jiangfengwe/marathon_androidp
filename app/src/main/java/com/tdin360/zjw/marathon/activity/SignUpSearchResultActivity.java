package com.tdin360.zjw.marathon.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.alipay.PayResult;
import com.tdin360.zjw.marathon.model.SignUpInfo;
import com.tdin360.zjw.marathon.model.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.weight.SelectPayPopupWindow;
import com.tdin360.zjw.marathon.wxapi.Constants;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * 报名查询详情
 */
public class SignUpSearchResultActivity extends BaseActivity {


    private TextView title;//导航栏标题
    private TextView name;//用户名
    private TextView phone;//电话
    private TextView birthday;//出生日期
    private TextView idNumber;//证件号码
    private TextView certificateType;//证件类型
    private TextView gender;//性别
    private TextView nationality;//国家
    private TextView province;//省/市
    private TextView city;//城市
    private TextView county;//区县
    private TextView attendProject;//参赛项目
    private TextView clothingSize;//服装尺码
    private TextView address;//现居地址
    private TextView postcode;//邮政编码
    private TextView urgencyLinkman;//紧急联系人姓名
    private TextView urgencyLinkmanPhone;//紧急联系人电话
    private TextView createTime;//报名时间
    private TextView attendNumber;//参赛号码
    private TextView applyNature;//报名方式（单人、团体）
    private TextView money;//报名费用
    private TextView orderNumber;//订单号
    private Button payBtn;//立即支付按钮

    //自定义的弹出框类
    private SelectPayPopupWindow menuWindow;
    //提交表单信息模型
    private SignUpInfo signUpInfo;

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setToolBarTitle("报名信息确认");

         showBackButton();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        this.api = WXAPIFactory.createWXAPI(this,null);


        initView();
        loadData();
        /**
         * 进入支付
         */
        this.findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pop弹出选择支付方式
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPayPopupWindow(SignUpSearchResultActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(SignUpSearchResultActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_up_search_result;
    }

    //为弹出窗口实现事件监听
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_aliPay://支付宝支付
                      toAliPay();
                    break;
                case R.id.btn_wXPay://微信支付
                   toWxPay();
                    break;
                default:
                    break;
            }


        }

    };

    /**
     * 初始化
     */
    private void initView() {


        this.name = (TextView) this.findViewById(R.id.name);
        this.phone = (TextView) this.findViewById(R.id.phone);
        this.idNumber = (TextView) this.findViewById(R.id.IDNumber);
        this.birthday = (TextView) this.findViewById(R.id.birthday);
        this.certificateType = (TextView) this.findViewById(R.id.IDNumberType);
        this.gender = (TextView) this.findViewById(R.id.gander);
        this.nationality = (TextView) this.findViewById(R.id.country);
        this.province = (TextView) this.findViewById(R.id.province);
        this.city = (TextView) this.findViewById(R.id.city);
        this.county = (TextView) this.findViewById(R.id.county);
        this.attendProject = (TextView) this.findViewById(R.id.projectName);
        this.clothingSize = (TextView) this.findViewById(R.id.clothesSize);
        this.address = (TextView) this.findViewById(R.id.address);
        this.postcode = (TextView) this.findViewById(R.id.post);
        this.urgencyLinkman = (TextView) this.findViewById(R.id.contactsName);
        this.urgencyLinkmanPhone = (TextView) this.findViewById(R.id.contactsPhone);
        this.attendNumber = (TextView) this.findViewById(R.id.attendNumber);
        this.createTime = (TextView) this.findViewById(R.id.time);
        this.applyNature = (TextView) this.findViewById(R.id.applyNature);
        this.money = (TextView) this.findViewById(R.id.cost);
        this.orderNumber= (TextView) this.findViewById(R.id.orderNumber);
        this.payBtn = (Button) this.findViewById(R.id.payBtn);
    }

    /**
     * 加载数据
     */
    private void loadData() {

        Intent intent = this.getIntent();
        String title = intent.getStringExtra("title");
        this.title.setText(title);
        this.signUpInfo = (SignUpInfo) intent.getSerializableExtra("signUpInfo");
        if(signUpInfo==null){
            return;
        }
        /**
         * 绑定显示数据
         */

        this.name.setText(signUpInfo.getName());
        this.phone.setText(signUpInfo.getPhone());
        this.idNumber.setText(signUpInfo.getIdNumber());
        this.birthday.setText(signUpInfo.getBirthday());
        this.certificateType.setText(signUpInfo.getCertificateType());
        this.gender.setText(signUpInfo.getGender());
        this.nationality.setText(signUpInfo.getNationality());
        this.province.setText(signUpInfo.getProvince());
        this.city.setText(signUpInfo.getCity());
        this.county.setText(signUpInfo.getCounty());
        this.attendProject.setText(signUpInfo.getAttendProject());
        this.clothingSize.setText(signUpInfo.getClothingSize());
        this.address.setText(signUpInfo.getAddress());
        this.postcode.setText(signUpInfo.getPostcode());
        this.urgencyLinkman.setText(signUpInfo.getUrgencyLinkman());
        this.urgencyLinkmanPhone.setText(signUpInfo.getUrgencyLinkmanPhone());
        this.attendNumber.setText(signUpInfo.getAttendNumber());
        this.createTime.setText(signUpInfo.getCreateTime());
        this.money.setText(signUpInfo.getTotal_fee());
        this.applyNature.setText(signUpInfo.getApplyNature().equals("")? "个人":signUpInfo.getApplyNature());
        this.orderNumber.setText(signUpInfo.getOut_trade_no());
        if(signUpInfo.isPayed()){//已支付
            payBtn.setText("已支付");
            payBtn.setEnabled(false);
            payBtn.setBackgroundResource(R.drawable.oval_enable_button);
        }
    }

    public void back(View view) {
        finish();
    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 1;
    private void toAliPay(){

        /**
         * 完整的符合支付宝参数规范的订单信息
         **/
        String orderInfo = getOrderInfo(signUpInfo.getSubject(),signUpInfo.getBody(),signUpInfo.getTotal_fee());
        final String payInfo = orderInfo+"\""+"&sign="+"\""+signUpInfo.getSign()+"\""+"&sign_type="+"\""+signUpInfo.getSign_type()+"\"";
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(SignUpSearchResultActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

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
                        Toast.makeText(SignUpSearchResultActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                         payBtn.setText("已支付");
                         payBtn.setEnabled(false);
                         payBtn.setBackgroundResource(R.drawable.oval_enable_button);
                         signUpInfo.setPayed(true);
                         SharedPreferencesManager.insertValue(getApplicationContext(),signUpInfo);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SignUpSearchResultActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            payBtn.setText("支付确认中");
                            payBtn.setEnabled(false);
                            payBtn.setBackgroundResource(R.drawable.oval_enable_button);

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SignUpSearchResultActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

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
        String orderInfo = "partner=" + "\"" + signUpInfo.getPartner() + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + signUpInfo.getSeller_id() + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" +signUpInfo.getOut_trade_no()+ "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径http://notify.msp.hk/notify.htm
        orderInfo += "&notify_url=" + "\"" +signUpInfo.getNotify_url()+ "\"";

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


        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        Toast.makeText(this, "获取订单中...", Toast.LENGTH_SHORT).show();

        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10*1000);
        params.setMaxRetryCount(1);

        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String s) {

                Log.d("===============>", "onSuccess: "+s);

              if(s!=null&&!s.equals("")){

                  try {
                      JSONObject obj = new JSONObject(s);


                      if(null != obj && !obj.has("retcode") ){

                          PayReq req = new PayReq();

                          req.appId			= Constants.APP_ID;
                          req.partnerId		= obj.getString("partnerid");
                          req.prepayId		= obj.getString("prepayid");
                          req.nonceStr		= obj.getString("noncestr");
                          req.timeStamp		= obj.getString("timestamp");
                          req.packageValue	= obj.getString("package");
                          req.sign			= obj.getString("sign");
                          req.extData			= "app data"; // optional
                          
                          // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

                          api.sendReq(req);
                          Toast.makeText(SignUpSearchResultActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                      }else {

                          Toast.makeText( SignUpSearchResultActivity.this, "返回错误"+obj.getString("retmsg"), Toast.LENGTH_SHORT).show();

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

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


}