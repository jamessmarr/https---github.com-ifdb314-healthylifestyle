package com.smarr.android.healthylifestyle;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends Activity {

	private EditText userName, passWord;
	private Button signIn;
	private TextView forgotPassword, registerNow, forgotUsernamePassword;

	private Editable user_name, pass_word;

	private boolean userNameOk, passWordOk;

	private String device_ID, version_ID;
	public static final String APP_PREFERENCES = "app_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		// imports ui elements
		userName = (EditText) findViewById(R.id.user_name);
		passWord = (EditText) findViewById(R.id.password);
		signIn = (Button) findViewById(R.id.signIn);
		forgotUsernamePassword = (TextView) findViewById(R.id.forgot_username);
		registerNow = (TextView) findViewById(R.id.register_now);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();

		// get device_ID and store it
		device_ID = Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);
		prefEditor.putString("device_ID", device_ID);
		prefEditor.commit();

		// get app version and store it
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version_ID = pInfo.versionName;
			prefEditor.putString("version_ID", version_ID);
			prefEditor.commit();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		if (savedInstanceState == null) {
			if (settings.getBoolean("needRegister", true)) {
				new AlertDialog.Builder(this)
						.setTitle("WELCOME")
						.setMessage(
								"Thanks for downloading! Register now to start your path to a healthier lifestyle.")
						.setPositiveButton(R.string.ok, null).show();
			}
		}

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
									if (userNameOk && passWordOk) {
										signIn.setEnabled(true);
									}
								} else {
									userName.setError(getText(R.string.username_one_number));
									userNameOk = false;
									signIn.setEnabled(false);
								}

							} else {
								userName.setError(getText(R.string.username_too_long));
								userNameOk = false;
								signIn.setEnabled(false);
							}
						} else {
							userName.setError(getText(R.string.username_too_short));
							userNameOk = false;
							signIn.setEnabled(false);
						}
					} else {
						userName.setError(getText(R.string.username_start_letter));
						userNameOk = false;
						signIn.setEnabled(false);
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
								if (userNameOk && passWordOk) {
									signIn.setEnabled(true);
								}
							} else {
								passWord.setError(getText(R.string.password_one_number));
								passWordOk = false;
								signIn.setEnabled(false);
							}
						} else {
							passWord.setError(getText(R.string.password_one_letter));
							passWordOk = false;
							signIn.setEnabled(false);
						}
					} else {
						passWord.setError(getText(R.string.password_too_long));
						passWordOk = false;
						signIn.setEnabled(false);
					}
				} else {
					passWord.setError(getText(R.string.password_too_short));
					passWordOk = false;
					signIn.setEnabled(false);
				}
			}

		});

		// listener for the sign in button
		signIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				loginAction();

			}
		});

		// listener for forgot username text view
		forgotUsernamePassword.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				recovery();

			}
		});

		// listener for forgot password text view
		registerNow.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				register();

			}
		});

	}

	public void loginAction() {
		// gather info for validation of login
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		version_ID = settings.getString("version_ID", null);
		device_ID = settings.getString("device_ID", null);
		user_name = userName.getText();
		pass_word = passWord.getText();

		// send above info to server here

		// currently a loop around the login process with server
		int replyFromServer = 10;
		// make a call to a new class to handle all of the responses from server
		responseAction(replyFromServer);
	}

	// once user clicks sign in this method is called with the servers response
	private void responseAction(int replyFromServer) {
		int serverResponseID = replyFromServer;
		if (serverResponseID == 10) {
			// normal operating response
			// call next activity after login
			// startActivity(nextApplicableActivity); have to pass this an
			// intent i believe....look this up later and figure it out when you
			// get there
			updateUser();
		} else if (serverResponseID == 20) {
			// call new method to handle 20
			// actionRequired();
		} else if (serverResponseID == 30) {
			// invalid login attempt
			// could be bad username/password
			// or some other reason to cause validation to fail
			// give user a message stating error
			// make sure to re route the app back to login screen
			// call new method for 30
			// invalidLogin(serverResponseID);
		} else if (serverResponseID == 40) {
			// call new method to handle code 40
			// updateApp();
		} else {
			// any other result is same as a failure to login
			// prompt user with an error
			// re route app back to login screen
			// call invlaidLogin method
			// invalidLogin(serverResponseID);
		}
	}

	public void actionRequired() {
		// requires a message to user based upon additional action required
	}

	public void invalidLogin(int response) {
		// passing this method the response ID in order to make this method a
		// multi purpose method based on the code
	}

	public void updateApp() {
		// prompt user to update app with a dialog with two options
		// message states cannot continue without updating
		// option "ok" sends user to app store for update
		// option "no" or "later" will send user back to the login screen
	}

	private void register() {
		Intent register = new Intent(this, Registration.class);
		startActivity(register);
	}

	private void recovery() {
		Intent recovery = new Intent(this, Username_Password_Recovery.class);
		startActivity(recovery);
	}

	private void updateUser() {
		// gets the stored user agreements info
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES,
				MODE_PRIVATE);
		boolean doneAgreements = settings.getBoolean("doneAgreements", false);
		// tests if user agreements need to be completed
		if (doneAgreements) {
			// if no sends user to update user info activity
			Intent userInfoUpdate = new Intent(this, UserInfoUpdate.class);
			startActivity(userInfoUpdate);
		} else {

			// if yes sends to agreements activity
			Intent agreements = new Intent(this, UserAgreements.class);
			startActivity(agreements);
		}
	}

}
