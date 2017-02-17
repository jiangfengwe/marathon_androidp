package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.tdin360.zjw.marathon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的报名详情
 */
public class MySigUpDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("报名详情");
        showBackButton();
        initView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_sig_up_detail;
    }

    private void initView() {

        final List<View>pages = new ArrayList<>();
        //基本信息
        View view0 = View.inflate(this, R.layout.mysignup_detail_0, null);
//        联系方式
        View view1 = View.inflate(this, R.layout.mysignup_detail_1, null);
//        支付信息
        View view2 = View.inflate(this, R.layout.mysignup_detail_2, null);
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
     * 去支付
     * @param view
     */
    public void toPay(View view) {

        Intent intent = new Intent(this,PayActivity.class);
        startActivity(intent);

    }
}
