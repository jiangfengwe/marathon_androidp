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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;

import org.xutils.view.annotation.ViewInject;
/**
 * 设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    //implements UpdateManager.UpdateListener


    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_about_us)
    private TextView tvAbout;
    @ViewInject(R.id.tv_feedback)
    private TextView tvFeedback;
    @ViewInject(R.id.tv_clear)
    private TextView tvClear;
    @ViewInject(R.id.layout_vision)
    private LinearLayout layoutVision;
    @ViewInject(R.id.layout_phone)
    private RelativeLayout layoutPhone;
    @ViewInject(R.id.btn_exit)
    private Button btnExit;


    @ViewInject(R.id.cb_switchBtn)
    private CheckBox switchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }

    private void initView() {
        tvAbout.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        layoutVision.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        //是否接收推送通知
        switchBtn.setChecked(SharedPreferencesManager.getOpen(this));
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesManager.isOpen(SettingActivity.this,isChecked);
            }
        });
    }
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        Intent intent=null;
        switch (v.getId()){
            case R.id.tv_about_us:
                //关于我们
                intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_feedback:
                //反馈意见
               /* if(SharedPreferencesManager.isLogin(getApplicationContext())){
                    intent = new Intent(SettingActivity.this,FeedbackListActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(SettingActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //设置登录成功后跳转到反馈界面
                    LoginNavigationConfig.instance().setNavType(NavType.MyFeed);
                }*/
                intent = new Intent(SettingActivity.this,FeedbackListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_clear:
                //清除缓存
                ToastUtils.show(getApplicationContext(),"清除缓存");
                builder.setTitle("温馨提示");
                builder.setMessage("确定清除缓存吗?");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String  msg = SharedPreferencesManager.clearLogin(getApplicationContext());
                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);*/
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
                break;
            case R.id.layout_vision:
                //版本更新
                ToastUtils.show(getApplicationContext(),"版本更新");
                builder.setTitle("温馨提示");
                builder.setMessage("确定下载新版本吗?");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String  msg = SharedPreferencesManager.clearLogin(getApplicationContext());
                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);*/
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
                break;
            case R.id.layout_phone:
                //客服电话
                builder.setTitle("温馨提示");
                builder.setMessage("确定拨打客服电话吗?");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String  msg = SharedPreferencesManager.clearLogin(getApplicationContext());
                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);*/
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
                break;
            case R.id.btn_exit:
                //退出当前账号
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
                break;
        }

    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.setting_title);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

 /*   public void click(View view) {
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

    *//**
     * 打电话
     *//*
    private void callPhone() {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getResources().getString(R.string.tel)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(phoneIntent);

    }

    *//**
     * 授权成功拨打电话
     *//*
    @Override
    public void doCallPermission() {

        callPhone();
    }

    @Override
    public void doSDCardPermission() {

        downloadAPK();
    }


    *//**
     * 检查安装包是否有更新
     *//*
    private void checkUpdate(){

        UpdateManager.setUpdateListener(this);
        UpdateManager.checkNewVersion(getApplicationContext());

    }
    *//**
     * 下载安装包
     *//*
    private void downloadAPK(){


        Toast.makeText(this,"已在后台下载",Toast.LENGTH_SHORT).show();
        *//**
         * 启动下载器下载安装包
         *//*

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

        }else {  *//**已是最新版本*//*


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
    }*/

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
