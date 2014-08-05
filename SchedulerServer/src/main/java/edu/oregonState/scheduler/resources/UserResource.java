package edu.oregonState.scheduler.resources;

import io.dropwizard.hibernate.UnitOfWork;

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
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.data.UserJDBIDAO;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.User;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

@Path("/user")

public class UserResource implements UserAuthenticationRepository{
	private final UserDAO userDAO;
	private final UserJDBIDAO userJDBIDAO;
    public UserResource(UserDAO userDAO, UserJDBIDAO userJDBIDAO) {
    	this.userDAO = userDAO;
    	this.userJDBIDAO = userJDBIDAO;
    }

//public Schedule getProcessedScedule(@QueryParam("querySchedule") Optional<Schedule> querySchedule)
	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
    public void submitUserData(UserDTO userData) {
		User user = new User(userData);
		userDAO.create(user);
    }
	

	@UnitOfWork
	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Authentication getAuthentication(@QueryParam("userID") Optional<String> userID) {
		User user = userDAO.findByID(userID.get());
		return new Authentication(user.getGoogleToken(),user.getGoogleID());
	}

	@Override
	public Authentication getAuthentication(String userID) {
		String googleID = userJDBIDAO.findGoogleID(userID);
		String googleToken = userJDBIDAO.findGoogleToken(userID);
		return new Authentication(googleToken, googleID);
	}	
}
