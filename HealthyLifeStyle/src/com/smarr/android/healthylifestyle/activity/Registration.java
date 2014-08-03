package com.smarr.android.healthylifestyle.activity;

import java.util.List;

import org.apache.http.NameValuePair;
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

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.validation.UserValidation;

public class Registration extends Activity {

	private Button submitRegistration;
	private EditText userName, passWord, emailAddress;
	private TextView returnToLogin;

	private JSONObject registrationObject = new JSONObject();

	public static final String APP_PREFERENCES = "app_preferences";
	
	UserValidation validateRegistration = new UserValidation();

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
				submitRegistration.setEnabled(validateRegistration.validateUserName(s, userName, getText(R.string.username_too_long),
						getText(R.string.username_too_short),
						getText(R.string.username_one_number),
						getText(R.string.username_start_letter))
						&& android.util.Patterns.EMAIL_ADDRESS.matcher(
								emailAddress.getText().toString()).matches());
				
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
				submitRegistration.setEnabled(validateRegistration.validatePassWord(s, passWord, getText(R.string.password_too_long),
						getText(R.string.password_too_short),
						getText(R.string.password_one_number),
						getText(R.string.password_one_letter))
						&& android.util.Patterns.EMAIL_ADDRESS.matcher(
								emailAddress.getText().toString()).matches());
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
				if (validateRegistration.isPassWordOk()&&validateRegistration.isUserNameOk()
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
		SharedPreferences.Editor prefEditor = settings.edit();

		String url = null;
		
		int response = 10;//set this back at 0 when time comes to connect with server the value of 10 gets me around the http connection for now
		
		List<NameValuePair> postData = HttpConnectionUtilities.createPostDataHolder();
		
		HttpConnectionUtilities.addNameValuePair("username", userName.getText().toString(),postData);
		HttpConnectionUtilities.addNameValuePair("password", passWord.getText().toString(), postData);
		HttpConnectionUtilities.addNameValuePair("emailAddress",emailAddress.getText().toString(),postData);
		HttpConnectionUtilities.addNameValuePair("device_ID", settings.getString("device_ID", null),postData);
		
		/*try {
			response = HttpConnectionUtilities.postDataNoReturnMessage(url, postData);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		if (response == 10) {
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
			prefEditor.putBoolean("newUser", true);
			prefEditor.putBoolean("needRegister", false);
			prefEditor.commit();
		} 
		if (response == 40){
			new AlertDialog.Builder(this).setTitle(getText(R.string.username_exists_title))
			.setMessage(getText(R.string.username_exists))
			.setPositiveButton(R.string.ok, null).show();
		}
		if (response == 50){
			new AlertDialog.Builder(this).setTitle(getText(R.string.email_exists_title))
			.setMessage(getText(R.string.email_exists))
			.setPositiveButton(R.string.ok, null).show();
		}
		
		if (response == 60){
			new AlertDialog.Builder(this).setTitle(getText(R.string.device_id_exists_title))
			.setMessage(getText(R.string.device_id_exists))
			.setPositiveButton(R.string.ok, null).show();
		}
		if (response == 70){
			new AlertDialog.Builder(this).setTitle(getText(R.string.error_occured_title))
			.setMessage(getText(R.string.error_occured))
			.setPositiveButton(R.string.ok, null).show();
		}
		
		
	}

	private void goToLogIn() {
		Intent logIn = new Intent(this, LogIn.class);
		startActivity(logIn);
	}
}
