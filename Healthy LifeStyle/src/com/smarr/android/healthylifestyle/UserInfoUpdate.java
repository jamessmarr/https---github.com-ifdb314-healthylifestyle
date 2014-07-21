package com.smarr.android.healthylifestyle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

public class UserInfoUpdate extends Activity {

	private ViewFlipper flipper;
	private Button nextButton;
	private EditText weightCurrent, weightGoal;
	private RadioGroup currentBodyImage, desiredBodyImage; 
	
	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);
		
		
		

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
		nextButton = (Button) findViewById(R.id.next_button);
		weightCurrent = (EditText) findViewById(R.id.current_weight);
		weightGoal = (EditText) findViewById(R.id.weight_goal);
		currentBodyImage = (RadioGroup) findViewById(R.id.currentImage);
		desiredBodyImage = (RadioGroup) findViewById(R.id.desiredImage);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					flipper.showNext();
				} else {
					SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
							MODE_PRIVATE);
					SharedPreferences.Editor prefEditor = settings.edit();
					prefEditor.putString("current_weight", weightCurrent.getText().toString());
					prefEditor.putString("weight_goal", weightGoal.getText().toString());
					prefEditor.putInt("current_body_image", currentBodyImage.getCheckedRadioButtonId());
					prefEditor.putInt("desired_body_image", desiredBodyImage.getCheckedRadioButtonId());
					prefEditor.commit();
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
		Intent userStatus = new Intent(this, UserStatus.class);
		startActivity(userStatus);

	}

}
