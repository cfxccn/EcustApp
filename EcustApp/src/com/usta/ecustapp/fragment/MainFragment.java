package com.usta.ecustapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.SimpleAdapter;

import com.usta.ecustapp.R;
import com.usta.ecustapp.activity.JobTitleView;
import com.usta.ecustapp.activity.Map;
import com.usta.ecustapp.activity.NewsDetail;
import com.usta.ecustapp.activity.SchoolBus;
import com.usta.ecustapp.activity.SearchBooks;
import com.usta.ecustapp.activity.SearchClassroom;
import com.usta.ecustapp.activity.SearchLecture;
import com.usta.ecustapp.activity.Powerfare;
import com.usta.ecustapp.service.NewsService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MainFragment extends ListFragment {
	Intent intent;
	Menu _menu;
	private String newsid = "";
	JSONArray newsJsonArray;
	List<JSONObject> NewsInfos;
	ListView listViewNews;
	NewsService newsService = new NewsService();
	ImageView imageView_Lecture;
	View view;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter listItemAdapter ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.lay2_main, container, false);
		initView(view);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(listItemAdapter);
		get_News();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map=(HashMap<String, Object>) listItemAdapter.getItem(position);
		newsid = String.valueOf(map.get("textView_newsid"));

		Intent intent = new Intent();
		// intent.putExtra("index", index);
		intent.putExtra("newsid", newsid);
		intent.setClass(getActivity(), NewsDetail.class);
		startActivity(intent);
	}

	private void initView(View view) {
		listItemAdapter = new SimpleAdapter(getActivity(),
				listItem,// 数据源
				R.layout.newstitle_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "textView_newsid", "textView_News_title",
						"textView_News_releasetime", "textView_News_source" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.textView_newsid, R.id.textView_News_title,
						R.id.textView_News_releasetime,
						R.id.textView_News_source });
		listViewNews = (ListView) view.findViewById(android.R.id.list);
		ImageView ImageView_Job = (ImageView) view
				.findViewById(R.id.imageView_Job);
		ImageView_Job.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), JobTitleView.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView ImageView_Classroom = (ImageView) view
				.findViewById(R.id.imageView_Classroom);
		ImageView_Classroom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), SearchClassroom.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView imageView_Lecture = (ImageView) view
				.findViewById(R.id.imageView_Lecture);
		imageView_Lecture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), SearchLecture.class);
				startActivityForResult(intent, 0);
			}
		});

		ImageView imageView_Map = (ImageView) view
				.findViewById(R.id.imageView_Map);
		imageView_Map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), Map.class);
				startActivityForResult(intent, 0);
			}
		});

		ImageView ImageView_Searchbook = (ImageView) view
				.findViewById(R.id.imageView_Searchbook);
		ImageView_Searchbook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showLibDialog();
			}
		});

		ImageView imageView_Schoolbus = (ImageView) view
				.findViewById(R.id.imageView_Schoolbus);
		imageView_Schoolbus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), SchoolBus.class);
				startActivityForResult(intent, 0);
			}
		});

		ImageView imageView_Powerfare = (ImageView) view
				.findViewById(R.id.imageView_Powerfare);
		imageView_Powerfare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), Powerfare.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	protected void showLibDialog() {
		final Context context = getActivity();
		// 定义列表选项
		String[] items = { "校图书馆馆藏图书查询", "校图书馆实时座位信息" };
		// 创建对话框
		new AlertDialog.Builder(context).setTitle("请选择...")// 设置对话框标题
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) {
							Intent intent = new Intent();
							// intent.putExtra("index", index);
							intent.putExtra("todo", which);
							intent.setClass(getActivity(), SearchBooks.class);
							startActivityForResult(intent, 0);
						} else if (which == 1) {
							Intent intent = new Intent();
							// intent.putExtra("index", index);
							intent.putExtra("todo", which);
							intent.setClass(getActivity(), SearchBooks.class);
							startActivityForResult(intent, 0);
						}

					}
				}).setNegativeButton("取消", null).show();
	}

	private void get_News() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					newsJsonArray = newsService.getNewsTitles();
					if (newsJsonArray != null) {
						newshandler.sendEmptyMessage(0);
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

	@SuppressLint("HandlerLeak")
	private Handler newshandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			init_newslist();
		}
	};

	protected void init_newslist() {

		for (int i = 0; i < newsJsonArray.length(); i++) {
			JSONObject eachnewstitleJsonObject = newsJsonArray.optJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("textView_newsid", eachnewstitleJsonObject.optInt("id"));
			map.put("textView_News_title",
					eachnewstitleJsonObject.optString("newstitle").trim());
			map.put("textView_News_releasetime", eachnewstitleJsonObject
					.optString("newsrelease").trim());
			map.put("textView_News_source",
					eachnewstitleJsonObject.optString("newssource").trim());
			listItem.add(map);
		}
	}
}
