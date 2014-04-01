package com.usta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.MenuItem;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.KeyEvent;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
 


public class Map extends SherlockActivity {
	private int index;
	Intent intent;
	String content;
	BMapManager mBMapMan = null;
	MapView mMapView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        mBMapMan=new BMapManager(getApplication());
        mBMapMan.init("dPGBEvkbs38QCsYHqrVu006S", null);  
		setContentView(R.layout.map);

        //注意：请在试用setContentView前初始化BMapManager对象，否则会报错
        mMapView=(MapView)findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);
        //设置启用内置的缩放控件
        MapController mMapController=mMapView.getController();
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        GeoPoint point =new GeoPoint((int)(30.836546* 1E6),(int)(121.510806* 1E6));
        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(point);//设置地图中心点
        mMapController.setZoom(16);//设置地图zoom级别
        initspot();

    }
    private void initspot() {
		// TODO Auto-generated method stub
    	/**
    	 *  在想要添加Overlay的地方使用以下代码，
    	 *  比如Activity的onCreate()中
    	 */
    	//准备要添加的Overlay
    	

    	double mLat1 = 30.838787;
    	double mLon1 = 121.511165;
    	

    	double mLat2 = 30.835651;
    	double mLon2 = 121.507181;

    	double mLat3 = 30.839783;
    	double mLon3 =     	121.512346;
    	// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
    	GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
    	GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
    	GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
    	//准备overlay图像数据，根据实情情况修复
    	Drawable mark= getResources().getDrawable(R.drawable.a00);
    	//用OverlayItem准备Overlay数据
    	OverlayItem item1 = new OverlayItem(p1,"item1","item1");
    	//使用setMarker()方法设置overlay图片,如果不设置则使用构建ItemizedOverlay时的默认设置
    	OverlayItem item2 = new OverlayItem(p2,"item2","item2");
    	item2.setMarker(mark);
    	OverlayItem item3 = new OverlayItem(p3,"item3","item3");
    	 
    	//创建IteminizedOverlay
    	MapOverlay itemOverlay = new MapOverlay(mark, mMapView);
    	//将IteminizedOverlay添加到MapView中

    	mMapView.getOverlays().clear();
    	mMapView.getOverlays().add(itemOverlay);
    	 
    	//现在所有准备工作已准备好，使用以下方法管理overlay.
    	//添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高
    	itemOverlay.addItem(item1);
    	itemOverlay.addItem(item2);
    	itemOverlay.addItem(item3);
    	mMapView.refresh();
    	//删除overlay .
    	//itemOverlay.removeItem(itemOverlay.getItem(0));
    	//mMapView.refresh();
    	//清除overlay
    	// itemOverlay.removeAll();
    	// mMapView.refresh();
	}
	@Override
    protected void onDestroy(){
            mMapView.destroy();
            if(mBMapMan!=null){
                    mBMapMan.destroy();
                    mBMapMan=null;
            }
            super.onDestroy();
    }
    @Override
    protected void onPause(){
            mMapView.onPause();
            if(mBMapMan!=null){
                   mBMapMan.stop();
            }
            super.onPause();
    }
    @Override
    protected void onResume(){
            mMapView.onResume();
            if(mBMapMan!=null){
                    mBMapMan.start();
            }
           super.onResume();
    }



public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
	        setResult(RESULT_OK, intent);  
	        finish();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    



public boolean onOptionsItemSelected(MenuItem item) {  
    switch(item.getItemId()){  

  case android.R.id.home:  
	        setResult(RESULT_OK, intent);  
	        finish();  	        
	        break;  	        
    }  
    return super.onOptionsItemSelected(item);  
}  
}

