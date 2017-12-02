package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
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
