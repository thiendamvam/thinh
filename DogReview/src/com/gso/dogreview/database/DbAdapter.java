package com.gso.dogreview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.gso.dogreview.model.Comment;
import com.gso.dogreview.model.Dog;

public class DbAdapter {

	/*
	 * Dog table
	 */
	public static final String DOG_TABLE = "DOG_TABLE";
	public static final String DOG_ID = "DOG_ID";
	public static final String DOG_NAME = "DOG_NAME";
	public static final String DOG_DESC = "DOG_DESC";
	public static final String DOG_AVATAR = "DOG_AVATAR";
	public static final String DOG_FAVOURITE = "DOG_FAVOURITE";
	public static final String DOG_READ = "DOG_READ";
	/*
	 * Comment table
	 */

	public static final String COMMENT_TABLE = "COMMENT_TABLE";
	public static final String COMMENT_ID = "_id";
	public static final String COMMENT_DOG_ID = "COMMENT_DOG_ID";
	public static final String COMMENT_AVATAR = "COMMENT_AVATAR";
	public static final String COMMENT_COMMENT = "COMMENT_COMMENT";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "AMKit_Database";
	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static final String DATABASE_DOG_CREATE = "create virtual table "
			+ DOG_TABLE + " USING FTS3(" + DOG_ID
			+ " text not null primary key," + DOG_NAME + " text," + DOG_DESC
			+ " text," + DOG_AVATAR + " text," +DOG_FAVOURITE + " integer," +
			DOG_READ + " integer"+

			");";

	/*
	 * Comment table create
	 */

	private static final String DATABASE_COMMENT_CREATE = "create virtual table "
			+ COMMENT_TABLE+ " USING FTS3("
			+ COMMENT_ID+ " INTEGER AUTOINCREMENT,"
			+ COMMENT_DOG_ID+ " text not null primary key,"
			+ COMMENT_AVATAR + " text," 
			+ COMMENT_COMMENT + " text"+
			");";

	public static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_DOG_CREATE);
			db.execSQL(DATABASE_COMMENT_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_DOG_CREATE);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_COMMENT_CREATE);
			onCreate(db);
		}
	}

	public synchronized DbAdapter open() throws SQLException {
		if (mDbHelper == null)
			mDbHelper = new DatabaseHelper(mCtx);
		if (mDb != null) {
			if (!mDb.isOpen())
				mDb = mDbHelper.getWritableDatabase();
		} else {
			mDb = mDbHelper.getWritableDatabase();
		}
		return this;
	}

	public synchronized boolean isOpen() {
		if (mDb != null)
			return mDb.isOpen();
		else
			return false;
	}

	public void close() {
		mDb.close();
		mDbHelper.close();
	}

	public DbAdapter(Context context) {
		mCtx = context;
	}

	/*
	 * Get and inseart dog
	 */
	public Cursor getFavoriteDogList() {

		return mDb.query(DOG_TABLE, new String[] { DOG_ID, DOG_NAME, DOG_DESC,
				DOG_AVATAR, DOG_FAVOURITE, DOG_READ }, DOG_FAVOURITE+" MATCH ?", new String[]{"1"}, null, null, null);
	}
	
	public Cursor getDogList() {

		return mDb.query(DOG_TABLE, new String[] { DOG_ID, DOG_NAME, DOG_DESC,
				DOG_AVATAR, DOG_FAVOURITE, DOG_READ }, null, null, null, null, null);
	}
	public Cursor getDogListByName(String query) {

		return mDb.query(DOG_TABLE, new String[] { DOG_ID, DOG_NAME, DOG_DESC,
				DOG_AVATAR, DOG_FAVOURITE, DOG_READ }, DOG_NAME + " MATCH ?", new String[] { "*" + query
				+ "*" }, null, null, null);
	}
	public boolean insertDog(Dog dog) throws SQLiteException {
		ContentValues insertedValue = new ContentValues();
		insertedValue.put(DOG_ID, dog.getId());
		insertedValue.put(DOG_NAME, dog.getName());
		insertedValue.put(DOG_DESC, dog.getDescription());
		insertedValue.put(DOG_AVATAR, dog.getAvatar());
		insertedValue.put(DOG_FAVOURITE, dog.isFavourite()?1:0);
		insertedValue.put(DOG_READ, dog.isRead()?1:0);
		Cursor c = mDb.rawQuery("select *	from " + DOG_TABLE + " where "
				+ DOG_ID + "='" + dog.getId() + "'", null);
		if (c.getCount() < 1) {
			return mDb.insert(DOG_TABLE, null, insertedValue) != -1;
		} else {
			return false;
		}

	}
	public boolean updateDog(Dog dog) throws SQLiteException {
		ContentValues insertedValue = new ContentValues();
		insertedValue.put(DOG_ID, dog.getId());
		insertedValue.put(DOG_NAME, dog.getName());
		insertedValue.put(DOG_DESC, dog.getDescription());
		insertedValue.put(DOG_AVATAR, dog.getAvatar());
		insertedValue.put(DOG_FAVOURITE, dog.isFavourite()?1:0);
		insertedValue.put(DOG_READ, dog.isRead()?1:0);
//		Cursor c = mDb.rawQuery("select *	from " + DOG_TABLE + " where "
//				+ DOG_ID + "='" + dog.getId() + "'", null);
//		if (c.getCount() > 0) {
			return mDb.update(DOG_TABLE, insertedValue, DOG_ID+" MATCH ?", new String[]{dog.getId()}) != -1;
//		} else {
//			return false;
//		}

	}

