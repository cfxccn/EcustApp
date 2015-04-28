package com.usta.ecustapp.activity;



import com.renn.rennsdk.RennClient;
import com.renn.rennsdk.RennResponse;
import com.renn.rennsdk.RennClient.LoginListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.usta.ecustapp.tencent.*;
import com.usta.ecustapp.util.ToastUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.usta.ecustapp.R;
import com.usta.ecustapp.service.AccountService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.renn.rennsdk.RennExecutor.CallBack;
import com.renn.rennsdk.exception.RennException;
import com.renn.rennsdk.param.GetUserParam;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountSetting extends ActionBarActivity  implements OnClickListener {
	private int index;
	Intent intent;
	LayoutInflater inflater;
	String userEmail;
	String userPwd;
	String userKey="x";
	String userName;

	 SharedPreferences userInfo;
	 String userLocalName;
	 String userLocalEmail;
	 private RennClient rennClient;
	 private static final String RRAPP_ID = "268681";
	 private static final String RRAPI_KEY = "ba8f6a1e7c1542698737fcb00c56b6c6";
	 private static final String RRSECRET_KEY = "ca7c9f3744d64c3a85068ad9df1895ef";
    JSONObject userRenrenJsonObject;
    String userRenrenString;
    public static QQAuth mQQAuth;
    private UserInfo mInfo;
	private Tencent mTencent;
    public static String mAppid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		rennClient = RennClient.getInstance(this);
		rennClient.init(RRAPP_ID, RRAPI_KEY, RRSECRET_KEY);
		rennClient
				.setScope("read_user_blog read_user_photo read_user_status read_user_album "
						+ "read_user_comment read_user_share publish_blog publish_share "
						+ "send_notification photo_upload status_update create_album "
						+ "publish_comment publish_feed");
		// rennClient
		// .setScope("read_user_blog read_user_photo read_user_status read_user_album "
		// + "read_user_comment publish_blog publish_share "
		// + "send_notification photo_upload status_update create_album "
		// + "publish_feed");
		// rennClient.setScope("read_user_blog read_user_status");
		rennClient.setTokenType("bearer");
        

		mQQAuth = QQAuth.createInstance("101109752", AccountSetting.this.getApplicationContext());
		mTencent = Tencent.createInstance("101109752", AccountSetting.this);

		setContentView(R.layout.account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  
        intent = getIntent();
        index=intent.getIntExtra("index", 0);

        initLocalUserInfo();
        isLocalLogin();
        initbtn();



    }
private void initLocalUserInfo() {
		// TODO Auto-generated method stub
		userInfo = getSharedPreferences("setting", 0);  
		userLocalName=userInfo.getString("username", "null");
		userLocalEmail=userInfo.getString("useremail", "null");
	}
private void initbtn() {
		// TODO Auto-generated method stub
		final EditText editTextUserEmail=(EditText)findViewById(R.id.editTextUserEmail);
		final EditText editTextUserPwd=(EditText)findViewById(R.id.editTextUserPwd);

		Button btn_Login=(Button)findViewById(R.id.btn_Login);
		btn_Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userEmail=editTextUserEmail.getText().toString();
				userPwd=editTextUserPwd.getText().toString();
				if(userEmail.equalsIgnoreCase("")||userPwd.equalsIgnoreCase("")){
					

			  	  	ToastUtil.showToastShort(getApplicationContext(), "请输入信息");
					return;
				}
				ToastUtil.showToastShort(getApplicationContext(), "正在登录");

				loginViaNewThread();
				
			}
		});
		Button btn_Reg=(Button)findViewById(R.id.btn_Reg);
		btn_Reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(AccountSetting.this, Register.class);
				startActivityForResult(intent, 0);

			}
		});
		Button btn_RenRenLogin=(Button)findViewById(R.id.btn_RenRenLogin);
		btn_RenRenLogin.setOnClickListener(this);
		Button btn_QQLogin=(Button)findViewById(R.id.btn_QQLogin);
		btn_QQLogin.setOnClickListener(this);

		Button btn_Logout=(Button)findViewById(R.id.btn_Logout);
		btn_Logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		  		userInfo = getSharedPreferences("setting", 0);  
		 	    userInfo.edit().putString("useremail", "null").commit();  
		 	    userInfo.edit().putString("userkey", "null").commit();  
		 	    userInfo.edit().putString("username", "null").commit();  
		 	    

		 	    
		 	    ToastUtil.showToastShort(getApplicationContext(), "注销成功");
		 	    
				Intent intent =new Intent();
				intent.putExtra("index", index);
				intent.setClass(AccountSetting.this, AccountSetting.class);
				startActivityForResult(intent, 0);
		 	   finish();

			}
		});
	}

