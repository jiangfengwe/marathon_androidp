package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;

/***
 *
 *
 * 用户登录
 *
 */
public class LoginActivity extends Activity {

    private EditText editTextName,editTextPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.editTextName= (EditText) this.findViewById(R.id.tel);
        this.editTextPass= (EditText) this.findViewById(R.id.password);
    }
    public void back(View view) {

           finish();
    }
    public void loginClick(View view) {


        switch (view.getId()){

            case R.id.loginBtn://登录

               this.login();

                break;
            case R.id.registerBtn://注册

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forGet://忘记密码



                break;
        }
    }

    //用户登录
    private void login(){

    //验证用户输入

        String tel = this.editTextName.getText().toString().trim();
        String pass  =this.editTextPass.getText().toString().trim();

        if(tel.length()<11){

            Toast.makeText(this,"手机号输入有误!",Toast.LENGTH_SHORT).show();
            editTextName.requestFocus();
            return;
        }

        if(pass.length()==0){

            Toast.makeText(this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            editTextPass.requestFocus();
            return;
        }

        //提交到服务器验证

        SharedPreferences sharedPreferences = this.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

        String userName = sharedPreferences.getString("name", "");

        String pass1 = sharedPreferences.getString("pass", "");

        //判断登录成功
        if(userName.equals(tel)&&pass.equals(pass1)){


            Toast.makeText(this,"登录成功!",Toast.LENGTH_SHORT).show();

            finish();
        }else {

            Toast.makeText(this,"帐号或密码错误!",Toast.LENGTH_SHORT).show();

        }


    }
}
