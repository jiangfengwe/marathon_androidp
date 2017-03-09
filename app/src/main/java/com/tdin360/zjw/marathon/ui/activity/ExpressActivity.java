package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;


import com.tdin360.zjw.marathon.R;

/**
 * 申请快递
 * @author zhangzhijun
 * 2017-3-4
 */
public class ExpressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("申请快递");
        showBackButton();
    }
    @Override
    public int getLayout() {
        return R.layout.activity_express;
    }


}
