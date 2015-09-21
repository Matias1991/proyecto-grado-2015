package com.example.androidproject;

import java.util.HashMap;

import com.example.androidproject.controllers.UserController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSession {

	// Shared Preferences reference
	SharedPreferences pref;

	// Editor reference for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared preferences file name
	public static final String PREFER_NAME = "Reg";
	
	// Shared preferences mode
	int PRIVATE_MODE = 0;

	// All Shared Preferences Keys
	public static final String IS_USER_LOGIN = "IsUserLoggedIn";

	// User id (make variable public to access from outside)
	public static final String KEY_USER_ID = "UserId";
		
	// User name (make variable public to access from outside)
	public static final String KEY_USER_NAME = "UserName";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "Email";

	// password
	public static final String KEY_PASSWORD = "Password";

	// Constructor
	public UserSession(Context context){
	    this._context = context;
	    pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
	    editor = pref.edit();
	}

	//Create login session
	public void createUserLoginSession(VOUser voUser, String password){
	    // Storing login value as TRUE
	    editor.putBoolean(IS_USER_LOGIN, true);

	    // Storing id in preferences
	    editor.putString(KEY_USER_ID, String.valueOf(voUser.getId()));
	    
	    // Storing name in preferences
	    editor.putString(KEY_USER_NAME, voUser.getUserName());

	    // Storing password in preferences
	    editor.putString(KEY_PASSWORD,  password);

	    // Storing email in preferences
	    editor.putString(KEY_EMAIL, voUser.getEmail());
	    
	    // commit changes
	    editor.commit();
	}   

	/**
	 * Check login method will check user login status
	 * If false it will redirect user to login page
	 * Else do anything
	 * */
	public boolean checkLogin(){
	    // Check login status
	    if(!this.isUserLoggedIn()){

	        // user is not logged in redirect him to Login Activity
	        Intent i = new Intent(_context, Login.class);

	        // Closing all the Activities from stack
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	        // Add new Flag to start new Activity
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	        // Staring Login Activity
	        _context.startActivity(i);

	        return true;
	    }
	    return false;
	}



	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){

	    //Use hashmap to store user credentials
	    HashMap<String, String> user = new HashMap<String, String>();

	    // user id
	    user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
	    
	    // user name
	    user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));

	    // user password
	    user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

	    // user email id
	    user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
	    
	    // return user
	    return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser(){

	    // Clearing all user data from Shared Preferences
	    editor.clear();
	    editor.commit();

	    // After logout redirect user to MainActivity
	    Intent i = new Intent(_context, Login.class);

	    // Closing all the Activities
	    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	    // Add new Flag to start new Activity
	    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	    // Staring Login Activity
	    _context.startActivity(i);
	}


	// Check for login
	public boolean isUserLoggedIn(){
		
		if(pref.getBoolean(IS_USER_LOGIN, false))
		{
			UserController userController = new UserController(_context.getResources().getString(R.string.ip_server));
			try {
				if(userController.login(getUserDetails().get(KEY_USER_NAME), getUserDetails().get(KEY_PASSWORD)) != null)
					return true;
				else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			return false;
		
		return false;
	}
}
