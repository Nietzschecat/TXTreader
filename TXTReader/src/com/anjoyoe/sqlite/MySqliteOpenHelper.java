package com.anjoyoe.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	final String CREATE_TABLE_SQL = "create table  if not exists book (book_id integer primary key , book_name,book_path,book_corver )";

	public MySqliteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);

	}

	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
