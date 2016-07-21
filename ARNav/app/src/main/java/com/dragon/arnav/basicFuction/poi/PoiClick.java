package com.dragon.arnav.basicFuction.poi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.NaviPara;
import com.amap.api.maps.model.Poi;
import com.dragon.arnav.R;
/**
 * This file created by dragon on 2016/7/19 15:15,belong to com.dragon.arnav.basicFuction.poi .
 */
public class PoiClick extends Activity implements AMap.OnPOIClickListener,AMap.OnMarkerClickListener {
    private MapView mMapView;
    private AMap mAMap;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poiclick);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
//        设置监听时间
        mAMap.setOnPOIClickListener(this);
        mAMap.setOnMarkerClickListener(this);
    }
//地图POI点击回调
    @Override
    public void onPOIClick(Poi poi){
        mAMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
//        获取坐标
        markerOptions.position(poi.getCoordinate());
//        下面就是用来显示一个图标
        TextView textView = new TextView(getApplicationContext());
        textView.setText("到"+poi.getName()+"去");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.custom_info_bubble);
        markerOptions.icon(BitmapDescriptorFactory.fromView(textView));
        mAMap.addMarker(markerOptions);
    }
//    点击Marker图标后的执行的操作
    @Override
    public boolean onMarkerClick(Marker marker){
//        构造导航参数
        NaviPara naviPara = new NaviPara();
//        设置终点位置
        naviPara.setTargetPoint(marker.getPosition());
//        设置导航策略，这里是避免拥堵
        naviPara.setNaviStyle(AMapUtils.DRIVING_AVOID_CONGESTION);
        try{
//            这里调用高德地图来导航
            AMapUtils.openAMapNavi(naviPara,getApplicationContext());
        } catch (com.amap.api.maps.AMapException e){
//            如果你没安装，这会触发这个异常
            AMapUtils.getLatestAMapApp(getApplicationContext());
        }
        mAMap.clear();
        return false;
    }

//    下面这些没什么要说的
    @Override
    protected void onResume(){
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mMapView.onDestroy();
    }
}
