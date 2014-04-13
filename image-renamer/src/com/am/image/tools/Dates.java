package com.am.image.tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Various convenience methods for working with dates.
 * 
 * @author amarinkovic
 *
 */
public class Dates {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	private static final DecimalFormat df = new DecimalFormat("####");

	public static String diff(Date d1, Date d2) {
		return diff(d1.getTime(), d2.getTime());
	}
	
	public static String diff(long l1, long l2) {
		long diff = l1 - l2;
		
		long secs = Math.abs(diff / 1000 % 60);
		long mils = Math.abs(diff % 1000);
		long mins = Math.abs(diff / (60 * 1000) % 60);
		long hour = Math.abs(diff / (60 * 60 * 1000));
		
		df.setMinimumIntegerDigits(2);
		
		StringBuffer sb = new StringBuffer();
		if(!"00".equals(hour)) {
			sb.append(df.format(hour)).append(":");
		}
		sb.append(df.format(mins)).append(":");
		sb.append(df.format(secs)).append(".");
		sb.append(mils);
		
		return sb.toString();
	}
	
	public static String toString(long date) {
		return sdf.format(new Date(date)); 
	}
}