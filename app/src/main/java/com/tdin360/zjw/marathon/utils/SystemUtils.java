package com.tdin360.zjw.marathon.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tdin360.zjw.marathon.ui.activity.MyNoticeMessageActivity;

import java.util.List;

/**
 * Created by admin on 17/2/13.
 */

public class SystemUtils {

    /**
     * 用于处理通知消息
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
              //  Log.i("NotificationLaunch",
                      //  String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
      //  Log.i("NotificationLaunch",
            //    String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    //启动显示通知消息
    public static void startNoticeMessageActivity(Context context, Bundle bundle
                                            ){
        Intent intent = new Intent(context, MyNoticeMessageActivity.class);
         intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
