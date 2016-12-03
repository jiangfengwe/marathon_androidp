package com.tdin360.zjw.marathon.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManger;
import com.tdin360.zjw.marathon.utils.VersionManager;

public class SettingActivity extends Activity {

    private CheckBox switchBtn;
    private static OnIsClearDataListener onIsClearDataListener1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
                builder.setMessage("即将清空您的报名信息记录,确定清除吗?");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferencesManager.clear(SettingActivity.this);
                        //清空数据重新更改个人中心用户名
                        if(onIsClearDataListener1!=null){
                            onIsClearDataListener1.isClear();
                        }
                        Toast.makeText(SettingActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
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
     * 清空数据重新更改个人中心用户名
     * @param onIsClearDataListener
     */
    public static void setOnIsDataListener(OnIsClearDataListener onIsClearDataListener){

           onIsClearDataListener1=onIsClearDataListener;
    }
    public interface OnIsClearDataListener{

        void isClear();
    }
    public void back(View view) {
        finish();
    }
}
