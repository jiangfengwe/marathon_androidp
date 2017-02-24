package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

/**
 * 我的成绩
 */
public class MyAchievementActivity extends BaseActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的成绩");
       showBackButton();
        initView();
    }


    @Override
    public int getLayout() {
        return R.layout.activity_achievement;
    }


    private void initView() {
        this.listView = (ListView) this.findViewById(R.id.listView);
        this.listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder viewHolder;

                if(convertView==null){

                    viewHolder = new ViewHolder();
                    convertView=View.inflate(MyAchievementActivity.this,R.layout.achievement_list_item,null);
                    viewHolder.matchTime= (TextView) convertView.findViewById(R.id.time);
                    viewHolder.matchName= (TextView) convertView.findViewById(R.id.matchName);
                    viewHolder.matchAchievement= (TextView) convertView.findViewById(R.id.projectName);
                    convertView.setTag(viewHolder);
                }else {

                    viewHolder= (ViewHolder) convertView.getTag();
                }

                return convertView;
            }

           class ViewHolder{

                private TextView matchTime;
                private TextView matchName;
                private TextView matchAchievement;
            }
        });
    }
}
