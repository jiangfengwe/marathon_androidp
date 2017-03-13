package com.tdin360.zjw.marathon.utils.db;

import android.content.Context;

/**
 * 创建数据库表
 * Created by admin on 17/3/12.
 */

public class DbManager {

    public static void init(Context context){
        new SQLHelper(context);
    }
}
