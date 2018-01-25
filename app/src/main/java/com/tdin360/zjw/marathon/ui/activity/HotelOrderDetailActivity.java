package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OrderHOtelDetailBean;
import com.tdin360.zjw.marathon.model.OrderHotelBean;
import com.tdin360.zjw.marathon.model.OrderHotelCancelBean;
import com.tdin360.zjw.marathon.model.OrderHotelDetailDecryptBean;
import com.tdin360.zjw.marathon.model.RefundHotelBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 我的订单中酒店订单详情
 */

public class HotelOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_order_hotel_pay)
    private TextView tvPay;
    @ViewInject(R.id.tv_order_hotel_cancel)
    private TextView tvCancel;
    @ViewInject(R.id.order_hotel_pic)
    private ImageView ivPiv;
    @ViewInject(R.id.order_hotel_name)
    private TextView tvName;
    @ViewInject(R.id.order_hotel_phone)
    private TextView tvPhone;
    @ViewInject(R.id.order_hotel_count)
    private TextView tvCount;
    @ViewInject(R.id.order_hotel_in)
    private TextView tvIn;
    @ViewInject(R.id.order_hotel_out)
    private TextView tvOut;
    @ViewInject(R.id.order_hotel_number)
    private TextView tvNumber;
    @ViewInject(R.id.order_hotel_price)
    private TextView tvPrice;
    @ViewInject(R.id.order_hotel_room_name)
    private TextView tvRoomName;
    @ViewInject(R.id.order_hotel_order)
    private TextView tvOrder;
    @ViewInject(R.id.btn_hotel_order_detail)
    private Button btn;

    private OrderHotelDetailDecryptBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel=new OrderHotelDetailDecryptBean.ModelBean.BJHotelOrderModelBean();


    ImageOptions imageOptions;

    @ViewInject(R.id.layout_order_hotel_detail)
    private LinearLayout layoutShow;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
       // initDetailData();
        initNet();

        /* tView.setText(Html.fromHtml(sText, null, new MxgsaTagHandler(this)));
        tView.setClickable(true);
        tView.setMovementMethod(LinkMovementMethod.getInstance());*/

    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initDetailData();
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(getApplicationContext())){
            //加载网络数据
            initDetailData();
        }else {

            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
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

    private void initDetailData() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        String orderId = getIntent().getStringExtra("orderId");
        RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_DETAIL);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("orderId",orderId);
        params.setConnectTimeout(5000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                OrderHOtelDetailBean orderHOtelDetailBean = gson.fromJson(result, OrderHOtelDetailBean.class);
                boolean state = orderHOtelDetailBean.isState();
                if(state){
                   String orderSecretMessage = orderHOtelDetailBean.getOrderSecretMessage();
                    String decrypt = AES.decrypt(orderSecretMessage);
                    OrderHotelDetailDecryptBean orderHotelDetailDecryptBean = gson.fromJson(decrypt, OrderHotelDetailDecryptBean.class);
                    OrderHotelDetailDecryptBean.ModelBean model = orderHotelDetailDecryptBean.getModel();
                    bjHotelOrderModel= model.getBJHotelOrderModel();
                    String payMethod = bjHotelOrderModel.getPayMethod();
                    initData(payMethod);
                    Log.d("hotelorderS", "onSuccess: "+bjHotelOrderModel.getStatus());

                }else{
                    ToastUtils.showCenter(getApplicationContext(),orderHOtelDetailBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // mErrorView.show(rvHotel,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getApplicationContext(),"网络不给力,连接服务器异常!");
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

    private void initData(final String payMethod) {
        final OrderHotelBean.ModelBean.BJHotelOrderListModelBean bjHotelOrderListModelBean = SingleClass.getInstance().getBjHotelOrderListModelBean();
        Log.d("orderbacksss", "initRefund: "+bjHotelOrderListModelBean);
        final String orderNo = bjHotelOrderModel.getOrderNo();
        x.image().bind(ivPiv,bjHotelOrderModel.getHotelPictureUrl(),imageOptions);
        tvName.setText(bjHotelOrderModel.getHotelName());
        tvPrice.setText(bjHotelOrderModel.getTotalMoney()+"");
        tvCount.setText(bjHotelOrderModel.getRoomNumber()+"间");
        tvRoomName.setText(bjHotelOrderModel.getHotelRoomName());
        tvPhone.setText(bjHotelOrderModel.getPhone()+"");
        tvIn.setText(bjHotelOrderModel.getEnterDateStr());
        tvOut.setText(bjHotelOrderModel.getLeaveDateStr());
        tvNumber.setText(orderNo);
        boolean isCancel = bjHotelOrderListModelBean.isIsCancel();
        boolean isUsing = bjHotelOrderListModelBean.isIsUsing();
        boolean isPay = bjHotelOrderListModelBean.isIsPay();
        String status = bjHotelOrderListModelBean.getStatus();
       /* if(status.equals("7")){
            btn.setVisibility(View.VISIBLE);
            btn.setText("退款成功");
        }else {
            btn.setVisibility(View.GONE);
        }*/
        boolean isRefund = bjHotelOrderListModelBean.isIsRefund();
        //final String payMethod = bjHotelOrderListModelBean.getPayMethod();
        Log.d("orderbacksss", "initRefund: "+payMethod);
        if(isCancel){
            layoutShow.setVisibility(View.GONE);
        }else{
            if(isPay){
                layoutShow.setVisibility(View.GONE);
                if(isUsing){
                    btn.setVisibility(View.VISIBLE);
                    btn.setText("去评价");
                }else{
                    if(status.equals("6")){
                        btn.setVisibility(View.VISIBLE);
                        btn.setText("退款中");
                    }else if(status.equals("7")){
                        btn.setVisibility(View.VISIBLE);
                        btn.setText("退款成功");
                    } else{
                    btn.setVisibility(View.VISIBLE);
                    btn.setText("申请退款");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initRefund();
                        }
                        private void initRefund() {
                            try{
                                byte[] mBytes=null;
                                final KProgressHUD hud = KProgressHUD.create(HotelOrderDetailActivity.this);
                                hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setCancellable(true)
                                        .setAnimationSpeed(1)
                                        .setDimAmount(0.5f)
                                        .show();
                                String string="{\"orderNumber\":"+"\""+orderNo+"\",\"refundDesc\":"+"\""+"不喜欢"+"\",\"payMethod\":"+"\""+payMethod+"\",\"type\":"+"\""+"hotel"+"\",\"appKey\":\"BJYDAppV-2\"}";
                                Log.d("orderback", "initRefund: "+string);
                                mBytes=string.getBytes("UTF8");
                                String enString= AES.encrypt(mBytes);
                                RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_BACK_MONEY);
                                params.addBodyParameter("secretMessage",enString);
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        Log.d("loginfund", "onSuccess: "+result);
                                        Gson gson=new Gson();
                                        RefundHotelBean refundHotelBean = gson.fromJson(result, RefundHotelBean.class);
                                        boolean state = refundHotelBean.isState();
                                        if(state){
                                            ToastUtils.show(getApplicationContext(),refundHotelBean.getMessage());

                                            finish();
                                        }else{
                                            ToastUtils.show(getApplicationContext(),refundHotelBean.getMessage());
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
                                       // layoutLoading.setVisibility(View.GONE);
                                        hud.dismiss();
                                    }
                                });

                            }catch(Exception e){

                                //Log.d("error", "initData: "+e.getMessage());
                            }
                        }
                    });
                    }
                }
            }else{
                layoutShow.setVisibility(View.VISIBLE);
            }

        }

        tvOrder.setText(bjHotelOrderListModelBean.getOrderTimeStr());
        //去支付
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelOrderDetailActivity.this,PayActivity.class);
                intent.putExtra("type","hotel");
                intent.putExtra("orderNumber",orderNo);
                startActivity(intent);
               /* LoginUserInfoBean.UserBean loginInfo =
                        SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String customerId = loginInfo.getId();
                if(TextUtils.isEmpty(customerId)){
                    Intent intent=new Intent(HotelOrderDetailActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(HotelOrderDetailActivity.this,HotelCommentActivity.class);
                    intent.putExtra("orderId",bjHotelOrderListModelBean.getId()+"");
                    startActivity(intent);
                }
*/
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCancel();
            }
            private void initCancel() {
                final KProgressHUD hud = KProgressHUD.create(HotelOrderDetailActivity.this);
                hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(1)
                        .setDimAmount(0.5f)
                        .show();
                String orderId =bjHotelOrderListModelBean.getId() + "";
                RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_ORDER_CANCEL);
                params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                params.addBodyParameter("orderId",orderId);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("cancelhotel", "onSuccess: "+result);
                        Gson gson=new Gson();
                        OrderHotelCancelBean orderHotelCancelBean = gson.fromJson(result, OrderHotelCancelBean.class);
                        boolean state = orderHotelCancelBean.isState();
                        if(state){
                            //ToastUtils.showCenter(getApplicationContext(),orderHotelCancelBean.getMessage());
                            EnumEventBus cancelHotel = EnumEventBus.ORDERHOTELCANCEL;
                            EventBus.getDefault().post(new EventBusClass(cancelHotel));
                            finish();
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),orderHotelCancelBean.getMessage());
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
                });
            }
        });
    }

    private void initView() {
        //tvPay.setOnClickListener(this);
       /* String str="订单已完成，<font color='#ff621a'>再次预定</font>";
        tvOrderText.setText(Html.fromHtml(str));*/

    }

    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewline.setVisibility(View.GONE);
        titleTv.setText("预定详情");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_order_detail;
    }

    @Override
    public void onClick(View v) {
        /*Intent intent;
        switch (v.getId()){
            case R.id.tv_hotel_pay:
                intent=new Intent(HotelOrderDetailActivity.this,PayActivity.class);
                startActivity(intent);
                break;
        }*/

    }
}
