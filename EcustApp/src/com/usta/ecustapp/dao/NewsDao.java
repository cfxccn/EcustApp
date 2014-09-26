package com.usta.ecustapp.dao;

import java.util.List;

import android.content.Context;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.usta.ecustapp.model.NewsEntity;

public class NewsDao extends BaseDao {

	public NewsDao(Context context) {
		super(context);
	}

	public void cleanAndSaveAll(List<NewsEntity> newsEntities) throws DbException {
		db.deleteAll(NewsEntity.class);
		db.saveAll(newsEntities);
	}

	public List<NewsEntity> findTop7() throws DbException {
		List<NewsEntity> newsEntities = db.findAll(Selector
				.from(NewsEntity.class).orderBy("id").limit(7));
		return newsEntities;
	}

}
