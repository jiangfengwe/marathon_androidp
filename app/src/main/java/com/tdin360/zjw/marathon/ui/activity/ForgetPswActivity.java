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

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ForgetPswBean;
import com.tdin360.zjw.marathon.model.ForgetPswCodeBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;

/**
 *  忘记密码
 *
 */

public class ForgetPswActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.iv_forget_psw_back)
    private ImageView ivBack;
    @ViewInject(R.id.et_forget_psw_phone)
    private EditText etPhone;
    @ViewInject(R.id.et_forget_psw_input_code)
    private EditText etCode;
    @ViewInject(R.id.tv_forget_psw_get_code)
    private TextView tvCode;
    @ViewInject(R.id.et_forget_psw)
    private EditText etPsw;
    @ViewInject(R.id.btn_forget_psw_sure)
    private Button btnSure;
    @ViewInject(R.id.cb_forget_psw)
    private CheckBox cbPsw;
    @ViewInject(R.id.iv_forget_psw_cancel)
    private ImageView ivCancel;

    private static final int CODE=0x0102;
    private int time;


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
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        //取消图片的显示与隐藏
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etPhone.getEditableText() + "";
                if(!TextUtils.isEmpty(s)){
                    ivCancel.setVisibility(View.VISIBLE);
                }else{
                    ivCancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        return R.layout.activity_forget_psw;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_forget_psw_back:
                //返回
                finish();
                break;
            case R.id.iv_forget_psw_cancel:
                //一键清空
                etPhone.setText("");
                break;
            case R.id.tv_forget_psw_get_code:
                //获取验证码
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
            case R.id.btn_forget_psw_sure:
                //确定
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initSure();
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

    private void initSure() {
        try {
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
            String string="{'userPhone':'"+phone+"','validCode':'"+code+"','newPassword':'"+psw+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString=AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.FIND_PASSWORD);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("forget", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ForgetPswBean forgetPswBean = gson.fromJson(result, ForgetPswBean.class);
                    boolean state = forgetPswBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),forgetPswBean.getMessage());
                        finish();
                    }else {
                        ToastUtils.showCenter(getApplicationContext(),forgetPswBean.getMessage());
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
    /**
     * 开的倒计时
     */
    private void startTaskTimer(){
        time=60;
        tvCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }
    private void initCode() {
        try {
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
            RequestParams params=new RequestParams(HttpUrlUtils.FIND_PASSWORD_CODE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("feedbackcode", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ForgetPswCodeBean forgetPswCodeBean = gson.fromJson(result, ForgetPswCodeBean.class);
                    boolean state = forgetPswCodeBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),forgetPswCodeBean.getMessage());
                    }else {
                        ToastUtils.showCenter(getApplicationContext(),forgetPswCodeBean.getMessage());
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
                    //layoutLoading.setVisibility(View.GONE);
                    //hud.dismiss();

                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
