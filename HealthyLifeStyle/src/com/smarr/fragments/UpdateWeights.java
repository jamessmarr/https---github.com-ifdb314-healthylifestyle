package com.smarr.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarr.android.healthylifestyle.R;

 public class UpdateWeights extends Fragment {
	
	public UpdateWeights(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_update_weights, container, false);
         
        return rootView;
    }
}
