package com.usta.ecustapp.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.usta.ecustapp.R;
import com.usta.ecustapp.activity.PostDetails;
import com.usta.ecustapp.service.PostService;
import com.usta.ecustapp.util.ToastUtil;

public class PostFragment extends Fragment {
	public static PostFragment staticInstance = null;

	public static PostFragment getInstance() {
		if (staticInstance == null) {
			staticInstance = new PostFragment();
		}
		return staticInstance;
	}

	private int index;
	Intent intent;
	private ListView listViewPost;
	List<String> posttitleinfo;
	JSONArray postsTitilesJsonArray;
	String postid;
	Button btnNewPost;
	String posttitle;
	String text;
	String useremail;
	String userkey;
	String anony;
	EditText editTextNewPost;
	SharedPreferences userInfo;

	Button btnLoadMoreButton;
	int lastindex;
	PostService postService = new PostService();
	ArrayList<HashMap<String, Object>> listItemPost = new ArrayList<HashMap<String, Object>>();;
	SimpleAdapter listItemAdapterPost;
	int lastPostid;
	TextView tvLoading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_post, container, false);
		initView(view);
		initLocalUserInfo();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		getPostTitlesDataViaNewThread();
	}

	private void initView(View view) {
		listViewPost = (ListView) view.findViewById(R.id.listViewPost);
		editTextNewPost = (EditText) view.findViewById(R.id.editTextNewPost);
		View footerView = ((LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footview,
				null, false);
		listViewPost.addFooterView(footerView);
		tvLoading = (TextView) view.findViewById(R.id.textViewLoading);
		btnLoadMoreButton = (Button) view.findViewById(R.id.btnLoadMore);
		btnLoadMoreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loadMorePostTitlesDataViaNewThread();
				btnLoadMoreButton.setEnabled(false);
			}
		});
		Button btnNewPost = (Button) view.findViewById(R.id.btnNewPost);
		btnNewPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				posttitle = editTextNewPost.getText().toString();
				if (useremail.equals("null")) {
					ToastUtil.showToastShort(getActivity(), "请先至 设置-账号管理 登录");
					// toast1.show();
				} else if (posttitle.equals("")) {
					ToastUtil.showToastShort(getActivity(), "请输入内容");
				} else {
					ToastUtil.showToastShort(getActivity(), "正在发送");
					newPostViaNewThread();
				}
			}
		});
	}

	private void newPostViaNewThread() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = postService.newPost(posttitle, "", useremail, userkey,
						anony);
				if (i == 1) {
					newPostSuccess.sendEmptyMessage(0);
				} else if (i == -2) {
					newPostFailureNet.sendEmptyMessage(0);
				} else if (i == -1) {
					newPostFailure.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	private void initLocalUserInfo() {
		// TODO Auto-generated method stub
		userInfo = getActivity().getSharedPreferences("setting", 0);
		userkey = userInfo.getString("userkey", "null");
		useremail = userInfo.getString("useremail", "null");
		anony = userInfo.getString("anony", "null");
		if (anony.equals("on")) {
			anony = "1";
		} else {
			anony = "0";
		}
	}

	private void loadMorePostTitlesDataViaNewThread() {
		ToastUtil.showToastShort(getActivity(), "正在加载...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					postsTitilesJsonArray = postService
							.getPostsTitles(lastPostid);
					if (postsTitilesJsonArray != null) {
						if (postsTitilesJsonArray.length() == 0) {
							noMore.sendEmptyMessage(0);
						}
						loadMore.sendEmptyMessage(0);
					} else {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private Handler loadMore = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int oldlastindex = lastindex;
			for (int i = 0; i < postsTitilesJsonArray.length(); i++) {
				lastindex++;
				JSONObject postJsonObject = postsTitilesJsonArray
						.optJSONObject(i);
				JSONObject userJsonObject = postJsonObject
						.optJSONObject("user");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("textViewPBid", postJsonObject.optInt("postid"));
				lastPostid = postJsonObject.optInt("postid");
				map.put("textViewPBText", postJsonObject.optString("posttitle")
						.trim());
				map.put("textViewPBTime", postJsonObject.optString("date")
						.replace("T", " ").substring(0, 19));
				if (postJsonObject.optInt("anonymity") == 1) {
					map.put("textViewPBUser", "匿名");
				} else {
					map.put("textViewPBUser",
							userJsonObject.optString("username").trim());
				}
				listItemPost.add(map);
			}
			btnLoadMoreButton.setEnabled(true);
			listViewPost.setAdapter(listItemAdapterPost);
			listViewPost.setSelection(oldlastindex - 1);
		}
	};
	private Handler newPostSuccess = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getActivity(), "发送成功");
			editTextNewPost.setText("");
			getPostTitlesDataViaNewThread();
		}
	};
	private Handler noMore = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getActivity(), "已全部加载完");
		}
	};
	private Handler newPostFailureNet = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getActivity(), "发送失败 请检查网络");
		}
	};
	private Handler newPostFailure = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getActivity(), "请先至 设置-账号管理 重新登录");
		}
	};

	private void getPostTitlesDataViaNewThread() {
		lastPostid = Integer.MAX_VALUE;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					postsTitilesJsonArray = postService
							.getPostsTitles(lastPostid);
					if (postsTitilesJsonArray != null) {
						handler.sendEmptyMessage(0);
					} else {
						handler1.sendEmptyMessage(0);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	private Handler handler = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			initListViewPost();
		}
	};
	private Handler handler1 = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ToastUtil.showToastShort(getActivity(), "请检查网络");
		}
	};

	private void initListViewPost() {
		tvLoading.setVisibility(View.GONE);
		listItemPost.clear();
		for (int i = 0; i < postsTitilesJsonArray.length(); i++) {
			lastindex++;
			JSONObject postJsonObject = postsTitilesJsonArray.optJSONObject(i);
			JSONObject userJsonObject = postJsonObject.optJSONObject("user");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("textViewPBid", postJsonObject.optInt("postid"));
			lastPostid = postJsonObject.optInt("postid");
			map.put("textViewPBText", postJsonObject.optString("posttitle")
					.trim());
			map.put("textViewPBTime",
					postJsonObject.optString("date").replace("T", " ")
							.substring(0, 19));
			// map.put("textViewPBUser",
			// userJsonObject.optString("username").trim());
			if (postJsonObject.optInt("anonymity") == 1) {
				map.put("textViewPBUser", "匿名");
			} else {
				map.put("textViewPBUser", userJsonObject.optString("username")
						.trim());
			}
			listItemPost.add(map);
		}
		// TODO Auto-generated method stub
		listItemAdapterPost = new SimpleAdapter(getActivity(), listItemPost,// 数据源
				R.layout.post_postback_listview,// ListItem的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "textViewPBid", "textViewPBText",
						"textViewPBTime", "textViewPBUser" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.textViewPBid, R.id.textViewPBText,
						R.id.textViewPBTime, R.id.textViewPBUser });
		listViewPost.setAdapter(listItemAdapterPost);
		listViewPost.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ListView listView = (ListView) arg0;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				postid = String.valueOf(map.get("textViewPBid"));
				Intent intent = new Intent();
				intent.putExtra("index", index);
				intent.putExtra("postid", postid);
				intent.setClass(getActivity(), PostDetails.class);
				startActivityForResult(intent, 0);
			}
		});
	}

}
