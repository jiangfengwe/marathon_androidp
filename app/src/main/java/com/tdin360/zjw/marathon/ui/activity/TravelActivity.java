package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
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
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.TravelBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
/**
 * 旅游
 */
public class TravelActivity extends BaseActivity {

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

    @ViewInject(R.id.rv_travel)
    private RecyclerView rvTravel;
    private List<String> list=new ArrayList<>();
    private RecyclerViewBaseAdapter adapter;
    private List<TravelBean.ModelBean.BJTravelListModelBean> bjTravelListModel=new ArrayList<>();
    ImageOptions imageOptions;

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    private boolean flag=false;
    @ViewInject(R.id.springView)
    private SpringView springView;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                //.setCrop(true).setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.event_bg) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.event_bg) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initToolbar();
        //intData();
        initNet();
        initRecyclerView();

    }

    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        intData();
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
            intData();
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

    private void intData() {
        String eventId = SingleClass.getInstance().getEventId();
       // bjTravelListModel.clear();
        RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("eventId",eventId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("travel", "onSuccess: "+result);
                Gson gson=new Gson();
                TravelBean travelBean = gson.fromJson(result, TravelBean.class);
                boolean state = travelBean.isState();
                if(state){
                    TravelBean.ModelBean model = travelBean.getModel();
                    bjTravelListModel.addAll(model.getBJTravelListModel());
                    totalPage=model.getTotalPages();
                    if(bjTravelListModel.size()<=0){
                        mErrorView.show(rvTravel,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvTravel);
                    }
                }else {
                    ToastUtils.showCenter(getApplicationContext(),travelBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvTravel,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(TravelActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjTravelListModel);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });
    }

    private void initRecyclerView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<TravelBean.ModelBean.BJTravelListModelBean>(getApplicationContext(),bjTravelListModel,R.layout.item_travel_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, TravelBean.ModelBean.BJTravelListModelBean model) {
                Log.d("444444444444", "onBindNormalViewHolder: "+bjTravelListModel.size());
                ImageView ivPic = (ImageView) holder.getViewById(R.id.iv_travel_pic);
                x.image().bind(ivPic,model.getPictureUrl(),imageOptions);
                holder.setText(R.id.tv_travel_price,model.getPrice()+"");
                holder.setText(R.id.tv_travel_start,model.getStartPlace()+"——"+model.getEndPlace());
                holder.setText(R.id.tv_travel_data,model.getDay()+"日游");
            }
        };
        rvTravel.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rvTravel.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TravelBean.ModelBean.BJTravelListModelBean bjTravelListModelBean = bjTravelListModel.get(position);
                String travelId = bjTravelListModelBean.getId()+"";
                Log.d("eeeeeeeeeee", "onItemClick: "+travelId);
                Intent intent=new Intent(TravelActivity.this,TravelDetailActivity.class);
                SingleClass.getInstance().setTravelId(travelId);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjTravelListModel.clear();
                pageIndex=1;
                intData();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
                if(totalPage<=pageIndex){
                    return;
                }
                if(totalPage>pageIndex){
                    pageIndex++;
                    intData();
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
        titleTv.setText(R.string.hotel_travel);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_travel;
    }
}
