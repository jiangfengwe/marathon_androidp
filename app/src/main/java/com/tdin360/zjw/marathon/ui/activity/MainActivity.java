package com.tdin360.zjw.marathon.ui.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.jiguan.ExampleUtil;
import com.tdin360.zjw.marathon.ui.fragment.CircleFragment;
import com.tdin360.zjw.marathon.ui.fragment.EventFragment;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.umeng.socialize.UMShareAPI;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 程序主界面
 * @author zhangzhijun
 */
public class MainActivity extends AppCompatActivity{

    private static final String TAB1="tab1";
    private static final String TAB2="tab2";
    private static  final String TAB4="tab4";
    private EventFragment marathonFragment;//赛事首页
    private CircleFragment circleFragment;//圈子
    private MyFragment personalCenterFragment;//我的
    private RadioGroup radioGroup;

   // private String s;
   private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                   // Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    //Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            Log.d("code", "gotResult: "+code);
            switch (code) {
                case 0:
                    ToastUtils.showCenter(getApplicationContext(),"codeeeee");
                    logs = "Set tag and alias success";
                   // Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                   // Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    //Log.e(TAG, logs);
            }
            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String alias = SharedPreferencesManager.getAlias(getApplicationContext());
        Log.d("alias", "onSuccess: "+alias);
        if(TextUtils.isEmpty(alias)){
            ToastUtils.showCenter(getApplicationContext(),"alias为空");
            if(savedInstanceState!=null){
                this.marathonFragment= (EventFragment) this.getSupportFragmentManager().findFragmentByTag(TAB1);
                this.circleFragment = (CircleFragment) this.getSupportFragmentManager().findFragmentByTag(TAB2);
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
            return;
        }else {
           // ToastUtils.showCenter(getApplicationContext(),"alias");
            // 调用 Handler 来异步设置别名
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));

        }
        //System.out.println(s.equals("any string"));
//        startActivity(new Intent(this,WebActivity.class));

          //防止旋转屏幕时重叠
          if(savedInstanceState!=null){
              this.marathonFragment= (EventFragment) this.getSupportFragmentManager().findFragmentByTag(TAB1);
              this.circleFragment = (CircleFragment) this.getSupportFragmentManager().findFragmentByTag(TAB2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    }
    //切换时隐藏界面
    private void hide( FragmentTransaction fragmentTransaction){
        if(marathonFragment!=null&&marathonFragment.isAdded()){
            fragmentTransaction.hide(marathonFragment);
        }
        if(circleFragment!=null&&circleFragment.isAdded()){
            fragmentTransaction.hide(circleFragment);
        }

        if(personalCenterFragment!=null&&personalCenterFragment.isAdded()){
            fragmentTransaction.hide(personalCenterFragment);
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
