package com.usta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.KeyEvent;
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

public class JobTitleView extends SherlockActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    ListView list ;
    List<String> jobtitleinfo;
    SoapObject sObject;
    String  jobid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        list = (ListView) findViewById(R.id.listView_JobTitle);
        geijobtitledata();
    //    initlistview();
    }

    private void geijobtitledata() {
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    	     jobtitleinfo=new ArrayList<String>();
	    		sObject= GetNetData.getjobtitle();
	    		for(int i=0;i<48;i++)
	    		{
	    			jobtitleinfo.add(sObject.getProperty(i).toString());
	    		}
		    	 handler.sendEmptyMessage(0);
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
	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
      for(int i=0;i<7;i++)
      {
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("textView_jobid",jobtitleinfo.get(i*6));
          map.put("textView_Job_title",jobtitleinfo.get(1+i*6));
          map.put("textView_Job_treatment", jobtitleinfo.get(3+i*6));
          map.put("textView_Job_releasetime", jobtitleinfo.get(4+i*6));
          if(jobtitleinfo.get(5+i*6).equalsIgnoreCase("是")){
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
                 jobid= (String)map.get("textView_jobid");  
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

