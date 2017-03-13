package com.tdin360.zjw.marathon.utils;

import android.util.Log;

import java.io.File;

/**
 * 缓存管理
 * Created by admin on 17/3/13.
 */

public class CacheFileManager {


    private static long size;
    public static String getCacheSize(String path){


        File file = new File(path);

        if(!file.exists()||!file.isDirectory()){


            return "0kb";

        }

        File[] files = file.listFiles();

        for (File f:files
             ) {

            if(f.isFile()){

                size += f.length();


            }else {

               getCacheSize(f.getPath());
            }


        }
        Log.d("file-------", "getCacheSize: "+ size);
       return formatFileSize(size);

    }


    /**
     * 清除缓存
     * @param path
     * @return
     */
    public static void clearCache(String path){

        File file = new File(path);

        if(file.exists()){

            File[] files = file.listFiles();

            for (File f:files
                 ) {
                if(f.isFile()){
                    Log.d("file----delete---", "getCacheSize: "+f.getName());
                    f.delete();
                }else {

                    clearCache(f.getPath());
                }
            }


        }


    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
