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
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManager;

public class SettingActivity extends BaseActivity {

    private CheckBox switchBtn;

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

        //意见反馈
        this.findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
                startActivity(intent);
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
                       String  msg = SharedPreferencesManager.clearLogin(SettingActivity.this);
                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(Personal_CenterFragment.ACTION);
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

        boolean isNewVersion = UpdateManager.checkVersion(this);

        if(isNewVersion){//发现新版本

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("版本更新");
            alert.setMessage("发现新版本，是否下载更新?");
            alert.setCancelable(false);

            alert.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //判断权限
                    if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(SettingActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_CODE);
                    }else {

                        //启动下载器下载安装包
                        startService(new Intent(SettingActivity.this,DownloadAPKService.class));
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
    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==Constants.WRITE_EXTERNAL_CODE){

            //启动下载器下载安装包
             startService(new Intent(SettingActivity.this,DownloadAPKService.class));
        }
    }
}
