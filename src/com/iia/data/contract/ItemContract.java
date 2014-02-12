package com.iia.data.contract;

import android.database.sqlite.SQLiteDatabase;

import com.iia.searchandfind.AppSQLiteOpenHelper;

public class ItemContract {

	/**
	 * Helper for the dataBase
	 */
	private static AppSQLiteOpenHelper dbHelper;
	/**
	 * Instance of the SQLite dataBase
	 */
	private static SQLiteDatabase db;
	
	/**
	 * Table name
	 */
	public final static String TABLE = "T_ITEM";
	
	/**
	 * Columns for the table with name value
	 */
	public final static String COL_ID = "ID_ITEM";
	public final static String COL_LIBELLE = "LIBELLE";
	public final static String COL_COORD_LAT = "COORD_LAT";
	public final static String COL_COORD_LONG = "COORD_LONG";
	public final static String COL_ID_USER = "ID_USER";
	
	/**
	 * String[] with all columns names
	 */
	public final static String[] COLS = { 
		COL_ID,
		COL_LIBELLE,
		COL_COORD_LAT,
		COL_COORD_LONG,
		COL_ID_USER
	};
	
	/**
	 * Query CREATE TABLE
	 */
	public final static String SCHEMA = 
			"CREATE TABLE " + TABLE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_LIBELLE + " NVARCHAR(50) NOT NULL,"
			+ COL_COORD_LAT + " INTEGER NOT NULL," 
			+ COL_COORD_LONG + " INTEGER NOT NULL,"
			+ COL_ID_USER + " INTEGER NOT NULL REFERENCES "
			+ UserContract.TABLE + "(" + UserContract.COL_ID + ")"
			+ ")";
	
}
