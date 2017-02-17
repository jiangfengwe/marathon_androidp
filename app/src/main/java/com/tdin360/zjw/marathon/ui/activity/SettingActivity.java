package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManger;
import com.tdin360.zjw.marathon.utils.VersionManager;

public class SettingActivity extends BaseActivity {

    private CheckBox switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setToolBarTitle("系统设置");
          showBackButton();
        TextView version = (TextView) this.findViewById(R.id.version);
        version.setText("检查更新("+ VersionManager.getVersion(this)+")");
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
                 //向服务器发起版本匹配请求
                 //不是最新版本更新当前版本
                 //否则就是最新版本
                 new UpdateManger(SettingActivity.this).checkUpdateInfo();
                // Toast.makeText(SettingActivity.this,"已是最新版本!",Toast.LENGTH_SHORT).show();
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

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }


    public void back(View view) {
        finish();
    }
}
