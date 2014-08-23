package com.yusun.traffic_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

public class BootBroadcastReceiver extends BroadcastReceiver {

static final String ACTION = "boot";

@Override
public void onReceive(Context context, Intent intent) {


Intent sayHelloIntent=new Intent(context,networkwriter.class);
System.out.println("云哥最帅了！！！！");
//context.startActivity(sayHelloIntent);
//Intent i = new Intent(StartActivity.this, StatusbarControlService.class);
context.startService(sayHelloIntent);
}
}

