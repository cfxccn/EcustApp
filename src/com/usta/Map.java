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

        //ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
        mMapView=(MapView)findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);
        //�����������õ����ſؼ�
        MapController mMapController=mMapView.getController();
        // �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
        GeoPoint point =new GeoPoint((int)(30.836546* 1E6),(int)(121.510806* 1E6));
        //�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
        mMapController.setCenter(point);//���õ�ͼ���ĵ�
        mMapController.setZoom(16);//���õ�ͼzoom����
        initspot();

    }
    private void initspot() {
		// TODO Auto-generated method stub
    	/**
    	 *  ����Ҫ���Overlay�ĵط�ʹ�����´��룬
    	 *  ����Activity��onCreate()��
    	 */
    	//׼��Ҫ��ӵ�Overlay
    	

    	double mLat1 = 30.838787;
    	double mLon1 = 121.511165;
    	

    	double mLat2 = 30.835651;
    	double mLon2 = 121.507181;

    	double mLat3 = 30.839783;
    	double mLon3 =     	121.512346;
    	// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
    	GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
    	GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
    	GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
    	//׼��overlayͼ�����ݣ�����ʵ������޸�
    	Drawable mark= getResources().getDrawable(R.drawable.a00);
    	//��OverlayItem׼��Overlay����
    	OverlayItem item1 = new OverlayItem(p1,"item1","item1");
    	//ʹ��setMarker()��������overlayͼƬ,�����������ʹ�ù���ItemizedOverlayʱ��Ĭ������
    	OverlayItem item2 = new OverlayItem(p2,"item2","item2");
    	item2.setMarker(mark);
    	OverlayItem item3 = new OverlayItem(p3,"item3","item3");
    	 
    	//����IteminizedOverlay
    	MapOverlay itemOverlay = new MapOverlay(mark, mMapView);
    	//��IteminizedOverlay��ӵ�MapView��

    	mMapView.getOverlays().clear();
    	mMapView.getOverlays().add(itemOverlay);
    	 
    	//��������׼��������׼���ã�ʹ�����·�������overlay.
    	//���overlay, ���������Overlayʱʹ��addItem(List<OverlayItem>)Ч�ʸ���
    	itemOverlay.addItem(item1);
    	itemOverlay.addItem(item2);
    	itemOverlay.addItem(item3);
    	mMapView.refresh();
    	//ɾ��overlay .
    	//itemOverlay.removeItem(itemOverlay.getItem(0));
    	//mMapView.refresh();
    	//���overlay
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

