package com.usta.network;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Classroom {

	public static JSONArray getRoomData(String wday, String during)
	{
		String url="http://172.18.113.24:8080/testssh/getEmptyRoom.action?wday="+wday+"&during="+during;
//		String url="http://172.18.113.24:8080/testssh/getEmptyRoom.action?wday=1&during=3-4";
		HttpClient client = new DefaultHttpClient();
		
		HttpPost request;
		try {
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONObject jsonObject = new JSONObject(out);
					JSONArray roomsArray = (JSONArray)jsonObject.getJSONArray("room");
					return roomsArray;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
