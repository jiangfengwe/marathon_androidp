package com.tdin360.zjw.marathon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.step.StepSettingModel;

/**
 * 数据存储管理单例类
 * Created by Administrator on 2016/8/23.
 */
public class SharedPreferencesManager {

    public static final String LM_KEY="lm";
    public static final String STEP_LENGTH_KEY="step_length";
    public static final String WEIGHT_KEY="weight";
   /*
   清除用户报名数据
    */
    public static void clear(Context context){

        SharedPreferences mySharedPreferences= context.getSharedPreferences("data",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

    //保存设置是否接受推送消息状态
    public static void isOpen(Context context,boolean isOpen){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences("my_setting",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isOpen",isOpen);
        edit.commit();
    }
    //获取是否接受推送消息设置状态
    public static boolean getOpen(Context context){
        SharedPreferences mySharedPreferences= context.getSharedPreferences("my_setting",
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getBoolean("isOpen",true);
    }

    //保存登录信息
    public static void saveLoginInfo(Context context, LoginModel model){

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(LoginModel.nameKEY,model.getName());
        edit.putString(LoginModel.passKey,model.getPassword());
        edit.putString(LoginModel.imageKey,model.getImageUrl());
        edit.commit();

    }





    //获取用户登录信息
    public static LoginModel getLoginInfo(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString(LoginModel.nameKEY, "点击登录");
        String password = sharedPreferences.getString(LoginModel.passKey,"");
        String imageUrl = sharedPreferences.getString(LoginModel.imageKey, "");
        return new LoginModel(name,password,imageUrl);
    }


    /**
     * 判断用户是否登录
     * @param context
     * @return
     */
    public static boolean isLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

        if (sharedPreferences.getAll().size()>0){

            return true;
        }

            return false;


    }
    /**
     * 用户退出登录
     * @param context
     * @return
     */
    public static String clearLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

         if(sharedPreferences.getAll().size()>0){

             SharedPreferences.Editor edit = sharedPreferences.edit();

             edit.clear();
             edit.commit();
             return "已退出登录!";
         }

         return "您尚未登录";

    }

    /**
     *保存计步设置
     * @param context
     * @param model 计步设置参数
     *
     */
    public static void saveStepParams(Context context, StepSettingModel model){

        SharedPreferences shared = context.getSharedPreferences("step_set",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putInt(LM_KEY,model.getLm());
        edit.putInt(STEP_LENGTH_KEY,model.getStep_length());
        edit.putInt(WEIGHT_KEY,model.getWeight());
        edit.commit();

    }

    /**
     * 获取计步设置参数
     * @param context
     * @return
     */
    public static StepSettingModel getStepSettingModel(Context context){

        SharedPreferences shared = context.getSharedPreferences("step_set",Activity.MODE_PRIVATE);

        int lm = shared.getInt(LM_KEY, 3);

        int step_l = shared.getInt(STEP_LENGTH_KEY, 60);

        int weight = shared.getInt(WEIGHT_KEY, 50);
        return new StepSettingModel(lm,step_l,weight);


    }

    /**
     * 清除计步设置恢复默认
     * @param context
     */
    public static void clearStepParams(Context context){

        SharedPreferences shared = context.getSharedPreferences("step_set",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.clear();
        edit.commit();

    }

    //保存当前版本号
    public static void saveGuideStatus(Context context){

        SharedPreferences shared = context.getSharedPreferences("guide",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();
        edit.putInt("isGuide",UpdateManager.getVersion(context));
        edit.commit();
    }

    //判断是否进行用户引导
    public static boolean isGuide(Context context){

        SharedPreferences shared = context.getSharedPreferences("guide",Activity.MODE_PRIVATE);
        //获取历史版本号
        int isGuide = shared.getInt("isGuide", 0);

//        如果发现新版本就进行引导
        if(UpdateManager.getVersion(context)>isGuide){

            return true;
        }

        return false;

    }
}
