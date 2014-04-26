package com.usta.activity;

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
import com.usta.R;
import com.usta.network.Job;

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
    String jobid;
    String JobTitle,JobRelease,qbcert,JobDetail,JobTreatment,JobTime,JobSite,JobReq,JobTag,JobVia;
    JSONObject jobdetailsJsonObject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        jobid=intent.getStringExtra("jobid");
        index=intent.getIntExtra("index", 0);
        getJobDetailsFromNewThread();
    }


    
    private void getJobDetailsFromNewThread() {
	   
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

	    	jobdetailsJsonObject=Job.getJobDetails(jobid);
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
		//������Ϣ���ͳ�����ʱ���ִ��Handler���������
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
		 JobTitle=jobdetailsJsonObject.optString("infotitile"); 
         JobTime=jobdetailsJsonObject.optString("infotitile"); 
         JobDetail=jobdetailsJsonObject.optString("jobdetail"); 
         JobReq=jobdetailsJsonObject.optString("jobrequire"); 
         JobSite=jobdetailsJsonObject.optString("jobsite"); 
         JobTreatment=jobdetailsJsonObject.optString("jobtreatment"); 
         JobTag=jobdetailsJsonObject.optString("jobtag"); 
         JobRelease=jobdetailsJsonObject.optString("releasetime"); 
         qbcert=jobdetailsJsonObject.optString("qinban_cert"); 
         JobVia=jobdetailsJsonObject.optString("via"); 
		textView_JobTitle.setText(JobTitle);
		textView_JobRelease.setText(JobRelease);
		textView_JobDetail.setText(JobDetail);
		textView_JobTreatment.setText(JobTreatment);
		TextView_JobTime.setText(JobTime);
		textView_JobSite.setText(JobSite);
		textView_JobReq.setText(JobReq);
		textView_JobTag.setText(JobTag);
		textView_JobVia.setText(JobVia);
		if(qbcert.equalsIgnoreCase("��")){
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
		textView1.setText("�������ݣ�");
		textView2.setText("����н�ʣ�");
		textView3.setText("����ʱ�䣺");
		textView4.setText("�����ص㣺");
		textView5.setText("����Ҫ��");
		textView6.setText("������ע��");
		textView7.setText("������ʽ��");


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

