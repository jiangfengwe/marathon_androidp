package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ChangeHeadPicBean;
import com.tdin360.zjw.marathon.model.ChangeNicknameBean;
import com.tdin360.zjw.marathon.model.ChangePhoneBean;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.MyInfoBean;
import com.tdin360.zjw.marathon.model.MyInfoSexBean;
import com.tdin360.zjw.marathon.model.UserModel;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyDatePickerDialog;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.db.impl.MyInfoServiceImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.File;
import java.util.List;

/**
 *
 * 我的个人资料
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

//implements MyDatePickerDialog.OnMyDatePickerChangeListener
   /* private ImageView imageView;
    private EditText editName;
    private EditText editEmail;
    private RadioButton gender1,gender2;
    private boolean isCanEdit;
    private LinearLayout main;
    private TextView loadFail;
    //出生日期选择相关
    private TextView editBirth;
    //用于存储用户选择裁剪后的头像
    //private KProgressHUD hud;

    private MyInfoServiceImpl service;*/

    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;


    @ViewInject(R.id.layout_modify_portrait)
    private LinearLayout layoutPortrait;
    @ViewInject(R.id.layout_modify_sign)
    private LinearLayout layoutSign;
    @ViewInject(R.id.rg_modify_sex)
    private RadioGroup rgSex;
    @ViewInject(R.id.rb_boy)
    private RadioButton rbBoy;
    @ViewInject(R.id.rb_girl)
    private RadioButton rbgirl;
    @ViewInject(R.id.tv_modify_psw)
    private TextView tvPsw;
    @ViewInject(R.id.layout_modify_phone)
    private LinearLayout layoutPhone;

    @ViewInject(R.id.iv_modify_headimg)
    private ImageView ivHeadImg;
    @ViewInject(R.id.tv_modify_sign)
    private TextView tvSign;
    @ViewInject(R.id.tv_modify_phone)
    private TextView tvPhone;
    @ViewInject(R.id.tv_modify_nickname)
    private TextView tvNickName;
    @ViewInject(R.id.layout_modify_nickname)
    private LinearLayout layoutNickName;

    private ImageOptions imageOptions;
    private boolean isgender;
    private String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        imageOptions= new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        initToolbar();
        initView();
        initModifyInfo();
    }
    //修改性别信息
    private void initModifyInfo() {
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_boy:
                        flag = "false";
                        break;
                    case R.id.rb_girl:
                        flag = "true";
                        break;
                }
                //rgSex.check(R.id.rb_girl);
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String customerId = loginInfo.getId() + "";
                RequestParams params=new RequestParams(HttpUrlUtils.USER_INFO_SEX);
                params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                params.addBodyParameter("customerId",customerId);
                params.addBodyParameter("gender",flag);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("update", "onSuccess: "+result);
                        Gson gson=new Gson();
                        MyInfoSexBean myInfoSexBean = gson.fromJson(result, MyInfoSexBean.class);
                        boolean state = myInfoSexBean.isState();
                        if(state){
                            if(flag.equals("true")){
                                isgender=false;
                            }else{
                                isgender=true;
                            }
                            ToastUtils.showCenter(getApplicationContext(),myInfoSexBean.getMessage());
                            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                            LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(loginInfo.getId(), loginInfo.getHeadImg(),
                                    loginInfo.getNickName(),isgender, loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                                    loginInfo.getCustomerSign(), loginInfo.getPhone());
                            //保存用户登录数据
                            SharedPreferencesManager.saveLoginInfo(MyInfoActivity.this,userBean);
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),myInfoSexBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtils.showCenter(MyInfoActivity.this,"网络不给力,连接服务器异常!");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        //layoutLoading.setVisibility(View.GONE);
                        //hud.dismiss();

                    }
                });
            }
        });
    }

    @Subscribe
    public void onEvent(EventBusClass event){
        switch (event.getEnumEventBus()){
            case SIGN:
                //修改签名成功
                initView();
                break;
            case PHONE:
                //修改电话号码成功
                initView();
                break;
        }
        /*if(event.getEnumEventBus()== EnumEventBus.SIGN){
            //修改签名成功
            initView();
        }
        if(event.getEnumEventBus()== EnumEventBus.PHONE){
            //修改签名成功
            initView();
        }*/
    }

    private void initView() {
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        x.image().bind(ivHeadImg,loginInfo.getHeadImg(),imageOptions);
        tvNickName.setText(loginInfo.getNickName());
        tvSign.setText(loginInfo.getCustomerSign());
        String phone = loginInfo.getPhone();
        //*隐藏号码
        if(!TextUtils.isEmpty(phone) && phone.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            tvPhone.setText(sb.toString());
        }
        if( loginInfo.isGender()){
            rgSex.check(R.id.rb_boy);
        }else{
            rgSex.check(R.id.rb_girl);
        }
        Log.d("loginInfoisGender", "initView: "+loginInfo.isGender());
        layoutPortrait.setOnClickListener(this);
        layoutNickName.setOnClickListener(this);
        layoutSign.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        tvPsw.setOnClickListener(this);
    }

    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewline.setVisibility(View.GONE);
        titleTv.setText("修改个人资料");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_info;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);
        Intent intent;
        switch (v.getId()){
            case R.id.layout_modify_portrait:
                //修改头像
                alert.setTitle("更改头像");
                alert.setCancelable(false);
                alert.setItems(new String[]{"相册", "取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                           /* case 0:
                                //android 6.0 判断是否拥有打开照相机的权限
                                if(hasPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                    openCamera();
                                }else {
                                    requestPermission(Constants.CAMERA_CODE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }
                                break;*/
                            case 0:
                                //检查sd卡读取权限
                              /*  if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                                    modifyHeadPic();
                                }else {

                                    requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }*/
                                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                                    modifyHeadPic();
                                }else {
                                    if(ActivityCompat.checkSelfPermission(MyInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                        ActivityCompat.requestPermissions(MyInfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10003);
                                    }else {
                                        modifyHeadPic();
                                    }
                                }
                                break;
                            case 1:
                                dialog.dismiss();
                                break;

                        }
                    }
                });
                alert.show();
                break;
            case R.id.layout_modify_nickname:
                //修改昵称
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    showDialog();
                }else {
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    alert.setMessage("网络不可用，是否打开网络设置");
                    alert.setCancelable(false);
                    alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //打开网络设置

                            startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

                        }
                    });
                    alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }

                break;
            case R.id.layout_modify_sign:
                //修改签名
                intent=new Intent(MyInfoActivity.this,ChangeSignActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_modify_psw:
                //修改密码
                intent=new Intent(MyInfoActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_modify_phone:
                //修改手机号
                intent=new Intent(MyInfoActivity.this,ChangePhoneActivity.class);
                startActivity(intent);
                break;
        }

    }
    //PictureSelector加载相册图片图片
    public void modifyHeadPic() {
        PictureSelector.create(MyInfoActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo()// 是否可预览视频 true or false
                //.enablePreviewAudio() // 是否可播放音频 true or false
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .glideOverride(200,200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .compressMaxKB(Luban.CUSTOM_GEAR)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
                // .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                // .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                // .rotateEnabled() // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                // .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
                // .recordVideoSecond()//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //PictureAdapter adapter=new PictureAdapter(getApplicationContext(),localMedias);
                    final String cutPath = localMedias.get(0).getCutPath();
                    //adapter.setList(localMedias);
                    //adapter.notifyDataSetChanged();
                   /* layoutLoading.setVisibility(View.VISIBLE);
                    ivLoading.setBackgroundResource(R.drawable.loading_before);
                    AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                    background.start();*/
                    final KProgressHUD hud = KProgressHUD.create(this);
                    hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(1)
                            .setDimAmount(0.5f)
                            .show();
                    LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                    String customerId = loginInfo.getId()+"";
                    RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_HEAD_PIC);
                    final File file = new File(cutPath);//新建一个file文件
                    params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                    params.addBodyParameter("customerId",customerId);
                    params.addBodyParameter("uploadedFile",file);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("headpic", "onSuccess: "+result);
                            Gson gson=new Gson();
                            ChangeHeadPicBean changeHeadPicBean = gson.fromJson(result, ChangeHeadPicBean.class);
                            boolean state = changeHeadPicBean.isState();
                            if(state){
                                String headImg = changeHeadPicBean.getUser().getHeadImg();
                                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                                LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(loginInfo.getId(), headImg,
                                        loginInfo.getNickName(), loginInfo.isGender(), loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                                        loginInfo.getCustomerSign(), loginInfo.getPhone());
                                //保存用户登录数据
                                SharedPreferencesManager.saveLoginInfo(MyInfoActivity.this,userBean);
                                initView();
                                x.image().bind(ivHeadImg,cutPath,imageOptions);
                                 //通知个人中心更新头像
                                Intent intent = new Intent(MyFragment.ACTION);
                                sendBroadcast(intent);
                            }else{
                                ToastUtils.showCenter(getApplicationContext(),changeHeadPicBean.getMessage());
                            }
                           /* try {
                                JSONObject jsonObject=new JSONObject(result);
                                boolean state = jsonObject.getBoolean(MainConstant.STATE);
                                String message = jsonObject.getString(MainConstant.MESSAGE);
                                if(state){
                                    PreUtils sharePreference=new PreUtils();
                                    sharePreference.saveBgPath(getApplication(),cutPath);
                                    EnumEventBus em=EnumEventBus.USERNAME;
                                    EventBus.getDefault().post(new LanguageEvent(em));
                                    x.image().bind(imageViewHeadPic,cutPath,imageOptions);
                                    //clearPic();
                                    mPopupWindow.dismiss();
                                }else{
                                    Intent intent=new Intent(ModifyPersonalActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            ToastUtils.showCenter(MyInfoActivity.this,"网络不给力,连接服务器异常!");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {
                            //layoutLoading.setVisibility(View.GONE);
                            hud.dismiss();
                        }
                    });

                    break;

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 10003:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    modifyHeadPic();
                    //用户授权成功
                }else {
                    //用户没有授权
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("您需要设置权限才能使用该功能");
                    alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(MyInfoActivity.this);
                        }
                    });
                    alert.show();

                }
                break;
            default:
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
    private void showDialog() {
        final Dialog dialog=new Dialog(MyInfoActivity.this, R.style.MyDialog);
        View inflate = View.inflate(MyInfoActivity.this, R.layout.item_mydialog_nickname, null);
        dialog.setContentView(inflate);
        final EditText etNickname = (EditText) inflate.findViewById(R.id.et_nickname);
        Button btnNickname = (Button) inflate.findViewById(R.id.btn_nickname);
        btnNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickName = etNickname.getText().toString().trim();
                //ToastUtils.show(getApplicationContext(),etNickname.getText().toString());
                //修改昵称
                if(TextUtils.isEmpty(nickName)){
                    ToastUtils.showCenter(getApplicationContext(),"修改昵称不能为空");
                    return;
                }
                if(nickName.length()>5){
                    ToastUtils.showCenter(getApplicationContext(),"昵称长度不能超过5哦空");
                    return;
                }
               /* layoutLoading.setVisibility(View.VISIBLE);
                ivLoading.setBackgroundResource(R.drawable.loading_before);
                AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                background.start();*/
                final KProgressHUD hud = KProgressHUD.create(MyInfoActivity.this);
                hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(1)
                        .setDimAmount(0.5f)
                        .show();
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String customerId = loginInfo.getId()+"";
                RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_NICKNAME);
                params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                params.addBodyParameter("customerId",customerId);
                params.addBodyParameter("nickName",nickName);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("nickname", "onSuccess: "+result);
                        Gson gson=new Gson();
                        ChangeNicknameBean changeNicknameBean = gson.fromJson(result, ChangeNicknameBean.class);
                        boolean state = changeNicknameBean.isState();
                        if(state){
                            ToastUtils.showCenter(getApplicationContext(),changeNicknameBean.getMessage());
                            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                            LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(loginInfo.getId(), loginInfo.getHeadImg(),
                                    nickName, loginInfo.isGender(), loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                                    loginInfo.getCustomerSign(), loginInfo.getPhone());
                            //保存用户登录数据
                            SharedPreferencesManager.saveLoginInfo(MyInfoActivity.this,userBean);
                            initView();
                            tvNickName.setText(nickName);
                            //通知个人中心更新昵称
                            Intent intent = new Intent(MyFragment.ACTION);
                            sendBroadcast(intent);
                            //tvNickName.setText(nickName);
                            dialog.dismiss();
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),changeNicknameBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtils.showCenter(MyInfoActivity.this,"网络不给力,连接服务器异常!");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        //layoutLoading.setVisibility(View.GONE);
                        hud.dismiss();

                    }
                });

            }


        });
        dialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(getApplicationContext());
    }


    //打开相机
    private static  final  int OpenCameraRequestCode =1;
    private void openCamera(){
        try {
            Uri u = Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.jpg"));
            //调用照相机
            Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION,OpenCameraRequestCode);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,u);
            startActivityForResult(intent,OpenCameraRequestCode);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MyInfoActivity.this,"sd卡不可用",Toast.LENGTH_SHORT).show();
        }

    }


    /**
            * 打开相机权限授权成功
     */
    @Override
    public void doCameraPermission() {
        openCamera();
    }

    /*private void initView() {

        this.service = new MyInfoServiceImpl(this);
        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        this.editName = (EditText) this.findViewById(R.id.name);

        this.editEmail = (EditText) this.findViewById(R.id.email);
        this.editBirth = (TextView) this.findViewById(R.id.birthday);
        this.gender1 = (RadioButton) this.findViewById(R.id.radio1);
        this.gender2 = (RadioButton) this.findViewById(R.id.radio2);
        this.main = (LinearLayout) this.findViewById(R.id.main);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);

        //initHUD();

        //加载失败点击重新加载
        this.loadFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loadData();
            }
        });

        this.navRightItemTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (navRightItemTitle().getText().toString().equals("编辑")) {

                    setIsEdit(true);

                } else {
                    //保存
                    submit();

                }

            }
        });


            //更换头像
            this.findViewById(R.id.head_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);

                        alert.setTitle("更改头像");
                        alert.setCancelable(false);
                        alert.setItems(new String[]{"拍照", "相册", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0:
                                        //android 6.0 判断是否拥有打开照相机的权限
                                        if(hasPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                                            openCamera();
                                        }else {

                                            requestPermission(Constants.CAMERA_CODE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        }
                                        break;
                                    case 1:
                                        //检查sd卡读取权限
                                        if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                                            selectPic();
                                        }else {

                                            requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        }
                                        break;
                                    case 2:
                                        dialog.dismiss();
                                        break;

                                }
                            }
                        });
                        alert.show();


                }
            });

            this.findViewById(R.id.birthdayBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isCanEdit) {
                        MyDatePickerDialog dialog = new MyDatePickerDialog(MyInfoActivity.this);
                        dialog.setOnMyDatePickerChangeListener(MyInfoActivity.this);
                        dialog.show();
                    }
                }
            });


        showHeadImage();

        //加载用户信息
        loadData();

        }
    *//**
     * 初始化提示框
     *//*
    *//*private void initHUD(){

        //显示提示框
        this.hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(true);
        hud.setAnimationSpeed(1);
        hud.setDimAmount(0.5f);

    }*//*
    *//**
     * 显示头像
     *//*
    private void showHeadImage(){

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
//                .setLoadingDrawableId(R.drawable.signup_photo)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.signup_photo)//加载失败后默认显示图片
                .build();
          x.image().bind(imageView,SharedPreferencesManager.getLoginInfo(this).getImageUrl(),imageOptions);


    }
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {


        *//**
         * 判断网络是否处于可用状态
         *//*
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
            httpRequest();
        } else {



            //获取缓存数据

            UserModel userModel = service.getUserModel(SharedPreferencesManager.getLoginInfo(this).getName());
            if(userModel==null){
                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);

                //如果获取得到缓存数据则加载本地数据
                //如果缓存数据不存在则需要用户打开网络设置

                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setMessage("网络不可用，是否打开网络设置");
                alert.setCancelable(false);
                alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //打开网络设置

                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

            }else {

                showUserData(userModel);
            }



        }
    }

    *//**
     * 获取网络数据
     *//*
    private void httpRequest(){

        service.delete();
         loadFail.setVisibility(View.GONE);

         //hud.show();
        RequestParams params = new RequestParams(HttpUrlUtils.GET_MYINFO);
        params.addQueryStringParameter("phone",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    String name = json.getString("Name");

                    String email = json.getString("Email");
                    String birthDate = json.getString("BirthDate");
                    boolean gender = json.getBoolean("Gender");

                    UserModel model = new UserModel("",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName(),name,email,birthDate,gender);
                      showUserData(model);
                      service.addInfo(model);

                    //设置信息不可编辑
                    setIsEdit(false);
                    loadFail.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

               Toast.makeText(MyInfoActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();

                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                //hud.dismiss();



            }
        });



}

    //显示用户信息
    private void showUserData(UserModel model){

        this.editName.setText((model.getName()==null || model.getName().equals("null"))? "未设置":model.getName());
        this.editEmail.setText((model.getEmail()==null || model.getEmail().equals("null"))? "未设置":model.getEmail());
        this.editBirth.setText((model.getBirthday()==null || model.getBirthday().equals("null"))? "未设置":model.getBirthday());
        if(model.isGender()){
           gender1.setChecked(true);
        }else {
          gender2.setChecked(true);
        }

        main.setVisibility(View.VISIBLE);
    }

    //打开相机
    private static  final  int OpenCameraRequestCode =1;
    private void openCamera(){

            try {


                Uri u = Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.jpg"));
                //调用照相机
                Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION,OpenCameraRequestCode);
                 intent.putExtra(MediaStore.EXTRA_OUTPUT,u);
                startActivityForResult(intent,OpenCameraRequestCode);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MyInfoActivity.this,"sd卡不可用",Toast.LENGTH_SHORT).show();
            }

    }

    //打开相册选择图片
    private static  final  int SelectRequestCode =2;
    private void selectPic(){

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SelectRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode!= RESULT_OK){

            return;
        }

//通过照相或者选择本地相册返回这里调用系统图片裁剪
        if(requestCode==SelectRequestCode)

        {
            //将选择回来的照片进行裁剪
            Uri uri = data.getData();
            startPhotoZoom(uri);
        }else if(requestCode==OpenCameraRequestCode){

            Uri uri = Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.png"));
            startPhotoZoom(uri);
        }

        //图片裁剪后返回这里
        if(requestCode==Constants.EDIT_IMAGE_CODE){

             upLoadFile(new File(getExternalFilesDir("images").getPath()+"/temp.png"));

        }

    }


    *//**
     * 打开相机权限授权成功
     *//*
    @Override
    public void doCameraPermission() {

        openCamera();
    }

    *//**
     * sd卡读取权限授权成功
      *//*
    @Override
    public void doSDCardPermission() {

         selectPic();
    }

    *//**
     * 上传文件
     * @param file 文件路径
     *//*
    private void upLoadFile(final File file){


        //hud.show();
        RequestParams params = new RequestParams(HttpUrlUtils.UPLOAD_IMAGE);
        LoginModel info = SharedPreferencesManager.getLoginInfo(this);
        params.addQueryStringParameter("phone",info.getName());
        params.addQueryStringParameter("password",info.getPassword());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.setMultipart(true);
        try {
            params.addBodyParameter("uploadedFile",file,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject json  = new JSONObject(result);
                    JSONObject message = json.getJSONObject("EventMobileMessage");
                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");
                    String url = json.getString("AvatarUrl");

                    if(success){
                        Toast.makeText(MyInfoActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                        //上传成功将新的图片地址保存起来

                         SharedPreferencesManager.updateImageUrl(getApplicationContext(),url);
                        //显示上传的头像
                        showHeadImage();

                        *//**
                         * 通知个人中心更新头像
                         *//*
                         Intent intent = new Intent(MyFragment.ACTION);
                         sendBroadcast(intent);

                    }else {

                        Toast.makeText(MyInfoActivity.this,reason,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(main,"头像上传失败",Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            Snackbar.make(main,"头像上传失败",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                //hud.dismiss();

            }
        });

    }

    //保存更改信息
    private void submit() {


//         保存个人信息之前判断网络是否可用
        if(!NetWorkUtils.isNetworkAvailable(this)){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String birthday = editBirth.getText().toString().trim();
         boolean gender;

         if(gender1.isChecked()){
             gender=true;//男
         }else {
             gender=false;//女
         }

         //hud.show();

        RequestParams params = new RequestParams(HttpUrlUtils.CHANGE_MYINFO);
        params.addBodyParameter("phone", SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        params.addBodyParameter("Email",email);
        params.addBodyParameter("name",name=="未设置"?"":name);
        params.addBodyParameter("Gender",gender+"");
        params.addBodyParameter("BirthDate",birthday=="未设置"?"":birthday);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("Password",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getPassword());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);



                    boolean success = json.getBoolean("Success");
                    String reason = json.getString("Reason");
                    Toast.makeText(MyInfoActivity.this,reason,Toast.LENGTH_SHORT).show();
                    if(success){

                        setIsEdit(false);
                        isCanEdit=false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(MyInfoActivity.this,"网络错误或无法访问服务器!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                //hud.dismiss();

            }
        });


    }

//    设置edit是否可编辑
    private void isEdit(boolean value, EditText editText) {

        if (value) {

            editText.setFocusable(true);

            editText.setFocusableInTouchMode(true);

            editText.setFilters(new InputFilter[] { new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return null;

                }

            } });

        } else {

           //设置不可获取焦点

            editText.setFocusable(false);

            editText.setFocusableInTouchMode(false);

           //输入框无法输入新的内容

            editText.setFilters(new InputFilter[] { new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return source.length() < 1 ? dest.subSequence(dstart, dend) : "";

                }

            } });

        }

    }

   private void setIsEdit(boolean isEdit){

       if(isEdit){


           navRightItemTitle().setText("保存");


       }else {

           navRightItemTitle().setText("编辑");


       }
       this.gender1.setClickable(isEdit);
       this.gender2.setClickable(isEdit);
       this.isCanEdit=isEdit;
       this.isEdit(isEdit,editName);
       this.isEdit(isEdit,editEmail);

   }
    //返回
    public void back(View view) {


          checkSave();

    }


    @Override
    public void onChange(int year, int month, int day) {

        editBirth.setText(new StringBuilder().append( year).append("-").append(month).append("-").append(day));
    }

    @Override
    public void onBackPressed() {



        checkSave();

    }


    //检查用户编辑是否保存
    private void checkSave(){


        if(isCanEdit){

            AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);

            alert.setTitle("信息尚未保存,确定要退出吗?");
            alert.setCancelable(false);
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.show();
        }else {

            finish();
        }
    }


    *//**
     * 裁剪图片方法实现
     * @param uri
     *//*
    public void startPhotoZoom(Uri uri) {


        *//*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
         * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
         * 制做的了...吼吼
         *//*
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image*//*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        intent.putExtra("output", Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.png")));
        intent.putExtra("outputFormat", "PNG");
        startActivityForResult(intent,Constants.EDIT_IMAGE_CODE);
    }*/

}
