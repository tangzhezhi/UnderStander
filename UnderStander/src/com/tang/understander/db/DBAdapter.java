package com.tang.understander.db;

import com.tang.understander.base.MyApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "understander.db";
	private static final int DATABASE_VERSION = 2;
	private static final int MAX_NUMBER = 50;

	private SQLiteDatabase db;
	private Context mContext;
	private DBHelper dbHelper;

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public DBAdapter(Context context) {
		this.mContext = context;
		dbHelper = new DBHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DBAdapter() {
		this.mContext = MyApplication.getInstance().getApplicationContext();
		dbHelper = new DBHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void open() throws SQLException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}

	public void close() {
		db.close();
	}

	

	 static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context mContext, String name, CursorFactory factory, int version) {
			super(mContext, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DBScript.CREATE_TABLE_TASKCURRENTDAY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading from version " + oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			db.execSQL(DBScript.DROP_TABLE_TASKCURRENTDAY);
			onCreate(db);
		}
	}

}