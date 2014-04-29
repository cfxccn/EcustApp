package com.usta.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends SherlockActivity {
	private int index;
	Intent intent;
	String userEmail;
	String userPwd0;
	String userPwd1;
	String userNickName;
	String userMobile;
	String userDepart;
	String userNum;
	String userRealName;
	String userKey;
	Toast toast0,toast1	, toast2, toast3	, toast4;
	 SharedPreferences userInfo;

	
	private static final String[] department = {"信息学院","化工学院","法学院","化学学院","理学院","人文学院","生工学院","外语学院","信息学院","药学院","艺术学院","资环学院"};
	Spinner spinnerUserDepart ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
		userInfo = getSharedPreferences("setting", 0);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        toast0=Toast.makeText(this, "正在注册", Toast.LENGTH_SHORT);

        toast1=Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT);
        toast2=Toast.makeText(this, "注册成功，正在重新启动", Toast.LENGTH_SHORT);
        toast3=Toast.makeText(this, "注册失败，昵称或邮箱已被使用", Toast.LENGTH_SHORT);
        toast4=Toast.makeText(this, "注册失败，检查网络", Toast.LENGTH_SHORT);

        initSpinner();
        initbtn();
    }
  private void initSpinner() {
  	spinnerUserDepart = (Spinner)findViewById(R.id.spinnerUserDepart);
  	ArrayAdapter<String> userDepartArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,department);
  	userDepartArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	spinnerUserDepart.setAdapter(userDepartArrayAdapter);
	}
private void initbtn() {
	  final EditText editTextUserEmail=(EditText)findViewById(R.id.editTextUserEmail);
	  final EditText editTextUserPwd0=(EditText)findViewById(R.id.editTextUserPwd0);
	  final EditText editTextUserPwd1=(EditText)findViewById(R.id.editTextUserPwd1);
	  final EditText editTextUserNickName=(EditText)findViewById(R.id.editTextUserNickName);
	  final EditText editTextUserNum=(EditText)findViewById(R.id.editTextUserNum);
	  final EditText editTextUserMobile=(EditText)findViewById(R.id.editTextUserMobile);
	  final EditText editTextUserRealName=(EditText)findViewById(R.id.editTextUserRealName);
	  final Spinner spinnerUserDepart=(Spinner)findViewById(R.id.spinnerUserDepart);


Button btn_RegSubmit=(Button)findViewById(R.id.btn_RegSubmit);
btn_RegSubmit.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		userEmail=editTextUserEmail.getText().toString();
		userPwd0=editTextUserPwd0.getText().toString();
		userPwd1=editTextUserPwd1.getText().toString();
		userNickName=editTextUserNickName.getText().toString();
		userMobile=editTextUserMobile.getText().toString();
		userNum=editTextUserNum.getText().toString();
		userRealName=editTextUserRealName.getText().toString();
		userDepart=spinnerUserDepart.getSelectedItem().toString();

		if(!userPwd0.equals(userPwd1)){
			toast1.show();
			return;
		}
		if(!userEmail.matches("\\w{0,}\\@\\w{0,}\\.{1}\\w{0,}")||
				!userMobile.matches("^[1][3-8]+\\d{9}")||
				!userNum.matches("^[0,1][0,2][0,1]+\\d{5}")||
				userNickName.trim().equalsIgnoreCase("")||
				userRealName.trim().equalsIgnoreCase("")){
			toast1.show();

		}else{
			}
		registerFromNewThread();
	}
	
});
}


	private Handler registerSuccess =new Handler(){
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		toast2.show();
  		userInfo = getSharedPreferences("setting", 0);  
 	    userInfo.edit().putString("useremail", userEmail).commit();  
 	    userInfo.edit().putString("userkey", userKey).commit();  
 	    userInfo.edit().putString("username", userNickName).commit();  
		Intent intent =new Intent();
		intent.setClass(Register.this,WelcomeActivity.class);   
		//	mainIntent.putExtra("weather", weatherJsonObject.toString());
		Register.this.startActivity(intent);   
		Register.this.finish();   
		}
		};
		private Handler registerFailureName =new Handler(){
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			toast3.show();

			}
			};
			private Handler registerFailureNet =new Handler(){
				public void handleMessage(Message msg){
				super.handleMessage(msg);
				toast4.show();

				}
				};


protected void registerFromNewThread() {
	// TODO Auto-generated method stub
	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	toast0.show();
	    	 userKey=Account.register(userNickName,userEmail,userPwd0,userMobile ,userDepart, userNum, userRealName);
	    	if(!userKey.startsWith("-")){
//注册成功
	    	
	    		registerSuccess.sendEmptyMessage(0);
	    	}
	    	else if(userKey=="-2"){
	    		registerFailureNet.sendEmptyMessage(0);
	    	//	return;
	    	}
	    	else if(userKey.equals("-1")){
	    		registerFailureName.sendEmptyMessage(0);
	    	//	return;
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

