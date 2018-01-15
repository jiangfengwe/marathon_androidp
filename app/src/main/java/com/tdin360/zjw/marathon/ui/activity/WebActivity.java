package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.ValueCallback;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

/**
 *  赛事详情
 */
public class WebActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.mWebView)
    private WebView webView;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;
    @ViewInject(R.id.title)
    private TextView titleTv;
    @ViewInject(R.id.back)
    private ImageView back;
    @ViewInject(R.id.close)
    private ImageView close;
    @ViewInject(R.id.more)
    private ImageView more;


    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;





    private ShareAction action;
    private String webUrl;
    private String imageUrl;
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()== EnumEventBus.WEBVIEW){
            initWeb();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
       /* Intent intent = getIntent();
        if(intent!=null){
            url = intent.getStringExtra("url");
            this.imageUrl=intent.getStringExtra("imageUrl");
        }*/
        initWeb();
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
        //String url="file:///android_asset/test.html";
        LoginUserInfoBean.UserBean modelInfo= SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String id = modelInfo.getId();
        String url = getIntent().getStringExtra("url");
        if(TextUtils.isEmpty(id)){
            webUrl= url + "?customerId=" +0;
        }else{
            webUrl = url + "?customerId=" +id;
        }

        Log.d("webUrl", "initWeb: "+webUrl);
        webView.loadUrl(webUrl);
        webView.addJavascriptInterface(WebActivity.this,"android");
    }
  private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    private void initView() {
        back.setOnClickListener(this);
        close.setOnClickListener(this);
        more.setOnClickListener(this);
        more.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //刷新旋转动画
                Animation animation= AnimationUtils.loadAnimation(WebActivity.this,R.anim.anim_in_web);
                if (animation != null) {
                    more.startAnimation(animation);
                }  else {
                    more.setAnimation(animation);
                    more.startAnimation(animation);
                }
                LinearInterpolator lin = new LinearInterpolator();
                animation.setInterpolator(lin);
                webView.clearHistory();
                webView.clearFormData();
                webView.reload();
                return false;
            }
        });
    }
    //给js调用的方法
    @JavascriptInterface
    public void toHotel(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(WebActivity.this,HotelActivity.class);

                startActivity(intent);

            }
        });
    }
    @JavascriptInterface
    public void toTravel(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WebActivity.this,TravelActivity.class);
                startActivity(intent);

            }
        });
    }
    @JavascriptInterface
    public void toLogin(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WebActivity.this,LoginActivity.class);
                intent.putExtra("webview","1");
                startActivity(intent);

            }
        });
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                //返回
                if(webView.canGoBack()){
                    webView.goBack();
                }else {
                    overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
                    finish();
                }
                break;
            case R.id.close:
                //关闭
                finish();
               /* Intent intent=new Intent(WebActivity.this,ApplyActivity.class);
                startActivity(intent);*/
                break;
            case R.id.more:
                //分享
                //webView.reload();
                //刷新旋转动画
                Animation animation= AnimationUtils.loadAnimation(WebActivity.this,R.anim.anim_in_web);
                if (animation != null) {
                    more.startAnimation(animation);
                }  else {
                    more.setAnimation(animation);
                    more.startAnimation(animation);
                }
                LinearInterpolator lin = new LinearInterpolator();
                animation.setInterpolator(lin);
                webView.reload();

             /*   if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M) {
                    share();
                }else {
                    if (ContextCompat.checkSelfPermission(WebActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(WebActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
                    } else {
                        share();
                    }
                }*/
                break;
        }
    }
   private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
             progressBar.setProgress(i);
            if(i==100){
                more.clearAnimation();
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
           // titleTv.setText(s);
        }
       // For Android < 3.0
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
            //titleTv.setText(webView.getTitle());
        }
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            Log.d("------url--->", "shouldOverrideUrlLoading: "+s);
            //微信支付
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
//
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
