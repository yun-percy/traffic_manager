package com.yusun.traffic_manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class traffic_settings extends Activity{
	private Button done ,fix;
	private EditText all_traffic=null, used_traffic=null;
	private TextView display_all,display_used;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.traffic_settings);
		all_traffic=(EditText)findViewById(R.id.all_traffic);
		used_traffic=(EditText)findViewById(R.id.used_traffic);
		display_all=(TextView)findViewById(R.id.display_all);
		display_used=(TextView)findViewById(R.id.display_used);
		
		done=(Button)findViewById(R.id.done);
		initView();
		
		fix=(Button)findViewById(R.id.fix);
		done.setOnClickListener(doneListener);
		fix.setOnClickListener(fixListener);
		
}
OnClickListener doneListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			  String all_data=all_traffic.getText().toString();  
             String used_data=used_traffic.getText().toString();  
             SharedPreferences preferences=getSharedPreferences("softinfo",Context.MODE_WORLD_READABLE);  
             Editor edit=preferences.edit();  
             System.out.println ("##########"+all_data+"$$$$$$$$$$"+used_data);
             if(all_data.equals("")){
            	 System.out.println ("##########"+all_data);
            
             }
             else {
            	 edit.putFloat("all", new Float(all_data)); 
             }
            if(used_data.equals("")){
            	System.out.println ("##########"+all_data);
            }
            else{
            		 edit.putFloat("used",new Float(used_data)); 
            	 }
             
             
             edit.commit();  
             Toast.makeText(traffic_settings.this, "成功",Toast.LENGTH_LONG).show();  
//			Toast.makeText(traffic_settings.this,"test", Toast.LENGTH_SHORT).show(); 
//			String mall_traffic="100M";
//		 	mall_traffic=all_traffic.getText().toString();
//		 	display_all.setText("流量套餐为： "+mall_traffic+ "MB" );display_all.setVisibility(4);
             SharedPreferences ferences=getSharedPreferences("softinfo",0);  
            float all_data1=ferences.getFloat("all", 20);  
            float used_data1=ferences.getFloat("used",20);
          display_all.setText(String.valueOf(all_data1));  
          display_used.setText(String.valueOf(used_data));  
          display_all.setVisibility(0);
          display_used.setVisibility(0);
 			all_traffic.setVisibility(4);
 			used_traffic.setVisibility(4);
 			fix.setVisibility(0);
 			done.setVisibility(4);
		}
	};
	OnClickListener fixListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			display_all.setVisibility(4);
			display_used.setVisibility(4);
			all_traffic.setVisibility(0);
			used_traffic.setVisibility(0);
			fix.setVisibility(4);
			done.setVisibility(0);
			
		}
	};
	private void initView(){
		SharedPreferences ferences=getSharedPreferences("softinfo",0);  
        float all_data=ferences.getFloat("all", 300);  
        float used_data=ferences.getFloat("used", 0);  
        display_all.setText(String.valueOf(all_data));  
        display_used.setText(String.valueOf(used_data));  
	}
}