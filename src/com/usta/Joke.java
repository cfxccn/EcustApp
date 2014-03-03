package com.usta;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Joke extends Activity implements OnClickListener {
	private String text=" 12";
	int num ;
	jokeSqlitedata jsd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.joke);
		Button button=(Button)findViewById(R.id.button1);
		button.setOnClickListener(this);
		
		//jokeSqlitedata jsd =new jokeSqlitedata(this);
	//	SQLiteDatabase db = jsd.getWritableDatabase();
		//jokedo jd=new jokedo(this);
		//SQLiteDatabase db;
		//jd.add();
		//db.execSQL("insert into jokes(text) value('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
		/*Cursorcursor =db.rawQuery("select * from jokes", null);
		if(cursor.moveToFirst())
		{
			 text =cursor.getString(1);
		}
		cursor.close();
		db.close();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
		
	}	
	

	@Override 
	public void onClick(View v) {
		jsd=new jokeSqlitedata(this);
		SQLiteDatabase db=jsd.getWritableDatabase();
		//db.execSQL("insert into jokes (text) values ('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
		
		//db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");
		//jokedo jd=new jokedo(this);
		//SQLiteDatabase db=jsd.getWritableDatabase();
		db.execSQL("insert into jokes (text) values ('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
		db.execSQL("insert into jokes (text) values ('ŮA����²������ڶ��أ�ŮB���ҹ���û��һ�ٽ��ŮA�������˵����ŮB��Ҳ��һ�ٶ�ʮ�')");
		db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");db.execSQL("insert into jokes (text) values ('��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�')");
		//db.close();
		Cursor cursor =db.rawQuery("select * from jokes", null);
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
		db.close();
		//��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�
		//db.execSQL("insert into jokes(text) value('')");
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.button1:
		{TextView tw1=(TextView)findViewById(R.id.textView2);
		 //tw1.setText("��ǰ��һŮ��ƽ�أ�Ȼ�������������������Լ��ؼң�����ɫ��ô�졭���������Ļ��˾䣺���Ҿ��������£�Ȼ��˵���𼤶������Լ��ˡ����Լ��ˡ����ˡ��ˡ�");
		 tw1.setText(text);
		}
		
			
		}
		
		}
		
	}


