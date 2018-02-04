package com.tdin360.zjw.marathon.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.view.annotation.ViewInject;

public class ZxingWebActivity extends BaseActivity {
    @ViewInject(R.id.zxing_web)
    private WebView webView;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;

    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWeb();
        initToolbar();
    }
    private void initToolbar() {
        //titleTv.setText("评论");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        showBack(toolbar, imageView);
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
    }
    private void initWeb() {
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
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId();
        String phone = loginInfo.getPhone();
        Intent intent=getIntent();
        String type = intent.getStringExtra("type");
        String webUrl = HttpUrlUtils.ZXING_URL+"?CustomerId="+customerId+"&Phone="+phone+"&type=RegisterNumber";
        Log.d("webUrl", "initWeb: "+webUrl);
        webView.loadUrl(webUrl);
        webView.addJavascriptInterface(ZxingWebActivity.this,"android");

    }
    @Override
    public int getLayout() {
        return R.layout.activity_zxing_web;
    }
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            progressBar.setProgress(i);

        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
             titleTv.setText(s);
        }
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            progressBar.setVisibility(View.GONE);
            titleTv.setText(webView.getTitle());
        }
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            Log.d("------url--->", "shouldOverrideUrlLoading: "+s);
            //微信支付
            if(s!=null&&s.startsWith("weixin://app/")){
                Log.d("------支付--->", "shouldOverrideUrlLoading: "+s);
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("weixin://wap/pay?appid%3Dwx2421b1c4370ec43b%26noncestr%3Dee0456cf9096f19b0b1a5bf6405bfdf8%26\n" +
                            "package%3DWAP%26\n" +
                            "prepayid%3Dwx2016041510310675dd0b79e80509438698%26\n" +
                            "timestamp%3D1460687466%26\n" +
                            "sign%3DE9409AE1B77B897D422F661558C7F9C6"));
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    ToastUtils.show(ZxingWebActivity.this, "请安装微信最新版客户端");
                }
                return true;


            }
            /**
             * 支付宝支付
             */
            if(s!=null&&s.contains("alipays://platformapi/startApp?")){

                try {
                    Intent  intent = new Intent(Intent.ACTION_VIEW,Uri.parse(s));
                    startActivity(intent);

                } catch (Exception e) {


                }

                return true;
            }


            return  false;
        }
    }
}
