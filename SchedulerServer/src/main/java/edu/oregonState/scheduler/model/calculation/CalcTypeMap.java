package edu.oregonState.scheduler.model.calculation;

import java.util.HashMap;
import java.util.Map;

public class CalcTypeMap {
	private Map<String,CalculationType> calcTypeMap = new HashMap<>();
	public CalcTypeMap(){
		calcTypeMap.put(CalculationType.GetTimeSlotsAllAreAvailable, CalculationType.GivenTimeRangeAndUsersGetTimeSlotsAllAreAvailable);
		calcTypeMap.put(CalculationType.GetUsersAvailableEntireTime, CalculationType.GivenTimeRangeAndUsersGetUsersAvailableEntireTime);
		calcTypeMap.put(CalculationType.GetUsersSchedule, CalculationType.GivenTimeRangeGetUsersSchedule);
	}
	
	public CalculationType getCalculationType(String key){
		if (!calcTypeMap.containsKey(key)){
			throw new IllegalArgumentException("Key does not correspond to calculationType");
		} else {
			return calcTypeMap.get(key);
		}		
	}
}
