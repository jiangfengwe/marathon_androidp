package com.tdin360.zjw.marathon.model.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 版本管理
 * Created by Administrator on 2016/8/23.
 */
public class VersionManager {
    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无版本";
        }
    }
}
