package com.tdin360.zjw.marathon.ui.activity;

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
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ChangePhoneBean;
import com.tdin360.zjw.marathon.model.ChangePhoneCodeBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 修改手机号
 */

public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener {
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

    @ViewInject(R.id.et_change_phone_old)
    private EditText etOld;
    @ViewInject(R.id.et_change_old_phone_input_code)
    private EditText etCode;
    @ViewInject(R.id.tv_change_old_phone_get_code)
    private TextView tvCode;
    @ViewInject(R.id.btn_change_phone_sure)
    private Button btnSure;
    @ViewInject(R.id.iv_change_phone_old_cancel)
    private ImageView ivCancelOld;

    private static final int CODE=0x0100;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }
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
    private void initView() {
        tvCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        ivCancelOld.setOnClickListener(this);
        //取消图片的显示与隐藏
        etOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etOld.getEditableText() + "";
                if(!TextUtils.isEmpty(name)){
                    ivCancelOld.setVisibility(View.VISIBLE);
                }else{
                    ivCancelOld.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



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
        titleTv.setText("修改手机号");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_old_phone_get_code:
                //原手机发送验证码
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initOldCode();
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
            case R.id.btn_change_phone_sure:
                //确定更改手机号
                //判断网络是否处于可用状态
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
            case R.id.iv_change_phone_old_cancel:
                //清空旧号码
                etOld.setText("");
                break;

        }

    }
    private void initData() {
        try{
            byte[] mBytes=null;
            String validCode = etCode.getText().toString().trim();
            String oldPhone = etOld.getText().toString().trim();
            if(TextUtils.isEmpty(oldPhone)){
                ToastUtils.showCenter(getApplicationContext(),"号码不能为空");
                return;
            }
            if(!isMobileNO(oldPhone)){
                ToastUtils.showCenter(getApplicationContext(),"号码不符合规则");
                return;
            }
            if(TextUtils.isEmpty(validCode)){
                ToastUtils.showCenter(getApplicationContext(),"验证码不能为空");
                return;
            }
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            String string="{'userOldPhone':'"+oldPhone+"','validCode':'"+validCode+"','appKey':'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_PHONE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("phonecode", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ChangePhoneBean changePhoneBean = gson.fromJson(result, ChangePhoneBean.class);
                    boolean state = changePhoneBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),changePhoneBean.getMessage());
                        Intent intent=new Intent(ChangePhoneActivity.this,ChangePhoneNewActivity.class);
                        startActivity(intent);
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),changePhoneBean.getMessage());
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
                    hud.dismiss();

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
    /**
     * 开的倒计时
     */
    private void startTaskTimer(){
        time=60;
        tvCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }

    private void initOldCode() {
        //原手机验证码
        try{
            byte[] mBytes=null;
            String oldPhone = etOld.getText().toString().trim();
            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            String customerId = loginInfo.getId()+"";
            if(TextUtils.isEmpty(oldPhone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不能为空");
                return;
            }
            if(!isMobileNO(oldPhone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            startTaskTimer();
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            String string="{'userOldPhone':"+oldPhone+",'customerId':"+customerId+",'appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_OLD_PHONE_CODE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("phone", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ChangePhoneCodeBean changePhoneCodeBean = gson.fromJson(result, ChangePhoneCodeBean.class);
                    boolean state = changePhoneCodeBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),changePhoneCodeBean.getMessage());
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),changePhoneCodeBean.getMessage());
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
                    hud.dismiss();

                }
            });
        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }

    }
}
