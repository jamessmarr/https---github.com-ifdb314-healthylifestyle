package com.smarr.android.healthylifestyle.activity;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.smarr.android.healthylifestyle.R;
import com.smarr.android.healthylifestyle.utilities.exception.BaseException;
import com.smarr.android.healthylifestyle.utilities.http.HttpConnectionUtilities;
import com.smarr.android.healthylifestyle.utilities.shared_preferences.StoreAppInfo;

public class UserStatus extends Activity {

	
	
	private int desired_Weight, failures;

	private StoreAppInfo storage;
	
	private JSONObject userProfile;
	private int Gender = 1;
	private String DOB = "6/14/1986";
	private int current_Weight;
	private String weight_last_updated;
	private int current_body_type;//current body image
	private int weight_goal;//desired weight
	private int current_strikes;
	private int current_attempts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_status);
		
		storage = new StoreAppInfo(getApplicationContext());

		current_Weight = storage.getInt("current_Weight", 0);
		weight_last_updated = storage.getString("weight_last_updated", "");
		desired_Weight = storage.getInt("desired_Weight", 0);
				
		if(storage.getBoolean("newUser", false)){
			getUserProfile();
		}
		
		getUserMessages();
		
		sendSyncInfo();
		
		getSyncPlans();
		
		
		

		if (savedInstanceState == null) {

		}
	}
	
	
	
	public void getUserProfile(){
		//this should be the url for user profile
		String url = null;
		
		//attempts to pull user profile from server
		//and store the returned string into a JSON Object for parsing
		try{
		userProfile = new JSONObject(HttpConnectionUtilities.doGet(url));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//parse all of these from the profile JSON object
		try {
			 //not a correct parse but since there is no info to parse at this time the following code works
			//Gender=userProfile.getInt("gender");
			//DOB = (Date) userProfile.get("dob");
			//the following are defined from user actions they are listed just as a reference
			//current_Weight 
			//weight_last_updated 
			//current_body_type;//current body image
			//weight_goal;//desired weight
			current_strikes = userProfile.getInt("current_strikes");
			//current_attempts = userProfile.getInt("current_attempts");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void getUserMessages(){
		//could be one of two things
			//either get users messages for the day from the server
			//or simply present the days plan notifications
	}
	
	public void sendSyncInfo(){
		//this should be the url for posting user info
		String url = null;
		
		int response = 10;//set this back at 0 when time comes to connect with server the value of 10 gets me around the http connection for now
		
		//creates data package to send updated info to server
		List<NameValuePair> postData = HttpConnectionUtilities.createPostDataHolder();
		
		HttpConnectionUtilities.addNameValuePair("current_Weight", current_Weight+"",postData);
		HttpConnectionUtilities.addNameValuePair("weight_last_updated", weight_last_updated,postData);
		HttpConnectionUtilities.addNameValuePair("desired_Weight", desired_Weight+"", postData);
		HttpConnectionUtilities.addNameValuePair("failures", failures+"", postData);//current_strikes?
		
		
		
		/*try {
			response = HttpConnectionUtilities.postDataNoReturnMessage(url, postData);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(response == 10){
			Toast.makeText(this, "User info successfully synced with server", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void getSyncPlans(){
		//grab any updates for the users current plan
		//grab the version number from LifeStylePlan
		//send this numebr to server expecting 2 returns
			//first response is ok version numbers match
			//second response will be needs to download plan updates
		
		//cant code this until I get the lifestlyePlan constructor working
	}
	
	public void getInitialPictures(){
		//5 pictures taken on first use and every 2 weks afterwards
	}
	
	

}
