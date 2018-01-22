package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tdin360.zjw.marathon.model.UserModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.MyInfoService;

/**
 * Created by admin on 17/3/22.
 */

public class MyInfoServiceImpl implements MyInfoService {


    private SQLHelper sqlHelper;

    public MyInfoServiceImpl(Context context){

        this.sqlHelper = new SQLHelper(context);

    }

    @Override
    public void addInfo(UserModel model) {
        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tel",model.getPhone());
        values.put("name",model.getName());
        values.put("gander",model.isGender());
        values.put("bothday",model.getBirthday());
        values.put("email",model.getEmail());
        conn.insert(SQLHelper.MY_INFO_TABLE,null,values);
        conn.close();


    }

    @Override
    public UserModel getUserModel(String tel) {

        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();

        Cursor cursor = conn.query(SQLHelper.MY_INFO_TABLE, null, "tel=?", new String[]{tel}, null, null, null);

        if(cursor.getCount()>0&&cursor.moveToFirst()) {
            String tel1 = cursor.getString(cursor.getColumnIndex("tel"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            boolean gander = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("gander")));
            String bothday = cursor.getString(cursor.getColumnIndex("bothday"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            cursor.close();
            conn.close();
            return new UserModel(null,tel1,name,email,bothday,gander);
        }

        cursor.close();
        conn.close();
        return null;
    }

    @Override
    public void delete() {

        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.MY_INFO_TABLE,null,null);


        }catch (Exception e){}

        conn.close();

    }
}
