package com.usta.activity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.network.Bus;
import com.usta.network.HttpUtils;
import com.usta.network.Job;
import com.usta.network.Lecture;




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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SchoolBus extends SherlockActivity 
{
	private int index;
	final private static String path1="http://172.18.113.24:8080/jsonProjject/servlet/action2?action_flag=schoolbus";
	Intent intent;
	private static String day,type,route;
	private static final String[] mDay={"��һ","�ܶ�","����","����","����","����","����"};
	private static final String[] bustype={"��ְ�೵","���ð೵","�ξ��೵"};
	private static final String[] laifan={"����-���","����-��ɽ","���-����","���-��ɽ","��ɽ-���","��ɽ-����"};
    JSONArray busJsonArray;
	Spinner spinnerDay;
	Spinner spinnerType;
	Spinner spinnerRoute;
	ListView listViewBus;
	Toast toast1;
	Toast toast2;

	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.schoolbus);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
	        intent = getIntent();
	        index=intent.getIntExtra("index", 0);
	        
	        init_spinner();
	        init_btn();
	        
	        listViewBus=(ListView)findViewById(R.id.listViewBus);

	        toast1=Toast.makeText(this,"�޺��ʰ��", Toast.LENGTH_SHORT);
	 }
	 private void init_btn() {
		// TODO Auto-generated method stub
		Button btnSearchBus=(Button)findViewById(R.id.btnSearchBus);
		btnSearchBus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				day=spinnerDay.getSelectedItem().toString();
				type=spinnerType.getSelectedItem().toString();
				route=spinnerRoute.getSelectedItem().toString();
				getBusDataFromNewThread();
				
			}
		});
	}	 
	 private   Handler handler =new Handler(){
		 @Override
		 //������Ϣ���ͳ�����ʱ���ִ��Handler���������
		 public void handleMessage(Message msg){
		 super.handleMessage(msg);
		 initListView();
		 }
		 };	

	private Handler resultEmptyhandler =new Handler(){
		@Override
		//������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg){
		super.handleMessage(msg);
  	  listViewBus.setAdapter(null);

    	  toast1.show();

		}
		};


			    
			 private void initListView() {
				 //jobsTitilesJsonArray
				  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			      for(int i=0;i<busJsonArray.length();i++)
			      {
			    	  JSONObject busJsonObject=busJsonArray.optJSONObject(i);
			          HashMap<String, Object> map = new HashMap<String, Object>();
			          map.put("textViewTime",busJsonObject.optString("time"));
			          map.put("textView_PUPoint","�ϳ���:"+busJsonObject.optString("PUPoint"));
			          map.put("textView_Destination", "�³���:"+busJsonObject.optString("Destination"));
			          map.put("textViewPrice", busJsonObject.optString("price"));
			         
			          listItem.add(map);
			      }
					// TODO Auto-generated method stub
				 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//����Դ 
				            R.layout.bus_listview,//ListItem��XMLʵ��
				            //��̬������ImageItem��Ӧ������        
				            new String[] {"textViewTime","textView_PUPoint", "textViewPrice","textView_Destination"}, 
				            //ImageItem��XML�ļ������һ��ImageView,����TextView ID
				            new int[] {R.id.textViewTime,R.id.textView_PUPoint,R.id.textViewPrice,R.id.textView_Destination }
				        );
				 listViewBus.setAdapter(listItemAdapter);
			 }

			  private void getBusDataFromNewThread() {
			   	new Thread(new Runnable(){
				    @Override
				    public void run() {
				    	try {
				    		
				    		busJsonArray=Bus.getBusInfo(day, type,route );
				    
				    		if(busJsonArray!=null){
				    			if(busJsonArray.length()==0){
				    				resultEmptyhandler.sendEmptyMessage(0);
				    				return ;
				    			}
						    	 handler.sendEmptyMessage(0);
				    		}
				    		else {
					    		return;

				    		}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
			    			return;

						}
				    }
				}).start();
			   	
				}

	private void init_spinner() {
		// TODO Auto-generated method stub
		 spinnerDay=(Spinner)findViewById(R.id.spinnerDay);
		 ArrayList<String> allday=new ArrayList<String>();
		 for(int i=0;i<mDay.length;i++)
		 {
			 allday.add(mDay[i]);
		 }
		 ArrayAdapter<String> dayArrayAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allday);
		 dayArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinnerDay.setAdapter(dayArrayAdapter);
		 
		  spinnerType=(Spinner)findViewById(R.id.spinnerType);
			 ArrayList<String> alltype=new ArrayList<String>();
			 for(int i=0;i<bustype.length;i++)
			 {
				 alltype.add(bustype[i]);
			 }
			 ArrayAdapter<String> typeArrayAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,alltype);
			 typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 spinnerType.setAdapter(typeArrayAdapter);
			 
			 
			 
			 spinnerRoute=(Spinner)findViewById(R.id.spinnerRoute);
			 ArrayList<String> allroute=new ArrayList<String>();
			 for(int i=0;i<laifan.length;i++)
			 {
				 allroute.add(laifan[i]);
			 }
			 ArrayAdapter<String> routeArrayAdapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,allroute);
			 routeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 spinnerRoute.setAdapter(routeArrayAdapter);
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

	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
		        setResult(RESULT_OK, intent);  
		        finish();  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }



}