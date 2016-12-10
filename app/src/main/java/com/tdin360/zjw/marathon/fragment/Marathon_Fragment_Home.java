package com.tdin360.zjw.marathon.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonHomeMyGridViewAdapter;
import com.tdin360.zjw.marathon.adapter.ViewPagerAdapter;
import com.tdin360.zjw.marathon.model.CarouselItem;
import com.tdin360.zjw.marathon.model.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.weight.MyGridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 马拉松首页
 * Created by Administrator on 2016/6/17.
 */
 @ContentView(R.layout.marathon_fragment_home)
public class Marathon_Fragment_Home extends BaseFragment implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.homeViewPager)
    private ViewPager homeViewPager;
    private ArrayList<View> list;
    private List<CarouselItem>carouselList;
    private List<CarouselItem>sponsorList;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;
    @ViewInject(R.id.main)
   private LinearLayout main;
    @ViewInject(R.id.loadingView)
    private LinearLayout loadingView;
    @ViewInject(R.id.loadingFail)
    private LinearLayout loadingFail;


    private int index;
    @ViewInject(R.id.myGridView)
    private MyGridView myGridView;

    public static Marathon_Fragment_Home newInstance(){
        Marathon_Fragment_Home fragment_marathon_home=new Marathon_Fragment_Home();
        return fragment_marathon_home;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        this.carouselList=new ArrayList<>();
        this.sponsorList=new ArrayList<>();
        this.homeViewPager.addOnPageChangeListener(this);
        httpGetList();
        /**
         * 加载失败点击重新加载
         */
        loadingFail.findViewById(R.id.loadFail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingFail.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                httpGetList();
            }
        });
    }


    //    赞助商列表
    private void hotRecommend() {

        MarathonHomeMyGridViewAdapter adapter = new MarathonHomeMyGridViewAdapter(sponsorList,getActivity());
        myGridView.setAdapter(adapter);
    }

    //轮播图
    private void viewPagerCarousel() {

        this.list=new ArrayList<>();
        for (int i=0;i<carouselList.size();i++){
            CarouselItem carouselItem = this.carouselList.get(i);
            ImageView imageView  =new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(220))//图片大小
                    .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.search_nodata)//加载失败后默认显示图片
                    .build();
             x.image().bind(imageView,carouselItem.getPicUrl(),imageOptions);
            list.add(imageView);


            //设置轮播图指示器
            if(carouselList.size()>1){
            RadioButton radioButton  =new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setButtonDrawable(R.drawable.carousel_checkbox_selector);
            RadioGroup.LayoutParams param  =new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(5,0,0,0);
            radioButton.setLayoutParams(param);
            radioButton.setEnabled(false);
            radioGroup.addView(radioButton);
            }
        }
        radioGroup.check(0);
        ViewPagerAdapter adapter  =new ViewPagerAdapter(list);
        homeViewPager.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(100,3000);
    }

    private Handler handler  =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

             if(msg.what==100){

                 if(index<list.size()-1){
                     index++;
                 }else {
                     index=0;
                 }
                 homeViewPager.setCurrentItem(index);
                 radioGroup.check(index);
                 handler.sendEmptyMessageDelayed(100,3000);
             }

        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
           index=position;
           radioGroup.check(position);
           handler.removeMessages(100);
           handler.sendEmptyMessageDelayed(100,3000);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /**
     * 请求网络数据
     */
    private void httpGetList(){

        /**
         * 清空旧数据
         */
        if(carouselList!=null) {
            carouselList.clear();
            sponsorList.clear();
        }
          x.http().get(new RequestParams(HttpUrlUtils.MARATHON_HOME),new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json  =new JSONObject(result);
                    /**
                     * 获取轮播图数据
                     */
                    JSONArray array1  =json.getJSONArray("MarathonPictureViwepagerModels");
                    for(int i=0;i<array1.length();i++){
                       JSONObject o = (JSONObject) array1.get(i);
                      carouselList.add(new CarouselItem(o.getString("PictureUrl"),o.getString("Url")));
                    }
                    /**
                     * 获取赞助商数据
                     */
                     JSONArray array2  =json.getJSONArray("MarathonPictureSponsorModels");
                    for(int i=0;i<array2.length();i++){
                       JSONObject o = (JSONObject) array2.get(i);
                       sponsorList.add(new CarouselItem(o.getString("PictureUrl"),o.getString("Url")));
                    }
                    main.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                   loadFail();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                        loadFail();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
                viewPagerCarousel();
                hotRecommend();
                loadingView.setVisibility(View.GONE);

            }
        });
    }

    /**
     * 加载失败
     */
    private void loadFail(){

         loadingView.setVisibility(View.GONE);
         loadingFail.setVisibility(View.VISIBLE);
    }

}
