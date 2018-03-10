package com.tdin360.zjw.marathon.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CommonAdapter;
import com.tdin360.zjw.marathon.adapter.EventTabLayoutAdapter;
import com.tdin360.zjw.marathon.adapter.ViewHolder;
import com.tdin360.zjw.marathon.model.EventModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.ui.activity.LoginActivity;
import com.tdin360.zjw.marathon.ui.activity.PublishActivity;
import com.tdin360.zjw.marathon.ui.activity.SearchActivity;
import com.tdin360.zjw.marathon.ui.activity.ZxingActivity;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;
import com.tdin360.zjw.marathon.utils.db.impl.EventServiceImpl;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**赛事列表
 * Created by Administrator on 2016/8/9.
 */
public class EventFragment extends BaseFragment implements View.OnClickListener {
    //implements PullToRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,UpdateManager.UpdateListener

    private List<EventModel> list = new ArrayList<>();
  /*  @ViewInject(R.id.listView)
    private ListView listView;*/
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    //private EventAdapter marathonListViewAdapter;
    private int pageNumber=1;
    private int pageCount;
    private EventServiceImpl impl;
    /*@ViewInject(R.id.pull_Layout)
    private PullToRefreshLayout pullToRefreshLayout;*/
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

    @ViewInject(R.id.tabs_event)
    private TabLayout tabLayout;
    @ViewInject(R.id.vp_event)
    private ViewPager viewPagerEvent;
    public static EventFragment newInstance(){

        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return  inflater.inflate(R.layout.fragment_event,container,false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         this.impl  = new EventServiceImpl(getContext());
        initView();

    }

    private void initView() {
        //首页toolbar
        this.mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                if(mode== ErrorView.ViewShowMode.NOT_NETWORK){

                   //pullToRefreshLayout.autoRefresh();
                }
            }
        });

        titleTv.setText("赛事");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.qr_code);
        imageView.setOnClickListener(this);

        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        this.rightImage.setImageResource(R.drawable.search_big);
        this.rightImage.setVisibility(View.VISIBLE);
        //搜索
        this.rightImage.setOnClickListener(this);
        //赛事状态tablayout
        viewPagerEvent.setAdapter(new EventTabLayoutAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPagerEvent);
        viewPagerEvent.setOffscreenPageLimit(1);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_Back:
                //二维码扫描
                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getActivity());
                String customerId = loginInfo.getId();
                if(TextUtils.isEmpty(customerId)){
                    intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                        intent=new Intent(getActivity(), ZxingActivity.class);
                        startActivity(intent);
                    }else {
                        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},90);
                        }else {
                            intent=new Intent(getActivity(), ZxingActivity.class);
                            startActivity(intent);
                        }
                    }
                }


                break;
            case R.id.navRightItemImage:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 90:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                  Intent  intent=new Intent(getActivity(), ZxingActivity.class);
                    startActivity(intent);
                    //用户授权成功
                }else {
                    //用户没有授权
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    alert.setTitle("提示");
                    alert.setMessage("你需要设置摄像头权限才可以使用该功能");
                    alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //CommonUtils.getAppDetailSettingIntent(getContext());
                            getAppDetailSettingIntent(getActivity());
                        }
                    });
                    alert.show();

                }
                break;
            default:
                break;
        }
    }
    /**
     * 设置权限界面
     * @param context
     */
    public  void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        context.startActivity(localIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
