package edu.oregonState.scheduler.model.calculation;

import java.util.HashMap;
import java.util.Map;

public enum CalculationType {
	GivenTimeRangeGetUsersSchedule("GetUsersSchedule"),
	GivenTimeRangeAndUsersGetUsersAvailableEntireTime("GetUsersAvailableEntireTime"),
	GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable("GetTimeSlotsAllAreAvailable");
	
	public static String GetUsersSchedule = "GetUsersSchedule";
	public static String GetUsersAvailableEntireTime = "GetUsersAvailableEntireTime";
	public static String GetTimeSlotsAllAreAvailable = "GetTimeSlotsAllAreAvailable";
	

	
	private String key;
	private CalculationType(String key){
		key = this.key;
	}
	
	public String getKey(){
		return key;
	}
	
}
