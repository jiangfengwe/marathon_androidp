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
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.CommonAdapter;
import com.tdin360.zjw.marathon.adapter.ViewHolder;
import com.tdin360.zjw.marathon.service.DownloadAPKService;
import com.tdin360.zjw.marathon.ui.activity.MarathonDetailsActivity;
import com.tdin360.zjw.marathon.ui.activity.SearchActivity;
import com.tdin360.zjw.marathon.model.EventModel;
import com.tdin360.zjw.marathon.ui.activity.WebActivity;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.MessageEvent;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.UpdateManager;
import com.tdin360.zjw.marathon.utils.db.impl.EventServiceImpl;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.tdin360.zjw.marathon.weight.pullToControl.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**赛事列表
 * Created by Administrator on 2016/8/9.
 */
public class EventFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,UpdateManager.UpdateListener {

    private List<EventModel>list = new ArrayList<>();
    @ViewInject(R.id.listView)
    private ListView listView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;
    private EventAdapter marathonListViewAdapter;
    private int pageNumber=1;
    private int pageCount;
    private EventServiceImpl impl;
    @ViewInject(R.id.pull_Layout)
    private PullToRefreshLayout pullToRefreshLayout;
    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;

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
          EventBus.getDefault().register(this);
         this.impl  = new EventServiceImpl(getContext());
         this.marathonListViewAdapter = new EventAdapter(getActivity(),list,R.layout.marathon_list_item);
         this.listView.setAdapter(marathonListViewAdapter);
         this.pullToRefreshLayout.setOnRefreshListener(this);
         this.listView.setOnItemClickListener(this);

        this.mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {

                if(mode== ErrorView.ViewShowMode.NOT_NETWORK){

                   pullToRefreshLayout.autoRefresh();
                }
            }
        });

        TextView titleTv = (TextView) view.findViewById(R.id.toolbar_title);
        titleTv.setText("赛事");
        this.rightImage.setImageResource(R.drawable.search_big);
           this.rightImage.setVisibility(View.VISIBLE);

        //搜索
            this.rightImage.setOnClickListener(new View.OnClickListener() {
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
            marathonListViewAdapter.update(list);

//            Log.d("------local--->", "loadData: "+list.get(0).isWebPage());

            //如果获取得到缓存数据则加载本地数据
            if(list.size()==0){

                //2.如果缓存数据不存在则需要用户打开网络设置

               mErrorView.show(pullToRefreshLayout,"没有数据,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);

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

           if(eventInfo.isWebPage()){

               Intent intent = new Intent(getContext(), WebActivity.class);
               intent.putExtra("url",eventInfo.getShardUrl());
               intent.putExtra("imageUrl",eventInfo.getPicUrl());
               startActivity(intent);

           }else {
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

    }

    /**
     * 数据适配器
     */

    class EventAdapter extends CommonAdapter<EventModel>{


        public EventAdapter(Context context, List<EventModel> list, @LayoutRes int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        protected void onBind(ViewHolder holder, EventModel model) {

            holder.setText(R.id.eventName,model.getName());
            holder.setText(R.id.signUpTime,model.getSignUpStartTime());
            holder.setText(R.id.eventTime,model.getStartDate());
            holder.setText(R.id.status,model.getStatus());
            ImageView imageView = holder.getViewById(R.id.imageView);
            x.image().bind(imageView,model.getPicUrl());
            if(model.getStatus().contains("结束")){

             holder.getViewById(R.id.status).setEnabled(false);
            }else {

              holder.getViewById(R.id.status).setEnabled(true);
            }


        }
    }

    //加载网络数据

    private void httpRequest(final boolean isRefresh){


        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_HOME);
        params.addQueryStringParameter("pageNumber",pageNumber+"");
        params.addBodyParameter("appKey","eventkeyfdsfds520tdzh123456");
        params.setConnectTimeout(5*1000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

//                Log.d("-------->", "onSuccess: "+s);

                if(s==null||s.equals("")){
                    return;
                }

                try {
                    impl.deleteAll();
                    if (isRefresh){
                       list.clear();
                    }



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
                        boolean showPage = object.getBoolean("IsShowPage");

                        EventModel eventModel = new EventModel(id, eventName, status, pictureUrl,signUpStartTimeStr,eventStartTime,shareUrl,isRegister,showPage);
                        list.add(eventModel);

                        impl.addEvent(eventModel);

                    }

                   if (isRefresh){

                     pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                   }else {
                       pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                   }


                    if(list.size()<=0){

                        mErrorView.show(pullToRefreshLayout,"暂时还没有数据",ErrorView.ViewShowMode.NOT_DATA);
                    }else {

                        mErrorView.hideErrorView(pullToRefreshLayout);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();


                    if (isRefresh){

                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }else {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                    }

                    mErrorView.show(pullToRefreshLayout,"服务器数据异常",ErrorView.ViewShowMode.ERROR);

                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

                if(list.size()==0) {
                    mErrorView.show(pullToRefreshLayout, "加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                }
                ToastUtils.show(getActivity(),"网络不给力,连接服务器异常!");
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

             marathonListViewAdapter.update(list);
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

                    CommonUtils.getAppDetailSettingIntent(getActivity());
                }
            });

            alert.show();

            return;
        }
           if (requestCode==Constants.WRITE_EXTERNAL_CODE&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

               //启动下载器下载安装包

               downloadAPK();

           }
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
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

                        downloadAPK();
                    }else {

                    //没有权限就申请
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_CODE);

                    } else {

                        downloadAPK();
                    }

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

    private ProgressBar progressBar;
    private TextView currentTv;
    private TextView totalTv;
    private AlertDialog dialog;
    //下载安装包
    private void downloadAPK(){


        if(url==null&&url.equals("")){

            Toast.makeText(getActivity(),"更新包无法下载",Toast.LENGTH_SHORT).show();
            return;
        }

        //启动下载器下载安装包
        Intent intent = new Intent(getActivity(), DownloadAPKService.class);
        intent.putExtra("url",url);
        getActivity().startService(intent);


        /**
         * 下载更新进度提示框
         */
        this.dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setCancelable(false);
        View view = View.inflate(getActivity(), R.layout.update_download_dialog,null);
        this.progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        this.currentTv= (TextView) view.findViewById(R.id.currentTv);
        this.totalTv= (TextView) view.findViewById(R.id.totalTv);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();

    }


    /**
     * 下载更新进度条处理
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateDownload(MessageEvent event){

        if(event.getType()== MessageEvent.MessageType.DOWNLOAD_UPDATE) {
            progressBar.setMax((int) event.getTotalSize());
            progressBar.setProgress((int) event.getCurrentSize());
            totalTv.setText(CommonUtils.FormatFileSize(event.getTotalSize()));
            currentTv.setText(CommonUtils.FormatFileSize(event.getCurrentSize()));
            //下载完成
            if(event.getCurrentSize()==event.getTotalSize()){

                 if(dialog!=null){

                   dialog.dismiss();
                 }


            }

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
