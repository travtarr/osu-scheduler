package edu.oregonState.scheduler.model.calculation;

import java.util.List;

import edu.oregonState.scheduler.core.Schedule;

public interface CalculationStrategy {
	public Schedule calculate(List<Schedule> schedules);
}
