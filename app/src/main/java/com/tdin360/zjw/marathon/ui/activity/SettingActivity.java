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
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManager;

import org.xutils.view.annotation.ViewInject;

public class SettingActivity extends BaseActivity{

    @ViewInject(R.id.switchBtn)
    private CheckBox switchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setToolBarTitle("系统设置");
          showBackButton();




        /**
         * 是否接收推送通知
         */

          switchBtn.setChecked(SharedPreferencesManager.getOpen(this));
          switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   SharedPreferencesManager.isOpen(SettingActivity.this,isChecked);
              }
          });

        /**
         * 关于我们
         */
        this.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 意见反馈
         */

        this.findViewById(R.id.quest).setOnClickListener(new View.OnClickListener() {
            Intent intent=null;
            @Override
            public void onClick(View v) {

                if(SharedPreferencesManager.isLogin(getApplicationContext())){

                   intent = new Intent(SettingActivity.this,FeedbackListActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(SettingActivity.this,LoginActivity.class);
                    startActivity(intent);


                    /**
                     *  设置登录成功后跳转到反馈界面
                     */
                    LoginNavigationConfig.instance().setNavType(NavType.MyFeed);
                }
            }
        });



        /**
         * 清空记录
         */
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

                        /**
                         * 通知个人中心修改登录状态
                         */
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

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }






}
