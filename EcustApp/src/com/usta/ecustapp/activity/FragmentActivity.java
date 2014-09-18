package com.usta.ecustapp.activity;

import java.util.ArrayList;

import com.usta.ecustapp.R;
import com.usta.ecustapp.activity.PostTitleView;
import com.usta.ecustapp.adapter.MyFragmentPagerAdapter;
import com.usta.ecustapp.fragment.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentActivity extends ActionBarActivity {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private int index = 0;
	TextView textViewNearby, textViewHome, textViewSearch, textViewSetting,
			textViewPost;
	ImageView imageViewNearby, imageViewHome, imageViewSearch,
			imageViewSetting, imageViewPost;
	ConnectivityManager manger;
	Intent intent;
	ListView listView_news;
	ListView listView_nearby;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitViewPager();
		InitBottomView();
	}

	private void InitBottomView() {
		imageViewNearby = (ImageView) findViewById(R.id.imageView_Nearby);
		imageViewHome = (ImageView) findViewById(R.id.imageView_Home);
		imageViewSearch = (ImageView) findViewById(R.id.imageView_Search);
		imageViewSetting = (ImageView) findViewById(R.id.imageView_Setting);
		imageViewPost = (ImageView) findViewById(R.id.imageView_Post);
		textViewNearby = (TextView) findViewById(R.id.tvlay1);
		textViewHome = (TextView) findViewById(R.id.tvlay2);
		textViewSearch = (TextView) findViewById(R.id.tvlay3);
		textViewSetting = (TextView) findViewById(R.id.tvlay4);
		textViewPost = (TextView) findViewById(R.id.tvlay5);

		imageViewNearby.setOnClickListener(bottomOnClickListener);
		imageViewHome.setOnClickListener(bottomOnClickListener);
		imageViewSearch.setOnClickListener(bottomOnClickListener);
		imageViewSetting.setOnClickListener(bottomOnClickListener);
		imageViewPost.setOnClickListener(bottomOnClickListener);
		textViewNearby.setOnClickListener(bottomOnClickListener);
		textViewHome.setOnClickListener(bottomOnClickListener);
		textViewSearch.setOnClickListener(bottomOnClickListener);
		textViewSetting.setOnClickListener(bottomOnClickListener);
		textViewPost.setOnClickListener(bottomOnClickListener);
		
		textViewNearby.setTextColor(Color.BLACK);
		textViewHome.setTextColor(Color.BLUE);
		textViewSearch.setTextColor(Color.BLACK);
		textViewSetting.setTextColor(Color.BLACK);
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		LayoutInflater mInflater = getLayoutInflater();
		mInflater.inflate(R.layout.lay2_main, mPager);
		Fragment nearbyFragment =new NearbyFragment();
		Fragment mainFragment =new MainFragment();
		Fragment searchFragment =new SearchFragment();
		Fragment settingFragment =new SettingFragment();
		fragmentsList.add(nearbyFragment);
		fragmentsList.add(mainFragment);
		fragmentsList.add(searchFragment);
		fragmentsList.add(settingFragment);
		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(1);
		mPager.setOnPageChangeListener(new FragmentOnPageChangeListener());

	}

	private OnClickListener bottomOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.tvlay1:
				;
			case R.id.imageView_Nearby:
				index = 0;
				mPager.setCurrentItem(0);
				break;
			case R.id.tvlay2:
				;
			case R.id.imageView_Home:
				index = 1;
				mPager.setCurrentItem(1);
				break;
			case R.id.tvlay3:
				;
			case R.id.imageView_Search:
				index = 2;
				mPager.setCurrentItem(2);
				break;
			case R.id.tvlay4:
				;
			case R.id.imageView_Setting:
				index = 3;
				mPager.setCurrentItem(3);
				break;
			case R.id.tvlay5:
				;
			case R.id.imageView_Post:
				Intent intent = new Intent();
				intent.putExtra("index", index);
				intent.setClass(FragmentActivity.this, PostTitleView.class);
				startActivityForResult(intent, 0);
				;
				break;
			}
		}
	};

	public class FragmentOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		public void onPageSelected(int arg0) {
			index = arg0;
			if (arg0 == 0) {
				textViewNearby.setTextColor(Color.BLUE);
				textViewHome.setTextColor(Color.BLACK);
				textViewSearch.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLACK);
			}
			if (arg0 == 1) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLUE);
				textViewSearch.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLACK);
			}
			if (arg0 == 2) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLACK);
				textViewSearch.setTextColor(Color.BLUE);
				textViewSetting.setTextColor(Color.BLACK);
			}
			if (arg0 == 3) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLACK);
				textViewSearch.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLUE);
			}
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
	
	}

}
