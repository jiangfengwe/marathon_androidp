package com.tdin360.zjw.marathon.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 旅游立即预定
 */

public class TravelOrderActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_travel_choose_submit)
    private TextView tvSubimit;
    @ViewInject(R.id.tv_travel_order_dec)
    private TextView tvDec;
    @ViewInject(R.id.tv_travel_order_add)
    private TextView tvAdd;
    @ViewInject(R.id.tv_travel_order_sum)
    private TextView tvSum;
    @ViewInject(R.id.et_travel_order_phone)
    private TextView etPhone;
    private int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

    }

    private void initView() {
        tvDec.setOnClickListener(this);
        tvAdd.setOnClickListener(this);

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
        tvSubimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TravelOrderActivity.this,TravelOrderSubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_travel_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_travel_order_dec:
                if(count<=1){
                    return;
                }
                count--;
                setSum();
                break;
            case R.id.tv_travel_order_add:
                if(count>99){
                    return;
                }
                count++;
                setSum();
                break;
        }

    }

    private void setSum() {
        //订单金额
        tvSum.setText(""+count);
        //int goodCount = Integer.parseInt(textViewCount.getText().toString());
        //priceSum = goodCount * price ;
        //String c=""+priceSum;
        //textViewSum.setText(c);
    }
}
