package com.usta.ecustapp.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "job")
public class JobEntity {
	@Id(column = "id")
	private int id;
	@Column(column = "jobtitle")
	private String jobTitle;
	@Column(column = "jobtime")
	private String jobTime;
	@Column(column = "jobdetail")
	private String jobDetail;
	@Column(column = "jobrequire")
	private String jobRequire;
	@Column(column = "jobsite")
	private String jobSite;
	@Column(column = "jobtreatment")
	private String jobTreatment;
	@Column(column = "jobtag")
	private String jobTag;
	@Column(column = "releasetime")
	private String releasetime;
	@Column(column = "qinban_cert")
	private String qinban_cert;
	@Column(column = "via")
	private String via;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobTime() {
		return jobTime;
	}
	public void setJobTime(String jobTime) {
		this.jobTime = jobTime;
	}
	public String getJobDetail() {
		return jobDetail;
	}
	public void setJobDetail(String jobDetail) {
		this.jobDetail = jobDetail;
	}
	public String getJobRequire() {
		return jobRequire;
	}
	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}
	public String getJobSite() {
		return jobSite;
	}
	public void setJobSite(String jobSite) {
		this.jobSite = jobSite;
	}
	public String getJobTreatment() {
		return jobTreatment;
	}
	public void setJobTreatment(String jobTreatment) {
		this.jobTreatment = jobTreatment;
	}
	public String getJobTag() {
		return jobTag;
	}
	public void setJobTag(String jobTag) {
		this.jobTag = jobTag;
	}
	public String getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}
	public String getQinban_cert() {
		return qinban_cert;
	}
	public void setQinban_cert(String qinban_cert) {
		this.qinban_cert = qinban_cert;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	

}
