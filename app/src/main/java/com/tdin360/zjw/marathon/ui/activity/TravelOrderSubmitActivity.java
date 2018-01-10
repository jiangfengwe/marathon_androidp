package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.TravelOrderInfoBean;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 旅游预定提交
 */

public class TravelOrderSubmitActivity extends BaseActivity {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_travel_go_pay)
    private TextView tvPay;
    @ViewInject(R.id.travel_order_pic)
    private ImageView ivPic;
    @ViewInject(R.id.travel_order_name)
    private  TextView tvName;
    @ViewInject(R.id.travel_order_count)
    private TextView tvCount;
    @ViewInject(R.id.travel_order_price)
    private TextView tvPrice;
    @ViewInject(R.id.travel_order_time)
    private TextView tvTime;
    @ViewInject(R.id.travel_order_phone)
    private TextView tvPhone;
    @ViewInject(R.id.travel_order_number)
    private TextView tvNumber;

    ImageOptions imageOptions;
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

    }

    private void initData() {
        TravelOrderInfoBean.ModelBean.BJTravelOrderModelBean bjTravelOrderModel = SingleClass.getInstance().getBjTravelOrderModel();
        List<TravelOrderInfoBean.ModelBean.BJTravelStayInCustomerListModelBean> bjTravelStayInCustomerListModel =
                SingleClass.getInstance().getBjTravelStayInCustomerListModel();
        x.image().bind(ivPic,bjTravelOrderModel.getPictureUrl(),imageOptions);
        tvName.setText(bjTravelOrderModel.getStartPlace()+"——"+bjTravelOrderModel.getEndPlace());
        tvCount.setText(bjTravelOrderModel.getTravelNumber()+"天");
        tvPrice.setText(bjTravelOrderModel.getTotalMoney()+"");
        tvPhone.setText(bjTravelOrderModel.getPhone()+"");
        tvTime.setText(bjTravelOrderModel.getStartDatestr()+"~~"+bjTravelOrderModel.getEndDatestr());
        final String orderNo = bjTravelOrderModel.getOrderNo();
        tvNumber.setText(orderNo);
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelOrderSubmitActivity.this,PayActivity.class);
                intent.putExtra("type","travel");
                intent.putExtra("orderNumber",orderNo);
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
        titleTv.setText("预定信息");

    }

    @Override
    public int getLayout() {
        return R.layout.activity_travel_order_submit;
    }
}
