package com.tdin360.zjw.marathon.app;

import android.app.Application;
import android.os.StrictMode;


import com.squareup.leakcanary.LeakCanary;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeServiceImpl;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/8/10.
 */
public class App extends Application {

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

        Config.DEBUG=false;
        UMShareAPI.get(this);

        //创建数据库
        new NoticeServiceImpl(this);


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
        // Normal app init code...


    }
    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }

//    分享平台配置
    {

        PlatformConfig.setWeixin("wx56d6dadff22bbf5e","47211ce7c72da9b01ace9b47ba5d9dfa");
        PlatformConfig.setQQZone("1105925323","q4m5hraExG9wAfe7");
        PlatformConfig.setSinaWeibo("2316733117", "a4bdff71157545c0366244049d37d218","https://api.weibo.com/oauth2/default.html");

    }
}
