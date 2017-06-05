package com.tdin360.zjw.marathon.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.SignUpInfoModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的报名详情
 */
public class MySigUpDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener{
    //用于接收返回状态的请求码
    public static int REQUEST_CODE=0x05;
    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private View view0;
    private View view1;
    private View view2;
    private Button payBtn;
    private String price;
    private String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("报名详情");
        showBackButton();
        initView();
        register();

        SignUpInfoModel model = (SignUpInfoModel) getIntent().getSerializableExtra("model");
        showData(model);
        if(model==null){//当报名成功直接支付成功后查看报名详情会进入该操作

            String orderNo = getIntent().getStringExtra("orderNo");
            httpRequest(orderNo);

        }

    }
    /**
     * 请求网络数据(支付成功后根据订单号更新支付结果)
     */
    private void httpRequest(String orderNo) {

        RequestParams params = new RequestParams(HttpUrlUtils.MY_SIGN_UP_DETAILS);
        params.addQueryStringParameter("orderNo",orderNo);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {



                try {
                    JSONObject json = new JSONObject(result);


                    JSONObject eventMobileMessage = json.getJSONObject("EventMobileMessage");

                    boolean success = eventMobileMessage.getBoolean("Success");
                    String reason = eventMobileMessage.getString("Reason");

                    if(success){

                        JSONObject object = json.getJSONObject("RegistratorMessageModel");


                            String id = object.getString("Id");
                            //赛事图片
                            String pictureUrl = object.getString("CuurentEventPictureUrl");


                            //赛事名称
                            String eventName = object.getString("EventName");

                            //真实姓名
                            String name = object.getString("RegistratorName");
//                             性别
                            boolean sex = object.getBoolean("RegistratorSex");
//                             生日
                            String birth = object.getString("Birthday");

//                             现居地址
                            String address = object.getString("RegistratorPlace");
//                             国家
                            String country = object.getString("Country");

//                             省份
                            String province = object.getString("Province");
//                             城市
                            String city = object.getString("City");
//                             地区
                            String county = object.getString("County");
//                             手机号码
                            String phone = object.getString("RegistratorPhone");

//                             邮箱
                            String email = object.getString("RegistratorEmail");

//                             证件号码
                            String number = object.getString("RegistratorDocumentNumber");

//                             证件类型
                            String type = object.getString("RegistratorDocumentType");

//                             服装尺码
                            String size = object.getString("RegistratorSize");

//                             参赛项目
                            String projectType = object.getString("RegistratorCompeteType");

//                             是否支付
                            boolean isPay = object.getBoolean("RegistratorIsPay");

//                              参赛号码
                            String documentNumber = object.getString("RegistratorDocumentNumber");

//                             邮政编码
                            String postCode = object.getString("RegisterPostCode");

//                             报名费用
                            String money = object.getString("Money");
//                             紧急联系人
                            String emergencyContactName = object.getString("EmergencyContactName");

//                             紧急联系电话
                            String emergencyContactPhone = object.getString("EmergencyContactPhone");

//                             报名时间
                            String createTime = object.getString("CreateTimeStr");

                            //订单号
                            String orderNo = object.getString("OrderNo");


                            SignUpInfoModel model = new SignUpInfoModel(pictureUrl, id, eventName, name, phone, email, birth, number, type, sex, country, province, city, county, projectType, size, address, postCode, emergencyContactName, emergencyContactPhone, documentNumber, isPay, createTime, orderNo, money);
                            showData(model);

                    }else {

                        Toast.makeText(MySigUpDetailActivity.this,reason,Toast.LENGTH_SHORT).show();

                        //没有查询到报名信息
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MySigUpDetailActivity.this, "网络错误或访问服务器出错!", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {



            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_my_sig_up_detail;
    }

    private void initView() {

        final List<View>pages = new ArrayList<>();


        //基本信息
        this.view0 = View.inflate(this, R.layout.mysignup_detail_0, null);



//        联系方式
        this.view1 = View.inflate(this, R.layout.mysignup_detail_1, null);



//        支付信息
         this.view2 = View.inflate(this, R.layout.mysignup_detail_2, null);
        this.payBtn= (Button) view2.findViewById(R.id.payBtn);
        pages.add(view0);
        pages.add(view1);
        pages.add(view2);
        this.radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
        this.viewPager = (ViewPager) this.findViewById(R.id.viewPager);
        this.viewPager.addOnPageChangeListener(this);
        this.radioGroup.setOnCheckedChangeListener(this);


        this.viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = pages.get(position);
                container.addView(view,0);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = pages.get(position);
                container.removeView(view);
            }
        });


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                radioGroup.check(R.id.radio1);
                break;
            case 1:
                radioGroup.check(R.id.radio2);
                break;
            case 2:
                radioGroup.check(R.id.radio3);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.radio1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.radio2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radio3:
                viewPager.setCurrentItem(2);
                break;
        }
    }


    /**
     * 显示报名详细信息
     * @param model
     */

    private void showData(SignUpInfoModel model){

        if(model==null){

            return;
        }


        /**
         * -------------------- 基本信息部分 -----------------
         */

        //姓名
        TextView nameTv = (TextView) view0.findViewById(R.id.name);

        nameTv.setText(model.getName());

//        性别
        TextView ganderTv = (TextView) view0.findViewById(R.id.gander);

        ganderTv.setText(model.getGender()==true?"男":"女");

        //生日
        TextView birthDayTv = (TextView) view0.findViewById(R.id.birthday);
        birthDayTv.setText(model.getBirthday());

        //证件类型
        TextView idNumType = (TextView) view0.findViewById(R.id.idCardType);

        idNumType.setText(model.getCertificateType());

        //证件号码
        TextView idNumTv  = (TextView) view0.findViewById(R.id.iDNumber);
        idNumTv.setText(model.getIdNumber());

        //赛事名称
        TextView eventNameTv = (TextView) view0.findViewById(R.id.eventName);
        eventNameTv.setText(model.getEventName());

        //参赛项目
        TextView  projectNameTv = (TextView) view0.findViewById(R.id.projectName);
        projectNameTv.setText(model.getAttendProject());
//        参赛号码
        TextView attendNumber = (TextView) view0.findViewById(R.id.attendNumber);
        attendNumber.setText((model.getAttendNumber().equals("null"))|| model.getAttendNumber().equals("") ? "暂未分配" :model.getAttendNumber());

        //服装尺码
        TextView  clothesSize = (TextView) view0.findViewById(R.id.clothesSize);
        clothesSize.setText(model.getClothingSize());

        //报名时间
        TextView date = (TextView) view0.findViewById(R.id.dateTime);
        date.setText(model.getCreateTime());

        /**
         *  -------------------- 联系方式部分 -----------------
         */

        //手机号
       TextView  telTv = (TextView) view1.findViewById(R.id.tel);

        telTv.setText(model.getPhone());


        //国家
       TextView  countryTv = (TextView) view1.findViewById(R.id.country);

        countryTv.setText(model.getNationality());

        //省份
        TextView provinceTv = (TextView) view1.findViewById(R.id.province);

        provinceTv.setText(model.getProvince());

        //城市
        TextView cityTv = (TextView) view1.findViewById(R.id.city);

        cityTv.setText(model.getCity());

        //区县
        TextView areaTv = (TextView) view1.findViewById(R.id.area);

        areaTv.setText(model.getCounty());

        //现居地址
       TextView  addressTv = (TextView) view1.findViewById(R.id.address);
        addressTv.setText(model.getAddress());


        //邮政编码s
        TextView postTv = (TextView) view1.findViewById(R.id.postCode);

        postTv.setText(model.getPostcode());

        //紧急联系人
        TextView linkNameTv = (TextView) view1.findViewById(R.id.linkName);
        linkNameTv.setText(model.getUrgencyLinkman());

        //紧急联系电话
       TextView linkPhoneTv = (TextView) view1.findViewById(R.id.linkPhone);

        linkPhoneTv.setText(model.getUrgencyLinkmanPhone());

        /**
         *  -------------------- 支付信息部分 -----------------
         */


        //订单号
        TextView  order = (TextView) view2.findViewById(R.id.orderNumber);

        this.orderNo = model.getOrderNum();
        this.price = model.getPrice();
        order.setText(model.getOrderNum());

        //支付金额
        TextView priceTv = (TextView) view2.findViewById(R.id.price);

        priceTv.setText("¥ "+model.getPrice());

        //支付状态
         TextView isPayTv = (TextView) view2.findViewById(R.id.isPay);

        isPayTv.setText(model.isPayed()==true?"已支付":"未支付");

         if(model.isPayed()){

             payBtn.setVisibility(View.GONE);
         }else {
             payBtn.setVisibility(View.VISIBLE);
         }

    }

    /**
     * 去支付
     * @param view
     */
    public void toPay(View view) {
        
         Intent intent = new Intent(this,PayActivity.class);
         intent.putExtra("order",orderNo);
         intent.putExtra("subject","报名费用");
         intent.putExtra("money",price);
         intent.putExtra("from","signUpDetail");
         startActivityForResult(intent,REQUEST_CODE);

    }

    private MyBroadcastReceiver receiver;

    private void register(){

        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter  =new IntentFilter(PayActivity.PAY_ACTION);
        registerReceiver(receiver,intentFilter);
    }
    /**
     * （当从详情页去支付成功时）进行数据更新
     */
    private class MyBroadcastReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent!=null){

                String orderNo = intent.getStringExtra("orderNo");
                httpRequest(orderNo);
            }



        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(receiver!=null){

            unregisterReceiver(receiver);
        }
    }
}
