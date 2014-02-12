package com.iia.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iia.data.Classes.Item;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;
import com.iia.searchandfind.R;

public class ListPointActivity extends Activity {

	private Button btnNew;
	private ImageView ivBack;
	private ListView lvPoint;
	
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
        ArrayList<Item> items = new ItemManager(this).GetItems(settings.getInt("IDUser", 0));
        
        // init adapter for the listView
		MyAdapter adapter = new MyAdapter(this, R.layout.row_list, items);
		
		// set the adapter to the listView
		lvPoint.setAdapter(adapter);
		
        // set onClickListener to control button and imageButton
        btnNew.setOnClickListener(onBtnNewClick);
        ivBack.setOnClickListener(onIvBackClick);
        
        //set onItemClickListener to select an item
        lvPoint.setOnItemClickListener(onItemClick);
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
	private OnItemClickListener onItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// get the item selected in listView
			Item item = (Item) lvPoint.getItemAtPosition(position);
			
		}
	};
	
	// Adapter for the listView of items
	private static class MyAdapter extends ArrayAdapter<Item> {

		public Context context;
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
