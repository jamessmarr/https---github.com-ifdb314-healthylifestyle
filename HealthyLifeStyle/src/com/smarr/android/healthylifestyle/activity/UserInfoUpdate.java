package com.smarr.android.healthylifestyle.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ViewFlipper;

import com.smarr.android.healthylifestyle.R;

public class UserInfoUpdate extends Activity {

	private ViewFlipper flipper;
	private Button nextButton;
	private int current_Weight, desired_Weight, current_body_image_checked, desired_body_image_checked;

	private RadioButton current_image_1, current_image_2, current_image_3,
			current_image_4, current_image_5;

	private RadioButton desired_image_1, desired_image_2, desired_image_3,
			desired_image_4, desired_image_5;

	private EditText input;
	private AlertDialog currentWeight, desiredWeight;
	private AlertDialog.Builder builder;
	
	private String lastLogin;

	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_update);
		lastLogin();
		

		if (savedInstanceState == null) {
		}

		flipper = (ViewFlipper) findViewById(R.id.UserUpdateFlipper);
		current_image_1 = (RadioButton) findViewById(R.id.currentImage1);
		current_image_2 = (RadioButton) findViewById(R.id.currentImage2);
		current_image_3 = (RadioButton) findViewById(R.id.currentImage3);
		current_image_4 = (RadioButton) findViewById(R.id.currentImage4);
		current_image_5 = (RadioButton) findViewById(R.id.currentImage5);

		desired_image_1 = (RadioButton) findViewById(R.id.desiredImage1);
		desired_image_2 = (RadioButton) findViewById(R.id.desiredImage2);
		desired_image_3 = (RadioButton) findViewById(R.id.desiredImage3);
		desired_image_4 = (RadioButton) findViewById(R.id.desiredImage4);
		desired_image_5 = (RadioButton) findViewById(R.id.desiredImage5);

		// controls radio button checks since they arent in a group
		current_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 1;
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 2; 
				current_image_1.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});

		current_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 3;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_4.setChecked(false);
				current_image_5.setChecked(false);
			}
		});

		current_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 4;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_5.setChecked(false);
			}
		});
		current_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				current_body_image_checked = 5;
				current_image_1.setChecked(false);
				current_image_2.setChecked(false);
				current_image_3.setChecked(false);
				current_image_4.setChecked(false);
			}
		});

		desired_image_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 1;
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 2;
				desired_image_1.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 3;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_4.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 4;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_5.setChecked(false);
			}
		});
		desired_image_5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				desired_body_image_checked = 5;
				desired_image_1.setChecked(false);
				desired_image_2.setChecked(false);
				desired_image_3.setChecked(false);
				desired_image_4.setChecked(false);
			}
		});

		nextButton = (Button) findViewById(R.id.next_button);

		nextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flipper.getDisplayedChild() != flipper.getChildCount() - 1) {
					flipper.showNext();
				} else {
					SharedPreferences settings = getSharedPreferences(
							APP_PREFERENCES, MODE_PRIVATE);
					SharedPreferences.Editor prefEditor = settings.edit();
					prefEditor.putInt("current_Weight", current_Weight);
					prefEditor.putInt("desired_Weight", desired_Weight);
					prefEditor.putInt("current_body_image", current_body_image_checked);
					prefEditor.putInt("desired_body_image", desired_body_image_checked);
					prefEditor.commit();
					nextActivity();
				}
			}

		});

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

	public void currentWeightUpdate() {
		builder = new AlertDialog.Builder(this);
		input = new EditText(this);
		input.setInputType(2); //2 is for numeric input keyboard
		input.setHint("Allowed values are 50-400");
		input.setMaxLines(1);
		input.setGravity(Gravity.CENTER_HORIZONTAL);
		builder.setView(input);
		builder.setTitle("Current Weight");
		builder.setMessage("Please Enter Your current weight");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//stores date user last updated weight
						SharedPreferences settings = getSharedPreferences(
								APP_PREFERENCES, MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						Calendar c = Calendar.getInstance();
						int month = c.get(Calendar.MONTH) + 1;
						int year = c.get(Calendar.YEAR);
						int day = c.get(Calendar.DATE);
						
						String weight_last_updated ="  " + month +"/"+ day + "/" + year;
						prefEditor.putString("weight_last_updated", weight_last_updated);
						prefEditor.commit();
						desiredWeightUpdate();

					}
				});

		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) throws NumberFormatException {
				try {
					current_Weight = Integer.parseInt(arg0.toString());

					if (current_Weight > 49) {
						if (current_Weight < 401) {
							input.setError(null);
							currentWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(true);
						} else {
							input.setError("Please make a valid entry");
							currentWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(false);
						}
					} else {
						input.setError("Please make a valid entry");
						currentWeight.getButton(Dialog.BUTTON_POSITIVE)
								.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		currentWeight = builder.create();
		currentWeight.show();
		currentWeight.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
	}

	public void desiredWeightUpdate() {
		builder = new AlertDialog.Builder(this);
		input = new EditText(this);
		input.setInputType(2); //2 is for numeric input keyboard
		input.setHint("Allowed values are 50-400");
		input.setMaxLines(1);
		input.setGravity(Gravity.CENTER_HORIZONTAL);
		builder.setView(input);
		builder.setTitle("Weight Goal");
		builder.setMessage("Please Enter Your desired weight");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		input.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) throws NumberFormatException {
				try {
					desired_Weight = Integer.parseInt(arg0.toString());

					if (desired_Weight > 49) {
						if (desired_Weight < 401) {
							input.setError(null);
							desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(true);
						} else {
							input.setError("Please make a valid entry");
							desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
									.setEnabled(false);
						}
					} else {
						input.setError("Please make a valid entry");
						desiredWeight.getButton(Dialog.BUTTON_POSITIVE)
								.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		desiredWeight = builder.create();
		desiredWeight.show();
		desiredWeight.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);

	}
	
	public void lastLogin(){
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();

		lastLogin = settings.getString("lastLogin", "null");

		if (lastLogin == "null") {
			//sets last login time if first time logging on
			Time now = new Time(Time.getCurrentTimezone());
			now.setToNow();
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH) + 1;
			int year = c.get(Calendar.YEAR);
			int day = c.get(Calendar.DATE);
			
			lastLogin ="  " + month +"/"+ day + "/" + year + " at " + now.format("%k:%M");
			prefEditor.putString("lastLogin", lastLogin);
			prefEditor.commit();
			currentWeightUpdate();
		} else {
			//displays the last login date and time and stores new date time for this login
			new AlertDialog.Builder(this).setTitle(getText(R.string.welcome_back))
			.setMessage(getText(R.string.last_login) + lastLogin)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					currentWeightUpdate();
					
				}
			}).show();
			
			Time now = new Time(Time.getCurrentTimezone());
			now.setToNow();
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH) + 1;
			int year = c.get(Calendar.YEAR);
			int day = c.get(Calendar.DATE);
			
			lastLogin ="  " + month +"/"+ day + "/" + year + " at " + now.format("%k:%M");
			prefEditor.putString("lastLogin", lastLogin);
			prefEditor.commit();
					
		}
	}

}
