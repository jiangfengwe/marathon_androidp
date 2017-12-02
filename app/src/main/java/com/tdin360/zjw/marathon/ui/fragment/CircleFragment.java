package com.tdin360.zjw.marathon.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CircleTabTitleAdapter;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.ui.activity.CircleDetailActivity;
import com.tdin360.zjw.marathon.ui.activity.CircleMessageActivity;
import com.tdin360.zjw.marathon.ui.activity.MyCircleActivity;
import com.tdin360.zjw.marathon.ui.activity.PublishActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子
 * @author zhangzhijun
 * Created by admin on 17/3/9.
 */

public class CircleFragment extends BaseFragment implements View.OnClickListener{
    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.rv_circle)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    private ShareAction action;





    public static CircleFragment newInstance(){


        return new CircleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_circle,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initView();

       /* TabLayout layout = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.mViewPager);
        viewPager.setAdapter(new CircleTabTitleAdapter(getChildFragmentManager()));
        layout.setupWithViewPager(viewPager);*/



    }

    private void initView() {
        for (int i = 0; i < 8; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getContext(),list,R.layout.item_circle) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
               // cirpicList = circleList.get(position <0? 0:position).getCirpicList();
               /* for(int i=0;i<cirpicList.size();i++){
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(cirpicList.get(i).getPicUrl());
                    info.setBigImageUrl(cirpicList.get(i).getPicUrl());
                    imageInfo.add(info);
                }*/
               String str="http://www.eaglesoft.org/public/UploadFiles/image/20141017/20141017152856_751.jpg";

                for(int i=0;i<12;i++){
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(str);
                    info.setBigImageUrl(str);
                    imageInfo.add(info);
                }
                NineGridView nineGridView = (NineGridView) holder.getViewById(R.id.circle_nineGrid);
                nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(),imageInfo));
                TextView tvComment = (TextView) holder.getViewById(R.id.tv_circle_comment);
                final TextView tvShare = (TextView) holder.getViewById(R.id.tv_circle_share);
                final TextView tvPraise = (TextView) holder.getViewById(R.id.tv_circle_praise);
                ImageView ivPortrait = (ImageView) holder.getViewById(R.id.iv_circle_portrait);
                //头像跳转到我的动态
                ivPortrait.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), MyCircleActivity.class);
                        startActivity(intent);
                    }
                });
                //点赞
                tvPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.anim_in_praise);
                        tvPraise.clearAnimation();
                        tvPraise.startAnimation(animation);
                    }
                });
                //评论
                tvComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), CircleDetailActivity.class);
                        startActivity(intent);
                    }
                });
                //分享
                tvShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.anim_in_praise);
                        tvShare.clearAnimation();
                        tvShare.startAnimation(animation);
                        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                            shareApp();
                        }else {
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10001);
                            }else {
                                shareApp();
                            }
                        }
                    }
                });

            }
        };
        adapter.addHeaderView(R.layout.item_circle_head);
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), CircleDetailActivity.class);
                startActivity(intent);

            }
        });
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
    /**
     *  分享给好友
     */
    private void shareApp(){

           /*使用友盟自带分享模版*/
        action = new ShareAction(getActivity()).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                UMWeb umWeb = new UMWeb(getString(R.string.shareDownLoadUrl));
                umWeb.setTitle("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。");
                umWeb.setDescription("佰家运动");
                UMImage image = new UMImage(getActivity(),R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(getActivity()).withText("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。")
                        .setPlatform(share_media)
                        .setCallback(new MyUMShareListener())
                        .withMedia(umWeb)
                        .share();
            }


        });


        action.open();
    }
    /**
     * 自定义分享结果监听器
     */
    private class MyUMShareListener implements UMShareListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(),"正在打开分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(getActivity(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(getActivity(), platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(getActivity(), platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(),"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }

    private void initToolbar() {
        titleTv.setText("佰家圈");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.circle_notice);
        imageView.setOnClickListener(this);

        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        this.rightImage.setImageResource(R.drawable.circle_publish);
        this.rightImage.setVisibility(View.VISIBLE);
        //发布动态
        this.rightImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_Back:
                //通知
                intent=new Intent(getActivity(), CircleMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.navRightItemImage:
                //发布动态
                intent=new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
                break;
        }

    }
}
