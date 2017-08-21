package com.tdin360.zjw.marathon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 获取手机验证码
 * @ahthor zzj
 * @time 17/7/30 上午9:33
 */
public class GetCodeActivity extends BaseActivity {

    public static Activity instance;
    private static final int CODE=0x0100;
    private int time;
    @ViewInject(R.id.tip)
    private TextView tip;
    @ViewInject(R.id.getCode)
    private TextView getCode;
    @ViewInject(R.id.code)
    private EditText editCode;
    private String phone;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance=this;
        setToolBarTitle("验证码");
        showBackButton();

        Intent intent = getIntent();

        if(intent!=null){
             this.phone = intent.getStringExtra("phone");
             this.type=intent.getStringExtra("type");
             startTaskTimer();
            tip.setText("已向手机，"+phone+"发送短信，请注意查收!");

        }


        /**
         * 重新获取验证码
         */
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });

    }

    /**
     * 获取验证码
     */
    private void  getCode(){

        startTaskTimer();

        //发送验证码
        RequestParams params = new RequestParams(HttpUrlUtils.SEND_SMS);
        params.addBodyParameter("tel",phone);
        params.addBodyParameter("type",type);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.setConnectTimeout(5*1000);
        params.setMaxRetryCount(0);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);

                    boolean success = json.getBoolean("isSuccess");
                    String reason = json.getString("content");
                    if(success){
                        Toast.makeText(GetCodeActivity.this,"验证码已成功发送",Toast.LENGTH_SHORT).show();

                    }else {
                        restTaskTimer();
                        Toast.makeText(GetCodeActivity.this,reason,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(GetCodeActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(GetCodeActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();
                restTaskTimer();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    /**
     * 开的倒计时
     */
    private void startTaskTimer(){

        time=10;
        getCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }

    /**
     * 重置验证码按钮
     */
    private void restTaskTimer(){

        handler.removeMessages(CODE);
        getCode.setText("获取验证码");
        getCode.setEnabled(true);

    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(CODE,1000);
            if(time>0){
             getCode.setText(time+"s后获取");
                time--;

            }else {
                handler.removeMessages(CODE);
              getCode.setText("获取验证");
              getCode.setEnabled(true);
            }

        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_get_code;
    }


    /**
     * 下一步
     * @param view
     */

    public void nextClick(View view) {

        /**
         * 输入验证
         */

       final String code =  this.editCode.getText().toString().trim();

        if(code.length()==0){

            ToastUtils.showCenter(getBaseContext(),"验证不能为空!");

            return;
        }


        /**
         * 验证验证码
         */

        RequestParams params = new RequestParams(HttpUrlUtils.VALIDATE_CODE);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("validCode",code);
        params.addBodyParameter("type",type);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    boolean success = obj.getBoolean("Success");
                    String reason = obj.getString("Reason");

                    if(success){


                        /**
                         * 验证码验证成功进入下一步
                         * 设置密码
                         */

                 Intent intent = new Intent(GetCodeActivity.this,SettingPasswordActivity.class);
                 intent.putExtra("phone",phone);
                 intent.putExtra("code",code);
                 intent.putExtra("type",type);
                 startActivity(intent);


                    }else {

                     ToastUtils.showCenter(getBaseContext(),reason);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                ToastUtils.showCenter(getBaseContext(),"网络错误,无法连接服务器!");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });



    }


}
