package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManager;

public class SettingActivity extends BaseActivity implements UpdateManager.UpdateListener{

    private CheckBox switchBtn;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setToolBarTitle("系统设置");
          showBackButton();

        TextView version = (TextView) this.findViewById(R.id.version);
        version.setText("检查更新("+ UpdateManager.getVersionName(this)+")");
        //是否接收推送通知
          switchBtn = (CheckBox) this.findViewById(R.id.switchBtn);
          switchBtn.setChecked(SharedPreferencesManager.getOpen(this));
          switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   SharedPreferencesManager.isOpen(SettingActivity.this,isChecked);
              }
          });
        //检查更新
         this.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

              checkUpdate();

             }
         });


        //清空记录
        this.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("温馨提示");
                builder.setMessage("确定退出登录吗?");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String  msg = SharedPreferencesManager.clearLogin(getApplicationContext());
                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    /**
     * 检查安装包是否有更新
     */
    private void checkUpdate(){

        UpdateManager.setUpdateListener(this);
        UpdateManager.checkNewVersion(getApplicationContext());

    }
    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }


    @Override
    public void doSDCardPermission() {

        downloadAPK();
    }

    //下载安装包
    private void downloadAPK(){


        Toast.makeText(this,"已在后台下载",Toast.LENGTH_SHORT).show();
        //启动下载器下载安装包
        Intent intent = new Intent(SettingActivity.this, DownloadAPKService.class);
        intent.putExtra("url",url);
        startService(intent);
    }

    @Override
    public void checkFinished(boolean isUpdate, String content, String url) {

         this.url = url;
        if(isUpdate){//发现新版本

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("版本更新");
            alert.setMessage(Html.fromHtml(content));
            alert.setCancelable(false);

            alert.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //判断权限
                    if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        downloadAPK();

                    }else {
                        requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    }

                }
            });

            alert.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });


            alert.show();

        }else {//已是最新版本

            AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);

            alert.setTitle("版本检查");
            alert.setMessage("当前已是最新版本!");
            alert.setCancelable(false);

            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });

            alert.show();
        }
    }
}
