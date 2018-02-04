package com.tdin360.zjw.marathon.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
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
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.NineGridViewWrapper;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.Bean;
import com.tdin360.zjw.marathon.model.CircleShareBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.PraiseBean;
import com.tdin360.zjw.marathon.ui.activity.CircleDetailActivity;
import com.tdin360.zjw.marathon.ui.activity.CircleMessageActivity;
import com.tdin360.zjw.marathon.ui.activity.LoginActivity;
import com.tdin360.zjw.marathon.ui.activity.MyCircleActivity;
import com.tdin360.zjw.marathon.ui.activity.PhotoBrowseActivity;
import com.tdin360.zjw.marathon.ui.activity.PublishActivity;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 圈子
 * @author zhangzhijun
 * Created by admin on 17/3/9.
 */

public class CircleFragment extends BaseFragment implements View.OnClickListener{

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.iv_circle_publish)
    private ImageView rightImage;
    @ViewInject(R.id.iv_circle_notice_show)
    private ImageView ivShow;
    @ViewInject(R.id.iv_circle_back_top)
    private ImageView mImageViewRebackTop;
    @ViewInject(R.id.iv_circle_notice)
    private ImageView imageView;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;

    @ViewInject(R.id.rv_circle)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    private List<Bean.ModelBean.BJDynamicListModelBean> bjDynamicListModel=new ArrayList<>();
    private Bean.ModelBean.BJDynamicListModelBean bjDynamicListModelBean=new Bean.ModelBean.BJDynamicListModelBean();
    private String customerId;

    private ShareAction action;

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    @ViewInject(R.id.springView)
    private SpringView springView;


    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    ImageOptions imageOptions,imageOptionsCircle;

    private boolean flag=false;
    private int index;
    private boolean isShare;

    private boolean mIsRefreshing;

    public static CircleFragment instance;
    public CircleFragment() {
        instance=this;
        // Required empty public constructor
    }
    public static CircleFragment newInstance(){

        return   new CircleFragment();
    }
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()==EnumEventBus.CIRCLECOMMENT){
            initData(1);
        }
        if(event.getEnumEventBus()==EnumEventBus.CIRCLE){
            initData(1);
        }
        if(event.getEnumEventBus()==EnumEventBus.NOTICE){
            boolean open = SharedPreferencesManager.getNotice(getContext());
            if(open){
                ivShow.setVisibility(View.VISIBLE);
            }else{
                ivShow.setVisibility(View.GONE);
            }
        }
        if(event.getEnumEventBus()==EnumEventBus.CIRCLENOTICE){
            boolean open = SharedPreferencesManager.getNotice(getContext());
            if(open){
                ivShow.setVisibility(View.VISIBLE);
            }else{
                ivShow.setVisibility(View.GONE);
            }
        }
        if(event.getEnumEventBus()==EnumEventBus.EXIT){
            initData(1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!flag){
            EventBus.getDefault().register(this);
            flag=!flag;
        }
        View inflate = inflater.inflate(R.layout.fragment_circle, container, false);
        return  inflate;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
        customerId= loginInfo.getId()+"";
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
               // .setCrop(true)
                //.setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        imageOptionsCircle = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        boolean open = SharedPreferencesManager.getNotice(getContext());
        /*if(open){
            ivShow.setVisibility(View.VISIBLE);
        }else{
            ivShow.setVisibility(View.GONE);
        }*/
        initNet();
        initToolbar();
        initView();

        //initData();
    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initData(0);
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(getContext())){
            //加载网络数据
            initData(0);
        }else {
            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
    public void initData(int i) {
        if(i==1){
            bjDynamicListModel.clear();
        }
       /* if(page==1){
            pageIndex=page;
        }*/
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
        String customerId = loginInfo.getId() + "";
        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("pageSize",""+pageSize);
        /*if(page==1){
            pageIndex=page;
            params.addBodyParameter("pageIndex",""+page);
            initTop();
        }else{
            params.addBodyParameter("pageIndex",""+pageIndex);
        }*/
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("customerId",customerId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("circle", "onSuccess: "+result);
                Gson gson=new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                boolean state = bean.isState();
                if(state){
                    bjDynamicListModel.addAll(bean.getModel().getBJDynamicListModel()) ;
                    totalPage=bean.getModel().getTotalPages();
                    for (int i = 0; i <bjDynamicListModel.size() ; i++) {
                        String dynamicsContent = bjDynamicListModel.get(i).getReleaseTimeStr();
                        Log.d("dynamicsContent", "onSuccess: "+dynamicsContent);
                        Bean.ModelBean.BJDynamicListModelBean.UserModelBean userModel = bjDynamicListModel.get(i).getUserModel();
                        String nickName = userModel.getHeadImg();
                        Log.d("nickName", "onSuccess: "+nickName);
                        boolean isChecked = bjDynamicListModel.get(i).getIsChecked();
                        Log.d("ischecked", "onSuccess: "+isChecked);
                    }
                    //bjDynamicListModelBean= bjDynamicListModel.get(0);
                    //Log.d("wwwwwww22222", "onBindHeaderViewHolder: "+bjDynamicListModelBean.getDynamicsTitle());
                    if(bjDynamicListModel.size()<=0){
                        mErrorView.show(rvCircle,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvCircle);
                    }
                }else{
                    ToastUtils.showCenter(getContext(),bean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvCircle,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjDynamicListModel);
                //adapter.notifyDataSetChanged();
                layoutLoading.setVisibility(View.GONE);
                mIsRefreshing=false;


            }
        });
    }

    private void initView() {
        for (int i = 0; i < 8; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<Bean.ModelBean.BJDynamicListModelBean>(getContext(),bjDynamicListModel,R.layout.item_circle) {
            @Override
            protected void onBindNormalViewHolder(final NormalViewHolder holder, final Bean.ModelBean.BJDynamicListModelBean model) {
                LinearLayout layoutRecommend = (LinearLayout) holder.getViewById(R.id.layout_circle_recommend);
                LinearLayout layoutCircle = (LinearLayout) holder.getViewById(R.id.layout_circle);
                final LinearLayout layoutShare = (LinearLayout) holder.getViewById(R.id.layout_circle_share);
                final LinearLayout LayoutPraise = (LinearLayout) holder.getViewById(R.id.layout_circle_praise);
                final TextView tvPraise = (TextView) holder.getViewById(R.id.tv_circle_praise);
                final TextView tvShare = (TextView) holder.getViewById(R.id.tv_circle_share);
                ImageView ivPortrait = (ImageView) holder.getViewById(R.id.iv_circle_portrait);
                final CheckBox checkBox = (CheckBox) holder.getViewById(R.id.cb_circle);
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
                String customerId = loginInfo.getId() + "";
                if((!TextUtils.isEmpty(customerId))&&model.isTag()){
                    checkBox.setChecked(true);
                }else{
                    checkBox.setChecked(false);
                }
                boolean isRecommend = model.isIsRecommend();
                if(isRecommend){
                    layoutRecommend.setVisibility(View.VISIBLE);
                    layoutCircle.setVisibility(View.GONE);
                    holder.setText(R.id.tv_circle_title,model.getDynamicsTitle());
                    holder.setText(R.id.tv_circle_title_time,model.getReleaseTimeStr());
                    holder.setText(R.id.tv_circle_title_content,model.getDynamicsIntroduce());
                    ImageView imageView = (ImageView) holder.getViewById(R.id.iv_circle_title_pic);
                    x.image().bind(imageView,model.getDynamicsThumsPictureUrl(),imageOptions);
                    holder.setText(R.id.tv_circle_praise,model.getTagsNumber()+"");
                    holder.setText(R.id.tv_circle_comment,model.getCommentNumber()+"");
                    String share = model.getShare() + "";
                    holder.setText(R.id.tv_circle_share,share);
                    holder.setText(R.id.tv_circle_look,model.getView()+"");
                   // LinearLayout layoutHead = (LinearLayout) holder.getViewById(R.id.layout_circle);
                   /* layoutHead.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), CircleDetailActivity.class);
                            boolean isRecommend = bjDynamicListModelBean.isIsRecommend();
                            intent.putExtra("isRecommend",isRecommend);
                            String dynamicId1 = bjDynamicListModelBean.getId() + "";
                            intent.putExtra("dynamicId",dynamicId1);
                            startActivity(intent);
                        }
                    });*/

                }else {
                    layoutCircle.setVisibility(View.VISIBLE);
                    layoutRecommend.setVisibility(View.GONE);
                    NineGridView nineGridView = (NineGridView) holder.getViewById(R.id.circle_nineGrid);
                    //用户设置
                    final Bean.ModelBean.BJDynamicListModelBean.UserModelBean userModel = model.getUserModel();
                    x.image().bind(ivPortrait,userModel.getHeadImg(),imageOptionsCircle);
                    holder.setText(R.id.tv_circle_name,userModel.getNickName());
                    String releaseTimeStr = model.getReleaseTimeStr();
                    holder.setText(R.id.tv_circle_time,releaseTimeStr);
                    holder.setText(R.id.tv_circle_content,model.getDynamicsContent());
                    holder.setText(R.id.tv_circle_praise,model.getTagsNumber()+"");
                    holder.setText(R.id.tv_circle_comment,model.getCommentNumber()+"");
                    String share = model.getShare() + "";
                    holder.setText(R.id.tv_circle_share,share);
                    holder.setText(R.id.tv_circle_look,model.getView()+"");
                    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                    List<Bean.ModelBean.BJDynamicListModelBean.BJDynamicsPictureListModel> bjDynamicsPictureListModel
                            = bjDynamicListModel.get(getPosition(holder) < 0 ? 0 : getPosition(holder)).getBJDynamicsPictureListModel();
                    String str="http://www.eaglesoft.org/public/UploadFiles/image/20141017/20141017152856_751.jpg";
                    //String str="http://www.eaglesoft.org/public/UploadFiles/image/20141017/20141017152856_751.jpg";

                    for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                        String pictureUrl = bjDynamicsPictureListModel.get(i).getPictureUrl();
                        String pictureUrlThumbnailUrl = bjDynamicsPictureListModel.get(i).getThumbPictureUrl();
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(pictureUrlThumbnailUrl);
                        info.setBigImageUrl(pictureUrl);
                        imageInfo.add(info);
                    }
                    //nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(),imageInfo));
                    final ArrayList<String> image= new ArrayList<>();
                    for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                        image.add(bjDynamicsPictureListModel.get(i).getPictureUrl());
                    }
                    nineGridView.setAdapter(new NineGridViewAdapter(getContext(),imageInfo) {
                        @Override
                        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                            super.onImageItemClick(context, nineGridView, index, imageInfo);
                           /* Intent intent=new Intent(getActivity(), PhotoBrowseActivity.class);
                            intent.putExtra("list",image);
                            startActivity(intent);*/
                            ImageView imageView1=new ImageView(context);


                            //Log.d("pictureList.size()", "onEvent: "+pictureList.size());
                            MNImageBrowser.showImageBrowser(getActivity(),imageView1,index, image);
                        }
                    });
                    //头像跳转到我的动态
                    ivPortrait.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id = userModel.getId()+"";
                            Intent intent=new Intent(getActivity(), MyCircleActivity.class);
                            intent.putExtra("customerId",id);
                            startActivity(intent);
                        }
                    });
                }
                //评论跳转到详情
                LinearLayout layoutComment = (LinearLayout) holder.getViewById(R.id.layout_circle_comment);
                layoutComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), CircleDetailActivity.class);
                        intent.putExtra("isRecommend",model.isIsRecommend());
                        intent.putExtra("dynamicId",model.getId()+"");
                        intent.putExtra("comment",10);
                        startActivity(intent);
                    }
                });

                //点赞
                LayoutPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
                       String customerId= loginInfo.getId()+"";
                      if(TextUtils.isEmpty(customerId)){
                          Intent intent=new Intent(getActivity(),LoginActivity.class);
                          intent.putExtra("webview","2");
                          startActivity(intent);
                      }else {
                        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_PRAISE);
                        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                        params.addBodyParameter("customerId",customerId);
                        params.addBodyParameter("dynamicId",model.getId() + "");
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("praise", "onSuccess: "+result);
                                Gson gson=new Gson();
                                PraiseBean praiseBean = gson.fromJson(result, PraiseBean.class);
                                boolean state = praiseBean.isState();
                                if(state){
                                    //ToastUtils.showCenter(getContext(),praiseBean.getMessage());
                                    int tagsNumber = model.getTagsNumber();
                                    tagsNumber++;
                                    //bjDynamicListModel.clear();
                                    //initData(0);
                                    model.setTagsNumber(tagsNumber);
                                    tvPraise.setText(tagsNumber+"");
                                    checkBox.setChecked(true);
                                    model.setTag(true);
                                    adapter.notifyDataSetChanged();
                                }else {
                                    ToastUtils.showCenter(getContext(),praiseBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                //mErrorView.show(rvCircle,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                                ToastUtils.showCenter(getContext(),"网络不给力,连接服务器异常!");
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {
                                layoutLoading.setVisibility(View.GONE);

                            }
                        });
                      }


                    }
                });

                layoutShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dynamicId = model.getId() + "";
                        //index=model.getId();
                       // String url = "http://www.baijar.com/EventAppApi/DynamicSharePage?dynamicId=" +dynamicId;
                        final String url = "http://www.baijar.com/EventAppApi/DynamicSharePage?dynamicId=" +dynamicId;
                        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                           // shareApp(url);
                            initShare(url, model, tvShare,dynamicId);
                        }else {
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10001);
                            }else {
                                //shareApp(url);
                                /*使用友盟自带分享模版*/
                                initShare(url, model, tvShare,dynamicId);
                            }
                        }
                    }
                });
            }

        };
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), CircleDetailActivity.class);
                if(bjDynamicListModel.size()<=0){
                    return;
                }
                Bean.ModelBean.BJDynamicListModelBean bjDynamicListModelBean= bjDynamicListModel.get(position);
                String dynamicId1 = bjDynamicListModelBean.getId() + "";
                boolean isRecommend = bjDynamicListModelBean.isIsRecommend();
                intent.putExtra("isRecommend",isRecommend);
                intent.putExtra("dynamicId",dynamicId1);
                startActivity(intent);

            }
        });
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
                       // mImageViewRebackTop.setVisibility(View.INVISIBLE);
                    } else {
                       // mImageViewRebackTop.setVisibility(View.VISIBLE);
                    }

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {//拖动中
                    //mImageViewRebackTop.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //rvCircle.setItemAnimator(new DefaultItemAnimator());
        rvCircle.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsRefreshing) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        mImageViewRebackTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTop();
            }
        });

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mIsRefreshing=true;
                springView.onFinishFreshAndLoad();
                if(bjDynamicListModel.size()<=0){
                    return;
                }
                pageIndex=1;
                initData(1);
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
                if(totalPage<=pageIndex){
                    return;
                }
                if(totalPage>pageIndex){
                    pageIndex++;
                    initData(0);
                }

            }
        });
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));



    }

    private void initShare(final String url, final Bean.ModelBean.BJDynamicListModelBean model, final TextView tvShare, final String dynamicId) {
    /*使用友盟自带分享模版*/
        action = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                UMWeb umWeb = new UMWeb(url);
                String dynamicsContent = model.getDynamicsContent();
                umWeb.setTitle("佰家运动");
                if(!TextUtils.isEmpty(dynamicsContent)){
                    umWeb.setDescription(dynamicsContent);
                }else{
                    umWeb.setDescription("赛事尽在佰家运动App");
                }
                UMImage image = new UMImage(getActivity(), R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(getActivity()).withText("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。")
                        .setPlatform(share_media)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                Toast.makeText(getActivity(),"正在打开分享...",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResult(SHARE_MEDIA platform) {
                                if (platform.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(getActivity(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                                            && platform != SHARE_MEDIA.EMAIL
                                            && platform != SHARE_MEDIA.FLICKR
                                            && platform != SHARE_MEDIA.FOURSQUARE
                                            && platform != SHARE_MEDIA.TUMBLR
                                            && platform != SHARE_MEDIA.POCKET
                                            && platform != SHARE_MEDIA.PINTEREST
                                            && platform != SHARE_MEDIA.INSTAGRAM
                                            && platform != SHARE_MEDIA.GOOGLEPLUS
                                            && platform != SHARE_MEDIA.YNOTE
                                            && platform != SHARE_MEDIA.EVERNOTE) {
                                        Toast.makeText(getActivity(), platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
                                        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_SHARE);
                                        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                                        params.addBodyParameter("dynamicId",dynamicId);
                                        x.http().post(params, new Callback.CommonCallback<String>() {
                                            @Override
                                            public void onSuccess(String result) {
                                                Log.d("share", "onSuccess: "+result);
                                                Gson gson=new Gson();
                                                CircleShareBean circleShareBean = gson.fromJson(result, CircleShareBean.class);
                                                boolean state = circleShareBean.isState();
                                                if(state){
                                                    int share = model.getShare();
                                                    share++;
                                                    //initData(0);
                                                    tvShare.setText(share+"");
                                                    //tvPraise.setText(share+"");
                                                    model.setShare(share);
                                                    adapter.notifyDataSetChanged();
                                                }else{
                                                    ToastUtils.showCenter(getContext(),circleShareBean.getMessage());
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

                                            }
                                        });

                                      /*  int share = model.getShare();
                                        share++;
                                        initData(0);
                                        tvShare.setText(share+"");
                                        //tvPraise.setText(share+"");
                                        model.setShare(share);
                                        adapter.notifyDataSetChanged();*/
                                    }

                                }
                            }

                            @Override
                            public void onError(SHARE_MEDIA platform, Throwable throwable) {
                                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                                        && platform != SHARE_MEDIA.EMAIL
                                        && platform != SHARE_MEDIA.FLICKR
                                        && platform != SHARE_MEDIA.FOURSQUARE
                                        && platform != SHARE_MEDIA.TUMBLR
                                        && platform != SHARE_MEDIA.POCKET
                                        && platform != SHARE_MEDIA.PINTEREST

                                        && platform != SHARE_MEDIA.INSTAGRAM
                                        && platform != SHARE_MEDIA.GOOGLEPLUS
                                        && platform != SHARE_MEDIA.YNOTE
                                        && platform != SHARE_MEDIA.EVERNOTE) {
                                    Toast.makeText(getActivity(), platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(getActivity(),"分享已取消!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .withMedia(umWeb)
                        .share();
            }


        });


        action.open();
    }

    public void initTop() {
        //rvCircle.smoothScrollToPosition(0);
        rvCircle.scrollToPosition(0);

    }

    /**
     *  分享给好友
     */
    private void shareApp(final String url){

           /*使用友盟自带分享模版*/
        action = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                UMWeb umWeb = new UMWeb(url);
                umWeb.setTitle("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。");
                umWeb.setDescription("佰家运动");
                UMImage image = new UMImage(getActivity(),R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(getActivity()).withText("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。")
                        .setPlatform(share_media)
                        .setCallback(new MyUMShareListener())
                        .withMedia(umWeb)
                        .share();
            }


        });


        action.open();
    }
    /**
     * 自定义分享结果监听器
     */
    private class MyUMShareListener implements UMShareListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(),"正在打开分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(getActivity(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(getActivity(), platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
                    Bean.ModelBean.BJDynamicListModelBean bjDynamicListModelBean = bjDynamicListModel.get(index);
                    int share = bjDynamicListModelBean.getShare();
                    share++;
                    initData(0);
                    //tvPraise.setText(share+"");
                    bjDynamicListModelBean.setTagsNumber(share);
                    adapter.notifyDataSetChanged();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(getActivity(), platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(),"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initToolbar() {
        //消息通知
        imageView.setOnClickListener(this);
        //点击顶部
        toolbar.setOnClickListener(this);

        //发布动态
        this.rightImage.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_circle_notice:
                //通知
                LoginUserInfoBean.UserBean loginInfo1 = SharedPreferencesManager.getLoginInfo(getContext());
                String id = loginInfo1.getId();
                if(TextUtils.isEmpty(id)){
                    intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else{
                    SharedPreferencesManager.isNotice(getActivity(),false);
                    boolean open = SharedPreferencesManager.getNotice(getContext());
                    if(open){
                        ivShow.setVisibility(View.VISIBLE);
                    }else{
                        ivShow.setVisibility(View.GONE);
                    }

                    intent=new Intent(getActivity(), CircleMessageActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.iv_circle_publish:
                //发布动态
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getActivity());
                String customerId = loginInfo.getId()+"";
                if(TextUtils.isEmpty(customerId)){
                    intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else{
                intent=new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
                }
                break;
            case R.id.mToolBar:
                //回到顶部
                initTop();
                break;
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
