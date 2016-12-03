package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.tdin360.zjw.marathon.R;

public class AboutUsActivity extends Activity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        this.webView= (WebView) findViewById(R.id.webView);
        this.webView.loadUrl("file:///android_asset/about.html");
    }

    public void back(View view) {
        finish();
    }
}
