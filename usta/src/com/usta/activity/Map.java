package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.yoojia.imagemap.ImageMap;
import net.yoojia.imagemap.core.Bubble;
import net.yoojia.imagemap.core.CircleShape;
import net.yoojia.imagemap.core.PolyShape;
import net.yoojia.imagemap.core.Shape;



import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.MenuItem;
import com.usta.R;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 


public class Map extends SherlockActivity {
	private int index;
	Intent intent;
	String content;
	private ImageMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);
		setContentView(R.layout.map);
        map = (ImageMap) findViewById(R.id.imagemap);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_fengxian, new BitmapFactory.Options()); 
        map.setMapBitmap(bitmap);
        View bubble = getLayoutInflater().inflate(R.layout.popup,null);


        map.setBubbleView(bubble,new Bubble.RenderDelegate() {
            @Override
            public void onDisplay(Shape shape, View bubbleView) {
                ImageView logo = (ImageView) bubbleView.findViewById(R.id.logo);         
                TextView name = (TextView) bubbleView.findViewById(R.id.name); 
                name.setText("abc"); 
                logo.setImageResource(R.drawable.kfc_logo);
            }
        });
        showPostion(); 

    }
	public boolean showPostion(){ 
		
		CircleShape a = new CircleShape("a", Color.RED);
		a.setValues(String.format("%.5f,%.5f,15", (double)150, (double)150));
		map.addShape(a);
		
		float aa[] = {10, 10, 10, 20, 20, 30, 30, 20, 30, 10, 10, 10};
		
		PolyShape b = new PolyShape("b", Color.RED);
		b.setValues(aa);
		map.addShape(b);
		
		CircleShape black = new CircleShape("NO", Color.TRANSPARENT);
		black.setValues(String.format("%.5f,%.5f,15",(double)1000,(double)700));
		map.addShapeAndRefToBubble(black);
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {  
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			setResult(RESULT_OK, intent);  
			finish();  
			return true;
		}  	    
		return super.onKeyDown(keyCode, event);  
	} 
    



	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch(item.getItemId()){  
	
	  case android.R.id.home:  
		        setResult(RESULT_OK, intent);  
		        finish();  	        
		        break;  	        
	    }  
	    return super.onOptionsItemSelected(item);  
	}  
}

