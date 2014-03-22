package com.usta;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class SchoolBus extends SherlockActivity
{
	private int index;
	Intent intent;
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.schoolbus);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
	        intent = getIntent();
	        index=intent.getIntExtra("index", 0);
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
	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 

}
