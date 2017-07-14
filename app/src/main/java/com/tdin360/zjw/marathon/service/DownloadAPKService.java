package com.tdin360.zjw.marathon.service;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;

import java.io.File;
import java.io.IOException;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 更新下载器
 */
public class DownloadAPKService extends Service {

    public DownloadAPKService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        register();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String url = intent.getStringExtra("url");
        downloadTask(url);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 下载任务
     */
    private void downloadTask(String url) {


        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //设置下载网络环境
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //显示下载通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置下载路径

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "baijar.apk");
        //显示下载界面
        request.setVisibleInDownloadsUi(true);
        // 设置一些基本显示信息
        request.setTitle("安装包更新");
        request.setDescription("正在下载安装包");
        request.setMimeType("application/vnd.android.package-archive");

         dm.enqueue(request);


    }

    /**
     * 注册广播监听
     */
    private void register(){


        receiver = new DownloadReceiver();

        IntentFilter filter  = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        registerReceiver(receiver,filter);

    }

    private DownloadReceiver receiver;
    /**
     * 用来监听更新下载情况的广播
     */

    private class DownloadReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent == null) {

                return;
            }
            //下载完成启动安装
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (longExtra != -1) {


                    installApk(context, longExtra);
                }
            }
        }

        //获取下载文件
        public File queryDownloadedApk(Context context, long id) {
            File targetApkFile = null;
            if (id != -1) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
                DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
                Cursor cur = manager.query(query);
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                        targetApkFile = new File(Uri.parse(uriString).getPath());

                    }

                    cur.close();
                }
            }
            return targetApkFile;

        }

        /**
         * 安装apk
         */
        private void installApk(Context context, long downloadApkId) {


            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(queryDownloadedApk(context, downloadApkId)), "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);

            // 如果不加上这句的话在apk安装完成之后点击打开会崩溃
            //android.os.Process.killProcess(android.os.Process.myPid());
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        //注销广播
        if(receiver!=null){

            unregisterReceiver(receiver);
        }
    }

}