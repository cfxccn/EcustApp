package com.usta;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.usta.CircleLayout.OnItemClickListener;
import com.usta.CircleLayout.OnItemSelectedListener;

import android.R.integer;
import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
	private TextView tv_tolay1_main,tv_tolay2_main,tv_tolay3_main,tv_tolay4_main,tv_tolay5_main;
	private SharedPreferences userInfo;
	private static	String namespace="http://WebXml.com.cn/";
    final static  String serviceUrl = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";  
	private String tvdate="";
	private String tvtemp="";
	private String tvwind="";
	private String tvweather="";
	private String pic1="";
	private String pic2="";
	private int _pic1=0;
	private int _pic2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        init_ImageView();
		init_ViewPager();
		init_LayInstru();
		init_Weather();
		
    }
   private void init_Weather() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	getweatherdata("奉贤");
	    	 handler.sendEmptyMessage(0);
	    }
	}).start();
   	
	}

private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		TextView tvDate=(TextView)findViewById(R.id.tvDate);
		TextView tvTemp=(TextView)findViewById(R.id.tvTemp);
		TextView tvWind=(TextView)findViewById(R.id.tvWind);
		TextView tvWeather=(TextView)findViewById(R.id.tvWeath);
		tvDate.setText(tvdate);
		tvTemp.setText(tvtemp);
		tvWind.setText(tvwind);
		tvWeather.setText(tvweather);


		ImageView imgv1=(ImageView)findViewById(R.id.imgv1);
		ImageView imgv2=(ImageView)findViewById(R.id.imgv2);
		int x=pic1.lastIndexOf(".");
		String fname1=pic1.substring(0,x);
		int y=pic2.lastIndexOf(".");
		String fname2=pic2.substring(0,y);
		_pic1=Integer.parseInt(fname1);
		_pic2=Integer.parseInt(fname2);

		imgv1.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic1));
		imgv2.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic2));

		
		}
		};

		protected void getweatherdata(String theCityCode) {

			String methodname ="getWeather"; 
			String soapaction=namespace+methodname;
			SoapObject request = new SoapObject(namespace, methodname);
	        request.addProperty("theCityCode",theCityCode);
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;//是否是dotNet WebService  
	        envelope.bodyOut=request;
	        // AndroidHttpTransport ht=new  AndroidHttpTransport(serviceUrl);
            HttpTransportSE ht = new HttpTransportSE(serviceUrl);  
	        ht.debug = true;
            try  
            {   
            	//System.out.println(soapaction);
                // 第5步：调用WebService  
                ht.call(soapaction, envelope);  
            //	System.out.println("ht.call" ); 
                if (envelope.getResponse() != null)  
                {  
                    // 第6步：使用getResponse方法获得WebService方法的返回结果  
                	SoapObject  soapObject = (SoapObject ) envelope.getResponse();
//                	SoapObject result = (SoapObject) envelope.bodyIn;  
//                	SoapObject detail = (SoapObject) result.getProperty(0);                  	
                    tvdate=   soapObject.getProperty(7).toString();  
                    tvtemp=   soapObject.getProperty(8).toString();  
                    tvwind=   soapObject.getProperty(9).toString();  
                    tvweather=soapObject.getProperty(4).toString(); 
                    pic1=soapObject.getProperty(10).toString(); 
                    pic2=soapObject.getProperty(11).toString(); 
                    
                }  
                else {  
                }  
            }  
            catch (Exception e)  
            {  
            	//tvweather=e.toString();
            }  


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
    	tv_tolay4_main = (TextView) findViewById(R.id.tv_tolay4_main);
    	tv_tolay5_main = (TextView) findViewById(R.id.tv_tolay5_main);
    	
    	tv_tolay3_main.setBackgroundColor(Color.GRAY);
    	
    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
    	Animation animation = new TranslateAnimation(one*index, one*index, 0, 0);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		imageView.startAnimation(animation);
    	
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
    	tv_tolay4_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{        	
				index=3;
				viewPager.setCurrentItem(3);
			}
		});
    	tv_tolay5_main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{        	
				index=4;
				viewPager.setCurrentItem(4);
			}
		});
    }
	private void init_lay1()
	{
	}
	private void init_lay2()
	{
	}

	private void init_lay3()
	{
//		Button btn_tosearchdrugs_lay1 = (Button) findViewById(R.id.btn_tosearchdrugs_lay1);
//		btn_tosearchdrugs_lay1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v)
//			{
//			//	 setContentView(R.layout.searchdrugs);
//				Intent intent =new Intent();
//				intent.putExtra("index", index);
//				intent.setClass(MainActivity.this, SearchDrugs.class);
//				startActivityForResult(intent, 0);
//		//		MainActivity.this.finish();
//			}
//		});
		Button btn_tosearchbooks_lay1 = (Button) findViewById(R.id.btn_tosearchbooks_main);
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
		Button btn_tosearchclassroom_lay1 = (Button) findViewById(R.id.btn_tosearchclassroom_main);
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
		Button btn_schoolbus_lay3=(Button)findViewById(R.id.button5);
		btn_schoolbus_lay3.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, schoolbus.class);
				startActivityForResult(intent, 0);
			}
	});
