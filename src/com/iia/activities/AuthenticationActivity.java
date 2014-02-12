package com.iia.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

public class AuthenticationActivity extends Activity {

	@SuppressWarnings("static-access")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        final UserManager usermanager = new UserManager(this);
        
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_authentication, null);
        
        final TextView login = (TextView) alertDialogView.findViewById(R.id.login);
        final TextView password = (TextView) alertDialogView.findViewById(R.id.password);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("Authentication");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
 
        
        // Button create
        adb.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	Intent intent = new Intent(AuthenticationActivity.this,
            			NewUserActivity.class);
            	
            	startActivity(intent);
          } });
        
        // Button connexion
        adb.setNegativeButton("OK", 
        						new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	String loginValue = login.getText().toString();
            	if (usermanager.LoginExist(loginValue)) {
            		if (usermanager.GetUserByArgument(0,
            				login.getText().toString()).getPassword() ==
            				password.getText().toString()) {
            			
            			Intent intent = new Intent(
            					AuthenticationActivity.this,
        						HomeActivity.class);
            		
            			intent.putExtra("passUser", login.getText().toString());
            				
            			startActivity(intent);
            
            			
            		}
            		else {
            			Toast.makeText(getBaseContext(),
            					"Fail password".toString(),
            					Toast.LENGTH_LONG).show();
            		}
            		
            	}
            	
            	else {
            		Toast.makeText(getBaseContext(),
        					"User don't exist".toString(),
        					Toast.LENGTH_LONG).show();
            	}
				
          } });
 
        adb.show();
    }

}
