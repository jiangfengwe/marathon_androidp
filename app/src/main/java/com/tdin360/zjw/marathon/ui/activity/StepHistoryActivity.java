package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.StepModel;
import com.tdin360.zjw.marathon.step.StepUtils;
import com.tdin360.zjw.marathon.utils.db.impl.StepServiceImpl;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * 计步历史纪录
 * @author zhangzhijun
 */
public class StepHistoryActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener{

    private ListView refreshListView;
    private StepServiceImpl stepService;
    private List<StepModel>list=new ArrayList<>();
    private TextView empty;
    private  MyAdapter adapter;
    private PullToRefreshLayout pullToRefreshLayout;
    private int totalPages;
    private int pageNumber=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("历史纪录");
        showBackButton();
        initView();

    }

    private void initView() {

        this.stepService = new StepServiceImpl(getApplicationContext());
        this.refreshListView = (ListView) this.findViewById(R.id.listView);
        this.pullToRefreshLayout = (PullToRefreshLayout) this.findViewById(R.id.pull_Layout);
        this.pullToRefreshLayout.setOnRefreshListener(this);
        this.empty = (TextView) this.findViewById(R.id.empty);
        this.adapter = new MyAdapter();
        this.refreshListView.setAdapter(adapter);
        this.registerForContextMenu(refreshListView);

        pullToRefreshLayout.autoRefresh();

    }

    //加载数据
    private void loadData(boolean isRefresh){

        list = this.stepService.getAllStep();
        adapter.notifyDataSetChanged();
        //没有记录
        if(list!=null&&list.size()==0){

            empty.setVisibility(View.VISIBLE);
        }

        if (isRefresh){

            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }else {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }
    @Override
    public int getLayout() {
        return R.layout.activity_step_history;
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


        pageNumber=1;
        loadData(true);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        //上拉加载更多
        if(pageNumber==totalPages){

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);

        }else {
            pageNumber++;
            loadData(false);

        }
    }



    /**
     * 数据适配器
     */
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
            viewHolder.distance.setText((StepUtils.formatDouble(model.getTotalDistance()))+"m");
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("操作");
        menu.add(0,1,0,"删除选项");
        menu.add(0,2,0,"全部删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){

            case 1://删除选项

              boolean isOk = this.stepService.deleteStepById(list.get((int)menuInfo.id).getId());

                if(isOk){
                    Toast.makeText(StepHistoryActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                    loadData(true);
                }

                break;
            case 2://全部删除
               boolean isSuccess = this.stepService.deleteAll();

                if(isSuccess){
                    Toast.makeText(StepHistoryActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                    loadData(true);
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
