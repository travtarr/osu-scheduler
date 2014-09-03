package edu.oregonState.scheduler.provider;

import java.io.IOException;

import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.user.Authentication;
import edu.oregonState.scheduler.user.User;

public interface ScheduleProvider {
	public Schedule getSchedule(String userID, Authentication authentication) throws IOException;
}
