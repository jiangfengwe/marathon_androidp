package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NewsItem;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;

public class NewsAndNoticeDetailsActivity extends Activity {

    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private TextView detailName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_or_notice_details);
        this.webView= (WebView) this.findViewById(R.id.webView);
        this.progressBar= (ProgressBar) this.findViewById(R.id.progressBar);
        this.loadingView= (LinearLayout) this.findViewById(R.id.loadingView);
        this.detailName= (TextView) this.findViewById(R.id.detailName);
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        this.detailName.setText(type);
        if(url!=null){
           loading(HttpUrlUtils.MAIN_HTTP_URL+url);
        }
    }
     private void loading(String url){

         this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
         this.webView.getSettings().setJavaScriptEnabled(true);
         this.webView.setWebChromeClient(new MyWebViewChromeClient());
         this.webView.setWebViewClient(new MyWebViewClient());
         this.webView.loadUrl(url);
     }
    private class MyWebViewChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }
    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        //如果网页能返回就返回，否则就退出当前activity
        if(this.webView.canGoBack()){
            loadingView.setVisibility(View.VISIBLE);
            this.webView.goBack();

        }else {
            finish();
        }
    }
    /**
     * 返回
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
