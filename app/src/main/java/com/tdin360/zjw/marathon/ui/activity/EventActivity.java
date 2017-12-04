package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;
/**
 *  赛事详情
 */

public class EventActivity extends BaseActivity {
    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;
    /*@ViewInject(R.id.close)
    private ImageView ivApply;*/

    /*@ViewInject(R.id.event_day)
    private TextView tvDay;
    @ViewInject(R.id.event_hour)
    private TextView tvHour;*/

    @ViewInject(R.id.apply)
    private TextView apply;
    @ViewInject(R.id.iv_hotel)
    private ImageView ivHotel;
    @ViewInject(R.id.iv_travel)
    private ImageView ivTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
        //tvDay.setText("距离开赛还有"+"09"+"天");
        //tvHour.setText("16"+"小时");
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventActivity.this,ApplyActivity.class);
                startActivity(intent);
            }
        });
        ivHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventActivity.this,HotelActivity.class);
                startActivity(intent);
            }
        });
        ivTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventActivity.this,TravelActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        rightImage.setImageResource(R.drawable.share);
        titleTv.setText("2017贵阳马拉松");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(EventActivity.this,"share");
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_event;
    }
}
