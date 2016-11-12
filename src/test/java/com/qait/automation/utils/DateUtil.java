package com.qait.automation.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	static Calendar cal;
	static SimpleDateFormat s;
	String[] arr;

	public static String converttimestamp(Long unixSeconds) {
		Date date;
		date = new Date(unixSeconds);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the
																				// format
																				// of
																				// your
																				// date
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String converttimestamp(String unixSeconds) {
		return converttimestamp(Long.parseLong(unixSeconds));
	}

	public static String[] getNextDate(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, frequency);
		} else {
			cal.add(Calendar.YEAR, frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}

	public String[] getPreviousDate(String dateModule, int frequency) {
		cal = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		if (dateModule.equalsIgnoreCase("day")) {
			cal.add(Calendar.DATE, -frequency);
		} else if (dateModule.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, -frequency);
		} else {
			cal.add(Calendar.YEAR, -frequency);
		}
		String result = s.format(new Date(cal.getTimeInMillis()));
		String year = result.substring(0, 4);
		String month = result.substring(4, 6);
		String day = result.substring(6, 8);
		String[] date = { year, month, day };
		return date;
	}

	public String[] getDate(String dateText) {
		arr = new String[3];
		if (dateText.equalsIgnoreCase("Over 1 year ago")) {
			return getPreviousDate("year", 2);
		} else if (dateText.equalsIgnoreCase("Within past year")) {
			return getPreviousDate("year", 1);
		} else if (dateText.equalsIgnoreCase("Within 30 days from now")) {
			return getNextDate("day", 20);
		} else if (dateText.equalsIgnoreCase("Over 30 days from now")) {
			return getNextDate("month", 2);
		} else if (dateText.equalsIgnoreCase("NA")) {
			System.out.println("Step : date value is NA in data sheet\n");
			return null;
		} else {
			System.out.println("Step : date value in data sheet is invalid\n");
		}
		return null;

	}

	public static String getCurrentdateInStringWithGivenFormate(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());
		return date;
	}

	public static String getAddYearWithLessOnedayInStringWithGivenFormate(
			String formate, String yearToAdd) {
		int yearToAddInInteger = Integer.parseInt(yearToAdd);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 365 * yearToAddInInteger);
		Date nextYear = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		String ourformat = formatter.format(nextYear.getTime());
		return ourformat;

	}

}
