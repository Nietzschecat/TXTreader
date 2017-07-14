package com.anjoyoe.luu;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.anjoyoe.Dao.BookDao;
import com.anjoyoe.adapter.MyAdapter;
import com.anjoyoe.entities.Bookbean;
import com.anjoyoe.sd.Book;
import com.anjoyoe.sqlite.MySqliteOpenHelper;

public class ShelfActivity extends Activity {

	ListView show;
	MySqliteOpenHelper dbHelper;
	MyAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// dbHelper = new MySqliteOpenHelper(this, "myBook.db", null, 1);
		// SQLiteDatabase db = dbHelper.getWritableDatabase();
		// // db.execSQL("insert into book values(null,?,?,?)", new String[] {
		// // "one",
		// // "two", "three" });
		// Cursor cursor = db.rawQuery("select* from book", null);
		// // db.delete("book", null, null);
		// ArrayList<String> lists = new ArrayList<String>();
		// while (cursor.moveToNext()) {
		// lists.add(cursor.getString(1));
		// }

		show = (ListView) findViewById(R.id.show);

		// show = (ListView) findViewById(R.id.show);
		// ArrayList<String> lists = new ArrayList<String>();
		// lists.add("aaaa");
		// lists.add("bb");
		// lists.add("cc");
		// lists.add("dd");
		// lists.add("ee");
		// MyAdapter adapter = new MyAdapter(this, lists);
		// show.setAdapter(adapter);

	}

	@Override
	protected void onResume() {

		ArrayList<Bookbean> lists = new ArrayList<Bookbean>();
		BookDao dao = new BookDao(this);
		dao.open();
		lists = dao.selectAll();
		dao.close();
		adapter = new MyAdapter(this, lists);
		show.setAdapter(adapter);
		super.onResume();

	}

	// ���ذ�����¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog builder = new Builder(this).create();
			builder.setTitle("提示");
			builder.setMessage("确定退出吗");
			builder.setButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					System.exit(0);
				}

			});
			builder.setButton2("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {

				}
			});
			builder.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	// ���menu
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "本地");
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent intent = new Intent(this, Book.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}