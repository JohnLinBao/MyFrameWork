package com.alfred.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sf = null;

	/**
	 * 返回当前系统时间 "yyyy-mm-dd HH:mm:ss"
	 * @return
	 */
	public String getCurrentDateByCalender(){
		int year =  Calendar.getInstance().get(Calendar.YEAR);
		int month =  Calendar.getInstance().get(Calendar.MONTH);
		int day =  Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int hour =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min =  Calendar.getInstance().get(Calendar.MINUTE);
		int sec =  Calendar.getInstance().get(Calendar.SECOND);
		return year+"-"+month+"-"+day+" "+hour+":"+min+":"+":"+sec;
	}

	/**
	 * 返回当前系统时间 yyyy-mm-dd HH:mm:ss
	 * @return
	 */
	public String getCurrentDate(){
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		return sf.format(date.getTime());
	}

	/**
	 * 向后推30天
	 * 
	 * @return
	 */
	public String getBackTime(String S_date) {
		long start_date = getStringToDate(S_date);
		long reduce_day = 30L * 24 * 3600 * 1000;
		return getDateToString(start_date + reduce_day);

	}

	/**
	 * 根据查询天数获取查询时间 比如265天是一年的哪一天
	 * 
	 * @return
	 */
	public String getTime(long query_day,String S_date, String E_date) {
		long reduce_day;
		long output_date;
		long start_date = getStringToDate(S_date);
		long end_date = getStringToDate(E_date);
		long differ_day = end_date - start_date;
		reduce_day = query_day * 24L * 3600 * 1000;
		if (differ_day <= reduce_day) {
			output_date = start_date;
		} else {
			output_date = end_date - reduce_day;
		}
		String time = getDateToString(output_date);
		return getDateToString(output_date);
	}

	/**
	 * 时间戳转换年月日字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateToString(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}

	/**
	 * 年月日字符串转换成时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static long getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

}
