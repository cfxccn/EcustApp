package com.usta.network;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.usta.activity.SchoolBus;

import android.os.Handler;
import android.os.Message;

public class HttpUtils {

	
	 private static String path="http://172.18.113.24:8080/jsonProjject/servlet/paction";
	 private static  URL url_;
	/* static
	 {
		 try {
			url_=new URL(path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }*/
	 public static String sendPostMessage(Map<String,String> params,String encode)
	 {
		
			 StringBuffer buffer =new StringBuffer();
			 //buffer.append("?");
			 try{
			  if(params!=null&&!params.isEmpty())
			  { for(Map.Entry<String,String> entry:params.entrySet())
				 {
					 
						buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),encode)).append("&");
						//System.out.println(buffer.toString());
				 }
						buffer.deleteCharAt(buffer.length()-1);
			  }
						//System.out.print(buffer.toString());
					
							HttpURLConnection urlConnection =(HttpURLConnection)url_.openConnection();
							//
							urlConnection.setConnectTimeout(3000);
							urlConnection.setRequestMethod("POST");
							urlConnection.setDoInput(true);
							urlConnection.setDoOutput(true);
							byte[] mydata =buffer.toString().getBytes();
							urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
							urlConnection.setRequestProperty("Content-Length",String.valueOf(mydata.length));
					
								
							
							OutputStream outputStream=urlConnection.getOutputStream();
							//outputStream=urlConnection.getOutputStream();
							
							
							// DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
							outputStream.write(mydata,0,mydata.length);
							outputStream .close();
							int responseCode =urlConnection.getResponseCode();
							if(responseCode==200)
							{
								//return changeInputStream(urlConnection.getInputStream(),encode);
								
							}
						}
						
						 catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
						 
					 		
					catch (Exception e) {
						// TODO Auto-generated catch block
						//System.out.print(e+"catch2");
					}
				 	 
			  return "";
      	 
	 }
	
	 private static String changeInputStream(InputStream inputStream,String encode)
	 {
		 ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
		 byte[] data =new byte[1024];
		 int len=0;
		 String result="";
		 if(inputStream!=null){
			 try {
				while((len=inputStream.read(data))!=-1)
				 {
					 outputStream.write(data,0,len);
					 
				 }
				result=new String(outputStream.toByteArray(),encode);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.print(e+"catch3");
			}
		 }
		 return result;
	 }
	
	 public static String getJsoncontent(String url_path)
	 {
		 String s1="";
		 try{
			 URL url=new URL(url_path);
			 HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			 connection.setConnectTimeout(30000);
			 connection.setRequestMethod("GET");
			 connection.setDoInput(true);
			 int code =connection.getResponseCode();
			 if(code ==200)
			 {
				return  changeInputStream(connection.getInputStream());
			 }
		 }catch(Exception e)
		 {
			  s1=e.toString();
		 }
		 return s1;
	 }
	 	private static String changeInputStream(InputStream inputStream)  {
		// TODO Auto-generated method stub
		
		String jsonString ="" ;
		ByteArrayOutputStream outputstream  =new ByteArrayOutputStream();
		int len=0;
		byte[] data=new byte[1024];
		try {
			while((len=inputStream.read(data))!=-1)
			{
				outputstream.write(data,0,len);
			}
			jsonString =new String(outputstream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
	 	
	 	public static String  postspdata()
	 	{
	 		try {
				url_=new URL(path);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		String [] strs=SchoolBus.spinnervalue();
	 		Map<String,String> params =new HashMap<String,String>();
	 		params.put("sp1", strs[0]);
	 		params.put("sp2", strs[1]);
	 		params.put("sp3", strs[2]);
	 		String result=sendPostMessage(params,"utf-8");
	 		return  result;
	 		//System.out.print(result);
	 	}
}
