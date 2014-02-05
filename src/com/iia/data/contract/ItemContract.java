package com.iia.data.contract;

import android.database.sqlite.SQLiteDatabase;

import com.iia.searchandfind.AppSQLiteOpenHelper;

public class ItemContract {

	private static AppSQLiteOpenHelper dbHelper;
	private static SQLiteDatabase db;
	
	public final static String TABLE = "T_ITEM";
	
	public final static String COL_ID = "ID_ITEM";
	public final static String COL_LIBELLE = "LIBELLE";
	public final static String COL_COORD_ALT = "COORD_ALT";
	public final static String COL_COORD_LONG = "COORD_LONG";
	public final static String COL_ID_USER = "ID_USER";
	
	public final static String[] COLS = { 
		COL_ID,
		COL_LIBELLE,
		COL_COORD_ALT,
		COL_COORD_LONG,
		COL_ID_USER
	};
	
	public final static String SCHEMA = 
			"CREATE TABLE " + TABLE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_LIBELLE + " NVARCHAR(50) NOT NULL,"
			+ COL_COORD_ALT + " INTEGER NOT NULL," 
			+ COL_COORD_LONG + " INTEGER NOT NULL,"
			+ COL_ID_USER + " INTEGER NOT NULL REFERENCES "
			+ UserContract.TABLE + "(" + UserContract.COL_ID + ")"
			+ ")";
	
}