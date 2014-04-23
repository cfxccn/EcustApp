package com.usta.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.getnetdata.GetNetData;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.usta.R;

public class ViewPost extends SherlockActivity {
	private int index;
	private int postid;
	Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        postid=intent.getIntExtra("postid", 0);
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
    case R.id.newpostback: 
    	Intent intent =new Intent();
		intent.putExtra("index", index);
		intent.putExtra("postid", postid);
		intent.setClass(ViewPost.this, NewPostBack.class);
		startActivityForResult(intent, 0);
      break;  
      }  
      return super.onOptionsItemSelected(item);  
  }  
  
  public boolean onCreateOptionsMenu(Menu menu) {	
		getSupportMenuInflater().inflate(R.menu.viewpost, menu);
		return true;
	}
}

