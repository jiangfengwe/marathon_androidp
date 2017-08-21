package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.finalteam.rxgalleryfinal.ui.fragment.MediaGridFragment;

/**
 * 设置密码
 */
public class SettingPasswordActivity extends BaseActivity {


    @ViewInject(R.id.password1)
    private EditText password1;
    @ViewInject(R.id.password2)
    private EditText password2;
    @ViewInject(R.id.showPass1)
    private CheckBox showPass1;
    @ViewInject(R.id.showPass2)
    private CheckBox showPass2;
    private String phone;
    private String code;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         showBackButton();
        setToolBarTitle("设置密码");

        Intent intent = getIntent();
        if(intent!=null){

            this.phone = intent.getStringExtra("phone");
            this.code=intent.getStringExtra("code");
            this.type=intent.getStringExtra("type");
        }

        this.showPass1.setOnCheckedChangeListener(new MyCheckBoxListener());
        this.showPass2.setOnCheckedChangeListener(new MyCheckBoxListener());
    }


    /**
     * 显示和隐藏密码
     */

    class MyCheckBoxListener implements CompoundButton.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){


                case R.id.showPass1:


                        if(isChecked){

                            //显示密码
                            password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password1.setSelection(password1.getText().length());
                        }else {
                            //隐藏密码

                            password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password1.setSelection(password1.getText().length());
                        }

                    break;
                case R.id.showPass2:
                    if(isChecked){

                        //显示密码
                        password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password2.setSelection(password2.getText().length());
                    }else {
                        //隐藏密码

                        password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password2.setSelection(password2.getText().length());
                    }

                    break;

            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting_password;
    }


    /**
     * 提交
     * @param view
     */
    public void submit(View view) {



        if(inputValidate()){

            String newPass = password2.getText().toString().trim();

            /**
       * 向服务器提交数据
        */


            //显示提示框
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();

            RequestParams params = new RequestParams(HttpUrlUtils.SET_PASSWORD);
            params.addBodyParameter("phone",phone);
            params.addBodyParameter("validCode",code);//验证码
            params.addBodyParameter("password",newPass);
            params.addBodyParameter("type",type);
            params.addBodyParameter("appKey",HttpUrlUtils.appKey);
            params.setConnectTimeout(5*1000);
            params.setMaxRetryCount(0);
            if(type.equals("zc")) {
                params.addBodyParameter("registerSource", "来自Android客户端");
            }


            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {

                    try {
                        JSONObject json = new JSONObject(s);

                        boolean success = json.getBoolean("Success");
                        String reason = json.getString("Reason");

                        if(success){

                            if(type.equals("zc")){

                               ToastUtils.showCenter(getBaseContext(),"注册成功");
                            }else {

                                ToastUtils.showCenter(getBaseContext(),"重置密码成功");
                            }

                            //清除登录信息
                            SharedPreferencesManager.clearLogin(SettingPasswordActivity.this);
                            //找回密码成功后跳转到登录界面
                            finishActivity();
                        }else {

                            ToastUtils.showCenter(getBaseContext(),reason);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onError(Throwable throwable, boolean b) {
                    ToastUtils.showCenter(getBaseContext(),"网络错误或无法访问服务器!");
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                    hud.dismiss();
                }
            });


        }

    }

    /**
     * 销毁输入手机号和获取验证码的界面
     */
    private void finishActivity(){

        RegisterOneActivity.instance.finish();
        GetCodeActivity.instance.finish();
        finish();


    }


    /**
     * 输入验证
     * @return
     */
    private boolean inputValidate(){

        String pass1 = password1.getText().toString().trim();
        String pass2 = password2.getText().toString().trim();

        if(pass1.length()==0){

            password1.requestFocus();

           ToastUtils.showCenter(getBaseContext(),"密码不能为空!");
            return false;

        }
        if(pass1.length()<6){

            password1.requestFocus();

            ToastUtils.showCenter(getBaseContext(),"密码长度至少是六位数!");
            return false;

        }

        if(pass2.length()==0){

          password2.requestFocus();
          ToastUtils.showCenter(getBaseContext(),"确认密码不能为空!");

            return false;
        }

        if(!pass1.equals(pass2)){

            ToastUtils.showCenter(getBaseContext(),"两次输入的密码不一致!");
            return false;


        }

        return true;
    }



}
