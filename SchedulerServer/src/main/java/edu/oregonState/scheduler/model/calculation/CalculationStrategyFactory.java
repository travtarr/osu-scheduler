package edu.oregonState.scheduler.model.calculation;

public class CalculationStrategyFactory {
	
	public CalculationStrategy getCalculationStrategy(CalculationType calculationType){
		switch (calculationType) {
		case GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable:
			return getGivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable();
		case GivenTimeRangeAndUsersGetUsersAvailableEntireTime:
			return getGivenTimeRangeAndUsersGetUsersAvailableEntireTime();
		case GivenTimeRangeGetUsersSchedule:
			return getGivenTimeRangeGetUsersSchedule();
	    default:
	    	throw new IllegalArgumentException("Unsupported calculation type");
		}
		
	}
	
	private CalculationStrategy getGivenTimeRangeGetUsersSchedule(){
		return PassThruStrategy();
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
