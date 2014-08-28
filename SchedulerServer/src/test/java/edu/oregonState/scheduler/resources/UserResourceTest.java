package edu.oregonState.scheduler.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.data.UserJDBIDAO;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.user.GoogleTokenProvider;
import edu.oregonState.scheduler.user.User;

public class UserResourceTest {
	private UserResource target;
	private UserDAO mockUserDAO;
	private UserJDBIDAO mockuserJDBIDAO;
	private GoogleTokenProvider mockGoogleTokenProvider;

	@Before
	public void setUpBefore() throws Exception {
		mockUserDAO = mock(UserDAO.class);
		mockuserJDBIDAO = mock(UserJDBIDAO.class);
		mockGoogleTokenProvider = mock(GoogleTokenProvider.class);
		target = new UserResource(mockUserDAO, mockuserJDBIDAO, mockGoogleTokenProvider);
	}

	@Test
	public void submitUserData_validInput_passesToScheduleModel() throws IOException {
		//arrange
		UserDTO userData = new UserDTO("davidsbr","bkdson","rkljg34QA_awef");
		
		//act
		target.submitUserData(userData);
		
		//assert
		verify(mockUserDAO,times(1)).create(any(User.class));
	}

}