private Handler loginSuccess =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		

  	  	ToastUtil.showToastShort(getApplicationContext(), "登录成功");
  		userInfo = getSharedPreferences("setting", 0);  
 	    userInfo.edit().putString("useremail", userEmail).commit();  
 	    userInfo.edit().putString("userkey", userKey).commit();  
 	    userInfo.edit().putString("username", userName).commit();  
		Intent intent =new Intent();
		intent.putExtra("index", index);
		intent.setClass(AccountSetting.this, AccountSetting.class);
		startActivityForResult(intent, 0);
 	   finish();
// 	    isLocalLogin();
//        initbtn();
//	       setResult(RESULT_OK, intent);  
//	       finish();  
		}
		};
		private Handler loginFailure =new Handler(){
			@Override
			//当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			

	  	  	
	  	  	ToastUtil.showToastShort(getApplicationContext(), "登录失败，帐号密码错误");

			}
			};
			private Handler loginFailureNet =new Handler(){
				@Override
				//当有消息发送出来的时候就执行Handler的这个方法
				public void handleMessage(Message msg){
				super.handleMessage(msg);
				

				
		  	  	ToastUtil.showToastShort(getApplicationContext(), "登录失败，请检查网络");

				}
				};

protected void loginViaNewThread() {
	// TODO Auto-generated method stub
	new Thread(new Runnable(){
	    @Override
	    public void run() {

	    	ToastUtil.showToastShort(getApplicationContext(), "正在登录");
	    	userKey= AccountService.login(userEmail, userPwd);
	 	    	if(!userKey.startsWith("-")){
	 				//登录成功
	 	    		userName=userKey.substring(0, userKey.indexOf(","));
	 	    		userKey=userKey.substring(userKey.indexOf(",")+1,userKey.length());
	 	    		loginSuccess.sendEmptyMessage(0);
	 	    		return;
	 	    	}
	 	    	else if(userKey.equals("-1")){
	 	    		loginFailure.sendEmptyMessage(0);
	 	    		return;
	 	    	}	 else if(userKey=="-2") {
	 	    		loginFailureNet.sendEmptyMessage(0);
	 	    		return;
	 	    	}	
	    	}
	}).start();
   	
}
private void isLocalLogin() {
	
	LinearLayout layoutUserInfo=(LinearLayout)findViewById(R.id.layoutUserInfo);   
	LinearLayout layoutUserLogin=(LinearLayout)findViewById(R.id.layoutUserLogin);   


if(!userLocalName.equalsIgnoreCase("null")&&!userLocalEmail.equalsIgnoreCase("null")){
	layoutUserInfo.setVisibility(View.VISIBLE);
	layoutUserLogin.setVisibility(View.GONE);
	
	EditText textViewUserName=(EditText)findViewById(R.id.tvUserNickName);
	EditText textViewUserEmail=(EditText)findViewById(R.id.tvUserEmail);
	textViewUserName.setText(userLocalName);
	textViewUserEmail.setText(userLocalEmail);
	textViewUserName.setCursorVisible(false);//隐藏光标 
	textViewUserName.setFocusable(false);//失去焦点 
	textViewUserName.setFocusableInTouchMode(false);//虚拟键盘隐藏 
	textViewUserEmail.setCursorVisible(false);//隐藏光标 
	textViewUserEmail.setFocusable(false);//失去焦点 
	textViewUserEmail.setFocusableInTouchMode(false);//虚拟键盘隐藏 
	
	
}else{
	layoutUserInfo.setVisibility(View.GONE);
	layoutUserLogin.setVisibility(View.VISIBLE);
	
}

	
	}
public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
	        setResult(RESULT_OK, intent);  
	        finish();  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    } 
    



