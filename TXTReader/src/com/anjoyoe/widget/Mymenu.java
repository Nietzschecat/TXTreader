package com.anjoyoe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anjoyoe.luu.PageTurnActivity;
import com.anjoyoe.luu.R;

public class Mymenu extends LinearLayout {
	PageTurnActivity mActivity;
	SeekBar seek;

	public Mymenu(Context context) {
		super(context);
	}

	public Mymenu(Context context2, AttributeSet set) {
		super(context2, set);
		if (context2 instanceof PageTurnActivity)
			mActivity = (PageTurnActivity) context2;
		LayoutInflater inflater = mActivity.getLayoutInflater();
		LinearLayout mLiner = (LinearLayout) inflater.inflate(R.layout.menu,
				null);
		addView(mLiner);
		TextView size = (TextView) findViewById(R.id.textSize);
		
		size.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				setVisibility(View.GONE);

			}
		});

	}

}
