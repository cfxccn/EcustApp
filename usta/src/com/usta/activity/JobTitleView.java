package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.usta.R;
import com.usta.service.JobService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class JobTitleView extends ActionBarActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    ListView list ;
    List<String> jobtitleinfo;
    JSONArray jobsTitilesJsonArray;
    String  jobid;
    JobService jobService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        list = (ListView) findViewById(R.id.listView_JobTitle);
        getJobsTitleDataViaNewThread();
    //    initlistview();
    }

    private void getJobsTitleDataViaNewThread() {
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		
	    		jobsTitilesJsonArray=jobService.getJobTitles();
	    	//     jobtitleinfo=new ArrayList<String>();
	    	//	sObject= GetNetData.getjobtitle();
//	    		for(int i=0;i<48;i++)
//	    		{
//	    			
//	    			jobtitleinfo.add(sObject.getProperty(i).toString().trim());
//	    		}
	    		if(jobsTitilesJsonArray!=null){
			    	 handler.sendEmptyMessage(0);
	    		}
	    		else{
	    			return;
	    		}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
	    }
	}).start();
   	
	}

private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		initlistview();
		}
		};


    
    
 private void initlistview() {
	 //jobsTitilesJsonArray
	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
      for(int i=0;i<jobsTitilesJsonArray.length();i++)
      {
    	  JSONObject jobJsonObject=jobsTitilesJsonArray.optJSONObject(i);
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("textView_jobid",jobJsonObject.optInt("id"));
          map.put("textView_Job_title",jobJsonObject.optString("infotitile").trim());
          map.put("textView_Job_treatment", jobJsonObject.optString("jobtreatment").trim());
          map.put("textView_Job_releasetime", jobJsonObject.optString("releasetime").trim());
          if(jobJsonObject.optString("qinban_cert").trim().equalsIgnoreCase("是")){
        	  map.put("qb_cert",R.drawable.qb_cert );
          }
          else{
              map.put("qb_cert",R.drawable.qb_nocert );    	  
          }
          listItem.add(map);
      }
		// TODO Auto-generated method stub
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.jobtitle_listview,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"textView_Job_title","textView_Job_treatment", "textView_Job_releasetime","qb_cert","textView_jobid"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.textView_Job_title,R.id.textView_Job_treatment,R.id.textView_Job_releasetime,R.id.imageView_qb_cert,R.id.textView_jobid}
	        );
	 list.setAdapter(listItemAdapter);
	 list.setOnItemClickListener(new OnItemClickListener() {  
		  
         @Override  
         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                 long arg3) {  
        	 ListView listView = (ListView)arg0;  
                 HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);  
                 jobid= String.valueOf(map.get("textView_jobid"));
 				Intent intent =new Intent();
 				intent.putExtra("index", index);
 				intent.putExtra("jobid", jobid);
 				intent.setClass(JobTitleView.this, JobDetail.class);
 				startActivityForResult(intent, 0);

         }  
     }); 

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

