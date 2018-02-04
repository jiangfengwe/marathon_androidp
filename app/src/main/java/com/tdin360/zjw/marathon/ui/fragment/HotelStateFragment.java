package com.tdin360.zjw.marathon.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店状态列表
 */
public class HotelStateFragment extends BaseFragment {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.rv_hotel_state)
    private RecyclerView rvHotel;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    private boolean flag=false;
    private List<OrderHotelBean.ModelBean.BJHotelOrderListModelBean> bjHotelOrderListModel=new ArrayList<>();

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    @ViewInject(R.id.springView)
    private SpringView springView;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private boolean isVisible;
    private boolean isPrepared;

    public HotelStateFragment() {
        // Required empty public constructor
    }

    public static HotelStateFragment newInstance(String statue){
        HotelStateFragment hotelStateFragment=new HotelStateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Status",statue);
        hotelStateFragment.setArguments(bundle);
        return hotelStateFragment;
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

    private void onVisible() {
        if(!isPrepared || !isVisible) {
            return;
        }
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initNet();
        //initData();
        initView();
    }

    private void onInvisible() {
        bjHotelOrderListModel.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_hotel_state, container, false);
        if(!flag){
            EventBus.getDefault().register(this);
            flag=!flag;
        }
        return inflate;
    }
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()== EnumEventBus.ORDERHOTELCANCEL){
            //取消预约成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjHotelOrderListModel.clear();
            initData();
        }
        if(event.getEnumEventBus()== EnumEventBus.HOTELREFUND){
            //申请退款成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjHotelOrderListModel.clear();
            initData();
        }
        if(event.getEnumEventBus()== EnumEventBus.PAYHOTEL){
            //支付成功
            //Toast.makeText(getContext(),"school",Toast.LENGTH_LONG).show();
            bjHotelOrderListModel.clear();
            initData();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void initData() {
        //bjHotelOrderListModel.clear();
        Bundle arguments = this.getArguments();
        String Status = (String) arguments.get("Status");
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getContext());
        String customerId = loginInfo.getId() + "";
        RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("customerId",customerId);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("Status",Status);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("orderhotel", "onSuccess: "+result);
                Gson gson=new Gson();
                OrderHotelBean orderHotelBean = gson.fromJson(result, OrderHotelBean.class);
                boolean state = orderHotelBean.isState();
                if(state){
                    OrderHotelBean.ModelBean model = orderHotelBean.getModel();
                    bjHotelOrderListModel.addAll(model.getBJHotelOrderListModel());
                    totalPage= model.getTotalPages();
                    if(bjHotelOrderListModel.size()<=0){
                        mErrorView.show(rvHotel,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvHotel);
                    }
                }
                else{
                    ToastUtils.showCenter(getContext(),orderHotelBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvHotel,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(bjHotelOrderListModel);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        onVisible();
      /*  layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initNet();
        //initData();
        initView();*/
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
        if(NetWorkUtils.isNetworkAvailable(getContext())){
            //加载网络数据
            initData();
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

    private void initView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<OrderHotelBean.ModelBean.BJHotelOrderListModelBean>(getContext(),
                bjHotelOrderListModel, R.layout.item_order_hotel_state_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, OrderHotelBean.ModelBean.BJHotelOrderListModelBean model) {
                holder.setText(R.id.tv_order_hotel_name,model.getHotelName());
                holder.setText(R.id.tv_order_hotel_time,model.getOrderTimeStr());
                holder.setText(R.id.tv_order_hotel_room,model.getHotelRoomName());
                holder.setText(R.id.tv_order_hotel_count,model.getRoomNumber()+"间");
                holder.setText(R.id.tv_order_hotel_state,model.getStatus());
                holder.setText(R.id.tv_order_hotel_price,model.getTotalMoney()+"");
                holder.setText(R.id.tv_order_hotel_pay_time,"剩余支付时间："+model.getPayTime());
                TextView textView = (TextView) holder.getViewById(R.id.tv_order_hotel_state);
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
        rvHotel.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvHotel.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderHotelBean.ModelBean.BJHotelOrderListModelBean bjHotelOrderListModelBean = bjHotelOrderListModel.get(position);
                SingleClass.getInstance().setBjHotelOrderListModelBean(bjHotelOrderListModelBean);
                Intent intent=new Intent(getActivity(), HotelOrderDetailActivity.class);
                String orderId= bjHotelOrderListModelBean.getId() + "";
                intent.putExtra("orderId",orderId);
                Log.d("state", "onItemClick: "+orderId);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjHotelOrderListModel.clear();
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
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));

    }
}
