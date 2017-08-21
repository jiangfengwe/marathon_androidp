package com.tdin360.zjw.marathon.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 输入手机号获取验证码
 * @ahthor zzj
 * @time 17/7/30 上午9:32
 */
public class RegisterOneActivity extends BaseActivity {

     public static Activity instance;
    @ViewInject(R.id.phone)
    private EditText editTextPhone;
    @ViewInject(R.id.textCount)
    private TextView textCount;
    @ViewInject(R.id.clear)
    private ImageView clear;
    @ViewInject(R.id.nextBtn)
    private Button nextBtn;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          instance=this;

        Intent intent = this.getIntent();
        if(intent==null)return;

        String title = intent.getStringExtra("title");
        this.type = getIntent().getStringExtra("type");

        setToolBarTitle(title);
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

                if(s.toString().length()==11){

                    nextBtn.setEnabled(true);
                }else {

                   nextBtn.setEnabled(false);
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

        this.findViewById(R.id.toLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        final String phone = editTextPhone.getText().toString().trim();

        if(phone.length()==0){

            ToastUtils.showCenter(getBaseContext(),"手机号码不能为空!");

            return;
        }

        if(!ValidateUtils.isMobileNO(phone)){

           ToastUtils.showCenter(getBaseContext(),"手机号码格式错误!");

            return;
        }

        nextBtn.setEnabled(false);

        //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setDetailsLabel("请稍后")
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();



        /**
         * 验证手机号并发送验证码
         */
        RequestParams params = new RequestParams(HttpUrlUtils.REGISTER_ONE);
        params.addBodyParameter("phone",phone);
        params.addBodyParameter("type",type);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

//                Log.d("------------>", "onSuccess: "+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    boolean success = obj.getBoolean("Success");
                    String reason = obj.getString("Reason");
                    if(success){


                        /**
                         *  验证成功进行注册第二步
                         */

                        String type = getIntent().getStringExtra("type");
                        Intent intent = new Intent(RegisterOneActivity.this,GetCodeActivity.class);
                        intent.putExtra("phone",phone);
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

                nextBtn.setEnabled(true);
                ToastUtils.showCenter(getBaseContext(),"网络连接错误或服务器错误!");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();
                nextBtn.setEnabled(true);
            }
        });



    }

}
