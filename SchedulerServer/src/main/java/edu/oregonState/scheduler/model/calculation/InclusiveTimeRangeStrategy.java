package edu.oregonState.scheduler.model.calculation;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class InclusiveTimeRangeStrategy implements CalculationStrategy {
	private DateTime maxTime;
	private DateTime minTime;
	private String[] userIDs;
	private DateTimeBuilder builder = new DateTimeBuilder();
	
	public InclusiveTimeRangeStrategy(CalendarEvent timeRange){
		userIDs = timeRange.getUserIds();
		maxTime = builder.getDateTimeFromEndOfEvent(timeRange);
		minTime = builder.getDateTimeFromStartOfEvent(timeRange);
	}

	@Override
	public Schedule calculate(List<Schedule> schedules) {
		List<CalendarEvent> calendarEvents = new ArrayList<>();
		System.out.println("Min Time\t" + minTime.toString());
		System.out.println("Max Time\t" + maxTime.toString());		
		for(Schedule schedule : schedules){
			for(CalendarEvent event: schedule.getEvents()){
				DateTime startTime = builder.getDateTimeFromEndOfEvent(event);
				DateTime endTime = builder.getDateTimeFromStartOfEvent(event);
				System.out.println("StartTime\t" + startTime.toString());
				System.out.println("EndTime\t" + endTime.toString());
				if(!(endTime.isBefore(minTime) || maxTime.isBefore(startTime)) ){
					event.setUserIds(userIDs);
					calendarEvents.add(event);
					System.out.println("Inclusive Time Range Strategy returning: " + event.toString());
				}
			}			
		}
		CalendarEvent[] eventsArray= new CalendarEvent[calendarEvents.size()];		
		calendarEvents.toArray(eventsArray);
		return new Schedule(eventsArray);		
	}
	
	
}

