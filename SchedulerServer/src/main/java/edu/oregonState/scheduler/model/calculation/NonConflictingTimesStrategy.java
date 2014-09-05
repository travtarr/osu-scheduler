package edu.oregonState.scheduler.model.calculation;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class NonConflictingTimesStrategy implements CalculationStrategy {
	
	private Interval startInterval;
	private Set<String> userIDs = new HashSet<>();
	private DateTimeBuilder builder = new DateTimeBuilder();
	private CalendarEvent timeRange;
	
	
	public NonConflictingTimesStrategy(CalendarEvent timeRange){
		System.out.println("NonConflicting time range requested inside this range: " + timeRange.toString());
		this.timeRange = timeRange;
		userIDs.addAll(Arrays.asList(timeRange.getUserIds()));
		startInterval = new Interval(builder.getDateTimeFromStartOfEvent(timeRange),builder.getDateTimeFromEndOfEvent(timeRange));
	}

	@Override
	public Schedule calculate(List<Schedule> schedules) {
		Set<Interval> freeIntervals = new HashSet<>();
		freeIntervals.add(startInterval);
		
		for(Schedule schedule : schedules){			
			for(CalendarEvent event : schedule.getEvents()){
				System.out.println("Checking conflicts against: " + event.toString());
				Interval busyInterval = new Interval(builder.getDateTimeFromStartOfEvent(event),builder.getDateTimeFromEndOfEvent(event));
				Queue<Interval> checkTheseIntervals= new ArrayDeque<>();
				checkTheseIntervals.addAll(freeIntervals);
				freeIntervals = new HashSet<>();
				while(!checkTheseIntervals.isEmpty()){
					Interval freeInterval = checkTheseIntervals.poll();
					if (!intersect(freeInterval,busyInterval)){
						freeIntervals.add(freeInterval); //this is still valid
						System.out.println("This is still valid: " + freeInterval.toString());
						continue;
					}
					if(contains(busyInterval,freeInterval)){
						System.out.println("This is not valid: " + freeInterval.toString());
						//discard
						continue;
					}
					addIntervalOnLeftIfNeeded(freeIntervals, busyInterval,freeInterval);
					addIntervalOnRightIfNeeded(freeIntervals, busyInterval,freeInterval);					
				}							
			}
		}
		CalendarEvent[] eventsArray = getEventsArray(freeIntervals);
		
		Schedule schedule = new Schedule(eventsArray);
		System.out.println("NonConflictingTimesStrategy returning: " + schedule.toString());
		return new Schedule(eventsArray);	
	}

	private CalendarEvent[] getEventsArray(Set<Interval> freeIntervals) {
		CalendarEvent[] eventsArray= new CalendarEvent[freeIntervals.size()];
		List<CalendarEvent> events = new ArrayList<>();
		for(Interval interval : freeIntervals){
			CalendarEvent event = new CalendarEvent();
			event.setEndDay(interval.end.getDayOfMonth());
			event.setEndHour(interval.end.getHourOfDay());
			event.setEndMinute(interval.end.getMinuteOfHour());
			event.setEndMonth(interval.end.getMonthOfYear());
			event.setEndYear(interval.end.getYear());
			
			event.setStartDay(interval.start.getDayOfMonth());
			event.setStartHour(interval.start.getHourOfDay());
			event.setStartMinute(interval.start.getMinuteOfHour());
			event.setStartMonth(interval.start.getMonthOfYear());
			event.setStartYear(interval.start.getYear());
			System.out.println("Raw Offset is:" + interval.start.getZone().toTimeZone().getRawOffset());
			System.out.println("Offset is:" + interval.start.getZone().toTimeZone().getRawOffset()/3600000);
			
			event.setTimeZoneOffset(interval.start.getZone().toTimeZone().getRawOffset()/3600000);
			String[] userIDs = new String[this.userIDs.size()];
			this.userIDs.toArray(userIDs);
			event.setUserIds(userIDs);
			events.add(event);
		}
		
		events.toArray(eventsArray);
		return eventsArray;
	}

	private void addIntervalOnLeftIfNeeded(Set<Interval> freeIntervals,
			Interval busyInterval, Interval freeInterval) {
		if(freeInterval.end.isBefore(busyInterval.end)){
			Interval interval = new Interval(freeInterval.start, busyInterval.start);
			System.out.println("New interval made: " + interval.toString());
			freeIntervals.add(interval);
		}
	}

	private void addIntervalOnRightIfNeeded(Set<Interval> freeIntervals,
			Interval busyInterval, Interval freeInterval) {
		if(busyInterval.end.isBefore(freeInterval.end)){
			Interval interval = new Interval(busyInterval.end, freeInterval.end);
			freeIntervals.add(interval);
			System.out.println("New interval made: " + interval.toString());
		}
	}
	
	

	private boolean contains(Interval thisInterval, Interval containsThatInterval){
		if(thisInterval.start.isBefore(containsThatInterval.start) && thisInterval.end.isAfter(containsThatInterval.end))
			return true;
		return false;
	}
	
	private boolean intersect(Interval a, Interval b){
		if (b.end.isBefore(a.start))
			return false;
		if (b.start.isAfter(a.end))
			return false;		
		return true;
	}
	
	private class Interval{
		DateTime start;
		DateTime end;		
		
		Interval(DateTime start, DateTime end){
			this.start = start;
			this.end = end;
		}
	}
	
}
