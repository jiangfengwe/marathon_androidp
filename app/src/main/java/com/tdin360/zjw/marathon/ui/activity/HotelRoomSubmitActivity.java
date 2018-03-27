package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
/**
 * 酒店预定详情
 */

public class HotelRoomSubmitActivity extends BaseActivity {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_hotel_room_pay)
    private TextView tvPay;
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
    @ViewInject(R.id.order_hotel_live_name)
    private TextView tvLiveName;
    ImageOptions imageOptions;

    @ViewInject(R.id.hotel_order_webview)
    private WebView webView;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;

    public static HotelRoomSubmitActivity instance;
    public HotelRoomSubmitActivity() {
        instance=this;
        // Required empty public constructor
    }
    public void finishActivity(){
        finish();
    }



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
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        initToolbar();
        initData();
        initWeb();
    }
    private void initWeb() {
        Intent intent=getIntent();
        String orderId = intent.getStringExtra("orderId");
        String  url = HttpUrlUtils.HOTEL_ORDER_WEBVIEW+"?appKey="+HttpUrlUtils.appKey+"&orderId="+orderId;
        Log.d("orderIdurl", "onCreate: "+url);
        Log.d("orderIdurl", "onCreate: "+url);
        webView.getSettings().setUseWideViewPort(true);//内容适配，设置自适应任意大小的pc网页
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webView.getSettings().setBuiltInZoomControls(false);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setWebChromeClient(new MyWebChromeClient());
        this.webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }
    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            progressBar.setProgress(i);
            if(i==100){
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            // titleTv.setText(s);
        }
        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {

        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {

        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

            return true;
        }


    }
    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            progressBar.setVisibility(View.GONE);
        }
        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            return  false;
        }
    }

    private void initData() {
        HotelOrderInfoBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel = SingleClass.getInstance().getBjHotelOrderModel();
        List<HotelOrderInfoBean.ModelBean.BJHotelStayInCustomerListModelBean> bjHotelStayInCustomerListModel =
                SingleClass.getInstance().getBjHotelStayInCustomerListModel();
        final String orderNo = bjHotelOrderModel.getOrderNo();
        final String orderId = bjHotelOrderModel.getId() + "";
        for (int i = 0; i < bjHotelStayInCustomerListModel.size(); i++) {
            String name = bjHotelStayInCustomerListModel.get(i).getName();
            tvLiveName.setText(name);
        }
        x.image().bind(ivPiv,bjHotelOrderModel.getHotelPictureUrl(),imageOptions);
        tvName.setText(bjHotelOrderModel.getHotelName());
        tvPrice.setText(bjHotelOrderModel.getTotalMoney()+"");
        tvCount.setText(bjHotelOrderModel.getRoomNumber()+"间");
        tvRoomName.setText(bjHotelOrderModel.getHotelRoomName());
        tvPhone.setText(bjHotelOrderModel.getPhone()+"");
        tvIn.setText(bjHotelOrderModel.getEnterDateStr());
        tvOut.setText(bjHotelOrderModel.getLeaveDateStr());
        tvNumber.setText(orderNo);
        tvOrder.setText(bjHotelOrderModel.getOrderTimeStr());
        //去支付
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelRoomSubmitActivity.this,PayActivity.class);
                intent.putExtra("type","hotel");
                intent.putExtra("orderNumber",orderNo);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payOrder","hotelpay");
                startActivity(intent);
            }
        });
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
        return R.layout.activity_hotel_room_submit;
    }
}
