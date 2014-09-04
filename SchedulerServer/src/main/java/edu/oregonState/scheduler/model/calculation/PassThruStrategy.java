package edu.oregonState.scheduler.model.calculation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class PassThruStrategy implements CalculationStrategy {

	@Override
	public Schedule calculate(List<Schedule> schedules) {
		LinkedList<CalendarEvent> events = new LinkedList<>();
		for(Schedule schedule: schedules){
			events.addAll(Arrays.asList(schedule.getEvents()));
		}
		CalendarEvent[] eventArray = new CalendarEvent[events.size()];
		events.toArray(eventArray);
		return new Schedule(eventArray);
	}

}
