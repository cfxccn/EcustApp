package com.usta;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchClassroom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchclassroom);
        init();
    }
    
    private void init(){
    	Button btnback=(Button)findViewById(R.id.btnback_classroom);
    	btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
				Intent intent =new Intent();
				intent.setClass( SearchClassroom.this, MainActivity.class);
				startActivity(intent);
				SearchClassroom.this.finish();

				
			}
		});
    	
    }

    
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */
    
    
}

