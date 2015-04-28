package com.usta.ecustapp.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.usta.ecustapp.*;
import com.usta.ecustapp.service.*;
import com.usta.ecustapp.util.ToastUtil;

import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.view.MenuItem;

public class SchoolBus extends ActionBarActivity {
	BusService busService = new BusService();
	private int index;
	final private static String path1 = "http://59.78.93.208:8080/jsonProjject/servlet/action2?action_flag=schoolbus";
	Intent intent;
	private static String day, type, route;
	private static final String[] mDay = { "周一", "周二", "周三", "周四", "周五", "周六",
			"周日" };
	private static final String[] bustype = { "教职班车", "中旅班车", "梦境班车" };
	private static final String[] laifan = { "奉贤-徐汇", "奉贤-金山", "徐汇-奉贤",
			"徐汇-金山", "金山-徐汇", "金山-奉贤" };
	JSONArray busJsonArray;
	Spinner spinnerDay;
	Spinner spinnerType;
	Spinner spinnerRoute;
	ListView listViewBus;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schoolbus);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		index = intent.getIntExtra("index", 0);
		init_spinner();
		init_btn();

		listViewBus = (ListView) findViewById(R.id.listViewBus);

		ToastUtil.showToastShort(getApplicationContext(), "无合适班次");
	}

	private void init_btn() {
		// TODO Auto-generated method stub
		Button btnSearchBus = (Button) findViewById(R.id.btnSearchBus);
		btnSearchBus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				day = spinnerDay.getSelectedItem().toString();
				type = spinnerType.getSelectedItem().toString();
				route = spinnerRoute.getSelectedItem().toString();
				getBusDataViaNewThread();

			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			initListView();
		}
	};

	private Handler resultEmptyhandler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listViewBus.setAdapter(null);
			ToastUtil.showToastShort(getApplicationContext(), "无合适班次");

		}
	};

	private void initListView() {
		// jobsTitilesJsonArray
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < busJsonArray.length(); i++) {
			JSONObject busJsonObject = busJsonArray.optJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("textViewTime", busJsonObject.optString("time").trim());
			map.put("textView_PUPoint",
					"上车点:" + busJsonObject.optString("PUPoint").trim());
			map.put("textView_Destination",
					"下车点:" + busJsonObject.optString("Destination").trim());
			map.put("textViewPrice", busJsonObject.optString("price").trim());

			listItem.add(map);
		}
		// TODO Auto-generated method stub
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
				R.layout.bus_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "textViewTime", "textView_PUPoint",
						"textViewPrice", "textView_Destination" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.textViewTime, R.id.textView_PUPoint,
						R.id.textViewPrice, R.id.textView_Destination });
		listViewBus.setAdapter(listItemAdapter);
	}

	private void getBusDataViaNewThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					busJsonArray = busService.getBusInfo(day, type, route);

					if (busJsonArray != null) {
						if (busJsonArray.length() == 0) {
							resultEmptyhandler.sendEmptyMessage(0);
							return;
						}
						handler.sendEmptyMessage(0);
					} else {
						return;

					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return;

				}
			}
		}).start();

	}

	private void init_spinner() {
		// TODO Auto-generated method stub
		spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
		ArrayList<String> allday = new ArrayList<String>();
		for (int i = 0; i < mDay.length; i++) {
			allday.add(mDay[i]);
		}
		ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, allday);
		dayArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerDay.setAdapter(dayArrayAdapter);

		spinnerType = (Spinner) findViewById(R.id.spinnerType);
		ArrayList<String> alltype = new ArrayList<String>();
		for (int i = 0; i < bustype.length; i++) {
			alltype.add(bustype[i]);
		}
		ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, alltype);
		typeArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerType.setAdapter(typeArrayAdapter);

		spinnerRoute = (Spinner) findViewById(R.id.spinnerRoute);
		ArrayList<String> allroute = new ArrayList<String>();
		for (int i = 0; i < laifan.length; i++) {
			allroute.add(laifan[i]);
		}
		ArrayAdapter<String> routeArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, allroute);
		routeArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRoute.setAdapter(routeArrayAdapter);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			setResult(RESULT_OK, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}