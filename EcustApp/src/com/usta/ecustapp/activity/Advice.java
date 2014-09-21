package com.usta.ecustapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.commonsware.cwac.richedit.RichEditText;
import com.usta.ecustapp.service.*;
import com.usta.ecustapp.util.ToastUtil;
import com.usta.ecustapp.*;

public class Advice extends ActionBarActivity {
	Intent intent;
	private ArrayAdapter<String> adapter;
	AdviceService adviceService = new AdviceService();

	private static final String[] grade = { "大一", "大二", "大三", "大四", "教职工", "其他" };

	String _sex = "";
	String advise = "";
	String _grade;
	EditText etext_advise;

	String screenWidth;
	String screenHeight;
	String densityDPI;
	String version;
	String model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advice);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		initbtn();
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
		Spinner spn_grade = (Spinner) findViewById(R.id.spn_grade);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, grade);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_grade.setAdapter(adapter);
		// spn_grade.setVisibility(View.VISIBLE);
		spn_grade.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				_grade = grade[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		RadioGroup group = (RadioGroup) this.findViewById(R.id.rgrp_sex);
		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID
				if (arg1 == R.id.rbtn_sex_male) {
					_sex = "男";
				}
				if (arg1 == R.id.rbtn_sex_female) {
					_sex = "女";
				}
			}
		});
		etext_advise = (RichEditText) findViewById(R.id.etext_advise);

	}

	private void sendAdvice() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// dm = getResources().getDisplayMetrics();
		screenWidth = Integer.toString(dm.widthPixels);
		screenHeight = Integer.toString(dm.heightPixels);
		densityDPI = Integer.toString(dm.densityDpi); // 屏幕密度（每寸像素：120/160/240/320）
		version = android.os.Build.VERSION.RELEASE;
		model = android.os.Build.MODEL;

		advise = etext_advise.getText().toString().trim();
		if ("".equals(advise) || _sex == "") {
			ToastUtil.showToastShort(this, "请输入内容并选择性别");
			return;
		}
		sendAdviseViaNewThread();
	}

	private Handler adviseSuccess = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getApplicationContext(), "发送成功");
			setResult(RESULT_OK, intent);
			finish();
		}
	};
	private Handler adviseFailure = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getApplicationContext(), "反馈失败，请检查联网");
		}
	};

	private void sendAdviseViaNewThread() {
		// TODO Auto-generated method stub
		ToastUtil.showToastShort(this, "正在发送");
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (adviceService.sendAdvise(_sex, _grade, advise, screenWidth,
						screenHeight, version, model, densityDPI) == 1) {
					adviseSuccess.sendEmptyMessage(0);
				} else {
					adviseFailure.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem sendIcon = menu.add("提交");
		sendIcon.setShowAsAction(2);
		sendIcon.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				sendAdvice();
				return false;
			}
		});
		return true;
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
