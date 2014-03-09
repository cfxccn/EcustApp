package com.usta;
import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.CircleLayout.OnItemClickListener;
import com.usta.CircleLayout.OnItemSelectedListener;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
//import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockActivity   {
    private int index = 0;
	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3;//各个页卡
	private TextView tv_tolay1_main,tv_tolay2_main,tv_tolay3_main;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		init_ImageView();
		init_ViewPager();
		init_LayInstru();
    }

    protected void onActivityResult(int requestCode, int resultCode,  
            Intent data){  
    	switch (resultCode){  
    	case RESULT_OK:  
    		Bundle b = data.getExtras();  
    		index=b.getInt("index");
    		viewPager.setCurrentItem(index);
    		}
    	
}  

    private void init_LayInstru() {
    	tv_tolay1_main = (TextView) findViewById(R.id.tv_tolay1_main);
    	tv_tolay2_main = (TextView) findViewById(R.id.tv_tolay2_main);
    	tv_tolay3_main = (TextView) findViewById(R.id.tv_tolay3_main);
    	tv_tolay1_main.setBackgroundColor(Color.GRAY);
    	
    	tv_tolay1_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{        	
				index=0;
				viewPager.setCurrentItem(0);
			}
		});
    	tv_tolay2_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{        	
				index=1;
				viewPager.setCurrentItem(1);
			}
		});
    	tv_tolay3_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{        	
				index=2;
				viewPager.setCurrentItem(2);
			}
		});
    }
	private void init_lay0btn()
	{
		Button btn_tosearchdrugs_lay1 = (Button) findViewById(R.id.btn_tosearchdrugs_lay1);
		btn_tosearchdrugs_lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			//	 setContentView(R.layout.searchdrugs);
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchDrugs.class);
				startActivityForResult(intent, 0);
		//		MainActivity.this.finish();
			}
		});
		Button btn_tosearchbooks_lay1 = (Button) findViewById(	R.id.btn_tosearchbooks_lay1);
		btn_tosearchbooks_lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			//	 setContentView( R.layout.searchbooks);
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchBooks.class);
				startActivityForResult(intent, 0);
			}
		});
		Button btn_tosearchclassroom_lay1 = (Button) findViewById(R.id.btn_tosearchclassroom_lay1);
		btn_tosearchclassroom_lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchClassroom.class);
				startActivityForResult(intent, 0);
			}
		});
		Button btn_tolinknet_lay1 = (Button) findViewById(R.id.btn_tolinknet_lay1);
		btn_tolinknet_lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Linknet.class);
				startActivityForResult(intent, 0);
			}
		});
		Button btn_tomorningtrain_lay1 = (Button) findViewById(R.id.btn_tomorningtrain_lay1);
		btn_tomorningtrain_lay1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Morningtrain.class);
				startActivityForResult(intent, 0);
			}
		});	
		
	}
	private void init_lay1btn()
	{
	}
	private void init_lay2btn()
	{
	Button btn_tojoke_lay3 = (Button) findViewById(R.id.btn_tojoke_lay3);
	btn_tojoke_lay3.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v)
		{
//		Intent intent =new Intent();
//		intent.setClass(MainActivity.this, Joke.class);
//		startActivity(intent);
//		MainActivity.this.finish();
			Intent intent =new Intent();
			intent.putExtra("index", index);
			intent.setClass(MainActivity.this, Joke.class);
			startActivityForResult(intent, 0);
		}
	});
	}

	private void init_ViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.lay1, null);
		view2=inflater.inflate(R.layout.lay2, null);
		view3=inflater.inflate(R.layout.lay3, null);
	//	view4=inflater.inflate(R.layout.xml1,null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
	//	views.add(view4);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(index);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	private void init_ImageView() {
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	
	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));
		}

		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0);
			 return mListViews.get(position);
		}

	    @Override
	    public void setPrimaryItem(View container, int position, Object object) {
	    	switch (position){
			case 0:	init_lay0btn();break;
			case 1: init_lay1btn();break;
			case 2: init_lay2btn();break;
			}
	    }
		
		public int getCount() {			
			return  mListViews.size();
		}

		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;
		}
	}

    public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one*index, one*arg0, 0, 0);
			index = arg0;
			
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

        	if (arg0==0){ 
        		tv_tolay1_main.setBackgroundColor(Color.GRAY);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		}
        	if (arg0==1){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.GRAY);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		}
        	if (arg0==2){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.GRAY);
        		}
		}
    }
          
    public boolean onCreateOptionsMenu(Menu menu) {      
        getSupportMenuInflater().inflate(R.menu.main,  menu);      
        return true;      
    }   



    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch(item.getItemId()){  

      case R.id.area_settings:  
//    	        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("请选择校区");  
//    	    	//  builder.setMessage("Message");  
//
//    	        builder.setPositiveButton("徐汇校区",  
//    	                new DialogInterface.OnClickListener() {  
//    	                    public void onClick(DialogInterface dialog, int whichButton) {  
//    	                        setTitle("点击了对话框上的Button1");  
//    	                    }  
//    	                }).setNeutralButton("奉贤校区",  
//    	                new DialogInterface.OnClickListener() {  
//    	                    public void onClick(DialogInterface dialog, int whichButton) {  
//    	                        setTitle("点击了对话框上的Button2");  
//    	                    }  
//    	                }).setNegativeButton("金山校区",  
//    	                new DialogInterface.OnClickListener() {  
//    	                    public void onClick(DialogInterface dialog, int whichButton) {  
//    	                        setTitle("点击了对话框上的Button3");  
//    	                    }  
//    	                }).show();  
			Intent intent =new Intent();
			intent.putExtra("index", index);
			intent.setClass(MainActivity.this, Setting.class);
			startActivityForResult(intent, 0);
    	  
  	        break;  

  	        
        }  
        return super.onOptionsItemSelected(item);  
    } 
}
