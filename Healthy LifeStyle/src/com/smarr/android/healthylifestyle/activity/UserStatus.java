package com.smarr.android.healthylifestyle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

import com.smarr.android.healthylifestyle.R;

public class UserStatus extends Activity {

	private TextView currentWeight;

	private String current_weight, lastLogin;

	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_status);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();

		lastLogin = settings.getString("lastLogin", null);

		if (lastLogin == null) {
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String date = today.format("%Y/%m/%d");
			String lastLogin = date + " at " + today.format("%k:%M");
			prefEditor.putString("lastLogin", lastLogin);
			prefEditor.commit();
		} else {
			new AlertDialog.Builder(this).setTitle(getText(R.string.welcome_back))
					.setMessage(getText(R.string.last_login) + lastLogin)
					.setPositiveButton(R.string.ok, null).show();
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String lastLogin = today.monthDay +" "+ today.month + " " + today.year + " at " + today.format("%k:%M");
			prefEditor.putString("lastLogin", lastLogin);
			prefEditor.commit();
		}

		currentWeight = (TextView) findViewById(R.id.currentUserWeight);
		current_weight = settings.getString("current_weight", null);
		currentWeight.setText(current_weight);

		if (savedInstanceState == null) {

		}
	}

}
