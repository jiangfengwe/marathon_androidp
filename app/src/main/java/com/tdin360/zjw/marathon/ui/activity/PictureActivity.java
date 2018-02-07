package com.tdin360.zjw.marathon.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.HotelDetailBean;
import com.tdin360.zjw.marathon.model.TravelDetailBean;
import com.tdin360.zjw.marathon.model.TravelPictureBean;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 图集
 */

public class PictureActivity extends BaseActivity {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.rv_picture)
    private RecyclerView rvPiv;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    ImageOptions imageOptions;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                //.setCrop(true).setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        initToolbar();
        initView();

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
        titleTv.setText("图集");
    }

    private void initView() {

        //List<TravelDetailBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel = SingleClass.getInstance().getBjTravelPictureListModel();
        String picture = getIntent().getStringExtra("picture");
        final ArrayList<String> image= new ArrayList<>();
        if(picture.equals("hotelPic")){
            List<HotelDetailBean.ModelBean.BJHotelPictureListModelBean> bjHotelPictureListModel = SingleClass.getInstance().getBjHotelPictureListModel();
            if(bjHotelPictureListModel.size()<=0){
                mErrorView.show(rvPiv,"暂时没有数据", ErrorView.ViewShowMode.NOT_DATA);
                return;
            }else{
                mErrorView.hideErrorView(rvPiv);
            adapter=new RecyclerViewBaseAdapter<HotelDetailBean.ModelBean.BJHotelPictureListModelBean>(getApplicationContext(),
                    bjHotelPictureListModel,R.layout.item_picture) {
                @Override
                protected void onBindNormalViewHolder(NormalViewHolder holder, HotelDetailBean.ModelBean.BJHotelPictureListModelBean model) {
                    ImageView ivHotel = (ImageView) holder.getViewById(R.id.iv_hotel_detail_pic);
                    x.image().bind(ivHotel,model.getPictureUrl(),imageOptions);
                }
            };
            for(int i=0;i<bjHotelPictureListModel.size();i++){
                image.add(bjHotelPictureListModel.get(i).getPictureUrl());
            }
            }
        }
        if(picture.equals("travelPic")){
            List<TravelPictureBean.ModelBean.BJTravelPictureListModelBean> bjTravelPictureListModel = SingleClass.getInstance().getBjTravelPictureListModel();
            if(bjTravelPictureListModel.size()<=0){
                mErrorView.show(rvPiv,"暂时没有数据", ErrorView.ViewShowMode.NOT_DATA);
                return;
            }else{
                mErrorView.hideErrorView(rvPiv);

            adapter=new RecyclerViewBaseAdapter<TravelPictureBean.ModelBean.BJTravelPictureListModelBean>(getApplicationContext(),
                    bjTravelPictureListModel,R.layout.item_picture) {
                @Override
                protected void onBindNormalViewHolder(NormalViewHolder holder, TravelPictureBean.ModelBean.BJTravelPictureListModelBean model) {
                    ImageView ivHotel = (ImageView) holder.getViewById(R.id.iv_hotel_detail_pic);
                    x.image().bind(ivHotel,model.getPictureUrl(),imageOptions);

                }
            };
            for(int i=0;i<bjTravelPictureListModel.size();i++){
                image.add(bjTravelPictureListModel.get(i).getPictureUrl());
            }
            }
        }
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView1=new ImageView(PictureActivity.this);
                //Log.d("pictureList.size()", "onEvent: "+pictureList.size());
                MNImageBrowser.showImageBrowser(PictureActivity.this,imageView1,position, image);
            }
        });
        rvPiv.setAdapter(adapter);
        rvPiv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_picture;
    }
}
