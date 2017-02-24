package com.tdin360.zjw.marathon.ui.activity;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.Marathon_MainFragment;
import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;


public class MainActivity extends FragmentActivity{

    private static final String TAB1="tab1";
    private static final String TAB2="tab2";
    private Marathon_MainFragment marathonFragment;
    private Personal_CenterFragment personalCenterFragment;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



          //防止旋转屏幕时重叠
          if(savedInstanceState!=null){
              marathonFragment= (Marathon_MainFragment) this.getSupportFragmentManager().findFragmentByTag(TAB1);
              personalCenterFragment= (Personal_CenterFragment) this.getSupportFragmentManager().findFragmentByTag(TAB2);
          }

          this.radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
          this.radioGroup.setOnCheckedChangeListener(new MyCheckedItemListener());
            radioGroup.check(R.id.tab1);
    }

    private class MyCheckedItemListener implements RadioGroup.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
            hide(fragmentTransaction);
            switch (checkedId){

                case R.id.tab1:
                    if(marathonFragment==null){
                        marathonFragment =  Marathon_MainFragment.newInstance();
                        fragmentTransaction.add(R.id.center,marathonFragment,TAB1);
                    }else {
                        fragmentTransaction.show(marathonFragment);
                    }

                    break;
                case R.id.tab2:
                     if(personalCenterFragment==null){
                         personalCenterFragment= Personal_CenterFragment.newInstance();
                         fragmentTransaction.add(R.id.center,personalCenterFragment,TAB2);

                     }else {
                         fragmentTransaction.show(personalCenterFragment);
                     }
                    break;
            }

            fragmentTransaction.commit();
        }
        //切换时隐藏界面
        private void hide( FragmentTransaction fragmentTransaction){

            if(marathonFragment!=null&&marathonFragment.isAdded()){
                fragmentTransaction.hide(marathonFragment);
            }
            if(personalCenterFragment!=null&&personalCenterFragment.isAdded()){
                fragmentTransaction.hide(personalCenterFragment);
            }
        }
    }


    //连续按两次退出

    private int clickCount=0;

    @Override
    public void onBackPressed() {
        clickCount++;
        if(clickCount==1){

            Toast.makeText(this,"再按一次退出~",Toast.LENGTH_SHORT).show();
        }else if(clickCount==2){

            finish();
        }

        handler.sendEmptyMessageDelayed(0,5000);

    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clickCount=0;
        }
    };

}
