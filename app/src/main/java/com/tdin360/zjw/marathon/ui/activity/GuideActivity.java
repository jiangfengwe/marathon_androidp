package com.tdin360.zjw.marathon.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tdin360.zjw.marathon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 *
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private ImageView btn;
    private List<ImageView>imageViews=new ArrayList<>();
    private int[] ids={R.drawable.guide0,R.drawable.guide1,R.drawable.guide2,R.drawable.guide3};
    private int count=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);


        this.mViewPager= (ViewPager) this.findViewById(R.id.guideViewPager);
        this.btn = (ImageView) this.findViewById(R.id.btn);
        this.mViewPager.addOnPageChangeListener(this);
        initGuideViews();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //记录用户已被引导过
               SharedPreferences share = GuideActivity.this.getSharedPreferences("GuideData",Activity.MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.putBoolean("isGuide",true);
                edit.commit();
            }
        });
    }

    //初始化引导页图片
    private void  initGuideViews() {

       for (int i=0;i<count;i++){

        ImageView imageView = new ImageView(this);imageView.setBackgroundColor(Color.BLUE);
        imageView.setImageResource(ids[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
         imageViews.add(imageView);
       }
        this.mViewPager.setAdapter(new GuideAdapter());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //最后一页可以点击按钮进入主页
        if(position==count-1){

           this.btn.setVisibility(View.VISIBLE);
        }else {

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
