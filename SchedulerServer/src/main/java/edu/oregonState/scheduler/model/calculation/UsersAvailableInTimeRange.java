package edu.oregonState.scheduler.model.calculation;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class UsersAvailableInTimeRange implements CalculationStrategy{
	private DateTime maxTime;
	private DateTime minTime;
	private Set<String> userIDs = new HashSet<>();
	private DateTimeBuilder builder = new DateTimeBuilder();
	private CalendarEvent timeRange;
	
	public UsersAvailableInTimeRange(CalendarEvent timeRange){
		this.timeRange = timeRange;
		userIDs.addAll(Arrays.asList(timeRange.getUserIds()));
		maxTime = builder.getDateTimeFromEndOfEvent(timeRange);
		minTime = builder.getDateTimeFromStartOfEvent(timeRange);
	}

	@Override
	public Schedule calculate(List<Schedule> schedules) {
		Map<String,List<Entry<DateTime,DateTime>>> busyTimes = new HashMap<>();
		initializeBusyIntervalMap(busyTimes);				
		populateBusyIntervalMap(schedules, busyTimes);
		
		for(Entry<String,List<Entry<DateTime,DateTime>>> entry : busyTimes.entrySet()){
			for(Entry<DateTime,DateTime> interval: entry.getValue()){
				String user = entry.getKey();
				DateTime startTime = interval.getKey();
				DateTime endTime = interval.getValue();
				if(!(endTime.isBefore(minTime) || maxTime.isBefore(startTime)) ){
					userIDs.remove(user);
				}
			}
		}
		CalendarEvent[] eventsArray= new CalendarEvent[]{timeRange};
		String[] userIDs = new String[this.userIDs.size()];
		this.userIDs.toArray(userIDs);		
		timeRange.setUserIds(userIDs);
		return new Schedule(eventsArray);		
	}

	private void initializeBusyIntervalMap(
			Map<String, List<Entry<DateTime, DateTime>>> busyTimes) {
		for(String userID : userIDs){
			busyTimes.put(userID, new ArrayList<Entry<DateTime,DateTime>>());
		}
	}

	private void populateBusyIntervalMap(List<Schedule> schedules,
			Map<String, List<Entry<DateTime, DateTime>>> busyTimes) {
		for(Schedule schedule : schedules){
			for(CalendarEvent event: schedule.getEvents()){
				DateTime startTime = builder.getDateTimeFromEndOfEvent(event);
				DateTime endTime = builder.getDateTimeFromStartOfEvent(event);
				for(String user: event.getUserIds()){
					if(busyTimes.containsKey(user)){
						List<Entry<DateTime,DateTime>> busyList = busyTimes.get(user);
						Entry<DateTime,DateTime> interval = new AbstractMap.SimpleEntry(startTime,endTime);
						busyList.add(interval);
					}
				}
			}			
		}
	}
	
}
