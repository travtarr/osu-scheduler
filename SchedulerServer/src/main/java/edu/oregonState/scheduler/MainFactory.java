package edu.oregonState.scheduler;

import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.model.calculation.CalculationStrategyFactory;
import edu.oregonState.scheduler.provider.StubScheduleProvider;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

final public class MainFactory {
	public static ScheduleModel getScheduleModel(){
		return new ScheduleModel(new UserAuthenticationRepository(), new StubScheduleProvider(), new CalculationStrategyFactory());
	}
}
