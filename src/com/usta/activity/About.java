package com.usta.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.usta.R;
import com.usta.getnetdata.GetNetData;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class About extends SherlockActivity {
	private int index;
	Intent intent;
	String buildStringFromServer;
	String buildInLocal;
	protected Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);

		TextView textView_version=(TextView)findViewById(R.id.textView_version);
		buildInLocal="";
		try {
			buildInLocal = String.valueOf(getPackageManager().getPackageInfo("com.usta", 0).versionCode);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        buildStringFromServer=buildInLocal;
		textView_version.setText("Build:"+buildInLocal);
		
      initbtn();
    }
  private void initbtn() {
		// TODO Auto-generated method stub
		btn_update=(Button)findViewById(R.id.btn_update);
		btn_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startUpdateFromNewThread();

			}
		});
		Button btn_checkforupdate=(Button)findViewById(R.id.btn_checkforupdate);
		btn_checkforupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getLatestVersionFromNewThread();

			}
		});

	}
protected void startUpdateFromNewThread() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			final Uri uri =Uri.parse("http://172.18.113.24:9092/update");          
	        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
	        startActivity(it);
		}
	        
		}).start();
	}
protected void getLatestVersionFromNewThread() {
	new Thread(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				buildStringFromServer=String.valueOf(GetNetData.getLatestVersion());
		    	if(Integer.parseInt(buildStringFromServer)>Integer.parseInt(buildInLocal)) 
				{//更新
		    		update_handler.sendEmptyMessage(0);
				}else  if(Integer.parseInt(buildStringFromServer)==Integer.parseInt(buildInLocal)) 
				{
					newest_handler.sendEmptyMessage(0);
				}else
				{
					noUpdate_handler.sendEmptyMessage(0);
				}
					
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}).start();
}
private Handler update_handler =new Handler(){
	@Override
	//当有消息发送出来的时候就执行Handler的这个方法
	public void handleMessage(Message msg){
	super.handleMessage(msg);
	initUpdateBtn();
	}
	};
	private Handler noUpdate_handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		Toast toast2=Toast.makeText(About.this, "检查不到新版本", Toast.LENGTH_SHORT);
		toast2.show();
		}
		};
		private Handler newest_handler =new Handler(){
			@Override
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			Toast toast3=Toast.makeText(About.this, "已经是最新版本", Toast.LENGTH_SHORT);
			toast3.show();
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
    


  protected void initUpdateBtn() {
	// TODO Auto-generated method stub
		Toast toast1=Toast.makeText(About.this, "最新版本"+buildStringFromServer, Toast.LENGTH_SHORT);
		toast1.show();
		btn_update.setVisibility(1);

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

