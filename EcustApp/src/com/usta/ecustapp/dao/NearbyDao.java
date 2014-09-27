package com.usta.ecustapp.dao;

import java.util.List;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.usta.ecustapp.model.NearbyEntity;

public class NearbyDao extends BaseDao{
	public NearbyDao(Context context) {
		super(context);
	}

	public void saveAll(List<NearbyEntity> nearbyEntity) throws DbException {
		//db.deleteAll(NearbyEntity.class);
		db.saveAll(nearbyEntity);
	}
	public void deleteAll() throws DbException {
		db.deleteAll(NearbyEntity.class);
	}
	public List<NearbyEntity> findTop7(String type) throws DbException {
		List<NearbyEntity> nearbyEntity = db.findAll(Selector
				.from(NearbyEntity.class).orderBy("id").where("type","=",type).limit(7));
		return nearbyEntity;
	}
}
