package com.usta.activity;

import org.json.JSONObject;



import com.usta.R;
import com.usta.service.LectureService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
public class LectureDetail extends ActionBarActivity {
	private int index;
	Intent intent;
	String title;
	String content;
    String lectureid;
    String lecturetitle,lecturetime,lecturesite,reporter,depart,remark,campus;
    JSONObject lectureDetailsJsonObject;
    LectureService lectureService=new LectureService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturedetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        lectureid=intent.getStringExtra("lectureid");
        index=intent.getIntExtra("index", 0);
        getLectureDetailsViaNewThread();
    }


    
    private void getLectureDetailsViaNewThread() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {

	    	lectureDetailsJsonObject=lectureService.getLectureDetails(lectureid);
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
		
		lecturetitle=lectureDetailsJsonObject.optString("lecturetitle").trim(); 
		lecturetime=lectureDetailsJsonObject.optString("lecturetime").replace("T", " "); 
		lecturesite=lectureDetailsJsonObject.optString("lecturesite").trim(); 
		reporter=lectureDetailsJsonObject.optString("reporter").trim(); 
		depart=lectureDetailsJsonObject.optString("depart").trim(); 
		remark=lectureDetailsJsonObject.optString("remark").trim(); 
		campus=lectureDetailsJsonObject.optString("campus").trim(); 

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

