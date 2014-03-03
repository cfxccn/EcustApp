package com.usta;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchDrugs extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdrugs);
        init();
    }
    
    private void init(){
    	Button btnback=(Button)findViewById(R.id.btnback_drugs);
    	btnback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				// setContentView(R.layout.activity_main);
				Intent intent =new Intent();
				intent.setClass( SearchDrugs.this, MainActivity.class);
				startActivity(intent);
				SearchDrugs.this.finish();
			}
		});
    }
}
