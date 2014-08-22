package com.yusun.traffic_manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;


public class networkwriter extends Service {
	
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		initLP();
		initView();
	}
	
	private void initView() {


		super.onCreate();
		System.out.println("云哥最帅了！！！！");
		SharedPreferences preferences=getSharedPreferences("softinfo",Context.MODE_WORLD_READABLE);  
       Editor edit=preferences.edit();  
       edit.putLong("wifi", new Long(500)); 
       edit.putFloat("yunge",new Float(20)); 
       edit.commit();  
	}

//	}
//	
	/*
	 * ��ʼ��������ֲ���,״̬����ʾͨ���ʶFLAG_FORCE_NOT_FULLSCREEN(mShowLP)������״̬����ȥ��ñ�־(mHideLP)
	 */
//	private void initLP() {
//		mShowLP = new WindowManager.LayoutParams();
//		mShowLP.flags = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//		mShowLP.gravity = Gravity.TOP;
//		mShowLP.format = PixelFormat.TRANSLUCENT;
//		mShowLP.height = 0;
//		mShowLP.width = 0;
//		mShowLP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//		
//		mHideLP = new WindowManager.LayoutParams();
//		mHideLP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//		mHideLP.gravity = Gravity.TOP;
//		mHideLP.format = PixelFormat.TRANSLUCENT;
//		mHideLP.height = 0;
//		mHideLP.width = 0;
//		mHideLP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//		
//		mTouchLP = new WindowManager.LayoutParams();
//		mTouchLP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//		mTouchLP.gravity = Gravity.TOP;
//		mTouchLP.format = PixelFormat.TRANSLUCENT;
//		//�����¼��Ŀ�ʼ����߶���Ϊ(0,30)���ɸ����Ҫ������
//		mTouchLP.height = 30;
//		mTouchLP.width = WindowManager.LayoutParams.MATCH_PARENT;
//		mTouchLP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//
//	}
	
	/*
	 * �ж��������룬����һ��ƫ�Ƽ�����״̬��
	 */
//	private OnTouchListener mTouchListener = new OnTouchListener() {
//		@Override
//		public boolean onTouch(View arg0, MotionEvent arg1) {
//			if (MotionEvent.ACTION_DOWN == arg1.getAction()) {
//				mDownY = (int) arg1.getY();
//			} else if (MotionEvent.ACTION_UP == arg1.getAction() && mDownY != -1) {
//				int offset = (int) (arg1.getY() - mDownY);
//				if (offset > 50) {
//					showStatusbar();
//				}
//				mDownY = -1;
//			}
//			return false;
//		}
//	};
//	
	/*
	 * ��ʾ״̬����5�������
	 */
//	private void showStatusbar() {
//		mWM.updateViewLayout(mView, mShowLP);
//		mHandler.postDelayed(mHideStatusbar, 5000);
//		mPost = true;
//	}
//	
//	/*
//	 * ����״̬��
//	 */
//	private Runnable mHideStatusbar = new Runnable() {
//		@Override
//		public void run() {
//			mPost = false;
//			mWM.updateViewLayout(mView, mHideLP);
//		}
//	};

}
