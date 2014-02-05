package com.iia.data.contract;

import com.iia.searchandfind.AppSQLiteOpenHelper;

import android.database.sqlite.SQLiteDatabase;

public class UserContract {

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
	public final static String TABLE = "T_USER";
	
	/**
	 * Columns for the table with name value
	 */
	public final static String COL_ID = "ID_USER";
	public final static String COL_NOM = "NOM";
	public final static String COL_PRENOM = "PRENOM";
	public final static String COL_LOGIN = "LOGIN";
	public final static String COL_PASSWORD = "PASSWORD";
	
	/**
	 * String[] with all columns names
	 */
	public final static String[] COLS = { 
		COL_ID,
		COL_NOM,
		COL_PRENOM,
		COL_LOGIN,
		COL_PASSWORD
	};
	
	/**
	 * Query CREATE TABLE
	 */
	public final static String SCHEMA = 
			"CREATE TABLE " + TABLE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_NOM + " NVARCHAR(50) NOT NULL,"
			+ COL_PRENOM + " NVARCHAR(50) NOT NULL," 
			+ COL_LOGIN + " NVARCHAR(50) NOT NULL,"
			+ COL_PASSWORD + " NVARCHAR(20) NOT NULL"
			+ ")";
	
}
