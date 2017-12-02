package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态详情
 */

public class CircleDetailActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_circle_detail)
    private Toolbar toolbarBack;
    @ViewInject(R.id.iv_circle_detail_back)
    private ImageView ivBack;



    @ViewInject(R.id.rv_circle_detail)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }

    private void initView() {
        for (int i = 0; i <6 ; i++) {
            list.add(""+i);

        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_circle_detail) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                String str="回复<font color='#ff621a'>楼主：</font>的统一问题极寒风暴㚥看能否佰家节能环保福尔";

                TextView tvResponse = (TextView) holder.getViewById(R.id.tv_circle_detail_response);
                tvResponse.setText(Html.fromHtml(str));

            }

            @Override
            public void onBindHeaderViewHolder(HeaderViewHolder holder) {
                super.onBindHeaderViewHolder(holder);
                ImageView ivEnter = (ImageView) holder.getViewById(R.id.iv_circle_detail_head_enter);
                WebView webView = (WebView) holder.getViewById(R.id.wb_circle_detail);
                String url = "http://www.baidu.com/";
                com.tencent.smtt.sdk.WebSettings settings = webView.getSettings();
                settings.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClient());
                webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient(){
            @Override
            public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {
                super.onReceivedTitle(view, title);
                //textViewTitle.setText(title);
                //avRefresh.setVisibility(View.GONE);
                //layoutRefresh.setVisibility(View.GONE);

            }
        });



                ivEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CircleDetailActivity.this,CircleDetailPraiseActivity.class);
                        startActivity(intent);

                    }
                });
            }
        };
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.addHeaderView(R.layout.item_circle_detail_head);
    }

    private void initToolbar() {
        showBack(toolbarBack,ivBack);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_circle_detail;
    }
}
