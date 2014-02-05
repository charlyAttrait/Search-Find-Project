package com.iia.searchandfind;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	public AppSQLiteOpenHelper dbHelper;
	public SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize database Helper
		dbHelper = new AppSQLiteOpenHelper(MainActivity.this, "MyDb", null, 1);
		
		// Get Db instance
		db = dbHelper.getWritableDatabase();
		
		// ...
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
