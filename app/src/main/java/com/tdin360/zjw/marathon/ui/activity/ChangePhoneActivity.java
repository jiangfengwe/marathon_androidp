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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * 修改手机号
 */

public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.et_change_phone_old)
    private EditText etOld;
    @ViewInject(R.id.et_change_phone_new)
    private EditText etNew;
    @ViewInject(R.id.et_change_phone_input_code)
    private EditText etCode;
    @ViewInject(R.id.tv_change_phone_get_code)
    private TextView tvCode;
    @ViewInject(R.id.btn_change_phone_sure)
    private Button btnSure;
    @ViewInject(R.id.iv_change_phone_old_cancel)
    private ImageView ivCancelOld;
    @ViewInject(R.id.iv_change_phone_new_cancel)
    private ImageView ivCancelOldNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
        tvCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        ivCancelOld.setOnClickListener(this);
        ivCancelOldNew.setOnClickListener(this);
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
        etNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etNew.getEditableText() + "";
                if(!TextUtils.isEmpty(name)){
                    ivCancelOldNew.setVisibility(View.VISIBLE);
                }else{
                    ivCancelOldNew.setVisibility(View.GONE);
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
            case R.id.tv_change_phone_get_code:
                //发送验证码
                ToastUtils.show(getApplicationContext(),"发送验证码");
                break;
            case R.id.btn_change_phone_sure:
                //确定更改
                ToastUtils.show(getApplicationContext(),"确定更改");
                break;
            case R.id.iv_change_phone_old_cancel:
                //清空旧密码
                etOld.setText("");
                break;
            case R.id.et_change_phone_new:
                //清空新密码
                etNew.setText("");
                break;
        }

    }
}
