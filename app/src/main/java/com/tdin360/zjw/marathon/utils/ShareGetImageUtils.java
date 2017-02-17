package com.tdin360.zjw.marathon.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;

/**
 * 从缓存中获取图片
 * Created by admin on 17/1/17.
 */

public class ShareGetImageUtils {

    private static Bitmap bitmap;

    public static Bitmap getBitmapByCance(String picUrl){


       x.image().loadFile(picUrl, null, new Callback.CacheCallback<File>() {

           @Override
           public boolean onCache(File file) {

               bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
               return false;
           }

           @Override
           public void onSuccess(File file) {

           }

           @Override
           public void onError(Throwable throwable, boolean b) {

           }

           @Override
           public void onCancelled(CancelledException e) {

           }

           @Override
           public void onFinished() {

           }
       });

        return bitmap;
    }
}
