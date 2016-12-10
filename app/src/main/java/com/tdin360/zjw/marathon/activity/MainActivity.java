package com.tdin360.zjw.marathon.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.fragment.MarathonFragment;
import com.tdin360.zjw.marathon.fragment.PersonalCenterFragment;
import com.tdin360.zjw.marathon.jiguan.ExampleUtil;

import org.xutils.x;

public class MainActivity extends FragmentActivity{

    public static boolean isForeground = false;
    private static final String TAB1="tab1";
    private static final String TAB2="tab2";
    private MarathonFragment marathonFragment;
    private PersonalCenterFragment personalCenterFragment;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         registerMessageReceiver();
          //防止旋转屏幕时重叠
          if(savedInstanceState!=null){
              marathonFragment= (MarathonFragment) this.getSupportFragmentManager().findFragmentByTag(TAB1);
              personalCenterFragment= (PersonalCenterFragment) this.getSupportFragmentManager().findFragmentByTag(TAB2);
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
                        marathonFragment =  MarathonFragment.newInstance();
                        fragmentTransaction.add(R.id.center,marathonFragment,TAB1);
                    }else {
                        fragmentTransaction.show(marathonFragment);
                    }

                    break;
                case R.id.tab2:
                     if(personalCenterFragment==null){
                         personalCenterFragment= PersonalCenterFragment.newInstance();
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

    //极光推送(自定义通知内容接收)
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    //接收极光推送自定义消息
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }

               Toast.makeText(x.app(),showMsg.toString(),Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        isForeground=true;
        super.onResume();

    }

    @Override
    protected void onPause() {
        isForeground=false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
