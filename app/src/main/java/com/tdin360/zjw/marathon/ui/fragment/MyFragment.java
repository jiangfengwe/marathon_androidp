package com.tdin360.zjw.marathon.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.ui.activity.AboutUsActivity;
import com.tdin360.zjw.marathon.ui.activity.MyGoodsListActivity;
import com.tdin360.zjw.marathon.ui.activity.MyAchievementListActivity;
import com.tdin360.zjw.marathon.ui.activity.LoginActivity;
import com.tdin360.zjw.marathon.ui.activity.MyInfoActivity;
import com.tdin360.zjw.marathon.ui.activity.MySignUpListActivity;
import com.tdin360.zjw.marathon.ui.activity.MyNoticeMessageActivity;
import com.tdin360.zjw.marathon.ui.activity.MyTeamListActivity;
import com.tdin360.zjw.marathon.ui.activity.SettingActivity;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NavType;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**个人中心
 * Created by Administrator on 2016/8/9.
 */
public class MyFragment extends Fragment {

    public static final String ACTION="LOGIN_STATUS";//广播action

    private  TextView userName;
    private ImageView myImageView;


    public static MyFragment newInstance(){

        return   new MyFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fargment_my,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName  = (TextView) view.findViewById(R.id.userName);
        myImageView = (ImageView) view.findViewById(R.id.myImage);

        //显示信息
        showInfo();
        userName.setText(SharedPreferencesManager.getLoginInfo(getContext()).getName());

        //注册广播
         register();

         view.findViewById(R.id.myHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //判断用户是否登录
                if(SharedPreferencesManager.isLogin(getContext())){

                    //若登录则显示用户信息
                    toMyInfo();

                }else {
                    //用户没有登录则跳转到登录
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    LoginNavigationConfig.instance().setNavType(NavType.Other);

                }



            }
        });

      //我的报名
       view.findViewById(R.id.signUpSearch).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(SharedPreferencesManager.isLogin(getContext())){

                   Intent intent = new Intent(getContext(), MySignUpListActivity.class);
                   startActivity(intent);
               }else {
                   Intent intent = new Intent(getContext(), LoginActivity.class);
                   startActivity(intent);
                   //设置登录成功后跳转到我的报名界面
                   LoginNavigationConfig.instance().setNavType(NavType.MySignUp);
               }


           }
       });
        //我的成绩
        view.findViewById(R.id.search_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SharedPreferencesManager.isLogin(getContext())){

                    Intent intent = new Intent(getContext(), MyAchievementListActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    //设置登录成功后跳转到物资界面
                    LoginNavigationConfig.instance().setNavType(NavType.MyMark);
                }


            }
        });
        /**
         * 我的物资
         */
        view.findViewById(R.id.myGoods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SharedPreferencesManager.isLogin(getContext())){

                    Intent intent = new Intent(getContext(), MyGoodsListActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    //设置登录成功后跳转到报名界面
                    LoginNavigationConfig.instance().setNavType(NavType.MyGoods);
                }



            }
        });

        /**
         * 我的团队
         */
        view.findViewById(R.id.myTeam).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Toast.makeText(getActivity(),"开发中敬请期待",Toast.LENGTH_SHORT).show();
//
//               Intent intent = new Intent(getContext(), MyTeamListActivity.class);
//                  startActivity(intent);
//               if(SharedPreferencesManager.isLogin(getContext())){
//
//                   Intent intent = new Intent(getContext(), MyGoodsListActivity.class);
//                   startActivity(intent);
//               }else {
//                   Intent intent = new Intent(getContext(), LoginActivity.class);
//                   startActivity(intent);
//                   //设置登录成功后跳转到报名界面
//                   LoginNavigationConfig.instance().setNavType(NavType.MyGoods);
//               }

           }
       });

        /**
         * 通知消息
         */
        view.findViewById(R.id.myNotice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MyNoticeMessageActivity.class);
                startActivity(intent);
            }
        });
      //关于我们
        view.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

       //系统设置
        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 显示个人信息
     */
    private void showInfo(){
        LoginModel model = SharedPreferencesManager.getLoginInfo(getContext());
        userName.setText(model.getName());
        //不登陆不显示头像
        if(SharedPreferencesManager.isLogin(getContext())){

            //    获取用户头像
            ImageOptions imageOptions = new ImageOptions.Builder()
                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
//                     .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                     .setRadius(DensityUtil.dip2px(80))
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//                    .setLoadingDrawableId(R.drawable.signup_photo)//加载中默认显示图片
                    .setUseMemCache(true)//设置使用缓存
                    .setFailureDrawableId(R.drawable.signup_photo)//加载失败后默认显示图片
                    .build();
            x.image().bind(myImageView,model.getImageUrl(),imageOptions);


        }else {
            myImageView.setImageResource(R.drawable.signup_photo);
        }

    }

//    跳转到我的信息界面
    private void toMyInfo(){

        Intent intent = new Intent(getContext(), MyInfoActivity.class);
        startActivity(intent);

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

        unRegister();
    }
}
