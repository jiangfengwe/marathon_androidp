package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.PeopleClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.HotelOrderBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OrderTravelBean;
import com.tdin360.zjw.marathon.model.TravelDetailBean;
import com.tdin360.zjw.marathon.model.TravelOrderBean;
import com.tdin360.zjw.marathon.model.TravelOrderInfoBean;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 旅游立即预定
 */

public class TravelOrderActivity extends BaseActivity implements View.OnClickListener {
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
    //日期选择
    @ViewInject(R.id.tv_travel_choose_time)
    private TextView tvTime;
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> options2Itemss = new ArrayList<>();
    OptionsPickerView pvOptions;

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

    @ViewInject(R.id.tv_travel_order_money)
    private TextView tvMoney;

    @ViewInject(R.id.layout_name)
    private LinearLayout layoutName;
    @ViewInject(R.id.layout_ic)
    private LinearLayout layoutIC;
    private List<PeopleClass> name=new ArrayList<>();
    //private List<EditText> ic=new ArrayList<>();


    @ViewInject(R.id.layout_choose_time)
    private LinearLayout layoutTime;
    @ViewInject(R.id.tv_travel_choose_year)
    private TextView tvYear;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private String year;
    private String time;

    private  LinearLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-");
        Calendar c=Calendar.getInstance();
        //Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        year =formatter.format(c.getTime());
        //c.add(Calendar.DAY_OF_MONTH,1);
        tvYear.setText(year);
        initToolbar();
        initView();
        initDate();
    }
    private void initDate() {
        List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean> apiTravelMonthDateList =
                SingleClass.getInstance().getApiTravelMonthDateList();
        if(apiTravelMonthDateList.size()<=0){
            return;
        }
        for (int i = 0; i <apiTravelMonthDateList.size() ; i++) {
            TravelDetailBean.ModelBean.ApiTravelMonthDateListBean apiTravelMonthDateListBean = apiTravelMonthDateList.get(i);
            String month = apiTravelMonthDateListBean.getMonth();
            List<TravelDetailBean.ModelBean.ApiTravelMonthDateListBean.ApiTravelDayDateListBean> apiTravelDayDateList =
                    apiTravelMonthDateListBean.getApiTravelDayDateList();
            options1Items.add(month);
            for (int j = 0; j <apiTravelDayDateList.size() ; j++) {
                TravelDetailBean.ModelBean.ApiTravelMonthDateListBean.ApiTravelDayDateListBean apiTravelDayDateListBean = apiTravelDayDateList.get(j);
                String day = apiTravelDayDateListBean.getDay();
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
                tvTime.setText(tx);
                 time= tvTime.getText().toString().trim();
                Log.d("timeeee2222222222", "initData: "+time);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("日期选择")//标题
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
    private void initView() {
        tvDec.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        layoutTime.setOnClickListener(this);
        tvSubimit.setOnClickListener(this);
        double price = getIntent().getDoubleExtra("price", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");

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
        addView();
    }
    @Override
    public int getLayout() {
        return R.layout.activity_travel_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_travel_order_dec:
                //数量减少
                if(count<=1){
                    return;
                }
                count--;

                layoutName.removeViewAt(count);
                layoutIC.removeViewAt(count);
                name.remove(count);
                //ic.remove(count);
               /* for (int i = count; i < count; i--) {
                    addView();
                }*/
                setSum();

                //setContentView(layoutName);
                break;
            case R.id.tv_travel_order_add:
                //数量增加

               /* name.clear();
                ic.clear();*/
                if(count>99){
                    return;
                }
                count++;
                addView();
                /*layoutName.removeViewAt(count+1);
                layoutIC.removeViewAt(count+1);*/
               /* layoutName.removeAllViews();
                layoutIC.removeAllViews();*/
               /* name.remove(count-1);
                ic.remove(count-1);*/
                //layout.removeViewAt(count-1);
              /*  for (int i =0; i < count; i++) {
                    addView();
                }*/
                setSum();
                break;
            case R.id.layout_choose_time:
                //日期选择
                initDate();
                break;
            case R.id.tv_travel_choose_submit:
                //提交订单
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
     /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
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
            String travelId = getIntent().getStringExtra("travelId");
            byte[] mBytes=null;
            Log.d("222222222222", "initData: "+customerId);
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject=new JSONObject();
            JSONObject tmpObj =null;
            for (int i = 0; i < name.size(); i++) {
                try {
                    tmpObj=new JSONObject();

                    String nameEt = name.get(i).getNameEt().getText().toString().trim();
                    String icEt = name.get(i).getNameIc().getText().toString().trim();
                    if(TextUtils.isEmpty(nameEt)&&TextUtils.isEmpty(icEt)){
                        ToastUtils.showCenter(getApplicationContext(),"姓名和身份证号不能同时为空！");
                        return;
                    }
                    tmpObj.put("userName",nameEt);
                    tmpObj.put("userDocument",icEt);

                    jsonArray.put(tmpObj);
                    tmpObj=null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            String userList = jsonArray.toString();
            Log.d("wwwwname2", "initToolbar: "+userList);
            JSONObject personInfos = jsonObject.put("userList", userList);
            Log.d("wwwwname4", "initToolbar: "+personInfos.toString());

            final String phone = etPhone.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不能为空");
                return;
            }
            String travelNumber = tvSum.getText().toString().trim();
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
           /* layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();*/
            //显示提示框
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setDetailsLabel("请稍后")
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            String time = tvTime.getText().toString().trim();
            Log.d("timeeee", "initData: "+time);
            String string="{\"travelNumber\":"+"\""+travelNumber+"\",\"startDate\":"+"\""+year+time+"\",\"userPhone\":"+"\""+phone+"\",\"travelId\":"+"\""+travelId+"\",\"customerId\":"+"\""+customerId+"\",\"appKey\":\"BJYDAppV-2\",\"userList\":"+userList+"}";
            Log.d("----------", "initData: "+string);
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.TRAVEL_DETAIL_ORDER);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("travelorder", "onSuccess: "+result);
                    Gson gson=new Gson();
                    TravelOrderBean travelOrderBean = gson.fromJson(result, TravelOrderBean.class);
                    boolean state = travelOrderBean.isState();
                    if(state){
                       String orderSecretMessage = travelOrderBean.getOrderSecretMessage();
                        String decrypt = AES.decrypt(orderSecretMessage);
                        Log.d("travelorderdecrypt", "onSuccess: "+decrypt);
                        TravelOrderInfoBean travelOrderInfoBean = gson.fromJson(decrypt, TravelOrderInfoBean.class);
                        TravelOrderInfoBean.ModelBean model = travelOrderInfoBean.getModel();
                        TravelOrderInfoBean.ModelBean.BJTravelOrderModelBean bjTravelOrderModel = model.getBJTravelOrderModel();
                        SingleClass.getInstance().setBjTravelOrderModel(bjTravelOrderModel);
                        List<TravelOrderInfoBean.ModelBean.BJTravelStayInCustomerListModelBean> bjTravelStayInCustomerListModel = model.getBJTravelStayInCustomerListModel();
                        SingleClass.getInstance().setBjTravelStayInCustomerListModel(bjTravelStayInCustomerListModel);
                        Intent intent=new Intent(TravelOrderActivity.this,TravelOrderSubmitActivity.class);
                        intent.putExtra("orderId",bjTravelOrderModel.getId()+"");
                        startActivity(intent);
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),travelOrderBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    //mErrorView.show(tvSubimit,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                    ToastUtils.show(TravelOrderActivity.this,"网络不给力,连接服务器异常!");
                }

                @Override
                public void onCancelled(CancelledException cex) {}
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
        layout= new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final LinearLayout layout1 = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        final EditText etName = new EditText(this);
        etName.setHint("姓名");
        etName.setTextSize(16);
        //name.add(etName);
        layoutName.addView(etName);
        final EditText etIC = new EditText(this);
        //ic.add(etIC);
        etIC.setHint("身份证");
        etIC.setTextSize(16);
        name.add(new PeopleClass(etName,etIC));

        layoutIC.addView(etIC);
        layout.addView(layout1);

    }

    private void setSum() {
        //订单金额
        tvSum.setText(""+count);
        double price = getIntent().getDoubleExtra("price", 0.0);
        double money = count * price;
        Log.d("44", "initView: "+money);
        Log.d("442", "initView: "+count);
        tvMoney.setText(money+"");
        //int goodCount = Integer.parseInt(textViewCount.getText().toString());
        //priceSum = goodCount * price ;
        //String c=""+priceSum;
        //textViewSum.setText(c);
    }
}
