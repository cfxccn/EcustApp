package com.usta;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R.string;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About extends SherlockActivity {
	private int index;
	Intent intent;
	String buildString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);

		TextView textView_version=(TextView)findViewById(R.id.textView_version);

		textView_version.setText("Build:"+getResources().getString(R.string.build));
		
      initbtn();
    }
  private void initbtn() {
		// TODO Auto-generated method stub
		 
		Button btn_checkforupdate=(Button)findViewById(R.id.btn_checkforupdate);
		btn_checkforupdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getLatestVersionFromNewThread();
				Toast toast1=Toast.makeText(About.this, buildString, Toast.LENGTH_SHORT);
				toast1.show();
			}
		});

	}
protected void getLatestVersionFromNewThread() {
	new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			buildString=String.valueOf(GetNetData.getLatestVersion());
		}
	}).start();
}
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

