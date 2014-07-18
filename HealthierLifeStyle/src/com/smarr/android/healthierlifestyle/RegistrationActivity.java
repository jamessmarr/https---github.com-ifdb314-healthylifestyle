package com.smarr.android.healthierlifestyle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		if (savedInstanceState == null) {
			
		}
		Toast.makeText(this, "it Worked", Toast.LENGTH_SHORT).show();
	}

	

}
