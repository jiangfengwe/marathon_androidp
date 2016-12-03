package com.tdin360.zjw.marathon.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.MarathonInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MarathonListViewAdapter extends BaseAdapter {

    private Context context;
    private List<MarathonInfo>list;

    public MarathonListViewAdapter(Context context, List<MarathonInfo> list) {
        this.context = context;
        this.list = list;
        handlerTimer.sendEmptyMessage(1);
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
             convertView =View.inflate(context, R.layout.marathon_list_item,null);
             viewHolder.time= (TextView) convertView.findViewById(R.id.time);
             convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
          viewHolder.time.setText(getTimeInfo(list.get(position).getTime()));
         return convertView;
    }
    class ViewHolder{

        TextView time;
    }

    /**
    格式化倒计时格式
     **/
    private String getTimeInfo(long time){
        long mDay=time/(1000*60*60*24);//计算天数
        Date d = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("HH 时 mm 分 ss 秒");
        return mDay+" 天 "+format.format(d);

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

                        MarathonInfo marathonInfo = list.get(index);
                        long time = marathonInfo.getTime();
                        if(time>1000){//判断是否还有条目能够倒计时，如果能够倒计时的话，延迟一秒，让它接着倒计时
                            isNeedCountTime = true;
                            marathonInfo.setTime(time-1000);
                        }else{
                            marathonInfo.setTime(0);
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
