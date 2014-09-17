package com.usta.ecustapp.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.usta.ecustapp.R;
import com.usta.ecustapp.service.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class About extends ActionBarActivity {
	private int index;
	Intent intent;
	String buildStringFromServer;
	String buildInLocal;
	@ViewInject(R.id.btn_update)
	Button btn_update;
	@ViewInject(R.id.btn_checkforupdate)
	Button btn_checkforupdate;
	@ViewInject(R.id.textView_version)
	TextView textView_version;

	public ProgressDialog pBar;
	private Handler handler = new Handler();
	VersionService versionService = new VersionService();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ViewUtils.inject(this);
		intent = getIntent();
		index = intent.getIntExtra("index", 0);

		buildInLocal = "";
		try {
			buildInLocal = String.valueOf(getPackageManager().getPackageInfo(
					"com.usta", 0).versionCode);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildStringFromServer = buildInLocal;
		textView_version.setText("Build:" + buildInLocal);
	}

	@OnClick(R.id.btn_update)
	public void btn_updateClick(View arg0) {
		// startUpdateFromNewThread();
		pBar = new ProgressDialog(About.this);
		// pBar.cancel();
		pBar.setIndeterminate(false);
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍候...");
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pBar.setCancelable(false);
		downFileFromNewThread();
	}
	@OnClick(R.id.btn_checkforupdate)
	public void btn_checkforupdateClick(View arg0) {
		getLatestVersionFromNewThread();
	}
	void downFileFromNewThread() {
		pBar.show();
		new Thread() {
			public void run() {
				String url = "http://59.78.93.208:9092/update";
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
				client.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, 3000);
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"usta.apk");
						fileOutputStream = new FileOutputStream(file);
						pBar.setMax((int) length / 1000);
						pBar.setProgress(0);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							pBar.setProgress(count / 1000);
							count += ch;
							if (length > 0) {
							}
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					finishdownload_handler.sendEmptyMessage(0);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	protected void startUpdateFromNewThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final Uri uri = Uri.parse("http://59.78.93.208:9092/update");
				final Intent it = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(it);
			}
		}).start();
	}

	protected void getLatestVersionFromNewThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					buildStringFromServer = String.valueOf(versionService
							.getLatestVersion());
					if (Integer.parseInt(buildStringFromServer) > Integer
							.parseInt(buildInLocal)) {// 更新
						update_handler.sendEmptyMessage(0);
					} else if (Integer.parseInt(buildStringFromServer) == Integer
							.parseInt(buildInLocal)) {
						newest_handler.sendEmptyMessage(0);
					} else {
						noUpdate_handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}

	private Handler update_handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			initUpdateBtn();
		}
	};
	private Handler noUpdate_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast toast2 = Toast.makeText(About.this, "检查不到新版本",
					Toast.LENGTH_SHORT);
			toast2.show();
		}
	};
	private Handler newest_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast toast3 = Toast.makeText(About.this, "已经是最新版本",
					Toast.LENGTH_SHORT);
			toast3.show();
		}
	};
	private Handler finishdownload_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pBar.cancel();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(Environment
					.getExternalStorageDirectory(), "usta.apk")),
					"application/vnd.android.package-archive");
			startActivity(intent);
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

	protected void initUpdateBtn() {
		// TODO Auto-generated method stub
		Toast toast1 = Toast.makeText(About.this, "最新版本"
				+ buildStringFromServer, Toast.LENGTH_SHORT);
		toast1.show();
		LinearLayout layoutUpdate = (LinearLayout) findViewById(R.id.layoutUpdate);
		layoutUpdate.setVisibility(View.VISIBLE);

	}

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
