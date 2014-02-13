package com.iia.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.iia.data.Classes.Item;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;
import com.iia.searchandfind.UtilLocationManager;

public class ListPointActivity extends Activity {

	private final static int CMD_EDIT = 0;
	private final static int CMD_DELETE = 1;
	
	private Button btnNew;

	private ImageView ivBack;
	private ListView lvPoint;
	
	private ArrayList<Item> items;
	private int selectedItem;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpoint);
     
        btnNew = (Button) this.findViewById(R.id.newpoint);
        ivBack = (ImageView) this.findViewById(R.id.back);
        lvPoint = (ListView) this.findViewById(R.id.listpoint);
        
        // get sharedPreferences to get idUser authentified
        SharedPreferences settings = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        
        // get the list of item for the user authentified
        items = new ItemManager(this).GetItems(settings.getInt("IDUser", 0));
        
        // init adapter for the listView
		MyAdapter adapter = new MyAdapter(this, R.layout.row_list, items);
		
		// set the adapter to the listView
		lvPoint.setAdapter(adapter);
		
        // set onClickListener to control button and imageButton
        btnNew.setOnClickListener(onBtnNewClick);
        ivBack.setOnClickListener(onIvBackClick);
        
        //set onItemClickListener to select an item
        lvPoint.setOnItemClickListener(onItemClick);
        // set onItemLongClickListener to change/delete an item
        lvPoint.setOnItemLongClickListener(onItemLongClick);
	}
	
	// OnClickListener for the button NewItem
	private OnClickListener onBtnNewClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// call the activity for add a new item
			Intent intent = new Intent(ListPointActivity.this,
					NewPointActivity.class);
    		
    		startActivity(intent);
		}
	};
	// OnClickListener for the imageButton ivBack
	private OnClickListener onIvBackClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// return to back activity
			finish();
		}
	};
	
	// onItemClickListener : user select a row in the list
	// onItemClickListener to get selected item in the list
	private OnItemClickListener onItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// get the item selected in listView
			Item item = (Item) lvPoint.getItemAtPosition(position);
			
			Intent intent = new Intent(ListPointActivity.this,
					CompassActivity.class);
			// send the item to CompassLocationActivity
    		intent.putExtra(CompassActivity.BUNDLE_ITEM, item);
    		startActivity(intent);
		}
	};
	// onItemLongClickListener to catch item and allow to change it or delete it
	private OnItemLongClickListener onItemLongClick = 
			new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					// save the item selected
					selectedItem = position;

				    registerForContextMenu(parent); 
				    openContextMenu(parent);
				    unregisterForContextMenu(parent);

					return true;
				}
			};
	
	
	/* 
	 * Get the choice of the user and openDialog to confirm or edit
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		    case CMD_EDIT: // command edit item
		        
		    	break;
		    case CMD_DELETE: // command delete item
		    	AlertDialog.Builder adb = new AlertDialog.Builder(this);
		    	
		        adb.setTitle("Etes vous sûr de vouloir supprimer cet item ?");
		        adb.setIcon(android.R.drawable.ic_dialog_alert);
		        adb.setCancelable(false);
		        
		        // Button YES
		        adb.setPositiveButton("OUI",
		        		new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// get item selected in listView
								Item selected = (Item) lvPoint.
										getItemAtPosition(selectedItem);
								// delete the item in db
								new ItemManager(ListPointActivity.this).
									DeleteItem(selected.getId());
								//refresh listView
								// ...
							}
						});
		        // Button Cancel
		        adb.setNegativeButton("Annuler",
		        		new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
		        adb.show();
		    	break;
	    }
		return true;
	}


	/* 
	 * Prepare a contextMenu with options "Modifier" and "Supprimer"
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, CMD_EDIT, 0, "Modifier");
	    menu.add(0, CMD_DELETE, 0, "Supprimer");  
	}


	// Adapter for the listView of items
	private static class MyAdapter extends ArrayAdapter<Item> {

		public Context context;
		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#notifyDataSetChanged()
		 */
		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

		public int resource;
		public LayoutInflater inflater;
		
		public MyAdapter(Context context, int resource, List<Item> items) {
			super(context, resource, items);
			this.context = context;
			this.resource = resource;
			
			this.inflater = LayoutInflater.from(this.context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(this.resource, null);
			
			// get the textView : item's name
			TextView name = (TextView) view.findViewById(R.id.item_name);
			
			// get the item
			Item item = this.getItem(position);
			
			// set the text of the textView
			name.setText(item.getLibelle());
			
			return view;
		}
		
	}
}
