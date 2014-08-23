package com.yusun.traffic_manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class traffic_settings extends Activity{
	private Button done ,cancel,fix;
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
		cancel=(Button)findViewById(R.id.cancel);
		done=(Button)findViewById(R.id.done);
		initView();
		
		fix=(Button)findViewById(R.id.fix);
		done.setOnClickListener(doneListener);
		fix.setOnClickListener(fixListener);
		cancel.setOnClickListener(cancelListener);
}
OnClickListener doneListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			  String all_data=all_traffic.getText().toString();  
             String used_data=used_traffic.getText().toString();  
             SharedPreferences preferences=getSharedPreferences("Gprs_data",0);  
             Editor edit=preferences.edit();  
             if(all_data.equals("")){
             }
             else {
            	 edit.putFloat("all", new Float(all_data)); 
             }
            if(used_data.equals("")){
            }
            else{
            	edit.putFloat("gprsdisplayMB",new Float(used_data));
            	float MBtoBYTES=preferences.getFloat("gprsdisplayMB", 0);
            	float used_data2=MBtoBYTES*1024*1024;
            	edit.putFloat("gprsdisplay",new Float(used_data2));
             }
             edit.commit();  
             Toast.makeText(traffic_settings.this, "设置成功",Toast.LENGTH_SHORT).show();
             float all_data1=preferences.getFloat("all", 20);  
             float used_data1=preferences.getFloat("gprsdisplay",20)/1024/1024;
          display_all.setText(String.valueOf(all_data1));  
          display_used.setText(String.valueOf(used_data1));  
          display_all.setVisibility(0);
          display_used.setVisibility(0);
 		   all_traffic.setVisibility(4);
 			used_traffic.setVisibility(4);
 			fix.setVisibility(0);
 			done.setVisibility(4);
 			cancel.setVisibility(4);
 			View view = getWindow().peekDecorView();
	        if (view != null) {
	            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
	        }
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
			cancel.setVisibility(0);
			
		}
	};
	OnClickListener cancelListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			display_all.setVisibility(0);
			display_used.setVisibility(0);
			all_traffic.setVisibility(4);
			used_traffic.setVisibility(4);
			fix.setVisibility(0);
			done.setVisibility(4);
			cancel.setVisibility(4);
			View view = getWindow().peekDecorView();
	        if (view != null) {
	            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
	        }
		}
	};
	private void initView(){
		SharedPreferences ferences=getSharedPreferences("Gprs_data",0);  
        float all_data=ferences.getFloat("all", 300);  
        float used_data=ferences.getFloat("gprsdisplay", 0)/1024/1024;  
        display_all.setText(String.valueOf(all_data));  
        display_used.setText(String.valueOf(used_data));  
	}
}