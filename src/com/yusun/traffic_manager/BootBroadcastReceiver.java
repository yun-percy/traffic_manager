package com.yusun.traffic_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	  if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
	    	Intent sayHelloIntent=new Intent(context,networkwriter.class);
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	System.out.println("云哥最帅了！！！！");
	    	
	    	context.startService(sayHelloIntent);
	    	  }
	    }
	}
//static final String ACTION = "boot";
//
//@Override
//public void onReceive(Context context, Intent intent) {
//
//
//Intent sayHelloIntent=new Intent(context,networkwriter.class);
//System.out.println("云哥最帅了！！！！");
////context.startActivity(sayHelloIntent);
////Intent i = new Intent(StartActivity.this, StatusbarControlService.class);
//context.startService(sayHelloIntent);
//}
//}
//
