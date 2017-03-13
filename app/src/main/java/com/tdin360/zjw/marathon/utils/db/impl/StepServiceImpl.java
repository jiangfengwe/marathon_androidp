package com.tdin360.zjw.marathon.utils.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tdin360.zjw.marathon.model.StepModel;
import com.tdin360.zjw.marathon.utils.db.SQLHelper;
import com.tdin360.zjw.marathon.utils.db.service.IStepService;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作计步历史纪录的实现类
 * Created by admin on 17/3/12.
 */

public class StepServiceImpl implements IStepService {

    private SQLHelper helper;


    public StepServiceImpl(Context context) {

        this.helper = new SQLHelper(context);
    }

    @Override
    public void addStep(StepModel model) {



//        1.查询当天是否有记录
        //如果数据库存在当天数据怎更新否则插入新纪录
         if(!updateStep(model)){

          SQLiteDatabase database = helper.getWritableDatabase();
            //插入新纪录
            ContentValues values = new ContentValues();
            values.put("userId",model.getUserId());
            values.put("totalStep",model.getTotalStep());
            values.put("totalDistance",model.getTotalDistance());
            values.put("totalKcal",model.getTotalKcal());
            values.put("date",model.getDate());
            database.insert(SQLHelper.STEP,null,values);
             database.close();
        }



    }

    @Override
    public void deleteStepById(int id) {

    }


    @Override
    public StepModel findStepById(int id) {

        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor query = database.query(SQLHelper.STEP, null, "id=?", new String[]{id+""}, null, null, null);
        while (query.moveToFirst()) {
            int userId = query.getInt(query.getColumnIndex("userId"));
            int totalStep = query.getInt(query.getColumnIndex("totalStep"));
            int totalDistance = query.getInt(query.getColumnIndex("totalDistance"));
            int totalKcal = query.getInt(query.getColumnIndex("totalKcal"));
            String createTime = query.getString(query.getColumnIndex("date"));
            query.close();
            database.close();
            return new StepModel(userId, totalStep, totalDistance, totalKcal, createTime);
        }

        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean updateStep(StepModel model) {

        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor query = database.query(SQLHelper.STEP, null, "date=?", new String[]{model.getDate()}, null, null, null);
        while (query.moveToFirst()) {

            int totalStep = query.getInt(query.getColumnIndex("totalStep"));
            double totalDistance = query.getDouble(query.getColumnIndex("totalDistance"));
            double totalKcal = query.getDouble(query.getColumnIndex("totalKcal"));

            //累加计步信息
            model.setTotalStep(totalStep+model.getTotalStep());
            model.setTotalDistance(totalDistance+model.getTotalDistance());
            model.setTotalKcal(totalKcal+model.getTotalKcal());

            //更新数据
            ContentValues values = new ContentValues();
            values.put("userId",model.getUserId());
            values.put("totalStep",model.getTotalStep());
            values.put("totalDistance",model.getTotalDistance());
            values.put("totalKcal",model.getTotalKcal());
            database.update(SQLHelper.STEP,values,"date=?",new String[]{model.getDate()});
            query.close();
            database.close();
            return true;
        }

        database.close();
        return false;

    }


    @Override
    public List<StepModel> getAllStep() {


        SQLiteDatabase database = helper.getReadableDatabase();



        Cursor query = database.query(SQLHelper.STEP, null, null,null, null, null,"date DESC");

        List<StepModel>list= new ArrayList<>();
          while (query.moveToNext()){

              int id = query.getInt(query.getColumnIndex("id"));
              int userId = query.getInt(query.getColumnIndex("userId"));
              int totalStep = query.getInt(query.getColumnIndex("totalStep"));
              int totalDistance = query.getInt(query.getColumnIndex("totalDistance"));
              int totalKcal = query.getInt(query.getColumnIndex("totalKcal"));
              String createTime = query.getString(query.getColumnIndex("date"));

              StepModel model = new StepModel(id, userId, totalStep, totalDistance, totalKcal, createTime);
              list.add(model);

          }

            query.close();
            database.close();

        return list;
    }
}
