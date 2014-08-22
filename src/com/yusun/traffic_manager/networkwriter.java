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
		checkData();
		initView();
	}
	
	private void initView() {


		super.onCreate();
		
		SharedPreferences preferences=getSharedPreferences("softinfo",Context.MODE_WORLD_READABLE);  
       Editor edit=preferences.edit();  
       edit.putLong("wifi", new Long(500)); 
       edit.putFloat("yunge",new Float(20)); 
       edit.commit();  
	}
	private void checkData() {


		super.onCreate();
		
		SharedPreferences preferences=getSharedPreferences("softinfo",Context.MODE_WORLD_READABLE);  
       Editor edit=preferences.edit();  
       edit.putLong("wifi", new Long(500)); 
       edit.putFloat("yunge",new Float(20)); 
       edit.commit();  
	}

}
