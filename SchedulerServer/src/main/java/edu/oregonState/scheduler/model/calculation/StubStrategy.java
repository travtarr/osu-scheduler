package edu.oregonState.scheduler.model.calculation;

import java.util.List;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class StubStrategy implements CalculationStrategy {

	public Schedule calculate(List<Schedule> schedules) {
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
}