//	public boolean updateDog(Dog dog) throws SQLiteException {
//		
//		int fav = dog.isFavourite()?1:0;
//		String sqlite  = "insert or replace into "+DOG_TABLE+" ("+
//		DOG_FAVOURITE+") values ("+fav+")";
//		ContentValues insertedValue = new ContentValues();
//		insertedValue.put(DOG_ID, dog.getId());
//		insertedValue.put(DOG_NAME, dog.getName());
//		insertedValue.put(DOG_DESC, dog.getDescription());
//		insertedValue.put(DOG_AVATAR, dog.getAvatar());
//		insertedValue.put(DOG_FAVOURITE, dog.isFavourite()?1:0);
//		mDb.rawQuery(sqlite, null);
//		return true;
//
//	}
	
	/*
	 * get and insert comment
	 */

	public Cursor getComment(String query) {
		return mDb.query(COMMENT_TABLE, new String[] { COMMENT_ID,
				COMMENT_DOG_ID, COMMENT_AVATAR,  COMMENT_COMMENT },
				COMMENT_DOG_ID + " MATCH ?",
				new String[] { "*" + query + "*" }, null, null, null);

	}

	public boolean insertComment(Comment comment) throws SQLiteException {
		ContentValues insertedValue = new ContentValues();
		insertedValue.put(COMMENT_ID, comment.getId());
		insertedValue.put(COMMENT_DOG_ID, comment.getDogId());
		insertedValue.put(COMMENT_AVATAR, comment.getAvatar());
		insertedValue.put(COMMENT_COMMENT, comment.getComment());
		

		Cursor c = mDb.rawQuery("select *	from " + COMMENT_TABLE + " where "
				+ COMMENT_ID + "='" + comment.getId() + "'", null);
		if (c.getCount() < 1) {
			return mDb.insert(COMMENT_TABLE, null, insertedValue) != -1;
		} else {
			return false;
		}

	}

	public boolean removeDog(Dog item) {
		// TODO Auto-generated method stub
		try {
			String quereClause = DOG_ID + "=?";
	    	String[] args = { item.getId()};
	    	return mDb.delete(DOG_TABLE, quereClause, args) > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeFavorites() {
		// TODO Auto-generated method stub
		try {
			String whereClause = DOG_FAVOURITE+" MATCH ?";
			String []whereArgs = {"1"};
			return mDb.delete(DOG_TABLE, whereClause, whereArgs) != -1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeReadDogList() {
		// TODO Auto-generated method stub
		try {
			String whereClause = DOG_READ+" MATCH ?";
			String []whereArgs = {"1"};
			return mDb.delete(DOG_TABLE, whereClause, whereArgs) != -1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public Cursor getDogById(String id) {
		// TODO Auto-generated method stub
		return mDb.query(DOG_TABLE, new String[] { DOG_ID,
				DOG_NAME, DOG_AVATAR,  DOG_DESC, DOG_FAVOURITE, DOG_READ },
				DOG_ID + " MATCH ?",
				new String[] { "*" + id + "*" }, null, null, null);
	}
}
