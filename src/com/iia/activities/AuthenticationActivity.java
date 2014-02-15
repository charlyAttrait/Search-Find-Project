package com.iia.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.data.Classes.User;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

public class AuthenticationActivity extends Activity {

	private TextView login;
	private TextView password;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDialog();
    }

	// function to create the authentification's dialog
	public void createDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_authentication, null);
        
        // get the textViews
        login = (TextView) alertDialogView.findViewById(R.id.login);
        password = (TextView) alertDialogView.findViewById(R.id.password);
 
		// init a new alertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("Authentication");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setCancelable(false);
 
        // Button create
        adb.setPositiveButton("Sign up", onAdbSignUpButton);
        // Button connexion
        adb.setNegativeButton("OK", onAdbOkButton);
        adb.show();
	}
	
	// onClickListener : happened where user click "Sign Up"
	private DialogInterface.OnClickListener onAdbSignUpButton = 
			new DialogInterface.OnClickListener() {
				
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(AuthenticationActivity.this,
        			NewUserActivity.class);
			// Navigate to activity for create a user
        	startActivity(intent);
        	finish();
		}
	};
	// onClickListener : happened where user click on "OK"
	private DialogInterface.OnClickListener onAdbOkButton = 
				new DialogInterface.OnClickListener() {
				
		@Override
		public void onClick(DialogInterface dialog, int which) {
			String loginValue = login.getText().toString();
			// instance of userManager to use
			UserManager manager = new UserManager(AuthenticationActivity.this);
			User user = null;
			
			// check informations are filled
			if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
				// if login exist in db
	        	if (manager.LoginExist(loginValue)) {
	        		// get the user with login = loginValue
	        		user = manager.GetUserByArgument(0, loginValue);
	        		
	        		// check the validity of the password for user
	        		if (user.getPassword().equals(password.getText().toString())) {
	        			
	        			Intent intent = new Intent(AuthenticationActivity.this,
	    						HomeActivity.class);
	        		
	        			// create a sharedPreferences
	        			//	-> Just save the idUser
						SharedPreferences settings = AuthenticationActivity.this.
								getSharedPreferences("settings", 
			        			Context.MODE_WORLD_READABLE);
						
						// put the idUser in settings
						SharedPreferences.Editor editor = settings.edit();
						editor.putInt("IDUser", manager.GetUserByArgument(0, 
									login.getText().toString()).getId());
						editor.commit();
			        	
	        			// Navigate to next activity
	        			startActivity(intent);
	        			finish();
	        		} else {
	        			Toast.makeText(getBaseContext(), "Fail password".toString(),
	        					Toast.LENGTH_LONG).show();
	        			createDialog();
	        		}
	        	} else {
	        		Toast.makeText(getBaseContext(), "User don't exist".toString(),
	    					Toast.LENGTH_LONG).show();
	        		createDialog();
	        	}
			} else {
				Toast.makeText(getBaseContext(), "no informations filled".toString(),
    					Toast.LENGTH_LONG).show();
				createDialog();
			}
		}
	};
	
}
