package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ValidateUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/***
 *
 * 找回密码
 */


public class RestPassWordActivity extends BaseActivity {


    private EditText editTextTel;
    private EditText editTextCode;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private int totalTime;
    private Button button;
    private ImageView clearBtn;

    private CheckBox showPass1,showPass2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setToolBarTitle("找回密码");
        showBackButton();

        this.editTextTel= (EditText) this.findViewById(R.id.tel);
        this.editTextCode= (EditText) this.findViewById(R.id.code);
        this.editTextPass1= (EditText) this.findViewById(R.id.password1);
        this.editTextPass2= (EditText) this.findViewById(R.id.password2);
        this.clearBtn = (ImageView) this.findViewById(R.id.iv_clean);
        this.showPass1 = (CheckBox) this.findViewById(R.id.showPass1);
        this.showPass2 = (CheckBox) this.findViewById(R.id.showPass2);
        this.showPass1.setOnCheckedChangeListener(new MyCheckBoxListener());
        this.showPass2.setOnCheckedChangeListener(new MyCheckBoxListener());

        /**
         * 监听输入框值的变化
         */
        this.editTextTel.addTextChangedListener(new TextWatcher() {
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

    }

    /**
     * 控制密码的显示与隐藏
     */
    private class MyCheckBoxListener implements CompoundButton.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()){

                case R.id.showPass1:
                    if(isChecked){

                        //显示密码
                        editTextPass1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editTextPass1.setSelection(editTextPass1.getText().length());
                    }else {
                      //隐藏密码

                        editTextPass1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editTextPass1.setSelection(editTextPass1.getText().length());
                    }

                    break;
                case R.id.showPass2:
                    if(isChecked){

                        editTextPass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        editTextPass2.setSelection(editTextPass2.getText().length());
                    }else {

                        editTextPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        editTextPass2.setSelection(editTextPass2.getText().length());
                    }
                    break;
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_rest_password;
    }


    //获取验证码
    public void getCode(View view) {
        button=(Button) view;

        String tel = this.editTextTel.getText().toString().trim();

        //验证手机号码是否符合规范
        if(tel.length()<11||!ValidateUtils.isMobileNO(tel)){

            Toast.makeText(this,"手机号有误,请正确填写手机号!",Toast.LENGTH_SHORT).show();
            editTextTel.requestFocus();
            return;
        }


        if(!NetWorkUtils.isNetworkAvailable(this)){

            Toast.makeText(this,"获取验证码需要联网",Toast.LENGTH_SHORT).show();

            return;
        }

       //发起验证码

        totalTime = 60;
        button.setEnabled(false);
        handler.sendEmptyMessage(0);

      /*  final RequestParams params = new RequestParams(HttpUrlUtils.SEND_SMS);
        params.addBodyParameter("tel",tel);
        params.addBodyParameter("type","zhmm");
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.setConnectTimeout(5*1000);
        params.setMaxRetryCount(0);

           x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    boolean success = json.getBoolean("Success");
                    String reason = json.getString("Reason");

                    if (success) {
                        Toast.makeText(RestPassWordActivity.this, "验证码已成功发送", Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(RestPassWordActivity.this, reason, Toast.LENGTH_SHORT).show();
                       restGetCode();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                Toast.makeText(RestPassWordActivity.this, "网络错误或访问服务器失败!", Toast.LENGTH_SHORT).show();
               restGetCode();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {


            }
        });*/


    }

    /**
     * 重置获取验证码按钮
     */
    private void restGetCode(){

        handler.removeMessages(0);
        button.setText("获取验证码");
        button.setEnabled(true);

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

                    }

                    break;
            }
        }
    };

    //提交
    public void submit(View view) {


        String tel = this.editTextTel.getText().toString().trim();

        //验证手机号码是否符合规范
        if(tel.length()<11||!ValidateUtils.isMobileNO(tel)){

            Toast.makeText(this,"手机号有误,请正确填写手机号!",Toast.LENGTH_SHORT).show();
            editTextTel.requestFocus();
            return;
        }

//        获取用户输入的验证码
        String inCode=editTextCode.getText().toString().trim();



        if (inCode.length()==0){

            Toast.makeText(this,"验证码不能为空!",Toast.LENGTH_SHORT).show();
            editTextCode.requestFocus();
            return;
        }

            //验证用户输入

            String pass1  =this.editTextPass1.getText().toString().trim();
            String pass2 = this.editTextPass2.getText().toString().trim();

            if(pass1.trim().length()<6){

                Toast.makeText(this,"密码长度不能少于六位数!",Toast.LENGTH_SHORT).show();
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

            //验证成功

        //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        RequestParams params = new RequestParams(HttpUrlUtils.FIND_PASSWORD);
         params.addBodyParameter("phone",tel);
         params.addBodyParameter("token",inCode);//验证码
         params.addBodyParameter("newPassword",pass2);
         params.addBodyParameter("appKey",HttpUrlUtils.appKey);
         params.setConnectTimeout(5*1000);
         params.setMaxRetryCount(0);

             x.http().post(params, new Callback.CommonCallback<String>() {
                 @Override
                 public void onSuccess(String s) {

                     try {
                         JSONObject  json = new JSONObject(s);

                         boolean success = json.getBoolean("Success");
                         String reason = json.getString("Reason");
                         Toast.makeText(RestPassWordActivity.this,reason,Toast.LENGTH_SHORT).show();
                         if(success){

                             //清除登录信息
                             SharedPreferencesManager.clearLogin(RestPassWordActivity.this);
                             //找回密码成功后跳转到登录界面
                             finish();
                         }


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }


                 @Override
                 public void onError(Throwable throwable, boolean b) {
                     Toast.makeText(RestPassWordActivity.this,"网络错误或无法访问服务器!",Toast.LENGTH_SHORT).show();
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


    /**
     * 清除内容
     * @param view
     */
    public void clear(View view) {

         editTextTel.setText(null);
    }
}
