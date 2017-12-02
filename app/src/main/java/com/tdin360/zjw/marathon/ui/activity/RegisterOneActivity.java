package com.tdin360.zjw.marathon.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 输入手机号获取验证码
 * @ahthor zzj
 * @time 17/7/30 上午9:32
 */
public class RegisterOneActivity extends BaseActivity implements View.OnClickListener{
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
                ToastUtils.show(getApplicationContext(),"get code");
                break;
            case R.id.btn_register_sure:
                //确定
                break;

        }

    }


    /**
     * 下一步
     * @param view
     */
   /* public void nextClick(View view) {

        final String phone = etPhone.getText().toString().trim();

        if(phone.length()==0){

            ToastUtils.showCenter(getBaseContext(),"手机号码不能为空!");

            return;
        }

        if(!ValidateUtils.isMobileNO(phone)){

           ToastUtils.showCenter(getBaseContext(),"手机号码格式错误!");

            return;
        }

        nextBtn.setEnabled(false);

        //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setDetailsLabel("请稍后")
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();



        *//**
         * 验证手机号并发送验证码
         *//*
        RequestParams params = new RequestParams(HttpUrlUtils.REGISTER_ONE);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("type",type);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

//                Log.d("------------>", "onSuccess: "+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    boolean success = obj.getBoolean("Success");
                    String reason = obj.getString("Reason");
                    if(success){


                        *//**
                         *  验证成功进行注册第二步
                         *//*

                        String type = getIntent().getStringExtra("type");
                        Intent intent = new Intent(RegisterOneActivity.this,GetCodeActivity.class);
                        intent.putExtra("phone",phone);
                        intent.putExtra("type",type);
                        startActivity(intent);

                    }else {



                        ToastUtils.showCenter(getBaseContext(),reason);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                nextBtn.setEnabled(true);
                ToastUtils.showCenter(getBaseContext(),"网络连接错误或服务器错误!");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();
                nextBtn.setEnabled(true);
            }
        });



    }*/

}
