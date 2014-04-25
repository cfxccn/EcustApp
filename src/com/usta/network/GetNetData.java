package com.usta.network;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.R.string;

public class GetNetData {    
	
	public static SoapObject _getweatherdata(String theCityCode) {
		String namespace="http://WebXml.com.cn/";
	    String serviceUrl = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";  
		String methodname ="getWeather"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("theCityCode",theCityCode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
        	//System.out.println(soapaction);
            // 第5步：调用WebService  
            ht.call(soapaction, envelope);  
        //	System.out.println("ht.call" ); 
            if (envelope.getResponse() != null)  
            {  
                // 第6步：使用getResponse方法获得WebService方法的返回结果  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
//            	SoapObject result = (SoapObject) envelope.bodyIn;  
//            	SoapObject detail = (SoapObject) result.getProperty(0);                  	
            	return soapObject;
            }  
            else {  
        		return null;  

            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        	//tvweather=e.toString();
        }  
		return null;  
	}

	public static JSONArray _getLectureData(String start, String department) throws Exception
	{
		department= URLEncoder.encode(department, "UTF-8");
		department= URLEncoder.encode(department, "UTF-8");
		String url="http://172.18.113.24:8080/testssh/getLecture.action?start="+start+"&department="+department;
//		String url="http://172.18.113.24:8080/testssh/getLecture.action?start=5&department=xinxi";
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
					JSONArray roomsArray = (JSONArray)jsonObject.getJSONArray("lecture");
					return roomsArray;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject _getjokedata()
	{
		 String url="http://172.18.113.24:8080/testssh/testJson.action";

		HttpClient client = new DefaultHttpClient();
		HttpGet request;
		try {
			request = new HttpGet(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONObject jsonObject = new JSONObject(out);
					return jsonObject;
			//		joke=(String)jsonObject.get("joke");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public static JSONArray _getairaqidata()
	{
		// String url="http://www.pm25.in/api/querys/only_aqi.json?city=shanghai&token=5j1znBVAsnSf5xQyNQyq&stations=no";
		 String url="http://www.pm25.in/api/querys/pm2_5.json?city=shanghai&token=jp1p2yuzcb4FRTwpotEb&stations=no";
		HttpClient client = new DefaultHttpClient();
		HttpGet request;
		try {
			request = new HttpGet(new URI(url));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String out = EntityUtils.toString(entity);
					JSONArray jsonArr = new JSONArray(out);
					return jsonArr;
			//		joke=(String)jsonObject.get("joke");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}

//	public static void _sendadvise_xml(String sex,String grade,String advise,String w,String h,String android_version,String mobile_model,String density){
//		String namespace="http://tempuri.org/";
//	    String serviceUrl = "http://172.18.113.24:9090/service1.asmx";
//		String methodname ="insertAdvise"; 
//		String soapaction=namespace+methodname;
//		SoapObject request = new SoapObject(namespace, methodname);
//        request.addProperty("sex",sex);
//        request.addProperty("grade",grade);
//        request.addProperty("text",advise);
//        request.addProperty("w",w);
//        request.addProperty("h",h);
//        request.addProperty("android_version",android_version);
//        request.addProperty("mobile_model",mobile_model);
//        request.addProperty("density",density);
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.dotNet=true;//是否是dotNet WebService  
//        envelope.bodyOut=request;
//        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
//        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
//        ht.debug = true;
//        try  
//        {   
//            ht.call(soapaction, envelope);  
//        }  
//        catch (Exception e)  
//        {  
//			e.printStackTrace();
//        }  
//	}
//
	public static SoapObject getjoke_xml(String theCityCode) {
		
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";   
		String methodname ="selectJoke"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
      //  request.addProperty("theCityCode",theCityCode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
            ht.call(soapaction, envelope);  
            if (envelope.getResponse() != null)  
            {  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
            	return soapObject;
            }  
            else {  
        		return null;  
            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        }  
		return null;  
	}

	public static void sendnewpost_xml(String postid,String content){
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/service1.asmx";
		String methodname ="newPost"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("postid",postid);
        request.addProperty("content",content);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
            ht.call(soapaction, envelope);  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        }  
	}

	public static void sendnewpostback_xml(String title,String content){
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/service1.asmx";
		String methodname ="newPostBack"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("title",title);
        request.addProperty("content",content);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
            ht.call(soapaction, envelope);  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        }  
	}

	public static SoapObject _getjobtitle() {
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";   
		String methodname ="searchJob"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
     //   request.addProperty("theCityCode",theCityCode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
        	//System.out.println(soapaction);
            // 第5步：调用WebService  
            ht.call(soapaction, envelope);  
        //	System.out.println("ht.call" ); 
            if (envelope.getResponse() != null)  
            {  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
            	
            	return soapObject;
            }  
            else {  
        		return null;  

            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        	//tvweather=e.toString();
        }  
		return null;  
	}
	
	public static SoapObject getjobdetail(String jobid) {
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";   
		String methodname ="searchJobDetail"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("jobid",jobid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
        	//System.out.println(soapaction);
            // 第5步：调用WebService  
            ht.call(soapaction, envelope);  
        //	System.out.println("ht.call" ); 
            if (envelope.getResponse() != null)  
            {  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
            	return soapObject;
            }  
            else {  
        		return null;  

            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        	//tvweather=e.toString();
        }  
		return null;  
	}

	public static SoapObject getnewstitle() {
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";   
		String methodname ="getNews"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
     //   request.addProperty("theCityCode",theCityCode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
        	//System.out.println(soapaction);
            // 第5步：调用WebService  
            ht.call(soapaction, envelope);  
        //	System.out.println("ht.call" ); 
            if (envelope.getResponse() != null)  
            {  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
            	
            	return soapObject;
            }  
            else {  
        		return null;  

            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        	//tvweather=e.toString();
        }  
		return null;  
	}

	public static SoapObject getnewsdetail(String newsid) {
		String namespace="http://tempuri.org/";
	    String serviceUrl = "http://172.18.113.24:9090/Service1.asmx";   
		String methodname ="getNewsDetail"; 
		String soapaction=namespace+methodname;
		SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("newsid",newsid);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;//是否是dotNet WebService  
        envelope.bodyOut=request;
        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
        ht.debug = true;
        try  
        {   
        	//System.out.println(soapaction);
            // 第5步：调用WebService  
            ht.call(soapaction, envelope);  
        //	System.out.println("ht.call" ); 
            if (envelope.getResponse() != null)  
            {  
            	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
            	return soapObject;
            }  
            else {  
        		return null;  

            }  
        }  
        catch (Exception e)  
        {  
			e.printStackTrace();
        	//tvweather=e.toString();
        }  
		return null;  
	}

	


	


	

	
}
