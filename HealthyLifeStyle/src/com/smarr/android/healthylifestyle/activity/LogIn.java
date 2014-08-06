package com.smarr.android.healthylifestyle.activity;

import java.util.List;

import org.apache.http.NameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;
import com.smarr.android.healthylifestyle.utilities.validation.UserValidation;

public class LogIn extends Activity {

	private EditText userName, passWord;
	private Button signIn;
	private TextView registerNow, forgotUsernamePassword;
	
	private StoreAppInfo storage;
	
	UserValidation validateLogIn = new UserValidation();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		storage = new StoreAppInfo(getApplicationContext());
		
		// imports ui elements
		userName = (EditText) findViewById(R.id.user_name);
		passWord = (EditText) findViewById(R.id.password);
		signIn = (Button) findViewById(R.id.signIn);
		forgotUsernamePassword = (TextView) findViewById(R.id.forgot_username);
		registerNow = (TextView) findViewById(R.id.register_now);


		// get device_ID and store it
		
		storage.putString("device_ID", Secure.getString(getBaseContext().getContentResolver(),
				Secure.ANDROID_ID));

		// get app version and store it
		PackageInfo pInfo;

		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			storage.putString("version_ID", pInfo.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		// if registration has not been completed on the device welcomes user
		if (storage.getBoolean("needRegister", true)) {
			new AlertDialog.Builder(this).setTitle(getText(R.string.welcome))
					.setMessage(getText(R.string.thank_you_for_downloading))
					.setPositiveButton(R.string.ok, null).show();
			//if user needs to register than the assumption is set that user needs photos for first time use as well
			//this value will not be false until photos are logged
			storage.putBoolean("needPhoto", true);
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
				
				signIn.setEnabled(validateLogIn.validateUserName(s, userName, getText(R.string.username_too_long),
						getText(R.string.username_too_short),
						getText(R.string.username_one_number),
						getText(R.string.username_start_letter)));
				
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

				signIn.setEnabled(validateLogIn.validatePassWord(s, passWord, getText(R.string.password_too_long),
						getText(R.string.password_too_short),
						getText(R.string.password_one_number),
						getText(R.string.password_one_letter)));
			
			}

		});

		// listener for the sign in button
		signIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				loginAction();
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
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

	@SuppressLint("NewApi") public void loginAction() {
		// gather info for validation of login
		String url = null;
		
		int response = 10;//set this back at 0 when time comes to connect with server the value of 10 gets me around the http connection for now
		
		List<NameValuePair> postData = HttpConnectionUtilities.createPostDataHolder();
		
		HttpConnectionUtilities.addNameValuePair("username", userName.getText().toString(),postData);
		HttpConnectionUtilities.addNameValuePair("password", passWord.getText().toString(), postData);
		HttpConnectionUtilities.addNameValuePair("version_ID", storage.getString("version_ID", null),postData);
		HttpConnectionUtilities.addNameValuePair("device_ID", storage.getString("device_ID", null),postData);
		
		
		
		/*try {
			response = HttpConnectionUtilities.postDataNoReturnMessage(url, postData);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if (response == 10){
			//insert doGet() from HttpConnectionUtilities to retrieve user data
			//assign that doGet() to a string that will be stored within a JSONObject to be parsed
				//JSONObject resultFromServer = new JSONObject(doGet());
			updateUser();
		}
		if (response == 20){//additional action required 
			//user message
			//action link
			//display user to click on action link to go to page
			//user cannot continue without performing the required action
			
			new AlertDialog.Builder(this).setTitle(getText(R.string.action_required_title))
			.setMessage(getText(R.string.action_required_text))
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//do whatever action is required I.E. update app or whatever
					
					//for now just loop back to login
			
						Intent intent = getIntent();
						finish();
						startActivity(intent);
					
				}
			}).show();
		}
		if (response == 30){//failed login
			//display error and ok message for user
			
			//this takes user back to the login screen to try again
			new AlertDialog.Builder(this).setTitle(getText(R.string.login_failed_title))
			.setMessage(getText(R.string.login_failed_text))
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//do whatever action is required I.E. update app or whatever
					
					//for now just loop back to login
					
						Intent intent = getIntent();
						finish();
						startActivity(intent);
					
				}
			}).show();
		}
		
		
		
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
		
		boolean doneAgreements = storage.getBoolean("doneAgreements", false);
		// tests if user agreements need to be completed
		if (doneAgreements) {
			// if agreements are done sends user to update user info activity
			Intent userStatus = new Intent(this, UserStatus.class);
			
			startActivity(userStatus);
		} else {

			// else sends to agreements activity
			Intent agreements = new Intent(this, UserAgreements.class);
			startActivity(agreements);
		}
	}

}
