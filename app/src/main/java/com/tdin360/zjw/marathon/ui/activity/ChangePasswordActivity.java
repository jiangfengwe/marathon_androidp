package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ChangePswBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

/***
 *
 *  修改登录密码
 */


public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener{
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

    @ViewInject(R.id.et_change_psw_old)
    private EditText etOld;
    @ViewInject(R.id.et_change_psw_new)
    private EditText etNew;
    @ViewInject(R.id.iv_change_psw_cancel)
    private ImageView ivCancel;
    @ViewInject(R.id.cb_change_psw)
    private CheckBox cbPsw;
    @ViewInject(R.id.btn_change_psw_sure)
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }
    private void initView() {
        ivCancel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        //取消图片的显示与隐藏
        etOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etOld.getEditableText() + "";
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
        cbPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //显示密码
                    etNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etNew.setSelection(etNew.getText().length());
                }else {
                    //隐藏密码
                    etNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etNew.setSelection(etNew.getText().length());
                }
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
        titleTv.setText("修改密码");
    }
    @Override
    public int getLayout() {
        return R.layout.activity_change_password;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_change_psw_cancel:
                //一键清空
                etOld.setText("");
                break;
            case R.id.btn_change_psw_sure:
                //确定
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
    private void initData() {

        try{
            byte[] mBytes=null;
            String newPsw = etNew.getText().toString().trim();
            String oldPsw = etOld.getText().toString().trim();
            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            String customerId = loginInfo.getId()+"";
            if(TextUtils.isEmpty(newPsw)){
                ToastUtils.showCenter(getApplicationContext(),"新密码不能为空");
                return;
            }
            if(TextUtils.isEmpty(oldPsw)){
                ToastUtils.showCenter(getApplicationContext(),"原密码不能为空");
                return;
            }
            if(newPsw.length()<6){
                ToastUtils.showCenter(getApplicationContext(),"密码长度应大于6");
                return;
            }
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            /*layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();*/
            String string="{'customerId':'"+customerId+"','newPassword':'"+newPsw+"','oldPassword':'"+oldPsw+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_PSW);
            params.addBodyParameter("secretMessage",enString);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("psw", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ChangePswBean changePswBean = gson.fromJson(result, ChangePswBean.class);
                    boolean state = changePswBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),changePswBean.getMessage());
                        finish();
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),changePswBean.getMessage());
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
   /* *//**
     * 控制显示或者隐藏密码的方法
     *//*
    private void showOrHidePassword(){
        this.checkBox1.setOnCheckedChangeListener(new MyCheckBoxListener());
        this.checkBox2.setOnCheckedChangeListener(new MyCheckBoxListener());
        this.checkBox3.setOnCheckedChangeListener(new MyCheckBoxListener());
    }
    *//**
     * 控制密码的显示与隐藏
     *//*

    //提交
    public void submit(View view) {


        //验证原密码输入
             String oldPass = editTextOldPass.getText().toString().trim();

             if(oldPass.length()<6){

                 Toast.makeText(this,"密码长度不能少小于六位数!",Toast.LENGTH_SHORT).show();
                 this.editTextOldPass.requestFocus();
                 return;
             }

            //验证用户输入

            String pass1  =this.editTextPass1.getText().toString().trim();//原始密码
            String pass2 = this.editTextPass2.getText().toString().trim();//新密码

            if(pass1.length()<6){

                Toast.makeText(this,"密码长度不能少小于六位数!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

            if(pass2.length()==0){

                Toast.makeText(this,"确认密码不能为空!",Toast.LENGTH_SHORT).show();
                this.editTextPass2.requestFocus();
                return;
            }

            if (!pass1.trim().equals(pass2.trim())){

                Toast.makeText(this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

            //验证成功
        //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        RequestParams params = new RequestParams(HttpUrlUtils.CHANGE_PASSWORD);
        params.addQueryStringParameter("phone",SharedPreferencesManager.getLoginInfo(this).getName());
        params.addQueryStringParameter("oldPassword",oldPass);
        params.addQueryStringParameter("newPassword",pass2);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
          
             x.http().post(params, new Callback.CommonCallback<String>() {
                 @Override
                 public void onSuccess(String s) {

                     try {
                         JSONObject  json = new JSONObject(s);

                         boolean success = json.getBoolean("Success");
                         String reason = json.getString("Reason");
                         Toast.makeText(ChangePasswordActivity.this,reason,Toast.LENGTH_SHORT).show();
                         if(success){
                             //修改成功后清空原有的登录信息并跳转到登录界面
                             SharedPreferencesManager.clearLogin(ChangePasswordActivity.this);
                             //更新登录信息
                             Intent i = new Intent(MyFragment.ACTION);
                             sendBroadcast(i);
                             finish();
                             //跳转到登录界面
                             Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                             startActivity(intent);

                         }



                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }

                 @Override
                 public void onError(Throwable throwable, boolean b) {
                     Toast.makeText(ChangePasswordActivity.this,"网络错误或无法访问服务器!",Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onCancelled(CancelledException e) {

                 }

                 @Override
                 public void onFinished() {

                     hud.dismiss();
                 }
             });



    }*/


}
