package com.example.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日历日期相关工具类
 * 日期类型 * 年:y 月:M 日:d 时:h(12制)/H(24值) 分:m 秒:s 毫秒:S
 * @author lin
 * 
 */
public class DateUtils {

	/**
	 * 英文简写如：2010
	 */
	public static String FORMAT_Y = "yyyy";

	/**
	 * 英文简写如：12:01
	 */
	public static String FORMAT_HM = "HH:mm";

	/**
	 * 英文简写如：1-12 12:01
	 */
	public static String FORMAT_MDHM = "MM-dd HH:mm";

	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_YMD = "yyyy-MM-dd";

	/**
	 * 英文全称 如：2010-12-01 23:15
	 */
	public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL_SN = "yyyyMMddHHmmssS";

	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_YMD_CN = "yyyy年MM月dd日";

	/**
	 * 中文简写 如：2010年12月01日 12时
	 */
	public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

	/**
	 * 中文简写 如：2010年12月01日 12时12分
	 */
	public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	public static Calendar calendar = null;

	/**
	 * 默认格式 ：2010-12-01 23:15:06
	 */
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	private DateUtils() {
	}

	/**
	 * 获取日期格式为2015-09-16
	 * 
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	public static String getDate(int year, int monthOfYear, int dayOfMonth) {
		StringBuffer sb = new StringBuffer();
		sb.append(year);
		sb.append("-");
		if (monthOfYear < 9) {
			sb.append("0");
		}
		sb.append(monthOfYear + 1);
		sb.append("-");
		if (dayOfMonth < 10) {
			sb.append("0");
		}
		sb.append(dayOfMonth);
		return sb.toString();
	}

	/**
	 * 获取09：25格式时间
	 * 
	 * @param hourOfDay
	 * @param minute
	 * @return
	 */
	public static String getTime(int hourOfDay, int minute) {
		StringBuffer sb = new StringBuffer();
		if (hourOfDay < 10) {
			sb.append("0");
		}
		sb.append(hourOfDay);
		sb.append(":");
		if (minute < 10) {
			sb.append("0");
		}
		sb.append(minute);
		return sb.toString();

	}

	/**
	 * 得到当前系统时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数 可以作为文件名 该时间点是唯一的
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 获得当前系统时间 
	 * 2010-12-01 23:15:06
	 * 
	 * @return
	 */
	 public static String getCurrentTimeInString() {
	        return getTime(getCurrentTimeInLong());
	    }
	 
	 /**
	 * 根据格式化字符串 获得当前系统时间 
	 * 
	 * @param format
	 * @return
	 */
	 public static String getCurrentTimeFormat(String format) {
			return getTime(getCurrentTimeInLong(), format);
		}
	 
	/**
	 * 格式化毫秒时间为字符串
	 * 
	 * @param timeInMillis
	 * @param format
	 * @return
	 */
	public static String getTime(long timeInMillis, String format) {
		return new SimpleDateFormat(format, Locale.CHINA).format(new Date(
				timeInMillis));
	}
	
	/**
	 * 使用默认格式格式化毫秒时间为字符串
	 * 格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, FORMAT);
    }

	/**
	 * 获得当前日期的字符串格式
	 * 
	 * @param format
	 *            格式化的类型
	 * @return 返回格式化之后的事件
	 */
	public static String getCurDateStr(String format) {
		Calendar c = Calendar.getInstance();
		return Calendar2Str(c, format);
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param d
	 *            Date对象
	 * @param format
	 *            需要的日期格式
	 * @return
	 */
	public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
		if (d == null) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(d);
		return s;
	}

	/**
	 * 格式化输出日期
	 * 
	 * @param d
	 *            Date对象
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
		return date2Str(d, null);
	}


	/**
	 * 格式化解析日期文本
	 * 
	 * @param str
	 *            日期字符串
	 * @param format
	 *            日期字符串格式
	 * @return Date对象
	 */
	public static Date str2Date(String str, String format) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = FORMAT;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化解析日期文本
	 * 
	 * @param str
	 *            日期字符串
	 * @return Date对象 yyyy-MM-dd HH:mm:ss
	 */
	public static Date str2Date(String str) {
		return str2Date(str, null);
	}
	
