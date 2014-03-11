package com.usta;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setTheme(R.style.Theme_Sherlock_Light_NoActionBar);
        setContentView(R.layout.activity_welcome);
        
        new Handler().postDelayed(new Runnable(){   
            @Override   
            public void run() {   
                Intent mainIntent = new Intent(WelcomeActivity.this,MainActivity.class);   
                WelcomeActivity.this.startActivity(mainIntent);   
                WelcomeActivity.this.finish();   
            }   
           }, 1000);  
        //init();
        isfirst();
        
    }
    protected void isfirst() {
    	
    	SharedPreferences userInfo = getSharedPreferences("setting", 0);  
    	String isfirst=userInfo.getString("isfirst", "yes");
//    	userInfo.edit().putString("name", user.getText().toString()).commit();  
//    	userInfo.edit().putString("area", user.getText().toString()).commit();  
    	if(isfirst=="yes"){
    	Toast toats=Toast.makeText(this, "首次使用请设置校区",Toast.LENGTH_LONG );
    	toats.show();
    	userInfo.edit().putString("isfirst","no").commit();  
    	}
	}
    
    
   

    

    
    
}
