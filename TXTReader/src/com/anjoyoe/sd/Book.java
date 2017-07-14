package com.anjoyoe.sd;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.anjoyoe.Dao.BookDao;
import com.anjoyoe.adapter.MyAdapters;
import com.anjoyoe.entities.Bookbean;
import com.anjoyoe.luu.PageTurnActivity;
import com.anjoyoe.luu.R;
import com.anjoyoe.sqlite.MySqliteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Book extends Activity {
	ListView show;
	ArrayList<File> lists;
	File parent;
	EditText way;
	Button back;
	MySqliteOpenHelper openHelper;
	SQLiteDatabase db;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mains);
		show = (ListView) findViewById(R.id.show);
		way = (EditText) findViewById(R.id.way);
		back = (Button) findViewById(R.id.back);
		parent = Environment.getExternalStorageDirectory();
		// ����getList();
		getList(parent);
		// �б?���¼���
		show.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View arg1,
					int position, long arg3) {
				File file = (File) listView.getItemAtPosition(position);
				if (file.isDirectory()) {
					File[] tmp = file.listFiles();
					if (tmp == null || tmp.length == 0) {
						Toast.makeText(Book.this, "文件不存在或为空", Toast.LENGTH_LONG)
								.show();
					} else {
						parent = file;
						getList(parent);
					}
				} else {
					if (file.getName().endsWith(".txt")) {
						// ������ļ�txt�ļ�����,����Read����ȡ��ͬʱ��ӵ���ݿ�
						Bookbean bean = new Bookbean();
						bean.bookName = file.getName();
						bean.path = file.getAbsolutePath();

						BookDao dao = new BookDao(Book.this);
						dao.open();
						if (dao.exists(file.getAbsolutePath())) {
							Toast.makeText(Book.this, "在书架上呢",
									Toast.LENGTH_LONG).show();
						} else {
							dao.inserts(bean);
						}

						dao.close();
						// ContentValues values = new ContentValues();
						// values.put("book_name", file.getName());
						// values.put("book_path", file.getAbsolutePath());
						// db.insert("book", null, values);
						Intent intent = new Intent(Book.this, PageTurnActivity.class);
						intent.putExtra("way", file);

						startActivity(intent);
						
					}
				}
			}
		});
		// ���ذ�ť�ĵ����¼�
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (parent.equals(Environment.getExternalStorageDirectory())) {
					return;
				} else {
					parent = parent.getParentFile();
					getList(parent);

				}

			}
		});

	}

	// ���ذ�������
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (parent.equals(Environment.getExternalStorageDirectory())) {
				return super.onKeyDown(keyCode, event);

			} else {
				parent = parent.getParentFile();
				getList(parent);
				return true;

			}
		}
		return super.onKeyDown(keyCode, event);
	}

	// �õ�list
	public void getList(File files) {
		way.setText(files.getAbsolutePath());
		Set<File> fileSet = new TreeSet<File>();
		Set<File> dirSet = new TreeSet<File>();
		for (File f : files.listFiles()) {
			if (f.isDirectory()) {
				dirSet.add(f);
			} else if (f.getName().endsWith(".txt")) {
				fileSet.add(f);
			}
		}
		lists = new ArrayList<File>(dirSet);
		lists.addAll(fileSet);
		MyAdapters adapter = new MyAdapters(this, lists);
		show.setAdapter(adapter);
	}
}
