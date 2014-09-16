package com.usta.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.yoojia.imagemap.ImageMap;
import net.yoojia.imagemap.core.Bubble;
import net.yoojia.imagemap.core.CircleShape;
import net.yoojia.imagemap.core.PolyShape;
import net.yoojia.imagemap.core.Shape;

import com.usta.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 


public class Map extends ActionBarActivity {
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
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newwidth = 1178;
        float scale = ((float)newwidth)/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        
        map.setMapBitmap(bitmap);
        View bubble = getLayoutInflater().inflate(R.layout.popup,null);


        map.setBubbleView(bubble,new Bubble.RenderDelegate() {
            @Override
            public void onDisplay(Shape shape, View bubbleView) {
                ImageView logo = (ImageView) bubbleView.findViewById(R.id.logo);         
                TextView name = (TextView) bubbleView.findViewById(R.id.name); 
                name.setText(shape.tag.toString()); 
                logo.setImageResource(R.drawable.ecustlogo);
            }
        });
        showPostion();

    }
	public boolean showPostion(){ 
		
/*		float Aclass[] = {769,463,830,514,801,559,744,518};
		PolyShape b = new PolyShape("A教", Color.TRANSPARENT);
		b.setValues(Aclass);
		map.addShape(b);
		b.setAlaph(0);*/
		
/*		float Bclass[] = {846,530,877,576,897,594,864,596,820,567};
		b = new PolyShape("B教", Color.TRANSPARENT);
		b.setValues(Bclass);
		b.setAlaph(0);
		map.addShape(b);*/
		
/*		float Cclass[] = {732,545,780,581,778,592,725,552};
		b = new PolyShape("C教", Color.TRANSPARENT);
		b.setValues(Cclass);
		b.setAlaph(0);
		map.addShape(b);*/
		
/*		float Dclass[] = {808,596,858,625,856,636,808,603};
		b = new PolyShape("D教", Color.TRANSPARENT);
		b.setValues(Dclass);
		b.setAlaph(0);
		map.addShape(b);*/
		
/*		float playgroud[] = {913,654,980,551,1036,601,987,684};
		b = new PolyShape("足球场(运动场)", Color.TRANSPARENT);
		b.setValues(playgroud);
		b.setAlaph(0);
		map.addShape(b);*/
		
		CircleShape black = new CircleShape("高大上的图书馆", Color.TRANSPARENT);
		black.setValues(String.format("%.5f,%.5f,45",(double)607,(double)523));
		map.addShapeAndRefToBubble(black);
		black.setAlaph(0);
		
		black = new CircleShape("E教英语楼", Color.TRANSPARENT);
		black.setValues(String.format("%.5f,%.5f,40",(double)676,(double)436));
		map.addShapeAndRefToBubble(black);
		black.setAlaph(0);		

		black = new CircleShape("体育馆", Color.TRANSPARENT);
		black.setValues(String.format("%.5f,%.5f,35",(double)890,(double)760));
		map.addShapeAndRefToBubble(black);
		black.setAlaph(0);
		
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

