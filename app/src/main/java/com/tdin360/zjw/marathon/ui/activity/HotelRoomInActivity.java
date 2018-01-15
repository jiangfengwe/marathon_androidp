package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.HotelOrderBean;
import com.tdin360.zjw.marathon.model.HotelOrderInfoBean;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
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
    @ViewInject(R.id.layout_hotel_room_info)
    private LinearLayout LayoutInfo;
    @ViewInject(R.id.et_hotel_order_phone)
    private EditText etPhone;
    private int count=1;
    private int countRoom=1;

    @ViewInject(R.id.tv_hotel_room_money)
    private TextView tvMoney;


    @ViewInject(R.id.layout_hotel_name)
    private LinearLayout layoutName;
    @ViewInject(R.id.layout_hotel_ic)
    private LinearLayout layoutIC;
    private List<EditText> name=new ArrayList<>();
    private List<EditText> ic=new ArrayList<>();
    private String str;
    /*@ViewInject(R.id.list_Lin)
    private LinearLayout listLayout;*/
    private static int id = 100;




    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    OptionsPickerView pvOptions;


    private String today,tomorrow;


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
        initDateLive();
        initToolbar();
        initView();

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
        String str="<font color='#ff621a'>入住说明：</font>觉得广播课题句话暖宫尽快帮您UR局UI人进步并不能杰克马门口v模具费没看见";
        tvIntro.setText(Html.fromHtml(str));
        tvIn.setOnClickListener(this);
        tvOut.setOnClickListener(this);
        tvDec.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        double price = getIntent().getDoubleExtra("hotelprice", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");


    }

    private void initToolbar() {
        addView();
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
    private void initDate(final TextView textView) {
        DatePickDialog dialog = new DatePickDialog(HotelRoomInActivity.this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        //dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                textView.setText(getTime(date));
                initDateLive();
            }
        });
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        DatePickDialog dialog = new DatePickDialog(HotelRoomInActivity.this);
        switch (v.getId()){
            case R.id.tv_hotel_room_in:
                //入住时间
               // initDate(tvIn);
                //DatePickDialog dialog = new DatePickDialog(HotelRoomInActivity.this);
                //设置上下年分限制
                dialog.setYearLimt(5);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                //dialog.setMessageFormat("yyyy-MM-dd HH:mm");
                //设置选择回调
                dialog.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        String time = getTime(date);
                        try {
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                            Date todayDate=formatter.parse(today);
                            Date timeDate=formatter.parse(time);
                            int i = (int) (timeDate.getTime()-todayDate.getTime()) / (1000 * 3600 * 24);
                            if(i>0){
                                today =time ;
                                tvEnter.setText(i+"晚");
                                tvIn.setText(getTime(date));
                                initDateLive();
                                Log.d("ddddd", "onCreate: "+i);
                            }else{
                                ToastUtils.showCenter(getApplicationContext(),"不能选择今天之前的日期");
                                tvIn.setText(today);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
                dialog.show();

                break;
            case R.id.tv_hotel_room_out:
                //initDate(tvOut);

                //设置上下年分限制
                dialog.setYearLimt(5);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                //dialog.setMessageFormat("yyyy-MM-dd HH:mm");
                //设置选择回调
                dialog.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        String time = getTime(date);
                        try {
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                            Date todayDate=formatter.parse(tomorrow);
                            Date timeDate=formatter.parse(time);
                            int i = (int) (timeDate.getTime()-todayDate.getTime()) / (1000 * 3600 * 24);
                            if(i>0){
                                tomorrow =time ;
                                tvEnter.setText(i+"晚");
                                tvOut.setText(getTime(date));
                                initDateLive();
                                Log.d("ddddd", "onCreate: "+i);
                            }else{
                                ToastUtils.showCenter(getApplicationContext(),"不能选择明天之前的日期");
                                tvOut.setText(tomorrow);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tomorrow = getTime(date);
                        tvOut.setText(tomorrow);
                        initDateLive();
                    }
                });
                dialog.show();
                break;
            case R.id.tv_hotel_room_dec:
                //入住房间减少
                if(count<=1){
                    return;
                }
                count=count-2;
                countRoom--;
                layoutName.removeAllViews();
                layoutIC.removeAllViews();
                for (int i = count; i < count; i--) {
                    addView();
                }
                setSum();
                break;
            case R.id.tv_hotel_room_add:
                //入住房间增加
               // layoutName.removeAllViews();
               // layoutIC.removeAllViews();
                layoutName.removeViewAt(count-1);
                layoutIC.removeViewAt(count-1);

                //layoutIC.removeViewAt(count-1);
                if(count>99){
                    return;
                }
                countRoom++;
                count=count+2;
                for (int i =0; i < count; i++) {
                    addView();
                }
                for (int i = 0; i < name.size(); i++) {
                    String string = name.get(i).getText().toString();
                    //ToastUtils.showCenter(getApplicationContext(),string);
                }
                //ToastUtils.showCenter(getApplicationContext(),name.size()+"");
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
                  /*  for (int j = 0; j <ic.size(); j++) {
                        if(i==j){
                            if(TextUtils.isEmpty(trimName)&&TextUtils.isEmpty(trimIc)){
                                ToastUtils.showCenter(getApplicationContext(),"姓名和身份证号不能为空");
                                return;
                            }
                            if(TextUtils.isEmpty(trimName)||TextUtils.isEmpty(trimIc)){
                                ToastUtils.showCenter(getApplicationContext(),"姓名或身份证号不能为空");
                                return;
                            }
                        }
                    }*/
                    tmpObj=new JSONObject();
                    String nameJson = name.get(i).getText().toString();
                    String icJson = ic.get(i).getText().toString();
                    if(TextUtils.isEmpty(nameJson)&&TextUtils.isEmpty(icJson)){
                        ToastUtils.showCenter(getApplicationContext(),"姓名和身份证号不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(nameJson)||TextUtils.isEmpty(icJson)){
                        ToastUtils.showCenter(getApplicationContext(),"姓名或身份证号不能为空");
                        return;
                    }
                    /*for (int j = 0; j <ic.size(); j++) {
                        if(i==j){
                            if(TextUtils.isEmpty(nameJson)&&TextUtils.isEmpty(icJson)){
                                ToastUtils.showCenter(getApplicationContext(),"姓名和身份证号不能为空");
                                return;
                            }
                            if(TextUtils.isEmpty(nameJson)||TextUtils.isEmpty(icJson)){
                                ToastUtils.showCenter(getApplicationContext(),"姓名或身份证号不能为空");
                                return;
                            }
                        }
                    }*/
                    tmpObj.put("userName",nameJson);
                    tmpObj.put("userDocument",icJson);
                    jsonArray.put(tmpObj);
                    tmpObj=null;
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

            layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            Log.d("222222222222", "initData: "+customerId);
            String string="{\"enterDate\":"+"\""+today+"\",\"leaveDate\":"+"\""+tomorrow+"\",\"roomNumber\":"+"\""+roomNumber+"\",\"userPhone\":"+"\""+phone+"\",\"hotelRoomId\":"+"\""+hotelRoomId+"\",\"customerId\":"+"\""+customerId+"\",\"appKey\":\"BJYDAppV-2\",\"userList\":"+userList+"}";
            Log.d("----------", "initData: "+string);
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.HOTEL_DETAIL_ORDER);
            params.addBodyParameter("secretMessage",enString);
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
        name.add(etName);
        layoutName.addView(etName);
        final EditText etIC = new EditText(this);
        ic.add(etIC);
        etIC.setHint("身份证");
        etIC.setTextSize(16);
        layoutIC.addView(etIC);
    }
    private void setSum() {
        //订单金额
        tvSum.setText(""+countRoom);
        double price = getIntent().getDoubleExtra("hotelprice", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");
    }
}
