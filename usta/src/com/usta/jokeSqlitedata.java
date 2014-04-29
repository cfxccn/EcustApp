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
		//db.execSQL("insert into jokes(text) value('丑女跟和尚同船渡河，和尚无意间瞅了丑女一眼，丑女立刻大发脾气：大胆秃头，光天化日之下竟敢偷看良家妇女！ ’ 和尚一听，吓得连忙把眼睛闭上。丑女一见，更生气了：你偷看我还不算，还敢闭上眼睛在心里想我！  和尚无法跟她讲道理，又把脸扭到一边。丑女得理不饶人，双手叉腰，大声训斥道：你觉得无脸见我，正好说明你心中有鬼！')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	

}
