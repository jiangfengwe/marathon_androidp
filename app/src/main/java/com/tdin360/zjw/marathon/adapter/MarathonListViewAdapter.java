package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.EventModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 马拉松赛列表
 * Created by Administrator on 2016/8/10.
 */
public class MarathonListViewAdapter extends BaseAdapter {


    private List<EventModel>list;

    private LayoutInflater inflater;
    public MarathonListViewAdapter(Context context, List<EventModel> list) {

        this.inflater=LayoutInflater.from(context);
        this.list = list;

    }

public void updateList( List<EventModel> list){

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

        ViewHolder viewHolder =null;

        if(convertView==null){
             viewHolder = new ViewHolder();
             convertView =inflater.inflate(R.layout.marathon_list_item,parent,false);
             viewHolder.eventName = (TextView) convertView.findViewById(R.id.eventName);
             viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
             viewHolder.eventTime = (TextView) convertView.findViewById(R.id.eventTime);
             viewHolder.signUpTime = (TextView) convertView.findViewById(R.id.signUpTime);
             viewHolder.status = (TextView) convertView.findViewById(R.id.status);

             convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        EventModel eventModel = list.get(position);
        viewHolder.eventName.setText(eventModel.getName());
        viewHolder.signUpTime.setText(eventModel.getSignUpStartTime());
        viewHolder.eventTime.setText(eventModel.getStartDate());
        //处理图片
        ImageOptions imageOptions = new ImageOptions.Builder()
                //.setSize(DensityUtil.dip2px(1000), DensityUtil.dip2px(320))//图片大小
                .setLoadingDrawableId(R.drawable.loading_banner_default)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.loading_banner_error)//加载失败后默认显示图片
                .build();
        x.image().bind(viewHolder.imageView, eventModel.getPicUrl(),imageOptions);

        viewHolder.status.setText(eventModel.getStatus());

        if(eventModel.getStatus().equals("已结束")){
            viewHolder.status.setEnabled(false);
        }else {
            viewHolder.status.setEnabled(true);
        }



         return convertView;
    }
    class ViewHolder{

        private TextView eventName;
        private ImageView imageView;
        private TextView signUpTime;
        private TextView eventTime;
        private TextView status;

    }

    /**
     *  毫秒转化  格式化倒计时格式
     * @param ms 毫秒
     * @return
     */

    private static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strDay+" 天 "+strHour+" 时 "+strMinute + " 分 " + strSecond + " 秒";
    }
    /**
     * 倒计时
     */
    private Handler handlerTimer = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:

                    boolean isNeedCountTime = false;
                    //①：其实在这块需要精确计算当前时间
                    for(int index =0;index<list.size();index++){



                        EventModel eventModel = list.get(index);
                        long time = eventModel.getTime();

                        if(time>1000){//判断是否还有条目能够倒计时，如果能够倒计时的话，延迟一秒，让它接着倒计时
                            isNeedCountTime = true;
                            eventModel.setTime(time-1000);
                        }else{
                            eventModel.setTime(0);
                        }

                    }
                    //②：for循环执行的时间
                    notifyDataSetChanged();
                    if(isNeedCountTime){
                        //TODO 然后用1000-（②-①），就赢延迟的时间
                        handlerTimer.sendEmptyMessageDelayed(1,1000);

                    }
                    break;
            }

        }

    };
}
