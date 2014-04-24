package com.usta.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
//import com.commonsware.cwac.richedit.RichEditText;



import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.richedit.RichEditText;
import com.usta.R;
import com.usta.network.*;

public class Advise extends SherlockActivity {
	private int index;
	Intent intent;
    private ArrayAdapter<String> adapter;  
   
    private static final String[] grade={"大一","大二","大三","大四","教职工","其他"};  

    String _sex="";
    String advise="";
    String _grade;
    EditText etext_advise;
    
	String screenWidth ; 
	String  screenHeight ;
	String densityDPI ; 
    String version ;        
    String model;
    Toast toast1,toast2,toast3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        initbtn();
    }


	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 

	    private void initbtn(){
	    	 Spinner spn_grade	 = (Spinner) findViewById(R.id.spn_grade);  
	    	adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,grade);
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	spn_grade.setAdapter(adapter);  
	    //	spn_grade.setVisibility(View.VISIBLE);  
	    	spn_grade.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
		           _grade=grade[arg2];  
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}});  
	    	RadioGroup group = (RadioGroup)this.findViewById(R.id.rgrp_sex);
	         //绑定一个匿名监听器
	         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	             @Override
	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	                 // TODO Auto-generated method stub
	                 //获取变更后的选中项的ID
	            	 if(arg1==R.id.rbtn_sex_male){
	     	    	    _sex="男";
	            	 }	    	            	
	            	if(arg1==R.id.rbtn_sex_female){
	     	    	    _sex="女";
   	            	 }
	             }
	         });
	    	etext_advise=(RichEditText)findViewById(R.id.etext_advise);

	    }
private void sendadvise() {
	// TODO Auto-generated method stub
	DisplayMetrics dm = new DisplayMetrics();  
	getWindowManager().getDefaultDisplay().getMetrics(dm);  
//		dm = getResources().getDisplayMetrics();  
	screenWidth  = Integer.toString(dm.widthPixels); 
	screenHeight = Integer.toString(dm.heightPixels); 
	densityDPI = Integer.toString(dm.densityDpi);     // 屏幕密度（每寸像素：120/160/240/320）  
    version = android.os.Build.VERSION.RELEASE;  			        
    model=android.os.Build.MODEL;
	
	advise=etext_advise.getText().toString().trim();
	toast1=Toast.makeText(Advise.this, "发送成功", Toast.LENGTH_SHORT);
	toast2=Toast.makeText(Advise.this, "反馈失败，请检查联网", Toast.LENGTH_SHORT);
	toast3=Toast.makeText(Advise.this, "请输入内容并选择性别", Toast.LENGTH_SHORT);

	if("".equals(advise)||_sex==""){
		toast3.show();
		return ;
	}
	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
				if(com.usta.network.Advise.sendAdvise(_sex, _grade, advise,screenWidth,screenHeight,version,model,densityDPI)==1){
					toast1.show();

					}
				else{				
					toast2.show();
				}
				//toast1.show();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    }
	}).start();
	
}
	    @Override  
	    public boolean onOptionsItemSelected(MenuItem item) {  
	        switch(item.getItemId()){  
	      case android.R.id.home:  
	  	        setResult(RESULT_OK, intent);  
	  	        finish();  	        
	  	        break; 
	      case R.id.sendadvise: 
	    	  sendadvise();
	        break;  
	        }  
	        return super.onOptionsItemSelected(item);  
	    }  
	    
	    public boolean onCreateOptionsMenu(Menu menu) {	
	  		getSupportMenuInflater().inflate(R.menu.advise, menu);
	  		return true;
	  	}
	  }
