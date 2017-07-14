package com.anjoyoe.luu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.anjoyoe.tool.BookPageFactory;
import com.anjoyoe.widget.Mymenu;
import com.anjoyoe.widget.PageWidget;

public class PageTurnActivity extends Activity {

	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	File filepath;
	private int screenWidth = 0;
	private int screenHeight = 0;
	// --------------------卢---------------
	public Mymenu mMenu;
	SeekBar bar;
	SharedPreferences pre;
	SharedPreferences.Editor ed;
	// --------------------lu--------------------

	private static final int[] resBitmap = { R.drawable.p1, R.drawable.p2,
			R.drawable.p3 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		Resources res = getResources();
		for (int r : resBitmap) {
			bitmaps.add(((BitmapDrawable) res.getDrawable(r)).getBitmap());
		}
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		mPageWidget = new PageWidget(this, screenWidth, screenHeight);
		pagefactory = new BookPageFactory(this, bitmaps, screenWidth,
				screenHeight);
		// ---------------------卢---------------------------------------

		pre = getSharedPreferences("activit",MODE_WORLD_WRITEABLE);
		ed = pre.edit();
		int b = pre.getInt("progress", 0);

		setContentView(R.layout.reads);
		LinearLayout liner = (LinearLayout) findViewById(R.id.reader);
		liner.addView(mPageWidget);
		mMenu = (Mymenu) findViewById(R.id.men);
		bar = (SeekBar) findViewById(R.id.seek);
		bar.setProgress(b);
		TextView size = (TextView) findViewById(R.id.textSize);
		size.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				mMenu.setVisibility(View.GONE);
				bar.setVisibility(View.VISIBLE);

			}
		});
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar ba) {
				ba.setMax(100);
				pagefactory.setTextSize(ba.getProgress()+10);
				pagefactory.onDraw(mCurPageCanvas);
				pagefactory.onDraw(mNextPageCanvas);
				mPageWidget.invalidate();
				ed.putInt("progress", ba.getProgress());
				ed.commit();

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

			}
		});

		// ----------------------------------------------

		mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bg));

		try {

			Intent intent = getIntent();
			File file = (File) intent.getSerializableExtra("way");
			filepath = file;
			pagefactory.openbook(filepath);
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			e1.printStackTrace();
			Toast.makeText(this, "电子书不存在,请将test.txt放在SD卡根目录下",
					Toast.LENGTH_SHORT).show();
		}

		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.dragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagefactory.isFirstPage())
								return false;
							mPageWidget.abortAnimation();
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if (pagefactory.isLastPage())
								return false;
							mPageWidget.abortAnimation();
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}
				return false;
			}
		});
	}

	// ---------------------卢红伟增加---------------------
	public void setBgBitmap() {
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.cartoon_girls));
		pagefactory.onDraw(mCurPageCanvas);
		pagefactory.onDraw(mNextPageCanvas);
		mPageWidget.invalidate();

	}



	// menu事件动画
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuOpened(int featureId, Menu menu) {

		if (mMenu != null) {
			if (mMenu.isShown()) {
				mMenu.setVisibility(View.GONE);
				mMenu.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.gone));
			} else {
				mMenu.setVisibility(View.VISIBLE);
				mMenu.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.show));
			}
		}
		return false;
	}

	// ----------------------------------------------------

}