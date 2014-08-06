package com.smarr.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarr.android.healthylifestyle.R;

public class MyStatus extends Fragment {
	
	
	
	
	public MyStatus(){
		
		
		
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_mystatus, container, false);
         
        return rootView;
    }
	
	
}
