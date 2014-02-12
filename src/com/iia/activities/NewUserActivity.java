package com.iia.activities;

import com.iia.data.Classes.User;
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
        
        final EditText etLastName = (EditText) this.findViewById(
        		R.id.lastname);
        final EditText etFirstName = (EditText) this.findViewById(
        		R.id.firstname);
        final EditText etLogin = (EditText) this.findViewById(
        		R.id.login);
        final EditText etPassword = (EditText) this.findViewById(
        		R.id.password);
        
     
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_newuser, null);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("Insert your informations");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
 
        
        // Button ok
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            /*	User user = new User(
						etLastName.getText().toString(),
						etFirstName.getText().toString(),
						etLogin.getText().toString(),
						etPassword.getText().toString()
						);
            	
            	UserManager.ADDUser(user);*/
            	
            	
            	Intent intent = new Intent(NewUserActivity.this,
            			ProfilActivity.class);
            	startActivity(intent);
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
