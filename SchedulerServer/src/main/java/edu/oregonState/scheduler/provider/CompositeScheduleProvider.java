package edu.oregonState.scheduler.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.config.ConfigFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.user.Authentication;

public class CompositeScheduleProvider implements ScheduleProvider {
	private final GoogleCalendarProvider googleCalendarProvider;
	private final CatalogScheduleProvider osuCatalogScheduleProvider;
	
	public CompositeScheduleProvider() throws ConfigException {
		super();
		googleCalendarProvider = new GoogleCalendarProvider(new ConfigFactory());
		this.osuCatalogScheduleProvider = new CatalogScheduleProvider();
	}
	
	@Override
	public Schedule getSchedule(String userID, Authentication authentication)
			throws IOException {
		Schedule googleSchedule = googleCalendarProvider.getSchedule(userID, authentication);
		Schedule osuSchedule = osuCatalogScheduleProvider.getSchedule(userID, authentication);
		int size = 0;
		ArrayList<CalendarEvent> events = new ArrayList<>();
		if(googleSchedule != null){
			size+= googleSchedule.getEvents().length;
			events.addAll(Arrays.asList(googleSchedule.getEvents()));
		}
		if(osuSchedule != null){
			size+= osuSchedule.getEvents().length;
			events.addAll(Arrays.asList(osuSchedule.getEvents()));
		}			
		CalendarEvent[] eventsArray = new CalendarEvent[size];
		events.toArray(eventsArray);
		Schedule fullSchedule = new Schedule(eventsArray);		
		return fullSchedule;
	}
	
	
}
