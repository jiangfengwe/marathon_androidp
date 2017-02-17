package com.tdin360.zjw.marathon.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类(做缓存)
 * Created by admin on 16/12/10.
 */

public class SQLHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME="marathon.db";

//    马拉松详情首页
    public static final String HOME_TABLE="home_table";

    //通知消息表
    public static final String NOTICE_TABLE="Notice";


    public SQLHelper(Context context){

        super(context,DB_NAME,null,1);

    }

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String create_Notice_Sql="CREATE TABLE  "+NOTICE_TABLE+" (\n" +
                "    id      INTEGER      NOT NULL\n" +
                "                         PRIMARY KEY AUTOINCREMENT,\n" +
                "    forName VARCHAR (20),\n" +
                "    forTime VARCHAR (20),\n" +
                "    content VARCHAR\n" +
                ");";

        db.execSQL(create_Notice_Sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
