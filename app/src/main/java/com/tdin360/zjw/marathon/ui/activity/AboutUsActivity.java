package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
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

import org.xutils.view.annotation.ViewInject;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity  {
//implements UpdateManager.UpdateListener
    private String url;

    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();

        /*TextView textView = (TextView) this.findViewById(R.id.version1);
        String versionName = UpdateManager.getVersionName(getApplicationContext());
        textView.setText("当前版本 V " + versionName);
        TextView version = (TextView) this.findViewById(R.id.version);
        version.setText(versionName);

         // 检查更新

        this.findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checkUpdate();

            }
        });*/


    }
    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        titleTv.setText(R.string.setting_about_us);


        showBack(toolbar,imageView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }




}
