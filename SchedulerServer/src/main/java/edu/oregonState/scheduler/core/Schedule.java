package edu.oregonState.scheduler.core;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schedule {
	private CalendarEvent events[];

	public Schedule(CalendarEvent[] events) {
		super();
		this.events = events;
	}

    @JsonProperty
	public CalendarEvent[] getEvents() {
		return events;
	}

    @JsonProperty
	public void setEvents(CalendarEvent[] events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "Schedule [events=" + Arrays.toString(events) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(events);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (!Arrays.equals(events, other.events))
			return false;
		return true;
	}
	
	
}
