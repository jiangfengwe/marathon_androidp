package com.tdin360.zjw.marathon.ui.activity;

import android.support.v7.app.AppCompatActivity;
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

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

/**
 *  忘记密码
 *
 */

public class ForgetPswActivity extends BaseActivity implements View.OnClickListener {
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
                break;
            case R.id.btn_forget_psw_sure:
                //确定
                break;
        }

    }
}
