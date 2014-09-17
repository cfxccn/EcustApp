package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.usta.R;
import com.usta.service.JobService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class JobDetail extends ActionBarActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    List<String> jobtitleinfo;
    String jobid;
    String JobTitle,JobRelease,qbcert,JobDetail,JobTreatment,JobTime,JobSite,JobReq,JobTag,JobVia;
    JSONObject jobdetailsJsonObject;
JobService JobService=new JobService();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        jobid=intent.getStringExtra("jobid");
        index=intent.getIntExtra("index", 0);
        getJobDetailsViaNewThread();
    }


    
    private void getJobDetailsViaNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
//	    		SoapObject sObject= GetNetData.getjobdetail(jobid);
//	                
//	             JobTitle=sObject.getProperty(0).toString(); 
//	             JobTime=sObject.getProperty(1).toString();
//	             JobDetail=sObject.getProperty(2).toString();
//	             JobReq=sObject.getProperty(3).toString();
//	             JobSite=sObject.getProperty(4).toString();
//	             JobTreatment=sObject.getProperty(5).toString();
//		         JobTag=sObject.getProperty(6).toString();
//	             JobRelease=sObject.getProperty(7).toString();
//	             qbcert=sObject.getProperty(8).toString();
//	             JobVia=sObject.getProperty(9).toString();
//
//		    	 handler.sendEmptyMessage(0);

	    	jobdetailsJsonObject=JobService.getJobDetails(jobid);
	    	if(jobdetailsJsonObject!=null){
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
		 JobTitle=jobdetailsJsonObject.optString("infotitile").trim();  
         JobTime=jobdetailsJsonObject.optString("jobtime").trim(); 
         JobDetail=jobdetailsJsonObject.optString("jobdetail").trim(); 
         JobReq=jobdetailsJsonObject.optString("jobrequire").trim(); 
         JobSite=jobdetailsJsonObject.optString("jobsite").trim(); 
         JobTreatment=jobdetailsJsonObject.optString("jobtreatment").trim();  
         JobTag=jobdetailsJsonObject.optString("jobtag").trim(); 
         JobRelease=jobdetailsJsonObject.optString("releasetime").trim();  
         qbcert=jobdetailsJsonObject.optString("qinban_cert").trim(); 
         JobVia=jobdetailsJsonObject.optString("via").trim(); 
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
		TextView textView1=(TextView)findViewById(R.id.textView1);
		TextView textView2=(TextView)findViewById(R.id.textView2);
		TextView textView3=(TextView)findViewById(R.id.textView3);
		TextView textView4=(TextView)findViewById(R.id.textView4);
		TextView textView5=(TextView)findViewById(R.id.textView5);
		TextView textView6=(TextView)findViewById(R.id.textView6);
		TextView textView7=(TextView)findViewById(R.id.textView7);
		textView1.setText("工作内容：");
		textView2.setText("　　薪资：");
		textView3.setText("工作时间：");
		textView4.setText("工作地点：");
		textView5.setText("工作要求：");
		textView6.setText("　　备注：");
		textView7.setText("报名方式：");


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

