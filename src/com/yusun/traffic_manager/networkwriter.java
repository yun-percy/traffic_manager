package com.yusun.traffic_manager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
public class networkwriter extends Service {
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		final Handler handler =new Handler();
		Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkData();
				checkGPRSData();
				handler.postDelayed(this, 2000);
			}
		};
		handler.postDelayed(runnable, 2000);
	}
	private void checkGPRSData(){
		SharedPreferences ferences=getSharedPreferences("Gprs_data",0);  
		long total_2g_3g_received = TrafficStats.getMobileRxBytes();
		long total_2g_3g_transmitted  = TrafficStats.getMobileTxBytes();
		long total_2g_3g_true = total_2g_3g_received + total_2g_3g_transmitted;
		SharedPreferences ferences_settings=getSharedPreferences("softinfo",0);  
		float all_data=ferences_settings.getFloat("all", 20);  
		float used_data=ferences_settings.getFloat("used", 20);  
		float total_2g_3g =  total_2g_3g_received + total_2g_3g_transmitted + used_data*1024*1024;
		float mmtotal_2g_3g = total_2g_3g/(1024*1024);
		float base_data=0;
		float old_Gprsdata=ferences.getFloat("gprsdatatemp",0);
		Editor edit=ferences.edit();  
	    if(total_2g_3g_true<2*1024){
	    	   base_data=old_Gprsdata;
	    	   float gprs_true=base_data+(float)total_2g_3g_true;
	    	   edit.putFloat("gprsdatatemp",new Float(gprs_true));
	       }
	       edit.putFloat("gprsdatatemp",new Float(base_data+(float)total_2g_3g_true));
	       edit.commit(); 
	}
	private void checkData() {
		super.onCreate();
		SharedPreferences ferences=getSharedPreferences("wifi_data",0);  
       long total_received = TrafficStats.getTotalRxBytes();
		long total_transmitted = TrafficStats.getTotalTxBytes();
		long total = total_received + total_transmitted;
		long total_2g_3g_received = TrafficStats.getMobileRxBytes();
		long total_2g_3g_transmitted  = TrafficStats.getMobileTxBytes();
		long total_2g_3g_true = total_2g_3g_received + total_2g_3g_transmitted;
		long total_wifi = total - total_2g_3g_true;
       long all_wifidata=ferences.getLong("wifiold", 0);  
       long all_wifidatatemp=ferences.getLong("wifitemp", 0);  
       Editor edit=ferences.edit(); 
       if(total<2*1024){
    	   all_wifidata=all_wifidatatemp;
    	   long ｗifi_true=all_wifidata+total_wifi;
    	   edit.putLong("wifitemp",new Long(ｗifi_true));
       }
       edit.putLong("wifitemp",new Long(all_wifidata+total_wifi));
       edit.commit(); 
	}
}
