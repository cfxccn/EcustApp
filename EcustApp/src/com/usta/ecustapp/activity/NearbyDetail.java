package com.usta.ecustapp.activity;

import java.util.HashMap;

import org.json.JSONObject;

import com.usta.ecustapp.*;
import com.usta.ecustapp.model.NearbyEntity;
import com.usta.ecustapp.service.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyDetail extends ActionBarActivity {
	private int index;
	Intent intent;
	NearbyEntity nearbyEntity;
	String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearbydetail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		nearbyEntity = (NearbyEntity) intent.getSerializableExtra("map");
		initView();
	}

	// private void getNearbyDetailViaNewThread() {
	//
	// // TODO Auto-generated method stub
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// jsonObject = nearbyService.getNearbyDetails(nearbytype,
	// Integer.parseInt(nearbyid));
	// if (jsonObject != null) {
	// handler.sendEmptyMessage(0);
	// } else {
	// return;
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }

	private void initView() {
		TextView textView_nearbyTitle = (TextView) findViewById(R.id.textView_nearbyTitle);
		TextView textView_NearbyIntro = (TextView) findViewById(R.id.textView_NearbyIntro);
		TextView TextView_NearbyLocation = (TextView) findViewById(R.id.TextView_NearbyLocation);
		TextView TextView_NearbyDetail = (TextView) findViewById(R.id.TextView_NearbyDetail);
		TextView TextView_NearbyPhone = (TextView) findViewById(R.id.TextView_NearbyPhone);
		TextView textView3 = (TextView) findViewById(R.id.textView3);
		TextView textView4 = (TextView) findViewById(R.id.textView4);
		TextView textView5 = (TextView) findViewById(R.id.textView5);
		textView3.setText("详情：");
		textView4.setText("地点：");
		textView5.setText("电话：");

		ImageView imageView_Nearbytype = (ImageView) findViewById(R.id.imageView_Nearbytype);

		textView_nearbyTitle.setText(nearbyEntity.getName());
		textView_NearbyIntro.setText(nearbyEntity.getIntroduction());
		TextView_NearbyLocation.setText(nearbyEntity.getLocation());
		TextView_NearbyDetail.setText(nearbyEntity.getDetail());
		TextView_NearbyPhone.setText(nearbyEntity.getPhone());

		type = nearbyEntity.getType().trim();

		if (type.equalsIgnoreCase("吃")) {
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(
					R.drawable.n1));
		} else if (type.equalsIgnoreCase("住")) {
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(
					R.drawable.n2));
		} else if (type.equalsIgnoreCase("行")) {
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(
					R.drawable.n3));
		} else if (type.equalsIgnoreCase("玩")) {
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(
					R.drawable.n4));
		} else if (type.equalsIgnoreCase("其他")) {
			imageView_Nearbytype.setImageDrawable(getResources().getDrawable(
					R.drawable.n5));
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
