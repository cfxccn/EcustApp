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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class JobDetail extends SherlockActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    List<String> jobtitleinfo;
    SoapObject sObject;
    String jobid;
    String JobTitle,JobRelease,qbcert,JobDetail,JobTreatment,JobTime,JobSite,JobReq,JobTag,JobVia;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        jobid=intent.getStringExtra("jobid");
        index=intent.getIntExtra("index", 0);
        getjobdetail();
    }


    
    private void getjobdetail() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		SoapObject sObject= GetNetData.getjobdetail(jobid);
	                
	             JobTitle=sObject.getProperty(0).toString(); 
	             JobTime=sObject.getProperty(1).toString();
	             JobDetail=sObject.getProperty(2).toString();
	             JobReq=sObject.getProperty(3).toString();
	             JobSite=sObject.getProperty(4).toString();
	             JobTreatment=sObject.getProperty(5).toString();
		         JobTag=sObject.getProperty(6).toString();
	             JobRelease=sObject.getProperty(7).toString();
	             qbcert=sObject.getProperty(8).toString();
	             JobVia=sObject.getProperty(9).toString();

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
		TextView textView_JobTitle=(TextView)findViewById(R.id.textView_JobTitle);
		TextView textView_JobRelease=(TextView)findViewById(R.id.textView_JobRelease);
		TextView textView_JobDetail=(TextView)findViewById(R.id.textView_JobDetail);
		TextView textView_JobTreatment=(TextView)findViewById(R.id.textView_JobTreatment);
		TextView TextView_JobTime=(TextView)findViewById(R.id.TextView_JobTime);
		TextView textView_JobSite=(TextView)findViewById(R.id.textView_JobSite);
		TextView textView_JobReq=(TextView)findViewById(R.id.textView_JobReq);
		TextView textView_JobTag=(TextView)findViewById(R.id.textView_JobTag);
		TextView textView_JobVia=(TextView)findViewById(R.id.textView_JobVia);
		ImageView imageView_qbcert=(ImageView)findViewById(R.id.imageView_qbcert);
		textView_JobTitle.setText(JobTitle);
		textView_JobRelease.setText(JobRelease);
		textView_JobDetail.setText(JobDetail);
		textView_JobTreatment.setText(JobTreatment);
		TextView_JobTime.setText(JobTime);
		textView_JobSite.setText(JobSite);
		textView_JobReq.setText(JobReq);
		textView_JobTag.setText(JobTag);
		textView_JobVia.setText(JobVia);
		if(qbcert.equalsIgnoreCase("是")){
			imageView_qbcert.setImageDrawable(getResources().getDrawable(R.drawable.qb_cert));
		}
		else{
			imageView_qbcert.setImageDrawable(getResources().getDrawable(R.drawable.qb_nocert));
		}
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

