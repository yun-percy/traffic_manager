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
				checkWifiData();
				checkGPRSData();
				checkpercent();
				handler.postDelayed(this, 2000);
			}
		};
		handler.postDelayed(runnable, 2000);
	}
	private void checkpercent(){
		SharedPreferences percentferences=getSharedPreferences("Gprs_data",0);
		float total_gprs=percentferences.getFloat("all", 0);
		float used_gprs=percentferences.getFloat("gprsdisplay", 0);
		int percent =0;
		percent=(int)(used_gprs/1024/1024/total_gprs*100);
		Editor edit =percentferences.edit();
		edit.putInt("percent",new Integer(percent));
		edit.commit();
	}
	private void checkGPRSData(){
		SharedPreferences ferences=getSharedPreferences("Gprs_data",0);  
		long total_2g_3g_received = TrafficStats.getMobileRxBytes();
		long total_2g_3g_transmitted  = TrafficStats.getMobileTxBytes();
		long total_2g_3g_true = total_2g_3g_received + total_2g_3g_transmitted;
		float base_Gprsdata=ferences.getFloat("gprsdatabase", 0);
		float old_Gprsdata=ferences.getFloat("gprsdisplay",0);
		Editor edit=ferences.edit();  
	    if(total_2g_3g_true<2*1024){
    	   edit.putFloat("gprsdatabase",new Float(old_Gprsdata));
       }
	   base_Gprsdata=ferences.getFloat("gprsdatabase", 0);
	   float display_data= base_Gprsdata+(float)total_2g_3g_true;
	   edit.putFloat("gprsdisplay", new Float(display_data));
	   edit.commit(); 
	}
	private void checkWifiData() {
		super.onCreate();
		SharedPreferences ferences=getSharedPreferences("wifi_data",0);  
       long total_received = TrafficStats.getTotalRxBytes();
		long total_transmitted = TrafficStats.getTotalTxBytes();
		long total = total_received + total_transmitted;
		long total_2g_3g_received = TrafficStats.getMobileRxBytes();
		long total_2g_3g_transmitted  = TrafficStats.getMobileTxBytes();
		long total_2g_3g_true = total_2g_3g_received + total_2g_3g_transmitted;
		long total_wifi = total - total_2g_3g_true;
       long old_wifidata=ferences.getLong("wifitemp", 0);  
       long all_wifidatatemp=ferences.getLong("wifitemp", 0);  
       Editor edit=ferences.edit(); 
       if(total_wifi<5*1024){
    	   edit.putLong("wifibase",new Long(all_wifidatatemp));
       }
       long wifi_base=ferences.getLong("wifibase",0 );
       edit.putLong("wifitemp",new Long(wifi_base+total_wifi));
       edit.commit(); 
	}
}
