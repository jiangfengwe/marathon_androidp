package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 获取手机验证码
 */
public class GetCodeActivity extends BaseActivity {


    private static final int CODE=0x0100;
    private int time;
    @ViewInject(R.id.tip)
    private TextView tip;
    @ViewInject(R.id.getCode)
    private TextView getCode;
    @ViewInject(R.id.code)
    private EditText editCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("验证码");
        showBackButton();

        Intent intent = getIntent();

        if(intent!=null){

            String phone = intent.getStringExtra("phone");

            tip.setText("已向手机，"+phone+"发送短信，请注意查收!");
            //发送验证码
             getCode();
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
       time=10;
        getCode.setEnabled(false);
       handler.sendEmptyMessage(CODE);
     //向服务发送发短信请求

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

       String code =  this.editCode.getText().toString().trim();

        if(code.length()==0){

            Toast toast = Toast.makeText(this,"验证不能为空!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

            return;
        }

        /**
         * 下一步
         */





    }
}
