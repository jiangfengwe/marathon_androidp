package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.HotelDetailBean;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 酒店详情
 */

public class HotelRoomActivity extends BaseActivity {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_hotel_room_use)
    private TextView tvIntro;
    @ViewInject(R.id.tv_hotel_room_order)
    private TextView tvOrder;

    @ViewInject(R.id.iv_room_detail_piv)
    private ImageView ivPic;
    @ViewInject(R.id.tv_room_detail_name)
    private TextView tvName;
    @ViewInject(R.id.tv_room_detail_classes)
    private TextView tvClasses;
    @ViewInject(R.id.tv_room_detail_price)
    private TextView tvPrice;
    @ViewInject(R.id.tv_room_detail_floor)
    private TextView tvFloor;
    @ViewInject(R.id.tv_room_detail_free)
    private TextView tvFree;
    @ViewInject(R.id.tv_room_detail_bed)
    private TextView tvBed;
    @ViewInject(R.id.tv_room_detail_breakfast)
    private TextView tvBreakfrast;
    @ViewInject(R.id.tv_room_detail_residue)
    private TextView tvResidue;

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
        initView();

    }

    private void initView() {
        String name = getIntent().getStringExtra("name");
        final HotelDetailBean.ModelBean.BJHotelRoomListModelBean bjHotelRoomListModelBean = SingleClass.getInstance().getBjHotelRoomListModelBean();
        x.image().bind(ivPic,bjHotelRoomListModelBean.getPictureUrl(),imageOptions);
        tvName.setText(name);
        tvPrice.setText(bjHotelRoomListModelBean.getPrice()+"");
        tvClasses.setText(bjHotelRoomListModelBean.getName());
        tvFree.setText(bjHotelRoomListModelBean.getWindow()+"、"+bjHotelRoomListModelBean.getNetwork()+"、"+bjHotelRoomListModelBean.getBathroom());
        tvBed.setText(bjHotelRoomListModelBean.getBedType());
        tvBreakfrast.setText(bjHotelRoomListModelBean.getBreakfast());
        boolean isBooking = bjHotelRoomListModelBean.isIsBooking();
        tvOrder.setText("立即预定");
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelRoomActivity.this,HotelRoomInActivity.class);
                String hotelRoomId = bjHotelRoomListModelBean.getId() + "";
                intent.putExtra("hotelRoomId",hotelRoomId);
                intent.putExtra("hotelprice",bjHotelRoomListModelBean.getPrice());
                startActivity(intent);
            }
        });
        String str="<font color='#ff621a'>使用说明：</font>"+bjHotelRoomListModelBean.getInstructions();
        tvIntro.setText(Html.fromHtml(str));

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
        titleTv.setText("房型详情");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_room;
    }
}
