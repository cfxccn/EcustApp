package com.usta.ecustapp.activity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.usta.ecustapp.*;

import android.view.MenuItem;

public class SearchBooks extends ActionBarActivity {
	private int index;
	private int todo;
	String urlString;
	Intent intent;
	private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbooks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  

        intent = getIntent();
        index=intent.getIntExtra("index", 0);
        todo=intent.getIntExtra("todo", 0);
        urlString="http://202.120.96.75/sms/opac/search/showSearch.action?xc=6";
        if(todo==1){
        	urlString="http://lib.ecust.edu.cn:8081/GATESEAT/LRP.ASPX";
        }
        initwebview();
    }
    private void initwebview(){
    	  webView = (WebView)findViewById(R.id.webView_lib);
          webView.loadUrl(urlString); 
          webView.setInitialScale(100);
      //    webView.getSettings().setBuiltInZoomControls(true);
          webView.setWebViewClient(new WebViewClient(){
              @Override
              public boolean shouldOverrideUrlLoading(WebView view, String url) {
              // TODO Auto-generated method stub
              view.loadUrl(url);
              return true;
         } });
          }
    

  public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
	        setResult(RESULT_OK, intent);  
	        finish();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.searchbook_menu, (android.view.Menu) menu);
//        return true;
//    }
  @Override      
  public boolean onCreateOptionsMenu(Menu menu) {      
      getMenuInflater().inflate(R.menu.searchbook_menu, menu);      
      return super.onCreateOptionsMenu(menu);      
  }   

  @Override  
  public boolean onOptionsItemSelected(MenuItem item) {  
      switch(item.getItemId()){  

    case android.R.id.home:  
	        setResult(RESULT_OK, intent);  
	        finish();  	        
	        break;  
    case R.id.homepage_searchbook:  
        webView.loadUrl("http://202.120.96.75/sms/opac/search/showSearch.action?xc=6");       
        break;  
	        
      }  
      return super.onOptionsItemSelected(item);  
  }  
}

