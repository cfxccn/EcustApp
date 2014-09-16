package com.usta.service;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

public class BusService {
	public  JSONArray getBusInfo(String day,String type,String route)
	{
		String url="http://59.78.93.208:9092/searchbus?day="+day+"&type="+type+"&route="+route+"";
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
}
