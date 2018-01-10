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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.ChangeHeadPicBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.MyCircleBean;
import com.tdin360.zjw.marathon.model.MyCircleBgBean;
import com.tdin360.zjw.marathon.model.MyCircleDeleteBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 我的动态
 */

public class MyCircleActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    @ViewInject(R.id.iv_my_circle_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_my_circle_pic)
    private ImageView ivPic;
    @ViewInject(R.id.iv_mycircle_bg)
    private ImageView layoutBg;
    @ViewInject(R.id.layout_my_circle)
    private RelativeLayout layout;

    @ViewInject(R.id.rv_my_circle)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    private boolean flag=false;
    @ViewInject(R.id.springView)
    private SpringView springView;
    private MyCircleBean.ModelBean model1=new MyCircleBean.ModelBean();
    private List<MyCircleBean.ModelBean.BJDynamicListModelBean> bjDynamicListModel=new ArrayList<>();

    ImageOptions imageOptions;

    int firstPosition;
    boolean isDragging;//判断scroll是否是用户主动拖拽
    boolean isScrolling;//判断scroll是否处于滑动中


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_circle_bg)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_circle_bg)//加载失败后默认显示图片
                .build();
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initView();
        initBg();
        initNet();
        //initData();
        //initNet();
    }
    private void initBg() {
          String pictureUrl = SharedPreferencesManager.readBgPath(getApplicationContext());
        if(TextUtils.isEmpty(pictureUrl)){
            layoutBg.setImageResource(R.drawable.my_circle_bg);
        }else{
            x.image().bind(layoutBg,pictureUrl,imageOptions);
        }

    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initData();
                        break;

                }
            }
        });
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
       // bjDynamicListModel.clear();
        final LoginUserInfoBean.UserBean model = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = model.getId() + "";
        RequestParams params=new RequestParams(HttpUrlUtils.MY_CIRCLE);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("customerId",customerId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mycircle", "onSuccess: "+result);
                Gson gson=new Gson();
                MyCircleBean myCircleBean = gson.fromJson(result, MyCircleBean.class);
                boolean state = myCircleBean.isState();
                if(state){
                    model1= myCircleBean.getModel();
                    totalPage=model1.getTotalPages();
                    bjDynamicListModel.addAll(model1.getBJDynamicListModel()) ;
                  /*  if(bjDynamicListModel.size()<=0){
                        ToastUtils.showCenter(getApplicationContext(),"还没有发布动态哦！");
                    }*/
                    if(bjDynamicListModel.size()<=0){
                        mErrorView.show(rvCircle,"还没有发布动态哦!",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvCircle);
                    }
                }else{
                    ToastUtils.showCenter(getApplicationContext(),myCircleBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvCircle,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(MyCircleActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjDynamicListModel);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        adapter=new RecyclerViewBaseAdapter<MyCircleBean.ModelBean.BJDynamicListModelBean>(getApplicationContext(),
                bjDynamicListModel,R.layout.item_my_circle) {
            @Override
            protected void onBindNormalViewHolder(final NormalViewHolder holder, final MyCircleBean.ModelBean.BJDynamicListModelBean model) {
                holder.setText(R.id.tv_mycircle_date,model.getReleaseTimeStr());
                holder.setText(R.id.tv_mycircle_content,model.getDynamicsContent());
                TextView tvContent = (TextView) holder.getViewById(R.id.tv_mycircle_content);
                //删除动态
                TextView tvDelete = (TextView) holder.getViewById(R.id.tv_my_circle_delete);
                //删除动态
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.support.v7.app.AlertDialog.Builder normalDialog =new android.support.v7.app.AlertDialog.Builder(MyCircleActivity.this);
                        normalDialog.setMessage("是否删除该动态");
                        normalDialog.setPositiveButton("是",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        initDelete();
                                    }
                                });
                        normalDialog.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //...To-do
                                        dialog.dismiss();
                                    }
                                });
                        // 显示
                        normalDialog.show();

                    }
                    private void initDelete() {
                        /*layoutLoading.setVisibility(View.VISIBLE);
                        ivLoading.setBackgroundResource(R.drawable.loading_before);
                        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                        background.start();*/
                        final KProgressHUD hud = KProgressHUD.create(MyCircleActivity.this);
                        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setCancellable(true)
                                .setAnimationSpeed(1)
                                .setDimAmount(0.5f)
                                .show();
                        LoginUserInfoBean.UserBean modelInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                        String customerId = modelInfo.getId() + "";
                        RequestParams params=new RequestParams(HttpUrlUtils.MY_CIRCLE_DELETE);
                        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                        params.addBodyParameter("customerId",customerId);
                        params.addBodyParameter("dynamicId",model.getId()+"");
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("delete", "onSuccess: "+result);
                                Gson gson=new Gson();
                                MyCircleDeleteBean myCircleDeleteBean = gson.fromJson(result, MyCircleDeleteBean.class);
                                boolean state = myCircleDeleteBean.isState();
                                if(state){
                                    bjDynamicListModel.remove(getPosition(holder));
                                    adapter.notifyDataSetChanged();

                                }else{
                                    ToastUtils.showCenter(getApplicationContext(),myCircleDeleteBean.getMessage());
                                }

                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                //mErrorView.show(rvCircle,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                                ToastUtils.showCenter(MyCircleActivity.this,"网络不给力,连接服务器异常!");
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {
                                hud.dismiss();
                                //layoutLoading.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                //九宫格展示图片
                NineGridView nine = (NineGridView) holder.getViewById(R.id.my_circle_nineGrid);
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                List<MyCircleBean.ModelBean.BJDynamicListModelBean.BJDynamicsPictureListModel> bjDynamicsPictureListModel
                        = model.getBJDynamicsPictureListModel();
                String str="http://www.eaglesoft.org/public/UploadFiles/image/20141017/20141017152856_751.jpg";
                for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                    String pictureUrl = bjDynamicsPictureListModel.get(i).getPictureUrl();
                    String thumbPictureUrl = bjDynamicsPictureListModel.get(i).getThumbPictureUrl();
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(thumbPictureUrl);
                    info.setBigImageUrl(pictureUrl);
                    imageInfo.add(info);
                }
                final ArrayList<String> image= new ArrayList<>();
                for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                    image.add(bjDynamicsPictureListModel.get(i).getPictureUrl());
                }
                nine.setAdapter(new NineGridViewAdapter(MyCircleActivity.this, imageInfo) {
                    @Override
                    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                        super.onImageItemClick(context, nineGridView, index, imageInfo);
                        /*Intent intent=new Intent(MyCircleActivity.this,PhotoBrowseActivity.class);
                        intent.putStringArrayListExtra("list",image);
                        startActivity(intent);*/
                        ImageView imageView1=new ImageView(MyCircleActivity.this);
                        //Log.d("pictureList.size()", "onEvent: "+pictureList.size());
                        MNImageBrowser.showImageBrowser(MyCircleActivity.this,imageView1,index, image);
                    }
                });
            }

            @Override
            public void onBindHeaderViewHolder(HeaderViewHolder holder) {
                super.onBindHeaderViewHolder(holder);
                holder.setText(R.id.tv_mycircle_name,model1.getNickName());
                holder.setText(R.id.tv_mycircle_sign,model1.getSign());

            }
        };
        adapter.addHeaderView(R.layout.item_my_circle_head);
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rvCircle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 判断是否滚动超过一屏
                    if (firstVisibleItemPosition == 0) {
                        layout.setBackgroundColor(Color.parseColor("#00000000"));
                    } else if(firstVisibleItemPosition == 1){
                        layout.setBackgroundColor(Color.parseColor("#50ff621a"));
                    } else {
                        layout.setBackgroundColor(Color.parseColor("#ff621a"));
                    }

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {//拖动中
                    layout.setBackgroundColor(Color.parseColor("#ff621a"));
                }
                /*switch(newState) {
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        if (!isDragging && !isScrolling) {
                            isScrolling = true; //a scrolling occurs
                            layout.setBackgroundColor(Color.parseColor("#ff621a"));
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        isDragging = true; //如果是用户主动滑动recyclerview，则不触发位置计算。
                        layout.setBackgroundColor(Color.parseColor("#30ff621a"));
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        layout.setBackgroundColor(Color.parseColor("#00000000"));
                      *//*  if (!isDragging && isScrolling){
                            isDragging = false;
                            isScrolling = false;
                            firstPosition = outputPortLayoutMgr.findFirstVisibleItemPosition();
                            int lastPos = outputPortLayoutMgr.findLastVisibleItemPosition();
                            //N.B.: firstVisibleItemPosition is not the first child of layoutmanager
                           View itemView = layoutMgr.getChildAt(position-(int) outputPortLayoutMgr.getChildAt(0).getTag());  //由于滚动事件会多次触发IDLE状态，我们只需要在第一次IDLE被触发时获取ItemView。
                        }*//*
                        break;*/

                   /* Log.d("newState", "onScrollStateChanged: " + newState);
                    if (newState > 0) {
                        ToastUtils.showCenter(getContext(), "addOnScrollListener");
                        if (newState == 1) {
                            layout.setBackgroundColor(Color.parseColor("#30ff621a"));
                        }
                        if (newState == 2) {
                            layout.setBackgroundColor(Color.parseColor("#ff621a"));
                        }
                    } else {
                        layout.setBackgroundColor(Color.parseColor("#00000000"));
                    }*/


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjDynamicListModel.clear();
                pageIndex=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
                if(totalPage<=pageIndex){
                    return;
                }
                if(totalPage>pageIndex){
                    pageIndex++;
                    initData();
                }

            }
        });
        springView.setHeader(new DefaultHeader(getApplicationContext()));
        springView.setFooter(new DefaultFooter(getApplicationContext()));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_circle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_my_circle_back:
                //返回
                finish();
                break;
            case R.id.iv_my_circle_pic:
                //切换背景图
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                    modifyHeadPic();
                }else {
                    if(ActivityCompat.checkSelfPermission(MyCircleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MyCircleActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                    }else {
                        modifyHeadPic();
                    }
                }
                break;
        }

    }
    //PictureSelector加载相册图片图片
    public void modifyHeadPic() {
        PictureSelector.create(MyCircleActivity.this)
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
                //.circleDimmedLayer(true)// 是否圆形裁剪 true or false
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
                    final KProgressHUD hud = KProgressHUD.create(this);
                    hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(1)
                            .setDimAmount(0.5f)
                            .show();
                  /*  layoutLoading.setVisibility(View.VISIBLE);
                    ivLoading.setBackgroundResource(R.drawable.loading_before);
                    AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                    background.start();*/
                    LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                    String customerId = loginInfo.getId()+"";
                    final File file = new File(cutPath);//新建一个file文件
                    RequestParams params=new RequestParams(HttpUrlUtils.MY_CIRCLE_PICTURE);
                    params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                    params.addBodyParameter("customerId",customerId);
                    params.addBodyParameter("uploadedFile",file);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("circlepic", "onSuccess: "+result);
                            Gson gson=new Gson();
                            MyCircleBgBean myCircleBgBean = gson.fromJson(result, MyCircleBgBean.class);
                            boolean state = myCircleBgBean.isState();
                            if(state){
                                String pictureUrl = myCircleBgBean.getPictureUrl();
                                x.image().bind(layoutBg,pictureUrl,imageOptions);
                                SharedPreferencesManager.saveBgPath(getApplicationContext(),pictureUrl);
                            }else{
                                ToastUtils.showCenter(getApplicationContext(),myCircleBgBean.getMessage());
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
                            hud.dismiss();
                            //layoutLoading.setVisibility(View.GONE);
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
            case 101:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    modifyHeadPic();
                    //用户授权成功
                }else {
                    //用户没有授权
                    AlertDialog.Builder alert = new AlertDialog.Builder(MyCircleActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("您需要设置权限才能使用该功能");
                    alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(MyCircleActivity.this);
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
