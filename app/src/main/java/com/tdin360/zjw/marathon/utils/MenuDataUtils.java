package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/2/19.
 */

public class MenuDataUtils {




    private static String[]menuTitles = {"赛事报名","赛事简介","赛事新闻","赛事公告","赛事路线"};
    private static int[]ids = {R.drawable.menu_icon1,R.drawable.menu_icon2,R.drawable.menu_icon3,R.drawable.menu_icon4,R.drawable.menu_icon5};


    public static List<MenuModel> getMenus(Context context){

        List<MenuModel>models=new ArrayList<>();
        for (int i=0;i<menuTitles.length;i++) {
            models.add(new MenuModel(i, BitmapFactory.decodeResource(context.getResources(), ids[i]),menuTitles[i]));
        }
        return models;
    }
}
