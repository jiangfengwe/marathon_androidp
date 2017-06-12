package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.MenuModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.MenuDataUtils;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ShareInfoManager;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.db.impl.EventDetailsServiceImpl;
import com.tdin360.zjw.marathon.weight.Carousel;
import com.tdin360.zjw.marathon.weight.MenuView;
import com.tdin360.zjw.marathon.weight.SpaceItemDecoration;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * 赛事详情界面
 * @author zhangzhijun
 */
public class MarathonDetailsActivity extends BaseActivity implements Carousel.OnCarouselItemClickListener{


    private Carousel mCarousel;
    private List<CarouselModel>carouselList=new ArrayList<>();
    private List<CarouselModel>sponsorList= new ArrayList<>();
    private TextView loadFail;
    private View  isEnableView;
    private EventDetailsServiceImpl service;
    private RecyclerView mRecyclerView;
    private View headerView;
    private MyAdapter mAdapter;
    private LinearLayout panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initMenu();

    }

    /**
     * 分享需要配置确保回调成功
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getApplication()).onActivityResult(requestCode,resultCode,data);


    }


    @Override
    public int getLayout() {
        return R.layout.activity_marathon_details;
    }

    //初始化
    private void initView() {

         this.headerView = View.inflate(this,R.layout.marathon_details_header,null);
         this.mRecyclerView = (RecyclerView) this.findViewById(R.id.mRecyclerView);
         this.panel = (LinearLayout) this.findViewById(R.id.panel);
         this.mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
         this.mRecyclerView.addItemDecoration(new SpaceItemDecoration(10,2));
         this.mAdapter=new MyAdapter();
         this.mRecyclerView.setAdapter(mAdapter);
         this.service = new EventDetailsServiceImpl(this);
        setToolBarTitle(MarathonDataUtils.init().getEventName());



        showBackButton();
        /**
         * 构建分享内容
         */
        ShareInfoManager manager = new ShareInfoManager(this);
         manager.buildShareWebLink(MarathonDataUtils.init().getEventName(),MarathonDataUtils.init().getShareUrl(),"佰家运动",MarathonDataUtils.init().getEventImageUrl());

        showShareButton(manager);

        //如果报名已结束就显示蒙板
          this.isEnableView=this.findViewById(R.id.isEnable);
         if(!MarathonDataUtils.init().isRegister()){

             this.isEnableView.setVisibility(View.VISIBLE);

         }

        /**
         * 不可报名时用户点击是进行提示
         */
         this.isEnableView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Snackbar.make(isEnableView,"报名暂时未开放或已结束!",Snackbar.LENGTH_SHORT).show();
             }
         });


         this.loadFail = (TextView) this.findViewById(R.id.loadFail);

         this.mCarousel = (Carousel) headerView.findViewById(R.id.mCarousel);
        this.mCarousel.setOnCarouselItemClickListener(this);

         this.loadFail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                loadData();
             }
         });


         loadData();



    }

    /**
     * 轮播图的点击处理
     * @param pos
     */
    @Override
    public void onClick(int pos) {


        CarouselModel model = carouselList.get(pos);
        if(model.getLinkUrl()==null||model.getLinkUrl().equals("null")){

            return;
        }
        String url = model.getLinkUrl();

        if(!url.startsWith("http")){

            url="http://"+url;
        }
        Intent intent = new Intent(this,ShowHtmlActivity.class);
        intent.putExtra("title",model.getTitle());
        intent.putExtra("url",url);

         startActivity(intent);

    }

    /**
     * 赞助商列表
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

       private final int HEAD=0x01;

        @Override
        public int getItemViewType(int position) {


            if(position==0){
                return HEAD;
            }
            return super.getItemViewType(position);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == HEAD
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(MyViewHolder holder) {
            super.onViewAttachedToWindow(holder);


            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if(lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && holder.getLayoutPosition() == 0) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType==HEAD){
                return new MyViewHolder(headerView);
            }

            return new MyViewHolder(View.inflate(MarathonDetailsActivity.this,R.layout.marath_details_list_item,null));
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {



            if(getItemViewType(position)==HEAD ){
                return;
            }


            CarouselModel model = sponsorList.get(getRealPosition(holder));
            ImageOptions imageOptions = new ImageOptions.Builder()
                        //.setSize(DensityUtil.dip2px(340), DensityUtil.dip2px(300))//图片大小
                        //.setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                        .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                        .setLoadingDrawableId(R.drawable.loading_small_default)//加载中默认显示图片
                        .setUseMemCache(true)//设置使用缓存
                        .setFailureDrawableId(R.drawable.loading_small_error)//加载失败后默认显示图片
                        .build();
                x.image().bind(holder.imageView, model.getPicUrl(), imageOptions);

             holder.title.setText(model.getTitle().equals("null")?"":model.getTitle());


        }
        public int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return headerView == null ? position : position - 1;
        }
        @Override
        public int getItemCount() {
            return headerView==null? sponsorList.size():sponsorList.size()+1;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView title;
            public MyViewHolder(final View itemView) {
                super(itemView);
                if(getItemViewType()!=HEAD){

                    this.imageView= (ImageView) itemView.findViewById(R.id.imageView);
                    this.title = (TextView) itemView.findViewById(R.id.sponsorName);

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {
                                CarouselModel model = sponsorList.get(getRealPosition(MyViewHolder.this));
                                if (model.getLinkUrl() != null && !model.getLinkUrl().equals("")) {

                                    String url=model.getLinkUrl();
                                    if (!model.getLinkUrl().startsWith("http")) {

                                        url = "http://" + model.getLinkUrl();
                                    }

                                    Intent it = new Intent(MarathonDetailsActivity.this,ShowHtmlActivity.class);
                                    it.putExtra("title",model.getTitle());
                                    it.putExtra("url",url);
                                    startActivity(it);
                                } else {

                                    Snackbar.make(itemView, "该暂赞助商没有提供链接地址", Snackbar.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
e.printStackTrace();
                                Snackbar.make(itemView, "地址有误无法打开", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        }
    }

    private KProgressHUD hud;
    //加载数据(包括缓存数据和网络数据)
    private void loadData(){

        hud = KProgressHUD.create(this);
                hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        /**
         * 判断网络是否处于可用状态
         */
        if(NetWorkUtils.isNetworkAvailable(this)){

            //加载网络数据
            httpRequest();
        }else {

            hud.dismiss();
            panel.setVisibility(View.GONE);
            //获取缓存数据

            this.carouselList = this.service.getAllEventDetail(MarathonDataUtils.init().getEventId(),"0");
            this.sponsorList = this.service.getAllEventDetail(MarathonDataUtils.init().getEventId(),"1");


            //如果获取得到缓存数据则加载本地数据
            if(carouselList.size()==0&&sponsorList.size()==0){
                panel.setVisibility(View.VISIBLE);
                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);
                //如果缓存数据不存在则需要用户打开网络设置

                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setMessage("网络不可用，是否打开网络设置");
                alert.setCancelable(false);
                alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //打开网络设置

                        startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
            }else {//显示本地数据

                showCarousel();
                mAdapter.notifyDataSetChanged();

            }

        }


    }
    /**
     * 初始化菜单选项
     */
    private void initMenu(){
      List<MenuModel>  list =  MenuDataUtils.getMenus(this);
      LinearLayout menuLayout = (LinearLayout)  headerView.findViewById(R.id.menuLayout);
     for(int i=0;i<list.size();i++){

         MenuModel menuModel = list.get(i);
         MenuView menu = new MenuView(this);
         menu.setMenuTitleColor(Color.WHITE);
         menu.setMenuContent(menuModel.getId(),menuModel.getIcon(),menuModel.getTitle());

         menuLayout.addView(menu);
         //菜单点击事件
         menu.setMenuOnItemClickListener(new MenuView.MenuOnItemClickListener() {
             @Override
             public void onMenuClick(MenuView itemView) {

                 Intent intent=null;
                 switch ((int)itemView.getMenuId()){

                     case 0://赛事简介

                         intent = new Intent(MarathonDetailsActivity.this,ShowHtmlActivity.class);
                         intent.putExtra("shareTitle",MarathonDataUtils.init().getEventName());
                         intent.putExtra("title","赛事简介");
                         intent.putExtra("url",HttpUrlUtils.MARATHON_INTRO+"?eventId="+MarathonDataUtils.init().getEventId()+"&categoryName=赛事简介");

                         break;
                     case 1: //赛事新闻
                         intent = new Intent(MarathonDetailsActivity.this,MarathonNewsListActivity.class);

                         break;
                     case 2://赛事公告
                         intent = new Intent(MarathonDetailsActivity.this,MarathonNoticeListActivity.class);

                         break;
                     case 3://参赛路线
                         intent = new Intent(MarathonDetailsActivity.this,MarathonMapActivity.class);


                         break;

                 }

                 startActivity(intent);

             }
         });
     }

    }

    /**
     * 请求网络数据
     */
    private void httpRequest(){


        loadFail.setVisibility(View.GONE);
        final RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_DETAILS);
        params.addQueryStringParameter("eventId", MarathonDataUtils.init().getEventId());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params,new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject json  =new JSONObject(result);

