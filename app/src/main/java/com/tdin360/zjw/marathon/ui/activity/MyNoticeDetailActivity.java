package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.SystemNoticeBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.db.impl.SystemNoticeDetailsServiceImpl;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.view.annotation.ViewInject;

import java.net.URL;
import java.util.List;

public class MyNoticeDetailActivity extends BaseActivity {

    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.notice_webview)
    private WebView webView;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getApplicationContext());
        List<SystemNoticeBean> allCircleNotice = systemNoticeDetailsService.getAllSystemNotice();
        SystemNoticeBean model=new SystemNoticeBean();
        model.setNotice("1");*/

        //setContentView(R.layout.activity_my_notice_detail);
        initToolbar();
        initWebView();
    }

    private void initWebView() {
        //显示提示框
        hud= KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        Intent intent=getIntent();
        String url1 = intent.getStringExtra("url");
        String messageId = intent.getStringExtra("messageId");
        SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getApplicationContext());
        systemNoticeDetailsService.update("1",messageId);
        //http://www.tdin360.com/
        Log.d("url1", "initWebView: "+url1);
        String url=HttpUrlUtils.URL+ url1;
        //int Id = getIntent().getIntExtra("Id", -1);
        //String url = HttpUrlUtils.NOTICE_DETAIL_URL + "?Id=" + Id;
        Log.d("noticeurl", "initWebView: "+url);
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
    }
    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            //progressBar.setProgress(i);
            if(i==100){
                hud.dismiss();
                //more.clearAnimation();
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);

        }
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
    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText("系统详情");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);
        /*SharedPreferencesManager.isNotice(getApplicationContext(),false);
        EnumEventBus notice = EnumEventBus.SYSTEMNOTICE;
        EventBus.getDefault().post(new EventBusClass(notice));
        finish();*/
    }
    @Override
    public int getLayout() {
        return R.layout.activity_my_notice_detail;
    }
}
