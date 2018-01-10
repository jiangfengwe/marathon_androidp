package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态消息
 */
public class CircleMessageActivity extends BaseActivity {
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.rv_circle_message)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initata();
        initView();

    }

    private void initata() {
        /*Intent intent=getIntent();
        String dynamicId = intent.getStringExtra("dynamicId");
        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_PRAISE_LIST);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("dynamicId",dynamicId);
        params.addBodyParameter("pageSize","5");
        params.addBodyParameter("pageIndex","1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("circlemessage", "onSuccess: "+result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });*/
    }

    private void initView() {
        for (int i = 0; i <6; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_circle_message) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {


            }
        };
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

    }

    private void initToolbar() {
        titleTv.setText("来访的佰家星人");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        showBack(toolbar,imageView);
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_circle_message;
    }
}
