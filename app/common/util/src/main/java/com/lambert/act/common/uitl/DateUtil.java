package com.lambert.act.common.uitl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private final static Logger log = LoggerFactory.getLogger(DateUtil.class);

	public final static long ONE_DAY_SECONDS = 86400;

	public final static String shortFormat = "yyyyMMdd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String timeFormat = "HHmmss";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseDtFormat = "yyyy��MM��dd��";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";
	public final static long ONE_DAY_MILL_SECONDS = 86400000;

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		df.setLenient(false);
		return df;
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDateNoTime(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() < shortFormat.length())) {
			throw new ParseException("length too little", 0);
		}
		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}
		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}
		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}
		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
		sDate = sDate.replaceAll(delimit, "");
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);
		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}
		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}
		return d;
	}

	public static Date parseDateNewFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		Date d = null;
		dateFormat.setLenient(false);
		if ((sDate != null) && (sDate.length() == newFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}
		return d;
	}

	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	public static boolean isValidHour(String hourStr) {
		if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
			int hour = new Integer(hourStr).intValue();

			if ((hour >= 0) && (hour <= 23)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isValidMinuteOrSecond(String str) {
		if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
			int hour = new Integer(str).intValue();

			if ((hour >= 0) && (hour <= 59)) {
				return true;
			}
		}

		return false;
	}

	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * @return �����ʱ���ʽ��Ϊ"yyyyMMdd"
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * ȡ�á�X��X��X�ա������ڸ�ʽ
	 *
	 * @param date
	 *
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static long getDiffMinutes(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
	}

	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			Integer.parseInt(strDate);
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			Long.parseLong(strDate);
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortDateString(String strDate) {
		return getShortDateString(strDate, "-|/");
	}

	public static String getShortDateString(String strDate, String delimiter) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		String temp = strDate.replaceAll(delimiter, "");

		if (isValidShortDateFormat(temp)) {
			return temp;
		}

		return null;
	}

	public static String getShortFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	public static String getWebFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(webFormat);

		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);

			return formatOut.format(date);
		} catch (ParseException e) {
			log.warn("convert() --- orign date error: " + dateString);
			return "";
		}
	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);

			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			log.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
			return false;
		}
	}

	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatTimeRange(Date startDate, Date endDate, String format) {
		if ((endDate == null) || (startDate == null)) {
			return null;
		}

		String rt = null;
		long range = endDate.getTime() - startDate.getTime();
		long day = range / DateUtils.MILLIS_PER_DAY;
		long hour = (range % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
		long minute = (range % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

		if (range < 0) {
			day = 0;
			hour = 0;
			minute = 0;
		}

		rt = format.replaceAll("dd", String.valueOf(day));
		rt = rt.replaceAll("hh", String.valueOf(hour));
		rt = rt.replaceAll("mm", String.valueOf(minute));

		return rt;
	}

	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	public static boolean isBeforeNow(Date date) {
		if (date == null)
			return false;
		return date.compareTo(new Date()) < 0;
	}

	public static void main(String[] args) {
		System.out.println(isBeforeNow(new Date()));
	}

	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
}
