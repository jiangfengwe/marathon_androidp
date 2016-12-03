package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NewsItem;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**新闻中心数据适配器
 * Created by Administrator on 2016/6/15.
 */
public class NewsListViewAdapter extends BaseAdapter {

    private List<NewsItem>list ;
    private Context context;

    public NewsListViewAdapter(List<NewsItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list==null? 0:list.size();
    }

    //更新数据列表
    public void updateListView(List<NewsItem>list){
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

        ViewHolder viewHolder=null;
            NewsItem newsItem = list.get(position);

        if(convertView==null){
            viewHolder  =new ViewHolder();
            convertView  = View.inflate(context, R.layout.news_list_item,null);
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.time= (TextView) convertView.findViewById(R.id.time);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(newsItem.getTitle());
        viewHolder.time.setText(newsItem.getTime());
        ImageOptions imageOptions = new ImageOptions.Builder()
              //  .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(60))//图片大小
                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.search_nodata)//加载失败后默认显示图片
                .build();
        x.image().bind(viewHolder.imageView,newsItem.getPicUrl(),imageOptions);

        return convertView;
    }

    class ViewHolder{
        TextView title,time;
        ImageView imageView;
    }
}
