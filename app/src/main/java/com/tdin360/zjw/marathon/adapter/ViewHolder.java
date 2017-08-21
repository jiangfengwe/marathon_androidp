package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 公共ViewHolder
 * Created by admin on 17/8/2.
 */

public class ViewHolder {


    private int mPosition;
    private View mConvertView;
    private SparseArray<View>mViews;
    private Context context;
    public ViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId,int position){


        this.context=context;
        this.mPosition=position;
        this.mViews=new SparseArray<>();
        this.mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        this.mConvertView.setTag(this);

    }

    public static ViewHolder get(Context context,View convertView, ViewGroup parent,@LayoutRes int layoutId,int position){

        if(convertView==null){


         return new ViewHolder(context,parent,layoutId,position);

        }else {


           ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.mPosition=position;
            return holder;
        }


    }

    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 通过id获取控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getViewById(@IdRes int id){


        View view  = mViews.get(id);
        if(view==null){

        view = mConvertView.findViewById(id);
         mViews.put(id,view);
        }

        return (T)view;
    }


    public ViewHolder setText(@IdRes int id,CharSequence text){


        TextView view = getViewById(id);
        view.setText(text);


        return this;

    }

    public ViewHolder setTextResource(@IdRes int id,@StringRes int stringId){

            String string = this.context.getResources().getString(stringId);

            TextView view = getViewById(id);
            view.setText(string);
        return this;

    }

    public ViewHolder setBitmap(@IdRes int id, Bitmap bitmap){

        ImageView view = getViewById(id);


        view.setImageBitmap(bitmap);

        return this;
    }


    public ViewHolder setImageResource(@IdRes int id, @DrawableRes int resId){

        ImageView view = getViewById(id);
        view.setImageResource(resId);
        return this;
    }

}
