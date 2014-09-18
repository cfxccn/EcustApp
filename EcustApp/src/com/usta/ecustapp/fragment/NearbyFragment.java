package com.usta.ecustapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.usta.ecustapp.R;
import com.usta.ecustapp.service.NearbyService;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class NearbyFragment extends ListFragment {
	String _type;
	ArrayList<String> type = new ArrayList<String>();
	ArrayAdapter<String> typeAdapter;
	NearbyService nearbyService = new NearbyService();
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter listItemAdapter;
	String nearbyid;
	String nearbytype;
	ListView listViewNearby;
	JSONArray nearbys;
	Spinner spr_type_nearby;

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
		View view = inflater.inflate(R.layout.lay1_nearby, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(listItemAdapter);
		spr_type_nearby.setAdapter(typeAdapter);
		spr_type_nearby.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					break;
				case 1:
					_type = "吃";
					getNearbyDataViaNewThread();
					break;
				case 2:
					_type = "住";
					getNearbyDataViaNewThread();
					break;
				case 3:
					_type = "行";
					getNearbyDataViaNewThread();
					break;
				case 4:
					_type = "玩";
					getNearbyDataViaNewThread();
					break;
				case 5:
					_type = "其他";
					getNearbyDataViaNewThread();
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	
	}

	private void initView(View view) {
		listViewNearby = (ListView) view.findViewById(android.R.id.list);
		listItemAdapter = new SimpleAdapter(getActivity(),
				listItem,// 数据源
				R.layout.nearby_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "no", "title", "location", "introduction" },
				new int[] { R.id.textView_NearbyNo_lv,
						R.id.textView_NearbyTitle_lv,
						R.id.textView_NearbyLocation_lv,
						R.id.textView_NearbyIntro_lv });
		spr_type_nearby = (Spinner) view.findViewById(R.id.spr_type_nearby);

		typeAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, type);
		typeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	}

	private void getNearbyDataViaNewThread() {
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
			init_NearbyList();
		}
	};

	private void init_NearbyList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		JSONObject nearby = new JSONObject();
		try {
			for (int i = 0; i < nearbys.length(); i++) {
				nearby = nearbys.getJSONObject(i);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("no", 1 + i);
				map.put("title", nearby.getString("name"));
				map.put("location", nearby.getString("location"));
				map.put("introduction", nearby.getString("introduction"));
				map.put("id", nearby.getInt("id"));
				map.put("type", nearby.getString("type"));
				listItem.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

}
