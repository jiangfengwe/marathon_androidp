package com.tdin360.zjw.marathon.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.view.annotation.ViewInject;

public class RegisterServiceActivity extends BaseActivity {
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.tv_service)
    private TextView tvTitle;
    @ViewInject(R.id.tv_server_back)
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register_service);
        String  url ="file:///android_asset/serverAgreement.html";
        Log.d("orderIdurl", "onCreate: "+url);
        webView.getSettings().setUseWideViewPort(true);//内容适配，设置自适应任意大小的pc网页
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setWebChromeClient(new MyWebChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
           /* progressBar.setProgress(i);
            if(i==100){
                more.clearAnimation();
            }*/
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            tvTitle.setText(webView.getTitle());
        }
      /*  // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }

*/
    }
    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            //progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
           // progressBar.setVisibility(View.GONE);
            tvTitle.setText(webView.getTitle());
        }
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {

            return  false;
        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_register_service;
    }
}
