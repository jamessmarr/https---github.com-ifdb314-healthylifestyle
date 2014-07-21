package com.smarr.android.healthylifestyle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class UserStatus extends Activity {
	
	private TextView currentWeight;
	
	private String current_weight;
	
	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_status);
		
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();

		currentWeight = (TextView) findViewById(R.id.currentUserWeight);
		current_weight = settings.getString("current_weight", null);
		currentWeight.setText(current_weight);
		
		if (savedInstanceState == null) {
			
		}
	}

	

}
