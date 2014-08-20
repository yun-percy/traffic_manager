package com.yusun.traffic_manager;

import java.text.DecimalFormat;

public class TextFormater
{
	public static String dataSizeFormat(float total_2g_3g)
	{
		DecimalFormat formater = new DecimalFormat("####.00");
		if(total_2g_3g < 1024)
		{
			return total_2g_3g + "byte";
		}
		else if(total_2g_3g < (1 << 20)) //左移20位，相当于1024 * 1024
		{
			float kSize = total_2g_3g/1024; //右移10位，相当于除以1024
			return formater.format(kSize) + "KB";
		}
		else if(total_2g_3g < (1 << 30)) //左移30位，相当于1024 * 1024 * 1024
		{
			float mSize = total_2g_3g/(1024*1024); //右移20位，相当于除以1024再除以1024
			return formater.format(mSize) + "MB";
		}
		else if(total_2g_3g < (1 << 40))
		{
			float gSize = total_2g_3g /(1024*1024*1024);
			return formater.format(gSize) + "GB";
		}
		else
		{
			return "size : error";
		}
	}
	
	public static String getSizeFromKB(long kSize)
	{
		return dataSizeFormat(kSize << 10);
	}

}
