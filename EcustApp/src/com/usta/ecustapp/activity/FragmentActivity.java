package com.usta.ecustapp.activity;

import java.util.ArrayList;



import com.usta.ecustapp.R;
import com.usta.ecustapp.adapter.MyFragmentPagerAdapter;
import com.usta.ecustapp.fragment.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentActivity extends ActionBarActivity {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	@SuppressWarnings("unused")
	private int index = 0;
	TextView textViewNearby, textViewHome, textViewSetting, textViewPost;
	ImageView imageViewNearby, imageViewHome, imageViewSetting, imageViewPost;
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
		imageViewPost = (ImageView) findViewById(R.id.imageView_Post);
		imageViewSetting = (ImageView) findViewById(R.id.imageView_Setting);
		textViewNearby = (TextView) findViewById(R.id.tvlay1);
		textViewHome = (TextView) findViewById(R.id.tvlay2);
		textViewPost = (TextView) findViewById(R.id.tvlay3);
		textViewSetting = (TextView) findViewById(R.id.tvlay4);

		imageViewNearby.setOnClickListener(bottomOnClickListener);
		imageViewHome.setOnClickListener(bottomOnClickListener);
		imageViewPost.setOnClickListener(bottomOnClickListener);
		imageViewSetting.setOnClickListener(bottomOnClickListener);

		textViewNearby.setOnClickListener(bottomOnClickListener);
		textViewHome.setOnClickListener(bottomOnClickListener);
		textViewPost.setOnClickListener(bottomOnClickListener);
		textViewSetting.setOnClickListener(bottomOnClickListener);

		textViewNearby.setTextColor(Color.BLACK);
		textViewHome.setTextColor(Color.BLUE);
		textViewPost.setTextColor(Color.BLACK);
		textViewSetting.setTextColor(Color.BLACK);

	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		LayoutInflater mInflater = getLayoutInflater();
		mInflater.inflate(R.layout.fragment_main, mPager);
		Fragment nearbyFragment = new NearbyFragment();
		Fragment mainFragment = new MainFragment();
		Fragment postFragment = new PostFragment();
		Fragment settingFragment = new SettingFragment();
		fragmentsList.add(nearbyFragment);
		fragmentsList.add(mainFragment);
		fragmentsList.add(postFragment);
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
			case R.id.imageView_Post:
				index = 2;
				mPager.setCurrentItem(2);
				break;
			case R.id.tvlay4:
				;
			case R.id.imageView_Setting:
				index = 3;
				mPager.setCurrentItem(3);
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
				textViewPost.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLACK);
				getSupportActionBar().setTitle("附近");
			}
			if (arg0 == 1) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLUE);
				textViewPost.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLACK);
				getSupportActionBar().setTitle("EcustApp");
			}
			if (arg0 == 2) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLACK);
				textViewPost.setTextColor(Color.BLUE);
				textViewSetting.setTextColor(Color.BLACK);
				getSupportActionBar().setTitle("校园广场");
			}
			if (arg0 == 3) {
				textViewNearby.setTextColor(Color.BLACK);
				textViewHome.setTextColor(Color.BLACK);
				textViewPost.setTextColor(Color.BLACK);
				textViewSetting.setTextColor(Color.BLUE);
				getSupportActionBar().setTitle("设置");
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
	}

    public boolean onCreateOptionsMenu(Menu menu) {	
    	 MenuItem searchIcon = menu.add("搜索"); 
    	 searchIcon.setIcon(R.drawable.search);
    	 searchIcon.setShowAsAction(2); 
		 searchIcon.setIntent(new Intent().setClass(this, Search.class));
		 return true;
  	}
}
