package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.network.Job;
import com.usta.network.Post;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PostTitleView extends SherlockActivity {
	private int index;
	Intent intent;
	private ListView listViewPost;
    List<String> posttitleinfo;
    JSONArray postsTitilesJsonArray;
    String postid;
    Button btnNewPost;
    String posttitle;
    String text;
    String useremail;
    String userkey;
    EditText editTextNewPost;
	 SharedPreferences userInfo;
	 Toast toast1;
	 Toast toast2;
	 Toast toast3;
	 Toast toast4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        listViewPost = (ListView) findViewById(R.id.listViewPost);
    	editTextNewPost=(EditText)findViewById(R.id.editTextNewPost);
        initLocalUserInfo();
        initbtn();
        getPostTitlesDataViaNewThread();
        toast1=Toast.makeText(this, "请先至 设置-账号管理 登录", Toast.LENGTH_SHORT);
        toast2=Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT);
        toast3=Toast.makeText(this, "请先至 设置-账号管理 重新登录", Toast.LENGTH_SHORT);
        toast4=Toast.makeText(this, "发送失败 请检查网络", Toast.LENGTH_SHORT);
    }
    private void initLocalUserInfo() {
		// TODO Auto-generated method stub
		userInfo = getSharedPreferences("setting", 0);  
		userkey=userInfo.getString("userkey", "null");
		useremail=userInfo.getString("useremail", "null");
	}

    private void getPostTitlesDataViaNewThread() {
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		postsTitilesJsonArray=Post.getPostsTitles();
	    		if(postsTitilesJsonArray!=null){
			    	 handler.sendEmptyMessage(0);
	    		}
	    		else{
	    			return;
	    		}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}).start();
   	
	}

    private void initbtn() {
		// TODO Auto-generated method stub
		Button btnNewPost=(Button)findViewById(R.id.btnNewPost);
		btnNewPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				posttitle=editTextNewPost.getText().toString();
				if(useremail.equals("null")){
					toast1.show();
				}else{
					newPostViaNewThread();
					}
			}
		});
	}

private Handler newPostSuccess=new Handler(){
	@Override
	public void handleMessage(Message msg){
	super.handleMessage(msg);
	toast2.show();
	editTextNewPost.setText("");
    getPostTitlesDataViaNewThread();


	}
	};
	private Handler newPostFailureNet=new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		toast4.show();

		}
		};
		private Handler newPostFailure=new Handler(){
			@Override
			//当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			toast3.show();
			}
			};
			
			
    private void newPostViaNewThread() {
    	// TODO Auto-generated method stub
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	    		int i=com.usta.network.Post.newPost(posttitle,"",useremail,userkey);
    				if(i==1){
    					newPostSuccess.sendEmptyMessage(0);
    					}
    				else if (i==-2){			
    					newPostFailureNet.sendEmptyMessage(0);
    				}
    				else if(i==-1){
    					newPostFailure.sendEmptyMessage(0);
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
 private void initlistview() {
	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
      for(int i=0;i<postsTitilesJsonArray.length();i++)
      {
    	  JSONObject postJsonObject=postsTitilesJsonArray.optJSONObject(i);
    	  JSONObject userJsonObject=postJsonObject.optJSONObject("user");
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("textViewPBid",postJsonObject.optInt("postid"));
          map.put("textViewPBText",postJsonObject.optString("posttitle").trim());
          map.put("textViewPBTime", postJsonObject.optString("date").replace("T", " ").substring(0, 19));
          map.put("textViewPBUser", userJsonObject.optString("username").trim());
          listItem.add(map);
      }
		// TODO Auto-generated method stub
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.post_postback_listview,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"textViewPBid","textViewPBText", "textViewPBTime","textViewPBUser"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.textViewPBid,R.id.textViewPBText,R.id.textViewPBTime,R.id.textViewPBUser}
	        );
	 listViewPost.setAdapter(listItemAdapter);
	 listViewPost.setOnItemClickListener(new OnItemClickListener() {  
		  
         @Override  
         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                 long arg3) {  
        	 ListView listView = (ListView)arg0;  
                 HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);  
                 postid= String.valueOf(map.get("textViewPBid"));
 				Intent intent =new Intent();
 				intent.putExtra("index", index);
 				intent.putExtra("postid", postid);
 				intent.setClass(PostTitleView.this, PostDetails.class);
 				startActivityForResult(intent, 0);

         }  
     }); 
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

