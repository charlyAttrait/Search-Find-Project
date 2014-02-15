package com.iia.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.iia.data.Classes.Item;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.MainActivity;
import com.iia.searchandfind.R;
import com.iia.searchandfind.UtilLocationManager;

public class NewPointActivity extends Activity {

	public final static String BUNDLE_ITEM = "item";
	
	private EditText newPoint;
	private ItemManager itemManager;
	
	// boolean to know if activity is to create or edit a point
	private boolean creationMode;
	// in case of edition, it is the item to edit
	private Item editItem;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpoint);
      
        // get the state of the activity : creation/edition
        if (this.getIntent().getExtras() != null) {
			creationMode = false;
			editItem = (Item) this.getIntent().getExtras().
					getSerializable(BUNDLE_ITEM);
		} else {
			creationMode = true;
			editItem = null;
		}
        
        // set the itemManager
        itemManager = MainActivity.getItemManager();
        
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
 
        // in case of edition
        if (!creationMode) {
			newPoint.setText(editItem.getLibelle());
		}
        
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
        		if (!itemManager.libelleExist(newPoint.getText().toString())) {
        			
        			UtilLocationManager utilLocationManager = 
        					new UtilLocationManager(NewPointActivity.this,
        							(MapFragment) getFragmentManager().
        							findFragmentById(R.id.fragment_map));;
        			
        			// check the current location is not null
        			if (utilLocationManager.myLocation != null) {
        				// get my current location
    					LatLng location = utilLocationManager.myLocation;
    					
    					// get sharedPreferences to get idUser authentified
    			        SharedPreferences settings = 
    			        		NewPointActivity.this.getSharedPreferences(
    			        		"settings", NewPointActivity.this.MODE_PRIVATE);
    					
    			        if (creationMode) { // creation of item
    			        	Item item = new Item();
        					item.setLibelle(newPoint.getText().toString());
        					item.setCoordLat(location.latitude);
        					item.setCoordLong(location.longitude);
        					item.setUser(MainActivity.getUserManager().
        							getUserByArgument(settings.getInt("IDUser", 0), 
        									""));
        					
        					item.setId(itemManager.addItem(item));
						} else { // edition of item
							// change values
							editItem.setLibelle(newPoint.getText().toString());
							editItem.setCoordLat(location.latitude);
							editItem.setCoordLong(location.longitude);
							// update item in db
							MainActivity.getItemManager().
							updateItem(editItem.getId(), editItem);
						}
    					
					} else {
						Toast.makeText(NewPointActivity.this,
								"item cannot be saved because no location found".
								toString(),
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(NewPointActivity.this,
							"item with this name already exist".toString(),
							Toast.LENGTH_LONG).show();
				}
        		
        		Intent intent = new Intent(NewPointActivity.this,
            			ListPointActivity.class);
            	startActivity(intent);
            	finish();
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
