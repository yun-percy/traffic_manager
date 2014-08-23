package com.yusun.traffic_manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrafficManagerActivity extends Activity
{
	private TextView tv_traffic_2g_3g,all_traffic=null,remain_hit,percent_hit ;
	private TextView tv_traffic_wifi;
	private ListView lv_traffic_content;
	private TrafficAdapter adapter;
	CircularProgressDrawable drawable;
	private ImageView circle_image;
//	CircleProgress sector;
	private List<TrafficInfo> trafficInfos;

	private Timer timer;
	private TimerTask timerTask;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			adapter.notifyDataSetChanged();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent sayHelloIntent=new Intent(TrafficManagerActivity.this,networkwriter.class);
		this.startService(sayHelloIntent);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.traffic_manager);
		all_traffic=(TextView)findViewById(R.id.all_traffic_main);
		tv_traffic_2g_3g = (TextView) findViewById(R.id.tv_traffic_2g_3g);
		tv_traffic_wifi = (TextView) findViewById(R.id.tv_traffic_wifi);
		lv_traffic_content = (ListView) findViewById(R.id.lv_traffic_content);
		remain_hit=(TextView)findViewById(R.id.remain_hit);
		percent_hit=(TextView)findViewById(R.id.percent_hit);
		trafficInfos = new ArrayList<TrafficInfo>();
		initResolveInfos();
		adapter = new TrafficAdapter();
		lv_traffic_content.setAdapter(adapter);
		/////////////////////////自动更新数据///////////

		
		final Handler handler =new Handler();
		Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				setTotalTraffic();
				handler.postDelayed(this,20000);
			}
		};
		handler.postDelayed(runnable, 2000);
