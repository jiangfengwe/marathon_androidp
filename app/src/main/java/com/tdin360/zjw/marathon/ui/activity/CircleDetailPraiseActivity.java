package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.CircleDetailPraiseListBean;
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
 * 点赞人数展示
 */

public class CircleDetailPraiseActivity extends BaseActivity {
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

    @ViewInject(R.id.rv_circle_praise)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    private boolean flag=false;
    @ViewInject(R.id.springView)
    private SpringView springView;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    ImageOptions imageOptions;

    private List<CircleDetailPraiseListBean.ModelBean.TagUserListModelBean> tagUserListModel=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions = new ImageOptions.Builder()
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
        //initData();
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
        //tagUserListModel.clear();
       /* final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();*/

        Intent intent=getIntent();
        String dynamicId= intent.getStringExtra("dynamicId");
        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_PRAISE_LIST);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("dynamicId",dynamicId);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("praiseList", "onSuccess: "+result);
                Gson gson=new Gson();
                CircleDetailPraiseListBean circleDetailPraiseListBean = gson.fromJson(result, CircleDetailPraiseListBean.class);
                boolean state = circleDetailPraiseListBean.isState();
                if(state){
                    CircleDetailPraiseListBean.ModelBean model = circleDetailPraiseListBean.getModel();
                    totalPage=model.getTotalPages();
                    tagUserListModel.addAll(model.getTagUserListModel());
                    if(tagUserListModel.size()<=0){
                        mErrorView.show(rvCircle,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvCircle);
                    }
                }else{
                    ToastUtils.showCenter(getApplicationContext(),circleDetailPraiseListBean.getMessage());
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
                adapter.update(tagUserListModel);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });
    }

    private void initView() {
        for (int i = 0; i < 15; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<CircleDetailPraiseListBean.ModelBean.TagUserListModelBean>(getApplicationContext(),tagUserListModel,R.layout.item_circle_praise) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, CircleDetailPraiseListBean.ModelBean.TagUserListModelBean model) {
                holder.setText(R.id.tv_praise_list_name,model.getNickName());
                ImageView portrait = (ImageView) holder.getViewById(R.id.iv_praise_list_portrait);
                x.image().bind(portrait,model.getHeadImg(),imageOptions);
                holder.setText(R.id.tv_praise_list_sign,model.getCustomerSign());

            }
        };
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(CircleDetailPraiseActivity.this,MyCircleActivity.class);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                tagUserListModel.clear();
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
        titleTv.setText("点过赞的佰家星人");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        showBack(toolbar,imageView);
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_circle_detail_praise;
    }
}
