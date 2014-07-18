package com.smarr.android.healthierlifestyle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class UserInfoUpdate extends Activity {

	private ViewFlipper flipper;
	private Button nextButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
		nextButton = (Button) findViewById(R.id.next_button);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					flipper.showNext();
				} else {
					nextActivity();
				}
			}

		});

		// get last login from server

		// display alert dialog welcoming user back and posting last login date

		// get user status
		// get users latest weight

		// have user update current image look

		// have user update desired image look

		// ask user to update weight goal 50-400lbs with one decimal allowed

	}

	private void nextActivity() {
		Intent intent = new Intent(this, UserStatusScreen.class);
		startActivity(intent);

	}

}
