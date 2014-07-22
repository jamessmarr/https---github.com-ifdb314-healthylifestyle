package com.smarr.android.healthylifestyle;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registration extends Activity {

	private Button submitRegistration;
	private EditText userName, passWord, emailAddress;

	private boolean userNameOk, passWordOk;

	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();

		submitRegistration = (Button) findViewById(R.id.submit_registration);
		userName = (EditText) findViewById(R.id.userName);
		passWord = (EditText) findViewById(R.id.passWord);
		emailAddress = (EditText) findViewById(R.id.userEmail);

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

		submitRegistration.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (!passWord.getText().toString().contains(csAlpha)
				// && !passWord.getText().toString().contains(csNumeric)) {
				// passWord.setError("Password must contain at least one number and one letter");
				// }
			}
		});

	}
}
