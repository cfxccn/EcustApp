package com.usta;
import java.util.ArrayList;
import java.util.List;

import com.usta.CircleLayout.OnItemClickListener;
import com.usta.CircleLayout.OnItemSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity   {
	
	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1,textView2,textView3;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3,view4;//各个页卡
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		InitImageView();
		InitViewPager();
    }

	private void initlay0()
	{
		Button btnsearchdurgs = (Button) findViewById(R.id.btnsearchdrugs);
		btnsearchdurgs.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			//	 setContentView(R.layout.searchdrugs);
				Intent intent =new Intent();
				intent.setClass(MainActivity.this, SearchDrugs.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		

		Button btnsearchbooks = (Button) findViewById(	R.id.btnsearchbooks);
		btnsearchbooks.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			//	 setContentView( R.layout.searchbooks);
			Intent intent =new Intent();
			intent.setClass(MainActivity.this, SearchBooks.class);
			startActivity(intent);
			MainActivity.this.finish();
			}
		});
		Button btnsearchclassroom = (Button) findViewById(R.id.btnsearchclassroom);
		btnsearchclassroom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			Intent intent =new Intent();
			intent.setClass(MainActivity.this, SearchClassroom.class);
			startActivity(intent);
			MainActivity.this.finish();
			}
		});
		Button btnlinknet = (Button) findViewById(R.id.btnlinknet);
		btnlinknet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			Intent intent =new Intent();
			intent.setClass(MainActivity.this, Linknet.class);
			startActivity(intent);
			MainActivity.this.finish();
			}
		});

		Button btnmorningtrain = (Button) findViewById(R.id.btnmorningtrain);
		btnmorningtrain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
			Intent intent =new Intent();
			intent.setClass(MainActivity.this, Morningtrain.class);
			startActivity(intent);
			MainActivity.this.finish();
			}
		});	
		
	}
	private void initlay1()
	{
	}

	private void initlay2()
	{
	Button btnjoke = (Button) findViewById(R.id.btn_joke);
	btnjoke.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v)
		{
		Intent intent =new Intent();
		intent.setClass(MainActivity.this, Joke.class);
		startActivity(intent);
		MainActivity.this.finish();
		}
	});
	}

	private void InitViewPager() {
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
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */


	/**
	 2      * 初始化动画
	 3 */

	private void InitImageView() {
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
			case 0:	initlay0();break;
			case 1: initlay1();break;
			case 2: initlay2();break;
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
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
		//Toast.makeText(ECUST.this, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
		}
    }
}
