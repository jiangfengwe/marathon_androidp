package com.tdin360.zjw.marathon.utils;

/**
 * Created by admin on 17/2/25.
 */

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * 用来监听更新下载情况的广播
 */

public class DownloadReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        //下载完成启动安装
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){

            long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if(longExtra!=-1) {
                installApk(context, longExtra);
            }
        }
    }

    //获取下载文件
    public File queryDownloadedApk(Context context,long id) {
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
    private void installApk(Context context,long downloadApkId) {


                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(queryDownloadedApk(context,downloadApkId)), "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);

           // 如果不加上这句的话在apk安装完成之后点击打开会崩溃
             android.os.Process.killProcess(android.os.Process.myPid());
    }


}

