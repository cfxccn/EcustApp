package com.usta.ecustapp.activity;

import java.util.ArrayList;

import com.usta.ecustapp.R;
import com.usta.ecustapp.adapter.FragmentTabAdapter;
import com.usta.ecustapp.fragment.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FragmentActivity extends ActionBarActivity {
	private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
	int index = 0;
	RadioButton radioButtonHome, radioButtonNearby, radioButtonSetting,
			radioButtonPost;
	Intent intent;
	RadioGroup rgs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initBottomView();

		rgs = (RadioGroup) findViewById(R.id.tabs_rg);
		Fragment nearbyFragment = NearbyFragment.getInstance();
		Fragment mainFragment = MainFragment.getInstance();
		Fragment postFragment = PostFragment.getInstance();
		Fragment settingFragment = SettingFragment.getInstance();

		fragmentsList.add(mainFragment);
		fragmentsList.add(nearbyFragment);
		fragmentsList.add(postFragment);
		fragmentsList.add(settingFragment);
		FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this,
				fragmentsList, R.id.fragment_content, rgs);
		tabAdapter
				.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
					@Override
					public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
							int checkedId, int index) {
						if (index == 0) {
							radioButtonHome.setTextColor(Color.BLUE);
							radioButtonNearby.setTextColor(Color.BLACK);
							radioButtonPost.setTextColor(Color.BLACK);
							radioButtonSetting.setTextColor(Color.BLACK);
							getSupportActionBar().setTitle("EcustApp");

						}
						if (index == 1) {
							radioButtonHome.setTextColor(Color.BLACK);
							radioButtonNearby.setTextColor(Color.BLUE);
							radioButtonPost.setTextColor(Color.BLACK);
							radioButtonSetting.setTextColor(Color.BLACK);
							getSupportActionBar().setTitle("附近");

						}
						if (index == 2) {
							radioButtonHome.setTextColor(Color.BLACK);
							radioButtonNearby.setTextColor(Color.BLACK);
							radioButtonPost.setTextColor(Color.BLUE);
							radioButtonSetting.setTextColor(Color.BLACK);
							getSupportActionBar().setTitle("校园广场");
						}
						if (index == 3) {
							radioButtonHome.setTextColor(Color.BLACK);
							radioButtonNearby.setTextColor(Color.BLACK);
							radioButtonPost.setTextColor(Color.BLACK);
							radioButtonSetting.setTextColor(Color.BLUE);
							getSupportActionBar().setTitle("设置");
						}

					}
				});
	}

	private void initBottomView() {
		radioButtonHome = (RadioButton) findViewById(R.id.tab_rb_a);
		radioButtonNearby = (RadioButton) findViewById(R.id.tab_rb_b);
		radioButtonPost = (RadioButton) findViewById(R.id.tab_rb_c);
		radioButtonSetting = (RadioButton) findViewById(R.id.tab_rb_d);
		radioButtonHome.setTextColor(Color.BLUE);
		radioButtonNearby.setTextColor(Color.BLACK);
		radioButtonPost.setTextColor(Color.BLACK);
		radioButtonSetting.setTextColor(Color.BLACK);

	}

	//
	// public class FragmentOnPageChangeListener implements OnPageChangeListener
	// {
	// @Override
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	// }
	//
	// public void onPageSelected(int arg0) {
	// index = arg0;
	// if (arg0 == 0) {
	// textViewNearby.setTextColor(Color.BLUE);
	// textViewHome.setTextColor(Color.BLACK);
	// textViewPost.setTextColor(Color.BLACK);
	// textViewSetting.setTextColor(Color.BLACK);
	// }
	// if (arg0 == 1) {
	// textViewNearby.setTextColor(Color.BLACK);
	// textViewHome.setTextColor(Color.BLUE);
	// textViewPost.setTextColor(Color.BLACK);
	// textViewSetting.setTextColor(Color.BLACK);
	// getSupportActionBar().setTitle("EcustApp");
	// }
	// if (arg0 == 2) {
	// textViewNearby.setTextColor(Color.BLACK);
	// textViewHome.setTextColor(Color.BLACK);
	// textViewPost.setTextColor(Color.BLUE);
	// textViewSetting.setTextColor(Color.BLACK);
	// getSupportActionBar().setTitle("校园广场");
	// }
	// if (arg0 == 3) {
	// textViewNearby.setTextColor(Color.BLACK);
	// textViewHome.setTextColor(Color.BLACK);
	// textViewPost.setTextColor(Color.BLACK);
	// textViewSetting.setTextColor(Color.BLUE);
	// getSupportActionBar().setTitle("设置");
	// }
	// }
	//
	// @Override
	// public void onPageScrollStateChanged(int arg0) {
	// // TODO Auto-generated method stub
	// }
	// }

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem searchIcon = menu.add("搜索");
		searchIcon.setIcon(R.drawable.search);
		searchIcon.setShowAsAction(2);
		searchIcon.setIntent(new Intent().setClass(this, Search.class));
		return true;
	}
}
