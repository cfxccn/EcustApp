package com.usta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.getnetdata.GetNetData;

public class Advise extends SherlockActivity {
	private int index;
	Intent intent;
    private ArrayAdapter<String> adapter;  
   
    private static final String[] grade={"大一","大二","大三","大四","教职工","其他"};  

    String _sex;
    String advise;
    String _grade;
    EditText etext_advise;
    
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
	    	etext_advise=(EditText)findViewById(R.id.etext_advise);
	    	Button btn_submit_advise=(Button) findViewById(R.id.btn_submit_advise);
	    	btn_submit_advise.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					advise=etext_advise.getText().toString();
					new Thread(new Runnable(){
					    @Override
					    public void run() {
					    	try {
								GetNetData.sendadvise_xml(_sex, _grade, advise);
								//Toast.makeText(Advise.this, "发送成功", Toast.LENGTH_SHORT).show();

							} catch (Exception e) {
								// TODO: handle exception
								//Toast.makeText(Advise.this, "发送失败", Toast.LENGTH_SHORT).show();
								e.printStackTrace();
							}
					    }
					}).start();
				}
			});
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
