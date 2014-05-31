package com.usta.activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.usta.R;
import com.usta.control.ListViewOnScrollView;
import com.usta.network.Nearby;
import com.usta.network.News;
import com.usta.network.Weather;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends SherlockActivity   {
    private int index =1;
	private ViewPager viewPager;//页卡内容
//	private ImageView imageView;// 动画图片
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3,view4;//各个页卡
	private ImageView iv_tolay1_main,iv_tolay2_main,iv_tolay3_main,iv_tolay4_main,iv_tolay5_main;
	private TextView  tv_tolay1,tv_tolay2,tv_tolay3,tv_tolay4, tv_tolay5;
	
	private static String lay3data[]={"","教师"};
	
	private SharedPreferences userInfo;
	private String tvdate1="";
	private String tvdate2="";

	private String pic11="";
	private String pic12="";
	private String pic21="";
	private String pic22="";
	private String air_aqi="实时空气质量：";
	private String air_advise="户外活动建议：";
	Intent intent;
	private int aqi;
	Menu _menu;

	private int _pic11=0;
	private int _pic12=0;
	private int _pic21=0;
	private int _pic22=0;
	private String newsid="";
	JSONArray  newsJsonArray;
	List<JSONObject>NewsInfos;
  //  List<String> newsinfo;
	ListView listView_news ;
    ListView listView_nearby ;
    ConnectivityManager manger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 //       init_ImageView();
		init_ViewPager();
		init_LayInstru();

       

		  manger = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE); 
    	  get_Weather();
    	  get_News();
          }
    private void get_News() {
    	new Thread(new Runnable(){
    	    @Override
    	    public void run() {
    	    	try {
    		    	 newsJsonArray=News.getNewsTitles();
    	    	if(newsJsonArray!=null){
    		    	newshandler.sendEmptyMessage(0);
    	    	}else{
    	    		return;
    	    	}
    	    	} catch (Exception e) {
    				// TODO: handle exception
    				e.printStackTrace();
      	    		return;
    			}
    	    
    	    }
    	}).start();
       	
    	}

    private Handler newshandler =new Handler(){
    		@Override
    		//当有消息发送出来的时候就执行Handler的这个方法
    		public void handleMessage(Message msg){
    		super.handleMessage(msg);

    		init_newslist();

    		}
    		};
    		
	public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.main, menu);
    	menu.findItem(R.id.view_inmap).setVisible(false);
    	_menu=menu;
        return true;
    }
    protected void init_newslist() {
TextView tvTextView=(TextView)findViewById(R.id.textView1);
tvTextView.setVisibility(View.GONE);
		listView_news = (ListView) findViewById(R.id.listView_news);
  	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
      
  	  for(int i=0;i<newsJsonArray.length();i++)
      {
  		  JSONObject eachnewstitleJsonObject=newsJsonArray.optJSONObject(i);
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("textView_newsid",eachnewstitleJsonObject.optInt("id"));
          map.put("textView_News_title",eachnewstitleJsonObject.optString("newstitle").trim());
          map.put("textView_News_releasetime",eachnewstitleJsonObject.optString("newsrelease").trim());
          map.put("textView_News_source",eachnewstitleJsonObject.optString("newssource").trim());
          listItem.add(map);
      }
		// TODO Auto-generated method stub
	 SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
	            R.layout.newstitle_listview,//ListItem的XML实现
	            //动态数组与ImageItem对应的子项        
	            new String[] {"textView_newsid","textView_News_title", "textView_News_releasetime","textView_News_source"}, 
	            //ImageItem的XML文件里面的一个ImageView,两个TextView ID
	            new int[] {R.id.textView_newsid,R.id.textView_News_title,R.id.textView_News_releasetime,R.id.textView_News_source}
	        );
	 
	 listView_news.setAdapter(listItemAdapter);
	 listView_news.setOnItemClickListener(new OnItemClickListener() {  
		  
         @Override  
         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                 long arg3) {  
        	 ListView listView = (ListView)arg0;  
                 HashMap<String, Object> map = (HashMap<String, Object>) listView.getItemAtPosition(arg2);  
                 newsid= String.valueOf(map.get("textView_newsid"));  
 				Intent intent =new Intent();
 				intent.putExtra("index", index);
 				intent.putExtra("newsid", newsid);
				intent.setClass(MainActivity.this, NewsDetail.class);
				startActivityForResult(intent, 0);
         }  
     }); 
	 
