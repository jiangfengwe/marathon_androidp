package com.tdin360.zjw.marathon.ui.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.jiguan.ExampleUtil;
import com.tdin360.zjw.marathon.model.RegisterCodeBean;
import com.tdin360.zjw.marathon.model.RegisterSureBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.tdin360.zjw.marathon.R.id.phone;


/**
 * 输入手机号获取验证码
 * @ahthor zzj
 * @time 17/7/30 上午9:32
 */
public class RegisterOneActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.iv_register_back)
    private ImageView ivBack;
    @ViewInject(R.id.tv_register_login)
    private TextView tvLogin;
    @ViewInject(R.id.et_register_phone)
    private EditText etPhone;
    @ViewInject(R.id.iv_register_cancel)
    private ImageView ivCancel;
    @ViewInject(R.id.et_input_code)
    private EditText etCode;
    @ViewInject(R.id.tv_get_code)
    private TextView tvCode;
    @ViewInject(R.id.cb_register_psw)
    private CheckBox cbPsw;
    @ViewInject(R.id.et_register_psw)
    private EditText etPsw;
    @ViewInject(R.id.btn_register_sure)
    private Button btnSure;

     public static Activity instance;


    private String type;

    private static final int CODE=0x0101;
    private int time;

    private static final int MSG_SET_ALIAS = 1002;
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
                    //ToastUtils.showCenter(getApplicationContext(),"codeeeee");
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
            ExampleUtil.showToast(logs, getApplicationContext());
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
          instance=this;
        Intent intent = this.getIntent();
        if(intent==null)return;
        String title = intent.getStringExtra("title");
        this.type = getIntent().getStringExtra("type");
        initView();
    }
    /**
     * 开的倒计时
     */
    private void startTaskTimer(){
        time=60;
        tvCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }
    private void initCode() {
      /*  layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        //获取验证码
        try{
            byte[] mBytes=null;
            String phone = etPhone.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不能为空");
                return;
            }
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            startTaskTimer();
            String string="{'userPhone':'"+phone+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString=AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.MARATHON_REGISTER);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("result", "onSuccess: "+result);
                    Gson gson=new Gson();
                    RegisterCodeBean registerCodeBean = gson.fromJson(result, RegisterCodeBean.class);
                    boolean state = registerCodeBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),registerCodeBean.getMessage());
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),registerCodeBean.getMessage());
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(RegisterOneActivity.this,"网络不给力,连接服务器异常!");
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                    //hud.dismiss();
                    //layoutLoading.setVisibility(View.GONE);
                }
            });
        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        tvCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        //取消按钮的隐藏
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
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

                /*if(s.toString().trim().length()>0){

                    ivCancel.setVisibility(View.VISIBLE);
                }else {

                    ivCancel.setVisibility(View.GONE);
                }

                if(s.toString().length()==11){

                    btnSure.setEnabled(true);
                }else {

                    btnSure.setEnabled(false);
                }*/

            }
        });
        //密码的显示和隐藏
        cbPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //显示密码
                    etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().length());
                }else {
                    //隐藏密码
                    etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().length());
                }
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_register_one;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_back:
                //跳转到登录
                finish();
                break;
            case R.id.tv_register_login:
                //跳转到登录
                finish();
                break;
            case R.id.iv_register_cancel:
                //意见清空
                etPhone.setText("");
                break;
            case R.id.tv_get_code:
                //获取验证码
                //ToastUtils.show(getApplicationContext(),"get code");
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initCode();
                }else {
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterOneActivity.this);
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
            case R.id.btn_register_sure:
                //确定
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initRegister();
                }else {
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterOneActivity.this);
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

    private void initRegister() {
        try{
            byte[] mBytes=null;
            String phone = etPhone.getText().toString().trim();
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            String code = etCode.getText().toString().trim();
            String psw = etPsw.getText().toString().trim();
            if(TextUtils.isEmpty(code)){
                ToastUtils.showCenter(getApplicationContext(),"验证码不能为空");
                return;
            }
            if(TextUtils.isEmpty(psw)){
                ToastUtils.showCenter(getApplicationContext(),"密码不能为空");
                return;
            }
            if(psw.length()<6){
                ToastUtils.showCenter(getApplicationContext(),"密码长度不能少于6");
                return;
            }
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            String string="{'userPhone':'"+phone+"','password':'"+psw+"','appKey': 'BJYDAppV-2','validCode':'"+code+"'}";
            mBytes=string.getBytes("UTF8");
            String enString=AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.VALIDATE_CODE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("register", "onSuccess: "+result);
                    Gson gson=new Gson();
                    RegisterSureBean registerSureBean = gson.fromJson(result, RegisterSureBean.class);
                    boolean state = registerSureBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),registerSureBean.getMessage());
                        finish();
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),registerSureBean.getMessage());
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(RegisterOneActivity.this,"网络不给力,连接服务器异常!");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    hud.dismiss();
                    //layoutLoading.setVisibility(View.GONE);

                }
            });
            }catch(Exception e){
                Log.d("error", "initData: "+e.getMessage());
            }

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


}
