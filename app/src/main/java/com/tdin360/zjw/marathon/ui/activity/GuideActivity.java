package com.tdin360.zjw.marathon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * @author  zhangzhijun
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private TextView btn;
    private List<ImageView>imageViews=new ArrayList<>();
    private int[] ids={R.drawable.guide0,R.drawable.guide1,R.drawable.guide2};
    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);


        this.mViewPager= (ViewPager) this.findViewById(R.id.guideViewPager);
        this.group = (RadioGroup) this.findViewById(R.id.radioGroup);
        this.btn = (TextView) this.findViewById(R.id.btn);
        this.mViewPager.addOnPageChangeListener(this);
        initGuideViews();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //记录用户已被引导过
                SharedPreferencesManager.saveGuideStatus(getApplicationContext());
            }
        });
    }

    //初始化引导页图片
    private void  initGuideViews() {

       for (int i=0;i<ids.length;i++){

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(ids[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
         imageViews.add(imageView);

         //添加指示器

           RadioButton radioButton = new RadioButton(this);
           radioButton.setId(i);
           radioButton.setButtonDrawable(R.drawable.guide_item_selector);
           radioButton.setPadding(10,10,10,10);
           group.addView(radioButton);
           if(i==0){
              radioButton.setChecked(true);
           }
       }

        this.mViewPager.setAdapter(new GuideAdapter());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        group.check(position);

        //最后一页可以点击按钮进入主页
        if(position==ids.length-1){

           this.btn.setVisibility(View.VISIBLE);
            //this.group.setVisibility(View.GONE);

        }else {
           // this.group.setVisibility(View.VISIBLE);
            this.btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /***
     *
     * 引导页图片适配器
     */
   private class GuideAdapter extends PagerAdapter{
        
    @Override
    public int getCount() {
        return imageViews.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = imageViews.get(position);
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        View view = imageViews.get(position);
        container.removeView(view);
    }
}
}
