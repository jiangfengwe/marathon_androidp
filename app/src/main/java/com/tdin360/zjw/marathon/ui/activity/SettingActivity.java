package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.Manifest;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ViewInject;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * 设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,UpdateManager.UpdateListener{
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
    @ViewInject(R.id.iv_clean)
    private Button btnExit;
    @ViewInject(R.id.tv_vision)
    private TextView tvVision;
    @ViewInject(R.id.tv_setting_update)
    private TextView tvUpdate;

    private String url;


    @ViewInject(R.id.cb_switchBtn)
    private CheckBox switchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //int versionName = UpdateManager.getVersion(getApplicationContext());
        String versionName = UpdateManager.getVersionName(getApplicationContext());
        tvVision.setText("V " + versionName);
      /*  TextView version = (TextView) this.findViewById(R.id.version);
        version.setText(versionName);*/
        initToolbar();
        initView();
    }

    private void initView() {
        tvAbout.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        //layoutVision.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
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
        LoginUserInfoBean.UserBean loginInfo =
                SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId() + "";
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

                if(TextUtils.isEmpty(customerId)){
                    intent=new Intent(SettingActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(SettingActivity.this,FeedbackListActivity.class);
                    startActivity(intent);
                }
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
            case R.id.tv_setting_update:
                //版本更新
                checkUpdate();
                break;
            case R.id.layout_phone:
                //客服电话
                if(ContextCompat.checkSelfPermission(SettingActivity.this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE},1);
                    //ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
                }else{
                    initPhone();
                }

                break;
            case R.id.iv_clean:
                //退出当前账号
                if(TextUtils.isEmpty(customerId)){
                    ToastUtils.showCenter(getApplicationContext(),"你当前没有账号登录哦！");
                }else{
                    initExit(builder);
                }
                break;
        }

    }
    //拨打电话
    private void initPhone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("温馨提示");
        builder.setMessage("确定拨打085138157660吗?");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "085138157660");
                intent.setData(data);
                startActivity(intent);
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
    private void initExit(AlertDialog.Builder builder) {
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
                EnumEventBus exit = EnumEventBus.EXIT;
                EventBus.getDefault().post(new EventBusClass(exit));
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
    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.setting_title);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }
    //检查安装包是否有更新
    private void checkUpdate(){
        UpdateManager.setUpdateListener(this);
        UpdateManager.checkNewVersion(getApplicationContext());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }
    @Override
    public void checkFinished(boolean isUpdate, String content, String url) {
        this.url = url;
        if(isUpdate){//发现新版本
            tvUpdate.setVisibility(View.VISIBLE);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("版本更新");
            alert.setMessage(Html.fromHtml(content));
            alert.setCancelable(false);
            alert.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(ContextCompat.checkSelfPermission(SettingActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        //申请权限
                        ActivityCompat.requestPermissions(SettingActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                        Toast.makeText(getApplicationContext(),"请允许权限下载安装",Toast.LENGTH_SHORT).show();
                    }else{
                        downloadAPK();
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

        }else {  //已是最新版本
            tvUpdate.setVisibility(View.GONE);
           /* AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
            alert.setTitle("版本检查");
            alert.setMessage("当前已是最新版本!");
            alert.setCancelable(false);
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });

            alert.show();*/
        }
    }
    //下载安装包
    private void downloadAPK(){
        Toast.makeText(this,"已在后台下载",Toast.LENGTH_SHORT).show();
        //启动下载器下载安装包
        Intent intent = new Intent(SettingActivity.this, DownloadAPKService.class);
        intent.putExtra("url",HttpUrlUtils.DOWNLOAD_URL);
        startService(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initPhone();
                }else {
                    //用户没有授权
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("您需要设置打电话权限才能使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(SettingActivity.this);
                           // CommonUtils.getAppDetailSettingIntent(SettingActivity.this);

                        }
                    });
                    alert.show();

                }
                break;
            case 110:
                if(permissions[0].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        downloadAPK();
                    }else{
                        //提示没有权限，安装不了咯
                        Toast.makeText(getApplicationContext(),"请允许权限下载安装",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    /**
     * 设置权限界面
     * @param context
     */
    public  void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        context.startActivity(localIntent);
    }





}
