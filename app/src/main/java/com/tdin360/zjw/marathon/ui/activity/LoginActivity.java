package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.iv_login_back)
    private ImageView ivBack;
    @ViewInject(R.id.tv_login_register)
    private TextView tvRegester;
    @ViewInject(R.id.et_login_phone)
    private EditText etPhone;
    @ViewInject(R.id.iv_login_cancel)
    private ImageView ivCancel;
    @ViewInject(R.id.et_login_psw)
    private EditText etPsw;
    @ViewInject(R.id.iv_login_psw_show)
    private ImageView ivPsw;
    @ViewInject(R.id.tv_login_forget)
    private TextView tvForget;
    @ViewInject(R.id.btn_login_sure)
    private Button btnSure;
    @ViewInject(R.id.layout_login_weixin)
    private LinearLayout layoutWeixin;
    private boolean flag;
    @ViewInject(R.id.cb_login_psw)
    private CheckBox cbPsw;


   /* @ViewInject(R.id.tel)
    private EditText editTextName;
    @ViewInject(R.id.password)
    private EditText editTextPass;
    @ViewInject(R.id.clear)
    private ImageView clearBtn;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        /**
         * 监听输入框值的变化
         */
       /* this.editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().trim().length()>0){
                    clearBtn.setVisibility(View.VISIBLE);
                }else {
                    clearBtn.setVisibility(View.INVISIBLE);
                }
            }
        });*/


        /**
         * 显示隐藏密码
         */

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked){
//
//                    //显示密码
//                    editTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    editTextPass.setSelection(editTextPass.getText().length());
//                }else {
//                    //隐藏密码
//
//                    editTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    editTextPass.setSelection(editTextPass.getText().length());
//                }
//            }
//        });
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvRegester.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        ivPsw.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        layoutWeixin.setOnClickListener(this);
        //监听输入框值的变化
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
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
        return R.layout.activity_login;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.iv_login_back:
                //返回
                finish();
                break;
            case R.id.tv_login_register:
                //注册
                intent = new Intent(LoginActivity.this,RegisterOneActivity.class);
                intent.putExtra("type","zc");
                intent.putExtra("title","注册");
                startActivity(intent);
                break;
            case R.id.iv_login_cancel:
                //清空手机号
                etPhone.setText("");
                break;
            case R.id.tv_login_forget:
                //忘记密码
                intent = new Intent(LoginActivity.this,ForgetPswActivity.class);
                intent.putExtra("type","zhmm");
                intent.putExtra("title","找回密码");
                startActivity(intent);
                break;
            case R.id.btn_login_sure:
                //登录
                //this.login();
                break;
            case R.id.layout_login_weixin:
                //微信登录
                //this.login();

                break;
        }
    }

    //用户登录
    /*private void login(){

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

                Log.d("-----登录--->", "onSuccess: "+s);
                try {
                    JSONObject json = new JSONObject(s);

                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    String avatarUrl = json.getString("AvatarUrl");

                    String customerId = json.getString("CustomerId");
                    //报名成功跳转到登录界面
                    if(success){

                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(LoginActivity.this,new LoginModel(customerId,tel,pass,avatarUrl));
                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);
                        hud.dismiss();

                        //登录成功后根据指定目标跳转到指定界面
                        switch (LoginNavigationConfig.instance().getNavType()){

                            case SignUp://跳转到报名界面
                                intent = new Intent(LoginActivity.this,SignUpActivity.class);
                                startActivity(intent);
                            break;
                            case MySignUp://跳转到我的报名界面
                                intent = new Intent(LoginActivity.this,MySignUpListActivity.class);
                                startActivity(intent);
                                break;
                            case MyMark://跳转到我的成绩界面
                                intent = new Intent(LoginActivity.this,MyAchievementListActivity.class);
                                startActivity(intent);
                                break;
                            case MyFeed://跳转到我的反馈界面
                                intent = new Intent(LoginActivity.this,FeedbackListActivity.class);
                                startActivity(intent);
                                break;

                            case MyGoods://跳转到我的物资界面

                                intent = new Intent(LoginActivity.this,MyGoodsListActivity.class);
                                startActivity(intent);
                                break;

                            case Team://团队报名
                                intent = new Intent(LoginActivity.this,TeamSignUpActivity.class);
                                startActivity(intent);
                                break;

                        }

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


    }*/


}
