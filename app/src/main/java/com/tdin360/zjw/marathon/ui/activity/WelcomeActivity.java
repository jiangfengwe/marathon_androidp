package com.tdin360.zjw.marathon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.tdin360.zjw.marathon.R;

import java.lang.ref.SoftReference;

import cn.jpush.android.api.JPushInterface;

/**
 * 启动页
 */
public class WelcomeActivity extends Activity {

    private Handler handler ;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler = new Handler();
        SharedPreferences share = this.getSharedPreferences("GuideData",Activity.MODE_PRIVATE);
        final boolean isGuide = share.getBoolean("isGuide", false);

         runnable = new Runnable() {
             @Override
             public void run() {

                 Intent intent;

                 //如果已经引导过了就直接跳转到主界面否则就进入引导页
                 if (!isGuide) {

                     intent = new Intent(WelcomeActivity.this, MainActivity.class);
                 }else {

                     intent = new Intent(WelcomeActivity.this, GuideActivity.class);

                 }

                 startActivity(intent);
                 finish();
             }
         };

        handler.postDelayed(runnable,2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
    }
}
