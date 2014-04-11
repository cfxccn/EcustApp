package com.usta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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


public class NearbyDetail extends SherlockActivity {
	private int index;
	Intent intent;

	String  type;
    String nearbyid;
    JSONArray jsonArray;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearbydetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent(); 
        nearbyid=intent.getStringExtra("nearbyid");
        index=intent.getIntExtra("index", 0);
        getnearbydetail();
    }


    
    private void getnearbydetail() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		jsonArray= GetNetData.getNeighbourDetail(Integer.parseInt(nearbyid));
	           

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
		TextView textView_nearbyTitle=(TextView)findViewById(R.id.textView_nearbyTitle);
		TextView textView_NearbyIntro=(TextView)findViewById(R.id.textView_NearbyIntro);
		TextView TextView_NearbyLocation=(TextView)findViewById(R.id.TextView_NearbyLocation);
		TextView TextView_NearbyDetail=(TextView)findViewById(R.id.TextView_NearbyDetail);
		TextView TextView_NearbyPhone=(TextView)findViewById(R.id.TextView_NearbyPhone);

		
		ImageView imageView_Nearbytype=(ImageView)findViewById(R.id.imageView_Nearbytype);
		
		try{
		textView_nearbyTitle.setText(jsonArray.getJSONObject(0).getString("name"));
		textView_NearbyIntro.setText(jsonArray.getJSONObject(0).getString("introduction"));
		TextView_NearbyLocation.setText(jsonArray.getJSONObject(0).getString("location"));
		TextView_NearbyDetail.setText(jsonArray.getJSONObject(0).getString("detail"));
		
		TextView_NearbyPhone.setText(jsonArray.getJSONObject(0).getString("phone"));

		
		type=jsonArray.getJSONObject(0).getString("type");
		}
		catch (Exception e) {
		}

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

