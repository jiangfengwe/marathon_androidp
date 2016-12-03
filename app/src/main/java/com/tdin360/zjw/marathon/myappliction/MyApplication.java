package com.tdin360.zjw.marathon.myappliction;

import android.app.Application;


import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(false);
        //极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);


    }
}