@Override  
  public boolean onOptionsItemSelected(MenuItem item) {  
      switch(item.getItemId()){  

    case android.R.id.home:  
	        setResult(RESULT_OK, intent);  
	        finish();  	        
	        break;  	        
      }  
      return super.onOptionsItemSelected(item);  
  }
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.btn_RenRenLogin:
		rennClient.setLoginListener(new LoginListener() {
			@Override
			public void onLoginSuccess() {
				// TODO Auto-generated method stub

                GetUserParam param3 = new GetUserParam();
                param3.setUserId(rennClient.getUid());
                try {
                    rennClient.getRennService().sendAsynRequest(param3, new CallBack() {    
                        @Override
                        public void onSuccess(RennResponse response) {
                       //     Toast.makeText(AccountSetting.this, response.toString(), Toast.LENGTH_SHORT).show();  
                           // userRenrenString=response.toString();
                           try {
							userRenrenJsonObject= response.getResponseObject();
			                userName=userRenrenJsonObject.optString("name");
							userEmail=userRenrenJsonObject.optInt("id")+"@RenRen";
							userKey=userRenrenJsonObject.optInt("id")+"@RenRen";
			                 ToastUtil.showToastShort(AccountSetting.this, "欢迎登陆"+userName);
			                 sendRRQQInfoViaNewThread();
                           } catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
       	 	    		loginSuccess.sendEmptyMessage(0);

                      }
						@Override
						public void onFailed(String arg0, String arg1) {
							// TODO Auto-generated method stub

						}
                    });
                } catch (RennException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}

			@Override
			public void onLoginCanceled() {
			}
		});
		rennClient.login(this);
		break;
	case R.id.btn_QQLogin:
		onClickQQLogin();
		break;
		}
}  

private void onClickQQLogin() {
	if (!mQQAuth.isSessionValid()) {
		IUiListener listener = new BaseUiListener() {
			@Override
			protected void doComplete(JSONObject values) {
				userKey=values.optString("openid")+"@QQ";
				userEmail=values.optString("openid")+"@QQ";
				updateUserInfo();
			}
		};
		mTencent.login(this, "all", listener);
	} else {
		mQQAuth.logout(this);
		updateUserInfo();
	}
}

private void updateUserInfo() {
	if (mQQAuth != null && mQQAuth.isSessionValid()) {
		IUiListener listener = new IUiListener() {
			
			@Override
			public void onError(UiError e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(final Object response) {
				Message msg = new Message();
				msg.obj = response;
				msg.what = 0;
				mHandler.sendMessage(msg);
				new Thread(){

					@Override
					public void run() {
						JSONObject json = (JSONObject)response;
						if(json.has("figureurl")){
							Bitmap bitmap = null;
							try {
								bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
							} catch (JSONException e) {
								
							}
							Message msg = new Message();
							msg.obj = bitmap;
							msg.what = 1;
							mHandler.sendMessage(msg);
						}
					}
					
				}.start();
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		};
//		  MainActivity.mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
//                    Constants.HTTP_GET, requestListener, null);
		mInfo = new UserInfo(this, mQQAuth.getQQToken());
		mInfo.getUserInfo(listener);
		
		
	} else {

	}
}

Handler mHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
		if (msg.what == 0) {
			JSONObject response = (JSONObject) msg.obj;
			if (response.has("nickname")) {
				try {
					userName=response.getString("nickname");
					
					loginSuccess.sendEmptyMessage(0);
					sendRRQQInfoViaNewThread();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(msg.what == 1){
			Bitmap bitmap = (Bitmap)msg.obj;

		}
	}

};




private class BaseUiListener implements IUiListener {

	@Override
	public void onComplete(Object response) {
		Util.showResultDialog(AccountSetting.this, response.toString(), "登录成功");
		
		doComplete((JSONObject)response);
	}

	protected void doComplete(JSONObject values) {
	}

	@Override
	public void onError(UiError e) {
	//	Util.toastMessage(AccountSetting.this, "onError: " + e.errorDetail);
	//	Util.dismissDialog();
	}

	@Override
	public void onCancel() {
		//Util.toastMessage(AccountSetting.this, "onCancel: ");
	//	Util.dismissDialog();
	}
}




protected void sendRRQQInfoViaNewThread() {
	// TODO Auto-generated method stub
	new Thread(new Runnable(){
	    @Override
	    public void run() {
	    	AccountService.rrqqLogin(userName, userEmail);
	    }
	}).start();
   	
}

}

