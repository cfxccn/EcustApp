package com.usta.activity;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.usta.R;
import com.usta.activity.*;
import com.usta.network.GetNetData;

public class search extends Activity{
	private String searchdata[]={"",""};
	SoapObject sObject;
	public int i=0;
	//private String activityinfo[]={"",""};
	private TextView tv1,tv2,tv3,tv4;
	private Button bu1,bu2;
	private String stractivity[]={"","","","","","","","","","","",""};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		searchdata = MainActivity.putlay3sdata();
		if(searchdata[1].equals("活动"))
			{
			setContentView(R.layout.search_activity);
			tv1=(TextView)findViewById(R.id.se_ac_tv2);
			tv2=(TextView)findViewById(R.id.se_ac_tv3);
			bu1=(Button)findViewById(R.id.se_ac_bu1);
		    get_activityinfo();
		    initbtn_activiyty();
		     }
		if(searchdata[1].equals("教师"))
		{
			setContentView(R.layout.search_teacher);
			tv1=(TextView)findViewById(R.id.se_te_tv2);
			tv2=(TextView)findViewById(R.id.se_te_tv3);
			tv3=(TextView)findViewById(R.id.se_te_tv4);
			tv4=(TextView)findViewById(R.id.se_te_tv5);
			bu1=(Button)findViewById(R.id.se_te_bu1);
			get_teacherinfo();
			initbtn_teacher();
			
		}
		if(searchdata[1].equals("办公室"))
		{
			setContentView(R.layout.search_office);
			tv1=(TextView)findViewById(R.id.se_off_tv2);
			tv2=(TextView)findViewById(R.id.se_off_tv3);
			tv3=(TextView)findViewById(R.id.se_off_tv4);
			tv4=(TextView)findViewById(R.id.se_off_tv5);
			bu1=(Button)findViewById(R.id.se_off_bu1);
			get_officeinfo();
			initbtn_office();
		}
		}
	private void initbtn_activiyty()
	{
           bu1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				i=i+2;
				tv1.setText("活动名称："+stractivity[i]);
    			tv2.setText("活动流程："+stractivity[i+1]);
    			if(stractivity[i+2].equals(""))
				{
					bu1.setVisibility(0x00000004);
				}
				
			}
		});
	}
	private void initbtn_teacher()
	{
           bu1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				i=i+4;
				tv1.setText("教师姓名："+stractivity[0]);
				tv2.setText("邮箱信息："+stractivity[1]);
				tv3.setText("教师介绍："+stractivity[2]);
				tv4.setText("所在办公室："+stractivity[3]);
				if(stractivity[i+4].equals(""))
				{
					bu1.setVisibility(0x00000004);
				}
				
				
			}
		});
	}
	private void initbtn_office()
	{
           bu1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				i=i+4;
				tv1.setText("办公室地址："+stractivity[0]);
				tv2.setText("办公室电话："+stractivity[1]);
				tv3.setText("办公室老师："+stractivity[2]);
				tv4.setText("办公室职能："+stractivity[3]);
				if(stractivity[i+4].equals(""))
				{
					bu1.setVisibility(0x00000004);
				}
				
			}
		});
	}
	private void get_activityinfo() {
    	new Thread(new Runnable(){ 
    	    public void run() {
    	    	try {
    	    		sObject=GetNetData.sendactivitykw(searchdata[0]);
    	    		while(sObject.getProperty(i)!=null)
    	    		{
    	    			stractivity[i]=sObject.getProperty(i).toString().trim();
    	    			i++;
    	    		}  	
    			} catch (Exception e) {
    				// TODO: handle exception   			
    			}
    	    	handler.sendEmptyMessage(0);
    	    }
    	}).start();      	
    	}
		  private Handler handler =new Handler(){
    		public void handleMessage(Message msg){
    		super.handleMessage(msg);
    		if(i==0)
    		{
    		//tv1.setText("活动名称："+activityinfo[0]);
    		//tv2.setText("活动流程："+activityinfo[1]);
    		tv1.setText("无查询结果");
    		tv2.setText("");	
    		bu1.setVisibility(0x00000004);
    		}
    		else
    		{
    			tv1.setText("活动名称："+stractivity[0]);
    			tv2.setText("活动流程："+stractivity[1]);
    			if(stractivity[2].equals(""))
    			{
    				bu1.setVisibility(0x00000004);
    			}
    			i=0;
    		}
    		}};
    		
private void get_teacherinfo() {
	new Thread(new Runnable(){ 
	    public void run() {
	    	try {
	    		sObject=GetNetData.sendteacherkw(searchdata[0]);
	    		while(sObject.getProperty(i)!=null)
	    		{
	    			stractivity[i]=sObject.getProperty(i).toString().trim();
	    			i++;
	    		}  	
	    		
			} catch (Exception e) {
				// TODO: handle exception   			
			}
	    	handler2.sendEmptyMessage(0);
	    }
	}).start();      	
	}
	  private Handler handler2 =new Handler(){
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		if(i==0)
		{
		tv1.setText("无查询结果");
		tv2.setText("");	
		tv3.setText("");
		tv4.setText("");
		bu1.setVisibility(0x00000004);
		}
		else
		{
			tv1.setText("教师姓名："+stractivity[0]);
			tv2.setText("邮箱信息："+stractivity[1]);
			tv3.setText("教师介绍："+stractivity[2]);
			tv4.setText("所在办公室："+stractivity[3]);
			if(stractivity[4].equals(""))
			{
				bu1.setVisibility(0x00000004);
			}
			i=0;
		}
		}};

		private void get_officeinfo() {
			new Thread(new Runnable(){ 
			    public void run() {
			    	try {
			    		sObject=GetNetData.sendofficekw(searchdata[0]);
			    		while(sObject.getProperty(i)!=null)
			    		{
			    			stractivity[i]=sObject.getProperty(i).toString().trim();
			    			i++;
			    		}  	
			    		
					} catch (Exception e) {
						// TODO: handle exception   			
					}
			    	handler3.sendEmptyMessage(0);
			    }
			}).start();      	
			}
			  private Handler handler3 =new Handler(){
				public void handleMessage(Message msg){
				super.handleMessage(msg);
				if(i==0)
				{
				tv1.setText("无查询结果");
				tv2.setText("");	
				tv3.setText("");
				tv4.setText("");
				bu1.setVisibility(0x00000004);
				}
				else
				{
					tv1.setText("办公室地址："+stractivity[0]);
					tv2.setText("办公室电话："+stractivity[1]);
					tv3.setText("办公室老师："+stractivity[2]);
					tv4.setText("办公室职能："+stractivity[3]);
					if(stractivity[4].equals(""))
					{
						bu1.setVisibility(0x00000004);
					}
					i=0;
				}
				}};
}