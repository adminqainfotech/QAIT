package com.qait.automation.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class CustomFunctions {

	
	public static String generateUniqueString(int enghtOfString){
		return RandomStringUtils.randomAlphabetic(enghtOfString).toLowerCase();
	}
	
	public static String generateUniqueEmailId(){
		return "pennfosterqa12@gmail.com";
		//return RandomStringUtils.randomAlphabetic(2).toLowerCase()+String.valueOf(System.nanoTime())+"@gmail.com";
	}
	
	public static  void testClassName(String className){
		System.out.println("\n");
		System.out.println("############## TEST CLASS STARTED : - " +className.toUpperCase()+" ####################");
		System.out.println("\n");
	}
	
	public static  void testMethodName(String testMethodName){
		System.out.println("\n");
		System.out.println("********** TEST METHOD  : - " +testMethodName.toUpperCase()+" ***************");
		System.out.println("\n");
	}
	
	public static String generateUniquePhoneNumber(){
	/*	LocalDateTime now = LocalDateTime.now();
		String time = "180" + now.getDayOfMonth()+ now.getHour() +now.getMinute()+now.getSecond();
		if(time.length()<10)
			return "800" + now.getDayOfMonth()+ now.getHour() +now.getMinute()+now.getSecond()+"0";
		else
			return time;*/
		return "8884275320";
	}
	
	public static int getCurrentAge(String startDateString){
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	    Date startDate = null;
	    try {
	        startDate = df.parse(startDateString);
	        System.out.println(startDateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		Calendar cal = Calendar.getInstance();
	    cal.setTime(startDate);
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
		Period p = Period.between(birthday, today);
		return p.getYears();
	}
	
}
