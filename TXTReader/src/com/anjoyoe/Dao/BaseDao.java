package com.anjoyoe.Dao;

import com.anjoyoe.sqlite.MySqliteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BaseDao {
	public static final String BOOK_TAB = "myBook.db";
	Context mContext;
	MySqliteOpenHelper dbHelper;
	SQLiteDatabase db;

	public BaseDao(Context context) {
		mContext = context;

	}

	public void open() {
		dbHelper = new MySqliteOpenHelper(mContext, BOOK_TAB, null, 1);

		if (db == null || !db.isOpen()) {
			db = dbHelper.getWritableDatabase();
		}
	}

	public void close() {
		dbHelper.close();
	}

	public long insert(String table, ContentValues values) {

		return db.insert(table, null, values);
	}

	public void delete(String table, String whereClause, String[] whereArgs) {
		db.delete(table, whereClause, whereArgs);
	}

	public Cursor query(String table, String[] colmans) {
		return db.query(table, colmans, null, null, null, null, null);
	}

	public boolean exists(String table, String id, String path) {
		boolean isexists;
		Cursor cur = db.query(table, new String[] { id }, id + "= ?",
				new String[] { path }, null, null, null);
		if (cur.moveToFirst()) {
			isexists = true;
		} else {
			isexists = false;
		}
		cur.close();
		return isexists;
	}

}
