package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ShareInfo;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ShareInfoManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * 母版界面
 * @author zhangzhijun
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView toolBarTitle;
    private ImageView shareImage;
    private ImageView btnBack;
    private ShareAction action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.toolBarTitle= (TextView) this.findViewById(R.id.toolbar_title);
        this.shareImage= (ImageView) this.findViewById(R.id.share);
        this.btnBack= (ImageView) this.findViewById(R.id.btn_Back);
        setSupportActionBar(this.mToolBar);
        //设置默认标题不显示
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);



    }

    /**
     * 初始化分享
     */
    private void initUMShare(final ShareInfoManager manager){

           /*使用友盟自带分享模版*/
        action = new ShareAction(BaseActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {




                if(manager.getShareType()== ShareInfoManager.ShareType.IMAGE){


                    new ShareAction(BaseActivity.this).withText("百家运动图片分享")
                            .setPlatform(share_media)
                            .setCallback(new CustomUMShareListener())
                            .withMedia(manager.ShareImage())
                            .share();

                }else {

                    new ShareAction(BaseActivity.this).withText("百家运动链接分享")
                            .setPlatform(share_media)
                            .setCallback(new CustomUMShareListener())
                            .withMedia(manager.shareLink())
                            .share();
                }

                }



        });

    }

//    设置标题
    public void setToolBarTitle(CharSequence title){

        if(title!=null){

            this.toolBarTitle.setText(title);
        }
    }

    /**
     * 自定义分享结果监听器
     */
    private class CustomUMShareListener implements UMShareListener {


        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(BaseActivity.this,"正在分享请稍后...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(BaseActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
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
                    Toast.makeText(BaseActivity.this, platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BaseActivity.this, platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(BaseActivity.this,"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setToolBarTitleColor(int color){

         this.toolBarTitle.setTextColor(color);
    }

    /**
     * 设置导航条颜色
     * @param color
     */
    public void setmToolBarColor(int color){

        this.mToolBar.setBackgroundColor(color);
    }

    /**
     * 显示返回按钮
     */
    public void showBackButton(){

        this.btnBack.setVisibility(View.VISIBLE);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public abstract int getLayout();


    //处理分享
    public void showShareButton(ShareInfoManager manager){

        this.shareImage.setVisibility(View.VISIBLE);

        //初始化分享
        initUMShare(manager);

        this.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //没有联网不能进行分享
                if(!NetWorkUtils.isNetworkAvailable(BaseActivity.this)){

                    Toast.makeText(BaseActivity.this,"亲,分享需要联网哦！",Toast.LENGTH_SHORT).show();

                    return;
                }
                //android 6.0需要申请权限才能操作
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};

                    //检查权限是否拥有某一权限
                    if(ActivityCompat.checkSelfPermission(BaseActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(BaseActivity.this,"部分分享需要权限才能分享哦!",Toast.LENGTH_SHORT).show();
                        //申请权限
                        ActivityCompat.requestPermissions(BaseActivity.this,mPermissionList,123);

                    }else {

                        action.open();
                    }
                }else {

                    action.open();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(grantResults.length==0){

            return;
        }
         if(requestCode == 123&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

             action.open();
         }
    }
}
