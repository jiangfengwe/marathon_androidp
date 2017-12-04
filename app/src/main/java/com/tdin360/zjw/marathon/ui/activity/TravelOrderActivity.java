package com.tdin360.zjw.marathon.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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
    //日期选择
    @ViewInject(R.id.rv_travel_month)
    private RecyclerView rvMonth;
    @ViewInject(R.id.rv_travel_day)
    private RecyclerView rvDay;
    private List<String> listMonth=new ArrayList<>();
    private List<String> listDay=new ArrayList<>();

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
        for (int i = 1; i <= 12; i++) {
            listMonth.add(i+"月");
        }
        for (int i = 1; i <= 30; i++) {
            listDay.add(i+"日");
        }
        //选择月份
        rvMonth.setAdapter(new RecyclerViewBaseAdapter<String>(getApplicationContext(),listMonth,R.layout.item_travel_day) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                TextView viewById = (TextView) holder.getViewById(R.id.tv_month);
                holder.setText(R.id.tv_month,model);

            }
        });
        rvMonth.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        //选择日期
        rvDay.setAdapter(new RecyclerViewBaseAdapter<String>(getApplicationContext(),listDay,R.layout.item_travel_day) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                holder.setText(R.id.tv_month,model);

            }
        });
        rvDay.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

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
