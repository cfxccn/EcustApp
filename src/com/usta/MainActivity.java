package com.usta;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;



import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.usta.getnetdata.GetNetData;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends SherlockActivity   {
    private int index = 2;
	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3,view4,view5;//各个页卡
	private ImageView iv_tolay1_main,iv_tolay2_main,iv_tolay3_main,iv_tolay4_main,iv_tolay5_main;
	private TextView  tv_tolay1,tv_tolay2,tv_tolay3,tv_tolay4,tv_tolay5;

	private SharedPreferences userInfo;
	private String tvdate1="";
	private String tvtemp1="";
	private String tvdate2="";
	private String tvtemp2="";
	private String pic11="";
	private String pic12="";
	private String pic21="";
	private String pic22="";
	private String air_aqi="今日空气质量：";
	private String air_advise="户外活动建议：";
	
	private int aqi;
	Menu _menu;

	private int _pic11=0;
	private int _pic12=0;
	private int _pic21=0;
	private int _pic22=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        init_ImageView();
		init_ViewPager();
		init_LayInstru();
		  ConnectivityManager manger = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE); 
          NetworkInfo info = manger.getActiveNetworkInfo(); 
          if (info!=null && info.isConnected())
          {
		init_Weather();
          }
          else 
          {
        	  Toast Toast1=Toast.makeText(this,"请联网", Toast.LENGTH_SHORT);
        	  Toast1.show();
          }

          }
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.chat, menu);
    	menu.findItem(R.id.newpost_chat).setVisible(false);
    	_menu=menu;
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
        case R.id.newpost_chat: 
			Intent intent =new Intent();
			intent.putExtra("index", index);
			intent.setClass(MainActivity.this, NewPost.class);
			startActivityForResult(intent, 0);
			
            break;
        }
        return true;
    }
    
    
    private void init_Weather() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	try {
	    		SoapObject sObject= GetNetData.getweatherdata("奉贤");
	            tvdate1=   sObject.getProperty(7).toString();  
	            tvtemp1=   sObject.getProperty(8).toString();  
	    //        tvweather=sObject.getProperty(4).toString(); 
	            
	            tvdate2=   sObject.getProperty(12).toString();  
	            tvtemp2=   sObject.getProperty(13).toString();  
	            
	            pic11=sObject.getProperty(10).toString(); 
	            pic12=sObject.getProperty(11).toString(); 
	            
	            pic21=sObject.getProperty(15).toString(); 
	            pic22=sObject.getProperty(16).toString(); 

	    	    JSONArray jsonArr= GetNetData.getairaqidata();
	    	    JSONObject jsonObject=jsonArr.getJSONObject(0);
	    	    aqi=(Integer)jsonObject.get("pm2_5");
    	    	//air_aqi=air_aqi+" "+Integer.toString(aqi);
    	    	air_aqi="今日空气质量："+(String)jsonObject.get("quality")+"   PM2.5指数："+Integer.toString(aqi);
    	    	if(aqi<80)
    	    	{air_advise=air_advise+"自由活动不受影响";
    	    	}else
    	    		if(aqi<120)
    	    		{air_advise=air_advise+"尽量减少";
    	    		}else
    	    			{air_advise=air_advise+"完全停止";
    	    			}
    	    //	air_aqi=air_aqi+" "+(String)jsonObject.get("primary_pollutant");

		    	 handler.sendEmptyMessage(0);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    
	    }
	}).start();
   	
	}

