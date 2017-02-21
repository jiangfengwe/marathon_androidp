package com.tdin360.zjw.marathon.ui.activity;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 显示地图（参赛路线）
 */
public class MarathonMapActivity extends BaseActivity {

   private MapView mMapView = null;
    private AMap map;
    private Polyline polyline;
    private UiSettings muiSetting;
    private TextView tv;
    private String[] titles={"马拉松全程","马拉松半程","迷你马拉松"};

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
    }


//    初始化
    private void init(){

        if(map==null) {
            this.map = mMapView.getMap();
            this.muiSetting = this.map.getUiSettings();
        }

         this.map.showBuildings(true);
        this.tv = (TextView) this.findViewById(R.id.tv);
        this.tv.setText(titles[0]);
        this.findViewById(R.id.bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        setShowScale();
        drawLine();
        setMapCenter();
        addMarker();
    }


    private void showPopWindow(){


        final PopupWindow pop = new PopupWindow(this);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        LinearLayout l = new LinearLayout(this);
        l.setBackgroundColor(Color.WHITE);
        pop.setBackgroundDrawable(null);
        ListView listView  = new ListView(this);
        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles));
        l.addView(listView);
        pop.setContentView(l);
        pop.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pop.showAsDropDown(tv,0,1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop.dismiss();
                String title = titles[position];
                tv.setText(title);

            }
        });


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
    private void drawLine(){


        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(39.999391,116.135972));
        latLngs.add(new LatLng(39.898323,116.057694));
        latLngs.add(new LatLng(39.900430,116.265061));
        latLngs.add(new LatLng(39.955192,116.140092));
//        polyline = map.addPolyline(new PolylineOptions().
//                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

       polyline = map.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.map_alr)).addAll(latLngs).width(18));
    }

    /**
     * 在图上添加覆盖物
     */
    private void addMarker(){

        LatLng startLatLng = new LatLng(39.999391,116.135972);
        LatLng endLatLng = new LatLng(39.955192, 116.140092);
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
    private void setMapCenter(){

        LatLng startLatLng = new LatLng(39.999391,116.135972);
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
