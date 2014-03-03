package com.usta;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class SearchBooks extends Activity {

	private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbooks);
      
         initwebview();
         initbtn();
    }
    private void initwebview(){
    	  webView = (WebView)findViewById(R.id.webView_lib);
          webView.loadUrl("http://202.120.96.75/sms/opac/search/showSearch.action?xc=6"); 
          webView.setWebViewClient(new WebViewClient(){
              @Override
              public boolean shouldOverrideUrlLoading(WebView view, String url) {
              // TODO Auto-generated method stub
              view.loadUrl(url);
              return true;
         } });
          }
    
    private void initbtn(){
    	Button btnback=(Button)findViewById(R.id.btnback_books);
    	btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
				Intent intent =new Intent();
				intent.setClass( SearchBooks.this, MainActivity.class);
				startActivity(intent);
				SearchBooks.this.finish();
			}
		});
    	Button btnhome=(Button)findViewById(R.id.btnhome_books);
    	btnhome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
				initwebview();
			}
		});

    }
  public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			Intent intent =new Intent();
			intent.setClass( SearchBooks.this, MainActivity.class);
			startActivity(intent);
			SearchBooks.this.finish();
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}

