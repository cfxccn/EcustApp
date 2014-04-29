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
import com.usta.account.Account;
import com.usta.network.*;




import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountSetting extends SherlockActivity {
	private int index;
	Intent intent;
	LayoutInflater inflater;
	String userEmail;
	String userPwd;
	String userKey="x";
	String userName;
	Toast Toast1;
	Toast Toast2;
	Toast Toast3;
	Toast Toast4;
	Toast Toast5;
	Toast Toast0;
	 SharedPreferences userInfo;
	 String userLocalName;
	 String userLocalEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);

        initLocalUserInfo();
        isLocalLogin();
        initbtn();
  	  	Toast1=Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT);
  	  	Toast2=Toast.makeText(this,"登录失败，请检查网络", Toast.LENGTH_SHORT);
  	  	Toast3=Toast.makeText(this,"注销成功", Toast.LENGTH_SHORT);
  	  	Toast4=Toast.makeText(this,"请输入信息", Toast.LENGTH_SHORT);
  	  	Toast5=Toast.makeText(this,"登录失败，帐号密码错误", Toast.LENGTH_SHORT);
  	  	Toast0=Toast.makeText(this,"正在登录", Toast.LENGTH_SHORT);


    }
private void initLocalUserInfo() {
		// TODO Auto-generated method stub
		userInfo = getSharedPreferences("setting", 0);  
		userLocalName=userInfo.getString("username", "null");
		userLocalEmail=userInfo.getString("useremail", "null");
	}
private void initbtn() {
		// TODO Auto-generated method stub
		final EditText editTextUserEmail=(EditText)findViewById(R.id.editTextUserEmail);
		final EditText editTextUserPwd=(EditText)findViewById(R.id.editTextUserPwd);

		Button btn_Login=(Button)findViewById(R.id.btn_Login);
		btn_Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userEmail=editTextUserEmail.getText().toString();
				userPwd=editTextUserPwd.getText().toString();
				if(userEmail.equalsIgnoreCase("")||userPwd.equalsIgnoreCase("")){
					Toast4.show();
					return;
				}
		    	Toast0.show();

				loginFromNewThread();
				
			}
		});
		Button btn_Reg=(Button)findViewById(R.id.btn_Reg);
		btn_Reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(AccountSetting.this, Register.class);
				startActivityForResult(intent, 0);

			}
		});
		Button btn_Logout=(Button)findViewById(R.id.btn_Logout);
		btn_Logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		  		userInfo = getSharedPreferences("setting", 0);  
		 	    userInfo.edit().putString("useremail", "null").commit();  
		 	    userInfo.edit().putString("userkey", "null").commit();  
		 	    userInfo.edit().putString("username", "null").commit();  
		 	    Toast3.show();
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(AccountSetting.this, AccountSetting.class);
				startActivityForResult(intent, 0);
		 	   finish();

			}
		});
	}

private Handler loginSuccess =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
    	Toast1.show();
  		userInfo = getSharedPreferences("setting", 0);  
 	    userInfo.edit().putString("useremail", userEmail).commit();  
 	    userInfo.edit().putString("userkey", userKey).commit();  
 	    userInfo.edit().putString("username", userName).commit();  
		Intent intent =new Intent();
		intent.putExtra("index", index);
		intent.setClass(AccountSetting.this, AccountSetting.class);
		startActivityForResult(intent, 0);
 	   finish();
 	    
 	    
// 	    isLocalLogin();
//        initbtn();
//	       setResult(RESULT_OK, intent);  
//	       finish();  
		}
		};
		private Handler loginFailure =new Handler(){
			@Override
			//当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
			super.handleMessage(msg);
	    	  Toast5.show();

			}
			};
			private Handler loginFailureNet =new Handler(){
				@Override
				//当有消息发送出来的时候就执行Handler的这个方法
				public void handleMessage(Message msg){
				super.handleMessage(msg);
		    	  Toast2.show();

				}
				};

protected void loginFromNewThread() {
	// TODO Auto-generated method stub
	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	Toast0.show();
	    	userKey= Account.login(userEmail, userPwd);

	 	    	if(!userKey.startsWith("-")){
	 				//登录成功
	 	    		
	 	    		userName=userKey.substring(0, userKey.indexOf(","));
	 	    		userKey=userKey.substring(userKey.indexOf(",")+1,userKey.length());
	 	    		loginSuccess.sendEmptyMessage(0);
	 	    		return;
	 	    	}
	 	    	else if(userKey.equals("-1")){
	 	    		loginFailure.sendEmptyMessage(0);
	 	    		return;

	 	    	}	 else if(userKey=="-2") {
	 	    		loginFailureNet.sendEmptyMessage(0);
	 	    		return;

	 	    	}	

	    		
	    	}
    		
	    	
	    	
	

    	

	    
	    
	    
	}).start();
   	
}
private void isLocalLogin() {
	
	LinearLayout layoutUserInfo=(LinearLayout)findViewById(R.id.layoutUserInfo);   
	LinearLayout layoutUserLogin=(LinearLayout)findViewById(R.id.layoutUserLogin);   


if(!userLocalName.equalsIgnoreCase("null")&&!userLocalEmail.equalsIgnoreCase("null")){
	layoutUserInfo.setVisibility(View.VISIBLE);
	layoutUserLogin.setVisibility(View.GONE);
	
	EditText textViewUserName=(EditText)findViewById(R.id.tvUserNickName);
	EditText textViewUserEmail=(EditText)findViewById(R.id.tvUserEmail);
	textViewUserName.setText(userLocalName);
	textViewUserEmail.setText(userLocalEmail);
	textViewUserName.setCursorVisible(false);//隐藏光标 
	textViewUserName.setFocusable(false);//失去焦点 
	textViewUserName.setFocusableInTouchMode(false);//虚拟键盘隐藏 
	textViewUserEmail.setCursorVisible(false);//隐藏光标 
	textViewUserEmail.setFocusable(false);//失去焦点 
	textViewUserEmail.setFocusableInTouchMode(false);//虚拟键盘隐藏 
	
	
}else{
	layoutUserInfo.setVisibility(View.GONE);
	layoutUserLogin.setVisibility(View.VISIBLE);
	
}

	
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

