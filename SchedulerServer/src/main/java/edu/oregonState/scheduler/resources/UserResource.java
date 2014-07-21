package edu.oregonState.scheduler.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import edu.oregonState.scheduler.MainFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.core.UserData;
import edu.oregonState.scheduler.model.ScheduleModel;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	private final ScheduleModel scheduleModel;
    public UserResource() {
    	scheduleModel = MainFactory.getScheduleModel();
    }
    
    public UserResource(ScheduleModel scheduleModel){
    	this.scheduleModel = scheduleModel;
    }

//public Schedule getProcessedScedule(@QueryParam("querySchedule") Optional<Schedule> querySchedule)
	@POST
	@Timed
    public void submitUserData(UserData userData) {        
        scheduleModel.addUser(userData);
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
