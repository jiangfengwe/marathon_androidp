package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CarouselItem;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


/**
 * Created by Administrator on 2016/6/15.
 */
public class MarathonHomeMyGridViewAdapter extends BaseAdapter {

    private List<CarouselItem>list ;
    private Context context;

    public MarathonHomeMyGridViewAdapter(List<CarouselItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
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
        CarouselItem carouselItem = list.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.marath_home_list_item,null);
             viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setSize(DensityUtil.dip2px(340), DensityUtil.dip2px(300))//图片大小
                // .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                .build();
        x.image().bind(viewHolder.imageView,carouselItem.getPicUrl(),imageOptions);
        return convertView;
    }

    class ViewHolder{
      ImageView imageView;

    }
}
