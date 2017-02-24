package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.MarathonHomeMyGridViewAdapter;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.MenuModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MenuDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ShareInfoManager;
import com.tdin360.zjw.marathon.weight.Carousel;
import com.tdin360.zjw.marathon.weight.MenuView;
import com.tdin360.zjw.marathon.weight.MyGridView;
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
public class MarathonDetailsActivity extends BaseActivity {

    private Carousel mCarousel;
    private MyGridView mGridView;
    private List<CarouselModel>carouselList=new ArrayList<>();
    private List<CarouselModel>sponsorList= new ArrayList<>();
    private MarathonHomeMyGridViewAdapter adapter;
    private String eventId;
    private TextView notData;
    private LinearLayout main;
    private LinearLayout loading;
    private TextView loadFail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        设置状态栏透明
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }

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
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_marathon_details;
    }

    //初始化
    private void initView() {


        this.eventId = this.getIntent().getStringExtra("eventId");
        String eventName = this.getIntent().getStringExtra("eventName");
        setToolBarTitle(eventName);
        showBackButton();
        /**
         * 构建分享内容
         */
        ShareInfoManager manager = new ShareInfoManager(this);
         manager.buildShareBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.guide3));
       // ShareInfoManager.getManager(this).setShareWebLink(eventName,"http://www.baidu.com","来自赛事详情的分享", BitmapFactory.decodeResource(getResources(),R.mipmap.logo));
        showShareButton(manager);

         this.main = (LinearLayout) this.findViewById(R.id.main);
         this.loading = (LinearLayout) this.findViewById(R.id.loading);
         this.loadFail = (TextView) this.findViewById(R.id.loadFail);
         this.notData = (TextView) this.findViewById(R.id.notData);
         this.mCarousel = (Carousel) this.findViewById(R.id.mCarousel);
         this.mGridView  = (MyGridView) this.findViewById(R.id.myGridView);
         this.adapter = new MarathonHomeMyGridViewAdapter(sponsorList,this);
         this.mGridView.setAdapter(adapter);

         this.loadFail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 httpRequest();
             }
         });

         loadData();


    }

    //加载数据(包括缓存数据和网络数据)
    private void loadData(){

        /**
         * 判断网络是否处于可用状态
         */
        if(NetWorkUtils.isNetworkAvailable(this)){

            //加载网络数据
            httpRequest();
        }else {

            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            loadFail.setText("点击重新加载");
            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据
            loading.setVisibility(View.GONE);

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
        }


    }
    /**
     * 初始化菜单选项
     */
    private void initMenu(){
      List<MenuModel>  list =  MenuDataUtils.getMenus(this);
      LinearLayout menuLayout = (LinearLayout) this.findViewById(R.id.menuLayout);
     for(int i=0;i<list.size();i++){

          MenuModel menuModel = list.get(i);
         MenuView menu = new MenuView(this);
         menu.setMenuContent(menuModel.getId(),menuModel.getIcon(),menuModel.getTitle());

         menuLayout.addView(menu);
         //菜单点击事件
         menu.setMenuOnItemClickListener(new MenuView.MenuOnItemClickListener() {
             @Override
             public void onMenuClick(MenuView itemView) {

                 Intent intent=null;
                 switch ((int)itemView.getMenuId()){

                     case 0://赛事报名
                         intent = new Intent(MarathonDetailsActivity.this,SignUpActivity.class);

                         break;
                     case 1://赛事简介

                         intent = new Intent(MarathonDetailsActivity.this,ShowHtmlActivity.class);
                         intent.putExtra("isSign",true);
                         intent.putExtra("title","赛事简介");
                         intent.putExtra("url","http://byydtk.oicp.net:26211/EventInfo/EventInfoDetail?eventId="+eventId+"&categoryName=赛事简介");
                         break;
                     case 2: //赛事新闻
                         intent = new Intent(MarathonDetailsActivity.this,MarathonNewsListActivity.class);

                         break;
                     case 3://赛事公告
                         intent = new Intent(MarathonDetailsActivity.this,MarathonNoticeListActivity.class);

                         break;
                     case 4://参赛路线
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

        /**
         * 清空旧数据
         */
        if(carouselList!=null) {
            carouselList.clear();
            sponsorList.clear();
        }
        loading.setVisibility(View.VISIBLE);
        loadFail.setVisibility(View.GONE);
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_HOME);
        params.addQueryStringParameter("eventId", eventId);
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

                        Log.d("-------->>>", "onSuccess: "+result);
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

                    }

//                    加载成功显示界面
                    main.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(MarathonDetailsActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();
                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

                loading.setVisibility(View.GONE);
                showCarousel();
                if(sponsorList.size()>0) {
                    //更新赞助商数据
                    adapter.notifyDataSetChanged();
                }else {
                    notData.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    //显示轮播图
    private void showCarousel(){

        List<View>views = new ArrayList<>();
        for(int i=0;i<carouselList.size();i++){

            CarouselModel carouselModel = this.carouselList.get(i);
            ImageView imageView  =new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    //.setSize(DensityUtil.dip2px(1000), DensityUtil.dip2px(320))//图片大小
                    .setLoadingDrawableId(R.drawable.carousel_default)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.search_nodata)//加载失败后默认显示图片
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

        Intent intent = new Intent(this,PayActivity.class);
        startActivity(intent);

    }
}
