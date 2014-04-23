package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class NearbyDetail extends SherlockActivity {
	private int index;
	Intent intent;

	String  type;
    String nearbyid;
    JSONObject jsonObject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearbydetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent(); 
        nearbyid=intent.getStringExtra("nearbyid");
        index=intent.getIntExtra("index", 0);
        getNearbyDetailFromNewThread();
    }


    
    private void getNearbyDetailFromNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		jsonObject= GetNetData.getNeighbourDetail(Integer.parseInt(nearbyid));
	    		if(jsonObject!=null){	
	    			handler.sendEmptyMessage(0);
	    			}
	    		else{return;}
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
		TextView textView_nearbyTitle=(TextView)findViewById(R.id.textView_nearbyTitle);
		TextView textView_NearbyIntro=(TextView)findViewById(R.id.textView_NearbyIntro);
		TextView TextView_NearbyLocation=(TextView)findViewById(R.id.TextView_NearbyLocation);
		TextView TextView_NearbyDetail=(TextView)findViewById(R.id.TextView_NearbyDetail);
		TextView TextView_NearbyPhone=(TextView)findViewById(R.id.TextView_NearbyPhone);
		TextView textView3=(TextView)findViewById(R.id.textView3);
		TextView textView4=(TextView)findViewById(R.id.textView4);
		TextView textView5=(TextView)findViewById(R.id.textView5);
		textView3.setText("���飺");
		textView4.setText("�ص㣺");
		textView5.setText("�绰��");

		
		ImageView imageView_Nearbytype=(ImageView)findViewById(R.id.imageView_Nearbytype);
		
		textView_nearbyTitle.setText(jsonObject.optString("name"));
		textView_NearbyIntro.setText(jsonObject.optString("introduction"));
		TextView_NearbyLocation.setText(jsonObject.optString("location"));
		TextView_NearbyDetail.setText(jsonObject.optString("detail"));
		
		TextView_NearbyPhone.setText(jsonObject.optString("phone"));

		
		type=jsonObject.optString("type");


		if(type.equalsIgnoreCase("��")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n1));
		}else
		if(type.equalsIgnoreCase("ס")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n2));
		}else
		if(type.equalsIgnoreCase("��")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n3));
		}else
		if(type.equalsIgnoreCase("��")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n4));
		}else
		if(type.equalsIgnoreCase("����")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n5));
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

