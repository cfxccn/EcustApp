package com.usta.ecustapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usta.ecustapp.R;

public class SearchFragment extends Fragment{
	private static final String TAG = "TestFragment";  
    private String hello;// = "hello android";  
    private String defaultHello = "default value";  
  
    public static SearchFragment newInstance() {  
    	SearchFragment newFragment = new SearchFragment();  

        return newFragment;  
  
    }  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        Bundle args = getArguments();  
        hello = args != null ? args.getString("hello") : defaultHello;  
    }  
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.lay3_search, container, false);  

        return view;  
  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
    }  
  
}
