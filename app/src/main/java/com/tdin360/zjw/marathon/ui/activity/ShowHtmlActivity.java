package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ShareInfo;

/**
 * 用于显示网页的界面
 * @author zhangzhijun
 */
public class ShowHtmlActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.webView = (WebView) this.findViewById(R.id.webView);
        this.signUpBtn = (Button) this.findViewById(R.id.signBtn);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
        this.loadingView = (LinearLayout) this.findViewById(R.id.loadingView);
        showBackButton();
        //处理其他界面传过来的数据
        Intent intent = this.getIntent();

        if (intent!=null){

            boolean isSign = intent.getBooleanExtra("isSign", false);
            if(isSign){
                this.signUpBtn.setVisibility(View.VISIBLE);
                this.signUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShowHtmlActivity.this,SignUpActivity.class);
                        startActivity(intent);
                    }
                });
            }
            String title = intent.getStringExtra("title");
            setToolBarTitle(title);
            String url = intent.getStringExtra("url");
            ShareInfo s = new ShareInfo("佰家运动",url,"", BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
            showShareButton(s);

            loading(url);

        }


    }
    @Override
    public int getLayout() {
        return R.layout.activity_show_html;
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
