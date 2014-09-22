package com.usta.ecustapp.fragment;

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
import com.usta.ecustapp.activity.JobTitleView;
import com.usta.ecustapp.activity.Library;
import com.usta.ecustapp.activity.Map;
import com.usta.ecustapp.activity.NewsDetail;
import com.usta.ecustapp.activity.SchoolBus;
import com.usta.ecustapp.activity.SearchClassroom;
import com.usta.ecustapp.activity.SearchLecture;
import com.usta.ecustapp.activity.Powerfare;
import com.usta.ecustapp.dao.NewsDao;
import com.usta.ecustapp.model.NewsEntity;
import com.usta.ecustapp.util.ModelUtil;
import com.usta.ecustapp.util.ToastUtil;

import android.widget.SimpleAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class MainFragment extends Fragment {
	ListView listViewNews;
	View view;
	ArrayList<HashMap<String, Object>> listItemNews = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter listItemAdapterNews;
	ImageView imageView_Job;
	ImageView imageView_Classroom;
	ImageView imageView_Lecture;
	ImageView imageView_Map;
	ImageView imageView_Searchbook;
	ImageView imageView_Schoolbus;
	ImageView imageView_Powerfare;
	NewsDao newsDao;
	List<NewsEntity> newsEntities;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsDao = new NewsDao(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);
		initView(view);
		getNews();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initView(View view) {
		listViewNews = (ListView) view.findViewById(android.R.id.list);
		imageView_Job = (ImageView) view.findViewById(R.id.imageView_Job);
		imageView_Job.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), JobTitleView.class);
				startActivityForResult(intent, 0);
			}
		});
		imageView_Classroom = (ImageView) view
				.findViewById(R.id.imageView_Classroom);
		imageView_Classroom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// intent.putExtra("index", index);
				intent.setClass(getActivity(), SearchClassroom.class);
				startActivityForResult(intent, 0);
			}
		});
		imageView_Lecture = (ImageView) view
				.findViewById(R.id.imageView_Lecture);
		imageView_Lecture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchLecture.class);
				startActivityForResult(intent, 0);
			}
		});
		imageView_Map = (ImageView) view.findViewById(R.id.imageView_Map);
		imageView_Map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), Map.class);
				startActivityForResult(intent, 0);
			}
		});
		imageView_Searchbook = (ImageView) view
				.findViewById(R.id.imageView_Searchbook);
		imageView_Searchbook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showLibDialog();
			}
		});
		imageView_Schoolbus = (ImageView) view
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
		imageView_Powerfare = (ImageView) view
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
		String[] items = { "校图书馆馆藏图书查询", "校图书馆实时座位信息" };
		new AlertDialog.Builder(getActivity()).setTitle("请选择...")// 设置对话框标题
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Intent intent = new Intent();
							intent.putExtra("todo", which);
							intent.setClass(getActivity(), Library.class);
							startActivityForResult(intent, 0);
						} else if (which == 1) {
							Intent intent = new Intent();
							intent.putExtra("todo", which);
							intent.setClass(getActivity(), Library.class);
							startActivityForResult(intent, 0);
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	private void getNews() {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, "http://59.78.93.208:9092/Newstitles",
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							newsEntities = ModelUtil
									.toNewsEntities(new JSONArray(
											responseInfo.result));

						} catch (JSONException e1) {
						}
						try {
							newsDao.save(newsEntities);
							initNewsList();
						} catch (DbException e) {
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						try {
							ToastUtil.showToastShort(getActivity(),
									"新闻更新失败,请检查网络.");
							newsEntities = newsDao.findTop7();
							initNewsList();
						} catch (DbException e) {
						}
					}
				});

	}

	protected void initNewsList() {
		if (null == newsEntities) {
			return;
		}
		listItemNews.clear();
		for (NewsEntity newsEntity : newsEntities) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = ModelUtil.toHashMap(newsEntity);
			listItemNews.add(map);
		}
		listItemAdapterNews = new SimpleAdapter(
				getActivity(),
				listItemNews,// 数据源
				R.layout.newstitle_listview,// ListItem的XML实现
				new String[] { "id", "newsTitle", "newsRelease", "newsSource" },
				new int[] { R.id.textView_newsid, R.id.textView_News_title,
						R.id.textView_News_releasetime,
						R.id.textView_News_source });
		listViewNews.setAdapter(listItemAdapterNews);
		listViewNews.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listItemAdapterNews
						.getItem(arg2);
				Intent intent = new Intent();
				intent.putExtra("map", map);
				intent.setClass(getActivity(), NewsDetail.class);
				startActivity(intent);
			}
		});
	}
}
