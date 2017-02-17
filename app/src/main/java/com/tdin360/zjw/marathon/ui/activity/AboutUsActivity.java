package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.tdin360.zjw.marathon.R;

public class AboutUsActivity extends BaseActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("关于我们");
        showBackButton();
        this.webView= (WebView) findViewById(R.id.webView);
        this.webView.loadUrl("file:///android_asset/about.html");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }

    public void back(View view) {
        finish();
    }
}
