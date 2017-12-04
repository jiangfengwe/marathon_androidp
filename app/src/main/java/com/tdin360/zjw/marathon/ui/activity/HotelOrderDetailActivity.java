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
 * 我的订单中酒店订单详情
 */

public class HotelOrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_hotel_pay)
    private TextView tvPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
        /* tView.setText(Html.fromHtml(sText, null, new MxgsaTagHandler(this)));
        tView.setClickable(true);
        tView.setMovementMethod(LinkMovementMethod.getInstance());*/

    }

    private void initView() {
        tvPay.setOnClickListener(this);
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
        Intent intent;
        switch (v.getId()){
            case R.id.tv_hotel_pay:
                intent=new Intent(HotelOrderDetailActivity.this,PayActivity.class);
                startActivity(intent);
                break;
        }

    }
}
