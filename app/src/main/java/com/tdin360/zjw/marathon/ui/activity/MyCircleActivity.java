package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的动态
 */

public class MyCircleActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.iv_my_circle_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_my_circle_pic)
    private ImageView ivPic;

    @ViewInject(R.id.rv_my_circle)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();


    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        for (int i = 0; i <4 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_my_circle) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

            }
        };
        adapter.addHeaderView(R.layout.item_my_circle_head);
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_circle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_my_circle_back:
                //返回
                finish();
                break;
            case R.id.iv_my_circle_pic:
                //切换背景图
                break;
        }

    }
}
