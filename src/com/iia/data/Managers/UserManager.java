package com.iia.data.Managers;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iia.data.Classes.User;
import com.iia.data.contract.UserContract;
import com.iia.searchandfind.AppSQLiteOpenHelper;

public class UserManager {

	/**
	 * Attributes
	 */
	private static final String TAG = "UserManager";
	private static AppSQLiteOpenHelper dbHelper;
	private static SQLiteDatabase db;
	
	/**
	 * Constructor
	 */
	public UserManager(Context context) {
		dbHelper = new AppSQLiteOpenHelper(context, "MyDb", null, 1);
		open();
	}

	/**
	 * Methods
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
	 * Add user in dataBase
	 * @param user to add in dataBase
	 * @return the identifier of this new user
	 */
	public static int ADDUser(User user) {
		// instance of ContentValues to add user in dataBase
		ContentValues content = new ContentValues();
		content.put(UserContract.COL_NOM, user.getNom());
		content.put(UserContract.COL_PRENOM, user.getPrenom());
		content.put(UserContract.COL_LOGIN, user.getLogin());
		content.put(UserContract.COL_PASSWORD, user.getPassword());
		
		int idUser = 0;
		
		try {
			// Open SQLite connection
			open();
			
			// Execute the query and get the id created
			idUser = (int) db.insert(UserContract.TABLE, null, content);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			// finally close the connection
			//close();
		}
		return idUser;
	}
	
	/**
	 * Update informations of user with id = idUser by newUser informations
	 * @param idUser of the user to update
	 * @param user with new informations
	 */
	public static void UpdateUser(int idUser, User user) {
		// instance of ContentValues to update user in dataBase
		ContentValues content = new ContentValues();
		content.put(UserContract.COL_NOM, user.getNom());
		content.put(UserContract.COL_PRENOM, user.getPrenom());
		content.put(UserContract.COL_LOGIN, user.getLogin());
		content.put(UserContract.COL_PASSWORD, user.getPassword());
		
		// WHERE Argument " WHERE .. = arg "
		String[] whereArgs = { String.valueOf(idUser) };
		// WHERE Clause " WHERE clause = .. "
		String whereClause = UserContract.COL_ID + " = ";
		
		try {
			// Open SQLite connection
			//open();
			
			// Execute the query
			db.update(UserContract.TABLE, content, whereClause, whereArgs);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			// finally close the connection
			//close();
		}
	}
	
	/**
	 * Get all the users in dataBase
	 * @return an arrayList of User
	 */
	public static ArrayList<User> GetUsers() {
		// List of users to return
		ArrayList<User> users = new ArrayList<User>();
		
		// Instance of user to add in list
		User user = null;
		
		try {
			// Open SQLite connection
			//open();
			
			// cursor of the select query
			Cursor c = db.query(UserContract.TABLE, 
					UserContract.COLS, 
					null, 
					null, 
					null, 
					null, 
					null);
			
			// go to the first (if possible)
			if (c.moveToFirst()) {
				// Browse the result of query
				do {
					// init the user with informations browse
					user = new User(
							c.getString(c.getColumnIndex(
									UserContract.COL_NOM)), 
							c.getString(c.getColumnIndex(
									UserContract.COL_PRENOM)), 
							c.getString(c.getColumnIndex(
									UserContract.COL_LOGIN)), 
							c.getString(c.getColumnIndex(
									UserContract.COL_PASSWORD)));
					
					// Set the identifier of the user
					user.setId(c.getInt(c.getColumnIndex(
							UserContract.COL_ID)));
					
					// Add user to list
					users.add(user);
				} while (c.moveToNext());
			}
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			// finally close the connection
			//close();
		}
		return users;
	}

	/**
	 * Inform of the existence (or not) of the login send
	 * @param login
	 * @return a boolean inform of the existence of the login
	 */
	public static boolean LoginExist(String login) {
		// boolean to send
		boolean exist = false;
		
		// Browse users in dataBase
		for (User user : GetUsers()) {
			// if login is already used
			if (user.getLogin() == login) {
				// Change state of boolean exist
				exist = true;
				break;
			}
		}
		
		return exist;
	}

	/**
	 * Get a user by his identifier or his login
	 * @param id : user identifier
	 * @param login : user login
	 * @return the user according to argument
	 */
	public static User GetUserByArgument(int id, String login) {
		User user = null;
		
		// Browse users in dataBase
		for (User DBuser : GetUsers()) {
			// if argument is ID and the id of user browse is the same than id send
			// or argument is login and the login of user browse is the same than login send
			if ((id != 0 && DBuser.getId() == id) ||
					(login != "" && DBuser.getLogin() == login)) {
				// set DBUser in the instance of user to return
				user = DBuser;
				break;
			}
		}
		
		return user;
	}
	
}
