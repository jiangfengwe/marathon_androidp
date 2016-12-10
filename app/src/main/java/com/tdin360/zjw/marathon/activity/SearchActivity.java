package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.wxapi.Constants;
import com.tdin360.zjw.marathon.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 *
 * 搜索
 */
public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.setSupportActionBar(this.mToolBar);
        this.mToolBar.setNavigationIcon(R.drawable.nav_back_selector);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //返回
    public void back(View view) {
        finish();
    }

//    搜索
    public void search(View view) {

   startActivity(new Intent(this, WXEntryActivity.class));

    }

}