private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		TextView tvDate1=(TextView)findViewById(R.id.tvDate1);
		TextView tvTemp1=(TextView)findViewById(R.id.tvTemp1);
		TextView tvDate2=(TextView)findViewById(R.id.tvDate2);
		TextView tvTemp2=(TextView)findViewById(R.id.tvTemp2);
		TextView tvWeather=(TextView)findViewById(R.id.tvWeath);
		TextView tvAdvise=(TextView)findViewById(R.id.tvAdvise);

	//	tvdate1.substring(tvdate1.indexOf("日"));
		tvDate1.setText("12时内"+tvdate1.substring(tvdate1.indexOf("日")+1));
		tvTemp1.setText(tvtemp1);

		tvDate2.setText("24时内"+tvdate2.substring(tvdate2.indexOf("日")+1));
		
		tvTemp2.setText(tvtemp2);
		ImageView imgv11=(ImageView)findViewById(R.id.imgv11);
		ImageView imgv12=(ImageView)findViewById(R.id.imgv12);	
		ImageView imgv21=(ImageView)findViewById(R.id.imgv21);
		ImageView imgv22=(ImageView)findViewById(R.id.imgv22);
		
		tvWeather.setText(air_aqi);
		tvAdvise.setText(air_advise);
		_pic11=Integer.parseInt(pic11.substring(0,pic11.lastIndexOf(".")));
		_pic12=Integer.parseInt(pic12.substring(0,pic12.lastIndexOf(".")));
		_pic21=Integer.parseInt(pic21.substring(0,pic21.lastIndexOf(".")));
		_pic22=Integer.parseInt(pic22.substring(0,pic22.lastIndexOf(".")));

		imgv11.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic11));
		imgv12.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic12));
		imgv21.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic21));
		imgv22.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic22));
		}
		};
