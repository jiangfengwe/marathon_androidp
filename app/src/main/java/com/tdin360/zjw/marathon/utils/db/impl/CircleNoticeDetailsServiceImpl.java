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

import java.util.ArrayList;
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
        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
       // Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,"NickName=?",new String[]{model.getNickName()}, null, null, null);
        //Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,null,null, null, null, null);
        //if(!cursor.moveToFirst()){
            ContentValues values = new ContentValues();
            values.put("NickName",model.getNickName());
            values.put("DynamicPictureUrl",model.getDynamicPictureUrl());
            values.put("HeadImg",model.getHeadImg());
            values.put("DynamicId",model.getDynamicId());
            values.put("CommentContent",model.getCommentContent());
            values.put("DynamicContent",model.getDynamicContent());
            values.put("MessageType",model.getMessageType());
            conn.insert(SQLHelper.PRAISE_COMMENT_TABLE, null, values);
       // }
        conn.close();

    }

    @Override
    public List<CirclePriseTableModel> getAllCircleNotice() {
        List<CirclePriseTableModel> list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String NickName = cursor.getString(cursor.getColumnIndex("NickName"));
            String DynamicPictureUrl = cursor.getString(cursor.getColumnIndex("DynamicPictureUrl"));
            String HeadImg = cursor.getString(cursor.getColumnIndex("HeadImg"));
            int DynamicId = cursor.getInt(cursor.getColumnIndex("DynamicId"));
            String CommentContent =  cursor.getString(cursor.getColumnIndex("CommentContent")) ;
            String DynamicContent =  cursor.getString(cursor.getColumnIndex("DynamicContent")) ;
            String messageType = cursor.getString(cursor.getColumnIndex("messageType"));
            list.add(new CirclePriseTableModel(NickName,DynamicPictureUrl,messageType,HeadImg,DynamicId,CommentContent,DynamicContent));
        }
        cursor.close();
        conn.close();
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
