package com.dragon.arnav.basicFuction.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.MarkerOptions;
import com.dragon.arnav.R;
import com.dragon.arnav.basicFuction.util.Constants;
import com.dragon.arnav.basicFuction.util.ToastUtil;

/**
 * This file created by dragon on 2016/7/28 21:26,belong to com.dragon.arnav.basicFuction.basic .
 */
public class basicMap extends Activity implements View.OnClickListener,AMap.CancelableCallback {
    private MapView mapView;
    private AMap aMap;
//    上下左右一次的移动像素点
    private static final int SCROLL_BY_PX = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_map);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();
    }
    private void init(){
        if(aMap == null){
            aMap = mapView.getMap();
        }
//        各种事件的监听方式
        Button stopAnimation = (Button)findViewById(R.id.stop_animation);
        stopAnimation.setOnClickListener(this);

        ToggleButton animate = (ToggleButton)findViewById(R.id.animate);
        animate.setOnClickListener(this);

        Button Lujiazui = (Button) findViewById(R.id.Lujiazui);
        Lujiazui.setOnClickListener(this);

        Button Zhongguancun = (Button) findViewById(R.id.Zhongguancun);
        Zhongguancun.setOnClickListener(this);

        Button scrollLeft = (Button)findViewById(R.id.scroll_left);
        scrollLeft.setOnClickListener(this);

        Button scrollRight = (Button)findViewById(R.id.scroll_right);
        scrollRight.setOnClickListener(this);

        Button scrollUp = (Button)findViewById(R.id.scroll_up);
        scrollUp.setOnClickListener(this);

        Button scrollDown = (Button) findViewById(R.id.scroll_down);
        scrollDown.setOnClickListener(this);

        Button zoomIn = (Button) findViewById(R.id.zoom_in);
        zoomIn.setOnClickListener(this);

        Button zoomOut = (Button)findViewById(R.id.zoom_out);
        zoomOut.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
//    根据动画按钮的状态来选择不同的camera来改变可视区域
//    AMap.CanclelableCallback：当一个任务完成或关闭时的回调接口。
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback){
        boolean animated = ((CompoundButton)findViewById(R.id.animate)).isChecked();
        if(animated){
//在指定的持续时间内，动画地移动地图到指定的位置，完成时调用可选的回调方法。 如果运动过程中调用getCameraPosition()，它将返回可视区域飞行中的当前位置。
//            第一个参数：定义转换的时间，第二个参数：运动的持续时间，第三个参数：回调函数
            aMap.animateCamera(update,1000,callback);
        }else{
//            照传入的CameraUpdate 参数移动可视区域。这个方法为瞬间移动，没有移动过程，如果在调用此方法后再调用getCameraPosition()将返回移动后位置。
            aMap.moveCamera(update);
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            /**
             * 点击停止动画按钮响应事件
             */
            case R.id.stop_animation:
                aMap.stopAnimation();
                break;
            /**
             * 点击“去中关村”按钮响应事件
             */
            case R.id.Zhongguancun:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.ZHONGGUANCUN, 18, 0, 30)), null);
                break;

            /**
             * 点击“去陆家嘴”按钮响应事件
             */
            case R.id.Lujiazui:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                Constants.SHANGHAI, 18, 30, 0)), this);
                break;
            /**
             * 点击向左移动按钮响应事件，camera将向左边移动
             */
            case R.id.scroll_left:
                changeCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0), null);
                break;
            /**
             * 点击向右移动按钮响应事件，camera将向右边移动
             */
            case R.id.scroll_right:
                changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0), null);
                break;
            /**
             * 点击向上移动按钮响应事件，camera将向上边移动
             */
            case R.id.scroll_up:
                changeCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX), null);
                break;
            /**
             * 点击向下移动按钮响应事件，camera将向下边移动
             */
            case R.id.scroll_down:
                changeCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX), null);
                break;
            /**
             * 点击地图放大按钮响应事件
             */
            case R.id.zoom_in:
                changeCamera(CameraUpdateFactory.zoomIn(), null);
                break;
            /**
             * 点击地图缩小按钮响应事件
             */
            case R.id.zoom_out:
                changeCamera(CameraUpdateFactory.zoomOut(), null);
                break;
            default:
                break;
        }
    }
    /**
     * 地图动画效果终止回调方法
     */
    @Override
    public void onCancel() {
        ToastUtil.show(basicMap.this, "Animation to 陆家嘴 canceled");
    }

    /**
     * 地图动画效果完成回调方法
     */
    @Override
    public void onFinish() {
        ToastUtil.show(basicMap.this, "Animation to 陆家嘴 complete");
    }

}
