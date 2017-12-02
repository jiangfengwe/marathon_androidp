package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }

    private void initView() {
        String str="<font color='#ff621a'>使用说明：</font>觉得广播课题句话暖宫尽快帮您UR局UI人进步并不能杰克马门口v模具费没看见";
        tvIntro.setText(Html.fromHtml(str));
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotelRoomActivity.this,HotelRoomInActivity.class);
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
        titleTv.setText("房型详情");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_room;
    }
}
