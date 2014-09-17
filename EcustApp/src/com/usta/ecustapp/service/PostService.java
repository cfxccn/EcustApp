package com.usta.ecustapp.service;

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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class PostService {
	public  JSONArray getPostsTitles(int lastindex)
	{
		String url="http://59.78.93.208:9092/PostsTitles?postid="+lastindex+"";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000 );
		HttpGet request;
		try {
			request = new HttpGet(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONArray jsonArray=new JSONArray(out);
					return jsonArray;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	public JSONArray getPostDetails(String postid)
	{
		String url="http://59.78.93.208:9092/PostDetails?postid="+postid+"";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000 );
		HttpPost request;
		try {
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONArray jsonArray=new JSONArray(out);
					return jsonArray;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public int newPost(String posttitle, String text, String useremail, String userkey,String anony){
	String url="http://59.78.93.208:9092/NewPost";
	HttpClient client = new DefaultHttpClient();
	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair ("posttitle", posttitle));  
		params.add(new BasicNameValuePair ("text", text)); 
		params.add(new BasicNameValuePair ("useremail", useremail)); 
		params.add(new BasicNameValuePair ("userkey", userkey)); 
		params.add(new BasicNameValuePair ("anony", anony)); 

		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String out = EntityUtils.toString(entity);
				int result=Integer.parseInt(out);
				return result;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -2;
	}

	public int newPostBack(String postid, String text, String useremail, String userkey,String anony){
	String url="http://59.78.93.208:9092/NewPostBack";
	HttpClient client = new DefaultHttpClient();
	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair ("postid", postid));  
		params.add(new BasicNameValuePair ("text", text)); 
		params.add(new BasicNameValuePair ("useremail", useremail)); 
		params.add(new BasicNameValuePair ("userkey", userkey)); 
		params.add(new BasicNameValuePair ("anony", anony)); 

		request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String out = EntityUtils.toString(entity);
				int result=Integer.parseInt(out);
				return result;
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -2;
	}
}
