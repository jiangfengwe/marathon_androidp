package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.SignUpInfoModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;

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

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private View view0;
    private View view1;
    private View view2;
    private String orderNo;
    private String subject;
    private String money;
    private Button payBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("报名详情");
        showBackButton();
        initView();

        SignUpInfoModel model = (SignUpInfoModel) getIntent().getSerializableExtra("model");
        showData(model);

    }

    private void httpRequest(String id){


        RequestParams params = new RequestParams(HttpUrlUtils.MY_SIGN_UP_DETAILS);

        params.addQueryStringParameter("applyId",id);

        params.setConnectTimeout(5*1000);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d("----------->>>>", "onSuccess: "+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
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


//        设置用于去支付的参数

         this.orderNo = model.getOrderNum();
         this.subject="参赛报名费用";
         this.money=model.getPrice();

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

        //服装尺码
        TextView  clothesSize = (TextView) view0.findViewById(R.id.clothesSize);
        clothesSize.setText(model.getClothingSize());

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
         intent.putExtra("subject",subject);
         intent.putExtra("money",money);
        startActivity(intent);

    }
}
