package com.usta.ecustapp.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.usta.ecustapp.R;
import com.usta.ecustapp.model.JobEntity;
import com.usta.ecustapp.model.NewsEntity;

public class ModelUtil {
	public static NewsEntity toNewsEntity(JSONObject jsonObject) {
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setId(jsonObject.optInt("id"));
		newsEntity.setNewsTitle(jsonObject.optString("newstitle"));
		newsEntity.setNewsDetail(jsonObject.optString("newsdetail"));
		newsEntity.setNewsRelease(jsonObject.optString("newsrelease"));
		newsEntity.setNewsSource(jsonObject.optString("newssource"));
		return newsEntity;
	}

	public static List<NewsEntity> toNewsEntities(JSONArray jsonArray) {
		List<NewsEntity> newsEntities = new LinkedList<NewsEntity>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			NewsEntity newsEntity = ModelUtil.toNewsEntity(jsonObject);
			newsEntities.add(newsEntity);
		}
		return newsEntities;
	}

	public static JobEntity toJobEntity(JSONObject jsonObject) {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setId(jsonObject.optInt("id"));
		jobEntity.setJobTitle(jsonObject.optString("infotitile"));
		jobEntity.setJobTime(jsonObject.optString("jobtime"));
		jobEntity.setJobDetail(jsonObject.optString("jobdetail"));
		jobEntity.setJobRequire(jsonObject.optString("jobrequire"));
		jobEntity.setJobSite(jsonObject.optString("jobsite"));
		jobEntity.setJobTreatment(jsonObject.optString("jobtreatment"));
		jobEntity.setJobTag(jsonObject.optString("jobtag"));
		jobEntity.setReleasetime(jsonObject.optString("releasetime"));
		jobEntity.setQinban_cert(jsonObject.optString("qinban_cert"));
		jobEntity.setVia(jsonObject.optString("via"));
		return jobEntity;
	}

	public static List<JobEntity> toJobEntities(JSONArray jsonArray) {
		List<JobEntity> jobEntities = new LinkedList<JobEntity>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			JobEntity jobEntity = ModelUtil.toJobEntity(jsonObject);
			jobEntities.add(jobEntity);
		}
		return jobEntities;
	}

	public static HashMap<String, Object> toHashMap(NewsEntity newsEntity) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", newsEntity.getId());
		map.put("newsTitle", newsEntity.getNewsTitle().trim());
		map.put("newsRelease", newsEntity.getNewsRelease().trim());
		map.put("newsDetail", newsEntity.getNewsDetail().trim());
		map.put("newsSource", newsEntity.getNewsSource().trim());
		return map;
	}

	public static HashMap<String, Object> toHashMap(JobEntity jobEntity) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", jobEntity.getId());
		map.put("jobTitle", jobEntity.getJobTitle().trim());
		map.put("jobTime", jobEntity.getJobTime().trim());
		map.put("jobDetail", jobEntity.getJobDetail().trim());
		map.put("jobRequire", jobEntity.getJobRequire().trim());
		map.put("jobSite", jobEntity.getJobSite().trim());
		map.put("jobTreatement", jobEntity.getJobTreatment().trim());
		map.put("jobTag", jobEntity.getJobTag().trim());
		map.put("releasetime", jobEntity.getReleasetime().trim());
//		map.put("qinban_cert", jobEntity.getQinban_cert().trim());
		if (jobEntity.getQinban_cert().trim()
				.equalsIgnoreCase("是")) {
			map.put("qinban_cert", R.drawable.qb_cert);
		} else {
			map.put("qinban_cert", R.drawable.qb_nocert);
		}
		map.put("via", jobEntity.getVia().trim());
		
		
		
		return map;
	}
}
