package com.tdin360.zjw.marathon.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OrderHotelBean;
import com.tdin360.zjw.marathon.model.OrderTravelBean;
import com.tdin360.zjw.marathon.ui.activity.HotelOrderDetailActivity;
import com.tdin360.zjw.marathon.ui.activity.TravelOrderDetailActivity;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 旅游订单状态fragment，动态生成
 */
public class TravelStateFragment extends BaseFragment {

    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.rv_travel_state)
    private RecyclerView rvState;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    private List<OrderTravelBean.ModelBean.BJTravelOrderListModelBean> bjTravelOrderListModel=new ArrayList<>();

    ImageOptions imageOptions;

    private boolean flag=false;

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=5;
    @ViewInject(R.id.springView)
    private SpringView springView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private boolean isVisible;
    private boolean isPrepared;

    public TravelStateFragment() {
        // Required empty public constructor
    }
    public static TravelStateFragment newInstance(String status){
        TravelStateFragment travelStateFragment=new TravelStateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Status",status);
        travelStateFragment.setArguments(bundle);
        return travelStateFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_travel_state, container, false);
        if(!flag){
            EventBus.getDefault().register(this);
            flag=!flag;
        }
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                .setUseMemCache(true).build();
        /*isPrepared = true;
        onVisible();*/
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
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
                        //ToastUtils.showCenter(getContext(),"reset");
                        layoutLoading.setVisibility(View.VISIBLE);
                        ivLoading.setBackgroundResource(R.drawable.loading_before);
                        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
                        background.start();
                       initData(1);
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(getContext())){
            //加载网络数据
            initData(1);
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

    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()== EnumEventBus.ORDER){
            //取消预约成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjTravelOrderListModel.clear();
            initData(1);
        }
        if(event.getEnumEventBus()== EnumEventBus.TRAVELREFUND){
            //申请退款成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjTravelOrderListModel.clear();
            initData(1);
        }
        if(event.getEnumEventBus()== EnumEventBus.PAYTRAVEL){
            //支付成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjTravelOrderListModel.clear();
            initData(1);
        }
        if(event.getEnumEventBus()== EnumEventBus.TRAVELCOMMENTORDER){
            //支付成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjTravelOrderListModel.clear();
            initData(1);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData(int i) {
        if(i==1){
            bjTravelOrderListModel.clear();
        }
        Bundle arguments = this.getArguments();
        String Status = (String) arguments.get("Status");
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
        String customerId = loginInfo.getId() + "";
        RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL_ORDER);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("customerId",customerId);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("Status",Status);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("ordertravel", "onSuccess: "+result);
                Gson gson=new Gson();
                OrderTravelBean orderTravelBean = gson.fromJson(result, OrderTravelBean.class);
                boolean state = orderTravelBean.isState();
                if(state){
                    //ToastUtils.showCenter(getContext(),orderTravelBean.getMessage());
                    OrderTravelBean.ModelBean model = orderTravelBean.getModel();
                    bjTravelOrderListModel .addAll(model.getBJTravelOrderListModel());
                    totalPage=model.getTotalPages();
                    for (int i = 0; i < bjTravelOrderListModel.size(); i++) {
                        String startPlace = bjTravelOrderListModel.get(i).getStartPlace();
                        Log.d("startPlace", "onSuccess: "+startPlace);
                    }
                    if(bjTravelOrderListModel.size()<=0){
                        mErrorView.show(rvState,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvState);
                    }

                }else{
                    ToastUtils.showCenter(getContext(),orderTravelBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //mErrorView.show(rvState,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                //ToastUtils.showCenter(getContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjTravelOrderListModel);
                layoutLoading.setVisibility(View.GONE);
               // hud.dismiss();

            }
        });

    }

    private void initView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<OrderTravelBean.ModelBean.BJTravelOrderListModelBean>(getContext(),
                bjTravelOrderListModel, R.layout.item_order_travel_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, OrderTravelBean.ModelBean.BJTravelOrderListModelBean model) {
                ImageView ivPic = (ImageView) holder.getViewById(R.id.iv_order_travel_pic);
                x.image().bind(ivPic,model.getPictureUrl(),imageOptions);
                holder.setText(R.id.tv_order_travel_count,model.getTravelNumber()+"");
                holder.setText(R.id.tv_order_travel_place,model.getStartPlace()+"————"+model.getEndPlace());
                holder.setText(R.id.tv_order_travel_price,model.getTotalMoney()+"");
                holder.setText(R.id.tv_order_travel_time,model.getOrderTimeStr());
                holder.setText(R.id.tv_order_travel_number,model.getOrderNo());
                holder.setText(R.id.tv_order_travel_pay_time,"剩余支付时间："+model.getPayTime());
                boolean isCancel = model.isIsCancel();
                TextView textView = (TextView) holder.getViewById(R.id.tv_order_travel_state);
                String status = model.getStatus();
                switch (status){
                    case "1":
                        textView.setText("待支付");
                        textView.setTextColor(Color.parseColor("#de6c64"));
                        break;
                    case "2":
                        textView.setText("已取消");
                        textView.setTextColor(Color.parseColor("#de6c64"));

                        break;
                    case "3":
                        textView.setText("待使用");
                        textView.setTextColor(Color.GREEN);

                        break;
                    case "4":
                        textView.setText("待评价");
                        textView.setTextColor(Color.RED);
                        break;
                    case "5":
                        textView.setText("已完成");
                        textView.setTextColor(Color.RED);
                        break;
                    case "6":
                        textView.setText("退款中");
                        textView.setTextColor(Color.RED);
                        break;
                    case "7":
                        textView.setText("退款成功");
                        textView.setTextColor(Color.RED);
                        break;
                }

            }
        };
        rvState.setAdapter(adapter);
        rvState.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderTravelBean.ModelBean.BJTravelOrderListModelBean bjTravelOrderListModelBean = bjTravelOrderListModel.get(position);
                SingleClass.getInstance().setBjTravelOrderListModelBean(bjTravelOrderListModelBean);
                Intent intent=new Intent(getActivity(), TravelOrderDetailActivity.class);
                String orderId = bjTravelOrderListModelBean.getId()+"";
                intent.putExtra("orderId",orderId);
                //intent.putExtra("orderId","payOrder");
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjTravelOrderListModel.clear();
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
}
