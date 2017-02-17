package com.tdin360.zjw.marathon.ui.activity;

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

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.model.NoticeModel;
import com.tdin360.zjw.marathon.model.ShareInfo;
import com.tdin360.zjw.marathon.utils.ShareGetImageUtils;

public class NewsAndNoticeDetailsActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.webView = (WebView) this.findViewById(R.id.webView);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        this.loadingView = (LinearLayout) this.findViewById(R.id.loadingView);
        showBackButton();

        Intent intent = this.getIntent();
        String type = intent.getStringExtra("type");
        if (type.equals("1")) {//新闻详情
            setToolBarTitle("新闻详情");
            NewsModel newsModel = (NewsModel) intent.getSerializableExtra("newsModel");
            showShareButton(new ShareInfo(newsModel.getTitle(), newsModel.getDetailUrl(), "", ShareGetImageUtils.getBitmapByCance(newsModel.getPicUrl())));

             loading(newsModel.getDetailUrl());

        } else {//公告详情
            setToolBarTitle("公告详情");
            NoticeModel noticeModel = (NoticeModel) intent.getSerializableExtra("noticeModel");
            showShareButton(new ShareInfo(noticeModel.getTitle(), noticeModel.getUrl(), "",null));
            loading(noticeModel.getUrl());



        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_news_or_notice_details;
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

}
