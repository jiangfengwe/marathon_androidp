package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.GridImageAdapter;
import com.tdin360.zjw.marathon.model.HotelCommentBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
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

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 评价
 */

public class HotelCommentActivity extends BaseActivity {

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;
    @ViewInject(R.id.navRightItemTitle)
    private TextView tvSubmit;

    @ViewInject(R.id.ratingBar_order_comment_level)
    private RatingBar ratingBar;
    @ViewInject(R.id.tv_travelcomment_score)
    private TextView tvScore;
    @ViewInject(R.id.et_comment_content)
    private EditText etContent;
    @ViewInject(R.id.btn_hotel_comment_sure)
    private Button btnSure;

    @ViewInject(R.id.rv_hotel_comment)
    private RecyclerView rvComment;
    private GridImageAdapter adapterPic;
    private List<LocalMedia> localMedias=new ArrayList<>();

    private String rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
        initRecyclerView();

    }
    private void initRecyclerView() {
        adapterPic=new GridImageAdapter(getContext(),onAddPicClickListener);
        adapterPic.setList(localMedias);
        rvComment.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvComment.setAdapter(adapterPic);
    }
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener=new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                initPic();
            }else {
                if(ActivityCompat.checkSelfPermission(HotelCommentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(HotelCommentActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},81);
                }else {
                    initPic();
                }
            }

        }
    };
    private void initPic() {
        PictureSelector.create(HotelCommentActivity.this)
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 81:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initPic();
                    //用户授权成功
                }else {
                    //用户没有授权
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(HotelCommentActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("你需要设置权限才可以使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(HotelCommentActivity.this);
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
    private void initView() {

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }
    private void initNet() {
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
            initData();
        }else {
            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
    }
    private void initData() {
        String evaluateContent = etContent.getText().toString().trim();
        if(TextUtils.isEmpty(rating)){
            ToastUtils.showCenter(getApplicationContext(),"评分不能为空");
            return;
        }
        if(TextUtils.isEmpty(evaluateContent)){
            ToastUtils.showCenter(getApplicationContext(),"评论内容不能为空");
            return;
        }
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
       /* layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        String orderId = getIntent().getStringExtra("orderId");
        LoginUserInfoBean.UserBean loginInfo =
                SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId();
        RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_COMMENT);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("orderId",orderId);
        params.addBodyParameter("customerId",customerId);
        params.addBodyParameter("evaluateContent",evaluateContent);
        params.addBodyParameter("scoring",rating);
        for(int i=0;i<localMedias.size();i++ ){
            params.addBodyParameter("file"+i,new File(localMedias.get(i).getCompressPath()),"image/jpeg",i+".jpg");
        }
        params.setConnectTimeout(5000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("hotelcomment", "onSuccess: "+result);
                Gson gson=new Gson();
                HotelCommentBean hotelCommentBean = gson.fromJson(result, HotelCommentBean.class);
                boolean state = hotelCommentBean.isState();
                if(state){
                    //ToastUtils.showCenter(getApplicationContext(),hotelCommentBean.getMessage());
                    showDialog();
                }else{
                    ToastUtils.showCenter(getApplicationContext(),hotelCommentBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");

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
    private void showDialog() {
        final Dialog dialog=new Dialog(getContext(), R.style.MyDialog);
        View inflate = View.inflate(getContext(), R.layout.item_mydialog, null);
        dialog.setContentView(inflate);
        inflate.findViewById(R.id.layout_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void initToolbar() {
        rating= ratingBar.getRating()+"";
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int rating1 = (int) ratingBar.getRating();
                tvScore.setText(rating1+"");

            }
        });
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText("评价");
        titleTv.setTextColor(Color.WHITE);
        tvSubmit.setText("提交");
        tvSubmit.setTextColor(Color.WHITE);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initNet();
            }
        });
        showBack(toolbar,imageView);

    }
    @Override
    public int getLayout() {
        return R.layout.activity_comment;
    }
}
