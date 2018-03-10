package com.tdin360.zjw.marathon.ui.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.SystemNoticeBean;
import com.tdin360.zjw.marathon.ui.activity.LoginActivity;
import com.tdin360.zjw.marathon.ui.activity.MyCircleActivity;
import com.tdin360.zjw.marathon.ui.activity.MyInfoActivity;
import com.tdin360.zjw.marathon.ui.activity.MyNoticeMessageActivity;
import com.tdin360.zjw.marathon.ui.activity.MyOrderActivity;
import com.tdin360.zjw.marathon.ui.activity.SettingActivity;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.db.impl.SystemNoticeDetailsServiceImpl;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**个人中心
 * Created by Administrator on 2016/8/9.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener{

    public static final String ACTION="LOGIN_STATUS";//广播action
    @ViewInject(R.id.my_header)
    private LinearLayout layoutHeader;
    @ViewInject(R.id.my_name)
    private TextView userName;
    @ViewInject(R.id.my_sign)
    private TextView tvSign;
    @ViewInject(R.id.my_portrait)
    //private ImageView myImageView;
    private SimpleDraweeView myImageView;
    @ViewInject(R.id.my_notice)
    private ImageView myNotice;
    @ViewInject(R.id.my_order)
    private LinearLayout layoutOrder;
    @ViewInject(R.id.my_dynamic)
    private LinearLayout layoutDynamic;
    @ViewInject(R.id.my_share)
    private LinearLayout layoutShare;
    @ViewInject(R.id.my_setting)
    private LinearLayout layoutSetting;
    @ViewInject(R.id.my_phone)
    private LinearLayout layoutPhone;
    @ViewInject(R.id.iv_system_notice_show)
    private ImageView ivShow;
    private boolean flag;






    private ShareAction action;
    private String noticeView;

    ImageOptions imageOptions;

    public static MyFragment newInstance(){

        return   new MyFragment();
    }
    @Subscribe
    public void onEvent(EventBusClass event){
        if(event.getEnumEventBus()==EnumEventBus.SYSTEM){
            boolean open = SharedPreferencesManager.getNotice(getContext());
            if(open){
                ivShow.setVisibility(View.VISIBLE);
            }else{
                ivShow.setVisibility(View.GONE);
            }
        }
        if(event.getEnumEventBus()==EnumEventBus.SYSTEMNOTICE){
            boolean open = SharedPreferencesManager.getNotice(getContext());
            if(open){
                ivShow.setVisibility(View.VISIBLE);
            }else{
                ivShow.setVisibility(View.GONE);
            }
        }
        if(event.getEnumEventBus()==EnumEventBus.NOTICECLICK){
            SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getActivity());
            final List<SystemNoticeBean> allCircleNotice = systemNoticeDetailsService.getAllSystemNotice();
            for (int i = 0; i <allCircleNotice.size() ; i++) {
                String notice = allCircleNotice.get(i).getNotice();
                Log.d("55555notice", "onEvent: "+notice);
                if(notice.equals("0")){
                    ivShow.setVisibility(View.VISIBLE);
                    break;
                }else{
                    ivShow.setVisibility(View.GONE);
                }
            }
        }
        if(event.getEnumEventBus()==EnumEventBus.NOTICEDELETE){
            SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getActivity());
            final List<SystemNoticeBean> allCircleNotice = systemNoticeDetailsService.getAllSystemNotice();
            for (int i = 0; i <allCircleNotice.size() ; i++) {
                String notice = allCircleNotice.get(i).getNotice();
                Log.d("55555notice", "onEvent: "+notice);
                if(notice.equals("0")){
                    ivShow.setVisibility(View.VISIBLE);
                    break;
                }else{
                    ivShow.setVisibility(View.GONE);
                }
            }
        }


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!flag){
            EventBus.getDefault().register(this);
            flag=!flag;
        }
        imageOptions= new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(30))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        return  inflater.inflate(R.layout.fargment_my,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //CirclePriseTableModel circlePriseTableModel=new CirclePriseTableModel("aa",null,null,null,3,"ttttttttttt","rrrrrrrr","2017-9-2",null);

        //显示信息
       showInfo();
        //注册广播
        register();
        initView();
        SystemNoticeDetailsServiceImpl systemNoticeDetailsService=new SystemNoticeDetailsServiceImpl(getContext());
        List<SystemNoticeBean> allSystemNotice = systemNoticeDetailsService.getAllSystemNotice();
        if(allSystemNotice.size()<=0){
            return;
        }else {
            for (int i = 0; i <allSystemNotice.size() ; i++) {
                String notice = allSystemNotice.get(i).getNotice();
                noticeView=notice;
            }
            if(noticeView.equals("0")){
                ivShow.setVisibility(View.VISIBLE);
            }else{
                ivShow.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        myNotice.setOnClickListener(this);
        layoutHeader.setOnClickListener(this);
        layoutOrder.setOnClickListener(this);
        layoutDynamic.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        LoginUserInfoBean.UserBean model = SharedPreferencesManager.getLoginInfo(getContext());
        String customerId = model.getId() + "";
        Intent intent;
        switch (v.getId()){
            case R.id.my_notice:
                //通知消息
                intent = new Intent(getContext(), MyNoticeMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_header:
                //登录
               /* intent = new Intent(getContext(), MyInfoActivity.class);
                startActivity(intent);*/
                //判断用户是否登录
                if(SharedPreferencesManager.isLogin(getContext())){
                    //若登录则显示用户信息
                     intent = new Intent(getContext(), MyInfoActivity.class);
                    startActivity(intent);
                }else {
                    //用户没有登录则跳转到登录
                    intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    LoginNavigationConfig.instance().setNavType(NavType.Other);
                }
                /*intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);*/
                break;
            case R.id.my_order:
                //我的订单
                if(TextUtils.isEmpty(customerId)){
                    intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else{
                intent=new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent);
                }
                break;
            case R.id.my_dynamic:
                LoginUserInfoBean.UserBean model1 = SharedPreferencesManager.getLoginInfo(getContext());
                String customerId1 = model1.getId() + "";
                //我的动态
                if(TextUtils.isEmpty(customerId1)){
                    intent=new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }else{
                    intent=new Intent(getActivity(), MyCircleActivity.class);
                    intent.putExtra("customerId",customerId1);
                    startActivity(intent);
                }
                break;
            case R.id.my_share:
                //分享给好友
                if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                shareApp();
            }else {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10001);
                }else {
                    shareApp();
                }
            }
                break;
            case R.id.my_setting:
                //系统设置
                intent= new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_phone:
               /* SystemNoticeDetailsServiceImpl systemNoticeDetailsService=new SystemNoticeDetailsServiceImpl(getContext());
                SharedPreferencesManager.isNotice(getContext(),true);
                EnumEventBus circle = EnumEventBus.SYSTEM;
                EventBus.getDefault().post(new EventBusClass(circle));
                SystemNoticeBean systemNoticeBean=new SystemNoticeBean(null,null,0,null,null);
                systemNoticeDetailsService.addSystemNotice(systemNoticeBean);*/
                //联系客服
                if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE},2);
                    //ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
                }else{
                    showTelDialog();
                }

                break;
        }

    }
    private void showTelDialog() {
        android.support.v7.app.AlertDialog.Builder normalDialog =new android.support.v7.app.AlertDialog.Builder(getActivity());
        normalDialog.setMessage("是否拨打085138157660");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String phone = textViewhot.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + "085138157660");
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          switch (requestCode){
              case 10001:
                  if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                      shareApp();
                    //用户授权成功
                  }else {
                      //用户没有授权
                      AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                      alert.setTitle("提示");
                      alert.setMessage("您需要设置存储权限才能使用该功能");
                      alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              CommonUtils.getAppDetailSettingIntent(getContext());
                          }
                      });
                      alert.show();

                  }
                  break;
              case 2:
                  if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                      showTelDialog();
                      //用户授权成功
                  }else {

                      //用户没有授权
                      AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                      alert.setTitle("提示");
                      alert.setMessage("您需要设置打电话权限才能使用该功能");
                      alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              CommonUtils.getAppDetailSettingIntent(getContext());
                          }
                      });
                      alert.show();

                  }
                  break;
          }

    }

    /**
     * 显示个人信息
     */
    private void showInfo(){
        LoginUserInfoBean.UserBean model = SharedPreferencesManager.getLoginInfo(getContext());
        userName.setText(model.getNickName());
        Log.d("model.getNickName()", "showInfo: "+model.getNickName());
        Log.d("model.getCustomerSign()", "showInfo: "+model.getCustomerSign());
        //不登陆不显示头像
        if(SharedPreferencesManager.isLogin(getContext())){
            //    获取用户头像
           /* ImageOptions imageOptions = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                     .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                     .setRadius(DensityUtil.dip2px(80))
                     .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                    .build();*/
           //x.image().bind(myImageView,model.getHeadImg(),imageOptions);
            Uri uri =  Uri.parse(model.getHeadImg());
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            myImageView.setController(controller);
            String customerSign = model.getCustomerSign();
            if(TextUtils.isEmpty(customerSign)){
                tvSign.setText("暂无签名");
            }else{
                tvSign.setText(model.getCustomerSign());
            }
        }else {
            myImageView.setImageResource(R.drawable.my_portrait);
            userName.setText("点击登录");
            tvSign.setText("");
            tvSign.setText("暂无签名");
        }
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

                //UMWeb umWeb = new UMWeb(getString(R.string.shareDownLoadUrl));
                UMWeb umWeb = new UMWeb(HttpUrlUtils.DOWNLOAD_URL);
                umWeb.setTitle("赛事尽在佰家赛事App，下载佰家赛事，随时随地了解赛事信息，查询、报名全程无忧。");
                umWeb.setDescription("佰家赛事");
                UMImage image = new UMImage(getActivity(),R.mipmap.logo);

                image.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
                image.compressFormat = Bitmap.CompressFormat.JPEG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                umWeb.setThumb(image);

                new ShareAction(getActivity()).withText("赛事尽在佰家赛事App，下载佰家赛事，随时随地了解赛事信息，查询、报名全程无忧。")
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

    private MyBroadcastReceiver receiver ;
    /**
     * 注册广播
     */
    private void register(){
        receiver = new MyBroadcastReceiver();
        IntentFilter filter  = new IntentFilter(ACTION);
        getActivity().registerReceiver(receiver,filter);

    }
    /**
     * 注销广播
     */
    public void unRegister(){
        getActivity().unregisterReceiver(receiver);

    }
    /**
     * 用于监听登录信息变话化的广播
     */
    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            showInfo();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegister();
    }
}
