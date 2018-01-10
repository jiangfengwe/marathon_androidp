package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.GridImageAdapter;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.PublishBean;
import com.tdin360.zjw.marathon.ui.fragment.CircleFragment;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 发布动态
 */

public class PublishActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.tv_publish_cancel)
    private TextView tvCancel;
    @ViewInject(R.id.tv_publish)
    private TextView tvPublish;
    @ViewInject(R.id.et_publish_content)
    private EditText etContent;
    @ViewInject(R.id.layout_publish)
    private LinearLayout layoutAdd;
    @ViewInject(R.id.rv_publish)
    private RecyclerView rvPublish;
    private GridImageAdapter adapterPic;
    private List<LocalMedia> localMedias=new ArrayList<>();

    //private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRecyclerView();
    }
    private void initView() {
        tvCancel.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        layoutAdd.setOnClickListener(this);
    }

    private void initRecyclerView() {
        adapterPic=new GridImageAdapter(getContext(),onAddPicClickListener);
        adapterPic.setList(localMedias);
        rvPublish.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvPublish.setAdapter(adapterPic);
    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener=new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                initPic();
            }else {
                if(ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PublishActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},8);
                }else {
                    initPic();
                }
            }
            //initPic();
        }
    };
    private void initPic() {
        PictureSelector.create(PublishActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo()// 是否可预览视频 true or false
                //.enablePreviewAudio() // 是否可播放音频 true or false
                .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressGrade(Luban.THIRD_GEAR)
                .glideOverride(130,130)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                //.cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                // .compressMaxKB(Luban.CUSTOM_GEAR)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
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
                    localMedias= PictureSelector.obtainMultipleResult(data);
                    adapterPic.setList(localMedias);
                    adapterPic.notifyDataSetChanged();
                    break;
            }
        }
    }


    @Override
    public int getLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_publish_cancel:
                //取消
                finish();
                break;
            case R.id.tv_publish:
                //发布
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initData();
                }else {
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    AlertDialog.Builder alert = new AlertDialog.Builder(PublishActivity.this);
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
        }

    }

    private void initData() {
        String dynamicContent = etContent.getText().toString().trim();
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId()+"";
            if(TextUtils.isEmpty(dynamicContent)&&localMedias.size()==0){
                ToastUtils.showCenter(getApplicationContext(),"发布内容和图片不能同时为空");
            }else{
                initSure(dynamicContent, customerId);
                return;
            }
            if(localMedias.size()==0&&TextUtils.isEmpty(dynamicContent)){
                ToastUtils.showCenter(getApplicationContext(),"发布内容和图片不能同时为空");
            }else{
                initSure(dynamicContent, customerId);
                return;
            }

    }

    private void initSure(String dynamicContent, String customerId) {
        final KProgressHUD hud = KProgressHUD.create(PublishActivity.this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        /*layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        RequestParams params=new RequestParams(HttpUrlUtils.PUBLISH_CIRCLE);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("dynamicContent",dynamicContent);
        params.addBodyParameter("customerId",customerId);
        for(int i=0;i<localMedias.size();i++ ){
            params.addBodyParameter("file"+i,new File(localMedias.get(i).getCompressPath()),"image/jpeg",i+".jpg");
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("publish", "onSuccess: "+result);
                Gson gson=new Gson();
                PublishBean publishBean = gson.fromJson(result, PublishBean.class);
                boolean state = publishBean.isState();
                if(state){
                    // ToastUtils.showCenter(getApplicationContext(),publishBean.getMessage());
                    CircleFragment.instance.initData(1);
                    clearPic();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                    imm.hideSoftInputFromWindow(tvPublish.getWindowToken(), 0);
                    finish();
                    //layoutRefresh.setVisibility(View.GONE);
                  /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();*/
            }else{
                ToastUtils.showCenter(getApplicationContext(),publishBean.getMessage());
            }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapterPic.notifyDataSetChanged();
                //layoutLoading.setVisibility(View.GONE);
                hud.dismiss();

            }

            @Override
            public void onWaiting() {
                ToastUtils.showCenter(getContext(),"onWaiting");
            }

            @Override
            public void onStarted() {
                ToastUtils.showCenter(getContext(),"onStarted");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                ToastUtils.showCenter(getContext(),"current");
                Log.d("long", "onLoading: "+current);

            }
        });
    }
    private void clearPic() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(PublishActivity.this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(PublishActivity.this);
                } else {
                    Toast.makeText(PublishActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 8:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initPic();
                    //用户授权成功
                }else {
                    //用户没有授权
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(PublishActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("你需要设置权限才可以使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(PublishActivity.this);
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
}
