package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.SystemNoticeBean;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.CircleNoticeDetailService;
import com.tdin360.zjw.marathon.utils.db.service.SystemNoticeDetailService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 赛事详情离线数据操作
 * Created by admin on 17/3/22.
 */

public class SystemNoticeDetailsServiceImpl implements SystemNoticeDetailService{

    private SQLHelper sqlHelper;

    public SystemNoticeDetailsServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);

    }
    @Override
    public void addSystemNotice(SystemNoticeBean model) {
        /**
         * messageIntroduce : 测试消息
         * messageType : systemnotification
         * messageId : 19
         *  private String time;
         private String notice;
         */
        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Calendar c=Calendar.getInstance();
        //Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        String format = formatter.format(c.getTime());
        model.setTime(format);
       /* model.setMessageIntroduce("aa");*/
        model.setNotice("0");
       // model.setNotice(1);
        //判断数据库中不存在才插入
       // Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,"NickName=?",new String[]{model.getNickName()}, null, null, null);
        //Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,null,null, null, null, null);
        //if(!cursor.moveToFirst()){
            ContentValues values = new ContentValues();
          /*  values.put("nickName",model.getNickName());
            values.put("dynamicPictureUrl",model.getDynamicPictureUrl());
            values.put("headImg",model.getHeadImg());
            values.put("dynamicId",model.getDynamicId());
            values.put("commentContent",model.getCommentContent());
            values.put("dynamicContent",model.getDynamicContent());
            values.put("messageType",model.getMessageType());*/
        //values.put("messageId",model.getMessageId());
        values.put("messageIntroduce",model.getMessageIntroduce());
        values.put("messageType",model.getMessageType());
        values.put("time",model.getTime());
        values.put("timeNotice",model.getNotice());
            conn.insert(SQLHelper.SYSTEM_NOTICE_TABLE, null, values);
        Log.d("model.getNickName()", "addCircleNotice: "+model.getMessageIntroduce());
       // }
        conn.close();

    }

    @Override
    public List<SystemNoticeBean> getAllSystemNotice() {
        List<SystemNoticeBean> list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.SYSTEM_NOTICE_TABLE, null, null, null, null, null, "time desc");
        while (cursor.moveToNext()){
           // String NickName = cursor.getString(cursor.getColumnIndex("nickName"));

            int DynamicId = cursor.getInt(cursor.getColumnIndex("messageId"));
            String DynamicContent =  cursor.getString(cursor.getColumnIndex("messageIntroduce")) ;

            String messageType = cursor.getString(cursor.getColumnIndex("messageType"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String notice = cursor.getString(cursor.getColumnIndex("timeNotice"));
            list.add(new SystemNoticeBean(DynamicContent,messageType,DynamicId,time,notice));
        }
        cursor.close();
        conn.close();
        Log.d("model.getNickName()size", "addCircleNotice: "+list.size());
        return list;
    }

    @Override
    public void deleteAll(String eventId) {
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.SYSTEM_NOTICE_TABLE,"messageId=?",new String[]{eventId});

        }catch (Exception e){


        }finally {
            conn.close();
        }
    }
    /**
     * 更新记录
     * @param timeNotice
     */
    public void update(String timeNotice,String index){
        SQLiteDatabase sqldb = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("timeNotice",timeNotice);
        sqldb.update(SQLHelper.SYSTEM_NOTICE_TABLE, values, "messageId = ?",new String[] { index });
        sqldb.close();

    }
}
