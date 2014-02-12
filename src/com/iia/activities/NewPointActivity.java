package com.iia.activities;

import com.iia.data.CLASS.Item;
import com.iia.data.CLASS.User;
import com.iia.data.Managers.ItemManager;
import com.iia.searchandfind.CompassActivity;
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

public class NewPointActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpoint);
      
        final EditText newpoint = (EditText) this.findViewById(R.id.nameitem);
        
        
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_newpoint, null);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
 
        adb.setView(alertDialogView);
        adb.setTitle("New point");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
 
        
        // Button ok
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	/*User user = new User("Flo", "B", "test", "1234");
            	Item item = new Item("testpoint", 0, 0, user);
            	ItemManager.ADDItem(item);*/
            	
            	Intent intent = new Intent(NewPointActivity.this,
            			CompassActivity.class);
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
