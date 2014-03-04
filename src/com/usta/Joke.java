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
	}
	@Override 
	public void onClick(View v) {
		jsd=new jokeSqlitedata(this);
		SQLiteDatabase db=jsd.getWritableDatabase();
		db.execSQL("insert into jokes (text) values ('丑女跟和尚同船渡河，和尚无意间瞅了丑女一眼，丑女立刻大发脾气：大胆秃头，光天化日之下竟敢偷看良家妇女！ ’ 和尚一听，吓得连忙把眼睛闭上。丑女一见，更生气了：你偷看我还不算，还敢闭上眼睛在心里想我！  和尚无法跟她讲道理，又把脸扭到一边。丑女得理不饶人，双手叉腰，大声训斥道：你觉得无脸见我，正好说明你心中有鬼！')");
		db.execSQL("insert into jokes (text) values ('女A：你猜猜我现在多重？女B：我估计没有一百斤……女A：你真会说话。女B：也有一百二十斤。')");
		db.execSQL("insert into jokes (text) values ('我前面一女生平胸，然后我问她哪天你晚上自己回家，被劫色怎么办…？她淡淡的回了句：‘我就脱了上衣，然后说，别激动，是自己人’…自己人…己人…人…')");db.execSQL("insert into jokes (text) values ('我前面一女生平胸，然后我问她哪天你晚上自己回家，被劫色怎么办…？她淡淡的回了句：‘我就脱了上衣，然后说，别激动，是自己人’…自己人…己人…人…')");
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
		switch(v.getId())
		{
		case R.id.button1:
		{TextView tw1=(TextView)findViewById(R.id.textView2);
		 tw1.setText(text);
		}
		}
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


