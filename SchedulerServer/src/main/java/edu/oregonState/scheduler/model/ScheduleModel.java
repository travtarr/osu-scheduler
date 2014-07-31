package edu.oregonState.scheduler.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.model.calculation.CalculationStrategy;
import edu.oregonState.scheduler.model.calculation.CalculationStrategyFactory;
import edu.oregonState.scheduler.model.calculation.CalculationType;
import edu.oregonState.scheduler.provider.ScheduleProvider;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

public class ScheduleModel {
	private final UserAuthenticationRepository userAuthenticationRepository;
	private final ScheduleProvider scheduleProvider;
	private final CalculationStrategyFactory calculationStrategyFactory;

		
	public ScheduleModel(
			UserAuthenticationRepository userAuthenticationRepository,
			ScheduleProvider scheduleProvider,
			CalculationStrategyFactory calculationStrategyFactory) {
		super();
		this.userAuthenticationRepository = userAuthenticationRepository;
		this.scheduleProvider = scheduleProvider;
		this.calculationStrategyFactory = calculationStrategyFactory;
	}

	public void addUser(UserDTO userData) {
		userAuthenticationRepository.addUser(userData);		
	}

	public Schedule calculateSchedule(CalculationType calculationType, Schedule schedule) {
		CalculationStrategy strategy = calculationStrategyFactory.getCalculationStrategy(calculationType);
		List<String> userIDs = getUserIDsFromSchedule(schedule);		
		List<Schedule> schedules = getSchedules(userIDs);		
		return strategy.calculate(schedules);
	}

	private List<String> getUserIDsFromSchedule(Schedule schedule) {
		CalendarEvent events[] = schedule.getEvents();
		Set<String> userIDs = new HashSet<>();
		for(CalendarEvent event : events){
			userIDs.addAll(Arrays.asList(event.getUserIds()));
		}
		return new ArrayList<String>(userIDs);
	}

	private List<Schedule> getSchedules(List<String> userIDs) {
		List<Schedule> schedules = new LinkedList<>();
		for(String userID : userIDs){
			Authentication userAuthentication = userAuthenticationRepository.getAuthentication(userID);
			schedules.add(scheduleProvider.getSchedule(userID, userAuthentication));
		}
		return schedules;
	}

}
