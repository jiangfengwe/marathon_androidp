package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.UpdateManager;

public class AboutUsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("关于我们");
        showBackButton();
        TextView textView  = (TextView) this.findViewById(R.id.version);

        textView.setText("V "+ UpdateManager.getVersionName(getApplicationContext()));

    }

    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }

    public void click(View view) {
        Intent intent=null;
       switch (view.getId()){


           case R.id.gw:

               intent = new Intent();
               intent.setAction("android.intent.action.VIEW");
               Uri content_url = Uri.parse("http://"+getResources().getString(R.string.webAddress));
               intent.setData(content_url);
               startActivity(intent);

               break;
           case R.id.kf:

               AlertDialog.Builder dialog = new AlertDialog.Builder(this);

               dialog.setTitle("提示");
               dialog.setMessage("确定拨打085188622851客服电话吗?");
               dialog.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       if(!hasPermission(Manifest.permission.CALL_PHONE)){


                           requestPermission(Constants.CALL_CODE,Manifest.permission.CALL_PHONE);

                       }else {

                           callPhone();
                       }

                   }
               });

               dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               dialog.show();


               break;

           case R.id.xy:
                intent = new Intent(AboutUsActivity.this,ShowHtmlActivity.class);
               intent.putExtra("title","服务协议");
               intent.putExtra("url","file:///android_asset/serverAgreement.html");
               startActivity(intent);

               break;

           case R.id.quest:

               if(SharedPreferencesManager.isLogin(getApplicationContext())){

                   intent = new Intent(AboutUsActivity.this,FeedbackListActivity.class);
                   startActivity(intent);
               }else {
                   intent = new Intent(AboutUsActivity.this,LoginActivity.class);
                   startActivity(intent);
                   //设置登录成功后跳转到反馈界面
                   LoginNavigationConfig.instance().setNavType(NavType.MyFeed);
               }

               break;
           case R.id.help:

               break;
       }

    }

    /**
     * 打电话
     */
    private void callPhone(){
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:"+getResources().getString(R.string.tel)));
        startActivity(phoneIntent);

    }

    /**
     * 授权成功拨打电话
     */
    @Override
    public void doCallPermission() {
        super.doCallPermission();
        callPhone();
    }
}
