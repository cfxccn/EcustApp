package com.usta.ecustapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.usta.ecustapp.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.usta.ecustapp.control.MapOverlay;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.TextItem;
import com.baidu.mapapi.map.TextOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class NearbyViewInMap extends ActionBarActivity {
	private int index;
	Intent intent;
	String nearbys_string;
	String content;
    ListView list ;
    List<String> jobtitleinfo;
    JSONArray nearbys;
    BMapManager bMapManager = null;
    MapView mapView = null;
	ArrayList<HashMap<String, Object>> nearybyinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapManager = new BMapManager(getApplication());
        bMapManager.init("dPGBEvkbs38QCsYHqrVu006S", null);
		
		setContentView(R.layout.nearby_viewinmap);
		
		// 初始化mapview对象，并且设置显示缩放控件
		mapView = (MapView) findViewById(R.id.bmapsView_nearby);
		mapView.setBuiltInZoomControls(true);
		
		MapController mapController = mapView.getController();
		

		mapController.setCenter(new GeoPoint((int)(30.841733* 1E6),(int)(121.510752* 1E6)));
		mapController.setZoom(15);
    	
    	
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        nearbys_string=intent.getStringExtra("nearbys");
        try {
			nearbys=new JSONArray(nearbys_string);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        getspotsdata() ;
    }

	//注意在onResume、onDestroy和onPause中控制mapview和地图管理对象的状态

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mapView.onResume();
		if(bMapManager!=null){
		bMapManager.start();
		}
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapView.destroy();
		if(bMapManager!=null){
			bMapManager.destroy();
			bMapManager=null;
		}
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mapView.onPause();
		if(bMapManager!=null){
			bMapManager.stop();
		}
		super.onPause();
	}

	private void getspotsdata() {
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		
	    
	    		nearybyinfo=new ArrayList<HashMap<String, Object>>();

	    		JSONObject nearby=new JSONObject();
	    		
	    		for(int i=0;i<nearbys.length();i++)
	    		{
	    			nearby=nearbys.getJSONObject(i);
	    			HashMap<String, Object> each = new HashMap<String, Object>();
	    			each.put("id",nearby.getInt("id"));
	    			each.put("longtitude",nearby.getString("longtitude"));
	    			each.put("latitude",nearby.getString("latitude"));
	    			each.put("name",nearby.getString("name"));
	    			nearybyinfo.add(each);
	   	    	} 
	    		
	    		
	    		
	    		
		    	 map_handler.sendEmptyMessage(0);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    }
	}).start();
   	
	
	}
private Handler map_handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		initspot();
		}
		};


    
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
	        setResult(RESULT_OK, intent);  
	        finish();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    



protected void initspot() {
	
	double lat,lon;
	List<GeoPoint> spotsArrayList=new ArrayList<GeoPoint>();
	List<OverlayItem> items =new ArrayList<OverlayItem>();
	//List<MapOverlay> items =new ArrayList<MapOverlay>();
	


	TextOverlay  textOverlay = new TextOverlay(mapView);  
	TextItem textItem = new TextItem();
	
	Drawable marka = getResources().getDrawable(R.drawable.m1);
	MapOverlay mOverlay=new MapOverlay(marka,mapView);
	OverlayItem iiiiiItem;

	for(int i=0;i<nearybyinfo.size();i++)
	{
		lat= Double.parseDouble(nearybyinfo.get(i).get("latitude").toString());
		lon= Double.parseDouble(nearybyinfo.get(i).get("longtitude").toString());
			
		
		spotsArrayList.add(new GeoPoint((int) ( lat* 1E6), (int) (lon * 1E6))) ;
	//	Drawable mark= getResources().getDrawable(R.id.textView1);
		Drawable mark= getResources().getDrawable(R.drawable.m1+i);
		iiiiiItem  = new OverlayItem(new GeoPoint((int) ( lat* 1E6), (int) (lon * 1E6)),nearybyinfo.get(i).get("name").toString(),"dd");
		iiiiiItem.setMarker(mark);
		items.add(iiiiiItem);
		//textOverlay.addText(textItem);    
		//textOverlay.addText(textItem);

	}


	mOverlay.addItem(items);
	mapView.getOverlays().clear();  
	mapView.getOverlays().add(mOverlay);  
	mapView.refresh();
	
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

