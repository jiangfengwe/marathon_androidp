package com.tdin360.zjw.marathon.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tdin360.zjw.marathon.model.EventBean;
import com.tdin360.zjw.marathon.ui.activity.ApplyActivity;
import com.tdin360.zjw.marathon.ui.activity.WebActivity;
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
 * 赛事状态fragment
 */
public class EventStateFragment extends BaseFragment {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.rv_event_state)
    private RecyclerView rvEventState;
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

    private boolean isVisible;
    private boolean isPrepared;

    private List<EventBean.ModelBean.BJEventSystemListModelBean> bjEventSystemListModel=new ArrayList<>();


    public EventStateFragment() {
        // Required empty public constructor
    }
    public static EventStateFragment newInstance(String statue){
        EventStateFragment eventStateFragment=new EventStateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Status",statue);
        eventStateFragment.setArguments(bundle);
        return eventStateFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }
    private void onInvisible() {
        bjEventSystemListModel.clear();
    }

    private void onVisible() {
        if(!isPrepared || !isVisible) {
            return;
        }
        initNet();
        //initData();
        initView();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_state, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        isPrepared = true;
        onVisible();

    }
    private void initNet() {
        /*mErrorView.setBackgroundResource(R.drawable.loading_error);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
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
    private void initData(int i) {
        //bjEventSystemListModel.clear();
       /* final KProgressHUD hud = KProgressHUD.create(getContext());
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();*/
       if(i==1){
           bjEventSystemListModel.clear();
       }
        Bundle arguments = this.getArguments();
        String Status = (String) arguments.get("Status");
        RequestParams params=new RequestParams(HttpUrlUtils.EVENT);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("IsStart",Status);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("event", "onSuccess: "+result);
                Gson gson=new Gson();
                EventBean eventBean = gson.fromJson(result, EventBean.class);
                boolean state = eventBean.isState();
                if(state){
                    EventBean.ModelBean model = eventBean.getModel();
                    bjEventSystemListModel.addAll(model.getBJEventSystemListModel());
                    totalPage = model.getTotalPages();
                    if(bjEventSystemListModel.size()<=0){
                        mErrorView.show(rvEventState,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvEventState);
                    }
                }else{
                    ToastUtils.showCenter(getContext(),eventBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvEventState,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();
                adapter.update(bjEventSystemListModel);

            }
        });
    }

    private void initView() {
        for (int i = 0; i <10 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<EventBean.ModelBean.BJEventSystemListModelBean>(getContext(),bjEventSystemListModel,
                R.layout.item_event_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, EventBean.ModelBean.BJEventSystemListModelBean model) {
                if(bjEventSystemListModel.size()<=0){
                    return;
                }
                ImageView ivPic = (ImageView) holder.getViewById(R.id.iv_event);
                x.image().bind(ivPic,model.getEventAppCoverPictureUrl(),imageOptions);
                holder.setText(R.id.tv_event_time_competition,model.getApplyEndTimeStr());
                holder.setText(R.id.tv_event_time_apply,model.getApplyStartTimeStr());
                holder.setText(R.id.tv_event_name,model.getName());
                TextView apply = (TextView) holder.getViewById(R.id.tv_apply);
                boolean isRegister = model.isIsRegister();
                //ToastUtils.showCenter(getContext(),statue);
                //Log.d("statue", "onBindNormalViewHolder: "+statue);
                if(isRegister){
                    apply.setText("正在报名>>");
                    apply.setTextColor(Color.parseColor("#ff621a"));
                    /*apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(),ApplyActivity.class);
                            startActivity(intent);

                        }
                    });*/
                }else {
                    apply.setText("报名截止>>");
                    apply.setTextColor(Color.parseColor("#9b9b9b"));
                }
               /* switch (statue){
                    case "null":
                        apply.setText("正在报名>>");
                        break;
                    case "true":
                        apply.setText("进行中>>");
                        break;
                    case "false":
                        apply.setText("已结束>>");
                        break;*/
                //}

            }
        };
        rvEventState.setAdapter(adapter);
        rvEventState.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBean.ModelBean.BJEventSystemListModelBean bjEventSystemListModelBean = bjEventSystemListModel.get(position);
                String eventId =bjEventSystemListModelBean .getId()+"";
                String url = bjEventSystemListModelBean.getUrl();
                SingleClass.getInstance().setEventId(eventId);
                String eventId1 = SingleClass.getInstance().getEventId();
                String name = bjEventSystemListModelBean.getName();
                Log.d("eventId1", "onItemClick: "+eventId1);
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                //Intent intent=new Intent(getActivity(), EventActivity.class);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjEventSystemListModel.clear();
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
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
    }
}
