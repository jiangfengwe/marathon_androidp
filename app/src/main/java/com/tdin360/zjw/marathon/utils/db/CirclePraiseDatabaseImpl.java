package com.tdin360.zjw.marathon.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.NoticeMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffery on 2018/1/15.
 */

public class CirclePraiseDatabaseImpl {

    private SQLHelper sqlHelper;

    public CirclePraiseDatabaseImpl() {
    }

    /**
     * 在构造方法里面完成 必须要用的类的初始化
     * @param context
     */

    public CirclePraiseDatabaseImpl(Context context) {
        sqlHelper = new SQLHelper(context);
    }
    /**
     * 添加一条记录
     * @paramname 联系人姓名
     * @paramphone 联系人电话
     * @return 返回的是添加后在数据库的行号  -1代表添加失败
     */
    public void add(CirclePriseTableModel model){
       /* SQLiteDatabase db = sqlHelper.getWritableDatabase();
        //db.execSQL("insert into contactinfo (name,phone) values (?,?)", new Object[]{name,phone});
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        //内部是组拼sql语句实现的.
        long rowid = db.insert("contactinfo", null, values);
        //记得释放数据库资源
        db.close();*/
        //return rowid;
        SQLiteDatabase conn = this.sqlHelper.getWritableDatabase();
        //判断数据库中不存在才插入
        Cursor cursor = conn.query(SQLHelper.PRAISE_COMMENT_TABLE, null,"imageUrl=?and MessageType=?",new String[]{model.getDynamicPictureUrl()}, null, null, null);
        if(!cursor.moveToFirst()){

            ContentValues values = new ContentValues();
            values.put("NickName",model.getNickName());
            values.put("DynamicPictureUrl",model.getDynamicPictureUrl());
            values.put("HeadImg",model.getHeadImg());
            values.put("DynamicId",model.getDynamicId());
            values.put("CommentContent",model.getCommentContent());
            values.put("DynamicContent",model.getCommentContent());
            values.put("MessageType",model.getMessageType());
            conn.insert(SQLHelper.PRAISE_COMMENT_TABLE, null, values);

        }

        conn.close();

    }
    public List<CirclePriseTableModel> getAllCircleDetail() {
        List<CirclePriseTableModel> list=new ArrayList<>();
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        Cursor cursor = conn.query(SQLHelper.EVENT_DETAIL_TABLE, null, null, null, null, null, null);
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
        Log.d("circlesize", "getAllCircleDetail: "+list.size());
        cursor.close();
        conn.close();
        return list;
    }
    public void deleteAll(String eventId) {
        SQLiteDatabase conn = this.sqlHelper.getReadableDatabase();
        try {

            conn.delete(SQLHelper.PRAISE_COMMENT_TABLE,"DynamicId=?",new String[]{eventId});

        }catch (Exception e){


        }finally {
            conn.close();
        }



    }
    /**
     * 根据姓名删除一条记录
     * @param name 要删除的联系人的姓名
     * @return 返回0代表的是没有删除任何的记录 返回整数int值代表删除了几条数据
     */
    public int delete(String name){
        //判断这个数据是否存在.
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        //db.execSQL("delete from contactinfo where name=?", new Object[]{name});
        int rowcount = db.delete("contactinfo", "name=?", new String[]{name});
        db.close();
        //再从数据库里面查询一遍,看name是否还在
        return rowcount;
    }
    /**
     * 修改联系人电话号码
     * @param newphone 新的电话号码
     * @param name 要修改的联系人姓名
     * @return 0代表一行也没有更新成功, >0 整数代表的是更新了多少行记录
     */
    public int update(String newphone , String name){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        //db.execSQL("update contactinfo set phone =? where name=?", new Object[]{newphone,name});
        ContentValues values = new ContentValues();
        values.put("phone", newphone);
        int rowcount =  db.update("contactinfo", values, "name=?", new String[]{name});
        db.close();
        return rowcount;
    }
    /**
     * 查询联系人的电话号码
     * @param name 要查询的联系人
     * @return 电话号码
     */
    public String getPhoneNumber(String name){
        String phone = null;
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        //Cursor  cursor = db.rawQuery("select phone from contactinfo where name=?", new String[]{name});
        Cursor cursor =  db.query("contactinfo", new String[]{"phone"}, "name=?", new String[]{name}, null, null, null);
        if(cursor.moveToNext()){//如果光标可以移动到下一位,代表就是查询到了数据
            phone = cursor.getString(0);
        }
        cursor.close();//关闭掉游标,释放资源
        db.close();//关闭数据库,释放资源
        return phone;
    }



}
