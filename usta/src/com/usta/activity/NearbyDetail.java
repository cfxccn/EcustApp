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
import com.usta.network.Nearby;

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
    String nearbytype;
    JSONObject jsonObject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearbydetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent(); 
        nearbyid=intent.getStringExtra("nearbyid");
        nearbytype=intent.getStringExtra("nearbytype");       
        index=intent.getIntExtra("index", 0);
        getNearbyDetailViaNewThread();
    }


    
    private void getNearbyDetailViaNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		jsonObject= Nearby.getNearbyDetails(nearbytype,Integer.parseInt(nearbyid));
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
		//当有消息发送出来的时候就执行Handler的这个方法
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
		textView3.setText("详情：");
		textView4.setText("地点：");
		textView5.setText("电话：");

		
		ImageView imageView_Nearbytype=(ImageView)findViewById(R.id.imageView_Nearbytype);
		
		textView_nearbyTitle.setText(jsonObject.optString("name").trim());
		textView_NearbyIntro.setText(jsonObject.optString("introduction").trim());
		TextView_NearbyLocation.setText(jsonObject.optString("location").trim());
		TextView_NearbyDetail.setText(jsonObject.optString("detail").trim());
		
		TextView_NearbyPhone.setText(jsonObject.optString("phone").trim());

		
		type=jsonObject.optString("type").trim();


		if(type.equalsIgnoreCase("吃")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n1));
		}else
		if(type.equalsIgnoreCase("住")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n2));
		}else
		if(type.equalsIgnoreCase("行")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n3));
		}else
		if(type.equalsIgnoreCase("玩")){
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(R.drawable.n4));
		}else
		if(type.equalsIgnoreCase("其他")){
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

