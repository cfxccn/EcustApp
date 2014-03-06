package com.usta;

import java.sql.DriverManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class SearchDrugs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdrugs);
        init();
    }
	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
				Intent intent =new Intent();
				intent.setClass( SearchDrugs.this, MainActivity.class);
				startActivity(intent);
				SearchDrugs.this.finish();
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 

    private void init(){
    	Button btnback=(Button)findViewById(R.id.btnback_drugs);
    	btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
				Intent intent =new Intent();
				intent.setClass( SearchDrugs.this, MainActivity.class);
				startActivity(intent);
				SearchDrugs.this.finish();
			}
		});
    	Button btnsearch_drugs=(Button)findViewById(R.id.btnsearch_drugs);
    	btnsearch_drugs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{

				}
		});
    }
}
