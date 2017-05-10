package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.GoodsModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 领取物资
 * @author zhangzhijun
 */
public class MyGoodsListActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener{

    private ListView refreshListView;
    private TextView loadFail;
    private List<GoodsModel>list = new ArrayList<>();
    private  MyAdapter myAdapter;
    private TextView not_found;
    private boolean isLoadFail;
    private int totalPages;
    private int pageNumber=1;
    private PullToRefreshLayout pullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的物资");
        showBackButton();

        initView();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_goods;
    }

    private void initView() {

        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.not_found = (TextView) this.findViewById(R.id.not_found);
        this.refreshListView = (ListView) this.findViewById(R.id.listView);
        this.pullToRefreshLayout = (PullToRefreshLayout) this.findViewById(R.id.pull_Layout);
        this.pullToRefreshLayout.setOnRefreshListener(this);
        this.myAdapter = new MyAdapter();
        this.refreshListView.setAdapter(myAdapter);

        loadData();

    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


        pageNumber=1;
        httpRequest(true);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        //上拉加载更多
        if(pageNumber==totalPages){

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);

        }else if(pageNumber<totalPages){
            pageNumber++;
            httpRequest(false);

        }else {

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);
        }
    }

    /**
     * 数据适配器
     */

    private class MyAdapter extends BaseAdapter{


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
        public View getView(final int position, View convertView, ViewGroup parent) {

            MyViewHolder holder =null;

            if(convertView==null){

                convertView  = LayoutInflater.from(MyGoodsListActivity.this).inflate(R.layout.goods_list_item,null);
                holder = new MyViewHolder();
                holder.eventName = (TextView) convertView.findViewById(R.id.eventName);
                holder.intro = (TextView) convertView.findViewById(R.id.intro);
                holder.btn  = (Button) convertView.findViewById(R.id.btn);
                convertView.setTag(holder);
            }else {

                holder = (MyViewHolder) convertView.getTag();
            }

            final GoodsModel model = list.get(position);

            holder.eventName.setText(model.getEventName());
            holder.intro.setText(model.getContent().equals("null")?"":model.getContent());

            /**
             * 查看物资
             */
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyGoodsListActivity.this,MyGoodsDetailsActivity.class);

                    intent.putExtra("model",model);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class MyViewHolder{


            private TextView eventName;
            private TextView intro;
            private Button btn;

        }
    }

    //加载数据(包括缓存数据和网络数据)
    private void loadData() {

        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {


            //加载网络数据
             pullToRefreshLayout.autoRefresh();
        } else {


            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据


            //如果缓存数据不存在则需要用户打开网络设置

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setMessage("网络不可用，是否打开网络设置");
            alert.setCancelable(false);
            alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //打开网络设置

                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            alert.show();
        }
    }


    /**
     * 请求网络数据
     */
    private void httpRequest(final boolean isRefresh){


        loadFail.setVisibility(View.GONE);
        RequestParams requestParams = new RequestParams(HttpUrlUtils.MY_GOODS);
        requestParams.addQueryStringParameter("phone", SharedPreferencesManager.getLoginInfo(this).getName());
        requestParams.addBodyParameter("appKey",HttpUrlUtils.appKey);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {


                try {

                    if(isRefresh){

                        list.clear();
                    }
                    JSONObject json  = new JSONObject(result);

//                    Log.d("物资-------->>>", "onSuccess: "+json);
                    totalPages = json.getInt("TotalPages");
                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    if(success){

                        JSONArray messageList = json.getJSONArray("MaterilasMessageList");


                        for (int i=0;i<messageList.length();i++){

                            JSONObject object = messageList.getJSONObject(i);
                            int id = object.getInt("Id");
                            String name = object.getString("Name");
                            String gender = object.getString("Gender");
                            String number = object.getString("CompetitionNumber");
                            String documentNumber = object.getString("DocumentNumber");
                            String size = object.getString("MaterilasSize");
                            String eventName = object.getString("EventName");
                            String content = object.getString("MaterilasContent");
                            boolean isApply = object.getBoolean("IsApply");
                            String goodsInfo = object.getString("GoodsInfo");

                            list.add(new GoodsModel(id,name,gender,number,documentNumber,eventName,content,size,isApply,goodsInfo));
                        }


                    }else {

                        Toast.makeText(x.app(),reason,Toast.LENGTH_SHORT).show();
                    }

                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                    }
                    isLoadFail=true;
                    not_found.setVisibility(View.GONE);
                    loadFail.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (isRefresh){

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }else {
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
                isLoadFail=true;
                loadFail.setVisibility(View.VISIBLE);
                not_found.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


                    //判断是否有数据
                    if ((list.size() > 0&&!isLoadFail)) {
                        not_found.setVisibility(View.VISIBLE);

                    } else {
                        not_found.setVisibility(View.GONE);
                    }

                myAdapter.notifyDataSetChanged();


            }
        });

    }



}
