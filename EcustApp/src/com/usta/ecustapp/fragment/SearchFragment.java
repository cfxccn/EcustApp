package com.usta.ecustapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usta.ecustapp.R;

public class SearchFragment extends Fragment{
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
    }  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.lay3_search, container, false);  
        return view;  
    }  
}
