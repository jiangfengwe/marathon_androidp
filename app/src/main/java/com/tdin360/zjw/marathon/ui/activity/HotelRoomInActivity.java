package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.PeopleClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.AA;
import com.tdin360.zjw.marathon.model.HotelDetailBean;
import com.tdin360.zjw.marathon.model.HotelOrderBean;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.TravelDetailBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.luck.picture.lib.R.id.time;

/**
 * 酒店预定，入住信息填写
 */

public class HotelRoomInActivity extends BaseActivity implements View.OnClickListener {
    //
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

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
    @ViewInject(R.id.tv_hotel_people_add)
    private TextView tvAddPeople;
    @ViewInject(R.id.tv_hotel_people_dec)
    private TextView tvDecPeople;
    @ViewInject(R.id.tv_hotel_people_sum)
    private TextView tvSumPeople;
    @ViewInject(R.id.layout_hotel_room_info)
    private LinearLayout LayoutInfo;
    @ViewInject(R.id.et_hotel_order_phone)
    private EditText etPhone;
    private int count=1;
    private int countRoom=1;
    private int countPeople=1;
    @ViewInject(R.id.tv_hotel_room_money)
    private TextView tvMoney;

    //日期选择
  /*  @ViewInject(R.id.tv_travel_choose_time)
    private TextView tvTime;*/
   /* private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> options2Itemss = new ArrayList<>();
    OptionsPickerView pvOptions;*/


    @ViewInject(R.id.layout_hotel_name)
    private LinearLayout layoutName;
    @ViewInject(R.id.layout_hotel_ic)
    private LinearLayout layoutIC;


    @ViewInject(R.id.layout_hotel_room_info)
    private LinearLayout layoutList;
    private LinearLayout layoutInfo;
    private List<PeopleClass> name=new ArrayList<>();
    //private List<EditText> ic=new ArrayList<>();

    private String str;
    /*@ViewInject(R.id.list_Lin)
    private LinearLayout listLayout;*/
    private static int id = 100;

    private LinearLayout layout;




    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<String> options2Itemss = new ArrayList<>();
    OptionsPickerView pvOptions;
    int enterDate;
    int outDate;


