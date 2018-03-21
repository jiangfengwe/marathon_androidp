package com.tdin360.zjw.marathon.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类(做缓存)
 * Created by admin on 16/12/10.
 */

public class SQLHelper extends SQLiteOpenHelper {
    public static final String TAG="DatabaseDemo";
    public static final int VERSION=2;
    private boolean mUpgradeResult=true;
    private Context mContext;
    private int mVersion;

    //数据库名称
    public static final String DB_NAME="marathonTwo.db";
    // 社交点赞评论通知表
    public static final String PRAISE_COMMENT_TABLE="Circle";
    // 系统通知表
    public static final String SYSTEM_NOTICE_TABLE="SystemNotice";

//    赛事主页表
    public static final String EVENT_TABLE="Event";

    //赛事详情表
    public static final String EVENT_DETAIL_TABLE="EventDetail";

    //通知消息表
    public static final String NOTICE_MESSAGE_TABLE="NoticeMessage";

    //个人资料信息表
    public static final String MY_INFO_TABLE="MY_INFO";

    //新闻
    public static final String NEWS_INFO="news";

//    通知
    public static final String NOTICE_INFO="notice";


    public SQLHelper(Context context){
        //super(context,DB_NAME,null,2);
        super(context,DB_NAME,null,VERSION);
        mContext=context;
        mVersion=VERSION;


    }

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 社交点赞评论表
        String circleSql ="CREATE TABLE "+PRAISE_COMMENT_TABLE+" (\n" +
                "    dynamicId            INTEGER \n" +
                "                          NOT NULL,\n" +
                "    nickName      VARCHAR,\n" +
                "    dynamicPictureUrl     VARCHAR,\n" +
                "    messageType   VARCHAR,\n" +
                "    headImg VARCHAR,\n" +
                "    commentContent VARCHAR,\n" +
                "    time VARCHAR DESC,\n" +
                "    dynamicContent   VARCHAR);" ;
        /**
         * messageIntroduce : 测试消息
         * messageType : systemnotification
         * messageId : 19
         *  private String time;
         private String notice;
         */
        String systemSql ="CREATE TABLE "+SYSTEM_NOTICE_TABLE+" (\n" +
                "    messageId            INTEGER PRIMARY KEY\n" +
                "                          NOT NULL,\n" +
                "    messageIntroduce      VARCHAR,\n" +
                "    url      VARCHAR,\n" +
                "    messageType   VARCHAR,\n" +
                "    time VARCHAR,\n" +
                "    title VARCHAR,\n" +
                "    timeNotice   VARCHAR);" ;

//        赛事数据表
        String eventSql ="CREATE TABLE "+EVENT_TABLE+" (\n" +
                "    id            INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                          NOT NULL,\n" +
                "    eventId       INTEGER NOT NULL,\n" +
                "    eventName     VARCHAR,\n" +
                "    eventImageUrl VARCHAR,\n" +
                "    shareUrl VARCHAR,\n" +
                "    eventStatus   VARCHAR,\n" +
                "    signUpTime    VARCHAR,\n" +
                "    eventTime     VARCHAR,\n" +
                "    enable  VARCHAR,"+
                "    isWebPage VARCHAR);";

//        赛事详情表
        String eventDetailSql="CREATE TABLE  "+EVENT_DETAIL_TABLE+" (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                     NOT NULL,\n" +
                "    title    VARCHAR,\n"+
                "    eventId  INTEGER,\n" +
                "    imageUrl VARCHAR,\n" +
                "    url      VARCHAR,\n" +
                "    type     VARCHAR\n" +
                ");";

//        通知消息表
        String create_Notice_Sql="CREATE TABLE  "+NOTICE_MESSAGE_TABLE+" (\n" +
                "    id      INTEGER      NOT NULL\n" +
                "                         PRIMARY KEY AUTOINCREMENT,\n" +
                "    forName VARCHAR (20),\n" +
                "    forTime VARCHAR (20),\n" +
                "    content VARCHAR\n" +
                ");";



        //我的资料表
        String myInfoSql="CREATE TABLE  "+MY_INFO_TABLE+" (\n" +
                "    id      INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                    NOT NULL,\n" +
                "    tel    VARCHAR NOT NULL,\n" +
                "    name    VARCHAR,\n" +
                "    gander  BOOLEAN,\n" +
                "    bothday VARCHAR,\n" +
                "    email   VARCHAR\n" +
                ");";
        //新闻
        String newsSql = "CREATE TABLE "+NEWS_INFO+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "newsId Integer,eventId VARCHAR,title VARCHAR,imageUrl VARCHAR,url VARCHAR,time VARCHAR)";
        //公告
        String noticeSql = "CREATE TABLE "+NOTICE_INFO+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "noticeId Integer, eventId VARCHAR,title VARCHAR,url VARCHAR,time VARCHAR)";



        db.execSQL(eventSql);
        db.execSQL(systemSql);

        db.execSQL(eventDetailSql);
        db.execSQL(create_Notice_Sql);
        db.execSQL(myInfoSql);
        db.execSQL(newsSql);
        db.execSQL(noticeSql);
        db.execSQL(circleSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PRAISE_COMMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+SYSTEM_NOTICE_TABLE);


      /*  db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_DETAIL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+NOTICE_MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+NEWS_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+NOTICE_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+MY_INFO_TABLE);*/
        int version = db.getVersion();
        if(version!=mVersion){
            if(db.isReadOnly()){
                throw new SQLiteException("Can't uagrade read-only database from version"+
                        db.getVersion()+"to"+mVersion);
            }
            db.beginTransaction();
            try {
                if(version==0){
                    onCreate(db);
                }else {
                    if(version>mVersion){
                        onDowngrade(db,version,mVersion);
                    }else{
                        onUpgrade(db,version,mVersion);
                    }
                }
                db.setVersion(mVersion);
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
        onCreate(db);

        
    }

}
