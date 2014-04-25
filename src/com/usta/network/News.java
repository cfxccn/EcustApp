package com.usta.network;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class News {

	public static JSONArray getNewsTitles( )
	{
		String url="http://172.18.113.24:9092/Newstitles";
		HttpClient client = new DefaultHttpClient();
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
		}
		return null;
	}
	
	
	public static JSONObject getNewsDetails(int id)
	{
		String url="http://172.18.113.24:9092/Newsdetails?id="+id+"";
		HttpClient client = new DefaultHttpClient();
		HttpPost request;
		try {
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONArray jsonArray=new JSONArray(out);
					
					return jsonArray.optJSONObject(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return null;
	}
	
}
