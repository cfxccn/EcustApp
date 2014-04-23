package com.usta.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R.string;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.usta.R;

public class Powerfare extends SherlockActivity {
	private int index;
	Intent intent;
	String fare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powerfare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);

      initbtn();
    }
  private void initbtn() {
		// TODO Auto-generated method stub
		 
		Button button_getpowerfare=(Button)findViewById(R.id.button_getpowerfare);
		button_getpowerfare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText editText_building=(EditText)findViewById(R.id.editText_building);
				EditText editText_room=(EditText)findViewById(R.id.editText_room);
				String building=editText_building.getText().toString();
				String room=editText_room.getText().toString();
				getpowerfareddata(building,room);

			}
		});

	}
  
  private void getpowerfareddata(final String building,final String room) {
		// TODO Auto-generated method stub
	   	new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	try {
		    	//	fare=GetNetData.getPowerFare(building,room);
		    		fare="75757";
		    		power_handler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}).start();
	}
	private Handler power_handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		TextView 	textView_fare=(TextView)findViewById(R.id.textView_fare);
		textView_fare.setText(fare);
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