    private String today,tomorrow,year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        //Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
         today =formatter.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH,1);
        tomorrow =formatter.format(c.getTime());
        tvIn.setText(today);
        tvOut.setText(tomorrow);

        SimpleDateFormat formatterYear=new SimpleDateFormat("yyyy");
        Calendar c1=Calendar.getInstance();
        //Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        year =formatterYear.format(c1.getTime());

        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String phone = loginInfo.getPhone();
        etPhone.setText(phone);
        initDateLive();
        initToolbar();
        initView();
        initDate();

    }
    private void initDate() {
        List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
        if(apiTravelMonthDateList.size()<=0){
            return;
        }
        options1Items.clear();
        options2Items.clear();
        options2Itemss.clear();
        for (int i = 0; i <apiTravelMonthDateList.size() ; i++) {
            AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(i);
            String month = apiHotelMonthDateListBean.getMonth()+"";
            List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
            options1Items.add(month);
            for (int j = 0; j <apiHotelDayDateList.size() ; j++) {
                AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(j);
                String day = apiHotelDayDateListBean.getDay();
                options2Itemss.add(day);
                options2Items.add(options2Itemss);
            }
        }

        //条件选择器
        pvOptions= new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)+"-"
                        + options2Items.get(options1).get(option2);
                // + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                tvIn.setText(year+"-"+tx);
                today = year + "-" + tx;
                List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
                AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(options1);
                List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(option2);
                String day = apiHotelDayDateListBean.getDay();
                enterDate= Integer.parseInt(day);
                //time= tvTime.getText().toString().trim();
                Log.d("timeeee2222222222", "initData: "+time);
                List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList1 = SingleClass.getInstance().getApiHotelMonthDateList1();
                if(apiTravelMonthDateList1.size()<=0){
                    return;
                }
                options1Items.clear();
                options2Items.clear();
                options2Itemss.clear();
                for (int i = 0; i <apiTravelMonthDateList1.size() ; i++) {
                    AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean1 = apiTravelMonthDateList1.get(i);
                    String month = apiHotelMonthDateListBean.getMonth()+"";
                    List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList1 = apiHotelMonthDateListBean.getApiHotelDayDateList();
                    options1Items.add(month);
                    for (int j = 0; j <apiHotelDayDateList.size() ; j++) {
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean1 = apiHotelDayDateList.get(j);
                        String day1 = apiHotelDayDateListBean.getDay();
                        options2Itemss.add(day1);
                        options2Items.add(options2Itemss);
                    }
                }
                //条件选择器
                pvOptions= new  OptionsPickerView.Builder(HotelRoomInActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)+"-"
                                + options2Items.get(options1).get(option2);

                        List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
                        AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(options1);
                        List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(option2);
                        String day = apiHotelDayDateListBean.getDay();
                        outDate= Integer.parseInt(day);
                        int i = outDate - enterDate;
                        if(i>0){
                            tomorrow = year + "-" + tx;
                            tvOut.setText(year+"-"+tx);

                            initDateLive();
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),"入住时间最短为一天");
                        }
                        return;
                        //Log.d("timeeee2222222222", "initData: "+time);

                    }
                })
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("离店日期")//标题
                        .setSubCalSize(18)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleColor(Color.parseColor("#ff621a"))//标题文字颜色
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
                        .setLinkage(false)//设置是否联动，默认true
                        .setLabels("月", "日", "区")//设置选择的三级单位
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                        .setOutSideCancelable(false)//点击外部dismiss default true
                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvOptions.setPicker(options1Items,options2Items);
                pvOptions.show();
               /* boolean showing = pvOptions.isShowing();
                if(!showing){
                    initDate();
                }*/

            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("入住日期")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.parseColor("#ff621a"))//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(false)//设置是否联动，默认true
                .setLabels("月", "日", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvOptions.setPicker(options1Items,options2Items);
        pvOptions.show();

    }
    private void initDateLive() {
        try {
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            Date todayDate=formatter.parse(today);
            Date tomorrowDate = formatter.parse(tomorrow);
            int i = (int) (tomorrowDate.getTime()-todayDate.getTime()) / (1000 * 3600 * 24);
            tvEnter.setText(i+"晚");
            Log.d("ddddd", "onCreate: "+i);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        /* AA.ModelBean.BJHotelRoomListModelBean bjHotelRoomListModelBean = SingleClass.getInstance().getBjHotelRoomListModelBean();
        String instructions = bjHotelRoomListModelBean.getInstructions();
        if(TextUtils.isEmpty(instructions)){
            String str="<font color='#ff621a'>入住说明：</font>暂无说明";
            tvIntro.setText(Html.fromHtml(str));
        }else{
            String str="<font color='#ff621a'>入住说明：</font>"+bjHotelRoomListModelBean.getInstructions();
            tvIntro.setText(Html.fromHtml(str));
        }*/
        String str="<font color='#ff621a'>入住说明：</font>暂无说明";
        tvIntro.setText(Html.fromHtml(str));

        tvIn.setOnClickListener(this);
        tvOut.setOnClickListener(this);
        tvDec.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvAddPeople.setOnClickListener(this);
        tvDecPeople.setOnClickListener(this);

        double price = getIntent().getDoubleExtra("hotelprice", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");
    }

    private void initToolbar() {
        addLayout();
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
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(date);
        return format1;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_hotel_room_in:
                //入住时间
                //initDate();
                List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
                if(apiTravelMonthDateList.size()<=0){
                    return;
                }
                options1Items.clear();
                options2Items.clear();
                options2Itemss.clear();
                for (int i = 0; i <apiTravelMonthDateList.size() ; i++) {
                    AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(i);
                    String month = apiHotelMonthDateListBean.getMonth()+"";
                    List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                    options1Items.add(month);
                    for (int j = 0; j <apiHotelDayDateList.size() ; j++) {
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(j);
                        String day = apiHotelDayDateListBean.getDay();
                        options2Itemss.add(day);
                        options2Items.add(options2Itemss);
                    }
                }

                //条件选择器
                pvOptions= new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)+"-"
                                + options2Items.get(options1).get(option2);
                        // + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        tvIn.setText(year+"-"+tx);
                        //time= tvTime.getText().toString().trim();
                        today = year + "-" + tx;
                        Log.d("timeeee2222222222", "initData: "+time);
                        List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
                        AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(options1);
                        List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(option2);
                        String day = apiHotelDayDateListBean.getDay();
                        enterDate= Integer.parseInt(day);

                    }
                })
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("入住日期")//标题
                        .setSubCalSize(18)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleColor(Color.parseColor("#ff621a"))//标题文字颜色
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
                        .setLinkage(false)//设置是否联动，默认true
                        .setLabels("月", "日", "区")//设置选择的三级单位
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                        .setOutSideCancelable(false)//点击外部dismiss default true
                        .isDialog(true)//是否显示为对话框样式
                        .build();

                pvOptions.setPicker(options1Items,options2Items);
                pvOptions.show();
                break;
            case R.id.tv_hotel_room_out:
                List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList1 = SingleClass.getInstance().getApiHotelMonthDateList1();
                if(apiTravelMonthDateList1.size()<=0){
                    return;
                }
                options1Items.clear();
                options2Items.clear();
                options2Itemss.clear();
                for (int i = 0; i <apiTravelMonthDateList1.size() ; i++) {
                    AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList1.get(i);
                    String month = apiHotelMonthDateListBean.getMonth()+"";
                    List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                    options1Items.add(month);
                    for (int j = 0; j <apiHotelDayDateList.size() ; j++) {
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(j);
                        String day = apiHotelDayDateListBean.getDay();
                        Log.d("timeeee2222222222day", "initData: "+day);
                        options2Itemss.add(day);
                        options2Items.add(options2Itemss);
                    }
                }
                //条件选择器
                pvOptions= new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)+"-"
                                + options2Items.get(options1).get(option2);
                        List<AA.ModelBean.ApiHotelMonthDateListBean> apiTravelMonthDateList = SingleClass.getInstance().getApiHotelMonthDateList1();
                        AA.ModelBean.ApiHotelMonthDateListBean apiHotelMonthDateListBean = apiTravelMonthDateList.get(options1);
                        List<AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean> apiHotelDayDateList = apiHotelMonthDateListBean.getApiHotelDayDateList();
                        AA.ModelBean.ApiHotelMonthDateListBean.ApiHotelDayDateListBean apiHotelDayDateListBean = apiHotelDayDateList.get(option2);
                        String day = apiHotelDayDateListBean.getDay();
                        outDate= Integer.parseInt(day);
                        int i = outDate - enterDate;
                        if(i>0){
                            tomorrow = year + "-" + tx;
                            tvOut.setText(year+"-"+tx);
                            initDateLive();
                        }else{
                            ToastUtils.showCenter(getApplicationContext(),"入住时间最短为一天");
                        }
                        Log.d("timeeee2222222222dayi", "initData: "+i);
                    }
                })
                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("离店日期")//标题
                        .setSubCalSize(18)//确定和取消文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleColor(Color.parseColor("#ff621a"))//标题文字颜色
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                        .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                        .setContentTextSize(18)//滚轮文字大小
                        .setLinkage(false)//设置是否联动，默认true
                        .setLabels("月", "日", "区")//设置选择的三级单位
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(1, 1, 1)  //设置默认选中项
                        .setOutSideCancelable(false)//点击外部dismiss default true
                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvOptions.setPicker(options1Items,options2Items);
                pvOptions.show();
                break;
            case R.id.tv_hotel_room_dec:
                //入住房间减少
                if(countRoom<=1){
                    return;
                }
                count--;
                countRoom--;
                layoutName.removeViewAt(count);
                layoutIC.removeViewAt(count);
                layoutName.removeViewAt(count);
                layoutIC.removeViewAt(count);
                //layout.removeViewAt(count);
                name.remove(count);
                name.remove(count);
                setSum();
                break;
            case R.id.tv_hotel_room_add:
                //入住房间增加
                if(countRoom>99){
                    return;
                }
                countRoom++;
                count++;
                addLayout();
                setSum();
                break;
            case R.id.tv_hotel_people_dec:
                //入住人数减少
                if(countPeople<=1){
                    return;
                }
                countPeople--;
                setSum();
                break;
            case R.id.tv_hotel_people_add:
                //入住人数增加
                if(countPeople>99){
                    return;
                }
                countPeople++;
                setSum();
                break;
            case R.id.layout_hotel_room_info:
                //入住人信息
                break;
            case R.id.tv_hotel_room_submit:
                //入住提交
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initData();
                }else {
                    layoutLoading.setVisibility(View.GONE);
                    //如果缓存数据不存在则需要用户打开网络设置
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("网络不可用，是否打开网络设置");
                    alert.setCancelable(false);
                    alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //打开网络设置

                            startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

                        }
                    });
                    alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    alert.show();

                }

                break;
        }

    }
   /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {

    /** 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    private void initData() {
        try{
            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            String customerId = loginInfo.getId();
            String hotelRoomId = getIntent().getStringExtra("hotelRoomId");
            byte[] mBytes=null;
            final String phone = etPhone.getText().toString().trim();
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            JSONObject tmpObj =null;
            for (int i = 0; i < name.size(); i++) {
                try {
                    tmpObj=new JSONObject();
                    String nameJson = name.get(i).getNameEt().getText().toString();
                    String icJson = name.get(i).getNameIc().getText().toString();
                    for (int j = 0; j < name.size(); j++) {
                        Log.d("rrrrrrrrr", "initData: "+name.size());
                        if((j)%2==0){
                            String stringName = name.get(j).getNameEt().toString();
                            String stringIc = name.get(j).getNameIc().toString();
                            if(TextUtils.isEmpty(stringName)&&TextUtils.isEmpty(stringIc)){
                                ToastUtils.showCenter(getApplicationContext(),"姓名和身份证号不能同时为空！");
                                return;
                            }
                        }
                    }
                    if(!TextUtils.isEmpty(nameJson)&&!TextUtils.isEmpty(icJson)){
                        tmpObj.put("userName",nameJson);
                        tmpObj.put("userDocument",icJson);
                        jsonArray.put(tmpObj);
                        tmpObj=null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不能为空");
                return;
            }
            String roomNumber = tvSum.getText().toString().trim();
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            String userList = jsonArray.toString();
            Log.d("wwwwname2", "initToolbar: "+userList);

            /*layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();*/
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            Log.d("222222222222", "initData: "+customerId);
            //String string="{\"enterDate\":"+"\""+today+"\",\"leaveDate\":"+"\""+tomorrow+"\",\"roomNumber\":"+"\""+roomNumber+"\",\"userPhone\":"+"\""+phone+"\",\"hotelRoomId\":"+"\""+hotelRoomId+"\",\"customerId\":"+"\""+customerId+"\",\"appKey\":\"BJYDAppV-2\",\"userList\":"+userList+"}";
            String string="{\"enterDate\":"+"\""+today+"\",\"leaveDate\":"+"\""+tomorrow+"\",\"roomNumber\":"+"\""+roomNumber+"\",\"peopleNumber\":"+"\""+countPeople+"\",\"userName\":"+"\""+"ee"+"\",\"userPhone\":"+"\""+phone+"\",\"hotelRoomId\":"+"\""+hotelRoomId+"\",\"customerId\":"+"\""+customerId+"\",\"appKey\":\"BJYDAppV-2\""+"}";
            Log.d("----------", "initData: "+string);

            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_DETAIL_ORDER);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("hotelorder", "onSuccess: "+result);
                    Gson gson=new Gson();
                    HotelOrderBean hotelOrderBean = gson.fromJson(result, HotelOrderBean.class);
                    boolean state = hotelOrderBean.isState();
                    if(state){
                        String orderSecretMessage = hotelOrderBean.getOrderSecretMessage();
                        String decrypt = AES.decrypt(orderSecretMessage);
                        Log.d("order", "onSuccess: "+decrypt);
                        HotelOrderInfoBean hotelOrderInfoBean = gson.fromJson(decrypt, HotelOrderInfoBean.class);
                        HotelOrderInfoBean.ModelBean model = hotelOrderInfoBean.getModel();
                        HotelOrderInfoBean.ModelBean.BJHotelOrderModelBean bjHotelOrderModel = model.getBJHotelOrderModel();
                        SingleClass.getInstance().setBjHotelOrderModel(bjHotelOrderModel);
                        List<HotelOrderInfoBean.ModelBean.BJHotelStayInCustomerListModelBean> bjHotelStayInCustomerListModel = model.getBJHotelStayInCustomerListModel();
                        SingleClass.getInstance().setBjHotelStayInCustomerListModel(bjHotelStayInCustomerListModel);
                        Intent intent=new Intent(HotelRoomInActivity.this,HotelRoomSubmitActivity.class);
                        intent.putExtra("orderId",bjHotelOrderModel.getId()+"");
                        startActivity(intent);
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),hotelOrderBean.getMessage());
                    }

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
                    //Log.d("error", "initData: ");

                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d("onCancelled", "initData: "+cex.getMessage());

                }

                @Override
                public void onFinished() {
                    Log.d("onFinished", "initData: ");
                    hud.dismiss();
                    //layoutLoading.setVisibility(View.GONE);
                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }
    }
    private void addView() {
        final EditText etName = new EditText(this);
        etName.setHint("姓名");
        etName.setTextSize(16);
        //name.add(etName);
        layoutName.addView(etName);
        final EditText etIC = new EditText(this);
        //ic.add(etIC);
        etIC.setHint("身份证");
        etIC.setTextSize(16);
        layoutIC.addView(etIC);
    }
    private void addLayout(){
        layout= new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);


        final EditText etName = new EditText(this);
        etName.setHint("姓名");
        etName.setTextSize(16);
       // name.add(etName);
        layoutName.addView(etName);
        final EditText etIC = new EditText(this);
       // ic.add(etIC);
        etIC.setHint("身份证"+count);
        etIC.setTextSize(16);
        layoutIC.addView(etIC);
        name.add(new PeopleClass(etName,etIC));

        final LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        final EditText etName2 = new EditText(this);
        etName2.setHint("姓名");
        etName2.setTextSize(16);
        //name.add(etName2);
        layoutName.addView(etName2);
        final EditText etIC2 = new EditText(this);
       // ic.add(etIC2);
        etIC2.setHint("身份证"+count);
        etIC2.setTextSize(16);
        layoutIC.addView(etIC2);

        name.add(new PeopleClass(etName2,etIC2));


        layout.addView(layout1);
        layout.addView(layout2);
       /* layoutInfo=new LinearLayout(this);
        layoutInfo.setOrientation(LinearLayout.VERTICAL);
        LayoutInfo.addView(layout);*/
        //layout.addView(layout1);
    }
    private void setSum() {
        //订单金额
        tvSum.setText(""+countRoom);
        tvSumPeople.setText(""+countPeople);
        double price = getIntent().getDoubleExtra("hotelprice", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");
    }
}
