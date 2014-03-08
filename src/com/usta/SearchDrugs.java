package com.usta;


import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.microsoft.sqlserver.*;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;

public class SearchDrugs extends SherlockActivity {
	private int index;
	Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdrugs);
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
      //  getSupportActionBar().setHomeButtonEnabled(true);  
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);  
        
        initbtn();
    }
	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//				Intent intent =new Intent();
//				intent.setClass( SearchDrugs.this, MainActivity.class);
//				startActivity(intent);
//				SearchDrugs.this.finish();
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 

    private void initbtn(){
    	Button btnback=(Button)findViewById(R.id.btnback_drugs);
    	btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
			//	Intent intent =new Intent();
		//		intent.setClass( SearchDrugs.this, MainActivity.class);
			//	startActivity(intent);
			//	Bundle b = new Bundle();  
				//b.putInt("index", index);  	
		        //intent.putExtras(b);  
		        setResult(RESULT_OK, intent);  
		        finish();  
			//	SearchDrugs.this.setResult(RESULT_OK,intent);
			//	SearchDrugs.this.finish();
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
