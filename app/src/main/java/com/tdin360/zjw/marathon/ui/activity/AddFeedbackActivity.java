package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 *  添加意见反馈
 * @author zhangzhijun
 */
public class AddFeedbackActivity extends BaseActivity {

    private EditText editText;
    private KProgressHUD hud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        setToolBarTitle("添加意见反馈");
       this.editText = (EditText) this.findViewById(R.id.edit);
        initHUD();



    }
    /**
     * 初始化提示框
     */
    private void initHUD(){

        //显示提示框
        this.hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(true);
        hud.setAnimationSpeed(1);
        hud.setDimAmount(0.5f);

    }
    @Override
    public int getLayout() {
        return R.layout.activity_addfeedback;
    }


    /**
     *
     * @param view
     */
   /* public void submit(View view) {


        String content = editText.getText().toString().trim();

        if(content==null||content.equals("")){

            Toast.makeText(AddFeedbackActivity.this,"内容不能为空!",Toast.LENGTH_SHORT).show();
            editText.requestFocus();
            return;
        }

        hud.show();
        RequestParams params = new RequestParams(HttpUrlUtils.ADDFEED_INFO);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("phone", SharedPreferencesManager.getLoginInfo(this).getName());
        params.addBodyParameter("FeedbackContent",content);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);

                    boolean success = json.getBoolean("Success");

                    if(success){
                        Toast.makeText(AddFeedbackActivity.this,"提交成功!",Toast.LENGTH_SHORT).show();
                        //更新列表
                        //Intent intent = new Intent(FeedbackListActivity.ACTION);
                        //sendBroadcast(intent);
                        finish();

                    }else {


                        Toast.makeText(AddFeedbackActivity.this,"提交失败!",Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(AddFeedbackActivity.this,"提交失败!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(AddFeedbackActivity.this,"无网络或连接服务器失败!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();
            }
        });
    }*/


}
