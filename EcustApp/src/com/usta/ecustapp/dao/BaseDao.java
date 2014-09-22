package com.usta.ecustapp.dao;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

public abstract class BaseDao {
	DbUtils db;
	public BaseDao(Context context){
		db= DbUtils.create(context);		
	}
}
