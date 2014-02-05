package com.iia.data.Managers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iia.data.CLASS.Item;
import com.iia.data.CLASS.User;
import com.iia.data.contract.ItemContract;
import com.iia.data.contract.UserContract;
import com.iia.searchandfind.AppSQLiteOpenHelper;

public class ItemManager {

	/**
	 * Attributes
	 */
	private static AppSQLiteOpenHelper dbHelper;
	private static SQLiteDatabase db;
	
	/**
	 * Cosntructor
	 */
	public ItemManager(Context context) {
		dbHelper = new AppSQLiteOpenHelper(context, "MyDb", null, 1);
	}

	/**
	 * Methodes
	 */
	/**
	 * Open dataBase connection
	 * @throws SQLException
	 */
	public static void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * Close dataBase connection
	 */
	public static void close() {
		dbHelper.close();
	}
	
	/**
	 * Add item in dataBase
	 * @param item to add in dataBase
	 * @return the identifier of this new item
	 */
	public static int ADDItem(Item item) {
		// instance of ContentValues to add item in dataBase
		ContentValues content = new ContentValues();
		content.put(ItemContract.COL_LIBELLE, item.getLibelle());
		content.put(ItemContract.COL_COORD_ALT, item.getCoord_Alt());
		content.put(ItemContract.COL_COORD_LONG, item.getCoord_Long());
		content.put(ItemContract.COL_ID_USER, item.getUser().getId());
		
		int idItem = 0;
		
		try {
			// Open SQLite connection
			open();
			
			// Execute the query and get the id created
			idItem = (int) db.insert(ItemContract.TABLE, null, content);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// finally close the connection
			close();
		}
		return idItem;
	}
	
	/**
	 * Update informations of item with id = idItem by item informations
	 * @param idItem of the item to update
	 * @param item with new informations
	 */
	public static void UpdateItem(int idItem, Item item) {
		// instance of ContentValues to update item in dataBase
		ContentValues content = new ContentValues();
		content.put(ItemContract.COL_LIBELLE, item.getLibelle());
		content.put(ItemContract.COL_COORD_ALT, item.getCoord_Alt());
		content.put(ItemContract.COL_COORD_LONG, item.getCoord_Long());
		content.put(ItemContract.COL_ID_USER, item.getUser().getId());
		
		// WHERE Argument " WHERE .. = arg "
		String[] whereArgs = { String.valueOf(idItem) };
		// WHERE Clause " WHERE clause = .. "
		String whereClause = ItemContract.COL_ID + " = ";
		
		try {
			// Open SQLite connection
			open();
			
			// Execute the query
			db.update(ItemContract.TABLE, content, whereClause, whereArgs);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// finally close the connection
			close();
		}
	}

	/**
	 * Delete the item with id = idItem
	 * @param idItem of the item to delete
	 */
	public static void DeleteItem(int idItem) {
		// WHERE Argument " WHERE .. = arg "
		String[] whereArgs = { String.valueOf(idItem) };
		// WHERE Clause " WHERE clause = .. "
		String whereClause = ItemContract.COL_ID + " = ";
		
		try {
			// Open SQLite connection
			open();
			
			// Execute the query
			db.delete(ItemContract.TABLE, whereClause, whereArgs);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// finally close the connection
			close();
		}
	}
	
	/**
	 * Get the list of items for the idUser send
	 * @param idUser
	 * @return a list of items
	 */
	public static ArrayList<Item> GetItems(int idUser) {
		// List of items to return
		ArrayList<Item> items = new ArrayList<Item>();
		
		// Instance of item to add in list
		Item item = null;
		
		// WHERE Argument " WHERE .. = arg "
		String[] whereArgs = { String.valueOf(idUser) };
		// WHERE Clause " WHERE clause = .. "
		String whereClause = ItemContract.COL_ID_USER + " = ";
		
		// in the case of idUser = 0, delete clause WHERE
		//		query will load every items in dataBase
		if (idUser == 0) {
			whereArgs = null;
			whereClause = null;
		}
		
		try {
			// Open SQLite connection
			open();
			
			// cursor of the select query
			Cursor c = db.query(ItemContract.TABLE, 
					ItemContract.COLS, 
					whereClause, 
					whereArgs, 
					null, 
					null, 
					null);
			
			// go to the first (if possible)
			if (c.moveToFirst()) {
				// Browse the result of query
				do {
					// init the item with informations browse
					item = new Item(
							c.getString(c.getColumnIndex(
									ItemContract.COL_LIBELLE)), 
							c.getInt(c.getColumnIndex(
									ItemContract.COL_COORD_ALT)), 
							c.getInt(c.getColumnIndex(
									ItemContract.COL_COORD_LONG)), 
							UserManager.GetUserByArgument(
									c.getInt(c.getColumnIndex(
											ItemContract.COL_ID_USER)), ""));
					
					// Set the identifier of item
					item.setId(c.getInt(c.getColumnIndex(
							ItemContract.COL_ID)));
					
					// Add item to list
					items.add(item);
				} while (c.moveToNext());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// finally close the connection
			close();
		}
		return items;
	}

	/**
	 * Inform of the existence (or not) of the item libelle send
	 * @param item libelle
	 * @return a boolean inform of the existence of the item libelle
	 */
	public static boolean LibelleExist(String libelle) {
		// boolean to send
		boolean exist = false;
		
		// Browse items in dataBase
		for (Item item : GetItems(0)) {
			// if libelle is already used
			if (item.getLibelle() == libelle) {
				// Change state of boolean exist
				exist = true;
				break;
			}
		}
		
		return exist;
	}

}
