package com.iia.searchandfind;

import com.iia.activities.AuthenticationActivity;
import com.iia.activities.ListPointActivity;
import com.iia.activities.NewPointActivity;
import com.iia.data.Managers.ItemManager;
import com.iia.data.Managers.UserManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class MainActivity extends Activity {

	public static MainActivity getInstance;
	
	private static AppSQLiteOpenHelper dbHelper;
	private static SQLiteDatabase db;
	
	private static UserManager userManager;
	private static ItemManager itemManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Initialize database Helper
		dbHelper = new AppSQLiteOpenHelper(MainActivity.this, "MyDb", null, 1);
		
		// Get Db instance
		db = dbHelper.getWritableDatabase();
		
		userManager = new UserManager();
		itemManager = new ItemManager();
		
		getInstance = this;
		
		// call the activity authentification
		Intent intent = new Intent(MainActivity.this,
				AuthenticationActivity.class);
		
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onDestroy() {
		// close dataBase connection before quit
		dbHelper.close();
		super.onDestroy();
	}


	/**
	 * ACCESSORS
	 */
	/**
	 * @return the dbHelper
	 */
	public static AppSQLiteOpenHelper getDbHelper() {
		return dbHelper;
	}
	/**
	 * @param dbHelper the dbHelper to set
	 */
	public static void setDbHelper(AppSQLiteOpenHelper dbHelper) {
		MainActivity.dbHelper = dbHelper;
	}
	/**
	 * @return the db
	 */
	public static SQLiteDatabase getDb() {
		return db;
	}
	/**
	 * @param db the db to set
	 */
	public static void setDb(SQLiteDatabase db) {
		MainActivity.db = db;
	}
	/**
	 * @return the userManager
	 */
	public static UserManager getUserManager() {
		return userManager;
	}
	/**
	 * @param userManager the userManager to set
	 */
	public static void setUserManager(UserManager userManager) {
		MainActivity.userManager = userManager;
	}
	/**
	 * @return the itemManager
	 */
	public static ItemManager getItemManager() {
		return itemManager;
	}
	/**
	 * @param itemManager the itemManager to set
	 */
	public static void setItemManager(ItemManager itemManager) {
		MainActivity.itemManager = itemManager;
	}

}