//                     Log.d("--------->>>", "onSuccess: "+json);
                    service.deleteAll(MarathonDataUtils.init().getEventId());
                    sponsorList.clear();
                    carouselList.clear();
                    /**
                     * 获取轮播图数据
                     */
                    JSONArray array1  =json.getJSONArray("CarouselPictures");
                    for(int i=0;i<array1.length();i++){

                        JSONObject jsonObject = array1.getJSONObject(i);

                        String picUrl = jsonObject.getString("PictureUrl");
                        String url = jsonObject.getString("Url");
                        String name = jsonObject.getString("Name");
                        CarouselModel model = new CarouselModel(MarathonDataUtils.init().getEventId(),name,picUrl,url,"0");
                        carouselList.add(model);

                        service.addEventDetail(model);

                    }
                    /**
                     * 获取赞助商数据
                     */

                    JSONArray array2  =json.getJSONArray("Sponsors");
                    for(int i=0;i<array2.length();i++){

                        JSONObject jsonObject = array2.getJSONObject(i);
                        String pictureUrl = jsonObject.getString("PictureUrl");
                        String url = jsonObject.getString("Url");
                        String title = jsonObject.getString("Name");
                        CarouselModel carouselModel = new CarouselModel(MarathonDataUtils.init().getEventId(),title,pictureUrl,url,"1");
                        sponsorList.add(carouselModel);
                        service.addEventDetail(carouselModel);


                    }
                   panel.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFail.setVisibility(View.VISIBLE);
                     panel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


                Toast.makeText(MarathonDetailsActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();
                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);
                panel.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

                hud.dismiss();
                showCarousel();

                //更新赞助商数据
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    //显示轮播图
    private void showCarousel(){

        List<View>views = new ArrayList<>();
        for(int i=0;i<carouselList.size();i++){

            CarouselModel carouselModel = this.carouselList.get(i);
            ImageView imageView  =new ImageView(this);
            imageView.setBackgroundResource(R.drawable.loading_banner_default);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    //.setSize(DensityUtil.dip2px(1000), DensityUtil.dip2px(320))//图片大小
//                    .setLoadingDrawableId(R.drawable.loading_banner_default)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.loading_banner_error)//加载失败后默认显示图片
                    .build();
            x.image().bind(imageView, carouselModel.getPicUrl(),imageOptions);
            views.add(imageView);


        }

        this.mCarousel.loadCarousel(views);
    }


    /**
     * 立即报名
     * @param view
     */
    public void toSignUp(View view) {


        switch (view.getId()){

            case R.id.signBtn://个人报名
                //检查用户是否登录登录后才能报名
                if(SharedPreferencesManager.isLogin(getApplicationContext())){

                    Intent intent = new Intent(MarathonDetailsActivity.this,SignUpActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(MarathonDetailsActivity.this,LoginActivity.class);
                    startActivity(intent);
                    //设置登录成功后跳转到报名界面
                    LoginNavigationConfig.instance().setNavType(NavType.SignUp);

                }
                break;
            case R.id.signTeam://团队报名

                Toast.makeText(this,"开发中敬请期待",Toast.LENGTH_SHORT).show();


//                //检查用户是否登录登录后才能报名
//                if(SharedPreferencesManager.isLogin(getApplicationContext())){
//
//                    Intent intent = new Intent(MarathonDetailsActivity.this,TeamSignUpActivity.class);
//                    startActivity(intent);
//
//                }else {
//                    Intent intent = new Intent(MarathonDetailsActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    //设置登录成功后跳转到报名界面
//                    LoginNavigationConfig.instance().setNavType(NavType.Team);
//
//                }
                break;
        }



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCarousel.onDestroy();
        //内存泄漏
        UMShareAPI.get(this).release();
    }
}
