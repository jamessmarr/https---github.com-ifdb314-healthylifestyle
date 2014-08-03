package com.smarr.android.healthylifestyle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.smarr.android.healthylifestyle.R;

public class UserAgreements extends Activity {

	private Button nextButton;
	private CheckBox aboutCheck, whyCheck, howWorksCheck, userObligationsCheck;
	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_agreements);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		final SharedPreferences.Editor prefEditor = settings.edit();

		if (savedInstanceState == null) {

		}

		nextButton = (Button) findViewById(R.id.next_button);

		aboutCheck = (CheckBox) findViewById(R.id.check_box_aboutApp);
		whyCheck = (CheckBox) findViewById(R.id.check_box_why_its_free);
		howWorksCheck = (CheckBox) findViewById(R.id.check_box_how_this_works);
		userObligationsCheck = (CheckBox) findViewById(R.id.check_box_user_obligations);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (aboutCheck.isChecked()) {
					aboutCheck.setError(null);
					if (whyCheck.isChecked()) {
						whyCheck.setError(null);
						if (howWorksCheck.isChecked()) {
							howWorksCheck.setError(null);
							if (userObligationsCheck.isChecked()) {
								userObligationsCheck.setError(null);
								prefEditor.putBoolean("doneAgreements", true);
								prefEditor.commit();
								nextActivity();
							} else {
								userObligationsCheck
										.setError(getText(R.string.required));
								alert();
							}
						} else {
							if(!aboutCheck.isChecked()){
								aboutCheck.setError(getText(R.string.required));
							}else{
								aboutCheck.setError(null);
							}
							if(!userObligationsCheck.isChecked()){
								userObligationsCheck
								.setError(getText(R.string.required));
							}else{
								userObligationsCheck.setError(null);
							}
							if(!whyCheck.isChecked()){
								whyCheck.setError(getText(R.string.required));
							}
							howWorksCheck.setError(getText(R.string.required));
							alert();
						}
					} else {
						if(!aboutCheck.isChecked()){
							aboutCheck.setError(getText(R.string.required));
						}else{
							aboutCheck.setError(null);
						}
						if(!howWorksCheck.isChecked()){
							howWorksCheck.setError(getText(R.string.required));
						}else{
							howWorksCheck.setError(null);
						}
						if(!userObligationsCheck.isChecked()){
							userObligationsCheck
							.setError(getText(R.string.required));
						}else{
							userObligationsCheck.setError(null);
						}
						whyCheck.setError(getText(R.string.required));
						alert();
					}
				} else {
					if(!whyCheck.isChecked()){
						whyCheck.setError(getText(R.string.required));
					}
					if(!howWorksCheck.isChecked()){
						howWorksCheck.setError(getText(R.string.required));
					}else{
						howWorksCheck.setError(null);
					}
					if(!userObligationsCheck.isChecked()){
						userObligationsCheck
						.setError(getText(R.string.required));
					}else{
						userObligationsCheck.setError(null);
					}
					aboutCheck.setError(getText(R.string.required));
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
				.setTitle(getText(R.string.attention))
				.setMessage(getText(R.string.please_agree_to_terms))
				.setNeutralButton(R.string.ok, null).show();
	}

}
