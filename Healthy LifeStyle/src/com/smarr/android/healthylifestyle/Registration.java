package com.smarr.android.healthylifestyle;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends Activity {

	private Button submitRegistration;
	private EditText userName, passWord, emailAddress;
	private TextView returnToLogin;

	private boolean userNameOk, passWordOk;

	private JSONObject registrationObject = new JSONObject();

	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		submitRegistration = (Button) findViewById(R.id.submit_registration);
		userName = (EditText) findViewById(R.id.userName);
		passWord = (EditText) findViewById(R.id.passWord);
		emailAddress = (EditText) findViewById(R.id.userEmail);
		returnToLogin = (TextView) findViewById(R.id.returnToLogIn);

		userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					if (Character.isLetter(s.charAt(0))) {
						if (s.length() > 7) {
							if (s.length() < 16) {
								if (Pattern.compile("[0-9]")
										.matcher(s.toString()).find()) {
									userName.setError(null);
									userNameOk = true;
									if (userNameOk
											&& passWordOk
											&& android.util.Patterns.EMAIL_ADDRESS
													.matcher(
															emailAddress
																	.getText()
																	.toString())
													.matches()) {
										submitRegistration.setEnabled(true);
									}
								} else {
									userName.setError(getText(R.string.username_one_number));
									userNameOk = false;
									submitRegistration.setEnabled(false);
								}

							} else {
								userName.setError(getText(R.string.username_too_long));
								userNameOk = false;
								submitRegistration.setEnabled(false);
							}
						} else {
							userName.setError(getText(R.string.username_too_short));
							userNameOk = false;
							submitRegistration.setEnabled(false);
						}
					} else {
						userName.setError(getText(R.string.username_start_letter));
						userNameOk = false;
						submitRegistration.setEnabled(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		passWord.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 5) {
					if (s.length() < 17) {
						if (Pattern.compile("[a-zA-Z]").matcher(s.toString())
								.find()) {
							if (Pattern.compile("[0-9]").matcher(s.toString())
									.find()) {
								passWord.setError(null);
								passWordOk = true;
								if (userNameOk
										&& passWordOk
										&& android.util.Patterns.EMAIL_ADDRESS
												.matcher(
														emailAddress.getText()
																.toString())
												.matches()) {
									submitRegistration.setEnabled(true);
								}
							} else {
								passWord.setError(getText(R.string.password_one_number));
								passWordOk = false;
								submitRegistration.setEnabled(false);
							}
						} else {
							passWord.setError(getText(R.string.password_one_letter));
							passWordOk = false;
							submitRegistration.setEnabled(false);
						}
					} else {
						passWord.setError(getText(R.string.password_too_long));
						passWordOk = false;
						submitRegistration.setEnabled(false);
					}
				} else {
					passWord.setError(getText(R.string.password_too_short));
					passWordOk = false;
					submitRegistration.setEnabled(false);
				}
			}

		});

		emailAddress.addTextChangedListener(new TextWatcher() {

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
					int arg3) {
				if (userNameOk
						&& passWordOk
						&& android.util.Patterns.EMAIL_ADDRESS.matcher(
								emailAddress.getText().toString()).matches()) {
					submitRegistration.setEnabled(true);
				} else {
					submitRegistration.setEnabled(false);
				}
			}

		});
		returnToLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goToLogIn();

			}
		});

		submitRegistration.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					registerRequest();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	private void registerRequest() throws JSONException {
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		// adds the registration required values into the JSON object throws
		// exception if any value is null

		registrationObject.put("userName", userName.getText());
		registrationObject.put("passWord", passWord.getText());
		registrationObject.put("emailAddress", emailAddress.getText());
		registrationObject.put("device_ID",
				settings.getString("device_ID", null));

		// added a boolean into the object to bypass the registration process
		// changing true or flase fails or succeds the process
		registrationObject.put("registerOk", true);
		// the following values are for testing the outcomes of the registration
		registrationObject.put("usernameExists", false);
		registrationObject.put("emailExists", false);
		registrationObject.put("deviceIDExists", false);

		// dummy call to server sending JSON object
		// registrationObject = sendInfo(registrationObject);
		// this is where I assume the server will add a boolean value into the
		// JSON object
		// as well as a string or two for errors and such

		if (registrationObject.getBoolean("registerOk")) {
			new AlertDialog.Builder(this)
					.setTitle(getText(R.string.thank_you))
					.setMessage(getText(R.string.thank_you_for_registering))
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SharedPreferences settings = getSharedPreferences(
											APP_PREFERENCES, MODE_PRIVATE);
									SharedPreferences.Editor prefEditor = settings
											.edit();
									prefEditor
											.putBoolean("needRegister", false);
									prefEditor.commit();
									goToLogIn();

								}
							}).show();
		} else {
			submitRegistration.setFocusableInTouchMode(true);
			submitRegistration.requestFocus();
			if (registrationObject.getBoolean("usernameExists")) {
				submitRegistration.setError(getText(R.string.username_exists));
			} else if (registrationObject.getBoolean("emailExists")) {
				submitRegistration.setError(getText(R.string.email_exists));
			} else if (registrationObject.getBoolean("deviceIDExists")) {
				submitRegistration.setError(getText(R.string.device_id_exists));
			} else {
				submitRegistration.setError(getText(R.string.error_occured));
			}
		}
	}

	private void goToLogIn() {
		Intent logIn = new Intent(this, LogIn.class);
		startActivity(logIn);
	}
}
