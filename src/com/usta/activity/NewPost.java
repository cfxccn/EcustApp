package com.usta.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.usta.R;

public class NewPost extends SherlockActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
       // initbtn();
    }
    private void sendpost(){

			EditText edittitle=	(EditText)findViewById(R.id.edittitle);
			title=edittitle.getText().toString();
			EditText editcontent=	(EditText)findViewById(R.id.editcontent);
			content=editcontent.getText().toString();
			new Thread(new Runnable(){
			    @Override
			    public void run() {
			    	try {
			//			GetNetData.sendnewpost_xml(title, content);
						//Toast.makeText(NewPost.this, "发送成功", Toast.LENGTH_SHORT).show();

			    	} catch (Exception e) {
						//Toast.makeText(NewPost.this, "发送失败", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
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
    
  public boolean onCreateOptionsMenu(Menu menu) {	
		getSupportMenuInflater().inflate(R.menu.newpost, menu);
		return true;
	}


  @Override  
  public boolean onOptionsItemSelected(MenuItem item) {  
      switch(item.getItemId()){  

      		case android.R.id.home:  
	        setResult(RESULT_OK, intent);  
	        finish();  	        
	        break;  
      		case R.id.sendadvise: 
      			sendpost();
      		break;  
	        
	        
      }  
      return super.onOptionsItemSelected(item);  
  }  
}

