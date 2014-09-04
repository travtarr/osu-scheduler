package edu.oregonState.scheduler.model.calculation;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;

public class CalculationStrategyFactory {
	
	public CalculationStrategy getCalculationStrategy(CalculationType calculationType, Schedule schedule){
		switch (calculationType) {
		case GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable:
			return getGivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable();
		case GivenTimeRangeAndUsersGetUsersAvailableEntireTime:
			return getGivenTimeRangeAndUsersGetUsersAvailableEntireTime();
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

	private CalculationStrategy getGivenTimeRangeAndUsersGetUsersAvailableEntireTime(){
		return getStubCalculationStrategy();
	}

	private CalculationStrategy getGivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable(){
		return getStubCalculationStrategy();
	}
	
	private CalculationStrategy getStubCalculationStrategy(){
		return new StubStrategy();
	}
	
}
