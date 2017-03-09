package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tdin360.zjw.marathon.R;

/**
 * 显示支付结果界面
 */
public class PayResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setToolBarTitle("支付结果");
          showBackButton();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_pay_result;
    }

    public void click(View view) {


        switch (view.getId()){


            case R.id.details://查看详情
               Intent intent = new Intent(this,MySignUpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.home://返回首页

                finish();

                break;
        }



    }


}
