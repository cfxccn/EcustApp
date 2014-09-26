package com.usta.ecustapp.dao;

import java.util.List;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.usta.ecustapp.model.JobEntity;

import android.content.Context;

public class JobDao extends BaseDao{
	public JobDao(Context context) {
		super(context);
	}
	public void cleanAndSaveAll(List<JobEntity> jobEntities) throws DbException {
		db.deleteAll(JobEntity.class);	
		db.saveAll(jobEntities);
	}
	public List<JobEntity> findTop8() throws DbException {
		List<JobEntity> jobEntities=db.findAll(Selector.from(JobEntity.class).orderBy("id",false).limit(8));
		return jobEntities;
	}	
}
