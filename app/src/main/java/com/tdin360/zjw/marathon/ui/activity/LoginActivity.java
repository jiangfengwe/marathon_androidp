package com.tdin360.zjw.marathon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.UserModel;
import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;
import com.tdin360.zjw.marathon.utils.FastBlurUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/***
 *
 *
 * 用户登录
 *
 */
public class LoginActivity extends BaseActivity {

    private EditText editTextName,editTextPass;
    private ImageView bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setToolBarTitle("登录");
          showBackButton();
        this.editTextName= (EditText) this.findViewById(R.id.tel);
        this.editTextPass= (EditText) this.findViewById(R.id.password);
        initBlur();
    }

    //处理背景毛玻璃效果
    private void initBlur(){

        this.bg = (ImageView) this.findViewById(R.id.bg);
        this. bg.setImageBitmap(FastBlurUtils.getBlurBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.login_register_bg)));
    }
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    public void loginClick(View view) {

        Intent intent;
        switch (view.getId()){

            case R.id.loginBtn://登录

               this.login();

                break;
            case R.id.registerBtn://注册

                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forGet://忘记密码

                intent = new Intent(LoginActivity.this,RestPassWordActivity.class);
                startActivity(intent);

                break;
        }
    }

    //用户登录
    private void login(){

    //验证用户输入

        final String tel = this.editTextName.getText().toString().trim();
        final String pass  =this.editTextPass.getText().toString().trim();

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

        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_LOGIN);
        params.addQueryStringParameter("phone",tel);
        params.addQueryStringParameter("password",pass);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {


                try {
                    JSONObject json = new JSONObject(s);

                    boolean success = json.getBoolean("Success");
                    String reason = json.getString("Reason");

                    //报名成功跳转到登录界面
                    if(success){

                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(LoginActivity.this,new LoginModel(tel,pass));

                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(Personal_CenterFragment.ACTION);
                        sendBroadcast(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this,reason,Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(LoginActivity.this, "网络错误或访问服务器失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


}
