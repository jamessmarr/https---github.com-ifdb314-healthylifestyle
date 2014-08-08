package com.smarr.android.healthylifestyle.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarr.android.healthylifestyle.R;

public class Notifications extends Fragment {

	public Notifications() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_notifications,
				container, false);

		return rootView;
	}
}
