package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.EventModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.EventService;
import java.util.ArrayList;
import java.util.List;

/**
 * 赛事数据表操作类
 * Created by admin on 17/3/22.
 */

public class EventServiceImpl implements EventService {

    private SQLHelper sqlHelper;


    public EventServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);

    }

    @Override
    public void addEvent(EventModel model) {

        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
        Cursor cursor = conn.query(SQLHelper.EVENT_TABLE, null,"eventId=?",new String[]{model.getId()+""}, null, null, null);
        if(!cursor.moveToFirst()){

            ContentValues values = new ContentValues();
            values.put("eventId",model.getId());
            values.put("eventImageUrl",model.getPicUrl());
            values.put("eventName",model.getName());
            values.put("eventStatus",model.getStatus());
            values.put("signUptime",model.getSignUpStartTime());
            values.put("eventTime",model.getStartDate());
            values.put("shareUrl",model.getShardUrl());
            values.put("enable",model.isRegister()?"true":"false");
            values.put("isWebPage",model.isWebPage() ? "true":"false");
           conn.insert(SQLHelper.EVENT_TABLE, null, values);


        }

        conn.close();


         getAllEvent();

    }

    @Override
    public List<EventModel> getAllEvent() {

        List<EventModel>list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.EVENT_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            int eventId = cursor.getInt(cursor.getColumnIndex("eventId"));
            String eventImageUrl = cursor.getString(cursor.getColumnIndex("eventImageUrl"));
            String eventName = cursor.getString(cursor.getColumnIndex("eventName"));
            String eventStatus = cursor.getString(cursor.getColumnIndex("eventStatus"));
            String signUptime = cursor.getString(cursor.getColumnIndex("signUpTime"));
            String eventTime = cursor.getString(cursor.getColumnIndex("eventTime"));
            String shareUrl = cursor.getString(cursor.getColumnIndex("shareUrl"));
            boolean enable = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("enable")));
            boolean isWebPage = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("isWebPage")));
            EventModel model = new EventModel(eventId, eventName, eventStatus, eventImageUrl, signUptime, eventTime, shareUrl, enable, isWebPage);
            list.add(model);


        }

        cursor.close();
        conn.close();
        return list;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        try {
            conn.delete(SQLHelper.EVENT_TABLE,null,null);
        }catch (Exception x){


        }finally {
            conn.close();
        }





    }
}
