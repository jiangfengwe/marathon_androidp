package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.model.NoticeModel;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.NoticeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/5/8.
 */

public class NoticeServiceImpl implements NoticeService {



    private SQLHelper helper;

    public NoticeServiceImpl(Context context){
        this.helper=new SQLHelper(context);
    }
    @Override
    public void addNotice(NoticeModel model) {

        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("eventId", MarathonDataUtils.init().getEventId());
        values.put("title",model.getTitle());
        values.put("url",model.getUrl());
        values.put("time",model.getTime());
        writableDatabase.insert(SQLHelper.NOTICE_INFO,null,values);
        writableDatabase.close();

    }

    @Override
    public boolean deleteNotice(String eventId) {


        SQLiteDatabase conn = this.helper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.NOTICE_INFO,"eventId=?",new String[]{eventId});

        }catch (Exception e){

        }finally {
            conn.close();
        }


        return false;
    }

    @Override
    public boolean deleteAllNotice() {


        SQLiteDatabase conn = this.helper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.NOTICE_INFO,null,null);

        }catch (Exception e){

        }finally {
            conn.close();
        }

        return false;
    }

    @Override
    public List<NoticeModel> getAllNotice(String eventId) {

        SQLiteDatabase conn = this.helper.getReadableDatabase();

        Cursor cursor = conn.query(SQLHelper.NOTICE_INFO, null, "eventId=?", new String[]{eventId}, null, null, null);
        List<NoticeModel>list=new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("noticeId"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            NoticeModel model = new NoticeModel(id,title,time,url);
            list.add(model);

        }
        conn.close();
        return list;


    }
}
