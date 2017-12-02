package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

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
    /*private EditText editTextOldPass;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private CheckBox checkBox1,checkBox2,checkBox3;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

      /*  setToolBarTitle("修改登录密码");
        showBackButton();
        this.editTextOldPass = (EditText) this.findViewById(R.id.oldPassword);
        this.editTextPass1= (EditText) this.findViewById(R.id.password1);
        this.editTextPass2= (EditText) this.findViewById(R.id.password2);
        //初始化控制密码显示以隐藏的checkbox
        this.checkBox1 = (CheckBox) this.findViewById(R.id.showPass1);
        this.checkBox2 = (CheckBox) this.findViewById(R.id.showPass2);
        this.checkBox3 = (CheckBox) this.findViewById(R.id.showPass3);

          showOrHidePassword();*/
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
                break;
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
    private class MyCheckBoxListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()){

                case R.id.showPass1:
                    if(isChecked){

                        //显示密码
                        editTextOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editTextOldPass.setSelection(editTextOldPass.getText().length());
                    }else {
                        //隐藏密码

                        editTextOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editTextOldPass.setSelection(editTextOldPass.getText().length());
                    }

                    break;
                case R.id.showPass2:
                    if(isChecked){

                        editTextPass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editTextPass1.setSelection(editTextPass1.getText().length());
                    }else {

                        editTextPass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editTextPass1.setSelection(editTextPass1.getText().length());
                    }
                    break;

                case R.id.showPass3:
                    if(isChecked){

                        editTextPass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editTextPass2.setSelection(editTextPass2.getText().length());
                    }else {

                        editTextPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editTextPass2.setSelection(editTextPass2.getText().length());
                    }
                    break;
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_change_password;
    }


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
