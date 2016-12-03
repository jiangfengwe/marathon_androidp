package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.SendSMSUtils;

/**
 * 用户手机号注册获取验证码验证
 */
public class RegisterActivity extends BaseActivity {

    private int totalTime=10;
    private CheckBox button;
    private EditText editTextTel;
    private EditText editTextCode;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private SendSMSUtils smsUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.smsUtils = SendSMSUtils.instance(this);
        this.editTextTel= (EditText) this.findViewById(R.id.tel);
        this.editTextCode= (EditText) this.findViewById(R.id.code);
        this.editTextPass1= (EditText) this.findViewById(R.id.password1);
        this.editTextPass2= (EditText) this.findViewById(R.id.password2);

    }

    //返回
    public void back(View view) {
        exit();
    }


    //获取验证码
    public void getCode(View view) {
        button=(CheckBox) view;

        String tel = this.editTextTel.getText().toString();

        //验证手机号码是否符合规范
        if(tel.length()<11){

            Toast.makeText(this,"手机号有误,请正确填写手机号!",Toast.LENGTH_SHORT).show();
            editTextTel.requestFocus();
            return;
        }
        button.setEnabled(false);
        button.setChecked(true);
        handler.sendEmptyMessage(0);



         smsUtils.sendSMSCode(tel,"1");


    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 0://处理获取验证码倒计时

                   handler.sendEmptyMessageDelayed(0,1000);
                    if(totalTime>0){
                        totalTime--;
                        button.setText(totalTime+"秒后重新获取");
                    }else {
                        handler.removeMessages(0);
                        button.setText("获取验证码");
                        button.setEnabled(true);
                        button.setChecked(false);
                        totalTime=10;
                    }

                    break;
            }
        }
    };

    //验证验证码
    public void submit(View view) {

//        获取用户输入的验证码
        String inCode=editTextCode.getText().toString();



        if (inCode.length()==0){

            Toast.makeText(this,"验证码不能为空!",Toast.LENGTH_SHORT).show();
            editTextCode.requestFocus();
            return;
        }

        //获取服务器的验证码
        String code = smsUtils.getCurrentCode();

        if (inCode.equals(code)){
            Toast.makeText(this,"验证码正确!",Toast.LENGTH_SHORT).show();




            //验证用户输入密码

            String pass1  =this.editTextPass1.getText().toString();
            String pass2 = this.editTextPass2.getText().toString();

            if(pass1.trim().length()<6){

                Toast.makeText(this,"密码长度必须为六位数!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

            if(pass2.trim().length()==0){

                Toast.makeText(this,"确认密码不能为空!",Toast.LENGTH_SHORT).show();
                this.editTextPass2.requestFocus();
                return;
            }

            if (!pass1.trim().equals(pass2.trim())){

                Toast.makeText(this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

           //向服务器提交注册数据

           //注册成功
            SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);

            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putString("name",editTextTel.getText().toString().trim());
            edit.putString("pass",pass1);
            edit.commit();
            Toast.makeText(this,"注册成功!",Toast.LENGTH_SHORT).show();
            Intent intent  =new Intent(this,LoginActivity.class);
            startActivity(intent);


        }else {


            Toast.makeText(this,"验证码错误!",Toast.LENGTH_SHORT).show();
        }




    }
}
