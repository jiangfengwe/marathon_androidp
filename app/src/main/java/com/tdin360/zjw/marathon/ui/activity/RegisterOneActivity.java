package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ValidateUtils;

import org.xutils.view.annotation.ViewInject;


/**
 * 注册第一步
 * 输入手机号获取验证码
 * @author zhangzhijun
 */
public class RegisterOneActivity extends BaseActivity {

    @ViewInject(R.id.phone)
    private EditText editTextPhone;
    @ViewInject(R.id.textCount)
    private TextView textCount;
    @ViewInject(R.id.clear)
    private ImageView clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("注册");
        showBackButton();

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textCount.setText(s.length()+"/11");
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().trim().length()>0){

                  clear.setVisibility(View.VISIBLE);
                }else {

                  clear.setVisibility(View.GONE);
                }

            }
        });

        /**
         * 一键清空
         */
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPhone.setText(null);
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_register_one;

    }


    /**
     * 下一步
     * @param view
     */
    public void nextClick(View view) {

        String phone = editTextPhone.getText().toString().trim();

        if(phone.length()==0){

            showTip("手机号码不能为空!");

            return;
        }

        if(!ValidateUtils.isMobileNO(phone)){

           showTip("手机号码格式错误!");

            return;
        }

        /**
         *  跳转到下一步
         */

        Intent intent = new Intent(RegisterOneActivity.this,GetCodeActivity.class);
         intent.putExtra("phone",phone);
         startActivity(intent);

    }

    /**
     * 显示提示内容
     * @param msg
     */
    private void showTip(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
}
