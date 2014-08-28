package edu.oregonState.scheduler.resources;

import java.io.IOException;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.base.Optional;

import edu.oregonState.scheduler.MainFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.data.UserJDBIDAO;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.GoogleTokenProvider;
import edu.oregonState.scheduler.user.User;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

@Path("/user")

public class UserResource implements UserAuthenticationRepository{
	private final UserDAO userDAO;
	private final UserJDBIDAO userJDBIDAO;
	private final GoogleTokenProvider googleTokenProvider;
    public UserResource(UserDAO userDAO, UserJDBIDAO userJDBIDAO, GoogleTokenProvider googleTokenProvider) {
    	this.userDAO = userDAO;
    	this.userJDBIDAO = userJDBIDAO;
    	this.googleTokenProvider = googleTokenProvider;
    }

//public Schedule getProcessedScedule(@QueryParam("querySchedule") Optional<Schedule> querySchedule)
	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
    public void submitUserData(UserDTO userData) throws IOException {
		User user = new User(userData);
		user.setGoogleToken(googleTokenProvider.getRefreshToken(user.getGoogleToken()));
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
	public Authentication getAuthentication(String userID) throws IOException {
		String googleID = userJDBIDAO.findGoogleID(userID);
		String googleToken = googleTokenProvider.getAuthenticationToken(userJDBIDAO.findGoogleToken(userID));
		return new Authentication(googleToken, googleID);
	}	
}