//	 Button btnMoreNews = (Button) findViewById(R.id.btnMoreNews);
//	 btnMoreNews.setVisibility(View.VISIBLE);
//	 btnMoreNews.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			Intent intent =new Intent();
//			intent.putExtra("index", index);
//			intent.setClass(MainActivity.this, SearchClassroom.class);
//			startActivityForResult(intent, 0);
//		}
//	});
    	
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
        case R.id.view_inmap: 
        	 manger = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE); 
        	NetworkInfo info = manger.getActiveNetworkInfo(); 
            if (info!=null && info.isConnected()&&nearbys!=null)
            {

    			intent =new Intent();
    			intent.putExtra("index", index);
    			intent.putExtra("nearbys",nearbys.toString());
    			intent.setClass(MainActivity.this, NearbyViewInMap.class);
    			startActivityForResult(intent, 0);
            }
            else 
            {
          	  Toast Toast1=Toast.makeText(this,"无信息", Toast.LENGTH_SHORT);
          	  Toast1.show();
            }
			
            break;
        }
        return true;
    }
    
    
    private void get_Weather() {
	   
		// TODO Auto-generated method stub
   	new Thread(new Runnable(){
	    @Override
	    public void run() {
	  	JSONObject  weatherJsonObject=Weather.getWeatherDetails();
	    		if(weatherJsonObject!=null){

					 tvdate1=  weatherJsonObject.optString("h12")+" "+weatherJsonObject.optString("h12temp");
			         tvdate2=   weatherJsonObject.optString("h24")+" "+weatherJsonObject.optString("h24temp");
			         pic11=weatherJsonObject.optString("h12img1");
			         pic12=weatherJsonObject.optString("h12img2");
			         pic21=weatherJsonObject.optString("h24img1");
			         pic22=weatherJsonObject.optString("h24img2");
			         aqi=weatherJsonObject.optInt("pm25");
			         
			         air_aqi="实时空气质量："+weatherJsonObject.optString("aqi")+"   PM2.5指数："+Integer.toString(aqi);
			         air_advise="户外活动建议：";
		    		if(aqi<80)
	    	    	{air_advise=air_advise+"自由活动不受影响";
	    	    	}else
	    	    		if(aqi<120)
	    	    		{air_advise=air_advise+"尽量减少";
	    	    		}else
	    	    			{air_advise=air_advise+"完全停止";
	    	    			}
			    	 weatherhandler.sendEmptyMessage(0);
	    			
	    		}else{
	    			
	    			return;
	    		}

	    
	    }
	}).start();
   	
	}

