package com.usta;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class Setting extends SherlockActivity {
	private int index;
	Intent intent;
	 SharedPreferences userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        initradiobtn();
    }
    
	  @SuppressLint("ShowToast")
	private void initradiobtn() {
		// TODO Auto-generated method stub
		  userInfo = getSharedPreferences("setting", 0);  
	    //	userInfo.edit().putString("area", "null").commit();  
	    	String area=userInfo.getString("area", "null");
	    	if(area.equalsIgnoreCase("xuhui")){
	    	//    userInfo.edit().putString("area", "xuhui").commit();  
	    		RadioButton radiobtn_xuhui_setting= (RadioButton)findViewById(R.id.radiobtn_xuhui_setting);
	    		radiobtn_xuhui_setting.setChecked(true);
	    	}
	    	if(area.equalsIgnoreCase("fengxian")){
    	   // 	userInfo.edit().putString("area", "fengxian").commit();  
	    		RadioButton radiobtn_fengxian_setting= (RadioButton)findViewById(R.id.radiobtn_fengxian_setting);
	    		radiobtn_fengxian_setting.setChecked(true);
	    	}
	    	if(area.equalsIgnoreCase("jinshan")){
    	   // 	userInfo.edit().putString("area", "jinshan").commit();  
	    		RadioButton radiobtn_jinshan_setting= (RadioButton)findViewById(R.id.radiobtn_jinshan_setting);
	    		radiobtn_jinshan_setting.setChecked(true);
	    	}
	    	final Toast toas1=Toast.makeText(this, "徐汇校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toas2=Toast.makeText(this, "奉贤校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toas3=Toast.makeText(this, "金山校区-已保存", Toast.LENGTH_SHORT);

	    	RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup_area_setting);
	    	         //绑定一个匿名监听器
	    	         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	             @Override
	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	    	                 // TODO Auto-generated method stub
	    	                 //获取变更后的选中项的ID
	    	            	 if(arg1==R.id.radiobtn_xuhui_setting){
	    	     	    	    userInfo.edit().putString("area", "xuhui").commit();  
	    	            		 toas1.show();
	    	            	 }	    	            	
	    	            	if(arg1==R.id.radiobtn_fengxian_setting){
		    	     	    	    userInfo.edit().putString("area", "fengxian").commit();  
		    	            		 toas2.show(); 
		    	            	 }
	    	            	 if(arg1==R.id.radiobtn_jinshan_setting){
	    	     	    	    userInfo.edit().putString("area", "jinshan").commit();  
	    	            		 toas3.show();
	    	            	 }

	    	             }
	    	         });
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//				Intent intent =new Intent();
//				intent.setClass( SearchDrugs.this, MainActivity.class);
//				startActivity(intent);
//				SearchDrugs.this.finish();
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */
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

