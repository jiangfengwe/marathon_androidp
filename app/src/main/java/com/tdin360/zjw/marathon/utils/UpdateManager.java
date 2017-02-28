package com.tdin360.zjw.marathon.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
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

    private Context context;

    private boolean isAutoCheck;

    /**
     * @param isAutoCheck 是否检查自动更新
     * @param context
     *
     */
    public UpdateManager(Context context,boolean isAutoCheck){

        this.context=context;
        this.isAutoCheck=isAutoCheck;
    }


    /**
     * 检查更新
     *
     */
    public void checkVersion(){


        Log.d("---->>", "checkVersion: ");
        /**
         * 如果网络不可用就不检查
         */
        if(!NetWorkUtils.isNetworkAvailable(context)){

            if(!isAutoCheck) {
                Toast.makeText(context, "当前网络不可用!", Toast.LENGTH_SHORT).show();

            }
            return;

        }

        RequestParams params = new RequestParams(HttpUrlUtils.GET_MYINFO);


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //获取服务器版本

                isUpdateVersion(1);

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
    }

    /**
     * 检查是否有新版本
     */
    private void isUpdateVersion(int serverVersion){

        //获取当前版本
        int currentVersion = getVersion();


        if(serverVersion<=0|currentVersion<=0){

            return;
        }

        //发现新版本
        if(serverVersion>currentVersion){

            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            alert.setTitle("版本更新");
            alert.setMessage("发现新版本，是否下载更新?");
            alert.setCancelable(false);

            alert.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //更新版本
                    Toast.makeText(context, "已在后台下载安装包...", Toast.LENGTH_SHORT).show();
                    //启动下载器下载安装包
                    context.startService(new Intent(context,DownloadService.class));
                }
            });

            alert.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();

            //已是最新版本
        }else if((serverVersion==currentVersion) && !isAutoCheck){

            AlertDialog.Builder alert = new AlertDialog.Builder(context);

            alert.setTitle("版本检查");
            alert.setMessage("当前已是最新版本!");
            alert.setCancelable(false);

            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });

            alert.show();
        }

    }
    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    private int getVersion() {
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
    public String getVersionName() {
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
