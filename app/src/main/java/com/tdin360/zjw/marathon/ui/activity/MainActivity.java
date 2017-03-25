package com.tdin360.zjw.marathon.ui.activity;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.fragment.CircleFragment;
import com.tdin360.zjw.marathon.ui.fragment.EventFragment;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.ui.fragment.StepFragment;

/**
 * 程序主界面
 * @author zhangzhijun
 */
public class MainActivity extends FragmentActivity{

    private static final String TAB1="tab1";
    private static final String TAB2="tab2";
    private static  final String TAB3="tab3";
    private static  final String TAB4="tab4";
    private EventFragment marathonFragment;//赛事首页
    private CircleFragment circleFragment;//圈子
    private StepFragment stepFragment;//运动
    private MyFragment personalCenterFragment;//我的
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //防止旋转屏幕时重叠
          if(savedInstanceState!=null){
              this.marathonFragment= (EventFragment) this.getSupportFragmentManager().findFragmentByTag(TAB1);
            //  this.circleFragment = (CircleFragment) this.getSupportFragmentManager().findFragmentByTag(TAB2);
              this.stepFragment = (StepFragment) this.getSupportFragmentManager().findFragmentByTag(TAB3);
              this.personalCenterFragment= (MyFragment) this.getSupportFragmentManager().findFragmentByTag(TAB4);
          }
        /**
         * 初始化tab组
         */
          this.radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
//          添加tab选择事件
          this.radioGroup.setOnCheckedChangeListener(new MyCheckedItemListener());
//        默认选中第一个tab
          this.radioGroup.check(R.id.tab1);
    }


    /**
     * 点击tab切换界面
     */
    private class MyCheckedItemListener implements RadioGroup.OnCheckedChangeListener{


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
            hide(fragmentTransaction);
            switch (checkedId){

                case R.id.tab1:
                    if(marathonFragment==null){
                        marathonFragment =  EventFragment.newInstance();
                        fragmentTransaction.add(R.id.center,marathonFragment,TAB1);
                    }else {
                        fragmentTransaction.show(marathonFragment);
                    }

                    break;
                case R.id.tab2:
                    if(circleFragment==null){
                        circleFragment =  CircleFragment.newInstance();
                        fragmentTransaction.add(R.id.center,circleFragment,TAB2);
                    }else {
                        fragmentTransaction.show(circleFragment);
                    }

                    break;
                case R.id.tab3:
                    if(stepFragment==null){
                        stepFragment =  StepFragment.newInstance();
                        fragmentTransaction.add(R.id.center,stepFragment,TAB3);
                    }else {
                        fragmentTransaction.show(stepFragment);
                    }

                    break;
                case R.id.tab4:
                     if(personalCenterFragment==null){
                         personalCenterFragment= MyFragment.newInstance();
                         fragmentTransaction.add(R.id.center,personalCenterFragment,TAB4);

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
            if(circleFragment!=null&&circleFragment.isAdded()){
                fragmentTransaction.hide(circleFragment);
            }
            if(stepFragment!=null&&stepFragment.isAdded()){
                fragmentTransaction.hide(stepFragment);
            }
            if(personalCenterFragment!=null&&personalCenterFragment.isAdded()){
                fragmentTransaction.hide(personalCenterFragment);
            }
        }
    }


    //连续按两次退出

    private int clickCount=0;
    private int WHAT=0;

    @Override
    public void onBackPressed() {
        clickCount++;
        if(clickCount==1){

            Toast.makeText(getApplicationContext(),"再按一次退出~",Toast.LENGTH_SHORT).show();
        }else if(clickCount==2){
            handler.removeMessages(WHAT);
            finish();
        }

        handler.sendEmptyMessageDelayed(WHAT,5000);

    }

    private  Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clickCount=0;
        }
    };

}
