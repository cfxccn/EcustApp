package com.usta;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Joke extends SherlockActivity implements OnClickListener {
//	private String text = " 12";
//	int num;
//	jokeSqlitedata jsd;
	private int index;
	Intent intent;
	private String joke;

	private static  String url="http://172.18.113.24:8080/testssh/testJson.action";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	//	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joke);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);
		initbtn();
	}

	@Override
	public void onClick(View v) {
		getTestJsonServerDataFromNewThread(url);
//		jsd = new jokeSqlitedata(this);
//		SQLiteDatabase db = jsd.getWritableDatabase();
//		db.execSQL("insert into jokes (text) values ('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
//		db.execSQL("insert into jokes (text) values ('ŮA����²������ڶ��أ�ŮB���ҹ���û��һ�ٽ��ŮA�������˵����ŮB��Ҳ��һ�ٶ�ʮ�')");
//		db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");
//		db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");
//		Cursor cursor = db.rawQuery("select * from jokes", null);
//		if (cursor.moveToLast()) {
//			num = cursor.getInt(0);
//		}
//		int k = (int) (Math.random() * num);
//		if (cursor.moveToPosition(k)) {
//			text = cursor.getString(1);
//		}
//		cursor.close();
//		db.close();
//		switch (v.getId()) {
//			case R.id.button1: {
//				TextView tw1 = (TextView) findViewById(R.id.textView2);
//				tw1.setText(text);
//			}
//		}
	}

	private void getTestJsonServerDataFromNewThread(final String url)
	{
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	    	getTestJsonServerDataFrom(url);
    	    	 handler.sendEmptyMessage(0);
    	    }
    	}).start();
	}
    private Handler handler =new Handler(){
		@Override
		//������Ϣ���ͳ�����ʱ���ִ��Handler���������
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		((TextView) findViewById(R.id.textview_joke)).setText(joke);
		}
		};
	
	
	private void getTestJsonServerDataFrom(String url)
	{
		HttpClient client = new DefaultHttpClient();
		HttpGet request;
		try {
			request = new HttpGet(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONObject jsonObject = new JSONObject(out);
					joke=(String)jsonObject.get("joke");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        setResult(RESULT_OK, intent);  
	        finish();  
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initbtn() {
		Button btnback = (Button) findViewById(R.id.btnback_joke);
		btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// setContentView(R.layout.activity_main);
//				Intent intent = new Intent();
//				intent.setClass(Joke.this, MainActivity.class);
//				startActivity(intent);
//				Joke.this.finish();
		        setResult(RESULT_OK, intent);  
		        finish();  
			}
		});
		Button button = (Button) findViewById(R.id.btnjoke);
		button.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getTestJsonServerDataFromNewThread(url);

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
