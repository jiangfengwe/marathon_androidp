package com.tdin360.zjw.marathon.utils;

import android.app.DownloadManager;
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

import java.io.File;
import java.io.IOException;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 更新下载器
 */
public class DownloadService extends Service {
    public DownloadService() {
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


          downloadTask();


        return super.onStartCommand(intent, flags, startId);
    }


    /**
     *
     * 下载任务
     */
    private void downloadTask(){


        DownloadManager  dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request =  new DownloadManager.Request(Uri.parse(HttpUrlUtils.DOWNLOAD_URL));

        //设置下载网络环境
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //显示下载通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置下载路径

         request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"update.apk");
        //显示下载界面
        request.setVisibleInDownloadsUi(true);
        // 设置一些基本显示信息
        request.setTitle("安装包更新");
        request.setDescription("apk下载中");
        request.setMimeType("application/vnd.android.package-archive");


        dm.enqueue(request);


    }





    }
