package com.smarr.android.healthylifestyle.misc_class;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;



public class DateFormatsAndInfo {

	private DateTimeFormatter format = DateTimeFormat.forPattern("YYYY, MMMM, dd");
	
	public DateFormatsAndInfo(){
		
	}
	
	public DateTime getNextSaturdayFirstUse(DateTime date){
		//sunday is 7 monday is 1
		//sets the date of first use to the next upcoming saturday if it is not saturday
		if(date.getDayOfWeek()!=6){
			while(date.getDayOfWeek()!=6){
				date = date.plusDays(1);
			}
		}
		return date;
	}
	
	public String getDateFormat(DateTime date){
		return format.print(date);
	}
	
	public DateTime parseDate(String storedDate){
		return DateTime.parse(storedDate, format);
	}

}
