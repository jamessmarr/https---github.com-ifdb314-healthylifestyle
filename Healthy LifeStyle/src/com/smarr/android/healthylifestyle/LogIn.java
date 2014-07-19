package com.smarr.android.healthylifestyle;

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

	private String device_ID, version_ID;
	public static final String APP_PREFERENCES = "app_preferences";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		userName = (EditText) findViewById(R.id.user_name);
		passWord = (EditText) findViewById(R.id.password);
		signIn = (Button) findViewById(R.id.signIn);
		forgotUsernamePassword = (TextView) findViewById(R.id.forgot_username);
		registerNow = (TextView) findViewById(R.id.register_now);

		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
		

		if (savedInstanceState == null) {
			if (settings.getBoolean("needRegister", true)) {
				new AlertDialog.Builder(this)
						.setTitle("WELCOME")
						.setMessage(
								"Thanks for downloading! Register now to start your path to a healthier lifestyle.")
						.setNeutralButton(R.string.ok, null).show();
			}
		}

		// disables sign in button until text is entered in username and
		// password fields
		userName.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				signIn.setEnabled(true);
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
		// send this info to server for validation
		device_ID = Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID);
		user_name = userName.getText();
		pass_word = passWord.getText();
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version_ID = pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// placeholder variable to keep errors off screen
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
		Intent recovery = new Intent(this, UserInfoUpdate.class);
		startActivity(recovery);
	}

	private void updateUser() {
		//gets the stored user agreements info
		SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
		boolean doneAgreements = settings.getBoolean("doneAgreements", false);
		//tests if user agreements need to be completed
		if(doneAgreements){
			//if no sends user to update user info activity
			Intent userInfoUpdate = new Intent(this, UserInfoUpdate.class);	
			startActivity(userInfoUpdate);
		}else{
			
			//if yes sends to agreements activity
			Intent agreements = new Intent(this, UserAgreements.class);	
			startActivity(agreements);
		}
	}
}
