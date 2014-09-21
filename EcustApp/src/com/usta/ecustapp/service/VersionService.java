package com.usta.ecustapp.service;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class VersionService {
	public int getLatestVersion() {
		String url = "http://59.78.93.208:9092/version";
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
		HttpPost request;
		try {
			request = new HttpPost(new URI(url));
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					int build = Integer.parseInt(out);
					return build;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
}
