package com.tdin360.zjw.marathon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.SignUpInfo;
import com.tdin360.zjw.marathon.model.UserModel;

/**
 * 数据存储管理单例类
 * Created by Administrator on 2016/8/23.
 */
public class SharedPreferencesManager {


    //保存用户报名数据
    public static void insertValue(Context context, SignUpInfo signUpInfo){
               //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences("data",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
         clear(context);//先旧数据清空
        edit.putString("Name",signUpInfo.getName());
        edit.putString("Phone",signUpInfo.getPhone());
        edit.putString("Birthday",signUpInfo.getBirthday());
        edit.putString("IDNumber",signUpInfo.getIdNumber());
        edit.putString("CertificateType",signUpInfo.getCertificateType());
        edit.putString("Gender",signUpInfo.getGender());
        edit.putString("Nationality",signUpInfo.getNationality());
        edit.putString("Province",signUpInfo.getProvince());
        edit.putString("City",signUpInfo.getCity());
        edit.putString("County",signUpInfo.getCounty());
        edit.putString("AttendProject",signUpInfo.getAttendProject());
        edit.putString("ClothingSize",signUpInfo.getClothingSize());
        edit.putString("Address",signUpInfo.getAddress());
        edit.putString("Postcode",signUpInfo.getPostcode());
        edit.putString("UrgencyLinkman",signUpInfo.getUrgencyLinkman());
        edit.putString("UrgencyLinkmanPhone",signUpInfo.getUrgencyLinkmanPhone());
        edit.putString("CreateTime",signUpInfo.getCreateTime());
        edit.putString("AttendNumber",signUpInfo.getAttendNumber());
        edit.putBoolean("IsPayed",signUpInfo.isPayed());
        edit.putString("ApplyNature",signUpInfo.getApplyNature());
        edit.putString("service",signUpInfo.getService());
        edit.putString("partner",signUpInfo.getPartner());
        edit.putString("_input_charset",signUpInfo.get_input_charset());
        edit.putString("sign_type",signUpInfo.getSign_type());
        edit.putString("notify_url",signUpInfo.getNotify_url());
        edit.putString("out_trade_no",signUpInfo.getOut_trade_no());
        edit.putString("subject",signUpInfo.getSubject());
        edit.putString("payment_type",signUpInfo.getPayment_type());
        edit.putString("seller_id",signUpInfo.getSeller_id());
        edit.putString("total_fee",signUpInfo.getTotal_fee());
        edit.putString("body",signUpInfo.getBody());
        edit.putString("sign",signUpInfo.getSign());
        edit.commit();

    }

    /**
     * 获取数据
     * @param context
     * @return
     */
    public static SignUpInfo getSignUpInfo(Context context){
        SharedPreferences mySharedPreferences= context.getSharedPreferences("data",
                Activity.MODE_PRIVATE);
        //没有数据
        if(mySharedPreferences.getAll().isEmpty()){

            return null;
        }

        //获取保存的数据
        SignUpInfo signUpInfo = new SignUpInfo(mySharedPreferences.getString("Name",""),mySharedPreferences.getString("Phone",""),
                mySharedPreferences.getString("Birthday",""),mySharedPreferences.getString("IDNumber",""),mySharedPreferences.getString("CertificateType",""),
                mySharedPreferences.getString("Gender",""),mySharedPreferences.getString("Nationality",""),mySharedPreferences.getString("Province",""),
                mySharedPreferences.getString("City",""),mySharedPreferences.getString("County",""),mySharedPreferences.getString("AttendProject",""),
                mySharedPreferences.getString("ClothingSize",""),mySharedPreferences.getString("Address",""),mySharedPreferences.getString("Postcode",""),
                mySharedPreferences.getString("UrgencyLinkman",""),mySharedPreferences.getString("UrgencyLinkmanPhone",""),mySharedPreferences.getString("CreateTime",""),
                mySharedPreferences.getBoolean("IsPayed",false),mySharedPreferences.getString("attendNumber",""), mySharedPreferences.getString("ApplyNature",""),
                mySharedPreferences.getString("service",""),mySharedPreferences.getString("partner",""),mySharedPreferences.getString("_input_charset",""),mySharedPreferences.getString("sign_type",""),
                mySharedPreferences.getString("notify_url",""),mySharedPreferences.getString("out_trade_no",""),mySharedPreferences.getString("subject",""),mySharedPreferences.getString("payment_type",""),
                mySharedPreferences.getString("seller_id",""),mySharedPreferences.getString("total_fee",""),mySharedPreferences.getString("body",""),mySharedPreferences.getString("sign",""));

              return signUpInfo;
    }
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

    //保存设置状态
    public static void isOpen(Context context,boolean isOpen){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences("my_setting",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isOpen",isOpen);
        edit.commit();
    }
    //获取设置状态
    public static boolean getOpen(Context context){
        SharedPreferences mySharedPreferences= context.getSharedPreferences("my_setting",
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getBoolean("isOpen",false);
    }

    //保存登录信息
    public static void saveLoginInfo(Context context, LoginModel model){

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("name",model.getName());
        edit.putString("password",model.getPassword());
        edit.commit();

    }

    //获取用户登录信息
    public static LoginModel getLoginInfo(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("loginInfo",Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "点击登录");
        String password = sharedPreferences.getString("password","");

        return new LoginModel(name,password);
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
}
