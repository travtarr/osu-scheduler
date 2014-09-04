package edu.oregonState.scheduler.core;

import java.util.Arrays;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private Integer timeZoneOffset;
	
	@Min(1990)
	@Max(2020)
	private Integer startYear;

	@Min(1990)
	@Max(2020)
	private Integer endYear;
	
	private String[] userIds;
	
	public CalendarEvent(Integer startYear, Integer startMonth, Integer startDay,
			Integer startHour, Integer startMinute, Integer endYear, Integer endMonth,
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
		this.timeZoneOffset = timeZoneOffset;
		this.userIds = userIds;
		this.startYear = startYear;
		this.endYear = endYear;
	}
	
	

	public CalendarEvent() {
	}




    @Override
	public String toString() {
		return "CalendarEvent [startMonth=" + startMonth + ", startDay="
				+ startDay + ", startHour=" + startHour + ", startMinute="
				+ startMinute + ", endMonth=" + endMonth + ", endDay=" + endDay
				+ ", endHour=" + endHour + ", endMinute=" + endMinute
				+ ", TimeZoneOffset=" + timeZoneOffset + ", startYear="
				+ startYear + ", endYear=" + endYear + ", userIds="
				+ Arrays.toString(userIds) + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timeZoneOffset == null) ? 0 : timeZoneOffset.hashCode());
		result = prime * result + ((endDay == null) ? 0 : endDay.hashCode());
		result = prime * result + ((endHour == null) ? 0 : endHour.hashCode());
		result = prime * result
				+ ((endMinute == null) ? 0 : endMinute.hashCode());
		result = prime * result
				+ ((endMonth == null) ? 0 : endMonth.hashCode());
		result = prime * result + ((endYear == null) ? 0 : endYear.hashCode());
		result = prime * result
				+ ((startDay == null) ? 0 : startDay.hashCode());
		result = prime * result
				+ ((startHour == null) ? 0 : startHour.hashCode());
		result = prime * result
				+ ((startMinute == null) ? 0 : startMinute.hashCode());
		result = prime * result
				+ ((startMonth == null) ? 0 : startMonth.hashCode());
		result = prime * result
				+ ((startYear == null) ? 0 : startYear.hashCode());
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
		if (timeZoneOffset == null) {
			if (other.timeZoneOffset != null)
				return false;
		} else if (!timeZoneOffset.equals(other.timeZoneOffset))
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
		if (endYear == null) {
			if (other.endYear != null)
				return false;
		} else if (!endYear.equals(other.endYear))
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
		if (startYear == null) {
			if (other.startYear != null)
				return false;
		} else if (!startYear.equals(other.startYear))
			return false;
		if (!Arrays.equals(userIds, other.userIds))
			return false;
		return true;
	}



	@JsonProperty
	public Integer getStartMonth() {
		return startMonth;
	}
    
    @JsonProperty
	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

    @JsonProperty
	public Integer getStartDay() {
		return startDay;
	}

    @JsonProperty
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

    @JsonProperty
	public Integer getStartHour() {
		return startHour;
	}

    @JsonProperty
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

    @JsonProperty
	public Integer getStartMinute() {
		return startMinute;
	}

    @JsonProperty
	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}

    @JsonProperty
	public Integer getEndMonth() {
		return endMonth;
	}

    @JsonProperty
	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

    @JsonProperty
	public Integer getEndDay() {
		return endDay;
	}

    @JsonProperty
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

    @JsonProperty
	public Integer getEndHour() {
		return endHour;
	}

    @JsonProperty
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

    @JsonProperty
	public Integer getEndMinute() {
		return endMinute;
	}

    @JsonProperty
	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}

    @JsonProperty
	public Integer getStartYear() {
		return startYear;
	}

    @JsonProperty
	public Integer getEndYear() {
		return endYear;
	}
    
    @JsonProperty
    public void setEndYear(Integer endYear){
    	this.endYear = endYear;
    }

    @JsonProperty
    public void setStartYear(Integer startYear){
    	this.startYear = startYear;
    }
    
    @JsonProperty
	public void setTimeZoneOffset(Integer timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

    @JsonProperty
	public Integer getTimeZoneOffset() {
		return timeZoneOffset;
	}
    
    @JsonProperty
	public String[] getUserIds() {
		return userIds;
	}

    @JsonProperty
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	
	
	
}
