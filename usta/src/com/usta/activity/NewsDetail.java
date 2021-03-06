package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.usta.R;
import com.usta.service.NewsService;

import android.view.MenuItem;

public class NewsDetail extends ActionBarActivity {
	private int index;
	Intent intent;
	String title;
	String content;
	List<String> jobtitleinfo;
	SoapObject sObject;
	String newsid;
	String NewTitle, NewsRelease, NewsDetail, NewsSource;
	JSONObject newsdetailsJsonObject;
	NewsService newsService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		newsid = intent.getStringExtra("newsid");
		index = intent.getIntExtra("index", 0);
		getNewsDetailsViaNewThread();
	}

	private void getNewsDetailsViaNewThread() {

		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					// SoapObject sObject= GetNetData.getnewsdetail(newsid);
					// NewTitle=sObject.getProperty(0).toString().trim();
					// NewsDetail=sObject.getProperty(1).toString().trim();
					// NewsRelease=sObject.getProperty(2).toString().trim();
					// NewsSource=sObject.getProperty(3).toString().trim();
					newsdetailsJsonObject = newsService.getNewsDetails(Integer
							.parseInt(newsid));
					if (newsdetailsJsonObject != null) {
						handler.sendEmptyMessage(0);
					} else {
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			NewTitle = newsdetailsJsonObject.optString("newstitle").trim();
			NewsDetail = newsdetailsJsonObject.optString("newsdetail").trim();
			NewsRelease = newsdetailsJsonObject.optString("newsrelease").trim();
			NewsSource = newsdetailsJsonObject.optString("newssource").trim();

			TextView textView_NewsTitle = (TextView) findViewById(R.id.textView_NewsTitle);
			TextView textView_NewsRelease = (TextView) findViewById(R.id.textView_NewsRelease);
			TextView textView_NewsDetail = (TextView) findViewById(R.id.textView_NewsDetail);
			TextView textView_NewsSource = (TextView) findViewById(R.id.textView_NewsSource);
			TextView textView4 = (TextView) findViewById(R.id.textView4);
			textView4.setText("来源：");
			textView_NewsTitle.setText(NewTitle);
			textView_NewsRelease.setText(NewsRelease);
			textView_NewsDetail.setText(NewsDetail);
			textView_NewsSource.setText(NewsSource);

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
		switch (item.getItemId()) {

		case android.R.id.home:
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
