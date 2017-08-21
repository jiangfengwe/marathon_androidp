package com.tdin360.zjw.marathon.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.HotelDetailsImageModel;
import com.tdin360.zjw.marathon.model.HotelItem;
import com.tdin360.zjw.marathon.model.HotelListModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HotelDetailsActivity extends BaseActivity {

    @ViewInject(R.id.mRecyclerView)
    private RecyclerView recyclerView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private int id;
    private String title;
    private  HotelListModel model;
    private RecyclerViewBaseAdapter adapter;
    private List<HotelItem>list=new ArrayList<>();
    private List<View>pics = new ArrayList<>();
    private List<String> picTitles=new ArrayList<>();
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showBackButton();


        Intent intent = getIntent();
        if(intent!=null){

            id = intent.getIntExtra("id",-1);
            title=intent.getStringExtra("title");
        }
        setToolBarTitle(title==null?"酒店详情":title);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(HotelDetailsActivity.this,list,R.layout.hotel_details_list_item);
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(R.layout.hotel_details_header);

        /**
         * 加载失败点击重试
         */
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                switch (mode){

                    case NOT_NETWORK:
                        httpRequest();
                        break;

                }
            }
        });

        httpRequest();


    }


    private void httpRequest(){

        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        RequestParams params = new RequestParams(HttpUrlUtils.HOTEL_DETAIL);
        params.addBodyParameter("hotelId",id+"");
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

//                Log.d("------------>", "onSuccess: "+result);


                try {
                    final ArrayList<String>images = new ArrayList<>();
                    JSONObject object = new JSONObject(result);
                    JSONObject obj = object.getJSONObject("EventHotelModel");

                     model = new Gson().fromJson(obj.toString(), HotelListModel.class);

                    final JSONArray pictures = obj.getJSONArray("EventHotelPictures");

                    for(int i=0;i<pictures.length();i++){

                        JSONObject jsonObject = pictures.getJSONObject(i);
                        HotelDetailsImageModel model = new Gson().fromJson(jsonObject.toString(), HotelDetailsImageModel.class);
                        images.add(model.getPictureUrl());
                        ImageView imageView = new ImageView(getBaseContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getBaseContext(),PhotoBrowseActivity.class);
                                intent.putStringArrayListExtra("list",images);
                                startActivity(intent);
                            }
                        });
                        x.image().bind(imageView,model.getPictureUrl());
                        pics.add(imageView);
                        picTitles.add(model.getName());
                    }



                    JSONArray hotelTypeList = object.getJSONArray("EventHotelTypeList");
                    for(int i=0;i<hotelTypeList.length();i++){

                        JSONObject jsonObject = hotelTypeList.getJSONObject(i);

                        HotelItem item = new Gson().fromJson(jsonObject.toString(), HotelItem.class);
                        list.add(item);
                    }


                    if(list.size()<=0){

                        mErrorView.show(recyclerView,"暂时没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(recyclerView);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    mErrorView.show(recyclerView,"服务器数据异常",ErrorView.ViewShowMode.ERROR);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                mErrorView.show(recyclerView,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(getBaseContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();
               adapter.update(list);

                if(galleryAdapter!=null){

                   galleryAdapter.update(pics);
                }

            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_detials;
    }


    class MyAdapter extends RecyclerViewBaseAdapter<HotelItem>{


        public MyAdapter(Context context, List<HotelItem> list, int layoutId) {
            super(context, list, layoutId);
        }


        @Override
        public void onBindHeaderViewHolder(final HeaderViewHolder holder) {


              ViewPager viewPager = (ViewPager) holder.getViewById(R.id.galleryView);
               galleryAdapter = new GalleryAdapter(pics);
               viewPager.setAdapter(galleryAdapter);
               holder.setText(R.id.textCountTip,"1/"+pics.size());
               viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                 @Override
                 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                 }

                 @Override
                 public void onPageSelected(int position) {

                     holder.setText(R.id.title,picTitles.get(position));
                     holder.setText(R.id.textCountTip,(position+1)+"/"+pics.size());

                 }

                 @Override
                 public void onPageScrollStateChanged(int state) {

                 }
             });

            if(model==null)return;
             holder.setText(R.id.hotel_name,model.getName());
             holder.setText(R.id.hotel_price,"¥"+model.getLowestPrice()+"起");
             holder.setText(R.id.hotel_Description,model.getDescription());
             String p2 = model.getPhone2()==null||model.getPhone2().equals("null")?"":","+model.getPhone2();
             holder.setText(R.id.hotel_tel,model.getPhone1()+p2);
             holder.setText(R.id.hotel_address,model.getAddress());

            }
        @Override
        protected void onBindNormalViewHolder(NormalViewHolder holder, final HotelItem model) {


            ImageView view = (ImageView) holder.getViewById(R.id.room_imageView);
            x.image().bind(view,model.getPictureUrl());
            holder.setText(R.id.room_name,model.getTitle());
              holder.setText(R.id.room_bad_size,model.getType());
              holder.setText(R.id.room_area,model.getArea()+"平米");
              holder.setText(R.id.room_price,model.getPrice()+"元");
             holder.getViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                    Intent intent = new Intent(HotelDetailsActivity.this,HotelOrderSubmitActivity.class);


                     intent.putExtra("id",model.getHotelId());
                     intent.putExtra("title",title+"-"+model.getTitle());
                     intent.putExtra("price",model.getPrice());
                     startActivity(intent);
                 }
             });
            holder.setText(R.id.room_intro,"");

        }
    }


    /**
     * 详情页顶部图片适配器
     */
    class GalleryAdapter extends PagerAdapter{


        private List<View>list;

        public GalleryAdapter(List<View>list){


            this.list=list;

        }
        public void update(List<View> list){

            this.list=list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list==null? 0:list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(list.get(position));

        }
    }
}
