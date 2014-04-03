package com.usta;

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
import com.usta.getnetdata.GetNetData;
import com.usta.control.GridViewOnScrollView;

import android.R.integer;
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

public class SearchLecture extends SherlockActivity {
	
	private int index;
	Intent intent;
	
	private static final String[] week = {"第三周","第五周","第七周","第九周","第十一周","第十三周","第十五周"};
	private static final String[] department = {"xinxi","化工学院","法学院","化学学院","理学院","人文学院","生工学院","图书馆","外语学院","信息学院","药学院","艺术学院","资环学院"};
	private String cweek;
	private String cdepartment;
	
	private List<String> lecturelist = new ArrayList<String>();
	
	private ArrayAdapter adapter;
	private GridViewOnScrollView gridView;
	private Context context = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlecture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        
        initSpinner();
        initbtn();
    }
    private void initbtn()
    {
    	Button btn = (Button)findViewById(R.id.btnsearch_lecture);
    	btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getLectureServerDataFromNewThread();
			}
		});
    }
    private void getLectureServerDataFromNewThread()
    {
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    					try {
    						cdepartment = ((Spinner)findViewById(R.id.spinner_department)).getSelectedItem().toString();
    						cweek = String.valueOf(((Spinner)findViewById(R.id.spinner_week)).getSelectedItemPosition());
    						if(cweek.equals("0")) cweek="3";
    						else if(cweek.equals("1")) cweek="5";
    						else if(cweek.equals("2")) cweek="7";
    						else if(cweek.equals("3")) cweek="9";
    						else if(cweek.equals("4")) cweek="11";
    						else if(cweek.equals("5")) cweek="13";
    						else if(cweek.equals("6")) cweek="15";
    						JSONArray lectureArray = null;
							try {
								
								lectureArray = GetNetData.getLectureData(cweek, cdepartment);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    		    	    	lecturelist.clear();
							for(int i = 0;i < lectureArray.length();i++){
								
									lecturelist.add(lectureArray.getJSONObject(i).getString("name"));
									lecturelist.add(lectureArray.getJSONObject(i).getString("credit"));
									lecturelist.add(lectureArray.getJSONObject(i).getString("start"));
									lecturelist.add(lectureArray.getJSONObject(i).getString("classroom"));
									lecturelist.add(lectureArray.getJSONObject(i).getString("department"));
									lecturelist.add(lectureArray.getJSONObject(i).getString("campus"));

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

    	    	 handler.sendEmptyMessage(0);
    	    }
    	    
    	}).start();
    }
    private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);

			adapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line,lecturelist);
			gridView = (GridViewOnScrollView)findViewById(R.id.gvLecture);
			gridView.setAdapter(adapter);
		}
	};
    private void initSpinner()
    {
    	Spinner spinWeek = (Spinner)findViewById(R.id.spinner_week);
    	Spinner spinDepartment = (Spinner)findViewById(R.id.spinner_department);
    	
		ArrayList<String> alweek=new ArrayList<String>();
		for(int i=0;i < week.length;i++){
			alweek.add(week[i]);
		}
		ArrayList<String> aldepartment=new ArrayList<String>();
		for(int i=0;i < department.length;i++){
			aldepartment.add(department[i]);
		}
    	ArrayAdapter<String> aaWeek = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,week);
    	aaWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	ArrayAdapter<String> aaDepartment = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,department);
    	aaDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	spinWeek.setAdapter(aaWeek);
 	
    	spinDepartment.setAdapter(aaDepartment);
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

