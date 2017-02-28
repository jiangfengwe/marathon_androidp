package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;
import com.tdin360.zjw.marathon.R;

/**
 *成绩详情
 * @author zhangzhijun
 */
public class MyAchievementDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("成绩详情");
         showBackButton();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_achievement_details ;
    }
}
