package edu.oregonState.scheduler.model.calculation;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class CalculationStrategyFactory {
	
	public CalculationStrategy getCalculationStrategy(CalculationType calculationType, Schedule schedule){
		switch (calculationType) {
		case GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable:
			return getGivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable(schedule.getEvents()[0]);
		case GivenTimeRangeAndUsersGetUsersAvailableEntireTime:
			return getGivenTimeRangeAndUsersGetUsersAvailableEntireTime(schedule.getEvents()[0]);
		case GivenTimeRangeGetUsersSchedule:
			return getGivenTimeRangeGetUsersSchedule(schedule.getEvents()[0]);
	    default:
	    	throw new IllegalArgumentException("Unsupported calculation type");
		}
		
	}
	
	private CalculationStrategy getGivenTimeRangeGetUsersSchedule(CalendarEvent range){
		return new InclusiveTimeRangeStrategy(range);
	}

	private CalculationStrategy PassThruStrategy() {
		return new PassThruStrategy();
	}

	private CalculationStrategy getGivenTimeRangeAndUsersGetUsersAvailableEntireTime(CalendarEvent range){
		return new UsersAvailableInTimeRange(range);
	}

	private CalculationStrategy getGivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable(CalendarEvent range){
		return new NonConflictingTimesStrategy(range);
	}
	
	private CalculationStrategy getStubCalculationStrategy(){
		return new StubStrategy();
	}
	
}
