package com.smarr.android.healthylifestyle.utilities.shared_preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StoreAppInfo{
	public static final String APP_PREFERENCES = "app_preferences";
	
	private SharedPreferences settings;
	private Editor prefEditor;
	
	
	public StoreAppInfo(Context context){
		settings = context.getSharedPreferences(APP_PREFERENCES, Activity.MODE_PRIVATE);
		prefEditor = settings.edit();
	}
	
	public String getString(String key, String defValue){
		return settings.getString(key, defValue);
		
	}
	
	public void putString(String key, String storeValue){
		prefEditor.putString(key, storeValue);
		prefEditor.commit();
	}
	
	public boolean getBoolean(String key, boolean defValue){
		return settings.getBoolean(key, defValue);
		
	}
	
	public void putBoolean(String key, boolean storeValue){
		prefEditor.putBoolean(key, storeValue);
		prefEditor.commit();
	}
	
	public int getInt(String key, int defValue){
		return settings.getInt(key, defValue);
	}
	
	public void putInt(String key, int storeValue){
		prefEditor.putInt(key, storeValue);
		prefEditor.commit();
	}
}
