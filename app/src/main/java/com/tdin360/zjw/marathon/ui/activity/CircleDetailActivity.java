package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.Bean;
import com.tdin360.zjw.marathon.model.CircleDetailBean;
import com.tdin360.zjw.marathon.model.CircleDetailCommentBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
 * 动态详情
 */

public class CircleDetailActivity extends BaseActivity {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.toolbar_circle_detail)
    private Toolbar toolbarBack;
    @ViewInject(R.id.iv_circle_detail_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_circle_detail_name)
    private TextView tvName;
    @ViewInject(R.id.iv_circle_detail_portrait)
    private ImageView ivPortrait;
    //评论
    @ViewInject(R.id.et_circle_detail_comment)
    private EditText etComment;
    @ViewInject(R.id.btn_circle_detail_comment)
    private Button btnSure;

    @ViewInject(R.id.rv_circle_detail)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    @ViewInject(R.id.springView)
    private SpringView springView;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    ImageOptions imageOptions;

    private String dynamicId="";
    private int commentCount;

    private List<CircleDetailBean.ModelBean.BJDynamicsCommentListModelBean> bjDynamicsCommentListModel=new ArrayList<>();
    private List<CircleDetailBean.ModelBean.BJDynamicsPictureListModelBean> bjDynamicsPictureListModel=new ArrayList<>();
    private CircleDetailBean.ModelBean model=new CircleDetailBean.ModelBean();
    private CircleDetailBean.ModelBean.UserModelBean userModel=new CircleDetailBean.ModelBean.UserModelBean();
    private List<CircleDetailBean.ModelBean.TagUserListModelBean> tagUserListModel=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        imageOptions = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        initAnim();
        Intent intent=getIntent();
        dynamicId= intent.getStringExtra("dynamicId");
        initToolbar();
        showInfo();
        //initData();
        initNet();
        initView();
        initComment();


    }
    @Subscribe
    public void onEvent(EventBusClass event){
     /*   if(event.getEnumEventBus()== EnumEventBus.PUBLISH){
            initData();
        }*/
        if(event.getEnumEventBus()==EnumEventBus.CIRCLEDETAILCOMMENT){
            //int  index =Integer.parseInt(event.getMsg()) ;
           // Log.d("circleCommentNumber", "onEvent: "+index);
            initData(1);
            //adapter.notifyItemChanged(index);

        }
    }
    private void initAnim() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
    }

    private void initComment() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserInfoBean.UserBean modelInfo=SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String commentContent = etComment.getText().toString().trim();
                String customerId = modelInfo.getId() + "";
                if (customerId == null || customerId.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(getContext(),"评论内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    final KProgressHUD hud = KProgressHUD.create(CircleDetailActivity.this);
                    hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(1)
                            .setDimAmount(0.5f)
                            .show();
                RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_DETAIL_COMMENT);
                params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                params.addBodyParameter("customerId",customerId);
                params.addBodyParameter("dynamicId",dynamicId);
                params.addBodyParameter("commentId","0");
                params.addBodyParameter("commentContent",commentContent);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("comment", "onSuccess: "+result);
                        Gson gson=new Gson();
                        CircleDetailCommentBean circleDetailCommentBean = gson.fromJson(result, CircleDetailCommentBean.class);
                        boolean state = circleDetailCommentBean.isState();
                        if(state){
                            //ToastUtils.showCenter(getApplicationContext(),circleDetailCommentBean.getMessage());
                            commentCount = bjDynamicsCommentListModel.size();
                            commentCount++;
                            initData(1);
                            etComment.setText("");
                            EnumEventBus em = EnumEventBus.CIRCLECOMMENT;
                            EventBus.getDefault().post(new EventBusClass(""+commentCount,em));
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),circleDetailCommentBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        //mErrorView.show(rvCircle,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
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
             }
            }
        });
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
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
            initData(0);
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
    private void initData(int i) {
        if(i==1){
            bjDynamicsCommentListModel.clear();
        }
        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_DETAIL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("dynamicId",dynamicId);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("circleetail", "onSuccess: "+result);
                Gson gson=new Gson();
                CircleDetailBean circleDetailBean = gson.fromJson(result, CircleDetailBean.class);
                boolean state = circleDetailBean.isState();
                if(state){
                     model= circleDetailBean.getModel();
                     totalPage=model.getTotalPages();
                     bjDynamicsCommentListModel.addAll(model.getBJDynamicsCommentListModel()) ;
                     bjDynamicsPictureListModel = model.getBJDynamicsPictureListModel();
                     userModel= model.getUserModel();
                     tagUserListModel= model.getTagUserListModel();
                    List<?> commentUserListModel = model.getCommentUserListModel();
                }else{
                    ToastUtils.showCenter(getApplicationContext(),circleDetailBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvCircle,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjDynamicsCommentListModel);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });

    }

    private void initView() {
        for (int i = 0; i <6 ; i++) {
            list.add(""+i);

        }
        adapter=new RecyclerViewBaseAdapter<CircleDetailBean.ModelBean.BJDynamicsCommentListModelBean>(getApplicationContext(),
                bjDynamicsCommentListModel,R.layout.item_circle_detail) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, final CircleDetailBean.ModelBean.BJDynamicsCommentListModelBean model) {
                TextView tvResponse = (TextView) holder.getViewById(R.id.tv_circle_detail_response);
                String str="回复<font color='#ff621a'>楼主：</font>的统一问题极寒风暴㚥看能否佰家节能环保福尔";
                tvResponse.setText(Html.fromHtml(str));
                TextView tvCallback = (TextView) holder.getViewById(R.id.tv_circle_detail_callback);
                ImageView portrait = (ImageView) holder.getViewById(R.id.iv_circle_detail_comment_portrait);
                String headImg = model.getHeadImg();
                x.image().bind(portrait,headImg,imageOptions);
                holder.setText(R.id.tv_circle_detail_comment_content,model.getCommentContent());
                holder.setText(R.id.tv_circle_detail_comment_name,model.getNickName());
                holder.setText(R.id.tv_circle_detail_comment_time,model.getCreateTimeStr());
                //回复
                tvCallback.setText(model.getCommentCount()+"回复");
                tvCallback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CircleDetailActivity.this,CallBackActivity.class);
                        intent.putExtra("commentId",model.getId()+"");
                        intent.putExtra("dynamicId",dynamicId);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onBindHeaderViewHolder(HeaderViewHolder holder) {
                super.onBindHeaderViewHolder(holder);
                boolean isRecommend = getIntent().getBooleanExtra("isRecommend",false);
                LinearLayout linearLayout = (LinearLayout) holder.getViewById(R.id.layout_circle_detail);
                WebView webView = (WebView) holder.getViewById(R.id.wb_circle_detail);
                if (isRecommend){
                    webView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                }else{
                    webView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.tv_circle_detail_head_content,model.getDynamicsContent());
                holder.setText(R.id.tv_circle_detail_head_name,userModel.getNickName());
                ImageView headPortrait = (ImageView) holder.getViewById(R.id.iv_circle_detail_head_portrait);
                x.image().bind(headPortrait,userModel.getHeadImg(),imageOptions);
                holder.setText(R.id.tv_circle_detail_head_praise,model.getTagsNumber()+"人赞过");

                //holder.setText(R.id.tv_circle_detail_head_name,userModel.getHeadImg());

                Log.d("model.getId()", "onBindHeaderViewHolder: "+model.getId());
                //动态图片
                NineGridView nineGridView = (NineGridView) holder.getViewById(R.id.circle_detail_nineGrid);
                ArrayList<ImageInfo> imageInfo= new ArrayList<>();
                for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                    String pictureUrl = bjDynamicsPictureListModel.get(i).getPictureUrl();
                    String pictureUrlThumbnailUrl = bjDynamicsPictureListModel.get(i).getThumbPictureUrl();
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(pictureUrlThumbnailUrl);
                    info.setBigImageUrl(pictureUrl);
                    imageInfo.add(info);
                }
                final ArrayList<String> image= new ArrayList<>();
                for(int i=0;i<bjDynamicsPictureListModel.size();i++){
                    image.add(bjDynamicsPictureListModel.get(i).getPictureUrl());
                }
                nineGridView.setAdapter(new NineGridViewAdapter(getApplicationContext(), imageInfo) {
                    @Override
                    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                        super.onImageItemClick(context, nineGridView, index, imageInfo);
                        /*Intent intent=new Intent(CircleDetailActivity.this,PhotoBrowseActivity.class);
                        intent.putStringArrayListExtra("list",image);
                        startActivity(intent);*/
                        ImageView imageView1=new ImageView(CircleDetailActivity.this);
                        //Log.d("pictureList.size()", "onEvent: "+pictureList.size());
                        MNImageBrowser.showImageBrowser(CircleDetailActivity.this,imageView1,index, image);
                    }
                });

                //社交详情头部
                RecyclerView rvHead = (RecyclerView) holder.getViewById(R.id.rv_circle_detail_head_portrait);
                rvHead.setAdapter(new RecyclerViewBaseAdapter<CircleDetailBean.ModelBean.TagUserListModelBean>(getApplicationContext(),
                        tagUserListModel,R.layout.item_circle_detail_head_praise) {
                    @Override
                    protected void onBindNormalViewHolder(NormalViewHolder holder, CircleDetailBean.ModelBean.TagUserListModelBean model) {
                        ImageView iv = (ImageView) holder.getViewById(R.id.iv_circle_detail_praise_portrait);
                        x.image().bind(iv,model.getHeadImg(),imageOptions);
                        if(getPosition(holder)==10){
                            holder.setImageResource(R.id.iv_circle_detail_praise_portrait,R.drawable.circle_detail_enter);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(CircleDetailActivity.this,CircleDetailPraiseActivity.class);
                                    startActivity(intent);
                                }
                            });
                            return;
                        }
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(CircleDetailActivity.this,MyCircleActivity.class);
                                intent.putExtra("dynamicId",dynamicId);
                                startActivity(intent);
                            }
                        });
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(CircleDetailActivity.this,CircleDetailPraiseActivity.class);
                                intent.putExtra("dynamicId",dynamicId);
                                startActivity(intent);
                            }
                        });

                    }
                });
                rvHead.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                //社交详情头部由网页展示

                String url = "http://www.baijar.com/EventAppApi/BJDynamicRecommendDetail?dynamicId=27";
                com.tencent.smtt.sdk.WebSettings settings = webView.getSettings();
                settings.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_CACHE_ELSE_NETWORK);
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClient());
                webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient(){
                @Override
                public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    //textViewTitle.setText(title);
                    //avRefresh.setVisibility(View.GONE);
                    //layoutRefresh.setVisibility(View.GONE);

                }
               /* ivEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(CircleDetailActivity.this,CircleDetailPraiseActivity.class);
                        startActivity(intent);

                    }
                });*/
        });


            }
        };
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.addHeaderView(R.layout.item_circle_detail_head);

        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CircleDetailBean.ModelBean.BJDynamicsCommentListModelBean bjDynamicsCommentListModelBean = bjDynamicsCommentListModel.get(position);
                int commentId = bjDynamicsCommentListModelBean.getId();
                Intent intent=new Intent(CircleDetailActivity.this,CallBackActivity.class);
                intent.putExtra("commentId",commentId);
                intent.putExtra("dynamicId",dynamicId);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjDynamicsCommentListModel.clear();
                pageIndex=1;
                initData(0);
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
        springView.setHeader(new DefaultHeader(getApplicationContext()));
        springView.setFooter(new DefaultFooter(getApplicationContext()));
    }
    /**
     * 显示个人信息
     */
    private void showInfo(){
       LoginUserInfoBean.UserBean modelInfo=SharedPreferencesManager.getLoginInfo(getApplicationContext());
        tvName.setText(modelInfo.getNickName());
        //不登陆不显示头像
        if(SharedPreferencesManager.isLogin(getApplicationContext())){
            x.image().bind(ivPortrait,modelInfo.getHeadImg(),imageOptions);
        }else {
            ivPortrait.setImageResource(R.drawable.my_portrait);
            tvName.setText("");
        }
    }

    private void initToolbar() {
        showBack(toolbarBack,ivBack);
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_circle_detail;
    }
}
