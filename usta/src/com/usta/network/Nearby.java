package com.usta.network;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Nearby {

	public static JSONArray getNearbyTitles(String _type, int id)
	{
		
		int type = 1; 
		if(_type.equalsIgnoreCase("吃")){type=1;}
		if(_type.equalsIgnoreCase("住")){type=2;}
		if(_type.equalsIgnoreCase("行")){type=3;}
		if(_type.equalsIgnoreCase("玩")){type=4;}
		if(_type.equalsIgnoreCase("其他")){type=5;}


		String url="http://172.18.113.24:9092/NeighbourhoodTitles?id="+id+"&type="+type+"";
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
	
	public static JSONObject getNearbyDetails( int id)
	{


		String url="http://172.18.113.24:9092/neighbourhooddetail?id="+id+"";
//		String url="http://172.18.113.24:8080/testssh/getEmptyRoom.action?wday=1&during=3-4";
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
					
					return jsonArray.optJSONObject(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
