package com.tdin360.zjw.marathon.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.HotelFragment;
import com.tdin360.zjw.marathon.ui.fragment.MyHotelOrderFragment;

/**
 * 酒店模块
 */
public class HotelActivity extends AppCompatActivity {


    private static final String TAG1="tag1";
    private static final String TAG2="tag2";

    private RadioGroup group;
    private HotelFragment listFragment;
    private MyHotelOrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        if (savedInstanceState != null) {

            this.listFragment = (HotelFragment) getSupportFragmentManager().findFragmentByTag(TAG1);
            this.orderFragment = (MyHotelOrderFragment) getSupportFragmentManager().findFragmentByTag(TAG2);

        }

        this.group = (RadioGroup) this.findViewById(R.id.radioGroup);
        this.group.setOnCheckedChangeListener(new MyListener());
        this.group.check(R.id.tab_hotel);
    }


    /**
     *  返回
     */
    public void back(View view) {

        finish();
    }


    /**
     * 点击切换顶部菜单
     */
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


    /**
     * 隐藏fragment
     * @param beginTransaction
     */
    private void hide( FragmentTransaction beginTransaction){

        if(listFragment!=null&&listFragment.isAdded()){

            beginTransaction.hide(listFragment);
        }

        if(orderFragment!=null&&orderFragment.isAdded()){

            beginTransaction.hide(orderFragment);
        }

    }
}
