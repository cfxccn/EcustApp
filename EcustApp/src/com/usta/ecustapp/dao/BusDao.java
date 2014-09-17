package com.usta.ecustapp.dao;

import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.usta.ecustapp.model.*;



public class BusDao {
	DbUtils db;
	public void init(Context context) {
		db = DbUtils.create(context);
	}
	public List<BusEntity> getAll() throws DbException{
		List<BusEntity> busEntities=new LinkedList<BusEntity>();
		busEntities=db.findAll(BusEntity.class);
		return busEntities;
	}
	
	
	
	
	
	
	
}
