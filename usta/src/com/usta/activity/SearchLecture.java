package com.usta.activity;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.usta.service.LectureService;
import com.usta.control.GridViewOnScrollView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.usta.R;

public class SearchLecture extends ActionBarActivity {
	
	LectureService lectureService=new LectureService();
	private int index;
	Intent intent;
	
	String lectureid;
	
	private List<String> lecturelist = new ArrayList<String>();
	
	private ArrayAdapter adapter;
	private GridViewOnScrollView gridView;
	private Context context = this;
	JSONArray lectureArray;
	ListView listViewNewsMoreTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlecture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        listViewNewsMoreTitles = (ListView) findViewById(R.id.listViewLecture);
        getLectureDataViaNewThread();
    }

    private void getLectureDataViaNewThread()
    {
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    				
						try {
							
							lectureArray = lectureService.getLectureList();
						
							if(lectureArray!=null){
						    	 handler.sendEmptyMessage(0);
							}
							else{
								return;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

    	    }
    	    
    	}).start();
    }
    private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			initlistview();
		}
	};
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
    	   	setResult(RESULT_OK, intent);  
       		finish();  
       		return true;  
		}  
		return super.onKeyDown(keyCode, event);  
	}

    protected void initlistview() {
		// TODO Auto-generated method stub
    	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
          for(int i=0;i<lectureArray.length();i++)
          {
        	  JSONObject lectureJsonObject=lectureArray.optJSONObject(i);
              HashMap<String, Object> map = new HashMap<String, Object>();
              map.put("textView_lectureid",lectureJsonObject.optInt("id"));
              map.put("textView_lecturetitle",lectureJsonObject.optString("lecturetitle").trim());
              map.put("textView_report", lectureJsonObject.optString("reporter").trim());
              map.put("textView_lecturetime", lectureJsonObject.optString("lecturetime").replace("T", " ").substring(0, 19));
            
              listItem.add(map);
          }
    		// TODO Auto-generated method stub
    	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
    	            R.layout.leturetitle_listview,//ListItem的XML实现
    	            //动态数组与ImageItem对应的子项        
    	            new String[] {"textView_lectureid","textView_lecturetitle", "textView_report","textView_lecturetime"}, 
    	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
    	            new int[] {R.id.textView_lectureid,R.id.textView_lecturetitle,R.id.textView_report,R.id.textView_lecturetime}
    	        );
    	 listViewNewsMoreTitles.setAdapter(listItemAdapter);
    	 listViewNewsMoreTitles.setOnItemClickListener(new OnItemClickListener() {  
    		  
             @Override  
             public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                     long arg3) {  
            	 ListView listView = (ListView)arg0;  
                     HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);  
                     lectureid= String.valueOf(map.get("textView_lectureid"));
     				Intent intent =new Intent();
     				intent.putExtra("index", index);
     				intent.putExtra("lectureid", lectureid);
     				intent.setClass(SearchLecture.this, LectureDetail.class);
     				startActivityForResult(intent, 0);

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

