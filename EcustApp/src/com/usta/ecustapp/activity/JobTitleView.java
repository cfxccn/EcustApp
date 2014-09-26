package com.usta.ecustapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.usta.ecustapp.R;
import com.usta.ecustapp.dao.JobDao;
import com.usta.ecustapp.model.JobEntity;
import com.usta.ecustapp.util.ModelUtil;
import com.usta.ecustapp.util.ToastUtil;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class JobTitleView extends ActionBarActivity {
	Intent intent;
	ListView listView;
	JobDao jobDao;
	List<JobEntity> jobEntities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		// index = intent.getIntExtra("index", 0);
		listView = (ListView) findViewById(R.id.listView_JobTitle);
		jobDao = new JobDao(this);
		getJob();
		// getJobsTitleDataViaNewThread();
		// initlistview();
	}

	//
	// private void getJobsTitleDataViaNewThread() {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	//
	// jobsTitilesJsonArray = jobService.getJobTitles();
	// // jobtitleinfo=new ArrayList<String>();
	// // sObject= GetNetData.getjobtitle();
	// // for(int i=0;i<48;i++)
	// // {
	// //
	// // jobtitleinfo.add(sObject.getProperty(i).toString().trim());
	// // }
	// if (jobsTitilesJsonArray != null) {
	// handler.sendEmptyMessage(0);
	// } else {
	// return;
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	//
	// }
	// }
	// }).start();
	//
	// }
	//
	// private Handler handler = new Handler() {
	// @Override
	// // 当有消息发送出来的时候就执行Handler的这个方法
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// initlistview();
	// }
	// };
	private void getJob() {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, "http://59.78.93.208:9092/jobstitles",
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							jobEntities = ModelUtil
									.toJobEntities(new JSONArray(
											responseInfo.result));
						} catch (JSONException e1) {
						}
						try {
							jobDao.cleanAndSaveAll(jobEntities);
							initListView();
						} catch (DbException e) {
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						try {
							ToastUtil.showToastShort(getApplicationContext(),
									"新闻更新失败,请检查网络.");
							jobEntities = jobDao.findTop8();
							initListView();
						} catch (DbException e) {
						}
					}
				});

	}

	private void initListView() {
		if(null==jobEntities){
			return;
		}
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (JobEntity jobEntity : jobEntities) {
			listItem.add(ModelUtil.toHashMap(jobEntity));
		}
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
				R.layout.jobtitle_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "jobTitle", "jobTreatement", "releasetime",
						"qinban_cert" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.textView_Job_title,
						R.id.textView_Job_treatment,
						R.id.textView_Job_releasetime, R.id.imageView_qb_cert });
		listView.setAdapter(listItemAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ListView listView = (ListView) arg0;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				Intent intent = new Intent();
				intent.putExtra("map", map);
				intent.setClass(JobTitleView.this, JobDetail.class);
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
		switch (item.getItemId()) {

		case android.R.id.home:
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
