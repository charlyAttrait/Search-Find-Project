package com.iia.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.data.Classes.User;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

public class NewUserActivity extends Activity {
	
	private UserManager userManager;
	
	private EditText etLastName;
	private EditText etFirstName;
	private EditText etLogin;
	private EditText etPassword;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
             
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_newuser, null);
        
        userManager = new UserManager(this);
        
        etLastName = (EditText) alertDialogView.findViewById(R.id.lastname);
        etFirstName = (EditText) alertDialogView.findViewById(R.id.firstname);
        etLogin = (EditText) alertDialogView.findViewById(R.id.login);
        etPassword = (EditText) alertDialogView.findViewById(R.id.password);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("Insert your informations");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setCancelable(false);
        
        // Button ok
        adb.setPositiveButton("OK", onAdbOKBUttonClick);
        // Button x
        adb.setNegativeButton("x", onAdbxButtonClick);
        adb.show();
    }

	// onClickListener : happened where user valid his informations
	private DialogInterface.OnClickListener onAdbOKBUttonClick = 
			new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// Save EditText in a String variable
        	String ln = etLastName.getText().toString();
        	String fn = etFirstName.getText().toString();
        	String log = etLogin.getText().toString();
        	String pass = etPassword.getText().toString();
        	
        	// prepare a user to add in db
        	User user = new User(ln, fn, log, pass);
        	
        	// add user in db and get his identifier
        	user.setId(userManager.ADDUser(user));
        	

			// create a sharedPreferences
			//	-> Just save the idUser
			SharedPreferences settings = 
					NewUserActivity.this.
					getSharedPreferences(
        			"settings", 
        			Context.MODE_WORLD_READABLE);
			
			// put the idUser in settings
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("IDUser", user.getId());
			editor.commit();
        	
        	Intent intent = new Intent(NewUserActivity.this,
        			HomeActivity.class);
        	startActivity(intent);
        	
        	Toast.makeText(NewUserActivity.this,
					"User add".toString(),
					Toast.LENGTH_LONG).show();
		}
	};
	// onClickListener : happened where user click on "x"
	private DialogInterface.OnClickListener onAdbxButtonClick = 
			new DialogInterface.OnClickListener() {
				
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
	};
}
