package com.anjoyoe.adapter;

import java.io.File;
import java.util.ArrayList;

import com.anjoyoe.luu.R;
import com.anjoyoe.tool.Tool;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapters extends AbstractAdapter<File> {
	

	public MyAdapters(Activity conn, ArrayList<File> beans) {
		super(conn, beans);
	}

	public int getCount() {
		return lists.size();
	}
	
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = inflater.inflate(R.layout.lists, null);
		File file = lists.get(position);
		ImageView image = (ImageView) convertView.findViewById(R.id.image);
		TextView number = (TextView) convertView.findViewById(R.id.numeber);

		if (file.isDirectory()) {
			image.setImageResource(R.drawable.cartoon_folder);
			if (file.list() == null) {
				number.setText("0 项");
			} else {
				number.setText(file.list().length + "项");
			}

		} else {
			image.setImageResource(R.drawable.cartoon_txt);
			number.setText(Tool.getSize(file.length()));
		}
		TextView name = (TextView) convertView.findViewById(R.id.list);
		TextView time = (TextView) convertView.findViewById(R.id.time);
		time.setText(Tool.getTime(file));
		name.setText(file.getName());

		return convertView;
	}



}
