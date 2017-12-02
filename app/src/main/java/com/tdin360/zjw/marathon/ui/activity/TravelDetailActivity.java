package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.TravelDetailTabLayoutAdapter;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.view.annotation.ViewInject;

/**
 * 旅游详情
 */

public class TravelDetailActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_travel_detail_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_travel_detail_share)
    private ImageView ivShare;
    @ViewInject(R.id.layout_travel_detail_pic)
    private LinearLayout layoutPic;
    @ViewInject(R.id.tv_travel_detail_name)
    private TextView tvName;
    @ViewInject(R.id.tv_travel_detail_price)
    private TextView tvPrice;
    @ViewInject(R.id.tv_travel_detail_address)
    private TextView tvAddress;
    @ViewInject(R.id.tv_travel_detail_route)
    private TextView tvRoute;
    @ViewInject(R.id.tv_travel_detail_consult)
    private TextView tvConsult;
    @ViewInject(R.id.tv_travel_detail_order)
    private TextView tvOrder;

    private ShareAction action;



    @ViewInject(R.id.tabs_travel_detail)
    private TabLayout tabsTravel;
    @ViewInject(R.id.vp_travel_detail)
    private ViewPager vpTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initTabLayout();

    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        layoutPic.setOnClickListener(this);
        tvConsult.setOnClickListener(this);
        tvOrder.setOnClickListener(this);

    }

    private void initTabLayout() {
        vpTravel.setAdapter(new TravelDetailTabLayoutAdapter(getSupportFragmentManager()));
        tabsTravel.setupWithViewPager(vpTravel);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_travel_detail;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_travel_detail_back:
                //返回
                finish();
                break;
            case R.id.iv_travel_detail_share:
                //分享
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                    shareApp();
                }else {
                    if(ContextCompat.checkSelfPermission(TravelDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10001);
                    }else {
                        shareApp();
                    }
                }
                break;
            case R.id.layout_travel_detail_pic:
                //图片浏览
                break;
            case R.id.tv_travel_detail_consult:
                //客服咨询
                break;
            case R.id.tv_travel_detail_order:
                //立即预定
                intent=new Intent(TravelDetailActivity.this,TravelOrderActivity.class);
                startActivity(intent);
                break;
        }

    }
    /**
     *  分享给好友
     */
    private void shareApp(){

           /*使用友盟自带分享模版*/
        action = new ShareAction(TravelDetailActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                UMWeb umWeb = new UMWeb(getString(R.string.shareDownLoadUrl));
                umWeb.setTitle("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。");
                umWeb.setDescription("佰家运动");
                UMImage image = new UMImage(TravelDetailActivity.this,R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(TravelDetailActivity.this).withText("赛事尽在佰家运动App，下载佰家运动，随时随地了解赛事信息，查询、报名全程无忧。")
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
            Toast.makeText(TravelDetailActivity.this,"正在打开分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(TravelDetailActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TravelDetailActivity.this, platform + " 分享成功啦!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TravelDetailActivity.this, platform + " 分享失败啦!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(TravelDetailActivity.this,"分享已取消!",Toast.LENGTH_SHORT).show();
        }
    }
}
