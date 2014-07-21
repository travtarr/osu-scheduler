package edu.oregonState.scheduler.core;

import java.util.Arrays;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CalendarEvent {
	
	@Min(value=1)
	@Max(value=12)
	private Integer startMonth;
	@Min(value=1)
	@Max(value=31)	
	private Integer startDay;
	@Min(value=0)
	@Max(value=23)	
	private Integer startHour;
	@Min(value=0)
	@Max(value=59)	
	private Integer startMinute;

	@Min(value=1)
	@Max(value=12)
	private Integer endMonth;
	@Min(value=1)
	@Max(value=31)	
	private Integer endDay;
	@Min(value=0)
	@Max(value=23)	
	private Integer endHour;
	@Min(value=0)
	@Max(value=59)	
	private Integer endMinute;
	
	@Min(value=-23)
	@Max(value=23)	
	private Integer TimeZoneOffset;
	
	private String[] userIds;

	
	
	public CalendarEvent(Integer startMonth, Integer startDay,
			Integer startHour, Integer startMinute, Integer endMonth,
			Integer endDay, Integer endHour, Integer endMinute,
			Integer timeZoneOffset, String[] userIds) {
		super();
		this.startMonth = startMonth;
		this.startDay = startDay;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endMonth = endMonth;
		this.endDay = endDay;
		this.endHour = endHour;
		this.endMinute = endMinute;
		TimeZoneOffset = timeZoneOffset;
		this.userIds = userIds;
	}
	
	

	public CalendarEvent() {
	}



	@Override
	public String toString() {
		return "CalendarEvent [startMonth=" + startMonth + ", startDay="
				+ startDay + ", startHour=" + startHour + ", startMinute="
				+ startMinute + ", endMonth=" + endMonth + ", endDay=" + endDay
				+ ", endHour=" + endHour + ", endMinute=" + endMinute
				+ ", TimeZoneOffset=" + TimeZoneOffset + ", userIds="
				+ Arrays.toString(userIds) + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((TimeZoneOffset == null) ? 0 : TimeZoneOffset.hashCode());
		result = prime * result + ((endDay == null) ? 0 : endDay.hashCode());
		result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
		result = prime * result
				+ ((endMinute == null) ? 0 : endMinute.hashCode());
		result = prime * result
				+ ((endMonth == null) ? 0 : endMonth.hashCode());
		result = prime * result
				+ ((startDay == null) ? 0 : startDay.hashCode());
		result = prime * result
				+ ((startHour == null) ? 0 : startHour.hashCode());
		result = prime * result
				+ ((startMinute == null) ? 0 : startMinute.hashCode());
		result = prime * result
				+ ((startMonth == null) ? 0 : startMonth.hashCode());
		result = prime * result + Arrays.hashCode(userIds);
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendarEvent other = (CalendarEvent) obj;
		if (TimeZoneOffset == null) {
			if (other.TimeZoneOffset != null)
				return false;
		} else if (!TimeZoneOffset.equals(other.TimeZoneOffset))
			return false;
		if (endDay == null) {
			if (other.endDay != null)
				return false;
		} else if (!endDay.equals(other.endDay))
			return false;
		if (endHour == null) {
			if (other.endHour != null)
				return false;
		} else if (!endHour.equals(other.endHour))
			return false;
		if (endMinute == null) {
			if (other.endMinute != null)
				return false;
		} else if (!endMinute.equals(other.endMinute))
			return false;
		if (endMonth == null) {
			if (other.endMonth != null)
				return false;
		} else if (!endMonth.equals(other.endMonth))
			return false;
		if (startDay == null) {
			if (other.startDay != null)
				return false;
		} else if (!startDay.equals(other.startDay))
			return false;
		if (startHour == null) {
			if (other.startHour != null)
				return false;
		} else if (!startHour.equals(other.startHour))
			return false;
		if (startMinute == null) {
			if (other.startMinute != null)
				return false;
		} else if (!startMinute.equals(other.startMinute))
			return false;
		if (startMonth == null) {
			if (other.startMonth != null)
				return false;
		} else if (!startMonth.equals(other.startMonth))
			return false;
		if (!Arrays.equals(userIds, other.userIds))
			return false;
		return true;
	}



	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}

	public Integer getTimeZoneOffset() {
		return TimeZoneOffset;
	}

	public void setTimeZoneOffset(Integer timeZoneOffset) {
		TimeZoneOffset = timeZoneOffset;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	
	
	
}