public Handler weatherhandler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		init_weather();
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
public void init_weather() {
			// TODO Auto-generated method stub
	LinearLayout layoutWeather=(LinearLayout)findViewById(R.id.layoutWeather);   

	layoutWeather.setVisibility(View.VISIBLE);
	TextView tvWeather=(TextView)findViewById(R.id.tvWeath);
	TextView tvAdvise=(TextView)findViewById(R.id.tvAdvise);
	TextView tv12=(TextView)findViewById(R.id.tv12);
	TextView tv24=(TextView)findViewById(R.id.tv24);
	tv12.setText("12时内 "+tvdate1);
	tv24.setText("24时内 "+tvdate2);


	ImageView imgv11=(ImageView)findViewById(R.id.imgv11);
	ImageView imgv12=(ImageView)findViewById(R.id.imgv12);	
	ImageView imgv21=(ImageView)findViewById(R.id.imgv21);
	ImageView imgv22=(ImageView)findViewById(R.id.imgv22);
	
	tvWeather.setText(air_aqi);
	tvAdvise.setText(air_advise);
	_pic11=Integer.parseInt(pic11);
	_pic12=Integer.parseInt(pic12);
	_pic21=Integer.parseInt(pic21);
	_pic22=Integer.parseInt(pic22);

	imgv11.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic11));
	imgv12.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic12));
	imgv21.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic21));
	imgv22.setImageDrawable(getResources().getDrawable(R.drawable.a00+_pic22));
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
	case R.id.imageView_Home: 
		index=1;
	viewPager.setCurrentItem(1); break;
	case R.id.tvlay3: ;
	case R.id.imageView_Search: 
		index=2;
	viewPager.setCurrentItem(2); break;
	case R.id.tvlay4: ;
	case R.id.imageView_Setting: 
		index=3;
	viewPager.setCurrentItem(3); break;
	case R.id.tvlay5: ;
	case R.id.imageView_Post: 
		Intent intent =new Intent();
		intent.putExtra("index", index);
		intent.setClass(MainActivity.this, PostTitleView.class);
		startActivityForResult(intent, 0);
		; break;

	}
	
	
} 
};
protected String nearbyid; 
protected String nearbytype; 


    private void init_LayInstru() {
    	iv_tolay1_main = (ImageView) findViewById(R.id.imageView_Nearby);
    	iv_tolay2_main = (ImageView) findViewById(R.id.imageView_Home);
    	iv_tolay3_main = (ImageView) findViewById(R.id.imageView_Search);
    	iv_tolay4_main = (ImageView) findViewById(R.id.imageView_Setting);
    	iv_tolay5_main = (ImageView) findViewById(R.id.imageView_Post);

    	tv_tolay1 = (TextView) findViewById(R.id.tvlay1);
    	tv_tolay2 = (TextView) findViewById(R.id.tvlay2);
    	tv_tolay3 = (TextView) findViewById(R.id.tvlay3);
    	tv_tolay4 = (TextView) findViewById(R.id.tvlay4);
    	tv_tolay5 = (TextView) findViewById(R.id.tvlay5);

    	
    	tv_tolay2.setTextColor(Color.BLUE);
    //	tv_tolay3_main.setBackgroundColor(Color.WHITE);
    	
    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		
    	Animation animation = new TranslateAnimation(one*index, one*index, 0, 0);
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
	//	imageView.startAnimation(animation);
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
	JSONArray nearbys ;
	
	
	private void init_lay1()
	{
		init_spiner();
	}
	String _type;
	private void getNearbyDataViaNewThread() {
		// TODO Auto-generated method stub
	   	new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	try {
		    		nearbys=Nearby.getNearbyTitles(_type,Integer.MAX_VALUE);
			    	 if(nearbys!=null)nearby_handler.sendEmptyMessage(0);
			    	 else 	
			    		 return;
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
		    }
		}).start();
	}
	private Handler nearby_handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		init_NearbyList();
		}
		};
		
	private void init_NearbyList() {
		listView_nearby = (ListView) findViewById(R.id.listView_nearby);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		
		JSONObject nearby=new JSONObject();
		//System.out.print( nearbys.length());
		try{
		for(int i=0;i<nearbys.length();i++)
		{
			nearby=nearbys.getJSONObject(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("no",1+i);
			map.put("title",nearby.getString("name"));
			map.put("location",nearby.getString("location"));
			map.put("introduction",nearby.getString("introduction"));
			map.put("id",nearby.getInt("id"));
			map.put("type",nearby.getString("type"));
			listItem.add(map);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			// TODO Auto-generated method stub
		 final SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源 
		            R.layout.nearby_listview,//ListItem的XML实现
		            //动态数组与ImageItem对应的子项        
		            new String[] {"no","title","location", "introduction"}, 
		            new int[] {R.id.textView_NearbyNo_lv,R.id.textView_NearbyTitle_lv,R.id.textView_NearbyLocation_lv,R.id.textView_NearbyIntro_lv}
		        );
		 listView_nearby.setAdapter(listItemAdapter);
		 listView_nearby.setOnItemClickListener(new OnItemClickListener() {  
			  
	         @Override  
	         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                 long arg3) {  
	                HashMap<String, Object> map = (HashMap<String, Object>) listView_nearby.getItemAtPosition(arg2);  
	                nearbyid= map.get("id").toString();
	                nearbytype= map.get("type").toString();   
	 				Intent intent =new Intent();
	 				intent.putExtra("index", index);
	 				intent.putExtra("nearbyid", nearbyid);
	 				intent.putExtra("nearbytype", nearbytype);
	 				intent.setClass(MainActivity.this, NearbyDetail.class);
	 				startActivityForResult(intent, 0);

	         }  
	     }); 
		
	}
	private void init_spiner() {
		 Spinner spr_type_nearby=(Spinner)findViewById(R.id.spr_type_nearby);
		 ArrayList<String> type=new ArrayList<String>();
		 type.add("请选择分类");
		 type.add("吃");
		 type.add("住");
		 type.add("行");
		 type.add("玩");
		 type.add("其他");			 
		 ArrayAdapter<String> aspnCountries =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
		 aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spr_type_nearby.setAdapter(aspnCountries);
		 spr_type_nearby.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				switch(arg2){
				
				case 0:				break;
				case 1:				
					_type="吃";
					getNearbyDataViaNewThread();
					break;
				case 2:
					_type="住";
					getNearbyDataViaNewThread();
					break;
				case 3:	
					_type="行";
					getNearbyDataViaNewThread();
					break;
				case 4:
					_type="玩";
					getNearbyDataViaNewThread();
					break;
				case 5:
					_type="其他";
					getNearbyDataViaNewThread();break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

    private final static int DIALOG=1;

	private void init_lay2()
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
		ImageView imageView_Lecture = (ImageView) findViewById(R.id.imageView_Lecture);
		imageView_Lecture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, SearchLecture.class);
				startActivityForResult(intent, 0);
			}
		});
		
		ImageView imageView_Map = (ImageView) findViewById(R.id.imageView_Map);
		imageView_Map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Map.class);
				startActivityForResult(intent, 0);
			}
		});

		ImageView ImageView_Searchbook = (ImageView) findViewById(R.id.imageView_Searchbook);
		ImageView_Searchbook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
