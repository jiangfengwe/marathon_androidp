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

    //计步历史
    public static final String STEP="Step";


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

        String create_Step_Sql="CREATE TABLE "+STEP+" (\n" +
                "    id            INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                          NOT NULL,\n" +
                "    userId        INTEGER NOT NULL,\n" +
                "    totalStep     INTEGER NOT NULL,\n" +
                "    totalDistance DOUBLE  NOT NULL,\n" +
                "    totalKcal     DOUBLE  NOT NULL,\n" +
                "    date          DATE    NOT NULL\n" +
                ");";

        db.execSQL(create_Notice_Sql);
        db.execSQL(create_Step_Sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
