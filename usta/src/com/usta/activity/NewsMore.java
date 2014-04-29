package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.control.ListViewOnScrollView;
import com.usta.network.Job;
import com.usta.network.News;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
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

public class NewsMore extends SherlockActivity {
	private int index;
	Intent intent;
	 
    ListView listViewNewsMoreTitles ;
	JSONArray  newsJsonArray;
	List<JSONObject>NewsInfos;
	private String newsid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsmore);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        getNewsMoreTitleDataFromNewThread();
    }

    private void getNewsMoreTitleDataFromNewThread() {
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	    	try {
    		    	 newsJsonArray=News.getNewsTitles();
//    		    	NewsInfos.add(newsJsonArray.getJSONObject(0));
//    		    	NewsInfos.add(newsJsonArray.getJSONObject(1));
//    		    	NewsInfos.add(newsJsonArray.getJSONObject(1));

    	    	if(newsJsonArray!=null){
    		    	newshandler.sendEmptyMessage(0);
    	    	}else{
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

    private Handler newshandler =new Handler(){
    		@Override
    		//当有消息发送出来的时候就执行Handler的这个方法
    		public void handleMessage(Message msg){
    		super.handleMessage(msg);

    		init_newslist();

    		}
    		};
    

	    protected void init_newslist() {
			// TODO Auto-generated method stub

	      listViewNewsMoreTitles = (ListView) findViewById(R.id.listViewNewsMoreTitles);
	  	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	      
	  	  for(int i=0;i<3;i++)
	      {
	  		  JSONObject eachnewstitleJsonObject=newsJsonArray.optJSONObject(i);
	          HashMap<String, Object> map = new HashMap<String, Object>();
	          map.put("textView_newsid",eachnewstitleJsonObject.optInt("id"));
	          map.put("textView_News_title",eachnewstitleJsonObject.optString("newstitle").trim());
	          map.put("textView_News_releasetime",eachnewstitleJsonObject.optString("newsrelease").trim());
	          map.put("textView_News_source",eachnewstitleJsonObject.optString("newssource").trim());
	          listItem.add(map);
	      }
			// TODO Auto-generated method stub
		 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
		            R.layout.newstitle_listview,//ListItem的XML实现
		            //动态数组与ImageItem对应的子项        
		            new String[] {"textView_newsid","textView_News_title", "textView_News_releasetime","textView_News_source"}, 
		            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
		            new int[] {R.id.textView_newsid,R.id.textView_News_title,R.id.textView_News_releasetime,R.id.textView_News_source}
		        );
		 
		 listViewNewsMoreTitles.setAdapter(listItemAdapter);
		 listViewNewsMoreTitles.setOnItemClickListener(new OnItemClickListener() {  
			  
	         @Override  
	         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                 long arg3) {  
	        	 ListViewOnScrollView listView = (ListViewOnScrollView)arg0;  
	                 HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);  
	                 newsid= String.valueOf(map.get("textView_newsid"));  
	 				Intent intent =new Intent();
	 				intent.putExtra("index", index);
	 				intent.putExtra("newsid", newsid);
					intent.setClass(NewsMore.this, NewsDetail.class);
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

