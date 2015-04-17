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
import com.usta.ecustapp.activity.NearbyDetail;
import com.usta.ecustapp.dao.NearbyDao;
import com.usta.ecustapp.model.NearbyEntity;
import com.usta.ecustapp.util.ModelUtil;
import com.usta.ecustapp.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearbyFragment extends Fragment {
	public static NearbyFragment staticInstance = null;

	public static NearbyFragment getInstance() {
		if (staticInstance == null) {
			staticInstance = new NearbyFragment();
		}
		return staticInstance;
	}

	ExpandableListAdapter listViewAdapter;
	ExpandableListView listView;
	String[] groupData = { "吃", "玩", "住", "行", "其他" };
	List<NearbyEntity> eatList = new ArrayList<NearbyEntity>();
	List<NearbyEntity> playList = new ArrayList<NearbyEntity>();
	List<NearbyEntity> liveList = new ArrayList<NearbyEntity>();
	List<NearbyEntity> goList = new ArrayList<NearbyEntity>();
	List<NearbyEntity> otherList = new ArrayList<NearbyEntity>();
	List<List<NearbyEntity>> childrenData = new ArrayList<List<NearbyEntity>>();
	NearbyDao nearbyDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nearbyDao = new NearbyDao(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_nearby, container, false);
		initView(view);
		getNearby();
		return view;
	}

	void getNearby() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, "http://59.78.93.208:9092/NearbyTitles",
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							List<JSONArray> jsonArrays = ModelUtil
									.toNearbyJSONArray(new JSONArray(
											responseInfo.result));
							eatList = ModelUtil.toNearbyEntities(jsonArrays
									.get(0));
							playList = ModelUtil.toNearbyEntities(jsonArrays
									.get(1));
							liveList = ModelUtil.toNearbyEntities(jsonArrays
									.get(2));
							goList = ModelUtil.toNearbyEntities(jsonArrays
									.get(3));
							otherList = ModelUtil.toNearbyEntities(jsonArrays
									.get(4));
							childrenData.clear();
							childrenData.add(eatList);
							childrenData.add(playList);
							childrenData.add(liveList);
							childrenData.add(goList);
							childrenData.add(otherList);

						} catch (JSONException e1) {
						}
						try {
							nearbyDao.deleteAll();
							nearbyDao.saveAll(eatList);
							nearbyDao.saveAll(playList);
							nearbyDao.saveAll(liveList);
							nearbyDao.saveAll(goList);
							nearbyDao.saveAll(otherList);
							initListView();
						} catch (DbException e) {
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						try {
							ToastUtil.showToastShort(getActivity(),
									"新闻更新失败,请检查网络.");
							eatList = nearbyDao.findTop7("吃");
							playList = nearbyDao.findTop7("玩");
							liveList = nearbyDao.findTop7("住");
							goList = nearbyDao.findTop7("行");
							otherList = nearbyDao.findTop7("其他");
							childrenData.clear();
							childrenData.add(eatList);
							childrenData.add(playList);
							childrenData.add(liveList);
							childrenData.add(goList);
							childrenData.add(otherList);
							initListView();
						} catch (DbException e) {
						}
					}
				});

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	void initView(View view) {
		listView = (ExpandableListView) view
				.findViewById(R.id.expandableListViewNearby);
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
			//	ToastUtil.showToastLong(getActivity(),arg2+"--"+arg3);
				NearbyEntity nearbyEntity=childrenData.get(arg2).get(arg3);
				Intent intent = new Intent();
				intent.putExtra("map", nearbyEntity);
				intent.setClass(getActivity(), NearbyDetail.class);
				startActivityForResult(intent, 0);
				return false;
			}
		});
	}

	void initListView() {
		listViewAdapter = new BaseExpandableListAdapter() {
			@Override
			public boolean isChildSelectable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
//				NearbyEntity nearbyEntity=childrenData.get(arg0).get(arg1);
//				Intent intent = new Intent();
//				intent.putExtra("map", nearbyEntity);
//				intent.setClass(getActivity(), NearbyDetail.class);
//				startActivityForResult(intent, 0);
				
				return true;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public View getGroupView(int arg0, boolean arg1, View arg2,
					ViewGroup arg3) {
				@SuppressWarnings("static-access")
				LayoutInflater layoutInflater = (LayoutInflater) getActivity()
						.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
				LinearLayout linearLayout = (LinearLayout) layoutInflater
						.inflate(R.layout.nearby_title_listview, null);
				TextView textView_Title = (TextView) linearLayout
						.findViewById(R.id.textView_Nearby_Title);
//				ImageView imageView_Title = (ImageView) linearLayout
//						.findViewById(R.id.imageView1);
				//imageView_Title.setImageDrawable(getResources().getDrawable(R.drawable.n1+arg0));
			textView_Title.setText("     "+groupData[arg0]);
				return linearLayout;
			}

			@Override
			public long getGroupId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public int getGroupCount() {
				return groupData.length;
			}

			@Override
			public Object getGroup(int arg0) {
				return groupData[arg0];
			}

			@Override
			public int getChildrenCount(int arg0) {
				return childrenData.get(arg0).size();
			}

			@Override
			public View getChildView(int arg0, int arg1, boolean arg2,
					View arg3, ViewGroup arg4) {
				NearbyEntity nearbyEntity = childrenData.get(arg0).get(arg1);
				@SuppressWarnings("static-access")
				LayoutInflater layoutInflater = (LayoutInflater) getActivity()
						.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
				LinearLayout linearLayout = (LinearLayout) layoutInflater
						.inflate(R.layout.nearby_listview, null);
				TextView textView_NearbyTitle_lv = (TextView) linearLayout
						.findViewById(R.id.textView_NearbyTitle_lv);
				TextView textView_NearbyIntro_lv = (TextView) linearLayout
						.findViewById(R.id.textView_NearbyIntro_lv);
				TextView textView_NearbyLocation_lv = (TextView) linearLayout
						.findViewById(R.id.textView_NearbyLocation_lv);
				textView_NearbyTitle_lv.setText(nearbyEntity.getName());
				textView_NearbyIntro_lv.setText(nearbyEntity.getIntroduction());
				textView_NearbyLocation_lv.setText(nearbyEntity.getLocation());
				return linearLayout;
			}

			@Override
			public long getChildId(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return arg1;
			}

			@Override
			public Object getChild(int arg0, int arg1) {
				return childrenData.get(arg0).get(arg1);
			}

		};
		listView.setAdapter(listViewAdapter);
	}

}