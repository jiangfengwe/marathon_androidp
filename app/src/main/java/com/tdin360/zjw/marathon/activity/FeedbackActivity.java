package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tdin360.zjw.marathon.R;

/**
 * 意见反馈
 */
public class FeedbackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void back(View view) {
         finish();
    }
}
