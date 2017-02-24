package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;
import com.tdin360.zjw.marathon.utils.FastBlurUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyProgressDialogUtils;
import com.tdin360.zjw.marathon.utils.SendSMSUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ValidateUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/***
 *
 *  修改登录密码
 */


public class ChangePasswordActivity extends BaseActivity {


    private EditText editTextOldPass;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("修改登录密码");
        showBackButton();

        this.editTextOldPass = (EditText) this.findViewById(R.id.oldPassword);
        this.editTextPass1= (EditText) this.findViewById(R.id.password1);
        this.editTextPass2= (EditText) this.findViewById(R.id.password2);

         initBlur();
    }

    //处理背景毛玻璃效果
    private void initBlur(){

        this.bg = (ImageView) this.findViewById(R.id.bg);
        this. bg.setImageBitmap(FastBlurUtils.getBlurBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.login_register_bg)));
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
        MyProgressDialogUtils.getUtils(this).showDialog("提交中...");
        RequestParams params = new RequestParams(HttpUrlUtils.CHANGE_PASSWORD);
        params.addQueryStringParameter("phone",SharedPreferencesManager.getLoginInfo(this).getName());
        params.addQueryStringParameter("oldPassword",oldPass);
        params.addQueryStringParameter("newPassword",pass2);
          
             x.http().post(params, new Callback.CommonCallback<String>() {
                 @Override
                 public void onSuccess(String s) {

                     try {
                         JSONObject  json = new JSONObject(s);

                         Log.d("=====更改登录密码======>", "onSuccess: "+s);
                         boolean success = json.getBoolean("Success");
                         String reason = json.getString("Reason");
                         Toast.makeText(ChangePasswordActivity.this,reason,Toast.LENGTH_SHORT).show();
                         if(success){
                             //修改成功后清空原有的登录信息并跳转到登录界面
                             SharedPreferencesManager.clearLogin(ChangePasswordActivity.this);
                             //更新登录信息
                             Intent i = new Intent(Personal_CenterFragment.ACTION);
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
                     MyProgressDialogUtils.getUtils(ChangePasswordActivity.this).closeDialog();
                 }
             });



    }


}
