package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.HotelItem;
import com.tdin360.zjw.marathon.model.HotelOrderDetailsModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的预订详情
 */
public class HotelOrderDetailsActivity extends BaseActivity {

    @ViewInject(R.id.hotel_imageView)
    private ImageView hotel_ImageView;
    private String id="-1";
    private String hotel_title;
    @ViewInject(R.id.hotel_name)
    private TextView hotel_name;
    @ViewInject(R.id.room_name)
    private TextView room_Name;
    @ViewInject(R.id.room_bad_size)
    private TextView room_bad_Size;
    @ViewInject(R.id.room_area)
    private TextView room_Area;
    @ViewInject(R.id.room_imageView)
    private ImageView room_ImageView;
    @ViewInject(R.id.room_price)
    private TextView room_Price;
    @ViewInject(R.id.cancel_Order)
    private TextView cancel;
    @ViewInject(R.id.roomCount_text)
    private TextView roomCount;
    @ViewInject(R.id.name)
    private TextView nameText;
    @ViewInject(R.id.idCardNumber)
    private TextView idCardNumberText;
    @ViewInject(R.id.inDate)
    private TextView inDateText;
    @ViewInject(R.id.outDate)
    private TextView outDateText;
    @ViewInject(R.id.createTime)
    private TextView createTimeText;
    @ViewInject(R.id.status_imageView)
    private ImageView status_imageView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    @ViewInject(R.id.main)
    private ScrollView main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         showBackButton();
         setToolBarTitle("订单详情");
        initView();
    }

    private void initView() {

        String bigImageUrl="";
        Intent intent = getIntent();
        if(intent!=null){

          bigImageUrl = intent.getStringExtra("bigImageUrl");
          this.id=intent.getStringExtra("id");
          this.hotel_title = intent.getStringExtra("title");
        }

        x.image().bind(hotel_ImageView,bigImageUrl);

        /**
         * 加载失败点击重试
         */
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:
                        httpRequest();
                        break;

                }
            }
        });

        httpRequest();
    }

    private void httpRequest(){


        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

       /* RequestParams params = new RequestParams(HttpUrlUtils.HOTEL_ORDER_DETAILS);
        params.addBodyParameter("reservationId",id);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

//                Log.d("-------详情---->", "onSuccess: "+result);

                try {
                    JSONObject object = new JSONObject(result);
                    HotelOrderDetailsModel model = new Gson().fromJson(object.toString(), HotelOrderDetailsModel.class);
                    JSONObject typeModel = object.getJSONObject("EventHotelTypeModel");
                    HotelItem hotelItem = new Gson().fromJson(typeModel.toString(), HotelItem.class);
                    model.setArea(hotelItem.getArea());
                    model.setPictureUrl(hotelItem.getPictureUrl());
                    model.setTitle(hotelItem.getTitle());
                    model.setType(hotelItem.getType());
                    showInfo(model);
                 mErrorView.hideErrorView(main);
                } catch (JSONException e) {
                    e.printStackTrace();

                    mErrorView.show(main,"服务器数据异常",ErrorView.ViewShowMode.ERROR);

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                mErrorView.show(main,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(getBaseContext(),"网络不给力,连接服务器异常!");
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

    private void showInfo(final HotelOrderDetailsModel model){

         if(model==null)return;

          this.hotel_name.setText(hotel_title);
          this.room_Name.setText(model.getTitle());
          this.room_Area.setText(model.getArea()+"平米");
          this.room_bad_Size.setText(model.getType());
          this.room_Price.setText(model.getTotalMoney()+"元");
          this.roomCount.setText(model.getRoomNumber());
          x.image().bind(room_ImageView,model.getPictureUrl());
          this.nameText.setText(model.getName());
          this.idCardNumberText.setText(model.getIDNumber());
          this.inDateText.setText(model.getEnterDates());
          this.outDateText.setText(model.getLeaveDates());
          this.createTimeText.setText(model.getCreateTimeS());


        switch (model.getStatus()){

            case "待支付":
                cancel.setText("去支付");
                status_imageView.setImageResource(R.drawable.status_paying);
                break;
            case "已预订":
                cancel.setText("取消预订");
                status_imageView.setImageResource(R.drawable.status_payed);
                break;
            case "已过期":
                cancel.setEnabled(false);
                cancel.setText(model.getStatus());
                status_imageView.setImageResource(R.drawable.status_old);
                break;
            case "已取消":
                cancel.setEnabled(false);
                cancel.setText(model.getStatus());
                status_imageView.setImageResource(R.drawable.status_cancel);
                break;
            case "已完成":
                cancel.setEnabled(false);
                cancel.setText(model.getStatus());
                status_imageView.setImageResource(R.drawable.status_finished);
                break;

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cancel.getText().toString().equals("取消预订")){


                    AlertDialog.Builder alert = new AlertDialog.Builder(HotelOrderDetailsActivity.this);

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
                    Intent intent = new Intent(getBaseContext(),PayActivity.class);
                    intent.putExtra("money",model.getTotalMoney());
                    intent.putExtra("order",model.getOrderNo());
                    intent.putExtra("subject","酒店预订费用");
                    intent.putExtra("isFormDetail",true);
                    intent.putExtra("isHotel",true);
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_order_deatils;
    }


    /**
     * 取消订单
     */
    public  void cancel(String orderNo){

        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

//        Log.d("-------订单号---->", "onSuccess: "+orderNo);
      /*  RequestParams params = new RequestParams(HttpUrlUtils.CANCEL_HOTEL_ORDER);
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
                        status_imageView.setImageResource(R.drawable.status_cancel);
                        cancel.setText("已取消");
                        cancel.setEnabled(false);
                        ToastUtils.show(getBaseContext(),"取消成功");
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.MessageType.HOTEL_STATS_UPDATE));


                    }else {

                        ToastUtils.show(getBaseContext(),reason);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                    ToastUtils.show(getBaseContext(),"服务器数据格式异常");

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                ToastUtils.show(getBaseContext(),"网络不给力,无法连接服务器");


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

}
