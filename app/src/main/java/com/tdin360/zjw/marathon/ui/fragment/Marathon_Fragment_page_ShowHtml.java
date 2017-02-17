package com.tdin360.zjw.marathon.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**马拉松详情页显示网页的fragment
 * Created by Administrator on 2016/7/6.
 */
    @ContentView(R.layout.fragment_showhtml)
public class Marathon_Fragment_page_ShowHtml extends BaseFragment{

    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;
    @ViewInject(R.id.loadingView)
    private LinearLayout loadingView;

    public static Marathon_Fragment_page_ShowHtml newInstance(String url){

        Marathon_Fragment_page_ShowHtml showHtmlFragment = new Marathon_Fragment_page_ShowHtml();
        Bundle bundle  =new Bundle();
        bundle.putString("url",url);
        showHtmlFragment.setArguments(bundle);
        return showHtmlFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = this.getArguments();
        String url = arguments.getString("url");
        if(url!=null&&!url.isEmpty()){
            loading(url);
        }
    }
    private void loading(String url){

        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = this.webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(false);//隐藏缩放按钮
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        this.webView.setWebChromeClient(new MyWebViewChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());
        this.webView.loadUrl(url);
    }
    private class MyWebViewChromeClient extends WebChromeClient {


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
                //用javascript隐藏系统定义的404页面信息
                String data = "加载错误！";
                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        }
    }

}
