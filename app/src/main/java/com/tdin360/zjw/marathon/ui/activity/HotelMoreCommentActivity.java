package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.HotelMoreCommentBean;
import com.tdin360.zjw.marathon.model.TravelMoreCommentBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看全部评价
 */

public class HotelMoreCommentActivity extends BaseActivity {
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

    @ViewInject(R.id.rv_hotel_more_comment)
    private RecyclerView rvComment;
    private RecyclerViewBaseAdapter adapter,rvAdapter;
    private List<String> list=new ArrayList<>();

    @ViewInject(R.id.springView)
    private SpringView springView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    private boolean flag=false;
    //private List<HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean> bjHotelEvaluateListModel=new ArrayList<>();
    private List<HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean> bjHotelEvaluateListModel=new ArrayList<>();
    private ImageOptions imageOptions,imageOptionsCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initData();
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                //.setCrop(true).setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        imageOptionsCircle= new ImageOptions.Builder()
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
        initToolbar();
        initNet();
        initView();


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
        //bjHotelEvaluateListModel.clear();
        String eventId = SingleClass.getInstance().getEventId();
        String hotelId = getIntent().getStringExtra("hotelId");
        RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_MORE_COMMENT);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("eventId",eventId);
        params.addBodyParameter("hotelId",hotelId);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("hotelmorecomment", "onSuccess: "+result);
                Gson gson=new Gson();
                HotelMoreCommentBean hotelMoreCommentBean = gson.fromJson(result, HotelMoreCommentBean.class);
                boolean state = hotelMoreCommentBean.isState();
                if(state){
                    HotelMoreCommentBean.ModelBean model = hotelMoreCommentBean.getModel();
                    totalPage=model.getTotalPages();
                    bjHotelEvaluateListModel.addAll(model.getBJHotelEvaluateListModel()) ;
                    String evaluateContent = bjHotelEvaluateListModel.get(0).getEvaluateContent();
                    Log.d("evaluateContent", "onSuccess: "+evaluateContent);
                    if(bjHotelEvaluateListModel.size()<=0){
                        mErrorView.show(rvComment,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvComment);
                    }

                }else{
                    ToastUtils.showCenter(getApplicationContext(),hotelMoreCommentBean.getMessage());
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvComment,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(HotelMoreCommentActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //hud.dismiss();
                adapter.update(bjHotelEvaluateListModel);
                layoutLoading.setVisibility(View.GONE);

            }
        });
    }

    private void initView() {
        for (int i = 0; i < 9; i++) {
            list.add(""+i);
        }


        adapter=new RecyclerViewBaseAdapter<HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean>(getApplicationContext(),
                bjHotelEvaluateListModel,R.layout.item_all_hotel_more_comment) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean model) {
                RecyclerView rvComment = (RecyclerView) holder.getViewById(R.id.rv_hotel_comment_pic);
                HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean.EvaluationUserModelBean evaluationUserModel = model.getEvaluationUserModel();
                SimpleDraweeView ivPortrait = (SimpleDraweeView) holder.getViewById(R.id.iv_all_hotel_comment_pic);
                //x.image().bind(ivPortrait,evaluationUserModel.getHeadImg(),imageOptionsCircle);
                Uri uri =  Uri.parse(evaluationUserModel.getHeadImg());
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .build();

                ivPortrait.setController(controller);


                holder.setText(R.id.tv_all_hotel_comment_name,evaluationUserModel.getNickName());
                holder.setText(R.id.tv_all_hotel_comment_time,model.getEvaluateTimeStr());
                holder.setText(R.id.tv_all_hotel_comment_content,model.getEvaluateContent());
                List<HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean> bjHotelEvaluatePictureListModel
                        = model.getBJHotelEvaluatePictureListModel();
                final ArrayList<String> image= new ArrayList<>();
                for(int i=0;i<bjHotelEvaluatePictureListModel.size();i++){
                    image.add(bjHotelEvaluatePictureListModel.get(i).getPictureUrl());
                }
                rvAdapter=new RecyclerViewBaseAdapter<HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean>
                        (getApplicationContext(),bjHotelEvaluatePictureListModel,R.layout.item_hotel_detail_pic) {
                    @Override
                    protected void onBindNormalViewHolder(NormalViewHolder holder, HotelMoreCommentBean.ModelBean.BJHotelEvaluateListModelBean.BJHotelEvaluatePictureListModelBean model) {
                        ImageView imageView = (ImageView) holder.getViewById(R.id.iv_comment_pic);
                        x.image().bind(imageView,model.getThumbPictureUrl(),imageOptions);
                    }
                };
                rvComment.setAdapter(rvAdapter);
                rvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                rvAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ImageView imageView1=new ImageView(HotelMoreCommentActivity.this);
                        //Log.d("pictureList.size()", "onEvent: "+pictureList.size());
                        MNImageBrowser.showImageBrowser(HotelMoreCommentActivity.this,imageView1,position, image);
                    }
                });
            }
        };
        rvComment.setAdapter(adapter);
        rvComment.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjHotelEvaluateListModel.clear();
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

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText("所有评论");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_more_comment;
    }
}
