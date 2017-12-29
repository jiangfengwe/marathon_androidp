package com.tdin360.zjw.marathon.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.MyHotelOrderModel;
import com.tdin360.zjw.marathon.ui.activity.HotelOrderDetailsActivity;
import com.tdin360.zjw.marathon.ui.activity.HotelOrderSubmitActivity;
import com.tdin360.zjw.marathon.ui.activity.PayActivity;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tdin360.zjw.marathon.weight.ZJSpaceItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预订
 * Created by admin on 17/7/26.
 */

public class MyHotelOrderFragment extends BaseFragment {


    private RecyclerViewBaseAdapter adapter;
    @ViewInject(R.id.mRecyclerView)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    private List<MyHotelOrderModel>list=new ArrayList<>();

    public static MyHotelOrderFragment newInstance(){

        return new MyHotelOrderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_my_hotel_order,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        this.adapter =new MyAdapter(getActivity(),list,R.layout.my_hotel_order_list);
        recyclerView.setAdapter(adapter);

        /**
         * 加载失败点击重试
         */
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:
                        //httpRequest();
                        break;

                }
            }
        });

        /**
         * 点击查看订单详情
         */
        this.adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getContext(), HotelOrderDetailsActivity.class);
                intent.putExtra("bigImageUrl",list.get(position).getPictureUrl());
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("title",list.get(position).getHotelName());
                startActivity(intent);
            }
        });


        // httpRequest();

    }

    /**
     * 更新订单列表
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){

        if(event.getType()== MessageEvent.MessageType.HOTEL_STATS_UPDATE) {

            list.clear();
            //httpRequest();
        }
    }

    /*private void httpRequest(){

        final KProgressHUD hud = KProgressHUD.create(getContext());
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
        RequestParams params = new RequestParams(HttpUrlUtils.MY_HOTEL_ORDER);

        params.addBodyParameter("customerId", SharedPreferencesManager.getLoginInfo(getContext()).getId());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

//                Log.d("-----order--", "onSuccess: "+result);
                try {
                    JSONObject object = new JSONObject(result);

                    JSONObject message = object.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");
                    if(success){

                        JSONArray array = object.getJSONArray("EventMyReservationList");

                        for(int i=0;i<array.length();i++){

                            JSONObject jsonObject = array.getJSONObject(i);
                        list.add(new Gson().fromJson(jsonObject.toString(),MyHotelOrderModel.class));
                        }


                    }else {


                        ToastUtils.showCenter(getContext(),reason);

                    }

                    if(list.size()<=0){

                        mErrorView.show(mRecyclerView,"您还没有订单信息",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(mRecyclerView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    mErrorView.show(mRecyclerView,"服务器数据异常",ErrorView.ViewShowMode.ERROR);

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                mErrorView.show(mRecyclerView,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(getActivity(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();
                adapter.update(list);

            }
        });


    }*/



    /**
     * 数据适配器
     */
    class MyAdapter extends RecyclerViewBaseAdapter<MyHotelOrderModel>{


        public MyAdapter(Context context, List<MyHotelOrderModel> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        protected void onBindNormalViewHolder(NormalViewHolder holder, final MyHotelOrderModel model) {


            ImageView view = (ImageView) holder.getViewById(R.id.hotel_imageView);
            x.image().bind(view,model.getPictureUrl());
            holder.setText(R.id.hotel_name,model.getHotelName());
            final TextView btn = (TextView) holder.getViewById(R.id.cancel_Order);

            switch (model.getStatus()){

                case "待支付":
                    btn.setEnabled(true);
                    btn.setText("去支付");
                    holder.setImageResource(R.id.status_imageView,R.drawable.status_paying);
                    break;
                case "已预订":
                    btn.setEnabled(true);
                    btn.setText("取消预订");
                    holder.setImageResource(R.id.status_imageView,R.drawable.status_payed);
                    break;
                case "已过期":
                    btn.setEnabled(false);
                    btn.setText(model.getStatus());
                    holder.setImageResource(R.id.status_imageView,R.drawable.status_old);
                    break;
                case "已取消":
                    btn.setEnabled(false);
                    btn.setText(model.getStatus());
                    holder.setImageResource(R.id.status_imageView,R.drawable.status_cancel);
                    break;
                case "已完成":
                    btn.setEnabled(false);
                    btn.setText(model.getStatus());
                    holder.setImageResource(R.id.status_imageView,R.drawable.status_finished);
                    break;

            }


            holder.getViewById(R.id.cancel_Order).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(btn.getText().toString().equals("取消预订")){


                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                        alert.setTitle("温馨提示");
                        alert.setMessage("确定取消该订单吗?");
                        alert.setCancelable(false);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                cancel(model.getOrderNo());
                            }
                        });

                        alert.setNegativeButton("取消", null);

                        alert.show();

                    }else if(model.getStatus().equals("待支付")){


                        //去支付
                        Intent intent = new Intent(getContext(),PayActivity.class);
                        intent.putExtra("money",model.getMoney());
                        intent.putExtra("order",model.getOrderNo());
                        intent.putExtra("subject","酒店预订费用");
                        intent.putExtra("isFormDetail",true);
                        intent.putExtra("isHotel",true);
                        startActivity(intent);

                    }

                }
            });



        }
    }


    /**
     * 取消订单
     */
    public  void cancel(String orderNo){

        final KProgressHUD hud = KProgressHUD.create(getActivity());
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        /*RequestParams params = new RequestParams(HttpUrlUtils.CANCEL_HOTEL_ORDER);
        params.addBodyParameter("orderNo",orderNo);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);

       x.http().post(params, new Callback.CommonCallback<String>() {
           @Override
           public void onSuccess(String result) {

               try {
                   JSONObject  object = new JSONObject(result);

                   JSONObject message = object.getJSONObject("EventMobileMessage");

                   boolean success = message.getBoolean("Success");
                   String reason = message.getString("Reason");
                   if(success){
                       list.clear();
                       //httpRequest();
                       ToastUtils.show(getContext(),"取消成功");


                   }else {

                       ToastUtils.show(getContext(),reason);
                   }


               } catch (JSONException e) {
                   e.printStackTrace();
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

               hud.dismiss();
           }
       });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }
}
