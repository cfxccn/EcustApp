package com.usta.ecustapp.activity;

import java.util.HashMap;



import com.usta.ecustapp.R;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class JobDetail extends ActionBarActivity {
	Intent intent;
	String JobTitle, JobRelease, qbcert, JobDetail, JobTreatment, JobTime,
			JobSite, JobReq, JobTag, JobVia;

	HashMap<String, Object> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobdetail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		map = (HashMap<String, Object>) intent.getSerializableExtra("map");
		initView();
	}

	private void initView() {
		TextView textView_JobTitle = (TextView) findViewById(R.id.textView_JobTitle);
		TextView textView_JobRelease = (TextView) findViewById(R.id.textView_JobRelease);
		TextView textView_JobDetail = (TextView) findViewById(R.id.textView_JobDetail);
		TextView textView_JobTreatment = (TextView) findViewById(R.id.textView_JobTreatment);
		TextView TextView_JobTime = (TextView) findViewById(R.id.TextView_JobTime);
		TextView textView_JobSite = (TextView) findViewById(R.id.textView_JobSite);
		TextView textView_JobReq = (TextView) findViewById(R.id.textView_JobReq);
		TextView textView_JobTag = (TextView) findViewById(R.id.textView_JobTag);
		TextView textView_JobVia = (TextView) findViewById(R.id.textView_JobVia);
		ImageView imageView_qbcert = (ImageView) findViewById(R.id.imageView_qbcert);
		JobTitle = map.get("jobTitle").toString();
		JobTime = map.get("jobTime").toString();
		JobDetail = map.get("jobDetail").toString();
		JobReq = map.get("jobRequire").toString();
		JobSite = map.get("jobSite").toString();
		JobTreatment = map.get("jobTreatement").toString();
		JobTag = map.get("jobTag").toString();
		JobRelease = map.get("releasetime").toString();
		qbcert = map.get("qinban_cert").toString();
		JobVia = map.get("via").toString();
		textView_JobTitle.setText(JobTitle);
		textView_JobRelease.setText(JobRelease);
		textView_JobDetail.setText(JobDetail);
		textView_JobTreatment.setText(JobTreatment);
		TextView_JobTime.setText(JobTime);
		textView_JobSite.setText(JobSite);
		textView_JobReq.setText(JobReq);
		textView_JobTag.setText(JobTag);
		textView_JobVia.setText(JobVia);
		if (qbcert.equalsIgnoreCase("是")) {
			imageView_qbcert.setImageDrawable(getResources().getDrawable(
					R.drawable.qb_cert));
		} else {
			imageView_qbcert.setImageDrawable(getResources().getDrawable(
					R.drawable.qb_nocert));
		}
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
		TextView textView5 = (TextView) findViewById(R.id.textView5);
		TextView textView6 = (TextView) findViewById(R.id.textView6);
		TextView textView7 = (TextView) findViewById(R.id.textView7);
		textView1.setText("工作内容：");
		textView2.setText("　　薪资：");
		textView3.setText("工作时间：");
		textView4.setText("工作地点：");
		textView5.setText("工作要求：");
		textView6.setText("　　备注：");
		textView7.setText("报名方式：");
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
