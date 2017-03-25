package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
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

    private CheckBox checkBox;
    private ImageView clearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setToolBarTitle("登录");
          showBackButton();
        this.editTextName= (EditText) this.findViewById(R.id.tel);
        this.editTextPass= (EditText) this.findViewById(R.id.password);
        this.checkBox = (CheckBox) this.findViewById(R.id.checkbox);
        this.clearBtn = (ImageView) this.findViewById(R.id.clear);

        /**
         * 清空手机号
         */
        this.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextName.setText(null);
            }
        });


        /**
         * 监听输入框值的变化
         */
        this.editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0){
                    clearBtn.setVisibility(View.VISIBLE);
                }else {
                    clearBtn.setVisibility(View.INVISIBLE);
                }
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    //显示密码
                    editTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editTextPass.setSelection(editTextPass.getText().length());
                }else {
                    //隐藏密码

                    editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextPass.setSelection(editTextPass.getText().length());
                }
            }
        });
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
                startActivityForResult(intent,500);
                break;
            case R.id.forGet://忘记密码

                intent = new Intent(LoginActivity.this,RestPassWordActivity.class);
                startActivity(intent);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==200){

            finish();
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

     //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        //提交到服务器验证
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_LOGIN);
        params.addQueryStringParameter("phone",tel);
        params.addQueryStringParameter("password",pass);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    String avatarUrl = json.getString("AvatarUrl");

                    //报名成功跳转到登录界面
                    if(success){

                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(LoginActivity.this,new LoginModel(tel,pass,avatarUrl));
                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);

                        hud.dismiss();
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

                hud.dismiss();

            }
        });


    }


}
