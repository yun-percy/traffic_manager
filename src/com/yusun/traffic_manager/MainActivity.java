package com.yusun.traffic_manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	TabHost tabHost = null;
	TextView t1,t2,t3;
	LinearLayout ll;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private ImageView cursor;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // TODO Auto-generated method stub
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	       {  
	           exitBy2Click();      //调用双击退出函数
	       }
	    return false;
	}
	private static Boolean isExit = false;
	     
	private void exitBy2Click() {
	    Timer tExit = null;
	    if (isExit == false) {
	        isExit = true; // 准备退出
	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	        tExit = new Timer();
	        tExit.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                isExit = false; // 取消退出
	            }
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
	     
	    } else {
	        finish();
	        System.exit(0);
	    }
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(this); // 获取壁纸管理器
		//获取当前壁纸
		Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		//将Drawable,转成Bitmap
		Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
		ll= (LinearLayout) this.findViewById(R.id.ll);
		//blur(bm,ll);
		blur(bm,ll);
		InitImageView();
		initTextView();
		initPagerViewer();

	}
	private void blur(Bitmap bkg, View view) { 
		   long startMs = System.currentTimeMillis(); 
		   float scaleFactor = 1; 
		   float radius = 20; 
		   //缩放比例
		        scaleFactor = 15; 
		        radius =2; 
		        ll= (LinearLayout) this.findViewById(R.id.ll);
		        Display mDisplay = getWindowManager().getDefaultDisplay();
		        int w = mDisplay.getWidth();
		        int h =mDisplay.getHeight();
		   Bitmap overlay = Bitmap.createBitmap((int) (w/scaleFactor), 
		           (int) (h/scaleFactor), Bitmap.Config.ARGB_8888); 
		   Canvas canvas = new Canvas(overlay); 
		   canvas.translate(-view.getLeft()/scaleFactor, -view.getTop()/scaleFactor); 
		   canvas.scale(1 / scaleFactor, 1 / scaleFactor); 
		   Paint paint = new Paint(); 
		   paint.setFlags(Paint.FILTER_BITMAP_FLAG); 
		   canvas.drawBitmap(bkg, 0, 0, paint); 
		 
		   //执行模糊
		   overlay = FastBlur.doBlur(overlay, (int)radius, true); 
		   
		   
		   view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay)); 
		} 
	private void initTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		
	}

	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		Intent intent = new Intent(context, TrafficManagerActivity.class);
		list.add(getView("A", intent));
		Intent intent2 = new Intent(context, traffic_settings.class);
		list.add(getView("B", intent2));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
		.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 2 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);
	}

	
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	public class MyPagerAdapter extends PagerAdapter{
		List<View> list =  new ArrayList<View>();
		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position,
				Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;
		int two = one * 2;

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
						t1.setTextColor(0xffffffff);
						t2.setTextColor(0xff909090);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
					t1.setTextColor(0xff909090);
					t2.setTextColor(0xffffffff);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);	
					t2.setTextColor(0xffffffff);
					t3.setTextColor(0xffaaaaaa);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					t2.setTextColor(0xffaaaaaa);
					t3.setTextColor(0xffffffff);
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}
	
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	};
}
