package edu.oregonState.scheduler.model.calculation;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import edu.oregonState.scheduler.core.CalendarEvent;

public class DateTimeBuilder {
	
	
	public DateTime getDateTimeFromStartOfEvent(CalendarEvent event){
		DateTime dateTime = new DateTime(event.getStartYear(),event.getStartMonth(),event.getStartDay(),event.getStartHour(),event.getStartMinute(),DateTimeZone.forOffsetHours(event.getTimeZoneOffset()));
		return dateTime;
	}
	
	public DateTime getDateTimeFromEndOfEvent(CalendarEvent event){
		DateTime dateTime = new DateTime(event.getEndYear(),event.getEndMonth(),event.getEndDay(),event.getEndHour(),event.getEndMinute(),DateTimeZone.forOffsetHours(event.getTimeZoneOffset()));
		return dateTime;
	}	
}
