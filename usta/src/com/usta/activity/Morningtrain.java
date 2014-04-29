package com.usta.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;

public class Morningtrain extends SherlockActivity {
	private int index;
	Intent intent;
	private String id;
	private String pwd;
	private String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morningtrain);
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
	    	Button btnback=(Button)findViewById(R.id.btnback_mt);
	    	btnback.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					// setContentView(R.layout.activity_main);
//					Intent intent =new Intent();
//					intent.setClass( Morningtrain.this, MainActivity.class);
//					startActivity(intent);
//					Morningtrain.this.finish();
			        setResult(RESULT_OK, intent);  
			        finish();  
				}
			});
	    	Button btnok_mt=(Button)findViewById(R.id.btnok_mt);
	    	btnok_mt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					 
					id=((EditText)findViewById(R.id.id_mt)).getText().toString();
					pwd=((EditText)findViewById(R.id.pwd_mt)).getText().toString();

					
					getDataFromNewThread();
					}
			});
	    }
		   private static	String namespace="http://tempuri.org/";
	        final static  String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";  
	        protected void  getDataFromNewThread(){
	        	new Thread(new Runnable(){
	        	    @Override
	        	    public void run() {
	        	    	getmorningtraindata();
	        	    	 handler.sendEmptyMessage(0);
	        	    }
	        	}).start();

	        };
	        private Handler handler =new Handler(){
        		@Override
        		//当有消息发送出来的时候就执行Handler的这个方法
        		public void handleMessage(Message msg){
        		super.handleMessage(msg);
    			TextView tv=(TextView)findViewById(R.id.textView4);
        			tv.setText(count);
        		}
        		};
		protected void getmorningtraindata() {
	
			String methodname ="selectJoke"; 
			String soapaction=namespace+methodname;
			SoapObject request = new SoapObject(namespace, methodname);
	     //   request.addProperty("_id","");
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;//是否是dotNet WebService  
	        envelope.bodyOut=request;
	        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
            HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
	        ht.debug = true;
            try  
            {   
            	//System.out.println(soapaction);
                // 第5步：调用WebService  
                ht.call(soapaction, envelope);  
            	System.out.println("ht.call" ); 
                if (envelope.getResponse() != null)  
                {  
                    // 第6步：使用getResponse方法获得WebService方法的返回结果  
                	SoapPrimitive  soapObject = (SoapPrimitive ) envelope.getResponse();

//                	SoapObject result = (SoapObject) envelope.bodyIn;  
//                	SoapObject detail = (SoapObject) result.getProperty(0);                  	
                   count =   soapObject.toString() + "\n";  
                }  
                else {  
                		count="222";
                }  
            }  
            catch (Exception e)  
            {  
        		count=e.toString();
            }  //定义Handler对象
			//TextView tv=(TextView)findViewById(R.id.textView4);
		//	tv.setText(count);
        	System.out.println(count);

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