/////////////////////自动更新数据结束
	}
	@Override
	protected void onStart()
	{
		timer = new Timer();
		timerTask = new TimerTask()
		{
			@Override
			public void run()
			{
				Message msg = Message.obtain();
				handler.sendMessage(msg);
			}
		};
		timer.schedule(timerTask, 1000, 3000);
		super.onStart();
	}
	
	@Override
	protected void onStop()
	{
		if(timer != null)
		{
			timer.cancel();
			timer = null;
			timerTask = null;
		}
		super.onStop();
	}
	
	private void setTotalTraffic()
	{
//		++++++++++++++数据读取与转换++++++++
		 SharedPreferences ferences=getSharedPreferences("Gprs_data",0);  
        float all_data=ferences.getFloat("all", 20);  
        int percent =ferences.getInt("percent", 0);
        float total_2g_3g=ferences.getFloat("gprsdisplay",0);
        tv_traffic_2g_3g.setText(String.valueOf(total_2g_3g));  
        all_traffic.setText("流量套餐 \n"+String.valueOf(all_data)+"MB");
		 tv_traffic_2g_3g.setText("GPRS流量 \n" + TextFormater.dataSizeFormat(total_2g_3g));
//		++++++++++++++结束++++++++++++++
		//wifi流量统计
		SharedPreferences wififerences=getSharedPreferences("wifi_data",0);  
		long wifi_datanow=wififerences.getLong("wifitemp", 0); 
		tv_traffic_wifi.setText("wifi流量 \n" + TextFormater.dataSizeFormat(wifi_datanow));
//		##########################画圆##################
		if(percent >=100){
			percent=100;
		}
		percent_hit.setText(100-percent+"% 剩余");
		DecimalFormat remainhit_format = new DecimalFormat("##0.00");
		float remainhit_data=all_data-(total_2g_3g/(1024*1024));
		if(remainhit_data <0){
			remainhit_data=total_2g_3g/1024/1024-all_data;
			String remain_hitten=remainhit_format.format((remainhit_data));
			remain_hit.setText("已超出 "+remain_hitten+"MB");
			remain_hit.setTextColor(0xffba2835);
		}
		else if(remainhit_data >=0){
			String remain_hitten=remainhit_format.format((all_data-total_2g_3g/1024/1024));
			remain_hit.setText("剩余流量 "+remain_hitten+"MB");
			remain_hit.setTextColor(0xffffffff);
		}
		circle_image = (ImageView) findViewById(R.id.circle_image);
        drawable = new CircularProgressDrawable(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size),
                getResources().getColor(android.R.color.white),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_blue_dark));
        circle_image.setImageDrawable(drawable);
		final Animator currentAnimation;
    	currentAnimation = prepareStyle2Animation(percent);
    	currentAnimation.start();
    	circle_image.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View arg0) {
    			currentAnimation.start();
    		}
    	});
	}
	private void initResolveInfos()
	{
		trafficInfos.clear();
		PackageManager packageManager = this.getPackageManager();
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
		for(ResolveInfo resolveInfo : resolveInfos)
		{
			String name = resolveInfo.loadLabel(packageManager).toString();
			Drawable icon = resolveInfo.loadIcon(packageManager);
			String packageName = resolveInfo.activityInfo.packageName;
			int uid = 0;;
			try
			{
				PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
				uid = packageInfo.applicationInfo.uid;
				long received = TrafficStats.getUidRxBytes(uid);
				long transmitted = TrafficStats.getUidTxBytes(uid);
				if(received == -1 && transmitted == -1 || (received == 0 && transmitted == 0))
				{
					continue;
				}
			}
			catch (NameNotFoundException e)
			{
				e.printStackTrace();
			}
			TrafficInfo trafficInfo = new TrafficInfo();
			trafficInfo.setName(name);
			trafficInfo.setIcon(icon);
			trafficInfo.setUid(uid);
			trafficInfos.add(trafficInfo);
		}
	}
	private class TrafficAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			return trafficInfos.size();
		}
		@Override
		public Object getItem(int position)
		{
			return trafficInfos.get(position);
		}
		@Override
		public long getItemId(int position)
		{
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view;
			ViewHolder holder;
			TrafficInfo info = trafficInfos.get(position);
			if(convertView == null)
			{
				view = View.inflate(TrafficManagerActivity.this, R.layout.traffic_manager_item, null);
				holder = new ViewHolder();
				holder.iv_traffic_icon = (ImageView) view.findViewById(R.id.iv_traffic_icon);
				holder.tv_traffic_name = (TextView) view.findViewById(R.id.tv_traffic_name);
				holder.tv_traffic_received = (TextView) view.findViewById(R.id.tv_traffic_received);
				holder.tv_traffic_transmitted = (TextView) view.findViewById(R.id.tv_traffic_transmitted);
				view.setTag(holder);
			}
			else
			{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_traffic_icon.setImageDrawable(info.getIcon());
			holder.tv_traffic_name.setText(info.getName());
			long received = TrafficStats.getUidRxBytes(info.getUid());
			long transmitted = TrafficStats.getUidTxBytes(info.getUid());
			holder.tv_traffic_received.setText(TextFormater.dataSizeFormat(received));
			holder.tv_traffic_transmitted.setText(TextFormater.dataSizeFormat(transmitted));
			return view;
		}
	}
	private class ViewHolder
	{
		ImageView iv_traffic_icon;
		TextView tv_traffic_name;
		TextView tv_traffic_received;
		TextView tv_traffic_transmitted;
	}
	private Animator prepareStyle2Animation(int percent) {
        AnimatorSet animation = new AnimatorSet();
        float progress=(float)percent/100;
        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f,progress);
        progressAnimation.setDuration(3600);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, "ringColor", getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_dark));
        if(percent <50){
        	  colorAnimator = ObjectAnimator.ofInt(drawable, "ringColor", getResources().getColor(android.R.color.holo_green_light),
                     getResources().getColor(R.color.green_dark));
        }
        else if(percent <80){
        	colorAnimator = ObjectAnimator.ofInt(drawable, "ringColor", getResources().getColor(android.R.color.holo_green_light),
                    getResources().getColor(R.color.bule));
       
        }
        else if (percent <95){
        	colorAnimator = ObjectAnimator.ofInt(drawable, "ringColor", getResources().getColor(android.R.color.holo_green_light),
                    getResources().getColor(R.color.orign));
       
        }
        else{
         colorAnimator = ObjectAnimator.ofInt(drawable, "ringColor", getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_dark));
        }
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(3600);
        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_FILL_PROPERTY, 1f, 0f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());
        Animator invertedCircle = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_FILL_PROPERTY, 0f, 1f);
        invertedCircle.setDuration(2200);
        invertedCircle.setStartDelay(1500);
        invertedCircle.setInterpolator(new OvershootInterpolator());
        animation.playTogether(progressAnimation, colorAnimator,innerCircleAnimation,invertedCircle);
        return animation;
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
}
