package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ShareInfoManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.x;

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
        /**
         * 设置布局
         */
        setContentView(getLayout());
        /**
         * 使用注解框架
         */
        x.view().inject(this);
        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.toolBarTitle= (TextView) this.findViewById(R.id.toolbar_title);
        this.shareImage= (ImageView) this.findViewById(R.id.share);
        this.btnBack= (ImageView) this.findViewById(R.id.btn_Back);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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



//                 分享图片
                if(manager.getShareType()== ShareInfoManager.ShareType.IMAGE){


                    new ShareAction(BaseActivity.this).withText(manager.getTitle())
                            .setPlatform(share_media)
                            .setCallback(new CustomUMShareListener())
                            .withMedia(manager.ShareImage())
                            .share();

                }else {//分享网页

                    new ShareAction(BaseActivity.this).withText(manager.getTitle())
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
            Toast.makeText(BaseActivity.this,"正在打开分享...",Toast.LENGTH_SHORT).show();
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

                if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                    action.open();

                }else {

                    requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });

    }



    /**
     * 检查是否拥有权限
     * @param permissions
     * @return
     */
      public boolean hasPermission(String... permissions){

//          不是6.0系统不进行检查
          if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){

              return true;
          }

          for (String permission:permissions) {

              if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                  return false;
              }
          }


          return true;

      }

    /**
     * 申请权限
     * @param code
     * @param permissions
     */

    public void requestPermission(int code,String...permissions){


        ActivityCompat.requestPermissions(this,permissions,code);

    }

    /**
     * 申请权限的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //没有获取到权限
        if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){

            String message="";

                switch (requestCode){

                    case Constants.WRITE_EXTERNAL_CODE:
                        message="您需要开启存储权限之后才能使用!";
                        break;
                    case Constants.CALL_CODE:
                        message="您需要开启电话权限之后才能使用!";
                        break;
                    case Constants.CAMERA_CODE:
                        message="您需要开启相机权限之后才能使用!";
                        break;
                }


            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("温馨提示");
            alert.setMessage(message);
            alert.setCancelable(false);
            alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(getAppDetailSettingIntent(BaseActivity.this));
                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
             alert.show();

            return;
        }

        switch (requestCode){

            case Constants.WRITE_EXTERNAL_CODE:

               doSDCardPermission();

                break;

            case Constants.CAMERA_CODE:
               doCameraPermission();
                break;
            case Constants.CALL_CODE:
                doCallPermission();
                break;
        }
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }


    /**
     * sd卡读取权限授权成功后默认执行改方法供给子类来调用
     */
    public void doSDCardPermission(){

         if(action!=null) {
             action.open();
         }
    }
    /**
     * 打开相机权限授权成功后默认执行改方法供给子类来调用
     */
    public void doCameraPermission(){}


    /**
     * 打电话权限回调
     */
    public void doCallPermission(){}


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
