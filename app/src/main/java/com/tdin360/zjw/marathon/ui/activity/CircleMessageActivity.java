package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.db.CirclePraiseDatabaseImpl;
import com.tdin360.zjw.marathon.utils.db.impl.CircleNoticeDetailsServiceImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态消息
 */
public class CircleMessageActivity extends BaseActivity {
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    private CircleNoticeDetailsServiceImpl circlePraiseDatabase;
    //List<CirclePriseTableModel> allCircleDetail=new ArrayList<>();

    @ViewInject(R.id.rv_circle_message)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    private ImageOptions imageOptions,imageOptionsCircle;
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()==EnumEventBus.CIRCLENOTICE){
           //initView();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                .setCrop(true)
                .setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        imageOptionsCircle = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
     /*   circlePraiseDatabase=new CircleNoticeDetailsServiceImpl(getApplicationContext());
        allCircleDetail=*/
        initToolbar();
        initView();
    }
    private void initView() {
        for (int i = 0; i <6; i++) {
            list.add(""+i);
        }
        //allCircleDetail.clear();
        circlePraiseDatabase=new CircleNoticeDetailsServiceImpl(getApplicationContext());
        List<CirclePriseTableModel> allCircleNotice = circlePraiseDatabase.getAllCircleNotice();
        if(allCircleNotice.size()<=0){
            ToastUtils.showCenter(getApplicationContext(),"暂时还没有星人来访问");
            return;
        }
        Log.d("allcircle", "initView: "+allCircleNotice.size());
        adapter=new RecyclerViewBaseAdapter<CirclePriseTableModel>(getApplicationContext(),allCircleNotice,R.layout.item_circle_message) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, CirclePriseTableModel model) {
                String commentContent = model.getCommentContent();
                ImageView imageViewHead = (ImageView) holder.getViewById(R.id.iv_circle_message);
                ImageView imageViewCircle = (ImageView) holder.getViewById(R.id.ic_circle_message_pic);
                x.image().bind(imageViewHead,model.getHeadImg(),imageOptionsCircle);
                String dynamicPictureUrl = model.getDynamicPictureUrl();
               if(TextUtils.isEmpty(commentContent)){
                   String str="<font color='#ff621a'>"+model.getNickName()+"</font>赞了你：";
                   holder.setText(R.id.tv_circle_message_title, Html.fromHtml(str));
                   holder.setText(R.id.tv_circle_message_comment,model.getCommentContent());
                   holder.setText(R.id.tv_circle_message_content,model.getDynamicContent());
                   holder.setText(R.id.tv_circle_message_time,model.getTime());
                  // Log.d("getDynamicContent", "onBindNormalViewHolder: "+model.getDynamicContent());
                   if(TextUtils.isEmpty(dynamicPictureUrl)){
                       imageViewCircle.setVisibility(View.GONE);
                   }else{
                       imageViewCircle.setVisibility(View.VISIBLE);
                       x.image().bind(imageViewCircle,model.getDynamicPictureUrl(),imageOptions);
                   }
                }else{
                   String str="<font color='#ff621a'>"+model.getNickName()+"</font>评论了你：";
                   holder.setText(R.id.tv_circle_message_title,Html.fromHtml(str));
                   holder.setText(R.id.tv_circle_message_comment,model.getCommentContent());
                   holder.setText(R.id.tv_circle_message_content,model.getDynamicContent());
                   holder.setText(R.id.tv_circle_message_time,model.getTime());
                   if(TextUtils.isEmpty(dynamicPictureUrl)){
                       imageViewCircle.setVisibility(View.GONE);
                   }else{
                       imageViewCircle.setVisibility(View.VISIBLE);
                       x.image().bind(imageViewCircle,model.getDynamicPictureUrl(),imageOptions);
                   }

               }
            }
        };
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

    }

    private void initToolbar() {
        titleTv.setText("来访的佰家星人");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        //showBack(toolbar,imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.isNotice(getApplicationContext(),false);
                EnumEventBus notice = EnumEventBus.NOTICE;
                EventBus.getDefault().post(new EventBusClass(notice));
                finish();
            }
        });
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_circle_message;
    }
}
