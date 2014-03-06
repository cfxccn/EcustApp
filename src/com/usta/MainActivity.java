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
	
	private ViewPager viewPager;//ҳ������
	private ImageView imageView;// ����ͼƬ
	private TextView textView1,textView2,textView3;
	private List<View> views;// Tabҳ���б�
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private View view1,view2,view3,view4;//����ҳ��
	
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
	  *  ��ʼ��ͷ��
	  */


	/**
	 2      * ��ʼ������
	 3 */

	private void InitImageView() {
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// ���ö�����ʼλ��
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

    	int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
		public void onPageScrollStateChanged(int arg0) {
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			imageView.startAnimation(animation);
		//Toast.makeText(ECUST.this, "��ѡ����"+ viewPager.getCurrentItem()+"ҳ��", Toast.LENGTH_SHORT).show();
		}
    }
}
