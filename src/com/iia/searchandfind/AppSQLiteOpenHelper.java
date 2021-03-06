package com.iia.searchandfind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.data.contract.ItemContract;
import com.iia.data.contract.UserContract;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

	public AppSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.beginTransaction();

		try {
			db.setTransactionSuccessful();
			
			// Create the structure of the dataBase
			db.execSQL(UserContract.SCHEMA);
			db.execSQL(ItemContract.SCHEMA);
			
		} catch (Exception e) {
			// TODO: handle exception
			String message = e.getMessage();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion) {
			// Vide la (ou les) table(s)
			
		}
	}

}
