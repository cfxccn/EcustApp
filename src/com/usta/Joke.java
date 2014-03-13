package com.usta;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Joke extends SherlockActivity  {
//	private String text = " 12";
//	int num;
//	jokeSqlitedata jsd;
	private int index;
	Intent intent;
	private String joke;

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joke);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);
		initbtn();
	}


	private void getTestJsonServerDataFromNewThread()
	{
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	    //	getTestJsonServerDataFrom(url);
    					try {
    		    	    	JSONObject jsonObject= 	GetNetData.getjokedata();
							joke=(String)jsonObject.get("joke");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

    	    	 handler.sendEmptyMessage(0);
    	    }
    	}).start();
	}
    private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		((TextView) findViewById(R.id.textview_joke)).setText(joke);
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

	private void initbtn() {
		Button btnback = (Button) findViewById(R.id.btnback_joke);
		btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		        setResult(RESULT_OK, intent);  
		        finish();  
			}
		});
		Button button = (Button) findViewById(R.id.btnjoke);
		button.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getTestJsonServerDataFromNewThread();

			}
		});
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
