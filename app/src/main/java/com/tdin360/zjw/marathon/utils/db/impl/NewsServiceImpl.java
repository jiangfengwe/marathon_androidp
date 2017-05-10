package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.model.NewsModel;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.NewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/5/8.
 */

public class NewsServiceImpl implements NewsService {
    private SQLHelper helper;

    public NewsServiceImpl(Context context){
        this.helper=new SQLHelper(context);
    }
    @Override
    public void addNews(NewsModel model) {

        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("eventId", MarathonDataUtils.init().getEventId());
        values.put("title",model.getTitle());
        values.put("imageUrl",model.getPicUrl());
        values.put("url",model.getDetailUrl());
        values.put("time",model.getTime());
        writableDatabase.insert(SQLHelper.NEWS_INFO,null,values);
        writableDatabase.close();

    }

    @Override
    public boolean deleteNews(String eventId) {

        SQLiteDatabase conn = this.helper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.NEWS_INFO,"eventId=?",new String[]{eventId});

        }catch (Exception e){

        }finally {
            conn.close();
        }

        return false;
    }

    @Override
    public boolean deleteAllNews() {
        SQLiteDatabase conn = this.helper.getReadableDatabase();
        try{
            conn.delete(SQLHelper.NEWS_INFO,null,null);

        }catch (Exception e){
        }finally {
            conn.close();
        }

        return false;
    }

    @Override
    public List<NewsModel> getAllNews(String eventId) {

        SQLiteDatabase conn = this.helper.getReadableDatabase();

        Cursor cursor = conn.query(SQLHelper.NEWS_INFO, null, "eventId=?", new String[]{eventId}, null, null, null);
        List<NewsModel>list=new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("newsId"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            NewsModel model = new NewsModel(id,title,imageUrl,url,time);
            list.add(model);

        }
        conn.close();
        return list;
    }
}
