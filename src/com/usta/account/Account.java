package com.usta.account;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class Account   {
	private static SharedPreferences userInfo;


public static String login(String userEmail,String userPwd){
	String url="http://172.18.113.24:9092/UserLogin";
	HttpClient client = new DefaultHttpClient();
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		   	
		params.add(new BasicNameValuePair ("userEmail", userEmail));  
		params.add(new BasicNameValuePair ("userPwd", userPwd)); 
		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String out = EntityUtils.toString(entity);
				//int result=Integer.parseInt(out);
				return out;
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		return "-2";
	}
	//return null;
	return "-2";

	}

	public static String register(String userName,String userEmail,String userPwd,String userMobile ,String userDepart,String userNum,String userRealName){
	String url="http://172.18.113.24:9092/UserRegister";
	HttpClient client = new DefaultHttpClient();
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		   	
		params.add(new BasicNameValuePair ("userName", userName));  
		params.add(new BasicNameValuePair ("userEmail", userEmail)); 
		params.add(new BasicNameValuePair ("userPwd", userPwd)); 
		params.add(new BasicNameValuePair ("userMobile", userMobile)); 
		params.add(new BasicNameValuePair ("userDepart", userDepart)); 
		params.add(new BasicNameValuePair ("userNum", userNum)); 
		params.add(new BasicNameValuePair ("userRealName", userRealName)); 

		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String out = EntityUtils.toString(entity);
			//	int result=Integer.parseInt(out);
				return out;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		return "-2";
	}
	//return null;
	return "-2";
		
	}
	

	public static String verify(String userEmail,String userKey){
	String url="http://172.18.113.24:9092/UserVerify";
	HttpClient client = new DefaultHttpClient();
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		   	
		params.add(new BasicNameValuePair ("userEmail", userEmail));  
		params.add(new BasicNameValuePair ("userKey", userKey)); 
		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String out = EntityUtils.toString(entity);
			//	int result=Integer.parseInt(out);
				return out;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		return "-2";
	}
	//return null;
	return "-2";
		
	}
	


}
