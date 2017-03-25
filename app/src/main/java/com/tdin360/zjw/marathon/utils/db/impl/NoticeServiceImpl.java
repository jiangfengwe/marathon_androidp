package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tdin360.zjw.marathon.model.NoticeMessageModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.NoticeService;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心通知消息接口实现类
 * Created by admin on 17/2/16.
 */

public class NoticeServiceImpl implements NoticeService {

    private SQLHelper sqlHelper;
    public NoticeServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);
    }

    @Override
    public void addNotice(NoticeMessageModel model) {

        SQLiteDatabase writableDatabase = this.sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("forName",model.getForName());
        values.put("forTime",model.getForTime());
        values.put("content",model.getMessage());
        writableDatabase.insert(SQLHelper.NOTICE_TABLE,null,values);
        writableDatabase.close();

    }

    @Override
    public boolean deleteNotice(int id) {

        SQLiteDatabase database = sqlHelper.getWritableDatabase();

        int delete = database.delete(SQLHelper.NOTICE_TABLE, "id=?", new String[]{id+""});
        if(delete>0){

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllNotice() {

        SQLiteDatabase database = sqlHelper.getWritableDatabase();

        int delete = database.delete(SQLHelper.NOTICE_TABLE,null,null);
        if(delete>0){

            return true;
        }

        return false;
    }

    @Override
    public List<NoticeMessageModel> getAllNotice() {

        SQLiteDatabase database = this.sqlHelper.getReadableDatabase();

        Cursor cursor = database.query(SQLHelper.NOTICE_TABLE, null, null, null, null, null,"id DESC");

        List<NoticeMessageModel>list=new ArrayList<>();
        while (cursor.moveToNext()){

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));

            String forName = cursor.getString(cursor.getColumnIndex("forName"));

            String forTime = cursor.getString(cursor.getColumnIndex("forTime"));
            String content = cursor.getString(cursor.getColumnIndex("content"));

            list.add(new NoticeMessageModel(id,forName,forTime,content));
        }

        cursor.close();
        database.close();

        return list;
    }
}
