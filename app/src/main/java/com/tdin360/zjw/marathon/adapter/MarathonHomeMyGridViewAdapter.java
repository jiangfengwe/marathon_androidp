package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CarouselModel;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


/**
 * 赞助商列表
 * Created by Administrator on 2016/6/15.
 */
public class MarathonHomeMyGridViewAdapter extends BaseAdapter {

    private List<CarouselModel>list ;
    private Context context;

    public MarathonHomeMyGridViewAdapter(List<CarouselModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * 更新数据列表
     * @param list
     */
    public void updateList(List<CarouselModel> list){

          this.list=list;
           notifyDataSetChanged();

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
        CarouselModel carouselModel = list.get(position);
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
               //.setSize(DensityUtil.dip2px(340), DensityUtil.dip2px(300))//图片大小
                 //.setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                 .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .setLoadingDrawableId(R.drawable.loading_small_default)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.loading_small_error)//加载失败后默认显示图片
                .build();
        x.image().bind(viewHolder.imageView, carouselModel.getPicUrl(),imageOptions);
        return convertView;
    }

    class ViewHolder{
      ImageView imageView;

    }
}
