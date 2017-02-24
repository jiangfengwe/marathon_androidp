package com.tdin360.zjw.marathon.ui.activity;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.FastBlurUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyProgressDialogUtils;
import com.tdin360.zjw.marathon.utils.SendSMSUtils;
import com.tdin360.zjw.marathon.utils.ValidateUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 用户手机号注册获取验证码验证
 */
public class RegisterActivity extends BaseActivity {

    private int totalTime;
    private Button button;
    private EditText editTextTel;
    private EditText editTextCode;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private SendSMSUtils smsUtils;
    private String tel;
    private String password;
    private ImageView bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        setToolBarTitle("注册");
        this.smsUtils = SendSMSUtils.instance(this);
        this.editTextTel= (EditText) this.findViewById(R.id.tel);
        this.editTextCode= (EditText) this.findViewById(R.id.code);
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
        return R.layout.activity_register;
    }


    //获取验证码
    public void getCode(View view) {
        button=(Button) view;

         this.tel = this.editTextTel.getText().toString().trim();

        //验证手机号码是否符合规范
        if(tel.length()<11||!ValidateUtil.isMobileNO(tel)){

            Toast.makeText(this,"手机号有误,请正确填写手机号!",Toast.LENGTH_SHORT).show();
            editTextTel.requestFocus();
            return;
        }
        totalTime=60;
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.getcode_button_checked);
        handler.sendEmptyMessage(0);



         //smsUtils.sendSMSCode(tel,"1");


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
                        button.setText("("+totalTime+")秒后重新获取");
                    }else {
                        handler.removeMessages(0);
                        button.setText("获取验证码");
                        button.setEnabled(true);
                        button.setBackgroundResource(R.drawable.getcode_button_check);

                    }

                    break;
            }
        }
    };

    //验证验证码
    public void submit(View view) {

//        获取用户输入的验证码
        String inCode=editTextCode.getText().toString().trim();



//        if (inCode.length()==0){
//
//            Toast.makeText(this,"验证码不能为空!",Toast.LENGTH_SHORT).show();
//            editTextCode.requestFocus();
//            return;
//        }

        //获取验证码
      //  String code = smsUtils.getCurrentCode();

       // if (inCode.equals(code)){
           // Toast.makeText(this,"验证码正确!",Toast.LENGTH_SHORT).show();




            //验证用户输入密码

            String pass1  =this.editTextPass1.getText().toString().trim();
            String pass2 = this.editTextPass2.getText().toString().trim();
            this.password=pass2;

            if(pass1.length()<6){

                Toast.makeText(this,"密码长度不能小于六位数!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

            if(pass2.length()==0){

                Toast.makeText(this,"确认密码不能为空!",Toast.LENGTH_SHORT).show();
                this.editTextPass2.requestFocus();
                return;
            }

            if (!pass1.equals(pass2.trim())){

                Toast.makeText(this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                this.editTextPass1.requestFocus();
                return;
            }

           //向服务器提交注册数据

            register();


       // }else {


           // Toast.makeText(this,"验证码错误!",Toast.LENGTH_SHORT).show();
      //  }


    }

    /**
     * 向服务器提交数据
     */
    private void register(){

        MyProgressDialogUtils.getUtils(this).showDialog("提交中...");
        this.tel = this.editTextTel.getText().toString().trim();
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_REGISTER);
        params.addQueryStringParameter("phone",tel);
        params.addQueryStringParameter("password",password);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {


                try {
                    JSONObject json = new JSONObject(s);

                    boolean success = json.getBoolean("Success");
                    String reason = json.getString("Reason");

                    //报名成功跳转到登录界面
                    if(success){

                Toast.makeText(RegisterActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                  finish();

                    }else {
                     Toast.makeText(RegisterActivity.this,reason,Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(RegisterActivity.this,"网络错误或无法访问服务器!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                MyProgressDialogUtils.getUtils(RegisterActivity.this).closeDialog();
            }
        });
    }
}
