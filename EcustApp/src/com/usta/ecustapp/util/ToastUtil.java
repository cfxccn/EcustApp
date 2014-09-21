package com.usta.ecustapp.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	  
	    public static void showToastLong(Context context,String msg) {  
	        showToast(context, msg, Toast.LENGTH_LONG);  
	    }  
	  
	    public static void showToastShort(Context context,String msg) {  
	        showToast(context, msg, Toast.LENGTH_SHORT);  
	    }  
	  
	  
	    public static void showToast(Context context, String msg, int duration) {  
	        Toast.makeText(context, msg, duration).show();  
	    }  
}