//		Button btn_tolinknet_lay1 = (Button) findViewById(R.id.btn_tolinknet_lay1);
//		btn_tolinknet_lay1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent =new Intent();
//				intent.putExtra("index", index);
//				intent.setClass(MainActivity.this, Linknet.class);
//				startActivityForResult(intent, 0);
//			}
//		});
		Button btn_tomorningtrain_lay1 = (Button) findViewById(R.id.btn_tomorningtrain_main);
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

	private void init_lay4()
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
	private void init_lay5()
	{
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
	    	final Toast toas1=Toast.makeText(this, "徐汇校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toas2=Toast.makeText(this, "奉贤校区-已保存", Toast.LENGTH_SHORT);
	    	final Toast toas3=Toast.makeText(this, "金山校区-已保存", Toast.LENGTH_SHORT);

	    	RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup_area_setting);
	    	         //绑定一个匿名监听器
	    	         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	             @Override
	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	    	                 // TODO Auto-generated method stub
	    	                 //获取变更后的选中项的ID
	    	            	 if(arg1==R.id.radiobtn_xuhui_setting){
	    	     	    	    userInfo.edit().putString("area", "xuhui").commit();  
	    	            		 toas1.show();
	    	            	 }	    	            	
	    	            	if(arg1==R.id.radiobtn_fengxian_setting){
		    	     	    	    userInfo.edit().putString("area", "fengxian").commit();  
		    	            		 toas2.show(); 
		    	            	 }
	    	            	 if(arg1==R.id.radiobtn_jinshan_setting){
	    	     	    	    userInfo.edit().putString("area", "jinshan").commit();  
	    	            		 toas3.show();
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
        		tv_tolay1_main.setBackgroundColor(Color.GRAY);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		tv_tolay4_main.setBackgroundColor(Color.WHITE);
        		tv_tolay5_main.setBackgroundColor(Color.WHITE);
        		}
        	if (arg0==1){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.GRAY);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		tv_tolay4_main.setBackgroundColor(Color.WHITE);
        		tv_tolay5_main.setBackgroundColor(Color.WHITE);
        		}
        	if (arg0==2){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.GRAY);
        		tv_tolay4_main.setBackgroundColor(Color.WHITE);
        		tv_tolay5_main.setBackgroundColor(Color.WHITE);
        		}
          	if (arg0==3){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		tv_tolay4_main.setBackgroundColor(Color.GRAY);
        		tv_tolay5_main.setBackgroundColor(Color.WHITE);
        		}
          	if (arg0==4){ 
        		tv_tolay1_main.setBackgroundColor(Color.WHITE);
        		tv_tolay2_main.setBackgroundColor(Color.WHITE);
        		tv_tolay3_main.setBackgroundColor(Color.WHITE);
        		tv_tolay4_main.setBackgroundColor(Color.WHITE);
        		tv_tolay5_main.setBackgroundColor(Color.GRAY);
        		}
		}
    }
}
