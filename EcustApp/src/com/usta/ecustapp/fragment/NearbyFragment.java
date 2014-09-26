package com.usta.ecustapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.usta.ecustapp.R;
import com.usta.ecustapp.activity.NearbyDetail;
import com.usta.ecustapp.activity.NearbyViewInMap;
import com.usta.ecustapp.service.NearbyService;
import com.usta.ecustapp.util.ToastUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class NearbyFragment extends Fragment {
	public static NearbyFragment staticInstance = null;

	public static NearbyFragment getInstance() {
		if (staticInstance == null) {
			staticInstance = new NearbyFragment();
		}
		return staticInstance;
	}

	String _type;
	ArrayList<String> type = new ArrayList<String>();
	ArrayAdapter<String> typeAdapter;
	NearbyService nearbyService = new NearbyService();
	ArrayList<HashMap<String, Object>> listItemNearby = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter listItemAdapterNearby;
	String nearbyid;
	String nearbytype;
	ListView listViewNearby;
	JSONArray nearbys;
	Spinner spr_type_nearby;
	Button buttonViewInMap;
	TextView btnNearbyOk;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type.add("请选择分类");
		type.add("吃");
		type.add("住");
		type.add("行");
		type.add("玩");
		type.add("其他");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_nearby, container, false);
		initView(view);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		spr_type_nearby.setAdapter(typeAdapter);
		spr_type_nearby.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					break;
				case 1:
					_type = "吃";
					break;
				case 2:
					_type = "住";
					break;
				case 3:
					_type = "行";
					break;
				case 4:
					_type = "玩";
					break;
				case 5:
					_type = "其他";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void initView(View view) {

		buttonViewInMap = (Button) view.findViewById(R.id.btn_viewinmap);
		listViewNearby = (ListView) view.findViewById(R.id.listViewNearby);
		spr_type_nearby = (Spinner) view.findViewById(R.id.spr_type_nearby);
		btnNearbyOk = (TextView) view.findViewById(R.id.btn_nearbyok);
		btnNearbyOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getNearbyDataViaNewThread();
			}
		});
		// listViewNearby.setAdapter(listItemAdapter);
		typeAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, type);
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		buttonViewInMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (null != nearbys) {
					Intent intent = new Intent();
					intent.putExtra("nearbys", nearbys.toString());
					intent.setClass(getActivity(), NearbyViewInMap.class);
					startActivityForResult(intent, 0);
				} else {
					ToastUtil.showToastShort(getActivity(), "无信息");
				}
			}
		});

	}

	private void getNearbyDataViaNewThread() {
		ToastUtil.showToastShort(getActivity(), "正在加载附近信息...");
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					nearbys = nearbyService.getNearbyTitles(_type,
							Integer.MAX_VALUE);
					if (nearbys != null)
						nearby_handler.sendEmptyMessage(0);
					else
						return;
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		}).start();

	}

	@SuppressLint("HandlerLeak")
	private Handler nearby_handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			initNearsbyList();
		}
	};

	private void initNearsbyList() {
		buttonViewInMap.setEnabled(true);
		listItemNearby.clear();
		for (int i = 0; i < nearbys.length(); i++) {
			JSONObject nearby = nearbys.optJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("no", 1 + i);
			map.put("title", nearby.optString("name"));
			map.put("location", nearby.optString("location"));
			map.put("introduction", nearby.optString("introduction"));
			map.put("id", nearby.optInt("id"));
			map.put("type", nearby.optString("type"));
			listItemNearby.add(map);
		}
		listItemAdapterNearby = new SimpleAdapter(
				getActivity(),
				listItemNearby,// 数据源
				R.layout.nearby_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "no", "title", "location", "introduction" },
				new int[] { R.id.textView_NearbyNo_lv,
						R.id.textView_NearbyTitle_lv,
						R.id.textView_NearbyLocation_lv,
						R.id.textView_NearbyIntro_lv });
		listViewNearby.setAdapter(listItemAdapterNearby);
		listViewNearby.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listViewNearby
						.getItemAtPosition(arg2);
				nearbyid = map.get("id").toString();
				nearbytype = map.get("type").toString();
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.putExtra("nearbyid", nearbyid);
				intent.putExtra("nearbytype", nearbytype);
				intent.setClass(getActivity(), NearbyDetail.class);
				startActivityForResult(intent, 0);
			}
		});
	}
}