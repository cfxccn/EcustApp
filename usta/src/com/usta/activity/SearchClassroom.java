package com.usta.activity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.service.ClassroomService;
import com.usta.control.GridViewOnScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.array;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.usta.R;

public class SearchClassroom extends SherlockActivity {
	
	private int index;
	Intent intent;
	
	private static final String[] wday = {"周日","周一","周二","周三","周四","周五","周六"};
	private static final String[] during = {"1-2","3-4","5-6","7-8","9-11"};
	private String cwday;
	private String cduring;
	
	private List<String> roomAlist = new ArrayList<String>();
	private List<String> roomBlist = new ArrayList<String>();
	private List<String> roomClist = new ArrayList<String>();
	
	private ArrayAdapter adapter;
	private GridViewOnScrollView gridView;
	private Context context = this;
	JSONArray roomsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchclassroom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        
        initSpinner();
        initbtn();
    }
    private void initbtn()
    {
    	Button btn = (Button)findViewById(R.id.btnsearch_classroom);
    	btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getEmptyRoomServerDataViaNewThread();
			}
		});
    }
    ClassroomService classroomService;
    private void getEmptyRoomServerDataViaNewThread()
    {
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    					cduring = ((Spinner)findViewById(R.id.spinner_during)).getSelectedItem().toString();
						cwday = String.valueOf(((Spinner)findViewById(R.id.spinner_wday)).getSelectedItemPosition());
						if(cwday.equals("0")) cwday="7";
						roomsArray=classroomService.getRoomData(cwday, cduring);
						if(roomsArray!=null){
			    	    	 handler.sendEmptyMessage(0);
						}
						else{
							return;
						}
    	    }
    	    
    	}).start();
    }
    private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
	    	roomAlist.clear();
	    	roomBlist.clear();
	    	roomClist.clear();
			for(int i = 0;i < roomsArray.length();i++){
				if(roomsArray.optJSONArray(i).optString(2).equals("A"))
					roomAlist.add(roomsArray.optJSONArray(i).optString(1));
				else if(roomsArray.optJSONArray(i).optString(2).equals("B"))
					roomBlist.add(roomsArray.optJSONArray(i).optString(1));
				else if(roomsArray.optJSONArray(i).optString(2).equals("C"))
					roomClist.add(roomsArray.optJSONArray(i).optString(1));	
			}
			((TextView)findViewById(R.id.tvRoomA)).setText("A教");
			adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,roomAlist);
			gridView = (GridViewOnScrollView)findViewById(R.id.gvRoomA);
			gridView.setAdapter(adapter);
			
			((TextView)findViewById(R.id.tvRoomB)).setText("B教");
			adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,roomBlist);
			gridView = (GridViewOnScrollView)findViewById(R.id.gvRoomB);
			gridView.setAdapter(adapter);
			
			((TextView)findViewById(R.id.tvRoomC)).setText("C教");
			adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,roomClist);
			gridView = (GridViewOnScrollView)findViewById(R.id.gvRoomC);
			gridView.setAdapter(adapter);
		}
	};
    private void initSpinner()
    {
    	Spinner spinWday = (Spinner)findViewById(R.id.spinner_wday);
    	Spinner spinDuring = (Spinner)findViewById(R.id.spinner_during);
    	
		ArrayList<String> alwday=new ArrayList<String>();
		for(int i=0;i < wday.length;i++){
			alwday.add(wday[i]);
		}
		ArrayList<String> alduring=new ArrayList<String>();
		for(int i=0;i < during.length;i++){
			alduring.add(during[i]);
		}
    	ArrayAdapter<String> aaWday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,wday);
    	aaWday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	ArrayAdapter<String> aaDuring = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,during);
    	aaDuring.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	spinWday.setAdapter(aaWday);
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    	spinWday.setSelection(c.get(Calendar.DAY_OF_WEEK) - 1);
    	
    	spinDuring.setAdapter(aaDuring);
    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
    	   	setResult(RESULT_OK, intent);  
       		finish();  
       		return true;  
		}  
		return super.onKeyDown(keyCode, event);  
	}

    @Override  
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

