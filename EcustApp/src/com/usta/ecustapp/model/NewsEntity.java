package com.usta.ecustapp.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "news")
public class NewsEntity {
	@Id(column = "id")
	private int id;
	@Column(column = "newstitle")
	private String newsTitle;
	@Column(column = "newsdetail")
	private String newsDetail;
	@Column(column = "newsrelease")
	private String newsRelease;
	@Column(column = "newssource")
	private String newsSource;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsDetail() {
		return newsDetail;
	}
	public void setNewsDetail(String newsDetail) {
		this.newsDetail = newsDetail;
	}
	public String getNewsRelease() {
		return newsRelease;
	}
	public void setNewsRelease(String newsRelease) {
		this.newsRelease = newsRelease;
	}
	public String getNewsSource() {
		return newsSource;
	}
	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}



}
