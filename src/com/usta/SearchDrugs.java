package com.usta;

import java.sql.DriverManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.microsoft.sqlserver.*;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;

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
    	Button btnsearch_drugs=(Button)findViewById(R.id.btnsearch_drugs);
    	btnsearch_drugs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				 String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				 String dbURL = "jdbc:sqlserver://172.18.113.24; DatabaseName=ecustj111";
				 SQLServerConnection dbConn;

				  try {

				   Class.forName(driverName);

				   dbConn = (SQLServerConnection) DriverManager.getConnection(dbURL, "test", "t1234567");

				   System.out.println("Connection Successful!");  //如果连接成功 控制台输出Connection Successful!

				  } catch (Exception e) {

				   e.printStackTrace();

				  }

				}
				

		});
    }
}
