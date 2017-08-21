package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.CommonUtils;
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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 基类
 * @author zhangzhijun
 */
@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity {

    @ViewInject(R.id.mToolBar)
    private Toolbar mToolBar;
    @ViewInject(R.id.toolbar_title)
    private TextView toolBarTitle;
    @ViewInject(R.id.navRightItemImage)
    private ImageView navRightItemImage;
    @ViewInject(R.id.navRightItemTitle)
    private TextView navRightItemTitle;
    @ViewInject(R.id.btn_Back)
    private ImageView btnBack;



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


    }

    public Toolbar navBar(){

        return this.mToolBar;
    }

    public ImageView navBack(){

        return  this.btnBack;
    }

    public ImageView navRightItemImage() {
        return navRightItemImage;
    }

    public TextView navRightItemTitle() {
        return navRightItemTitle;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);

    }


    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.anim_in_back_activity,R.anim.anim_out_back_activity);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_activity,R.anim.anim_out_activity);
    }



//    设置标题
    public void setToolBarTitle(CharSequence title){

        if(title!=null){

            this.toolBarTitle.setText(title);
        }
    }



    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setToolBarTitleColor(@ColorInt int color){

         this.toolBarTitle.setTextColor(color);
    }


    /**
     * 设置标题字体颜色
     * @param resId
     */

    public void setToolBarTitleColorResource(@ColorRes int resId){

       if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          this.toolBarTitle.setTextColor(getResources().getColor(resId,null));
         }else {
            this.toolBarTitle.setTextColor(getResources().getColor(resId));
         }
    }

    /**
     * 设置导航条颜色
     * @param color
     */
    public void setmToolBarColor(@ColorInt int color){

        this.mToolBar.setBackgroundColor(color);
    }
   /**
     * 设置导航条颜色
     * @param resId
     */
    public void setmToolBarColorRes(@ColorRes int resId){

          this.mToolBar.setBackgroundResource(resId);

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
            alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonUtils.getAppDetailSettingIntent(getBaseContext());

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
     * sd卡读取权限授权成功后默认执行改方法供给子类来调用
     */
    public void doSDCardPermission(){

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
