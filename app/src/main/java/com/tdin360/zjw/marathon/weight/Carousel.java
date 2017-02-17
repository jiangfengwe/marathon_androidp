package com.tdin360.zjw.marathon.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tdin360.zjw.marathon.R;

import java.util.List;

/**
 * 自定义轮播图
 * Created by admin on 17/1/15.
 */

public class Carousel extends RelativeLayout implements ViewPager.OnPageChangeListener{

    /**
     * handler消息标识
     */
    private static final int WHAT=1;
    /**
     * 默认轮播图切换时间
     */
    private int loopTime=3000;
    private ViewPager viewPager;
    /**
     * 轮播图指示器
     */
    private LinearLayout page;
    /**
     * 轮播图容器
     */
    private List<View>views;
    private Context context;
    /**
     * 记录上一个轮播图的索引
     */
    private int prePosition;
    /**
     * 当前轮播图的索引
     */
    private int currentPosition;
    /**
     *     底部指示器背景颜色
     */

    private int pageBarColor=android.R.color.transparent;

    public Carousel(Context context) {
        super(context);
        initView(context);
    }

    public Carousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }



    /**
     * 初始化控件
     * @param context
     */
    private void initView(Context context) {
        this.context=context;
        //初始化viewPager
        this.viewPager = new ViewPager(context);
        this.page  =new LinearLayout(context);
        this.viewPager.addOnPageChangeListener(this);
        this.addView(viewPager);
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout main = new LinearLayout(context);
        main.setLayoutParams(mainParams);
        main.setGravity(Gravity.BOTTOM);
        //初始化指示器
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
        this.page.setPadding(10,10,10,10);
        this.page.setLayoutParams(params);
        main.addView(page);
        this.addView(main);
    }

    /**
     * 设置底部指示器背景颜色
     * @param color 颜色
     */
    public void setPageBarBackground(int color){

        this.page.setBackgroundColor(color);

    }
    /**
     * 设置底部指示器背景颜色
     * @param resourceId 来自资源文件颜色
     */
    public void setPageBarBackgroundResource(int resourceId){


        this.page.setBackgroundResource(resourceId);

    }
    /**
     * 设置指示器位置
     * @param gravity
     */
    private void setPageBarPosition(int gravity){
        this.page.setGravity(gravity);
    }
    /**
     * 默认轮播方式
     * @param views
     */
    public void loadCarousel(final List<View>views){
        this.views=views;

        if(views.size()>1) {
            for (int i = 0; i < views.size(); i++) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
                ImageView item = new ImageView(context);
                params.leftMargin = 5;
                item.setLayoutParams(params);
                item.setBackgroundResource(R.drawable.carousel_checkbox_selector);
                if (i == 0) {

                    item.setEnabled(false);
                } else {
                    item.setEnabled(true);
                }
                page.addView(item);
                setPageBarPosition(Gravity.CENTER);

            }
            this.viewPager.setAdapter(new CarouselAdapter());

            this.viewPager.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (MotionEvent.ACTION_DOWN == event.getAction()) {

                        handler.removeMessages(WHAT);
                    } else if (MotionEvent.ACTION_UP == event.getAction()) {

                        handler.sendEmptyMessageDelayed(WHAT, loopTime);
                    }
                    return false;
                }
            });
            handler.sendEmptyMessageDelayed(WHAT, loopTime);
        }
    }

    /**
     *
     * @param views 轮播图片容器
     * @param loopTime 轮播时间(单位毫秒)
     */
    public void loadCarousel(final List<View>views,final int loopTime){
        this.views=views;
        this.loopTime=loopTime;
        for(int i=0;i<views.size();i++){

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            ImageView item = new ImageView(context);
            params.leftMargin=5;
            item.setLayoutParams(params);
            item.setBackgroundResource(R.drawable.carousel_checkbox_selector);
            if(i==0){

                item.setEnabled(false);
            }else {
                item.setEnabled(true);
            }
            page.addView(item);
             setPageBarPosition(Gravity.CENTER);
        }
        this.viewPager.setAdapter(new CarouselAdapter());

        this.viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MotionEvent.ACTION_DOWN==event.getAction()){

                    handler.removeMessages(WHAT);
                }else if(MotionEvent.ACTION_UP==event.getAction()){

                    handler.sendEmptyMessageDelayed(WHAT,loopTime);
                }
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(WHAT,loopTime);
    }

    /**
     *
     * @param views 轮播图容器
     * @param loopTime 轮播时间（单位毫秒）
     * @param gravity 设置指示器位置
     */
    public void loadCarousel(final List<View>views,final int loopTime,int gravity){
        this.views=views;
        this.loopTime=loopTime;
        for(int i=0;i<views.size();i++){

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            ImageView item = new ImageView(context);
            params.leftMargin=5;
            item.setLayoutParams(params);
            item.setBackgroundResource(R.drawable.carousel_checkbox_selector);
            if(i==0){

                item.setEnabled(false);
            }else {
                item.setEnabled(true);
            }
            page.addView(item);
            setPageBarPosition(gravity);
        }
        this.viewPager.setAdapter(new CarouselAdapter());

        this.viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MotionEvent.ACTION_DOWN==event.getAction()){

                    handler.removeMessages(WHAT);
                }else if(MotionEvent.ACTION_UP==event.getAction()){

                    handler.sendEmptyMessageDelayed(WHAT,loopTime);
                }
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(WHAT,loopTime);
    }

    /**
     * 控制自动轮播
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(views.size()==0){
                handler.removeMessages(WHAT);
                return;
            }
            handler.sendEmptyMessageDelayed(WHAT,loopTime);
            currentPosition++;
            viewPager.setCurrentItem(currentPosition,true);
        }
    };


    /**
     * 轮播图适配器
     */
    private class CarouselAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return views==null?0:Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position%=views.size();
            View view = views.get(position);
            ViewParent parent = view.getParent();

            if(parent!=null){
                ViewGroup vg = (ViewGroup) parent;
                vg.removeView(view);
            }

            container.addView(view,0);
            return  view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

              int p = position%views.size();
             page.getChildAt(prePosition%views.size()).setEnabled(true);
             page.getChildAt(p).setEnabled(false);
             this.prePosition= position;
             currentPosition= position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
