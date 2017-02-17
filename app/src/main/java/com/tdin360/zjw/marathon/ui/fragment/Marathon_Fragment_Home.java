package com.tdin360.zjw.marathon.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonHomeMyGridViewAdapter;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.weight.Carousel;
import com.tdin360.zjw.marathon.weight.MyGridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
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
public class Marathon_Fragment_Home extends BaseFragment {

    private ArrayList<View> list;
    private List<CarouselModel>carouselList;
    private List<CarouselModel>sponsorList;
    @ViewInject(R.id.main)
   private LinearLayout main;
    @ViewInject(R.id.loadingView)
    private LinearLayout loadingView;
    @ViewInject(R.id.loadingFail)
    private LinearLayout loadingFail;
    @ViewInject(R.id.mCarousel)
    private Carousel mCarousel;
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
            CarouselModel carouselModel = this.carouselList.get(i);
            ImageView imageView  =new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    //.setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(220))//图片大小
                    .setLoadingDrawableId(R.drawable.carousel_default)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.search_nodata)//加载失败后默认显示图片
                    .build();
             x.image().bind(imageView, carouselModel.getPicUrl(),imageOptions);
            list.add(imageView);


        }

        this.mCarousel.loadCarousel(list);
        this.mCarousel.setPageBarBackgroundResource(R.color.CarouselPageBarBackgroundColor);

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
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_HOME);
        params.addQueryStringParameter("eventId", MarathonDataUtils.init().getEventId());
        x.http().get(params,new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json  =new JSONObject(result);


                    /**
                     * 获取轮播图数据
                     */
                    JSONArray array1  =json.getJSONArray("CarouselPictures");
                    for(int i=0;i<array1.length();i++){

                        JSONObject jsonObject = array1.getJSONObject(i);

                        String picUrl = jsonObject.getString("PictureUrl");



                        carouselList.add(new CarouselModel(picUrl,""));
                        carouselList.add(new CarouselModel(picUrl,""));
                    }
                    /**
                     * 获取赞助商数据
                     */



                    JSONArray array2  =json.getJSONArray("Sponsors");
                    for(int i=0;i<array2.length();i++){

                        JSONObject jsonObject = array2.getJSONObject(i);
                        String pictureUrl = jsonObject.getString("PictureUrl");


                        sponsorList.add(new CarouselModel(pictureUrl,""));
                        sponsorList.add(new CarouselModel(pictureUrl,""));
                        sponsorList.add(new CarouselModel(pictureUrl,""));
                        sponsorList.add(new CarouselModel(pictureUrl,""));

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
