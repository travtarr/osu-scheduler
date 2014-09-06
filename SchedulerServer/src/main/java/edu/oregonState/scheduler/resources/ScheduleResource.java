package edu.oregonState.scheduler.resources;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import edu.oregonState.scheduler.MainFactory;
import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Saying;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.model.calculation.CalcTypeMap;
import edu.oregonState.scheduler.model.calculation.CalculationType;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {
	private final ScheduleModel scheduleModel;
	private final CalcTypeMap calcTypeMap = new CalcTypeMap();
    
	public ScheduleResource(ScheduleModel scheduleMode){
		this.scheduleModel = scheduleMode;
	}
	
    public ScheduleResource(UserAuthenticationRepository repo) throws ConfigException{
    	scheduleModel = MainFactory.getScheduleModel(repo);
    }

//public Schedule getProcessedScedule(@QueryParam("querySchedule") Optional<Schedule> querySchedule)
	@GET
	@Timed
    public Schedule getProcessedScedule(Schedule schedule, @QueryParam("queryType") Optional<String> queryType) throws IOException {
		CalculationType calcType = calcTypeMap.getCalculationType(queryType.get());
		System.out.println("Schedule resource requested: " + schedule.toString());
		return scheduleModel.calculateSchedule(calcType,schedule);//TODO: add input later
    }

	@POST @Path("/web")
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Schedule postProcessedScedule(Schedule schedule, @QueryParam("queryType") Optional<String> queryType) throws IOException {
		CalculationType calcType = calcTypeMap.getCalculationType(queryType.get());
		System.out.println("Schedule resource requested: " + schedule.toString());
		return scheduleModel.calculateSchedule(calcType,schedule);//TODO: add input later
    }

}
