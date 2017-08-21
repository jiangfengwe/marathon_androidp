package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.Gravity;
import android.widget.Toast;

/**
 * toast工具类
 * Created by admin on 17/8/8.
 */

public class ToastUtils {


   public static void show(Context context,CharSequence text){

       Toast.makeText(context.getApplicationContext(),text,Toast.LENGTH_SHORT).show();

   }
   public static void show(Context context,CharSequence text,@BaseTransientBottomBar.Duration int duration){

       Toast.makeText(context.getApplicationContext(),text,duration).show();

   }

   public static void showCenter(Context context,CharSequence text){

       Toast toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
       toast.setGravity(Gravity.CENTER,0,0);
       toast.show();
   }
   public static void showCenter(Context context,CharSequence text,@BaseTransientBottomBar.Duration int duration){

       Toast toast = Toast.makeText(context.getApplicationContext(), text,duration);
       toast.setGravity(Gravity.CENTER,0,0);
       toast.show();
   }


}
