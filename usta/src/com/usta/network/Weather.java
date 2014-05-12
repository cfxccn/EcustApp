package com.usta.network;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Weather {

	public static JSONObject getWeatherDetails()
	{
		// String url="http://www.pm25.in/api/querys/only_aqi.json?city=shanghai&token=5j1znBVAsnSf5xQyNQyq&stations=no";
		 String url="http://59.78.93.208:9092/weather";
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
					JSONObject jsonObj = new JSONObject(out);
					return jsonObj;
			//		joke=(String)jsonObject.get("joke");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
			return null;

		}
		return null;

	}

}
