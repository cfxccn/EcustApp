package com.usta.ecustapp.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.usta.ecustapp.*;
import com.usta.ecustapp.service.*;
public class WelcomeActivity extends ActionBarActivity {
	JSONObject weatherJsonObject;
	JSONArray newsJsonArray;

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
      //  isfirst();
        
        
        
        
        new Handler().postDelayed(new Runnable(){   
            @Override   
            public void run() {   
 				Intent mainIntent =new Intent();
 				mainIntent.setClass(WelcomeActivity.this,FragmentActivity.class);   
                WelcomeActivity.this.startActivity(mainIntent);   
                WelcomeActivity.this.finish();   
            }   
           }, 500);  
        //init();
        
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
