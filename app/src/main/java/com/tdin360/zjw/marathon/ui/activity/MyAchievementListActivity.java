package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.MarkModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的成绩列表
 */
public class MyAchievementListActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener {

    private ListView listView;
    private TextView loadFail;
    private List<MarkModel> data = new ArrayList<>();
    private MyAdapter adapter;

    private TextView not_found;
    private boolean isLoadFail;
    private int totalPages;
    private int pageNumber=1;
    private PullToRefreshLayout pullToRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的成绩");
       showBackButton();
        initView();
    }


    private void initView() {
        this.listView = (ListView) this.findViewById(R.id.listView);
        this.pullToRefreshLayout = (PullToRefreshLayout) this.findViewById(R.id.pull_Layout);
        this.pullToRefreshLayout.setOnRefreshListener(this);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.not_found = (TextView) this.findViewById(R.id.not_found);
        this.adapter = new MyAdapter();
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MyAchievementListActivity.this,MyAchievementDetailsActivity.class);
                intent.putExtra("model",(MarkModel)parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });


        loadData();
    }



    private class MyAdapter extends BaseAdapter{


            @Override
            public int getCount() {
                return data==null?0:data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder viewHolder;

                if(convertView==null){

                    viewHolder = new ViewHolder();
                    convertView=View.inflate(MyAchievementListActivity.this,R.layout.my_achievement_list_item,null);
                     viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                     viewHolder.matchName = (TextView) convertView.findViewById(R.id.eventName);
                     viewHolder.projectName = (TextView) convertView.findViewById(R.id.projectName);
                     viewHolder.qiang = (TextView) convertView.findViewById(R.id.qiang);
                     viewHolder.jing = (TextView) convertView.findViewById(R.id.jing);
                    viewHolder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
                    Animation animation = AnimationUtils.loadAnimation(MyAchievementListActivity.this, R.anim.arrow);
                    viewHolder.arrow.startAnimation(animation);
                    convertView.setTag(viewHolder);
                }else {

                    viewHolder= (ViewHolder) convertView.getTag();
                }

                MarkModel model = data.get(position);
                ImageOptions imageOptions = new ImageOptions.Builder()
                        // .setSize(DensityUtil.dip2px(340), DensityUtil.dip2px(300))//图片大小
                        // .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        .setLoadingDrawableId(R.drawable.loading_banner_default)//加载中默认显示图片
                        .setUseMemCache(true)//设置使用缓存
                        .setFailureDrawableId(R.drawable.loading_banner_error)//加载失败后默认显示图片
                        .build();
                 x.image().bind(viewHolder.imageView,model.getPictureUrl(),imageOptions);
                 viewHolder.matchName.setText(model.getEventName());
                 viewHolder.projectName.setText(model.getCompetitorType());

                 viewHolder.qiang.setText(model.getQiang().equals("null")||model.getQiang().equals("")?"00:00:00":model.getQiang());
                 viewHolder.jing.setText(model.getJing().equals("null")||model.getJing().equals("")?"00:00:00":model.getJing());

                return convertView;
            }



            class ViewHolder{

                private ImageView imageView;
                private TextView matchName;
                private TextView projectName;
                private TextView qiang;
                private TextView jing;
                private ImageView arrow;
            }
        }



    @Override
    public int getLayout() {
        return R.layout.activity_achievement;
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
     * 请求网络数据
     */
    private void httpRequest(final boolean isRefresh){

        loadFail.setVisibility(View.GONE);
        RequestParams requestParams = new RequestParams(HttpUrlUtils.MARK_SEARCH);
        requestParams.addBodyParameter("appKey",HttpUrlUtils.appKey);
         requestParams.addBodyParameter("phone", SharedPreferencesManager.getLoginInfo(getApplicationContext()).getName());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {


                try {

                    if(isRefresh){

                        data.clear();
                    }
                    JSONObject json = new JSONObject(result);
                    totalPages = json.getInt("TotalPages");
                    JSONObject message = json.getJSONObject("EventMobileMessage");

//                     Log.d("--------->>>>>>", "onSuccess: "+json);
                    boolean success = message.getBoolean("Success");

                    if(success){

                        JSONArray list = json.getJSONArray("GradeMessageList");


                         for (int i=0;i<list.length();i++){


                             JSONObject item = list.getJSONObject(i);

                             String name = item.getString("CompetitorName");
                             String number = item.getString("CompetitorJoinNumber");
                             String competitorRank = item.getString("CompetitorRank");
                             String competitorAmount = item.getString("CompetitorAmount");
                             String gradeJudge = item.getString("GradeJudge");
                             String eventName = item.getString("EventName");
                             String eventOrganizer = item.getString("EventOrganizer");
                             String pictureUrl = item.getString("PictureUrl");
                             boolean sex = item.getBoolean("CompetitorSex");
                             String competitorType = item.getString("CompetitorType");
                             String countryOrRegion = item.getString("CountryOrRegion");
                             String km5Qiang = item.getString("KM5Qiang");
                             String km5Jing = item.getString("KM5Jing");
                             String km10Qiang = item.getString("KM10Qiang");
                             String km10Jing = item.getString("KM10Jing");
                             String km137Qiang = item.getString("KM137Qiang");
                             String km137Jing = item.getString("KM137Jing");
                             String km18Qiang = item.getString("KM18Qiang");
                             String km18Jing = item.getString("KM18Jing");
                             String km20Qiang = item.getString("KM20Qiang");
                             String km20Jing = item.getString("KM20Jing");
                             String km210975Qiang = item.getString("KM210975Qiang");
                             String km210975Jing = item.getString("KM210975Jing");
                             String km215Qiang = item.getString("KM215Qiang");
                             String km215Jing = item.getString("KM215Jing");
                             String km25Qiang = item.getString("KM25Qiang");
                             String km25Jing = item.getString("KM25Jing");
                             String km29Qiang = item.getString("KM29Qiang");
                             String km29Jing = item.getString("KM29Jing");
                             String km35Qiang = item.getString("KM35Qiang");
                             String km35Jing = item.getString("KM35Jing");
                             String km376Qiang = item.getString("KM376Qiang");
                             String km376Jing = item.getString("KM376Jing");
                             String km40Qiang = item.getString("KM40Qiang");
                             String km40Jing = item.getString("KM40Jing");
                             String km42195Qiang = item.getString("KM42195Qiang");
                             String km42195Jing = item.getString("KM42195Jing");
                             String qiang = item.getString("Qiang");
                             String jing = item.getString("Jing");

                             data.add(new MarkModel(name,number,competitorRank,competitorAmount,gradeJudge,eventName,eventOrganizer,pictureUrl,sex,competitorType,
                                     countryOrRegion,km5Qiang,km5Jing,km10Qiang,km10Jing,km137Qiang,km137Jing,km18Qiang,km18Jing,km20Qiang,km20Jing,
                                     km210975Qiang,km210975Jing,km215Qiang,km215Jing,km25Qiang,km25Jing,km29Qiang,km29Jing,km35Qiang,km35Jing,km376Qiang,
                                     km376Jing,km40Qiang,km40Jing,km42195Qiang,km42195Jing,qiang,jing));


                         }


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
                    loadFail.setVisibility(View.GONE);
                    isLoadFail=true;
                    not_found.setVisibility(View.GONE);
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

                adapter.notifyDataSetChanged();

                if(!isLoadFail) {
                    if (data.size() == 0) {

                        not_found.setText("目前还没有任何成绩记录!");
                        not_found.setVisibility(View.VISIBLE);
                    } else {

                        not_found.setVisibility(View.GONE);
                    }
                }


            }
        });

    }


}
