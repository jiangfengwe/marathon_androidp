package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.GoodsModel;
import com.tdin360.zjw.marathon.model.NoticeModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.weight.RefreshListView;

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
public class MyGoodsActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private RefreshListView refreshListView;
    private TextView loadFail;
    private List<GoodsModel>list = new ArrayList<>();
    private  MyAdapter myAdapter;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的物资");
        showBackButton();

        initView();

    }

    private void initView() {

        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.refreshListView = (RefreshListView) this.findViewById(R.id.listView);
        this.refreshListView.setOnRefreshListener(this);
        this.myAdapter = new MyAdapter();
        this.refreshListView.setAdapter(myAdapter);
        initHUD();
        loadData();

    }
    /**
     * 初始化提示框
     */
    private void initHUD(){

        //显示提示框
        this.hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(true);
        hud.setAnimationSpeed(1);
        hud.setDimAmount(0.5f);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_goods;
    }

    @Override
    public void onDownPullRefresh() {

        list.clear();

        httpRequest();
    }

    @Override
    public void onLoadingMore() {

        httpRequest();
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

                convertView  = LayoutInflater.from(MyGoodsActivity.this).inflate(R.layout.goods_list_item,null);
                holder = new MyViewHolder();
                holder.eventName = (TextView) convertView.findViewById(R.id.eventName);
                holder.intro = (TextView) convertView.findViewById(R.id.intro);
                holder.btn  = (Button) convertView.findViewById(R.id.btn);
                convertView.setTag(holder);
            }else {

                holder = (MyViewHolder) convertView.getTag();
            }

            GoodsModel model = list.get(position);

            holder.eventName.setText(model.getEventName());
            holder.intro.setText(model.getContent());

            /**
             * 查看物资
             */
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyGoodsActivity.this,MyGoodsDetailsActivity.class);

                    intent.putExtra("id", list.get(position).getId());
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

            hud.show();
            //加载网络数据
            httpRequest();
        } else {


            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
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
    private void httpRequest(){


        RequestParams requestParams = new RequestParams(HttpUrlUtils.MY_GOODS);
        requestParams.addQueryStringParameter("phone","18798004918");


        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {

                try {
                    /*
                     * 获取新闻、公告数据列表
                      */
                    JSONObject json  = new JSONObject(result);

                    Log.d("==========", "onSuccess: "+json);


                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    if(success){

                        JSONArray messageList = json.getJSONArray("MaterilasMessageList");


                        for (int i=0;i<messageList.length();i++){

                            JSONObject object = messageList.getJSONObject(i);
                            int id = object.getInt("Id");
                            String eventName = object.getString("EventName");
                            String content = object.getString("MaterilasContent");
                            boolean isApply = object.getBoolean("IsApply");
                            list.add(new GoodsModel(id,eventName,content,isApply));
                        }


                    }else {

                        Toast.makeText(x.app(),reason,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    loadFail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


                Toast.makeText(MyGoodsActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();

                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                //判断是否有数据
//                if(noticeModelList.size()>0){
//
//                    tipNotData.setVisibility(View.GONE);
//                    noticeListViewAdapter.updateListView(noticeModelList);
//                }else {
//                    tipNotData.setVisibility(View.VISIBLE);
//                }

                 hud.dismiss();

                refreshListView.hideHeaderView();
                refreshListView.hideFooterView();
                myAdapter.notifyDataSetChanged();


            }
        });

    }



}
