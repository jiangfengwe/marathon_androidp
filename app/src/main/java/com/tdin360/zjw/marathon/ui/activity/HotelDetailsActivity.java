package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;


/**
 * 酒店详情
 */

public class HotelDetailsActivity extends BaseActivity implements View.OnClickListener{

   /* @ViewInject(R.id.mRecyclerView)
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
    private GalleryAdapter galleryAdapter;*/


    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.rv_hotel_detail)
    private RecyclerView recyclerView;
    private List<String> list=new ArrayList<>();
    private RecyclerViewBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initRecyclerView();
/*
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

        *//**
         * 加载失败点击重试
         *//*
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

        httpRequest();*/


    }

    private void initRecyclerView() {
        for (int i = 0; i <6 ; i++) {
            list.add(""+i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
       // recyclerView.setAdapter(new HotelDetalAdapter());
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_hotel_detail_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                TextView tvOrder = (TextView) holder.getViewById(R.id.tv_room_order);
                tvOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(HotelDetailsActivity.this,HotelRoomInActivity.class);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onBindHeaderViewHolder(HeaderViewHolder holder) {
                super.onBindHeaderViewHolder(holder);
            }

            @Override
            public void onBindFooterViewHolder(FooterViewHolder holder) {
                super.onBindFooterViewHolder(holder);
                RecyclerView rvDetail = (RecyclerView) holder.getViewById(R.id.rv_hotel_detail_foot);
                TextView tvMore = (TextView) holder.getViewById(R.id.tv_check_more_comment);
                rvDetail.setAdapter(new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_hotel_detail_pic) {
                    @Override
                    protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

                    }
                });
                rvDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                //查看更多评价
                tvMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(HotelDetailsActivity.this,HotelMoreCommentActivity.class);
                        startActivity(intent);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.addHeaderView(R.layout.item_hotel_detail_head);
        adapter.addFooterView(R.layout.item_hotel_detail_foot);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(HotelDetailsActivity.this,HotelRoomActivity.class);
                startActivity(intent);
            }
        });

        
    }
   /* class HotelDetalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final int HEAD=0x01;
        private final int CustomerUpload = 0X02;
        private final int foot = 0X03;
        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return HEAD;
            }
            if(position==1){

                return CustomerUpload;
            }
            if(position==2){

                return foot;
            }

            return super.getItemViewType(position);
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType== HEAD){
                return new HeaderViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_hotel_detail_head,parent,false));
            }
           *//* if(viewType== CustomerEvaluation){
                return new CustomerEvaluationViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_groupbuyingactivity_customer_evaluation,parent,false));
            }*//*
            if(viewType== CustomerUpload){
                return new CustomUploadViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_hotel_detail_rv,parent,false));
            }
            if(viewType== foot){
                return new CustomUploadViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_hotel_detail_foot,parent,false));
            }

            return new FootViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_hotel_detail_foot,parent,false));
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if(layoutManager instanceof GridLayoutManager){

                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                    @Override
                    public int getSpanSize(int position) {

                        if(getItemViewType(position)==HEAD){

                            return ((GridLayoutManager) layoutManager).getSpanCount();
                        }
                        if(getItemViewType(position)==foot){

                            return ((GridLayoutManager) layoutManager).getSpanCount();
                        }
                       *//* if(getItemViewType(position)== CustomerEvaluation){
                            return ((GridLayoutManager) layoutManager).getSpanCount();
                        }*//*

                        return 1;
                    }
                });

            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

            if(holder instanceof HeaderViewHolder) {
               *//* Intent intent=getIntent();
                String count = intent.getStringExtra("count");
                String s = "" + goodDetailModel.getPirce();
                ((HeaderViewHolder) holder).textViewPrice.setText(s);
                ((HeaderViewHolder) holder).textViewCount.setText(count);
                ImageView imageViewHead = ((HeaderViewHolder) holder).imageViewHead;
                x.image().bind(imageViewHead,goodDetailModel.getPicUrl());
                ((HeaderViewHolder) holder).textViewScore.setText(avStar);
                Log.d("ordetailapi.getPicUrl()", "onBindViewHolder: "+goodDetailModel.getPicUrl());
                ((HeaderViewHolder) holder).textViewDetail.setText(content);*//*

            }
            if(holder instanceof CustomUploadViewHolder){
              *//*  if(customerList.size()<=0){
                    return;
                }
                ((CustomUploadViewHolder) holder).textViewName.setText(customerList.get(position-1).getUserName());
                ((CustomUploadViewHolder) holder).textViewContent.setText(customerList.get(position-1).getCommentContent());
                ((CustomUploadViewHolder) holder).textViewTime.setText(customerList.get(position-1).getCommentTime());
                RatingBar ratingBar = ((CustomUploadViewHolder) holder).ratingBar;
                ratingBar.setRating(customerList.get(position - 1).getStar());
                ImageView imageViewHead = ((CustomUploadViewHolder) holder).imageViewHead;
                String headPicUrl = (String) customerList.get(position -1).getHeadPicUrl();
                x.image().bind(imageViewHead,headPicUrl,imageOptions);
                ((CustomUploadViewHolder) holder).adapter.update(disCommentPicList);*//*
            }
        }

        public int getPosition(RecyclerView.ViewHolder holder){

            return holder.getAdapterPosition()-1;
        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size()+2;
        }
        class CustomUploadViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageViewHead;
            private TextView textViewName,textViewTime;
            private RatingBar ratingBar;
            private TextView textViewContent;
            private RecyclerView recyclerView;
            private RecyclerViewBaseAdapter adapter;
            public CustomUploadViewHolder(View itemView) {
                super(itemView);
              *//*  imageViewHead= (ImageView) itemView.findViewById(R.id.group_head);
                textViewName= (TextView) itemView.findViewById(R.id.group_name);
                textViewTime= (TextView) itemView.findViewById(R.id.group_time);
                ratingBar= (RatingBar) itemView.findViewById(R.id.group_ratingBar_level);
                textViewContent= (TextView) itemView.findViewById(R.id.group_content);
                recyclerView= (RecyclerView) itemView.findViewById(R.id.recyclerView_group_detail);
                adapter=new RecyclerViewBaseAdapter<GoodsDetailBean.GoodDetailModelBean.CustomerListBean.DisCommentPicListBean>(getApplicationContext(),null,
                        R.layout.item_contactfragment_video_recyclerview) {
                    @Override
                    protected void onBindNormalViewHolder(NormalViewHolder holder, GoodsDetailBean.GoodDetailModelBean.CustomerListBean.DisCommentPicListBean model) {
                        ImageView imageView=(ImageView) holder.getViewById(R.id.image_imageUrl);
                        x.image().bind(imageView,model.getPictureUrl());
                        //  String picUrl = model.getPicUrl();

                    }
                };
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                recyclerView.addItemDecoration(new SpaceItem(10,3));*//*
            }

        }
        //头布局的ViewHolder
        class HeaderViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageViewHead;
            private TextView textViewMoreEvaluate,textViewScore;
            private  TextView textViewPrice,textViewCount,textViewDetail,textViewOrder;
            //头布局的ViewHoider
            public HeaderViewHolder(View itemView) {
                super(itemView);
             *//*   final String picUrl = goodDetailModel.getPicUrl();
                imageViewHead= (ImageView) itemView.findViewById(R.id.imageView_group_buying);
                textViewPrice= (TextView) itemView.findViewById(R.id.group_buying_price);
                textViewCount= (TextView) itemView.findViewById(R.id.group_buying_sale);
                textViewDetail= (TextView) itemView.findViewById(R.id.group_buying_detail);
                textViewOrder= (TextView) itemView.findViewById(R.id.textView_group_buying_order);
                textViewMoreEvaluate= (TextView) itemView.findViewById(R.id.textView_more_evaluate);
                textViewScore= (TextView) itemView.findViewById(R.id.score);
                textViewMoreEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(GroupBuyingActivity.this,"更多评论",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(GroupBuyingActivity.this,MoreEvaluateActivity.class);
                        startActivity(intent);
                    }
                });
                textViewOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String CustomerId = PreUtils.getInstance().readString(getApplicationContext(), MainConstant.ID);
                        if(CustomerId==null||CustomerId.equals("")){
                            Intent intent1=new Intent(GroupBuyingActivity.this,LoginActivity.class);
                            intent1.putExtra("detail","2");
                            startActivity(intent1);
                        }else {
                            Intent intent=new Intent(GroupBuyingActivity.this,OrderActivity.class);
                            Intent intent1=getIntent();
                            String name=intent1.getStringExtra("name");
                            intent.putExtra("name", name);
                            intent1.putExtra("type","goods");
                            //intent.putExtra("price",price);
                            intent.putExtra("picUrl", picUrl);
                            intent.putExtra("type","goods");
                            startActivity(intent);
                        }
                    }
                });*//*
            }



        }
        class FootViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageViewHead;
            private TextView textViewMoreEvaluate,textViewScore;
            private  TextView textViewPrice,textViewCount,textViewDetail,textViewOrder;
            //头布局的ViewHoider
            public FootViewHolder(View itemView) {
                super(itemView);
             *//*   final String picUrl = goodDetailModel.getPicUrl();
                imageViewHead= (ImageView) itemView.findViewById(R.id.imageView_group_buying);
                textViewPrice= (TextView) itemView.findViewById(R.id.group_buying_price);
                textViewCount= (TextView) itemView.findViewById(R.id.group_buying_sale);
                textViewDetail= (TextView) itemView.findViewById(R.id.group_buying_detail);
                textViewOrder= (TextView) itemView.findViewById(R.id.textView_group_buying_order);
                textViewMoreEvaluate= (TextView) itemView.findViewById(R.id.textView_more_evaluate);
                textViewScore= (TextView) itemView.findViewById(R.id.score);
                textViewMoreEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(GroupBuyingActivity.this,"更多评论",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(GroupBuyingActivity.this,MoreEvaluateActivity.class);
                        startActivity(intent);
                    }
                });
                textViewOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String CustomerId = PreUtils.getInstance().readString(getApplicationContext(), MainConstant.ID);
                        if(CustomerId==null||CustomerId.equals("")){
                            Intent intent1=new Intent(GroupBuyingActivity.this,LoginActivity.class);
                            intent1.putExtra("detail","2");
                            startActivity(intent1);
                        }else {
                            Intent intent=new Intent(GroupBuyingActivity.this,OrderActivity.class);
                            Intent intent1=getIntent();
                            String name=intent1.getStringExtra("name");
                            intent.putExtra("name", name);
                            intent1.putExtra("type","goods");
                            //intent.putExtra("price",price);
                            intent.putExtra("picUrl", picUrl);
                            intent.putExtra("type","goods");
                            startActivity(intent);
                        }
                    }
                });*//*
            }



        }

    }*/

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.hotel_detail);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }


   /* private void httpRequest(){

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

    }*/

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_detials;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Back:
                showBack(toolbar,imageView);
                break;
        }
    }


 /*   class MyAdapter extends RecyclerViewBaseAdapter<HotelItem>{


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


    *//**
     * 详情页顶部图片适配器
     *//*
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
    }*/
}
