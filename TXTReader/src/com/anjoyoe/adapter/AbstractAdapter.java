package com.anjoyoe.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractAdapter<T> extends BaseAdapter {
	Activity context;
	List<T> lists;
	LayoutInflater inflater;

	public AbstractAdapter(Activity mactivity, List<T> lists) {
		this.context = mactivity;
		this.lists = lists;
		inflater = LayoutInflater.from(context);
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}
	public abstract View getView(int position, View convertView, ViewGroup arg2);
	public abstract int getCount();
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
