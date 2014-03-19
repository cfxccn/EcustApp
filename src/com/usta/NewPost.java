package com.usta;

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
        initbtn();
    }
    private void initbtn(){
    	Button btn_submit_newpost=(Button)findViewById(R.id.btn_submit_newpost);
    	btn_submit_newpost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{ 
				EditText edittitle=	(EditText)findViewById(R.id.edittitle);
				title=edittitle.getText().toString();
				EditText editcontent=	(EditText)findViewById(R.id.editcontent);
				content=editcontent.getText().toString();
				new Thread(new Runnable(){
				    @Override
				    public void run() {
				    	try {
							GetNetData.sendnewpost_xml(title, content);
							//Toast.makeText(NewPost.this, "发送成功", Toast.LENGTH_SHORT).show();

				    	} catch (Exception e) {
							//Toast.makeText(NewPost.this, "发送失败", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
				    }
				}).start();
				
			}
		});
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

