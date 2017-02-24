package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonDetailPageTopTabAdapter;
import com.tdin360.zjw.marathon.model.ShareInfo;
import com.tdin360.zjw.marathon.utils.ViewPageAnim;

public class MarathonDetailActivity extends BaseActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        showBackButton();

        init();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_marathon_detail;
    }

    //初始化
    private void init() {
        //马拉松详情顶部的栏目标签
//

        String eventName = this.getIntent().getStringExtra("eventName");
        this.setToolBarTitle(eventName);
        this.viewPager = (ViewPager) this.findViewById(R.id.containerViewPager);
        viewPager.setPageTransformer(true,new ViewPageAnim());
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tabs);
        MarathonDetailPageTopTabAdapter adapter = new MarathonDetailPageTopTabAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    //报名
    public void signUp(View view) {

        Intent intent = new Intent(MarathonDetailActivity.this,PayActivity.class);
        startActivity(intent);
    }


}
