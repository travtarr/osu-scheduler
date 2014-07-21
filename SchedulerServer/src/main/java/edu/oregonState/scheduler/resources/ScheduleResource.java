package edu.oregonState.scheduler.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import edu.oregonState.scheduler.MainFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Saying;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.model.calculation.CalcTypeMap;
import edu.oregonState.scheduler.model.calculation.CalculationType;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {
	private final ScheduleModel scheduleModel;
	private final CalcTypeMap calcTypeMap = new CalcTypeMap();
    public ScheduleResource() {
    	scheduleModel = MainFactory.getScheduleModel();
    }
    
    public ScheduleResource(ScheduleModel scheduleModel){
    	this.scheduleModel = scheduleModel;
    }

//public Schedule getProcessedScedule(@QueryParam("querySchedule") Optional<Schedule> querySchedule)
	@GET
	@Timed
    public Schedule getProcessedScedule(Schedule schedule, @QueryParam("queryType") Optional<String> queryType) {
		CalculationType calcType = calcTypeMap.getCalculationType(queryType.get());
		return scheduleModel.calculateSchedule(calcType,schedule);//TODO: add input later
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
