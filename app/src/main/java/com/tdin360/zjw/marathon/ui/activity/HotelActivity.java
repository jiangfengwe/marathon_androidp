package com.tdin360.zjw.marathon.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.ui.fragment.HotelFragment;
import com.tdin360.zjw.marathon.ui.fragment.MyHotelOrderFragment;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店模块
 */
public class HotelActivity extends BaseActivity {

   /* private static final String TAG1="tag1";
    private static final String TAG2="tag2";

    private RadioGroup group;
    private HotelFragment listFragment;
    private MyHotelOrderFragment orderFragment;*/

    @ViewInject(R.id.hotel_back)
    private Toolbar toolbarBack;
    @ViewInject(R.id.iv_hotel_back)
    private ImageView imageViewBack;
    @ViewInject(R.id.tv_hotel_title)
    private TextView tvTitle;
    @ViewInject(R.id.cb_hotel)
    private CheckBox checkBoxHot;






   @ViewInject(R.id.rv_hotel)
   private RecyclerView rvHotel;
   private List<String> list=new ArrayList<>();
   private RecyclerViewBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

        /*if (savedInstanceState != null) {

            this.listFragment = (HotelFragment) getSupportFragmentManager().findFragmentByTag(TAG1);
            this.orderFragment = (MyHotelOrderFragment) getSupportFragmentManager().findFragmentByTag(TAG2);

        }

        this.group = (RadioGroup) this.findViewById(R.id.radioGroup);
        this.group.setOnCheckedChangeListener(new MyListener());
        this.group.check(R.id.tab_hotel);*/
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel;
    }

    private void initView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_hotel_rv) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

            }
        };
        rvHotel.setAdapter(adapter);
        rvHotel.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(HotelActivity.this,HotelDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initToolbar() {
        showBack(toolbarBack,imageViewBack);

        checkBoxHot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    imageViewBack.setImageResource(R.drawable.back_black);
                    toolbarBack.setBackgroundColor(Color.WHITE);
                    tvTitle.setTextColor(Color.BLACK);

                    final View popupView = HotelActivity.this.getLayoutInflater().inflate(R.layout.item_hotel_mydialog, null);
                    final PopupWindow window = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    RadioGroup rgRoomLevel = (RadioGroup) popupView.findViewById(R.id.rg_room_level);
                    RadioGroup rgPriceLevel = (RadioGroup) popupView.findViewById(R.id.rg_price_level);
                    //房型
                    rgRoomLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                        }
                    });
                    rgRoomLevel.check(R.id.rg_room_haohua);
                    //价格
                    rgPriceLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                        }
                    });
                    rgPriceLevel.check(R.id.rg_room_one);



                    // TODO: 2016/5/17 设置动画
                    // window.setAnimationStyle(R.style.popup_window_anim);
                    // TODO: 2016/5/17 设置背景颜色
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
               /* if(window.isShowing()){
                    checkBoxHot.setChecked(true);
                }else{
                    checkBoxHot.setChecked(false);
                }*/
                    // TODO: 2016/5/17 设置可以获取焦点
                    // window.setFocusable(true);
                    // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                    window.setOutsideTouchable(true);
                    //popupWindow消失是下拉图片变化
                    window.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            //点击PopupWindow以外区域时PopupWindow消失
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                checkBoxHot.setChecked(false);
                            }
                            return false;
                        }
                    });
                    // TODO：更新popupwindow的状态
                    window.update();
                    // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                    window.showAsDropDown(checkBoxHot, 0, 20);
                }else{
                    imageViewBack.setImageResource(R.drawable.back);
                    toolbarBack.setBackgroundColor(Color.parseColor("#ff621a"));
                    tvTitle.setTextColor(Color.WHITE);
                }
            }
        });
    }


    /**
     *  返回
     */
  /*  public void back(View view) {

        finish();
    }


    *//**
     * 点击切换顶部菜单
     *//*
    class MyListener implements RadioGroup.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            hide(beginTransaction);
            switch (checkedId){

                case R.id.tab_hotel:

                    if(listFragment==null){

                        listFragment = HotelFragment.newInstance();

                        beginTransaction.add(R.id.main,listFragment,TAG1);

                    }  else {

                        beginTransaction.show(listFragment);
                    }
                    break;
                case R.id.tab_my:

                    if(orderFragment==null){

                        orderFragment = MyHotelOrderFragment.newInstance();

                        beginTransaction.add(R.id.main,orderFragment,TAG2);

                    }  else {

                        beginTransaction.show(orderFragment);
                    }
                    break;
            }

            beginTransaction.commit();
        }


    }


    *//**
     * 隐藏fragment
     * @param beginTransaction
     *//*
    private void hide( FragmentTransaction beginTransaction){

        if(listFragment!=null&&listFragment.isAdded()){

            beginTransaction.hide(listFragment);
        }

        if(orderFragment!=null&&orderFragment.isAdded()){

            beginTransaction.hide(orderFragment);
        }

    }*/
}
