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

import com.google.android.gms.maps.model.LatLng;
import com.iia.data.Classes.Item;
import com.iia.data.Classes.User;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.LocationActivity;
import com.iia.searchandfind.R;

public class NewPointActivity extends Activity {

	private EditText newPoint;
	private ItemManager itemManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpoint);
      
        // set the itemManager
        itemManager = new ItemManager(this);
        
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(
        		R.layout.activity_newpoint, null);
 
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        newPoint = (EditText) alertDialogView.findViewById(R.id.nameitem);
        
        adb.setView(alertDialogView);
        adb.setTitle("New point");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
 
        // Button ok
        adb.setPositiveButton("OK", onAdbOKButton);
        // Button x
        adb.setNegativeButton("x", onAdbxButton);
 
        adb.show();
	}

	// onClickListener : happened where user click "OK"
	private DialogInterface.OnClickListener onAdbOKButton = 
			new DialogInterface.OnClickListener() {
				
		@Override
		public void onClick(DialogInterface dialog, int which) {
        	// if user has set a name for the item
        	if (!newPoint.getText().toString().equals("")) {
        		
        		// if item with this name already exist
        		if (!itemManager.LibelleExist(
        				newPoint.getText().toString())) {
        			
        			LocationActivity locationActivity = 
        					new LocationActivity();
        			// get my current location
					LatLng location = locationActivity.myLocation;
					
					// get sharedPreferences to get idUser authentified
			        SharedPreferences settings = 
			        		NewPointActivity.this.getSharedPreferences(
			        		"settings", NewPointActivity.this.MODE_PRIVATE);
					
					Item item = new Item();
					item.setLibelle(newPoint.getText().toString());
					item.setCoord_Lat(location.latitude);
					item.setCoord_Long(location.longitude);
					item.setUser(new UserManager(NewPointActivity.this).
							GetUserByArgument(settings.getInt("IDUser", 0), 
									""));
					
					item.setId(itemManager.ADDItem(item));
				} else {
					Toast.makeText(NewPointActivity.this,
							"item with this name already exist".toString(),
							Toast.LENGTH_LONG).show();
				}
        		
        		Intent intent = new Intent(NewPointActivity.this,
            			ListPointActivity.class);
            	startActivity(intent);
			} else {
				Toast.makeText(NewPointActivity.this,
						"no name completed!".toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	};
	// onClickListener : happened where user click on "x"
	private DialogInterface.OnClickListener onAdbxButton = 
				new DialogInterface.OnClickListener() {
					
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
		}
	};
	
}
