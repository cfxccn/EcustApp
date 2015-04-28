package com.usta.ecustapp.activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.usta.ecustapp.*;

import android.view.MenuItem;

public class Classroom extends ActionBarActivity {
	private int todo;
	String urlString;
	Intent intent;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classroom);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		intent = getIntent();
		todo = intent.getIntExtra("todo", 0);
		urlString = "http://bbs.ecust.edu.cn/page/WechatXHLSlemUdWueZhdbstwUdhesXa/fxjs.html";
		if (todo == 1) {
			urlString = "http://bbs.ecust.edu.cn/page/WechatXHLSlemUdWueZhdbstwUdhesXa/xhjs.html";
		}
		initwebview();
	}

	private void initwebview() {
		webView = (WebView) findViewById(R.id.webView_lib);
		webView.loadUrl(urlString);
		webView.setInitialScale(100);
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				//view.loadUrl(url);
				return false;
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

//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuItem menuIcon = menu.add("返回");
//		menuIcon.setShowAsAction(2);
//		menuIcon.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			@Override
//			public boolean onMenuItemClick(MenuItem arg0) {
//				webView.loadUrl("http://202.120.96.75/sms/opac/search/showSearch.action?xc=6");
//				return false;
//			}
//		});
//		return true;
//	}

	@Override
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
