package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ShareInfoManager;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.umeng.socialize.UMShareAPI;

/**
 * 用于显示网页的界面
 * @author zhangzhijun
 */
public class ShowHtmlActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.webView = (WebView) this.findViewById(R.id.webView);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.setWebChromeClient(new MyWebViewChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());

        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        initHUD();


        showBackButton();
        //处理其他界面传过来的数据
        Intent intent = this.getIntent();

        if (intent!=null){

            String title = intent.getStringExtra("title");
            setToolBarTitle(title);
            String shareTitle=null;
            String shareImageUrl=null;
            try{
                shareTitle = intent.getStringExtra("shareTitle");
                shareImageUrl = intent.getStringExtra("shareImageUrl");

            }catch (Exception e){

            }

            final String url = intent.getStringExtra("url");
            /**
             * 构建分享内容
             */

            if(shareTitle!=null) {
                ShareInfoManager manager = new ShareInfoManager(this);
                manager.buildShareWebLink(shareTitle, url, "佰家赛事", shareImageUrl);
                showShareButton(manager);
            }
           loading(url);

        }


    }


    /**
     * 分享需要配置确保回调成功
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public int getLayout() {
        return R.layout.activity_show_html;
    }

    private void loading(String url){

        if(!NetWorkUtils.isNetworkAvailable(this)){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();

            return;
        }
        this.webView.loadUrl(url);

     }

    /**
     * 初始化提示框
     */
    private void initHUD(){

        //显示提示框
        hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
                hud.setCancellable(true);
                hud.setAnimationSpeed(1);
                hud.setDimAmount(0.5f);

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
            hud.show();


        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return  true;


        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Toast.makeText(ShowHtmlActivity.this, "加载失败了", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            hud.dismiss();


        }
    }

    @Override
    public void onBackPressed() {

          finish();
    }

}
