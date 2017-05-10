package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
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
    public interface UpdateListener{


       void checkFinished(boolean isUpdate,String content,String url);

    }

    private static boolean isUpdate;
    private static UpdateListener listener;
    private static String url;
    private static String content;

    public static void setUpdateListener(UpdateListener listener1){

          listener=listener1;
    }
    /**
     * 检查更新
     *
     */
    public static void checkNewVersion(final Context context){


        /**
         * 如果网络不可用就不检查
         */
        if(!NetWorkUtils.isNetworkAvailable(context)){

            return;

        }

        RequestParams params = new RequestParams(HttpUrlUtils.UPDATE_URL);


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //获取服务器版本

                try {
                    JSONObject obj = new JSONObject(result);
                     url = obj.getString("AndoroidUrl");
                    content = obj.getString("UpdateContent");
                    int serverVersion=obj.getInt("Version");
                    //获取当前版本
                    int currentVersion = getVersion(context);



                    if(serverVersion<=0|currentVersion<=0){

                        return;
                    }
                  //检查到新版本
                    if(serverVersion>currentVersion){


                        isUpdate=true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

                if(listener!=null){

                  listener.checkFinished(isUpdate,content,url);

                }
            }
        });


    }

    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    public static int getVersion(Context context) {
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
