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

	/*
	 * Comment table
	 */

	public static final String COMMENT_TABLE = "COMMENT_TABLE";
	public static final String COMMENT_ID = "COMMENT_ID";
	public static final String COMMENT_DOG_ID = "COMMENT_DOG_ID";
	public static final String COMMENT_USER_AVATAR = "COMMENT_USER_AVATAR";
	public static final String COMMENT_DESC = "COMMENT_DESC";

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "AMKit_Database";
	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static final String DATABASE_DOG_CREATE = "create virtual table "
			+ DOG_TABLE + " USING FTS3(" + DOG_ID
			+ " text not null primary key," + DOG_NAME + " text," + DOG_DESC
			+ " text," + DOG_AVATAR + " text" +

			");";

	/*
	 * Comment table create
	 */

	private static final String DATABASE_COMMENT_CREATE = "create virtual table "
			+ COMMENT_TABLE
			+ " USING FTS3("
			+ COMMENT_ID
			+ " text not null primary key,"
			+ COMMENT_DOG_ID
			+ " text,"
			+ COMMENT_USER_AVATAR + " text," + COMMENT_DESC + " text" +

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

	public Cursor getDogList(String query) {

		return mDb.query(DOG_TABLE, new String[] { DOG_ID, DOG_NAME, DOG_DESC,
				DOG_AVATAR }, DOG_NAME + " MATCH ?", new String[] { "*" + query
				+ "*" }, null, null, null);
	}

	public boolean insertDog(Dog dog) throws SQLiteException {
		ContentValues insertedValue = new ContentValues();
		insertedValue.put(DOG_ID, dog.getId());
		insertedValue.put(DOG_NAME, dog.getName());
		insertedValue.put(DOG_DESC, dog.getDescription());
		insertedValue.put(DOG_AVATAR, dog.getAvatar());

		Cursor c = mDb.rawQuery("select *	from " + DOG_TABLE + " where "
				+ DOG_ID + "='" + dog.getId() + "'", null);
		if (c.getCount() < 1) {
			return mDb.insert(DOG_TABLE, null, insertedValue) != -1;
		} else {
			return false;
		}

	}

	/*
	 * get and insert comment
	 */

	public Cursor getComment(String query) {
		return mDb.query(COMMENT_TABLE, new String[] { COMMENT_ID,
				COMMENT_DOG_ID, COMMENT_USER_AVATAR, COMMENT_DESC },
				COMMENT_DOG_ID + " MATCH ?",
				new String[] { "*" + query + "*" }, null, null, null);

	}

	public boolean insertComment(Comment comment) throws SQLiteException {
		ContentValues insertedValue = new ContentValues();
		insertedValue.put(COMMENT_ID, comment.getId());
		insertedValue.put(COMMENT_DOG_ID, comment.getDogId());
		insertedValue.put(COMMENT_USER_AVATAR, comment.getUserAvatar());
		insertedValue.put(COMMENT_DESC, comment.getDescription());

		Cursor c = mDb.rawQuery("select *	from " + COMMENT_TABLE + " where "
				+ COMMENT_ID + "='" + comment.getId() + "'", null);
		if (c.getCount() < 1) {
			return mDb.insert(COMMENT_TABLE, null, insertedValue) != -1;
		} else {
			return false;
		}

	}
}
