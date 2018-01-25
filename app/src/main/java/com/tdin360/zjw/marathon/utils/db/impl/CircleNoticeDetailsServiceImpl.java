package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.CircleNoticeDetailService;
import com.tdin360.zjw.marathon.utils.db.service.EventDetailService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 赛事详情离线数据操作
 * Created by admin on 17/3/22.
 */

public class CircleNoticeDetailsServiceImpl implements CircleNoticeDetailService{

    private SQLHelper sqlHelper;

    public CircleNoticeDetailsServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);

    }


   /* @Override
    public void addEventDetail(CarouselModel model) {
        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
        Cursor cursor = conn.query(SQLHelper.EVENT_DETAIL_TABLE, null,"imageUrl=?",new String[]{model.getPicUrl()}, null, null, null);
        if(!cursor.moveToFirst()){

            ContentValues values = new ContentValues();
            values.put("eventId",model.getEventId());
            values.put("imageUrl",model.getPicUrl());
            values.put("url",model.getLinkUrl());
            values.put("title",model.getTitle());
            values.put("type",model.isType());
            conn.insert(SQLHelper.EVENT_DETAIL_TABLE, null, values);

        }

         conn.close();


    }

    @Override
    public List<CarouselModel> getAllEventDetail(String eventId,String type) {
        List<CarouselModel>list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.EVENT_DETAIL_TABLE, null, "eventId=? and type=?", new String[]{eventId, type}, null, null, null);

        while (cursor.moveToNext()){

            String eventId1 = cursor.getString(cursor.getColumnIndex("eventId"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String type1 =  cursor.getString(cursor.getColumnIndex("type")) ;
            list.add(new CarouselModel(eventId1,title,imageUrl,url,type1));

        }

        cursor.close();
        conn.close();
        return list;
    }*/

    @Override
    public void addCircleNotice(CirclePriseTableModel model) {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Calendar c=Calendar.getInstance();
        //Date curDate=new Date(System.currentTimeMillis());//获取当前时间       
        String format = formatter.format(c.getTime());
        model.setTime(format);

        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
       // Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,"NickName=?",new String[]{model.getNickName()}, null, null, null);
        //Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,null,null, null, null, null);
        //if(!cursor.moveToFirst()){
            ContentValues values = new ContentValues();
            values.put("nickName",model.getNickName());
            values.put("dynamicPictureUrl",model.getDynamicPictureUrl());
            values.put("headImg",model.getHeadImg());
            values.put("dynamicId",model.getDynamicId());
            values.put("commentContent",model.getCommentContent());
            values.put("dynamicContent",model.getDynamicContent());
            values.put("messageType",model.getMessageType());
            values.put("time",model.getTime());
            conn.insert(SQLHelper.PRAISE_COMMENT_TABLE, null, values);
        Log.d("model.getNickName()", "addCircleNotice: "+model.getNickName());
       // }
        conn.close();

    }

    @Override
    public List<CirclePriseTableModel> getAllCircleNotice() {
        List<CirclePriseTableModel> list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null, null, null, null, null, "time desc");
        while (cursor.moveToNext()){
            String NickName = cursor.getString(cursor.getColumnIndex("nickName"));
            Log.d("wwwwwwwwwww", "getAllCircleNotice: "+NickName);
            String DynamicPictureUrl = cursor.getString(cursor.getColumnIndex("dynamicPictureUrl"));
            String HeadImg = cursor.getString(cursor.getColumnIndex("headImg"));
            int DynamicId = cursor.getInt(cursor.getColumnIndex("dynamicId"));
            String CommentContent =  cursor.getString(cursor.getColumnIndex("commentContent")) ;
            String DynamicContent =  cursor.getString(cursor.getColumnIndex("dynamicContent")) ;
            String messageType = cursor.getString(cursor.getColumnIndex("messageType"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            list.add(new CirclePriseTableModel(NickName,DynamicPictureUrl,messageType,HeadImg,DynamicId,CommentContent,DynamicContent,time));
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

            conn.delete(SQLHelper.PRAISE_COMMENT_TABLE,"eventId=?",new String[]{eventId});

        }catch (Exception e){


        }finally {
            conn.close();
        }



    }
}
