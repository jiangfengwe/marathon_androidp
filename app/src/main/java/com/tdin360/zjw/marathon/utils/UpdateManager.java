package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 *
 * 检查版本更新
 * Created by admin on 17/2/25.
 * @author zhangzhijun
 */

public class UpdateManager {


    private static boolean isUpdate;
    /**
     * 检查更新
     *
     */
    public static boolean checkVersion(final Context context){


        /**
         * 如果网络不可用就不检查
         */
        if(!NetWorkUtils.isNetworkAvailable(context)){

            return isUpdate=false;

        }

        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_HOME);


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //获取服务器版本

                int serverVersion=1;
                //获取当前版本
                int currentVersion = getVersion(context);


                //检查到新版本
                if(serverVersion<=0|currentVersion<=0){

                    return;
                }

                if(serverVersion>currentVersion){

                    isUpdate=true;
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        return isUpdate;
    }

    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    private static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName ;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