//				Intent intent =new Intent();
//				intent.putExtra("index", index);
//				intent.setClass(MainActivity.this, SearchBooks.class);
//				startActivityForResult(intent, 0);
				showLibDialog();
			}
		});
//		ImageView imageView_Morningtrain = (ImageView) findViewById(R.id.imageView_Morningtrain);
//		imageView_Morningtrain.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent =new Intent();
//				intent.putExtra("index", index);
//				intent.setClass(MainActivity.this, Morningtrain.class);
//				startActivityForResult(intent, 0);
//			}
//		});
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
		
		ImageView imageView_Powerfare=(ImageView)findViewById(R.id.imageView_Powerfare);
		imageView_Powerfare.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v)
			{
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, Powerfare.class);
				startActivityForResult(intent, 0);
			}
		});

	}
	protected void showLibDialog() {
		final Context context = this;  
	    //定义列表选项  
	    String[] items = {"校图书馆馆藏图书查询","校图书馆实时座位信息"};  
	    //创建对话框  
	    new AlertDialog.Builder(context)  
	    .setTitle("请选择...")//设置对话框标题  
	    .setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(which==0){
					Intent intent =new Intent();
					intent.putExtra("index", index);
					intent.putExtra("todo", which);
					intent.setClass(MainActivity.this, SearchBooks.class);
					startActivityForResult(intent, 0);
				}else if(which==1){
					Intent intent =new Intent();
					intent.putExtra("index", index);
					intent.putExtra("todo", which);
					intent.setClass(MainActivity.this, SearchBooks.class);
					startActivityForResult(intent, 0);
				}
				
			}
		})
		.setNegativeButton("取消", null)
	    .show();  
	}
	public static String[] putlay3sdata()
	{
		return lay3data;
	}
	private void init_lay3()
	{
		RadioGroup lay3_s_rg =(RadioGroup)findViewById(R.id.lay3_s_rg);  
		lay3_s_rg.setOnCheckedChangeListener(new OnCheckedChangeListener(){  
		  
		    @Override  
		    public void onCheckedChanged(RadioGroup group, int checkedId) {  
		    // TODO Auto-generated method stub  
		    RadioButton r = (RadioButton)findViewById(checkedId);  
		    lay3data[1]= r.getText().toString();//获取被选中的单选按钮的值  
		    }         
		});
		

		final EditText lay3_s_ev1=(EditText)findViewById(R.id.lay3_s_et1);
		
		final RadioButton radioButton = (RadioButton)findViewById(lay3_s_rg.getCheckedRadioButtonId());
		
		ImageView imageView_Morningtrain = (ImageView) findViewById(R.id.lay3_s_ib1);
		imageView_Morningtrain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				
				lay3data[0]=lay3_s_ev1.getText().toString();
				if(lay3data[0].equals(""))
				{
			    Toast toast = Toast.makeText(MainActivity.this, "请输入信息", Toast.LENGTH_SHORT);                    
		        toast.show(); 
				}
				
				else
				{
				//lay3data[1]=radioButton.getText().toString();
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(MainActivity.this, search.class);
				startActivityForResult(intent, 0);
				}
			}
		});
	}
	private void joke()
	{
		userInfo = getSharedPreferences("setting", 0);  
		String joke=userInfo.getString("joke", "null");
	    	if(!joke.equalsIgnoreCase("off")){
	    		Toast toastJoke=Toast.makeText(this, "joke", Toast.LENGTH_LONG);
	    		toastJoke.show();
	    	}
	}
	private void init_lay4()
	{
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
//	    	String area=userInfo.getString("area", "null");
//	    	if(area.equalsIgnoreCase("xuhui")){
//	    		RadioButton radiobtn_xuhui_setting= (RadioButton)findViewById(R.id.radiobtn_xuhui_setting);
//	    		radiobtn_xuhui_setting.setChecked(true);
//	    	}
//	    	if(area.equalsIgnoreCase("fengxian")){
//	    		RadioButton radiobtn_fengxian_setting= (RadioButton)findViewById(R.id.radiobtn_fengxian_setting);
//	    		radiobtn_fengxian_setting.setChecked(true);
//	    	}
//	    	if(area.equalsIgnoreCase("jinshan")){
//	    		RadioButton radiobtn_jinshan_setting= (RadioButton)findViewById(R.id.radiobtn_jinshan_setting);
//	    		radiobtn_jinshan_setting.setChecked(true);
//	    	}
//	    	final Toast toastxuhui=Toast.makeText(this, "徐汇校区-已保存", Toast.LENGTH_SHORT);
//	    	final Toast toastfengxian=Toast.makeText(this, "奉贤校区-已保存", Toast.LENGTH_SHORT);
//	    	final Toast toastjinshan=Toast.makeText(this, "金山校区-已保存", Toast.LENGTH_SHORT);
//
//	    	RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup_area_setting);
//	    	         //绑定一个匿名监听器
//	    	         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//	    	             @Override
//	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
//	    	                 // TODO Auto-generated method stub
//	    	                 //获取变更后的选中项的ID
//	    	            	 if(arg1==R.id.radiobtn_xuhui_setting){
//	    	     	    	    userInfo.edit().putString("area", "xuhui").commit();  
//	    	     	    	   toastxuhui.show();
//	    	            	 }	    	            	
//	    	            	if(arg1==R.id.radiobtn_fengxian_setting){
//		    	     	    	    userInfo.edit().putString("area", "fengxian").commit();  
//		    	     	    	   toastfengxian.show(); 
//		    	            	 }
//	    	            	 if(arg1==R.id.radiobtn_jinshan_setting){
//	    	     	    	    userInfo.edit().putString("area", "jinshan").commit();  
//	    	     	    	   toastjinshan.show();
//	    	            	 }
//
//	    	             }
//	    	         });
//
	    	         String anony=userInfo.getString("anony", "null");
	    	 	    	if(anony.equalsIgnoreCase("on")){
	    	 	    		RadioButton radiobtn_anonyon_setting= (RadioButton)findViewById(R.id.radiobtn_anonyon_setting);
	    	 	    		radiobtn_anonyon_setting.setChecked(true);
	    	 	    	}
	    	 	    	if(anony.equalsIgnoreCase("off")){
	    	 	    		RadioButton radiobtn_anonyoff_setting= (RadioButton)findViewById(R.id.radiobtn_anonyoff_setting);
	    	 	    		radiobtn_anonyoff_setting.setChecked(true);
	    	 	    	}

	    	 	    	final Toast toastanonyon=Toast.makeText(this, "匿名-已保存", Toast.LENGTH_SHORT);
	    	 	    	final Toast toastanonyoff=Toast.makeText(this, "不匿名-已保存", Toast.LENGTH_SHORT);

	    	 	    	RadioGroup rgrp_anony = (RadioGroup)this.findViewById(R.id.rgrp_anony);    //绑定一个匿名监听器
	    	 	    	rgrp_anony.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    	 	    	             @Override
	    	 	    	             public void onCheckedChanged(RadioGroup arg0, int arg1) {
	    	 	    	                 // TODO Auto-generated method stub
	    	 	    	                 //获取变更后的选中项的ID
	    	 	    	            	 if(arg1==R.id.radiobtn_anonyon_setting){
	    	 	    	     	    	    userInfo.edit().putString("anony", "on").commit();  
	    	 	    	     	    	  toastanonyon.show();
	    	 	    	            	 }	    	            	
	    	 	    	            	if(arg1==R.id.radiobtn_anonyoff_setting){
	    	 		    	     	    	    userInfo.edit().putString("anony", "off").commit();  
	    	 		    	     	    	  toastanonyoff.show(); 
	    	 		    	            	 }

	    	 	    	             }
	    	 	    	         });
	    	 			Button btn_toabout_setting=(Button)findViewById(R.id.btn_toabout_setting);
	    	 			btn_toabout_setting.setOnClickListener(new OnClickListener() {
	    	 				@Override
	    	 				public void onClick(View v)
	    	 				{
	    	 					Intent intent =new Intent();
	    	 					intent.putExtra("index", index);
	    	 					intent.setClass(MainActivity.this, About.class);
	    	 					startActivityForResult(intent, 0);
	    	 				}
	    	 			});
