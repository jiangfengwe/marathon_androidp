package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;

public class AboutUsActivity extends BaseActivity implements UpdateManager.UpdateListener {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("关于我们");
        showBackButton();
        TextView textView = (TextView) this.findViewById(R.id.version1);
        String versionName = UpdateManager.getVersionName(getApplicationContext());
        textView.setText("当前版本 V " + versionName);
        TextView version = (TextView) this.findViewById(R.id.version);
        version.setText(versionName);

        /**
         * 检查更新
         */
        this.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUpdate();

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }

    public void click(View view) {
        Intent intent = null;
        switch (view.getId()) {


            case R.id.gw:

                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + getResources().getString(R.string.webAddress)));
                    startActivity(intent);
                } catch (Exception e) {

                    ToastUtils.show(getBaseContext(), "无法打开链接");
                }
                break;
            case R.id.kf:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                dialog.setTitle("提示");
                dialog.setMessage("确定拨打085188622851客服电话吗?");
                dialog.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!hasPermission(Manifest.permission.CALL_PHONE)) {


                            requestPermission(Constants.CALL_CODE, Manifest.permission.CALL_PHONE);

                        } else {

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
                intent = new Intent(AboutUsActivity.this, ShowHtmlActivity.class);
                intent.putExtra("title", "服务协议");
                intent.putExtra("url", "file:///android_asset/serverAgreement.html");
                startActivity(intent);

                break;

            case R.id.help:

                break;
        }

    }

    /**
     * 打电话
     */
    private void callPhone() {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getResources().getString(R.string.tel)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(phoneIntent);

    }

    /**
     * 授权成功拨打电话
     */
    @Override
    public void doCallPermission() {

        callPhone();
    }

    @Override
    public void doSDCardPermission() {

        downloadAPK();
    }


    /**
     * 检查安装包是否有更新
     */
    private void checkUpdate(){

        UpdateManager.setUpdateListener(this);
        UpdateManager.checkNewVersion(getApplicationContext());

    }
    /**
     * 下载安装包
     */
    private void downloadAPK(){


        Toast.makeText(this,"已在后台下载",Toast.LENGTH_SHORT).show();
        /**
         * 启动下载器下载安装包
         */

        Intent intent = new Intent(AboutUsActivity.this, DownloadAPKService.class);
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

        }else {  /**已是最新版本*/


            AlertDialog.Builder alert = new AlertDialog.Builder(AboutUsActivity.this);

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