	/**
	 * 格式化输出日期
	 * 
	 * @param c
	 *            Calendar对象
	 * @param format
	 *            需要的日期格式
	 * @return
	 */
	public static String Calendar2Str(Calendar c, String format) {
		if (c == null) {
			return null;
		}
		return date2Str(c.getTime(), format);
	}
	
	/**
	 * 格式化日期为Calendar对象
	 * 
	 * @param str
	 *            日期字符串
	 * @param format
	 *            日期字符串格式
	 * @return Calendar对象
	 */
	public static Calendar str2Calendar(String str, String format) {

		Date date = str2Date(str, format);
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}

	/**
	 * 格式化日期为Calendar对象
	 * 
	 * @param str
	 *            日期字符串
	 * @return Calendar对象
	 */
	public static Calendar str2Calendar(String str) {
		return str2Calendar(str, null);
	}

	/**
	 * 得到年
	 * 
	 * @param date
	 *            Date对象
	 * @return 年
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到月
	 * 
	 * @param date
	 *            Date对象
	 * @return 月
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;

	}

	/**
	 * 得到日
	 * 
	 * @param date
	 *            Date对象
	 * @return 日
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		calendar = Calendar.getInstance();

		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 在日期上增加数个整月
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return 增加数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return 增加之后的天数
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();

	}

	/**
	 * 获取距现在某一小时的时刻
	 * 
	 * @param format
	 *            格式化时间的格式
	 * @param h
	 *            距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
	 * @return 获取距现在某一小时的时刻
	 */
	public static String getNextHour(String format, int h) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		date.setTime(date.getTime() + h * 60 * 60 * 1000);
		return sdf.format(date);

	}

	/**
	 * 按默认格式算距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @return 按默认格式的字符串距离今天的天数
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(str2Date(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

	}

	/**
	 * 按用户格式字符串算距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return 按用户格式字符串距离今天的天数
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(str2Date(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;

	}

	/**
	 * 指定日期是否再系统日期之后
	 * 
	 * @param mytime
	 * @return
	 */
	public static boolean DateAfter(String mydate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date sysdate, selectdate;
		try {
			sysdate = sdf.parse(sdf.format(new Date()));
			selectdate = sdf.parse(mydate);
			// 测试此日期是否在指定日期之后。
			boolean flag = selectdate.after(sysdate);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 指定日期是否再系统日期前
	 * 
	 * @param mytime
	 * @return
	 */
	public static boolean DateBefore(String mydate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date sysdate, selectdate;
		try {
			sysdate = sdf.parse(sdf.format(new Date()));
			selectdate = sdf.parse(mydate);
			// 测试此日期是否在指定日期之前。
			boolean flag = selectdate.before(sysdate);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 指定时间是否在当前时间前
	 * 
	 * @param mytime
	 * @return
	 */
	public static boolean TimeBefore(String mytime) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		Date systime, selecttime;
		try {
			systime = sdf.parse(sdf.format(new Date()));
			selecttime = sdf.parse(mytime);
			// 测试此日期是否在指定日期之前。
			boolean flag = selecttime.before(systime);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 指定时间是否在当前时间后
	 * 
	 * @param mytime
	 * @return
	 */
	public static boolean TimeAfter(String mytime) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
		Date systime, selecttime;
		try {
			systime = sdf.parse(sdf.format(new Date()));
			selecttime = sdf.parse(mytime);
			// 测试此日期是否在指定日期之后。
			boolean flag = selecttime.after(systime);
			if (flag) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 人性化时间显示
	 * 
	 * @param date
	 *            时间字符串
	 * @return 格式化的时间
	 */
	public static String formatStringTime(String date) {
		return formatDateTime(str2Date(date, FORMAT_YMDHMS));
	}

	/**
	 * 人性化时间显示
	 * 
	 * @param date
	 *            date时间对象
	 * @return 格式化的时间
	 */
	public static String formatDateTime(Date date) {
		String text;
		long dateTime = date.getTime();
		if (isSameDay(dateTime)) {
			Calendar calendar = GregorianCalendar.getInstance();
			// 一分钟之内
			if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
				return "刚刚";
				// 一小时之内
			} else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
				return String.format("%d分钟之前",Math.abs(dateTime - calendar.getTimeInMillis()) / 60000);
			} else {
				calendar.setTime(date);
				int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
				// 17点之后
				if (hourOfDay > 17) {
					text = "晚上 hh:mm";
					// 0到6点
				} else if (hourOfDay >= 0 && hourOfDay <= 6) {
					text = "凌晨 hh:mm";
					// 11点到17点
				} else if (hourOfDay > 11 && hourOfDay <= 17) {
					text = "下午 hh:mm";
				} else {
					text = "上午 hh:mm";
				}
			}
		} else if (isYesterday(dateTime)) {
			text = "昨天 HH:mm";
		} else if (isBeforeYesterday(dateTime)) {
			text = "前天 HH:mm";
		} else if (isSameYear(dateTime)) {
			text = "M月d日 HH:mm";
		} else {
			text = "yyyy-M-d HH:mm";
		}

		// 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
		return new SimpleDateFormat(text, Locale.CHINA).format(date);
	}

	/** 一分钟之内，刚刚 */
	private static boolean inOneMinute(long time1, long time2) {
		return Math.abs(time1 - time2) < 60000;
	}

	/** 一小时之内，刚刚 */
	private static boolean inOneHour(long time1, long time2) {
		return Math.abs(time1 - time2) < 3600000;
	}

	/** 该时间是否为今天 */
	private static boolean isSameDay(long time) {
		long startTime = floorDay(Calendar.getInstance()).getTimeInMillis();
		long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
		return time > startTime && time < endTime;
	}

	/** 该时间是否为昨天 */
	private static boolean isYesterday(long time) {
		Calendar startCal;
		startCal = floorDay(Calendar.getInstance());
		startCal.add(Calendar.DAY_OF_MONTH, -1);
		long startTime = startCal.getTimeInMillis();

		Calendar endCal;
		endCal = ceilDay(Calendar.getInstance());
		endCal.add(Calendar.DAY_OF_MONTH, -1);
		long endTime = endCal.getTimeInMillis();
		return time > startTime && time < endTime;
	}

	/** 该时间是否为前天 */
	private static boolean isBeforeYesterday(long time) {
		Calendar startCal;
		startCal = floorDay(Calendar.getInstance());
		startCal.add(Calendar.DAY_OF_MONTH, -2);
		long startTime = startCal.getTimeInMillis();

		Calendar endCal;
		endCal = ceilDay(Calendar.getInstance());
		endCal.add(Calendar.DAY_OF_MONTH, -2);
		long endTime = endCal.getTimeInMillis();
		return time > startTime && time < endTime;
	}

	/** 该时间是否为今年 */
	private static boolean isSameYear(long time) {
		Calendar startCal;
		startCal = floorDay(Calendar.getInstance());
		startCal.set(Calendar.MONTH, Calendar.JANUARY);
		startCal.set(Calendar.DAY_OF_MONTH, 1);
		return time >= startCal.getTimeInMillis();
	}

	/** 设置时间为00：00：00：0 */
	private static Calendar floorDay(Calendar startCal) {
		// 将给定的日历字段设置为给定值。
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		return startCal;
	}

	/** 设置时间为23：59：59：999 */
	private static Calendar ceilDay(Calendar endCal) {
		endCal.set(Calendar.HOUR_OF_DAY, 23);
		endCal.set(Calendar.MINUTE, 59);
		endCal.set(Calendar.SECOND, 59);
		endCal.set(Calendar.MILLISECOND, 999);
		return endCal;
	}

	/**
	 * 时间格式转换 获取传入时间和当前时间的差值
	 * */
	public static String formatdatatime(String time) {

		long days = 0;
		long hours = 0;
		long minutes = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		try {

			Date d1 = df.parse(time);
			long enterTime = System.currentTimeMillis();
			long diff = enterTime - d1.getTime();// 这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);
			hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
		} catch (ParseException e) {
		}
		if (days > 0) {
			return "" + days + "天";
		} else if (hours > 0) {
			return "" + hours + "小时";
		} else {
			return "" + minutes + "分钟";
		}
	}
}
