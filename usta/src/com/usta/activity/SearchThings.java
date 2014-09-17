package com.usta.activity;

import java.net.URI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.usta.R;
import com.usta.service.GetNetData;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchThings extends ActionBarActivity {
	private String  searchdata[] ={"",""};
	private TextView tx1;
	private ListView lv1;
	String getnet;
	Intent intent;
	private int index;
	JSONArray searchjsonarray;
	Toast toast1;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchdata = MainActivity.putlay3sdata();
        setContentView(R.layout.search_);
		
		
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        //intent = getIntent();
       // index=intent.getIntExtra("index", 0);
		
		if(searchdata[1].equals("教师"))
		    getnet="SearchTeacher";	
		if(searchdata[1].equals("活动"))
			getnet="SearchActivity";	
		if(searchdata[1].equals("办公室"))
			getnet="SearchOffice";	
		 
		//tx1=(TextView)findViewById(R.id.sea_tv1);
		lv1=(ListView)findViewById(R.id.sea_lv1);
		Toast toast1=Toast.makeText(this,"无合适班次", Toast.LENGTH_SHORT);
		getsearchjsonarray();
	}
	private   Handler handler =new Handler(){
		 @Override
		 //当有消息发送出来的时候就执行Handler的这个方法
		 public void handleMessage(Message msg){
		 super.handleMessage(msg);
		 if(searchdata[1].equals("教师"))
		 initListView_teacher();
		 if(searchdata[1].equals("活动"))
		 initListView_activity();
		 if(searchdata[1].equals("办公室"))
			 initListView_office();
		 }
		 };	

	private Handler resultEmptyhandler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		lv1.setAdapter(null);
		toast1.show();
		}
		};
	private void getsearchjsonarray()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try{
					//searchjsonarray=GetNetData.getsearchInfo(getnet, searchdata[0]);
					searchjsonarray=GetNetData.getsearchInfo(getnet,searchdata[0]);
					if(searchjsonarray!=null){
		    			if(searchjsonarray.length()==0){
		    				resultEmptyhandler.sendEmptyMessage(0);
		    				return ;
		    			}
				    	 handler.sendEmptyMessage(0);
		    		}
		    		else {
			    		return;

		    		}
					
				}catch(Exception e)
				{
					
				}
			}
			
		}).start();
		
	}
	private void initListView_teacher() {
		 //jobsTitilesJsonArray
		  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	      for(int i=0;i<searchjsonarray.length();i++)
	      {
	    	  JSONObject teacherJsonObject=searchjsonarray.optJSONObject(i);
	          HashMap<String, Object> map = new HashMap<String, Object>();
	          map.put("se_te_tv1",teacherJsonObject.optString("name").trim());
	          map.put("se_te_tv2",teacherJsonObject.optString("department").trim());
	          map.put("se_te_tv3","办公室："+teacherJsonObject.optString("office").trim());
	          map.put("se_te_tv4",teacherJsonObject.optString("phonenumber").trim());
	          map.put("se_te_tv5",teacherJsonObject.optString("mailbox").trim());
	         
	          listItem.add(map);
	      }	
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.search_teacher,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"se_te_tv1","se_te_tv2", "se_te_tv3","se_te_tv4","se_te_tv5"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.se_te_tv1,R.id.se_te_tv2,R.id.se_te_tv3,R.id.se_te_tv4,R.id.se_te_tv5 }
	        );
	 lv1.setAdapter(listItemAdapter);
}
	private void initListView_activity() {
		 //jobsTitilesJsonArray
		  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	      for(int i=0;i<searchjsonarray.length();i++)
	      {
	    	  JSONObject teacherJsonObject=searchjsonarray.optJSONObject(i);
	          HashMap<String, Object> map = new HashMap<String, Object>();
	          map.put("se_ac_tv1",teacherJsonObject.optString("avtivityname").trim());
	          map.put("se_ac_tv2",teacherJsonObject.optString("activitynr").trim());
	          listItem.add(map);
	      }	
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.search_activity,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"se_ac_tv1","se_ac_tv2"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.se_ac_tv1,R.id.se_ac_tv2}
	        );
	 lv1.setAdapter(listItemAdapter);
}
	private void initListView_office() {
		 //jobsTitilesJsonArray
		  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	      for(int i=0;i<searchjsonarray.length();i++)
	      {
	    	  JSONObject teacherJsonObject=searchjsonarray.optJSONObject(i);
	          HashMap<String, Object> map = new HashMap<String, Object>();
	          map.put("se_off_tv1","办公室:"+teacherJsonObject.optString("address").trim());
	          map.put("se_off_tv2","电话:"+teacherJsonObject.optString("phonenumber").trim());
	          map.put("se_off_tv3","负责老师:"+teacherJsonObject.optString("manager").trim());
	          map.put("se_off_tv4","负责事宜:"+teacherJsonObject.optString("function").trim());
	          listItem.add(map);
	      }	
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.search_office,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"se_off_tv1","se_off_tv2","se_off_tv3","se_off_tv4"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.se_off_tv1,R.id.se_off_tv2,R.id.se_off_tv3,R.id.se_off_tv4}
	        );
	 lv1.setAdapter(listItemAdapter);
}
}
