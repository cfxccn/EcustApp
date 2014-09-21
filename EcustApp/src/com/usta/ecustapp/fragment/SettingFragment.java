package com.usta.ecustapp.fragment;

import com.usta.ecustapp.R;
import com.usta.ecustapp.activity.About;
import com.usta.ecustapp.activity.AccountSetting;
import com.usta.ecustapp.activity.Advice;
import com.usta.ecustapp.util.ToastUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingFragment extends Fragment {
	private int index;
	Intent intent;
	SharedPreferences userInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {

		Button btn_toadvise_setting = (Button) view.findViewById(R.id.btn_toadvise_setting);
		btn_toadvise_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("index", index);
				intent.setClass(getActivity(), Advice.class);
				startActivityForResult(intent, 0);
			}
		});
		userInfo = getActivity().getSharedPreferences("setting", 0);
		String anony = userInfo.getString("anony", "null");
		if (anony.equalsIgnoreCase("on")) {
			RadioButton radiobtn_anonyon_setting = (RadioButton) view.findViewById(R.id.radiobtn_anonyon_setting);
			radiobtn_anonyon_setting.setChecked(true);
		}
		if (anony.equalsIgnoreCase("off")) {
			RadioButton radiobtn_anonyoff_setting = (RadioButton) view.findViewById(R.id.radiobtn_anonyoff_setting);
			radiobtn_anonyoff_setting.setChecked(true);
		}
		RadioGroup rgrp_anony = (RadioGroup) view.findViewById(R.id.rgrp_anony); // 绑定一个匿名监听器
		rgrp_anony.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID

				if (arg1 == R.id.radiobtn_anonyon_setting) {
					userInfo.edit().putString("anony", "on").commit();
					ToastUtil.showToastShort(getActivity(), "匿名-已保存");
				}
				if (arg1 == R.id.radiobtn_anonyoff_setting) {
					userInfo.edit().putString("anony", "off").commit();
					ToastUtil.showToastShort(getActivity(), "不匿名-已保存");

				}

			}
		});
		Button btnAbout = (Button) view.findViewById(R.id.btn_toabout_setting);
		btnAbout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("index", index);
				intent.setClass(getActivity(), About.class);
				startActivityForResult(intent, 0);
			}
		});
		Button btnAccount = (Button) view.findViewById(R.id.btn_toaccount);
		btnAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("index", index);
				intent.setClass(getActivity(), AccountSetting.class);
				startActivityForResult(intent, 0);
			}
		});
	}
}