//	    	 			Button btn_torec_setting=(Button)findViewById(R.id.btn_torec_setting);
//	    	 			btn_torec_setting.setOnClickListener(new OnClickListener() {
//	    	 				@Override
//	    	 				public void onClick(View v)
//	    	 				{
//	    	 					Intent intent =new Intent();
//	    	 					intent.putExtra("index", index);
//	    	 					intent.setClass(MainActivity.this, Recommend.class);
//	    	 					startActivityForResult(intent, 0);
//	    	 				}
//	    	 			});
	    	 			Button btn_toaccount=(Button)findViewById(R.id.btn_toaccount);
	    	 			btn_toaccount.setOnClickListener(new OnClickListener() {
	    	 				@Override
	    	 				public void onClick(View v)
	    	 				{
	    	 					Intent intent =new Intent();
	    	 					intent.putExtra("index", index);
	    	 					intent.setClass(MainActivity.this, AccountSetting.class);
	    	 					startActivityForResult(intent, 0);
	    	 				}
	    	 			});
	    	 			
	}

	private void init_ViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.lay1_nearby, null);
		view2=inflater.inflate(R.layout.lay2_main, null);
		view3=inflater.inflate(R.layout.lay3_search, null);
		view4=inflater.inflate(R.layout.lay4_setting, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(index);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
//	private void init_ImageView() {
//		imageView= (ImageView) findViewById(R.id.cursor);
//		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenW = dm.widthPixels;// 获取分辨率宽度
//		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(offset, 0);
//		imageView.setImageMatrix(matrix);// 设置动画初始位置
//	}

	
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
			case 0:	break;
			case 1: init_lay2();break;
			case 2:break;
			case 3: break;
			case 4: break;


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
		//	imageView.startAnimation(animation);

        	if (arg0==0){ 
        		init_lay1();
            	tv_tolay1.setTextColor(Color.BLUE);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLACK);
            	_menu.findItem(R.id.view_inmap).setVisible(true);
            	init_lay1();

        		}
        	if (arg0==1){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLUE);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLACK);
            	_menu.findItem(R.id.view_inmap).setVisible(false);

            	init_lay2();
            	}
        	if (arg0==2){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLUE);
            	tv_tolay4.setTextColor(Color.BLACK);
            	_menu.findItem(R.id.view_inmap).setVisible(false);
            	init_lay3();

        		}
          	if (arg0==3){ 
            	tv_tolay1.setTextColor(Color.BLACK);
            	tv_tolay2.setTextColor(Color.BLACK);
            	tv_tolay3.setTextColor(Color.BLACK);
            	tv_tolay4.setTextColor(Color.BLUE);
            	_menu.findItem(R.id.view_inmap).setVisible(false);
            	init_lay4();
        		}

		}
    }
}
