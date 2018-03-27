package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.HotelListBean;
import com.tdin360.zjw.marathon.ui.fragment.HotelFragment;
import com.tdin360.zjw.marathon.ui.fragment.HotelOrderFragment;
import com.tdin360.zjw.marathon.ui.fragment.TravelFragment;
import com.tdin360.zjw.marathon.ui.fragment.TravelOrderFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店模块
 */
public class HotelActivity extends BaseActivity {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.hotel_back)
    private Toolbar toolbarBack;
    @ViewInject(R.id.iv_hotel_back)
    private ImageView imageViewBack;
    @ViewInject(R.id.tv_hotel_title)
    private TextView tvTitle;
    @ViewInject(R.id.cb_hotel)
    private CheckBox checkBoxHot;
    @ViewInject(R.id.view_bg)
    private View viewBg;


    @ViewInject(R.id.iv_hotel_tab_back)
    private ImageView ivTabBack;
    @ViewInject(R.id.rg_hotel)
    private RadioGroup rgHotel;
    @ViewInject(R.id.rb_hotel)
    private RadioButton rbHotelOrder;
    @ViewInject(R.id.rb_travel)
    private RadioButton rbTravelOrder;

    public static HotelActivity instance;
    public HotelActivity() {
        instance=this;
        // Required empty public constructor
    }

    public void finishActivity(){
        finish();
    }
    private HotelFragment hotelFragment;//酒店fragment
    private TravelFragment travelFragment;//旅游fragment
    private String hotelFragmentTag="hotelFragment";
    private String travelFragmentTag="travelFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickCheck();
        initTab();
        if(savedInstanceState!=null){
            hotelFragment = (HotelFragment) getSupportFragmentManager().findFragmentByTag(hotelFragmentTag);
            travelFragment = (TravelFragment) getSupportFragmentManager().findFragmentByTag(travelFragmentTag);
        }
        this.rgHotel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //通过事物来切换fragment
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                hideFragment(fragmentTransaction);
                switch (group.getCheckedRadioButtonId()){
                    case  R.id.rb_hotel:
                        rbHotelOrder.setBackgroundResource(R.color.white);
                        rbHotelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
                        rbTravelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
                        rbTravelOrder.setTextColor(getResources().getColor(R.color.white));
                        if(hotelFragment==null){
                            hotelFragment=new HotelFragment();
                            fragmentTransaction.add(R.id.layout_hotel,hotelFragment,hotelFragmentTag);
                        }else{
                            fragmentTransaction.show(hotelFragment);
                        }
                        break;
                    case  R.id.rb_travel:
                        rbTravelOrder.setBackgroundResource(R.color.white);
                        rbTravelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
                        rbHotelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
                        rbHotelOrder.setTextColor(getResources().getColor(R.color.white));
                        if(travelFragment==null){
                            travelFragment=new TravelFragment();
                            fragmentTransaction.add(R.id.layout_hotel,travelFragment,travelFragmentTag);
                        }else{
                            fragmentTransaction.show(travelFragment);
                        }
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }
    private void clickCheck() {
        //根据发现fragment点击的不同而选中不同的fragment
        Intent intent=getIntent();
        //通过事物来切换fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(intent.getStringExtra("webclick").equals("1")){
            rgHotel.check(R.id.rb_hotel);
            rbHotelOrder.setBackgroundResource(R.color.white);
            rbHotelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
            rbTravelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
            rbTravelOrder.setTextColor(getResources().getColor(R.color.white));
            if(hotelFragment==null){
                hotelFragment=new HotelFragment();
                fragmentTransaction.add(R.id.layout_hotel,hotelFragment,hotelFragmentTag);
            }else{
                fragmentTransaction.show(hotelFragment);
            }
            fragmentTransaction.commit();
        }
        if(intent.getStringExtra("webclick").equals("2")){
            rgHotel.check(R.id.rb_travel);
            rbTravelOrder.setBackgroundResource(R.color.white);
            rbTravelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
            rbHotelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
            rbHotelOrder.setTextColor(getResources().getColor(R.color.white));
            if(travelFragment==null){
                travelFragment=new TravelFragment();
                fragmentTransaction.add(R.id.layout_hotel,travelFragment,travelFragmentTag);
            }else{
                fragmentTransaction.show(travelFragment);
            }
            fragmentTransaction.commit();
        }

    }
    private  void hideFragment(FragmentTransaction fragmentTransaction){
        if(hotelFragment!=null&&hotelFragment.isAdded()){
            fragmentTransaction.hide(hotelFragment);
        }
        if(travelFragment!=null&&travelFragment.isAdded()){
            fragmentTransaction.hide(travelFragment);
        }

    }
    private void initTab() {
        ivTabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_hotel;
    }
}
