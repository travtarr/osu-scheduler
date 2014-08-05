package edu.oregonState.scheduler.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.Calendar.CalendarList.List;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.config.ConfigFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.User;


public class GoogleCalendarProvider implements ScheduleProvider {
	private final String clientSecret;
	private final String clientID;
	private final String redirectUrl;
	
	public GoogleCalendarProvider(ConfigFactory configFactory) throws ConfigException {
		super();
		Properties properties = configFactory.getProperties();
		this.clientSecret = properties.getProperty(ConfigFactory.secret);
		this.clientID = properties.getProperty(ConfigFactory.clientID);
		this.redirectUrl = properties.getProperty(ConfigFactory.redirectURI);
	}

	@Override
	public Schedule getSchedule(String userID, Authentication authentication) throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
				clientID, clientSecret, authentication.getToken(), redirectUrl).execute();
		GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
		Calendar service = new Calendar(httpTransport,jsonFactory,credential);
		
		//from  https://developers.google.com/google-apps/calendar/v3/reference/events/list
		String pageToken = null;
		java.util.List<Event> allEvents = new ArrayList<>();
		com.google.api.services.calendar.model.Events events;
		do {
			events = service.events().list("primary").setPageToken(pageToken).execute();
			allEvents.addAll(events.getItems());
			pageToken = events.getNextPageToken();
			
		} while (pageToken != null);
		
		//TODO: figure out recurring events
		java.util.List<CalendarEvent> calendarEvents = new ArrayList<>();
		for(Event event: allEvents){
			GregorianCalendar startTime = new GregorianCalendar();
			GregorianCalendar endTime = new GregorianCalendar();
			startTime.setTimeInMillis(event.getStart().getDateTime().getValue());
			endTime.setTimeInMillis(event.getStart().getDateTime().getValue());
			int timezone = event.getEnd().getDateTime().getTimeZoneShift();
			CalendarEvent calendarEvent = new CalendarEvent(
					startTime.get(GregorianCalendar.MONTH)+1,
					startTime.get(GregorianCalendar.DAY_OF_MONTH),
					startTime.get(GregorianCalendar.HOUR),
					startTime.get(GregorianCalendar.MINUTE),
					endTime.get(GregorianCalendar.MONTH)+1,
					endTime.get(GregorianCalendar.DAY_OF_MONTH),
					endTime.get(GregorianCalendar.HOUR),
					endTime.get(GregorianCalendar.MINUTE),
					timezone,
					new String[]{userID});
			calendarEvents.add(calendarEvent);
		}
		CalendarEvent[] calendarEventsArr = new CalendarEvent[calendarEvents.size()];
		calendarEvents.toArray(calendarEventsArr);
		return new Schedule(calendarEventsArr);
	}

}
