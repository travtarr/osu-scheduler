package edu.oregonState.scheduler.provider;


import java.io.IOException;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.config.ConfigFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.User;

public class StubScheduleProvider implements ScheduleProvider {
	private final ScheduleProvider googleCalendarProvider;
	
	public StubScheduleProvider() throws ConfigException{
		googleCalendarProvider = new GoogleCalendarProvider(new ConfigFactory());
	}
	
	public Schedule makeSchedule() {
        CalendarEvent events[] = {getEvent()};
        Schedule schedule = new Schedule(events);
        return schedule;
	}

	private CalendarEvent getEvent(){
		CalendarEvent event = new CalendarEvent();
		event.setEndDay(1);
		event.setEndHour(12);
		event.setEndMinute(0);
		event.setEndMonth(1);
		event.setStartDay(1);
		event.setStartHour(11);
		event.setStartMinute(0);
		event.setStartMonth(1);
		event.setTimeZoneOffset(-5);
		event.setUserIds(new String[]{"davidsbr"});
		return event;
	}
	@Override
	public Schedule getSchedule(String userID, Authentication authentication) throws IOException {
		return googleCalendarProvider.getSchedule(userID, authentication);
	}
}
