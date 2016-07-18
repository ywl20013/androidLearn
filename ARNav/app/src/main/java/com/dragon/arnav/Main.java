package com.dragon.arnav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class Main extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.map);
//        必须回调MapView的onCreate()方法
        mapView.onCreate(savedInstanceState);
        init();
        ToggleButton tb = (ToggleButton) findViewById(R.id.tb);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                if(isChecked){
                    aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                } else
                {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                }
            }
        });
    }

//初始化AMap对象
    private void init(){
        if(aMap ==null){
            aMap = mapView.getMap();
        }
    }
@Override
    protected void onResume(){
    super.onResume();
    mapView.onResume();
}
@Override
    protected void onPause(){
    super.onPause();
    mapView.onPause();
}
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }



}
