package com.tdin360.zjw.marathon.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.HotelOrderFragment;
import com.tdin360.zjw.marathon.ui.fragment.TravelOrderFragment;

import org.xutils.view.annotation.ViewInject;
/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity {
    @ViewInject(R.id.iv_my_order_back)
    private ImageView ivBack;
    @ViewInject(R.id.rg_order)
    private RadioGroup rgOrder;
    @ViewInject(R.id.rb_hotel_order)
    private RadioButton rbHotelOrder;
    @ViewInject(R.id.rb_travel_order)
    private RadioButton rbTravelOrder;
    private HotelOrderFragment hotelOrderFragment;//酒店订单
    private TravelOrderFragment travelOrderFragment;//旅游订单
    private String hotelOrderFragmentTag="hotelOrderFragment";
    private String travelOrderFragmentTag="travelOrderFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            hotelOrderFragment= (HotelOrderFragment) getSupportFragmentManager().findFragmentByTag(hotelOrderFragmentTag);
            travelOrderFragment= (TravelOrderFragment) getSupportFragmentManager().findFragmentByTag(travelOrderFragmentTag);
        }
        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //通过事物来切换fragment
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                hideFragment(fragmentTransaction);
                switch (group.getCheckedRadioButtonId()){
                    case  R.id.rb_hotel_order:
                        rbHotelOrder.setBackgroundResource(R.color.white);
                        rbHotelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
                        rbTravelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
                        rbTravelOrder.setTextColor(getResources().getColor(R.color.white));
                        if(hotelOrderFragment==null){
                            hotelOrderFragment=new HotelOrderFragment();
                            fragmentTransaction.add(R.id.layout_order,hotelOrderFragment,hotelOrderFragmentTag);
                        }else{
                            fragmentTransaction.show(hotelOrderFragment);
                        }
                        break;
                    case  R.id.rb_travel_order:
                        rbTravelOrder.setBackgroundResource(R.color.white);
                        rbTravelOrder.setTextColor(getResources().getColor(R.color.home_tab_title_color_check));
                        rbHotelOrder.setBackgroundResource(R.color.home_tab_title_color_check);
                        rbHotelOrder.setTextColor(getResources().getColor(R.color.white));
                        if(travelOrderFragment==null){
                            travelOrderFragment=new TravelOrderFragment();
                            fragmentTransaction.add(R.id.layout_order,travelOrderFragment,travelOrderFragmentTag);
                        }else{
                            fragmentTransaction.show(travelOrderFragment);
                        }
                        break;

                }
                fragmentTransaction.commit();
            }
        });
        rgOrder.check(R.id.rb_hotel_order);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private  void hideFragment(FragmentTransaction fragmentTransaction){
        if(hotelOrderFragment!=null&&hotelOrderFragment.isAdded()){
            fragmentTransaction.hide(hotelOrderFragment);
        }
        if(travelOrderFragment!=null&&travelOrderFragment.isAdded()){
            fragmentTransaction.hide(travelOrderFragment);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_order;
    }
}
