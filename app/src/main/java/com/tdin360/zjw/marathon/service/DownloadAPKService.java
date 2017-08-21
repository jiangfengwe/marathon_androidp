package com.tdin360.zjw.marathon.service;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 更新下载器
 */
public class DownloadAPKService extends Service {

    private long id;
    private final int CODE=0x06;
    private   DownloadManager dm;
    public DownloadAPKService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        register();

    }

    @Override
    public IBinder onBind(Intent intent) {

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


         dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //设置下载网络环境
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //显示下载通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置下载路径

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "com.baijar.www.apk");
        //显示下载界面
        request.setVisibleInDownloadsUi(true);
        // 设置一些基本显示信息
        request.setTitle("安装包更新");
        request.setDescription("正在下载安装包");
        request.setMimeType("application/vnd.android.package-archive");

          id = dm.enqueue(request);

         handler.sendEmptyMessage(CODE);

    }

    /**
     * 注册广播监听
     */
    private void register(){


        receiver = new DownloadReceiver();

        IntentFilter filter  = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        registerReceiver(receiver,filter);

    }



    private Handler handler  = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            EventBus.getDefault().post(new MessageEvent(getDownLoadingSize()[0],getDownLoadingSize()[1], MessageEvent.MessageType.DOWNLOAD_UPDATE));
            handler.sendEmptyMessageDelayed(CODE,100);
        }
    };



    //实时获取下载文件的大小
    public long[] getDownLoadingSize() {

         long[] array = new long[2];
        if (id != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
            DownloadManager manager = (DownloadManager) getBaseContext().getSystemService(getBaseContext().DOWNLOAD_SERVICE);
            Cursor cur = manager.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {

                    long currentSize = cur.getLong(cur.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    long totalSize = cur.getLong(cur.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                  array[0]=currentSize;
                  array[1]=totalSize;
                }

                cur.close();
            }
        }
        return array;

    }

    private DownloadReceiver receiver;
    /**
     * 用来监听更新下载情况的广播
     */

    private class DownloadReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {


            checkStatus();

        }

        //检查下载状态
        private void checkStatus() {

            DownloadManager.Query query = new DownloadManager.Query();
            //通过下载的id查找
            query.setFilterById(id);
            Cursor c = dm.query(query);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    //下载暂停
                    case DownloadManager.STATUS_PAUSED:
                        break;
                    //下载延迟
                    case DownloadManager.STATUS_PENDING:
                        break;
                    //正在下载
                    case DownloadManager.STATUS_RUNNING:
                        break;
                    //下载完成
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //下载完成安装APK
                        handler.removeMessages(CODE);
                    EventBus.getDefault().post(new MessageEvent(getDownLoadingSize()[0],getDownLoadingSize()[1], MessageEvent.MessageType.DOWNLOAD_UPDATE));
                        installApk();
                        break;
                    //下载失败
                    case DownloadManager.STATUS_FAILED:
                        handler.removeMessages(CODE);
                       EventBus.getDefault().post(new MessageEvent(0,0, MessageEvent.MessageType.DOWNLOAD_UPDATE));
                       ToastUtils.show(getBaseContext(),"下载失败");
                        break;
                }
            }
        }



        //获取下载文件
        public File queryDownloadedApk() {
            File targetApkFile = null;
            if (id != -1) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
                DownloadManager manager = (DownloadManager) getBaseContext().getSystemService(getBaseContext().DOWNLOAD_SERVICE);
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
        private void installApk() {

            try {

                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(queryDownloadedApk()), "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(install);
            }catch (Exception e){

                ToastUtils.show(getBaseContext(),"无法安装");
            }
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