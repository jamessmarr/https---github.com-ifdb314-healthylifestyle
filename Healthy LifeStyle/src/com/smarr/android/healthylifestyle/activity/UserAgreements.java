package com.smarr.android.healthylifestyle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;

public class UserAgreements extends Activity {

	private Button nextButton;
	private CheckBox aboutCheck, whyCheck, howWorksCheck, userObligationsCheck;
	private ViewFlipper agreementsView;
	public static final String APP_PREFERENCES = "app_preferences";


	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_agreements);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
		final SharedPreferences.Editor prefEditor = settings.edit();

		if (savedInstanceState == null) {
			
		}

		nextButton = (Button) findViewById(R.id.next_button);

		aboutCheck = (CheckBox) findViewById(R.id.check_box_aboutApp);
		whyCheck = (CheckBox) findViewById(R.id.check_box_why_its_free);
		howWorksCheck = (CheckBox) findViewById(R.id.check_box_how_this_works);
		userObligationsCheck = (CheckBox) findViewById(R.id.check_box_user_obligations);

		agreementsView = (ViewFlipper) findViewById(R.id.agreementsFlipper);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (aboutCheck.isChecked()
						&& agreementsView.getDisplayedChild() == 0) {
					agreementsView.showNext();
				} else if (whyCheck.isChecked()
						&& agreementsView.getDisplayedChild() == 1) {
					agreementsView.showNext();
				} else if (howWorksCheck.isChecked()
						&& agreementsView.getDisplayedChild() == 2) {
					agreementsView.showNext();
				} else if (userObligationsCheck.isChecked()
						&& agreementsView.getDisplayedChild() == 3) {
					prefEditor.putBoolean("doneAgreements", true);
					prefEditor.commit();
					nextActivity();
				} else {
					alert();
				}

			}
		});

	}

	private void nextActivity() {
		Intent userInfoUpdate = new Intent(this, UserInfoUpdate.class);
		startActivity(userInfoUpdate);
	}

	private void alert() {
		new AlertDialog.Builder(this)
				.setTitle("Attention")
				.setMessage(
						"Please agree to the terms of use before continuing")
				.setNeutralButton(R.string.ok, null).show();
	}

}
