package com.usta.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Advise {

	public static int sendAdvise(String sex,String grade,String advise,String w,String h,String android_version,String mobile_model,String density){
	String url="http://59.78.93.208:9092/AdviseInsert";
	HttpClient client = new DefaultHttpClient();
	client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
	client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
	HttpPost request;
	try {
		request = new HttpPost(url);
		 List<NameValuePair> params = new  ArrayList<NameValuePair>();  
		   	
		params.add(new BasicNameValuePair ("sex", sex));  
		params.add(new BasicNameValuePair ("grade", grade)); 
		params.add(new BasicNameValuePair ("text", advise)); 
		params.add(new BasicNameValuePair ("w", w)); 
		params.add(new BasicNameValuePair ("h", h)); 
		params.add(new BasicNameValuePair ("android_version", android_version)); 
		params.add(new BasicNameValuePair ("mobile_model", mobile_model)); 
		params.add(new BasicNameValuePair ("density", density)); 
		
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
	//return null;
	return -2;
		
	}

}
