package com.usta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class jokeSqlitedata extends SQLiteOpenHelper 
{

public jokeSqlitedata(Context context) {
		super(context, "jokes.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	/*public jokeSqlitedata(MainActivity context, String name, Object factory,
			int version) {
		// TODO Auto-generated constructor stub
		super(context, name, (CursorFactory) factory, version);
	}
*/
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table jokes (id integer primary key autoincrement,text varchar(300))");
		//db.execSQL("insert into jokes(text) value('��Ů������ͬ���ɺӣ������������˳�Ůһ�ۣ���Ů���̴�Ƣ������ͺͷ�����컯��֮�¾���͵�����Ҹ�Ů�� �� ����һ�����ŵ���æ���۾����ϡ���Ůһ�����������ˣ���͵���һ����㣬���ұ����۾����������ң�  �����޷������������ְ���Ť��һ�ߡ���Ů�������ˣ�˫�ֲ���������ѵ�����������������ң�����˵���������й�')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	

}
