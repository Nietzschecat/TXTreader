package com.anjoyoe.adapter;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.anjoyoe.Dao.BookDao;
import com.anjoyoe.entities.Bookbean;
import com.anjoyoe.luu.PageTurnActivity;
import com.anjoyoe.luu.R;

public class MyAdapter extends AbstractAdapter<Bookbean> {

	AlertDialog dialog;
	public static final int[] conver = { R.id.book1, R.id.book2, R.id.book3 };

	public MyAdapter(Activity context, ArrayList<Bookbean> lists) {
		super(context, lists);
	}

	public int getCount() {
		int book = lists.size();
		int y = 0;
		int z = 0;
		y = book % 3;
		if (y == 0) {
			z = book / 3;
		} else {
			z = (book / 3) + 1;
		}
		if (z < 4) {
			z = 4;
		}
		return z;
	}

	// position ��0��ʼ��
	public View getView(int position, View convertView, ViewGroup arg2) {

		convertView = inflater.inflate(R.layout.list, null);
		for (int i = 3 * position; i < 3 * (position + 1); i++) {
			TextView book = (TextView) convertView.findViewById(conver[i % 3]);
			final int j = i;
			if (i >= lists.size()) {
				book.setVisibility(View.GONE);
			} else {
				book.setVisibility(View.VISIBLE);
				book.setText(lists.get(i).bookName);
			}
			// ��ĵ����Ķ��¼�
			book.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					Bookbean bean = lists.get(j);
					Intent intent = new Intent(context, PageTurnActivity.class);
					File file = new File(bean.path);
					intent.putExtra("way", file);
					context.startActivity(intent);
					context.overridePendingTransition(R.anim.my_alpha_action,
							R.anim.out_alpha_action);
				}
			});
			// �鳤������dialogɾ��
			book.setOnLongClickListener(new OnLongClickListener() {
				public boolean onLongClick(View arg0) {
					dialog = new AlertDialog.Builder(context).create();
					dialog.setCanceledOnTouchOutside(true);
					dialog.show();
					dialog.setContentView(R.layout.dialog);
					ListView list = (ListView) dialog
							.findViewById(R.id.dialog1);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							context, R.layout.txt, new String[] { "删除", "清空" });
					list.setAdapter(adapter);
					list.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> listView,
								View arg1, int position, long arg3) {
							if (position == 0) {
								
								// adapter ��ɾ������¼���
								Bookbean bean = lists.get(j);
								BookDao dao = new BookDao(context);
								dao.open();
								dao.deletes(bean.path);
								dao.close();
								// ֱ��ɾ��ʵ�壬�ٽ���û���ˣ���ɾ���±꣬���ٽ��뻹�С�
								lists.remove(bean);
								notifyDataSetChanged();
								dialog.dismiss();

							} else if (position == 1) {
								BookDao dao = new BookDao(context);
								dao.open();
								dao.deleteAll();
								dao.close();
								// listsɾ����£�֪ͨadapter���¡�
								lists.clear();
								notifyDataSetChanged();
								dialog.dismiss();

							}

						}
					});

					return false;
				}
			});
		}

		return convertView;
	}

}
