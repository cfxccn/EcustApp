package com.usta;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Joke extends Activity  {
	private String text=" 12";
	int num ;
	jokeSqlitedata jsd;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joke);
		initbtn();
	//	initdata();
	}

	private void initbtn(){
		Button button=(Button)findViewById(R.id.btnjoke);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jsd=new jokeSqlitedata(Joke.this);
				SQLiteDatabase db=jsd.getWritableDatabase();
				db.execSQL("insert into jokes (text) values ('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
				db.execSQL("insert into jokes (text) values ('ŮA����²������ڶ��أ�ŮB���ҹ���û��һ�ٽ��ŮA�������˵����ŮB��Ҳ��һ�ٶ�ʮ�')");
				db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");
				cursor =db.rawQuery("select * from jokes", null);
				db.close();
				if(cursor.moveToLast())
				{
				 num=cursor.getInt(0);
				}	
				int k =(int)(Math.random()*num);
				if(cursor.moveToPosition(k))
				{
					 text =cursor.getString(1);
				}
				cursor.close();
//				switch(v.getId())
//				{
//				case R.id.btnjoke:
//				{
					TextView textview_joke=(TextView)findViewById(R.id.textview_joke);
					textview_joke.setText(text);
		//		}
		//		}				
			}
		});
		Button btnback_joke=(Button)findViewById(R.id.btnback_joke);
		btnback_joke.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// setContentView(R.layout.activity_main);
				Intent intent =new Intent();
				intent.setClass( Joke.this, MainActivity.class);
				startActivity(intent);
				Joke.this.finish();
				}
			});
		}


	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
				Intent intent =new Intent();
				intent.setClass( Joke.this, MainActivity.class);
				startActivity(intent);
				Joke.this.finish();
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    } 
}


