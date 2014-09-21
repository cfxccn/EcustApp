package com.usta.ecustapp.activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.usta.ecustapp.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Search extends ActionBarActivity {
	private SearchView searchView;
	private Context context;
	private MyHandler handler;
	private ScheduledExecutorService scheduledExecutor = Executors
			.newScheduledThreadPool(10);
	private String currentSearchTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		context = getApplicationContext();
		handler = new MyHandler();
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM);
		setTitle(" ");
		LayoutInflater inflater = getLayoutInflater();
		View customActionBarView = inflater.inflate(R.layout.searchview, null);
		searchView = (SearchView) customActionBarView
				.findViewById(R.id.search_view);
		searchView.setIconified(false);
		searchView.setQueryHint("搜附近,新闻,讲座,兼职,广场...");
		EditText searchEditText = (EditText) searchView
				.findViewById(R.id.search_src_text);
		searchEditText.setTextColor(Color.WHITE);
		searchView.setOnCloseListener(new OnCloseListener() {
			@Override
			public boolean onClose() {
				// to avoid click x button and the edittext hidden
				return true;
			}
		});
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(context, "begin search", Toast.LENGTH_SHORT)
						.show();
				return true;
			}

			public boolean onQueryTextChange(String newText) {
				if (newText != null && newText.length() > 0) {
					currentSearchTip = newText;
					showSearchTip(newText);
				}
				return true;
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL
						| Gravity.RIGHT);
		actionBar.setCustomView(customActionBarView, params);
		// show keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	}

	public void showSearchTip(String newText) {
		// excute after 500ms, and when excute, judge current search tip and
		// newText
		schedule(new SearchTipThread(newText), 500);
	}

	class SearchTipThread implements Runnable {
		String newText;

		public SearchTipThread(String newText) {
			this.newText = newText;
		}

		public void run() {
			// keep only one thread to load current search tip, u can get data
			// from network here
			if (newText != null && newText.equals(currentSearchTip)) {
				handler.sendMessage(handler.obtainMessage(1, newText
						+ " search tip"));
			}
		}
	}

	public ScheduledFuture schedule(Runnable command, long delayTimeMills) {
		return scheduledExecutor.schedule(command, delayTimeMills,
				TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			onBackPressed();
			return true;
		}
		}
		return false;
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	}
}
