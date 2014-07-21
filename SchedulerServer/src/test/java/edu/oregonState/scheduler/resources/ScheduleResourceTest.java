package edu.oregonState.scheduler.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.google.common.base.Optional;

import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.model.calculation.CalculationType;

public class ScheduleResourceTest {
	private ScheduleResource target;
		
	private ScheduleModel mockModel;
	private Schedule mockSchedule;
	private Schedule expected;

	@Before
	public void setUp() throws Exception {
		mockSchedule = mock(Schedule.class);
		expected = mock(Schedule.class);
		mockModel = mock(ScheduleModel.class);		
		target = new ScheduleResource(mockModel);
	}

	@Test
	public void scheduleResource_defaultSetUp_DoesNotReturnNull() {
		//arrange
		
		//act
		
		//assert
		assertNotNull(target);
	}

	@Test (expected = IllegalArgumentException.class)
	public void getProcessedScedule_illegalCalcType_throwsException() {
		//arrange
		
		//act
		Schedule result = target.getProcessedScedule(mock(Schedule.class), Optional.of("abdef"));		
		//assert
	}	
	
	@Test
	public void getProcessedScedule_noArgs_returnsASchedule() {
		//arrange
		when(mockModel.calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable, mockSchedule)).thenReturn(expected);
		//act
		Schedule actual = target.getProcessedScedule(mockSchedule, Optional.of("GetTimeSlotsAllAreAvailable"));
		
		//assert
		assertEquals(expected, actual);
	}

	@Test
	public void getProcessedScedule_noArgs_hasModelCalculateASchedule() {
		//arrange
		
		//act
		Schedule result = target.getProcessedScedule(mockSchedule, Optional.of("GetTimeSlotsAllAreAvailable"));
		
		//assert
		verify(mockModel,times(1)).calculateSchedule(CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable,mockSchedule);
	}
}
