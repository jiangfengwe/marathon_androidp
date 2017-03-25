package com.tdin360.zjw.marathon.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.FeedBackModel;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.utils.HeadImageUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 *  意见反馈列表
 * @author zhangzhijun
 */
public class FeedbackListActivity extends BaseActivity  implements RefreshListView.OnRefreshListener{

    private RefreshListView listView;
    private List<FeedBackModel>list = new ArrayList<>();
    private int totalPages;
    private HeadImageUtils utils;
    private MyAdapter myAdapter;
    private int pageNumber=1;
    private TextView not_found;
    private TextView loadFail;
    private boolean isLoadFail;
    public static String ACTION="reLoad";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        setToolBarTitle("我的意见反馈");
        initView();
        register();

    }

    private void initView() {

        this.utils = new HeadImageUtils(this);
        this.listView = (RefreshListView) this.findViewById(R.id.listView);
        this.listView.setOnRefreshListener(this);
        this.not_found = (TextView) this.findViewById(R.id.not_found);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.myAdapter = new MyAdapter();
        this.listView.setAdapter(myAdapter);
        initHUD();
        httpRequest();

    }
    private KProgressHUD hud;


    private void initHUD(){

        //显示提示框
        hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(true);
        hud.setAnimationSpeed(1);
        hud.setDimAmount(0.5f);
        hud.show();


    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//
//        menu.setHeaderTitle("操作");
//        menu.add(0,1,0,"删除选项");
//        menu.add(0,2,0,"全部删除");
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//
//            case 1:
//                break;
//            case 2:
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }

    @Override
    public int getLayout() {
        return R.layout.activity_list_feedback;
    }


    //添加新的意见反馈
    public void add(View view) {

        Intent intent  = new Intent(FeedbackListActivity.this,AddFeedbackActivity.class);
       startActivity(intent);
    }

    @Override
    public void onDownPullRefresh() {

        list.clear();
        pageNumber=1;

        httpRequest();

    }

    @Override
    public void onLoadingMore() {


        if(pageNumber<totalPages){

           pageNumber++;
            httpRequest();
        }else {
            listView.hideFooterView();

        }


    }

    /**
     * 获取网络数据
     */
    private void httpRequest(){

        loadFail.setVisibility(View.GONE);
        RequestParams params  =new RequestParams(HttpUrlUtils.FEED_LIST);
        params.addQueryStringParameter("phone", SharedPreferencesManager.getLoginInfo(FeedbackListActivity.this).getName());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("PageNumber",pageNumber+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);

                    totalPages = json.getInt("TotalPages");

                    JSONArray messageList = json.getJSONArray("FeedbackMessageList");

                    for(int i=0;i<messageList.length();i++){

                        JSONObject obj = messageList.getJSONObject(i);
                        String content = obj.getString("FeedbackContent");
                        String createTimeStr = obj.getString("CreateTimeStr");
                        String replyContent = obj.getString("ReplyContent");
                        String timeStr = obj.getString("FeedbackTimeStr");
                        list.add(new FeedBackModel(createTimeStr,content,timeStr,replyContent));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    isLoadFail=true;
                    loadFail.setVisibility(View.VISIBLE);
                    not_found.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                isLoadFail=true;
                loadFail.setVisibility(View.VISIBLE);
                not_found.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();

                listView.hideHeaderView();
                listView.hideFooterView();
                myAdapter.notifyDataSetChanged();
                if(!isLoadFail) {
                    if (list.size() == 0) {
                        not_found.setVisibility(View.VISIBLE);
                    } else {

                        not_found.setVisibility(View.GONE);
                    }
                }

            }
        });

    }

    /**
     * 展示反馈列表以及回复列表
     */
    private class MyAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return list==null? 0:list.size();
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

            MyViewHolder holder=null;

            if(convertView==null){

                holder = new MyViewHolder();
                convertView = LayoutInflater.from(FeedbackListActivity.this).inflate(R.layout.feedback_list_item,null);

                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                LoginModel info = SharedPreferencesManager.getLoginInfo(FeedbackListActivity.this);
                Bitmap image = utils.getImage(info.getName());
                if(image==null){

                  image = BitmapFactory.decodeResource(getResources(),R.drawable.signup_photo);
                }
                holder.image.setImageBitmap(image);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.content  = (TextView) convertView.findViewById(R.id.content);
                holder.answerContent = (TextView) convertView.findViewById(R.id.answerContent);
                holder.answerTime = (TextView) convertView.findViewById(R.id.answerTime);
                holder.isAnswer = (LinearLayout) convertView.findViewById(R.id.isAnswer);

                convertView.setTag(holder);
            }else {

               holder = (MyViewHolder) convertView.getTag();
            }


            FeedBackModel model = list.get(position);
            holder.time.setText(model.getTime());
            holder.content.setText(model.getContent());
            //如果答复就显示答复内容，否则不显示
            if(!model.getFromContent().equals("null")&&!model.equals("")){

                holder.answerContent.setText(model.getFromContent());
                holder.answerTime.setText(model.getFromTime());
                holder.isAnswer.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class MyViewHolder{

            private ImageView image;
            private TextView time;
            private TextView content;
            private LinearLayout isAnswer;
            private TextView answerContent;
            private TextView answerTime;
        }
    }


    /**
     * 用广播接收更新通知
     */
    private MyBroadcastReceiver receiver;
    private void register(){this.receiver = new MyBroadcastReceiver();

        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver,filter);

    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            list.clear();
            httpRequest();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }
}
