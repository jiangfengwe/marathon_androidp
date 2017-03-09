package com.tdin360.zjw.marathon.ui.activity;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.MapPointNode;
import com.tdin360.zjw.marathon.model.MapProjectModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 比赛地图（参赛路线）
 * @author zhangzhijun
 *
 */
public class MarathonMapActivity extends BaseActivity {

   private MapView mMapView = null;
    private AMap map;
    private Polyline polyline;
    private UiSettings muiSetting;
    private CheckBox tv;
    private List<MapProjectModel>datas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("参赛路线");
        showBackButton();

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

       init();

        httpRequest();
    }


    /**
     * 请求网络数据
     */
    private void httpRequest(){


        RequestParams params = new RequestParams(HttpUrlUtils.PROJECT_MAP);

        params.addBodyParameter("eventid", MarathonDataUtils.init().getEventId());

        params.setMaxRetryCount(60*1000);

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);

                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    if(success){

                        JSONArray routeList = json.getJSONArray("SingleRouteList");

                        for (int i=0;i<routeList.length();i++){

                            JSONObject jsonObject = routeList.getJSONObject(i);


                            String projectName = jsonObject.getString("RouteName");

                            JSONArray list = jsonObject.getJSONArray("CompetitionRouteList");

                            List<MapPointNode>nodes  =new ArrayList<>();
                            Log.d("----->>name--", "onSuccess: "+projectName);

                            MapPointNode startNode=null,endNode=null;
                            for (int j=0;j<list.length();j++){


                                JSONObject item = list.getJSONObject(j);

                                double longitude = item.getDouble("Longitude");
                                double latitude = item.getDouble("Latitude");
                                Log.d("-------->>>", "onSuccess: "+longitude);
                                nodes.add(new MapPointNode(longitude,latitude));

                                String note = item.getString("Note");

                                Log.d("note----->>", "onSuccess: "+note);
                                if(note!=null&&note.equals("起点")){

                                    //获取起点

                                    startNode = new MapPointNode(longitude,latitude);
                                }else if(note!=null&&note.equals("终点")){
                                    //获取终点
                                    endNode = new MapPointNode(longitude,latitude);

                                }



                            }

                             datas.add(new MapProjectModel(projectName,nodes,startNode,endNode));


                        }



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                if(datas.size()>0){

                    tv.setText(datas.get(0).getProjectName());
                    drawLine(datas.get(0).getNodes());
                    addMarker(datas.get(0));
                    setMapCenter(datas.get(0).getStartNode());
                }

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

//    初始化
    private void init(){

        if(map==null) {
            this.map = mMapView.getMap();
            this.muiSetting = this.map.getUiSettings();
        }

         this.map.showBuildings(true);
        this.tv = (CheckBox) this.findViewById(R.id.tv);

        this.findViewById(R.id.bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupWindow(tv);
            }
        });
        setShowScale();


    }


    private void showPopupWindow(View view) {

        tv.setChecked(true);

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.marathon_map_popwindow, null);
        ListView listView  = (ListView) contentView.findViewById(R.id.listView);

        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas));

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               popupWindow.dismiss();

                MapProjectModel model = datas.get(position);
                String title =  model.getProjectName();
                tv.setText(title);
                drawLine(model.getNodes());
                addMarker(model);
                setMapCenter(model.getStartNode());

                tv.setChecked(false);
            }
        });


        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv.setChecked(false);
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popwindow_background));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }
    /**
     * 设置显示比例尺
     */
    private void setShowScale(){

        this.muiSetting.setScaleControlsEnabled(true);
    }

    /**
     * 在图上绘制线条
     */
    private void drawLine(List<MapPointNode>nodes){


        List<LatLng> latLngs = new ArrayList<>();


//        latLngs.add(new LatLng(39.999391,116.135972));
//        latLngs.add(new LatLng(39.898323,116.057694));
//        latLngs.add(new LatLng(39.900430,116.265061));
//        latLngs.add(new LatLng(39.955192,116.140092));
        for (MapPointNode node:nodes){

            latLngs.add(new LatLng(node.getLatitude(),node.getLongitude()));
        }

//         polyline = map.addPolyline(new PolylineOptions().
//             addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

        polyline = map.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.map_alr)).addAll(latLngs).width(18));
    }

    /**
     * 在图上添加覆盖物
     */
    private void addMarker(MapProjectModel model){

        if(model.getStartNode()==null){

            return;
        }

       LatLng startLatLng = new LatLng(model.getStartNode().getLatitude(),model.getStartNode().getLongitude());
       LatLng endLatLng = new LatLng(model.getEndNode().getLatitude(),model.getEndNode().getLongitude());
//        Marker marker = map.addMarker(new MarkerOptions().position(startlatLng).title("起点").snippet("DefaultMarker"));
//        Marker marker1 = map.addMarker(new MarkerOptions().position(endlatLng).title("终点").snippet("DefaultMarker"));

        //起点 创建自定义覆盖物
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(startLatLng);
        markerOption.title("标题").snippet("内容");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.drawable.start)));

//        将覆盖物添加到地图上
        map.addMarker(markerOption);

//        终点
        markerOption = new MarkerOptions();
        markerOption.position(endLatLng);
        markerOption.title("标题").snippet("内容");
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.drawable.end)));

        map.addMarker(markerOption);
    }

    //设置地图中心点
    private void setMapCenter(MapPointNode startNode){

        if(startNode==null){

            return;
        }

        LatLng startLatLng = new LatLng(startNode.getLatitude(),startNode.getLongitude());
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                       startLatLng, 10, 30, 30)));

    }
    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        map.moveCamera(update);

    }
    @Override
    public int getLayout() {
        return R.layout.activity_marathon_map;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
