package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private TextView titleTv;
    private ShareAction action;
    private String url;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        this.webView= (WebView) this.findViewById(R.id.mWebView);
        this.progressBar= (ProgressBar) this.findViewById(R.id.progressBar);
        this.titleTv= (TextView) this.findViewById(R.id.title);


        Intent intent = getIntent();
        if(intent!=null){

            url = intent.getStringExtra("url");
            this.imageUrl=intent.getStringExtra("imageUrl");
        }



        /**
         * 返回上一页
         */
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        /**
         * 关闭
         */
        this.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 分享
         */
        this.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                android 6.0兼容
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {

                    share();
                }else {

                    if (ContextCompat.checkSelfPermission(WebActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(WebActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);

                    } else {

                        share();

                    }
                }


            }
        });


        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.setWebChromeClient(new MyWebChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());


        LoginModel model = SharedPreferencesManager.getLoginInfo(getBaseContext());
        String loginParams="";
        if(!model.getPassword().equals("")){

           loginParams = "?um="+model.getName()+"&"+"pw="+model.getPassword();
        }

        this.webView.loadUrl(url+loginParams);
 
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){


            case 1000:

                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    //授权成功
                    share();

                }else {

                    //授权失败
                    AlertDialog.Builder alert = new AlertDialog.Builder(WebActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("您需要设置允许存储权限才能使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CommonUtils.getAppDetailSettingIntent(getBaseContext());
                        }
                    });
                    alert.show();
                }

                break;
        }


    }


    private class MyWebChromeClient extends WebChromeClient{

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



    private class MyWebViewClient extends WebViewClient{



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

            /**
             * 微信支付
             */
            if(s!=null&&s.startsWith("weixin://wap/pay?")){

                Log.d("------支付--->", "shouldOverrideUrlLoading: "+s);
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(s));
                    startActivity(intent);

                }catch (ActivityNotFoundException e){
                    ToastUtils.show(WebActivity.this, "请安装微信最新版客户端");

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


    /**
     * 分享
     */
    private void share(){


                  /*使用友盟自带分享模版*/
        action = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {


                    UMWeb umWeb = new UMWeb(webView.getUrl());
                UMImage image = new UMImage(WebActivity.this,imageUrl);
                image.compressStyle= UMImage.CompressStyle.SCALE;
                   umWeb.setThumb(image);
                    umWeb.setTitle(webView.getTitle());
                    new ShareAction(WebActivity.this).withText(webView.getTitle())
                            .setPlatform(share_media)
                            .setCallback(new CustomUMShareListener())
                            .withMedia(umWeb)
                            .share();


            }



        });

        action.open();

    }

    /**
     * 自定义分享结果监听器
     */
    private class CustomUMShareListener implements UMShareListener {


        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(WebActivity.this,"正在打开分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(WebActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE
                        && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(WebActivity.this, platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {

              //  Log.d("分享失败了------>", "onError: "+throwable.getMessage());
                Toast.makeText(WebActivity.this, platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(WebActivity.this,"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
    }

    /**
     * 返回操作
     */
    private void back(){
        if(webView.canGoBack()){

            webView.goBack();
        }else {
            overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
            finish();
        }

    }
    @Override
    public void onBackPressed() {

        back();

    }
}
