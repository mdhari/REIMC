package com.log;

import java.util.Calendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import sun.util.calendar.LocalGregorianCalendar.Date;


public class DateConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long yourmilliseconds = 1352948897020L;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		 //Long.parseLong("0", 10) returns 0L
	/*	Date winterDate = new Date(yourmilliseconds);
		System.out.println(sdf.format(resultdate)); 
	*/	
		
		
		 Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(yourmilliseconds);
	         
         System.out.println(sdf.format(calendar.getTime()));
		
	}

}