protected void onActivityResult(int requestCode, int resultCode,  
            Intent data){  
    	switch (resultCode){  
    	case RESULT_OK:  
    		Bundle b = data.getExtras();  
    		index=b.getInt("index");
    		viewPager.setCurrentItem(index);
    		}
    	
}  
private OnClickListener laylistener = new OnClickListener(){ 
@Override 
public void onClick(View view) { 
	switch (view.getId()){
	case R.id.tvlay1: ;
	case R.id.imageView_Nearby:
		index=0;
		viewPager.setCurrentItem(0); break;
	case R.id.tvlay2: ;
	case R.id.imageView_Search: 
		index=1;
	viewPager.setCurrentItem(1); break;
	case R.id.tvlay3: ;
	case R.id.imageView_Home: 
		index=2;
	viewPager.setCurrentItem(2); break;
	case R.id.tvlay4: ;
	case R.id.imageView_Chat: 
		index=3;
	viewPager.setCurrentItem(3); break;
	case R.id.tvlay5: ;
	case R.id.imageView_Setting: 
		index=4;
	viewPager.setCurrentItem(4); break;
	}
	
	
} 
}; 


    private void init_LayInstru() {
    	iv_tolay1_main = (ImageView) findViewById(R.id.imageView_Nearby);
    	iv_tolay2_main = (ImageView) findViewById(R.id.imageView_Search);
    	iv_tolay3_main = (ImageView) findViewById(R.id.imageView_Home);
    	iv_tolay4_main = (ImageView) findViewById(R.id.imageView_Chat);
    	iv_tolay5_main = (ImageView) findViewById(R.id.imageView_Setting);
    	
    	tv_tolay1 = (TextView) findViewById(R.id.tvlay1);
    	tv_tolay2 = (TextView) findViewById(R.id.tvlay2);
    	tv_tolay3 = (TextView) findViewById(R.id.tvlay3);
    	tv_tolay4 = (TextView) findViewById(R.id.tvlay4);
    	tv_tolay5 = (TextView) findViewById(R.id.tvlay5);
    	
    	tv_tolay3.setTextColor(Color.BLUE);
    //	tv_tolay3_main.setBackgroundColor(Color.WHITE);
    	
    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
    	Animation animation = new TranslateAnimation(one*index, one*index, 0, 0);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		imageView.startAnimation(animation);
		tv_tolay1.setOnClickListener(laylistener);
		iv_tolay1_main.setOnClickListener(laylistener);
		tv_tolay2.setOnClickListener(laylistener);
		iv_tolay2_main.setOnClickListener(laylistener);
		tv_tolay3.setOnClickListener(laylistener);
		iv_tolay3_main.setOnClickListener(laylistener);
		tv_tolay4.setOnClickListener(laylistener);
		iv_tolay4_main.setOnClickListener(laylistener);
		tv_tolay5.setOnClickListener(laylistener);
		iv_tolay5_main.setOnClickListener(laylistener);
    }
	private void init_lay1()
	{
    	_menu.findItem(R.id.newpost_chat).setVisible(false);
	}
	private void init_lay2()
	{
    	_menu.findItem(R.id.newpost_chat).setVisible(false);

	}

	private void init_lay3()
	{
		ImageView ImageView_Job = (ImageView) findViewById(R.id.imageView_Job);
		ImageView_Job.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, JobTitleView.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView ImageView_Classroom = (ImageView) findViewById(R.id.imageView_Classroom);
		ImageView_Classroom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchClassroom.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView ImageView_Searchbook = (ImageView) findViewById(R.id.imageView_Searchbook);
		ImageView_Searchbook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchBooks.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView imageView_Morningtrain = (ImageView) findViewById(R.id.imageView_Morningtrain);
		imageView_Morningtrain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Morningtrain.class);
				startActivityForResult(intent, 0);
			}
		});
		ImageView imageView_Schoolbus=(ImageView)findViewById(R.id.imageView_Schoolbus);
		imageView_Schoolbus.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SchoolBus.class);
				startActivityForResult(intent, 0);
			}
	});
		
	}
	private void init_lay4()
	{
	}
	private void lay4joke()
	{
		userInfo = getSharedPreferences("setting", 0);  
		String joke=userInfo.getString("joke", "null");
	    	if(!joke.equalsIgnoreCase("off")){
	    		Toast toastJoke=Toast.makeText(this, "joke", Toast.LENGTH_LONG);
	    		toastJoke.show();
	    	}
	}
	private void init_lay5()
	{
    	_menu.findItem(R.id.newpost_chat).setVisible(false);
		Button btn_toadvise_setting=(Button)findViewById(R.id.btn_toadvise_setting);
		btn_toadvise_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Advise.class);
				startActivityForResult(intent, 0);
			}
		});
		userInfo = getSharedPreferences("setting", 0);  
	    //	userInfo.edit().putString("area", "null").commit();  
	    	String area=userInfo.getString("area", "null");
	    	if(area.equalsIgnoreCase("xuhui")){
	    	//    userInfo.edit().putString("area", "xuhui").commit();  
	    		RadioButton radiobtn_xuhui_setting= (RadioButton)findViewById(R.id.radiobtn_xuhui_setting);
	    		radiobtn_xuhui_setting.setChecked(true);
	    	}
	    	if(area.equalsIgnoreCase("fengxian")){
    	   // 	userInfo.edit().putString("area", "fengxian").commit();  
	    		RadioButton radiobtn_fengxian_setting= (RadioButton)findViewById(R.id.radiobtn_fengxian_setting);
	    		radiobtn_fengxian_setting.setChecked(true);
	    	}
	    	if(area.equalsIgnoreCase("jinshan")){
    	   // 	userInfo.edit().putString("area", "jinshan").commit();  
	    		RadioButton radiobtn_jinshan_setting= (RadioButton)findViewById(R.id.radiobtn_jinshan_setting);
	    		radiobtn_jinshan_setting.setChecked(true);
	    	}
	    	final Toast toastxuhui=Toast.makeText(this, "徐汇校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toastfengxian=Toast.makeText(this, "奉贤校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toastjinshan=Toast.makeText(this, "金山校区-已保存", Toast.LENGTH_SHORT);

	    	RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup_area_setting);
	    	         //绑定一个匿名监听器
	    	         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	             @Override
	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	    	                 // TODO Auto-generated method stub
	    	                 //获取变更后的选中项的ID
	    	            	 if(arg1==R.id.radiobtn_xuhui_setting){
	    	     	    	    userInfo.edit().putString("area", "xuhui").commit();  
	    	     	    	   toastxuhui.show();
	    	            	 }	    	            	
	    	            	if(arg1==R.id.radiobtn_fengxian_setting){
		    	     	    	    userInfo.edit().putString("area", "fengxian").commit();  
		    	     	    	   toastfengxian.show(); 
		    	            	 }
	    	            	 if(arg1==R.id.radiobtn_jinshan_setting){
	    	     	    	    userInfo.edit().putString("area", "jinshan").commit();  
	    	     	    	   toastjinshan.show();
	    	            	 }

	    	             }
	    	         });
	    	 	    //	userInfo.edit().putString("area", "null").commit();  
	    	         //SharedPreferences jokeInfo = getSharedPreferences("setting", 0);  

	    	         String joke=userInfo.getString("joke", "null");
	    	 	    	if(joke.equalsIgnoreCase("on")){
	    	 	    	//    userInfo.edit().putString("area", "xuhui").commit();  
	    	 	    		RadioButton radiobtn_jokeon_setting= (RadioButton)findViewById(R.id.radiobtn_jokeon_setting);
	    	 	    		radiobtn_jokeon_setting.setChecked(true);
	    	 	    	}
	    	 	    	if(joke.equalsIgnoreCase("off")){
	    	     	   // 	userInfo.edit().putString("area", "fengxian").commit();  
	    	 	    		RadioButton radiobtn_jokeoff_setting= (RadioButton)findViewById(R.id.radiobtn_jokeoff_setting);
	    	 	    		radiobtn_jokeoff_setting.setChecked(true);
	    	 	    	}

	    	 	    	final Toast toastjokeon=Toast.makeText(this, "笑话开启-已保存", Toast.LENGTH_SHORT);
	    	 	    	final Toast toastjokeoff=Toast.makeText(this, "笑话屏蔽-已保存", Toast.LENGTH_SHORT);

	    	 	    	RadioGroup rgrp_joke = (RadioGroup)this.findViewById(R.id.rgrp_joke);
	    	 	    	         //绑定一个匿名监听器
	    	 	    	rgrp_joke.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	 	    	             @Override
	    	 	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	    	 	    	                 // TODO Auto-generated method stub
	    	 	    	                 //获取变更后的选中项的ID
	    	 	    	            	 if(arg1==R.id.radiobtn_jokeon_setting){
	    	 	    	     	    	    userInfo.edit().putString("joke", "on").commit();  
	    	 	    	            		 toastjokeon.show();
	    	 	    	            	 }	    	            	
	    	 	    	            	if(arg1==R.id.radiobtn_jokeoff_setting){
	    	 		    	     	    	    userInfo.edit().putString("joke", "off").commit();  
	    	 		    	     	    	  toastjokeoff.show(); 
	    	 		    	            	 }

	    	 	    	             }
	    	 	    	         });
	}

	private void init_ViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.lay1_nearby, null);
		view2=inflater.inflate(R.layout.lay2_search, null);
		view3=inflater.inflate(R.layout.lay3_main, null);
		view4=inflater.inflate(R.layout.lay4_chat, null);
		view5=inflater.inflate(R.layout.lay5_setting, null);
	//	view4=inflater.inflate(R.layout.xml1,null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
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
		offset = (screenW / 5 - bmpW) / 2;// 计算偏移量
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
			case 0:	init_lay1();break;
			case 1: init_lay2();break;
			case 2: init_lay3();break;
			case 3: init_lay4();break;
			case 4: init_lay5();break;


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
	//	int two = one * 2;// 页卡1 -> 页卡3 偏移量
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
            	tv_tolay1.setTextColor(Color.BLUE);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLACK);
            	tv_tolay5.setTextColor(Color.BLACK);
        		}
        	if (arg0==1){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLUE);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLACK);
            	tv_tolay5.setTextColor(Color.BLACK);
            	}
        	if (arg0==2){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLUE);
            	tv_tolay4.setTextColor(Color.BLACK);
            	tv_tolay5.setTextColor(Color.BLACK);
            	_menu.findItem(R.id.newpost_chat).setVisible(false);
        		}
          	if (arg0==3){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLUE);
            	tv_tolay5.setTextColor(Color.BLACK);
            	_menu.findItem(R.id.newpost_chat).setVisible(true);
            	lay4joke();
        		}
          	if (arg0==4){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLACK);
            	tv_tolay5.setTextColor(Color.BLUE);
        		}
		}
    }
}
