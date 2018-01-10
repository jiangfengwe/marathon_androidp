package com.tdin360.zjw.marathon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.R.attr.id;


/**
 * 数据存储管理单例类
 * Created by Administrator on 2016/8/23.
 */
public class SharedPreferencesManager {
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
    public static void saveLoginInfo(Context context, LoginUserInfoBean.UserBean model){
        /**
         * Id : 507
         * HeadImg :
         * Phone : null
         * NickName : 用户507
         * Gender : false
         * Unionid : null
         * IsBindPhone : false
         * CustomerSign : null
         */

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(LoginUserInfoBean.ID,model.getId()+"");
        edit.putString(LoginUserInfoBean.HeadImg,model.getHeadImg());
        edit.putString(LoginUserInfoBean.NickName,model.getNickName());
        edit.putBoolean(LoginUserInfoBean.Gender,model.isGender());
        edit.putString(LoginUserInfoBean.Unionid,model.getUnionid());
        edit.putBoolean(LoginUserInfoBean.IsBindPhone,model.isIsBindPhone());
        edit.putString(LoginUserInfoBean.CustomerSign,model.getCustomerSign());
        edit.putString(LoginUserInfoBean.Phone,model.getPhone());
        edit.commit();

    }

    //更新头像
    public static void updateImageUrl(Context context,String url){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(LoginModel.imageKey,url);
        edit.commit();

    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public static <K, V> boolean putHashMapData(Context context,String key, Map<K, LoginUserInfoBean.UserBean> map) {
        boolean result;
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            edit.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        edit.apply();
        return result;
    }
    /**
     * 用于获取集合
     *
     * @param key key
     * @return HashMap
     */
    public static <V> HashMap<String, LoginUserInfoBean.UserBean> getHashMapData(Context context,String key, Class<LoginUserInfoBean.UserBean> clsV) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, "");
        HashMap<String, LoginUserInfoBean.UserBean> map = new HashMap<>();
        Gson gson = new Gson();
        JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String entryKey = entry.getKey();
            JsonObject value = (JsonObject) entry.getValue();
            map.put(entryKey, gson.fromJson(value, clsV));
        }
        Log.e("SharedPreferencesUtil", obj.toString());
        return map;
    }
    /**
     * Id : 507
     * HeadImg :
     * Phone : null
     * NickName : 用户507
     * Gender : false
     * Unionid : null
     * IsBindPhone : false
     * CustomerSign : null
     */
    //获取用户登录信息
    public static LoginUserInfoBean.UserBean getLoginInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        String id = sharedPreferences.getString(LoginUserInfoBean.ID, "");
        String headimg = sharedPreferences.getString(LoginUserInfoBean.HeadImg, "");
        String name = sharedPreferences.getString(LoginUserInfoBean.NickName, "点击登录");
        boolean gender = sharedPreferences.getBoolean(LoginUserInfoBean.Gender, false);
        String unionid = sharedPreferences.getString(LoginUserInfoBean.Unionid, "");
        boolean isbind = sharedPreferences.getBoolean(LoginUserInfoBean.IsBindPhone, false);
        String sign = sharedPreferences.getString(LoginUserInfoBean.CustomerSign, "");
        String phone = sharedPreferences.getString(LoginUserInfoBean.Phone, "");

        return new LoginUserInfoBean.UserBean(id,headimg,name,gender,unionid,isbind,sign,phone);
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
     * 保存背景皮肤图片的地址
     */
    public static void saveBgPath(Context context,String path) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        //获得Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //写数据
        editor.putString("circleBg", path);
        editor.commit();
    }

    public static String readBgPath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        return sharedPreferences.getString("circleBg","");
    }

    /**
     * 写入别名数据
     */
    public static void writeAlias(Context context,String alias) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

        //获得Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //写数据
        editor.putString("alias", alias);
        editor.commit();
    }
    /**
     * 读取别名数据
     */
    public static String getAlias(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        return sharedPreferences.getString("alias","");
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
