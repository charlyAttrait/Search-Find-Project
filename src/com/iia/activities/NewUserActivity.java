package com.iia.activities;

import com.iia.data.CLASS.User;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
             
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_newuser, null);
        
        final UserManager usermanager = new UserManager(this);
        
        final EditText etLastName = (EditText) alertDialogView.findViewById(
        		R.id.lastname);
        final EditText etFirstName = (EditText) alertDialogView.findViewById(
        		R.id.firstname);
        final EditText etLogin = (EditText) alertDialogView.findViewById(
        		R.id.login);
        final EditText etPassword = (EditText) alertDialogView.findViewById(
        		R.id.password);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("Insert your informations");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setCancelable(false);
 
        
        // Button ok
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressWarnings("static-access")
			public void onClick(DialogInterface dialog, int which) {
            	
            	// Save EditText in a String variable
            	String ln = etLastName.getText().toString();
            	String fn = etFirstName.getText().toString();
            	String log = etLogin.getText().toString();
            	String pass = etPassword.getText().toString();
            	
            	User user = new User(ln, fn, log, pass);
            	
            	usermanager.ADDUser(user);
            	
            	
            	Intent intent = new Intent(NewUserActivity.this,
            			HomeActivity.class);
            	startActivity(intent);
            	
            	Toast.makeText(NewUserActivity.this,
    					"User add".toString(),
    					Toast.LENGTH_LONG).show();
          } });
        
        // Button x
        adb.setNegativeButton("x", 
        						new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	
            		finish();
            	
				
          } });
 
        adb.show();
    }

}
