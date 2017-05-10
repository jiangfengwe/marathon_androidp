package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.EventDetailService;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛事详情离线数据操作
 * Created by admin on 17/3/22.
 */

public class EventDetailsServiceImpl implements EventDetailService{

    private SQLHelper sqlHelper;

    public EventDetailsServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);

    }


    @Override
    public void addEventDetail(CarouselModel model) {


        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
        Cursor cursor = conn.query(SQLHelper.EVENT_DETAIL_TABLE, null,"imageUrl=?",new String[]{model.getPicUrl()}, null, null, null);
        if(!cursor.moveToFirst()){

            ContentValues values = new ContentValues();
            values.put("eventId",model.getEventId());
            values.put("imageUrl",model.getPicUrl());
            values.put("url",model.getLinkUrl());
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

            String type1 =  cursor.getString(cursor.getColumnIndex("type")) ;

            list.add(new CarouselModel(eventId1,"",imageUrl,url,type1));

        }

        cursor.close();
        conn.close();
        return list;
    }

    @Override
    public void deleteAll(String eventId) {
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.EVENT_DETAIL_TABLE,"eventId=?",new String[]{eventId});

        }catch (Exception e){


        }finally {
            conn.close();
        }



    }
}
