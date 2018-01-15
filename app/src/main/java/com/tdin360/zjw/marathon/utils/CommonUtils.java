package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by admin on 17/8/11.
 */

public class CommonUtils {
    /**
     * 根据指定格式获取当前时间字符串
     * @param format
     * @return
     */
    public static String getNowTimeByFormat(String format){

        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 根据指定的时间格式和字符串日期转换成毫秒值
     * @param time
     * @param format
     * @return
     */
    public static long getTimeByString(String time,String format){

        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
          return sf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 设置权限界面
     * @param context
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
