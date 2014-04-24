package com.usta.activity;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.network.HttpUtils;




import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SchoolBus extends SherlockActivity implements OnClickListener
{
	private int index;
	final private static String path1="http://172.18.113.24:8080/jsonProjject/servlet/action2?action_flag=schoolbus";
	Intent intent;
	private static String bus,sp1,sp2,sp3,jsonString;
	private static final String[] mDay={"周一","周二","周三","周四","周五","周六","周日"};
	private static final String[] bustype={"教职班车","中旅班车","梦境班车"};
	private static final String[] laifan={"奉贤-徐汇","奉贤-金山","徐汇-奉贤","徐汇-金山","金山-徐汇","金山-奉贤"};
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.schoolbus);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
	        intent = getIntent();
	        index=intent.getIntExtra("index", 0);
	        find_spainner_view();
	        find_spainner_view2();
	        find_spainner_view3();
	        Button button=(Button)findViewById(R.id.button1);
	        button.setOnClickListener(this);
	       
	 }
	 public int getResourceId(String name)
		{
		  
			Field field;
			try {
				field = R.drawable.class.getField(name);
				return Integer.parseInt(field.get(null).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return 0;
		}
	 public  void tubiao()
	 {
		 TextView textview =(TextView)this.findViewById(R.id.textView1);
		 String html="<img src='test2'/>";
		 CharSequence ch= Html.fromHtml(html, new ImageGetter()
		 {
			 public  Drawable getDrawable(String  source)
			 {
			 Drawable drawable=getResources().getDrawable(getResourceId(source));
			// if(source.equals(object))
			 drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2);
			 
			 return drawable;
			 }
		 }, null);
		textview.setText(ch);
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
	  private void find_spainner_view()
	  {
		  Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
			 ArrayList<String> allday=new ArrayList<String>();
			 for(int i=0;i<mDay.length;i++)
			 {
				 allday.add(mDay[i]);
			 }
			 ArrayAdapter<String> aspnCountries =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allday);
			 aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 spinner1.setAdapter(aspnCountries);
	  }
	  private void  find_spainner_view2()
	  {
		  Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
			 ArrayList<String> allday=new ArrayList<String>();
			 for(int i=0;i<bustype.length;i++)
			 {
				 allday.add(bustype[i]);
			 }
			 ArrayAdapter<String> aspnCountries =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allday);
			 aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 spinner2.setAdapter(aspnCountries);
		  
	  }
	  private void  find_spainner_view3()
	  {
		  Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
			 ArrayList<String> allday=new ArrayList<String>();
			 for(int i=0;i<laifan.length;i++)
			 {
				 allday.add(laifan[i]);
			 }
			 ArrayAdapter<String> aspnCountries =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allday);
			 aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 spinner3.setAdapter(aspnCountries);
		  
	  }
	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }

	  public static String[] spinnervalue()
	  {
		  String[] strs=new String[3];
		  strs[0]=sp1;
		  strs[1]=sp2;
		  strs[2]=sp3;
		  return strs;
		  
	  }
	  private   Handler handler =new Handler(){
			 @Override
			 //当有消息发送出来的时候就执行Handler的这个方法
			 public void handleMessage(Message msg){
			 super.handleMessage(msg);
			 //处理UI
			 String string1 ="⊙";
			 jsonString=jsonString.replace("[" ,"");
			 jsonString=jsonString.replace("]","");
			 jsonString=jsonString.replace("time","出发时间");
			 jsonString=jsonString.replace("PUPoint","上车地点");
			 jsonString=jsonString.replace("Destination","下车地点");
			 jsonString=jsonString.replace("=","：");
			 //tubiao();
			 String[] strs ;
				// TODO Auto-generated method stub
				strs=jsonString.split("schoolbus");
				
				for(int i=0;i<strs.length;i++)
				{
					 if(i==0)
					 {
						 ((TextView) findViewById(R.id.textView1)).setText("");
					 }
					 else{
					 ((TextView) findViewById(R.id.textView1)).setText(((TextView) findViewById(R.id.textView1)).getText()+"  "+string1+strs[i]+"\n");
				  }
				}
				
		//	 ((TextView) findViewById(R.id.textView1)).setText(((TextView) findViewById(R.id.textView1)).getText()+jsonString);
			 }
			 };
		@Override
		public void onClick(View v) {
			
			
			
				new  Thread()
				{
					
					public void run(){
						Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
						sp1=spinner1.getSelectedItem().toString();
						Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
						sp2=spinner2.getSelectedItem().toString();
						Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
						sp3=spinner3.getSelectedItem().toString();
					    HttpUtils.postspdata();
				        jsonString =HttpUtils.getJsoncontent(path1);
						
				    handler.sendEmptyMessage(0);
				}
				
				}.start();
			
		
}
}
