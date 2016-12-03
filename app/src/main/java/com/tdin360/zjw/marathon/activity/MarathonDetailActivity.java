package com.tdin360.zjw.marathon.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonDetailPageTopTabAdapter;

public class MarathonDetailActivity extends FragmentActivity {
    private ViewPager viewPager;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marathon_detail);
        init();
    }
    //初始化
    private void init() {
        //马拉松详情顶部的栏目标签
       this.title= (TextView) this.findViewById(R.id.title);
        this.title.setText(this.getIntent().getStringExtra("title"));
        this.viewPager = (ViewPager) this.findViewById(R.id.containerViewPager);
        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tabs);
        MarathonDetailPageTopTabAdapter adapter = new MarathonDetailPageTopTabAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //报名
    public void signUp(View view) {

        Intent intent = new Intent(MarathonDetailActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
