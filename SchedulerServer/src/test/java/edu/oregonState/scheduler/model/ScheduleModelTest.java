package edu.oregonState.scheduler.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.model.calculation.CalculationStrategy;
import edu.oregonState.scheduler.model.calculation.CalculationStrategyFactory;
import edu.oregonState.scheduler.model.calculation.CalculationType;
import edu.oregonState.scheduler.model.calculation.StubStrategy;
import edu.oregonState.scheduler.provider.ScheduleProvider;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

public class ScheduleModelTest {
	
	private static final String userID = "davidsbr";
	private UserAuthenticationRepository mockUserAuthenticationRepository;
	private ScheduleProvider mockScheduleProvider;
	private CalculationStrategyFactory mockCalculationStrategyFactory;
	private ScheduleModel target;
	private CalculationStrategy mockStrategy;
	private Schedule mockSchedule;
	private Authentication mockAuthentication;

	@Before
	public void setUpBefore() throws Exception {
		mockUserAuthenticationRepository = mock(UserAuthenticationRepository.class);		
		mockScheduleProvider = mock(ScheduleProvider.class);		
		mockCalculationStrategyFactory = mock(CalculationStrategyFactory.class);
		mockStrategy = mock(CalculationStrategy.class);
		mockAuthentication = mock(Authentication.class);
		
		when(mockUserAuthenticationRepository.getAuthentication(anyString())).thenReturn(mockAuthentication);
		when(mockScheduleProvider.getSchedule(userID,mockAuthentication)).thenReturn(mockSchedule);
		when(mockCalculationStrategyFactory.getCalculationStrategy(any(CalculationType.class))).thenReturn(mockStrategy);
		target = new ScheduleModel(mockUserAuthenticationRepository,mockScheduleProvider,mockCalculationStrategyFactory);
	}

	@Test
	public void addUser_validData_addsToUserRepo() {
		//arrange
		UserDTO userData = new UserDTO("testID", "testGoogleID", "testGoogleAuth");
		
		//act
		target.addUser(userData);
		
		//assert
		verify(mockUserAuthenticationRepository,times(1)).addUser(userData);		
	}

	
	@Test
	public void calculateSchedule_validInput_getsCalculationStrategy() {
		//arrange
		Schedule schedule = getSchedule();
		
		//act
		target.calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable, schedule);
		
		//assert
		verify(mockCalculationStrategyFactory,times(1)).getCalculationStrategy(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable);
	}
	
	@Test
	public void calculateSchedule_validInput_UsesCalculationStrategy() {
		//arrange
		Schedule schedule = getSchedule();
		
		//act
		target.calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable, schedule);
		
		//assert
		verify(mockStrategy,times(1)).calculate(any(List.class));
	}	
	
	@Test
	public void calculateSchedule_validInput_GetsAuthentication() {
		//arrange
		Schedule schedule = getSchedule();
		
		//act
		target.calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable, schedule);
		
		//assert
		verify(mockUserAuthenticationRepository,times(1)).getAuthentication(anyString());
	}		
	
	@Test
	public void calculateSchedule_validInput_getsSchedule() {
		//arrange
		Schedule schedule = getSchedule();
		
		//act
		target.calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable, schedule);
		
		//assert
		verify(mockScheduleProvider,times(1)).getSchedule(userID, mockAuthentication);
	}	
	
	private Schedule getSchedule() {
        CalendarEvent events[] = {getEvent()};
        Schedule schedule = new Schedule(events);
        return schedule;
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
		event.setUserIds(new String[]{userID});
		return event;
	}
}
