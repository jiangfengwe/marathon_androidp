package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;

import com.tdin360.zjw.marathon.R;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        setToolBarTitle("意见反馈");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_feedback;
    }


}
