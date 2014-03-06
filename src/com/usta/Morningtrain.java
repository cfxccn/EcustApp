package com.usta;

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

public class Morningtrain extends Activity {
	private String id;
	private String pwd;
	private String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morningtrain);
        init();
    }


	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
				Intent intent =new Intent();
				intent.setClass( Morningtrain.this, MainActivity.class);
				startActivity(intent);
				Morningtrain.this.finish();
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 

	    private void init(){
	    	Button btnback=(Button)findViewById(R.id.btnback_mt);
	    	btnback.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					// setContentView(R.layout.activity_main);
					Intent intent =new Intent();
					intent.setClass( Morningtrain.this, MainActivity.class);
					startActivity(intent);
					Morningtrain.this.finish();
					
				}
			});
	    	Button btnok_mt=(Button)findViewById(R.id.btnok_mt);
	    	btnok_mt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					 
					id=((EditText)findViewById(R.id.id_mt)).getText().toString();
					pwd=((EditText)findViewById(R.id.pwd_mt)).getText().toString();

					
					getMTdata(id);
					}
			});
	    }
		   private static	String namespace="http://tempuri.org/";
	        final static  String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";  
	        protected void  getMTdata(final String _id){
	        	new Thread(new Runnable(){
	        	    @Override
	        	    public void run() {
	        	    	getmorningtraindata(_id);
	        	    	 handler.sendEmptyMessage(0);
	        	    }
	        	}).start();

	        };
	        private Handler handler =new Handler(){
        		@Override
        		//������Ϣ���ͳ�����ʱ���ִ��Handler���������
        		public void handleMessage(Message msg){
        		super.handleMessage(msg);
    			TextView tv=(TextView)findViewById(R.id.textView4);
        			tv.setText(count);
        		}
        		};
		protected void getmorningtraindata(String _id) {
	
			String methodname ="selectJoke"; 
			String soapaction=namespace+methodname;
			SoapObject request = new SoapObject(namespace, methodname);
	        request.addProperty("_id",_id);
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;//�Ƿ���dotNet WebService  
	        envelope.bodyOut=request;
	        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
            HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
	        ht.debug = true;
            try  
            {   
            	//System.out.println(soapaction);
                // ��5��������WebService  
                ht.call(soapaction, envelope);  
            	System.out.println("ht.call" ); 
                if (envelope.getResponse() != null)  
                {  
                    // ��6����ʹ��getResponse�������WebService�����ķ��ؽ��  
                	SoapPrimitive  soapObject = (SoapPrimitive ) envelope.getResponse();  
                    // ͨ��getProperty�������Product���������ֵ  
                   count =   soapObject.toString() + "\n";  
                }  
                else {  
                		count="222";
                }  
            }  
            catch (Exception e)  
            {  
        		count=e.toString();
            }  //����Handler����
			//TextView tv=(TextView)findViewById(R.id.textView4);
		//	tv.setText(count);
        	System.out.println(count);

		}

}