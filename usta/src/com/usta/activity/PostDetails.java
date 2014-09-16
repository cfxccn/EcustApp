package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.usta.R;
import com.usta.service.PostService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.MenuItem;


public class PostDetails extends ActionBarActivity {
	private int index;	
	Intent intent;
	String  type;
    String postid;
    JSONArray postdetailsJsonArray;
	private ListView listViewPostBack;
	private EditText editTextPostBack;
	private Toast toast1;
	private Toast toast2;
	private Toast toast3;
	private Toast toast4;
	private SharedPreferences userInfo;
	private String userkey;
	private String useremail;
	private String text;
	String anony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent(); 
        postid=intent.getStringExtra("postid");
        index=intent.getIntExtra("index", 0);

        listViewPostBack = (ListView) findViewById(R.id.listViewPostBack);
        listViewPostBack.clearAnimation();

        editTextPostBack=(EditText)findViewById(R.id.editTextPostBack);
        initLocalUserInfo();
        initbtn();
        getPostDetailsViaNewThread();
        toast1=Toast.makeText(this, "请先至 设置-账号管理 登录", Toast.LENGTH_SHORT);
        toast2=Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT);
        toast3=Toast.makeText(this, "请先至 设置-账号管理 重新登录", Toast.LENGTH_SHORT);
        toast4=Toast.makeText(this, "发送失败 请检查网络", Toast.LENGTH_SHORT);
    }

    private void initbtn() {
		// TODO Auto-generated method stub
		Button btnPostBack=(Button)findViewById(R.id.btnPostBack);
		btnPostBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				text=editTextPostBack.getText().toString();
				if(useremail.equals("null")){
					toast1.show();
				}else{
					newPostBackViaNewThread();
					}
			}
		});
	}
    private Handler newPostBackSuccess=new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    	super.handleMessage(msg);
    	toast2.show();
    	editTextPostBack.setText("");
    	getPostDetailsViaNewThread();
    	}
    	};
    	private Handler newPostBackFailureNet=new Handler(){
    		@Override
    		//当有消息发送出来的时候就执行Handler的这个方法
    		public void handleMessage(Message msg){
    		super.handleMessage(msg);
    		toast4.show();
    		}
    		};
    		private Handler newPostBackFailure=new Handler(){
    			@Override
    			//当有消息发送出来的时候就执行Handler的这个方法
    			public void handleMessage(Message msg){
    			super.handleMessage(msg);
    			toast3.show();
    			}
    			};
PostService postService;
        private void newPostBackViaNewThread() {
        	// TODO Auto-generated method stub
        	new Thread(new Runnable(){
        	    @Override
        	    public void run() {
        	    		int i=postService.newPostBack(postid, text,useremail,userkey,anony);
        				if(i==1){
        					newPostBackSuccess.sendEmptyMessage(0);
        					}
        				else if (i==-2){			
        					newPostBackFailureNet.sendEmptyMessage(0);
        				}
        				else if(i==-1){
        					newPostBackFailure.sendEmptyMessage(0);
        				}
        	    }
        	}).start();
        }
    private void initLocalUserInfo() {
		userInfo = getSharedPreferences("setting", 0);  
		userkey=userInfo.getString("userkey", "null");
		useremail=userInfo.getString("useremail", "null");
   	 	anony=userInfo.getString("anony", "null");
   	 	if(anony.equals("on")){
   	 	anony="1";
   	 	}else{
   	   	 	anony="0";

   	 	}
	}
    private void getPostDetailsViaNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		postdetailsJsonArray= postService.getPostDetails(postid);
	    		if(postdetailsJsonArray!=null){	
	    			handler.sendEmptyMessage(0);
	    			}
	    		else{return;}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    }
	}).start();
	}

private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		initlistview();
		}
		};
		protected void initlistview() {
			// TODO Auto-generated method stub
			 //jobsTitilesJsonArray
			JSONObject firstJsonObject=postdetailsJsonArray.optJSONObject(0);
			JSONArray postbacksJsonArray=postdetailsJsonArray.optJSONArray(1);
			TextView textViewPBText=(TextView)findViewById(R.id.textViewPBText);
			TextView textViewPBTime=(TextView)findViewById(R.id.textViewPBTime);
			TextView textViewPBUser=(TextView)findViewById(R.id.textViewPBUser);
			textViewPBText.setText(firstJsonObject.optString("posttitle"));
			textViewPBTime.setText(firstJsonObject.optString("date").replace("T", " ").substring(0, 19).trim());
		//	textViewPBUser.setText(firstJsonObject.optJSONObject("user").optString("username"));
	          if(firstJsonObject.optString("anonymity").equals("1"))
	          {
		      		textViewPBUser.setText("匿名");

	          }else{
	      		textViewPBUser.setText(firstJsonObject.optJSONObject("user").optString("username"));

	          }
			  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		      for(int i=0;i<postbacksJsonArray.length();i++)
		      {
		    	  JSONObject postbackJsonObject=postbacksJsonArray.optJSONObject(i);
		          HashMap<String, Object> map = new HashMap<String, Object>();
		   //       map.put("textView_jobid",postbackJsonObject.optInt("id"));
		          map.put("textViewPBText",postbackJsonObject.optString("text").trim());
		          map.put("textViewPBTime", postbackJsonObject.optString("date").replace("T", " ").substring(0, 19).trim());
		          if(postbackJsonObject.optInt("anonymity")==1)
		          {
		        	  map.put("textViewPBUser", "匿名");
		          }else{
		        	  map.put("textViewPBUser", postbackJsonObject.optJSONObject("user").optString("username").trim());
		          }
		          listItem.add(map);
		      }
				// TODO Auto-generated method stub
			 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
			            R.layout.post_postback_listview,//ListItem的XML实现
			            //动态数组与ImageItem对应的子项        
			            new String[] {"textViewPBText","textViewPBTime", "textViewPBUser"}, 
			            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
			            new int[] {R.id.textViewPBText,R.id.textViewPBTime,R.id.textViewPBUser}
			        );
			 listViewPostBack.setAdapter(listItemAdapter);
		}
		


public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
	        setResult(RESULT_OK, intent);  
	        finish();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    







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

