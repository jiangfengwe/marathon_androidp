package com.tdin360.zjw.marathon.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 酒店预定，入住信息填写
 */

public class HotelRoomInActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.tv_hotel_room_intro)
    private TextView tvIntro;
    @ViewInject(R.id.tv_hotel_room_submit)
    private TextView tvSubmit;
    @ViewInject(R.id.tv_hotel_room_in)
    private TextView tvIn;
    @ViewInject(R.id.tv_hotel_room_out)
    private TextView tvOut;
    @ViewInject(R.id.tv_hotel_room_enter)
    private TextView tvEnter;
    @ViewInject(R.id.tv_hotel_room_dec)
    private TextView tvDec;
    @ViewInject(R.id.tv_hotel_room_add)
    private TextView tvAdd;
    @ViewInject(R.id.tv_hotel_room_sum)
    private TextView tvSum;
    @ViewInject(R.id.layout_hotel_room_info)
    private LinearLayout LayoutInfo;
    private int count=1;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        str=formatter.format(curDate);
        tvIn.setText(str);
        tvOut.setText(str);
        initToolbar();
        initView();

    }

    private void initView() {
        String str="<font color='#ff621a'>入住说明：</font>觉得广播课题句话暖宫尽快帮您UR局UI人进步并不能杰克马门口v模具费没看见";
        tvIntro.setText(Html.fromHtml(str));

        tvIn.setOnClickListener(this);
        tvOut.setOnClickListener(this);
        tvDec.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);


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
        titleTv.setText("入住信息");
    }
    @Override
    public int getLayout() {
        return R.layout.activity_hotel_room_in;
    }
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //display();
            tvIn.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));

        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        final Calendar ca = Calendar.getInstance();
        switch (v.getId()){
            case R.id.tv_hotel_room_in:
                //入住时间
                showDialog(DATE_DIALOG);
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);

                break;
            case R.id.tv_hotel_room_out:
                //离店时间
                showDialog(DATE_DIALOG);
                mYear = ca.get(Calendar.YEAR);
                mMonth = ca.get(Calendar.MONTH);
                mDay = ca.get(Calendar.DAY_OF_MONTH);
                tvOut.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
                break;
            case R.id.tv_hotel_room_dec:
                //入住房间减少
                if(count<=1){
                    return;
                }
                count--;
                /*LinearLayout.LayoutParams layout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout newl=new RelativeLayout(getApplicationContext());
                for(int i=1;i<count;){
                    {
                       LayoutInfo.addView(newl,R.id.layout_hotel_room_info);
                    }
                }*/
                setSum();
                break;
            case R.id.tv_hotel_room_add:
                //入住房间增加
                if(count>99){
                    return;
                }
                count++;
               /* LinearLayout.LayoutParams layout1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                RelativeLayout newl1=new RelativeLayout(getApplicationContext());
                for(int i=1;i<count;){
                    {
                        //LayoutInfo.addView(newl1,layout1);
                        //LayoutInfo.addView(newl1,R.id.layout_hotel_room_info);
                    }
                }*/
                setSum();
                break;
            case R.id.layout_hotel_room_info:
                //入住人信息
                break;
            case R.id.tv_hotel_room_submit:
                //入住提交
                Intent intent=new Intent(HotelRoomInActivity.this,HotelRoomSubmitActivity.class);
                startActivity(intent);
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
