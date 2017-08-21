package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.tdin360.zjw.marathon.model.MenuModel;
import com.tdin360.zjw.marathon.weight.Carousel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17/2/19.
 */

public class MenuDataUtils {




    public static List<MenuModel> getMenus(Context context){


        List<MenuModel>list=new ArrayList<>();

        try {
            InputStream open = context.getAssets().open("menu.json");

            BufferedReader r = new BufferedReader(new InputStreamReader(open));

            String str ="";
            StringBuilder sb = new StringBuilder();

            while ((str=r.readLine())!=null){

                sb.append(str);
            }

            JSONArray array = new JSONArray(sb.toString());

            for (int i=0;i<array.length();i++){

                JSONObject object = array.getJSONObject(i);

                int id = object.getInt("id");
                String icon = object.getString("icon");
                String title = object.getString("title");

                InputStream stream = context.getAssets().open(icon);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                list.add(new MenuModel(id,bitmap,title));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
}
