package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NoticeModel;

import java.util.List;

/**通知公告数据适配器
 * Created by Administrator on 2016/6/15.
 */
public class NoticeListViewAdapter extends BaseAdapter {

    private List<NoticeModel>list ;
    private Context context;

    public NoticeListViewAdapter(List<NoticeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list==null? 0:list.size();
    }

    //更新数据列表
    public void updateListView(List<NoticeModel>list){
                this.list=list;
         notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        NoticeModel noticeModel = list.get(position);

        if(convertView==null){
            viewHolder  =new ViewHolder();
            convertView  = View.inflate(context, R.layout.notice_list_item,null);
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.date= (TextView) convertView.findViewById(R.id.date);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(noticeModel.getTitle());
        viewHolder.date.setText(noticeModel.getDate());
        viewHolder.day.setText(noticeModel.getDay());


        return convertView;
    }

    class ViewHolder{
        TextView title,date,day;

    }
}
