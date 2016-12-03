package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tdin360.zjw.marathon.R;

/**
 *
 * 我的信息
 */
public class MyInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
    }

    //返回
    public void back(View view) {
        finish();
    }
}
