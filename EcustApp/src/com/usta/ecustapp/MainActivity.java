package com.usta.ecustapp;

import java.util.ArrayList;

import com.usta.ecustapp.activity.PostTitleView;
import com.usta.ecustapp.adapter.MyFragmentPagerAdapter;
import com.usta.ecustapp.fragment.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
    private int currIndex = 0;  
	TextView textViewNearby, textViewHome, textViewSearch, textViewSetting,
			textViewPost;
	ImageView imageViewNearby, imageViewHome, imageViewSearch,
			imageViewSetting, imageViewPost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
	}

	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		LayoutInflater mInflater = getLayoutInflater();
		mInflater.inflate(R.layout.lay2_main, mPager);
		Fragment nearbyFragment = NearbyFragment.newInstance();
		Fragment mainFragment = MainFragment.newInstance();
		Fragment searchFragment = SearchFragment.newInstance();
		Fragment settingFragment = SettingFragment.newInstance();
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
				currIndex = 0;
				mPager.setCurrentItem(0);
				break;
			case R.id.tvlay2:
				;
			case R.id.imageView_Home:
				currIndex = 1;
				mPager.setCurrentItem(1);
				break;
			case R.id.tvlay3:
				;
			case R.id.imageView_Search:
				currIndex = 2;
				mPager.setCurrentItem(2);
				break;
			case R.id.tvlay4:
				;
			case R.id.imageView_Setting:
				currIndex = 3;
				mPager.setCurrentItem(3);
				break;
			case R.id.tvlay5:
				;
			case R.id.imageView_Post:
				Intent intent = new Intent();
				intent.putExtra("index", currIndex);
				intent.setClass(MainActivity.this, PostTitleView.class);
				startActivityForResult(intent, 0);
				;
				break;

			}

		}
	};


	public class FragmentOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
            currIndex = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:

		}

	}
}
