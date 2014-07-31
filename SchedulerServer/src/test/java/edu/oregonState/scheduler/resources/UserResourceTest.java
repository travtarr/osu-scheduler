package edu.oregonState.scheduler.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.model.ScheduleModel;

public class UserResourceTest {
	private UserResource target;
	private ScheduleModel mockModel;

	@Before
	public void setUpBefore() throws Exception {
		mockModel = mock(ScheduleModel.class);
		target = new UserResource(mockModel);
	}

	@Test
	public void submitUserData_validInput_passesToScheduleModel() {
		//arrange
		UserDTO userData = new UserDTO("davidsbr","bkdson","rkljg34QA_awef");
		
		//act
		target.submitUserData(userData);
		
		//assert
		verify(mockModel,times(1)).addUser(userData);
	}

}
