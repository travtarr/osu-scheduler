package edu.oregonState.scheduler.provider;

import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.user.Authentication;

public interface ScheduleProvider {
	public Schedule getSchedule(String userID, Authentication authentication);
}
