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
import com.iia.searchandfind.MainActivity;

public class UserManager {

	/**
	 * Attributes
	 */
	private static final String TAG = "UserManager";
	private static SQLiteDatabase db;
	
	/**
	 * Constructor
	 */
	public UserManager() {
		db = MainActivity.getDb();
	}

	/**
	 * Methods
	 */
	
	/**
	 * Add user in dataBase
	 * @param user to add in dataBase
	 * @return the identifier of this new user
	 */
	public int addUser(User user) {
		// instance of ContentValues to add user in dataBase
		ContentValues content = new ContentValues();
		content.put(UserContract.COL_NOM, user.getNom());
		content.put(UserContract.COL_PRENOM, user.getPrenom());
		content.put(UserContract.COL_LOGIN, user.getLogin());
		content.put(UserContract.COL_PASSWORD, user.getPassword());
		
		int idUser = 0;
		
		try {
			// Execute the query and get the id created
			idUser = (int) db.insert(UserContract.TABLE, null, content);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return idUser;
	}
	
	/**
	 * Update informations of user with id = idUser by newUser informations
	 * @param idUser of the user to update
	 * @param user with new informations
	 */
	public void updateUser(int idUser, User user) {
		// instance of ContentValues to update user in dataBase
		ContentValues content = new ContentValues();
		content.put(UserContract.COL_NOM, user.getNom());
		content.put(UserContract.COL_PRENOM, user.getPrenom());
		content.put(UserContract.COL_LOGIN, user.getLogin());
		content.put(UserContract.COL_PASSWORD, user.getPassword());
		
		// WHERE Argument " WHERE .. = arg "
		String[] whereArgs = { String.valueOf(idUser) };
		// WHERE Clause " WHERE clause = .. "
		String whereClause = UserContract.COL_ID + " = ?";
		
		try {
			// Execute the query
			db.update(UserContract.TABLE, content, whereClause, whereArgs);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * Get all the users in dataBase
	 * @return an arrayList of User
	 */
	public ArrayList<User> getUsers() {
		// List of users to return
		ArrayList<User> users = new ArrayList<User>();
		
		// Instance of user to add in list
		User user = null;
		
		try {
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
		}
		return users;
	}

	/**
	 * Inform of the existence (or not) of the login send
	 * @param login
	 * @return a boolean inform of the existence of the login
	 */
	public boolean loginExist(String login) {
		// boolean to send
		boolean exist = false;
		ArrayList<User> users = getUsers();
		
		// Browse users in dataBase
		for (User user : users) {
			// if login is already used
			if (user.getLogin().equals(login)) {
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
	public User getUserByArgument(int id, String login) {
		User user = null;
		ArrayList<User> users = getUsers();
		
		// Browse users in dataBase
		for (User DBuser : users) {
			// if argument is ID and the id of user browse is the same than id send
			// or argument is login and the login of user browse is the same than login send
			if ((id != 0 && DBuser.getId() == id) ||
					(!login.equals("") && DBuser.getLogin().equals(login))) {
				// set DBUser in the instance of user to return
				user = DBuser;
				break;
			}
		}
		
		return user;
	}
	
}
