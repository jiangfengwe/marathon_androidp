package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * list或gridView公用的adapter
 * Created by admin on 17/8/2.
 * @author zhangzhijun
 */

public abstract class CommonAdapter<T> extends BaseAdapter {


    private Context context;
    private List<T> list;
    private @LayoutRes int layoutId;
    public CommonAdapter(Context context, List<T> list,@LayoutRes int layoutId) {

        this.context=context;
        this.list = list;
        this.layoutId=layoutId;

    }

    public void update(List<T> list){

        this.list=list;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent){


        ViewHolder holder = ViewHolder.get(context, convertView, parent,layoutId, position);
        onBind(holder,list.get(position));

        return holder.getConvertView();
    }


   protected abstract void onBind(ViewHolder holder,T model);
}
