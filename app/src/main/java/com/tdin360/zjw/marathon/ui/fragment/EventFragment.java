package com.tdin360.zjw.marathon.ui.fragment;



import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.service.DownloadAPKService;

import com.tdin360.zjw.marathon.ui.activity.MarathonDetailsActivity;
import com.tdin360.zjw.marathon.ui.activity.SearchActivity;
import com.tdin360.zjw.marathon.adapter.MarathonListViewAdapter;
import com.tdin360.zjw.marathon.model.EventModel;

import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;
import com.tdin360.zjw.marathon.utils.db.impl.EventServiceImpl;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;
import com.tdin360.zjw.marathon.weight.pullToControl.PullableListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**马拉松赛事列表
 * Created by Administrator on 2016/8/9.
 */
public class EventFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,UpdateManager.UpdateListener {

    private List<EventModel>list = new ArrayList<>();
    private ListView listView;
    private MarathonListViewAdapter marathonListViewAdapter;
    private int pageNumber=1;
    private int pageCount;
    private TextView loadFail;
    private EventServiceImpl impl;
    private TextView not_found;
    private boolean isLoadFail;
    private PullToRefreshLayout pullToRefreshLayout;

    public static EventFragment newInstance(){

        return new EventFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_event,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         this.impl  = new EventServiceImpl(getContext());
         this.pullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_Layout);
         this.listView= (PullableListView) view.findViewById(R.id.listView);
         this.loadFail = (TextView) view.findViewById(R.id.loadFail);
         this.not_found = (TextView) view.findViewById(R.id.not_found);
         this.marathonListViewAdapter = new MarathonListViewAdapter(getActivity(),list);
         this.listView.setAdapter(marathonListViewAdapter);
         this.pullToRefreshLayout.setOnRefreshListener(this);
         this.listView.setOnItemClickListener(this);


        //搜索
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        //加载数据
         loadData();


        /**
         * 检查更新
         *
         */

        if(NetWorkUtils.isNetworkAvailable(getContext())){

            UpdateManager.checkNewVersion(getContext());
            UpdateManager.setUpdateListener(this);

        }
    }




    //加载数据(包括缓存数据和网络数据)
    private void loadData(){

        /**
         * 判断网络是否处于可用状态
         */
        if(NetWorkUtils.isNetworkAvailable(getActivity())){

            //加载网络数据

            //首次自动刷新
            pullToRefreshLayout.autoRefresh();


        }else {

            //1获取缓存数据
            list  = impl.getAllEvent();
            marathonListViewAdapter.updateList(list);
            not_found.setVisibility(View.GONE);
            //如果获取得到缓存数据则加载本地数据
            if(list.size()==0){
                loadFail.setVisibility(View.VISIBLE);
                //2.如果缓存数据不存在则需要用户打开网络设置

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setMessage("网络不可用，是否打开网络设置");
                alert.setCancelable(false);
                alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //打开网络设置
                        startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

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


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            EventModel eventInfo = (EventModel) parent.getAdapter().getItem(position);
            //为单例成员赋值
            MarathonDataUtils.init().setEventId(eventInfo.getId() + "");
            MarathonDataUtils.init().setEventName(eventInfo.getName());
            MarathonDataUtils.init().setStatus(eventInfo.getStatus());
            MarathonDataUtils.init().setShareUrl(eventInfo.getShardUrl());
            MarathonDataUtils.init().setEventImageUrl(eventInfo.getPicUrl());
            MarathonDataUtils.init().setRegister(eventInfo.isRegister());
            Intent intent = new Intent(getActivity(), MarathonDetailsActivity.class);
            startActivity(intent);

    }


    //加载网络数据

    private void httpRequest(final boolean isRefresh){



        loadFail.setVisibility(View.GONE);
        not_found.setVisibility(View.GONE);
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_HOME);
        params.addQueryStringParameter("pageNumber",pageNumber+"");
        params.addBodyParameter("appKey","eventkeyfdsfds520tdzh123456");
        params.setConnectTimeout(5*1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {



                if(s==null||s.equals("")){
                    return;
                }

                try {
                    impl.deleteAll();
                    if (isRefresh){
                       list.clear();
                    }

//                   Log.d("-------->", "onSuccess: "+s);

                    JSONObject obj = new JSONObject(s);

                    pageCount = obj.getInt("TotalPages");

                    JSONArray array = obj.getJSONArray("EventSystemMessageList");

                    for(int i=0;i<array.length();i++){

                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("Id");
                        String eventName = object.getString("EventName");
                        String status = object.getString("Status");
                        String eventStartTime = object.getString("EventStartTimeStr");
                        String signUpStartTimeStr = object.getString("EventApplyStartTimeStr");

                        String pictureUrl = object.getString("PictureUrl");

                        String shareUrl = object.getString("EventSiteUrl");
                        boolean isRegister = object.getBoolean("IsRegister");
                        EventModel eventModel = new EventModel(id, eventName, status, pictureUrl,signUpStartTimeStr,eventStartTime,shareUrl,isRegister);
                        list.add(eventModel);


                        impl.addEvent(eventModel);

                    }
                    loadFail.setVisibility(View.GONE);
                   if (isRefresh){

                     pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                   }else {
                       pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail.setVisibility(View.VISIBLE);
                    isLoadFail=true;
                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                  Toast.makeText(getActivity(),"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();
                  loadFail.setVisibility(View.VISIBLE);
                  not_found.setVisibility(View.GONE);
                  isLoadFail=true;
                if (isRefresh){

                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }else {
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {


                if(isLoadFail){
                  loadFail.setVisibility(View.GONE);
                }

                if(!isLoadFail&&list.size()==0){

                  not_found.setVisibility(View.VISIBLE);


                }else if(list.size()>0&&!isLoadFail){
                    not_found.setVisibility(View.GONE);

                }

             marathonListViewAdapter.updateList(list);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //没有获取到权限
        if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){



            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            alert.setTitle("温馨提示");
            alert.setMessage("您需要开启存储权限之后才能下载更新!");
            alert.setCancelable(false);
            alert.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(getAppDetailSettingIntent(getContext()));
                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

            return;
        }
           if (requestCode==Constants.WRITE_EXTERNAL_CODE){

               //启动下载器下载安装包

               downloadAPK();


           }
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


        pageNumber=1;
        httpRequest(true);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        if(pageNumber==pageCount){

        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);

        }else if(pageNumber<pageCount){

            pageNumber++;
            httpRequest(false);
        }else {

            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.NOT_MORE);
        }

    }



    private String url;
    @Override
    public void checkFinished(boolean isUpdate, String content, String url) {
        //检查更新

        this.url=url;
        if (isUpdate) {//发现新版本

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            alert.setTitle("版本更新");
            alert.setMessage(Html.fromHtml(content));
            alert.setCancelable(false);

            alert.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



                    //6.0系统兼容
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                        //没有权限就申请
                        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                         requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Constants.WRITE_EXTERNAL_CODE);

                        }else {

                            downloadAPK();
                        }

                    }else {

                        downloadAPK();
                    }


                }
            });

            alert.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });


            alert.show();
        }
    }


    //下载安装包
    private void downloadAPK(){


        Toast.makeText(getActivity(),"已在后台下载",Toast.LENGTH_SHORT).show();
        //启动下载器下载安装包
        Intent intent = new Intent(getActivity(), DownloadAPKService.class);
        intent.putExtra("url",url);
        getActivity().startService(intent);

    }





}
