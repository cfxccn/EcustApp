package com.usta.ecustapp.model;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "nearby")
public class NearbyEntity implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id(column = "id")
	int id;
	@Column(column = "name")
	String name;
	@Column(column = "type")
	String type;
	@Column(column = "introduction")
	String introduction;
	@Column(column = "detail")
	String detail;
	@Column(column = "location")
	String location;
	@Column(column = "longtitude")
	String longtitude;
	@Column(column = "latitude")
	String latitude;
	@Column(column = "phone")
	String phone;
	@Column(column = "campus")
	String campus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

}
