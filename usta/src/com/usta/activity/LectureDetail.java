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
import com.usta.network.Job;
import com.usta.network.Lecture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LectureDetail extends SherlockActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    String lectureid;
    String lecturetitle,lecturetime,lecturesite,reporter,depart,remark,campus;
    JSONObject lectureDetailsJsonObject;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturedetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        lectureid=intent.getStringExtra("lectureid");
        index=intent.getIntExtra("index", 0);
        getLectureDetailsFromNewThread();
    }


    
    private void getLectureDetailsFromNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {

	    	lectureDetailsJsonObject=Lecture.getLectureDetails(lectureid);
	    	if(lectureDetailsJsonObject!=null){
	    		handler.sendEmptyMessage(0);
	    		}
	    	else{
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

private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		TextView textView_LectureTitle=(TextView)findViewById(R.id.textView_LectureTitle);
		TextView textView_LectureTime=(TextView)findViewById(R.id.textView_LectureTime);
		TextView textView_Repoter=(TextView)findViewById(R.id.textView_Repoter);
		TextView textView_Campus=(TextView)findViewById(R.id.textView_Campus);
		TextView textView_Site=(TextView)findViewById(R.id.textView_Site);
		TextView textView_Remark=(TextView)findViewById(R.id.textView_Remark);
		TextView textView_Depart=(TextView)findViewById(R.id.textView_Depart);
		
		lecturetitle=lectureDetailsJsonObject.optString("lecturetitle"); 
		lecturetime=lectureDetailsJsonObject.optString("lecturetime"); 
		lecturesite=lectureDetailsJsonObject.optString("lecturesite"); 
		reporter=lectureDetailsJsonObject.optString("reporter"); 
		depart=lectureDetailsJsonObject.optString("depart"); 
		remark=lectureDetailsJsonObject.optString("remark"); 
		campus=lectureDetailsJsonObject.optString("campus"); 

		textView_LectureTitle.setText(lecturetitle);
		textView_LectureTime.setText(lecturetime);
		textView_Site.setText(lecturesite);
		textView_Repoter.setText(reporter);
		textView_Remark.setText(remark);
		textView_Depart.setText(depart);
		textView_Campus.setText(campus);

		
		
		TextView textView1=(TextView)findViewById(R.id.textView1);

		TextView textView5=(TextView)findViewById(R.id.textView5);
		TextView textView6=(TextView)findViewById(R.id.textView6);
		TextView textView7=(TextView)findViewById(R.id.textView7);
		TextView textView8=(TextView)findViewById(R.id.textView8);

		textView1.setText("报告人：");
		textView5.setText("　校区：");
		textView6.setText("　地点：");
		textView7.setText("　备注：");
		textView8.setText("　学院：");


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

