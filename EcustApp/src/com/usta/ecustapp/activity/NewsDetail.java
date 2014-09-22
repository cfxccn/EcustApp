package com.usta.ecustapp.activity;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.view.MenuItem;

import com.usta.ecustapp.*;
import com.usta.ecustapp.service.*;

public class NewsDetail extends ActionBarActivity {
	Intent intent;
	String title;
	String content;
	List<String> jobtitleinfo;
	SoapObject sObject;
	HashMap<String, Object> map;
	String newsTitle, newsRelease, newsDetail, newsSource;
	JSONObject newsdetailsJsonObject;
	NewsService newsService = new NewsService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		map = (HashMap<String, Object>) intent.getSerializableExtra("map");
		initView();
	}
	private void initView() {
		newsTitle = map.get("newsTitle").toString().trim();
		newsDetail =map.get("newsDetail").toString().trim();
		newsRelease =map.get("newsRelease").toString().trim();
		newsSource =map.get("newsSource").toString().trim();
		TextView textView_NewsTitle = (TextView) findViewById(R.id.textView_NewsTitle);
		TextView textView_NewsRelease = (TextView) findViewById(R.id.textView_NewsRelease);
		TextView textView_NewsDetail = (TextView) findViewById(R.id.textView_NewsDetail);
		TextView textView_NewsSource = (TextView) findViewById(R.id.textView_NewsSource);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
		textView4.setText("来源：");
		textView_NewsTitle.setText(newsTitle);
		textView_NewsRelease.setText(newsRelease);
		textView_NewsDetail.setText(newsDetail);
		textView_NewsSource.setText(newsSource);

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
		switch (item.getItemId()) {

		case android.R.id.home:
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
