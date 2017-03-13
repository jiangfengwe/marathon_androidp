package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.StepModel;
import com.tdin360.zjw.marathon.utils.db.impl.StepServiceImpl;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 计步历史纪录
 * @author zhangzhijun
 */
public class StepHistoryActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private RefreshListView refreshListView;
    private StepServiceImpl stepService;
    private List<StepModel>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("历史纪录");
        showBackButton();


        initView();
    }

    private void initView() {

        this.stepService = new StepServiceImpl(getApplicationContext());
        this.refreshListView = (RefreshListView) this.findViewById(R.id.refreshListView);
        this.refreshListView.setOnRefreshListener(this);
        loadData();
        this.refreshListView.setAdapter(new MyAdapter());
    }

    //加载数据
    private void loadData(){

        list = this.stepService.getAllStep();

         refreshListView.hideHeaderView();
        refreshListView.hideFooterView();
    }
    @Override
    public int getLayout() {
        return R.layout.activity_step_history;
    }

    @Override
    public void onDownPullRefresh() {
        loadData();
    }

    @Override
    public void onLoadingMore() {
        loadData();

    }


    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return  list.size();
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


            MyViewHolder viewHolder=null;

            if(convertView==null){

                viewHolder = new MyViewHolder();
                convertView = LayoutInflater.from(StepHistoryActivity.this).inflate(R.layout.step_history_list_item,null);
                convertView.setTag(viewHolder);
                viewHolder.year = (TextView) convertView.findViewById(R.id.year);
                viewHolder.day = (TextView) convertView.findViewById(R.id.day);
                viewHolder.stepCount= (TextView) convertView.findViewById(R.id.stepCount);
                viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
                viewHolder.kcal = (TextView) convertView.findViewById(R.id.kcal);

            }else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }

            StepModel model = list.get(position);

            viewHolder.year.setText(model.getYear());
            viewHolder.day.setText(model.getDay());
            viewHolder.stepCount.setText(model.getTotalStep()+"步");
            viewHolder.distance.setText((model.getTotalDistance()*0.001)+"km");
            viewHolder.kcal.setText(model.getTotalKcal()+"kcal");

            return convertView;
        }

        class MyViewHolder{

            private TextView year;
            private TextView day;
            private TextView stepCount;
            private TextView distance;
            private TextView kcal;


        }
    }
}
