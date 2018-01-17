package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.alipay.PayResult;
import com.tdin360.zjw.marathon.jiguan.ExampleUtil;
import com.tdin360.zjw.marathon.model.BindPhoneBean;
import com.tdin360.zjw.marathon.model.BindPhoneSureBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OtherLoginUserInfoBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 绑定手机号
 */

public class BindPhoneActivity extends BaseActivity implements View.OnClickListener {
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

    @ViewInject(R.id.et_bind_phone_new)
    private EditText etPhone;
    @ViewInject(R.id.iv_bind_phone_new_cancel)
    private ImageView ivCancel;
    @ViewInject(R.id.tv_bind_phone_get_code)
    private TextView tvCode;
    @ViewInject(R.id.et_bind_phone_input_code)
    private EditText etCode;
    @ViewInject(R.id.btn_bind_phone_sure)
    private Button btnSure;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private static final int CODE=0x0104;
    private int time;
    private static final int MSG_SET_ALIAS = 1003;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    //Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            Log.d("code", "gotResult: "+code);
            switch (code) {
                case 0:
                    ToastUtils.showCenter(getApplicationContext(),"codeeeee");
                    logs = "Set tag and alias success";
                    // Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    //Log.e(TAG, logs);
            }
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(CODE,1000);
            if(time>0){
                tvCode.setText(time+"s后获取");
                tvCode.setBackgroundColor(Color.parseColor("#ebebeb"));
                tvCode.setTextColor(Color.parseColor("#9b9b9b"));
                tvCode.setClickable(false);
                time--;
            }else {
                tvCode.setClickable(true);
                handler.removeMessages(CODE);
                tvCode.setText("获取验证码");
                tvCode.setTextColor(Color.parseColor("#ffffff"));
                tvCode.setBackgroundColor(Color.parseColor("#ff621a"));
                tvCode.setEnabled(true);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }

    private void initView() {
        tvCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
    }
    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewline.setVisibility(View.GONE);
        titleTv.setText("绑定手机号");
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etPhone.getEditableText() + "";
                if(!TextUtils.isEmpty(name)){
                    ivCancel.setVisibility(View.VISIBLE);
                }else{
                    ivCancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_bind_phone_get_code:
                //发送验证码
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initCode();
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
            case R.id.iv_bind_phone_new_cancel:
                //一键清空
                etPhone.setText("");
                break;
            case R.id.btn_bind_phone_sure:
                //绑定
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
                break;
        }
    }
    private void initData() {
        try{
            Intent intent=getIntent();
            String uId = intent.getStringExtra("uId");
            final String img = intent.getStringExtra("img");
            byte[] mBytes=null;
            final String phone1 = etPhone.getText().toString().trim();
            if(TextUtils.isEmpty(phone1)){
                ToastUtils.showCenter(getApplicationContext(),"手机号不能为空");
                return;
            }
            if(!isMobileNO(phone1)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            String code = etCode.getText().toString().trim();
            if(TextUtils.isEmpty(code)){
                ToastUtils.showCenter(getApplicationContext(),"验证码不能为空");
                return;
            }
           /* layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();*/
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            Log.d("uid", "initData: "+uId);
            String string="{'userPhone':'"+phone1+"','appKey': 'BJYDAppV-2','validCode':'"+code+"','uId':'"+uId+"'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.OTHER_PHONE);
            params.addBodyParameter("secretMessage",enString);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("loginbind", "onSuccess: "+result);
                    Gson gson=new Gson();
                    BindPhoneSureBean bindPhoneSureBean = gson.fromJson(result, BindPhoneSureBean.class);
                    boolean state = bindPhoneSureBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),bindPhoneSureBean.getMessage());
                        OtherLoginUserInfoBean.UserBean user = SingleClass.getInstance().getUser();
                        String id = user.getId()+"";
                        String headImg = user.getHeadImg();
                        String nickName = user.getNickName();
                        boolean gender = user.isGender();
                        String unionid = user.getUnionid();
                        boolean isBindPhone = user.isIsBindPhone();
                        String customerSign = user.getCustomerSign();
                        String customerAlias = user.getCustomerAlias();
                        String phone = user.getPhone();
                        // 调用 Handler 来异步设置别名
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS,customerAlias));
                        LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(id, img, nickName, gender, unionid, isBindPhone, customerSign, phone1);
                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(BindPhoneActivity.this,userBean);
                        EnumEventBus em = EnumEventBus.BIND;
                        EventBus.getDefault().post(new EventBusClass(em));
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);
                        //通知webActivity修改登录状态
                        Intent intent1 = getIntent();
                        int webview = intent1.getIntExtra("webview", -1);
                        //String webview = intent1.getStringExtra("webview");
                        if(webview==1){
                            EnumEventBus web = EnumEventBus.WEBVIEW;
                            EventBus.getDefault().post(new EventBusClass(web));
                        }
                        if(webview==2){
                            EnumEventBus circlePraise = EnumEventBus.CIRCLE;
                            EventBus.getDefault().post(new EventBusClass(circlePraise));
                        }
                        //通知网页
                        EnumEventBus web = EnumEventBus.WEBVIEW;
                        EventBus.getDefault().post(new EventBusClass(web));
                        //通知社交
                        EnumEventBus circlePraise = EnumEventBus.CIRCLE;
                        EventBus.getDefault().post(new EventBusClass(circlePraise));
                      /*  //通知webActivity修改登录状态
                        Intent intent1 = getIntent();
                        String webview = intent1.getStringExtra("webview");
                        switch (webview) {
                            case "1":
                                EnumEventBus web = EnumEventBus.WEBVIEW;
                                EventBus.getDefault().post(new EventBusClass(web));
                                break;
                            case "2":
                                EnumEventBus circle = EnumEventBus.CIRCLE;
                                EventBus.getDefault().post(new EventBusClass(circle));
                                break;
                        }*/
                        finish();
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),bindPhoneSureBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    //layoutLoading.setVisibility(View.GONE);
                    hud.dismiss();

                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }
    /**
     * 开的倒计时
     */
    private void startTaskTimer(){
        time=60;
        tvCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }
    /**
      * 验证手机格式
      */
    public static boolean isMobileNO(String mobiles) {
     /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    private void initCode() {
        try{
            Intent intent=getIntent();
            String uId = intent.getStringExtra("uId");
            byte[] mBytes=null;
            final String phone = etPhone.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"手机号不能为空");
                return;
            }
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            startTaskTimer();
            String string="{'userPhone':'"+phone+"','uId':'"+uId+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.OTHER_PHONE_CODE);
            params.addBodyParameter("secretMessage",enString);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("loginccode", "onSuccess: "+result);
                    Gson gson=new Gson();
                    BindPhoneBean bindPhoneBean = gson.fromJson(result, BindPhoneBean.class);
                    boolean state = bindPhoneBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),bindPhoneBean.getMessage());
                        //finish();
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),bindPhoneBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    //layoutLoading.setVisibility(View.GONE);
                    //hud.dismiss();

                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }
}
