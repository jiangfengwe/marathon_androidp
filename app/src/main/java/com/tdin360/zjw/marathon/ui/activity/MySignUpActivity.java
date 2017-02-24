package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的报名列表
 */
public class MySignUpActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private RefreshListView listView;
    private LinearLayout loading;
    private TextView loadFail;
    private List<Integer> list = new ArrayList<>();
    private int count;
    private  MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的报名");
        showBackButton();
        initView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_sign_up;
    }

    private void initView() {
        this.listView = (RefreshListView) this.findViewById(R.id.refreshListView);
        this.listView.setOnRefreshListener(this);
        this.loading  = (LinearLayout) this.findViewById(R.id.loading);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MySignUpActivity.this,MySigUpDetailActivity.class);
                startActivity(intent);


            }
        });

        this.myAdapter = new MyAdapter();
        this.listView.setAdapter(myAdapter);



        loadData();
    }


    private class MyAdapter extends BaseAdapter{


            @Override
            public int getCount() {
                return count;
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
                    convertView=View.inflate(MySignUpActivity.this,R.layout.my_signup_list_item,null);
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
                private ImageView imageView;
                private TextView matchTime;
                private TextView matchName;
                private TextView matchAchievement;
                private TextView isPay;
            }
        }
   
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {

        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
            httpRequest();
        } else {

            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据
            loading.setVisibility(View.GONE);

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
    @Override
    public void onDownPullRefresh() {

        httpRequest();

    }

    @Override
    public void onLoadingMore() {

        httpRequest();
    }


    /**
     * 请求网络数据
     */
    private void httpRequest() {

        loadFail.setVisibility(View.GONE);
        RequestParams params = new RequestParams(HttpUrlUtils.Marath_ALL_Envent);


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d(" -------->>>", "onSuccess: " + result);

                count=10;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MySignUpActivity.this, "网络错误或访问服务器出错!", Toast.LENGTH_SHORT).show();
                loadFail.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                loading.setVisibility(View.GONE);
                listView.hideHeaderView();
                listView.hideFooterView();
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
