package edu.oregonState.scheduler.provider;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.oregonState.scheduler.MainFactory;
import edu.oregonState.scheduler.core.CalendarEvent;
import edu.oregonState.scheduler.core.Catalog;
import edu.oregonState.scheduler.core.Schedule;
import edu.oregonState.scheduler.data.CatalogDAO;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.user.Authentication;

public class CatalogScheduleProvider implements ScheduleProvider {

	private CatalogScheduleSQLProvider sql;
	private static final int INSTRUCTOR = 5;
	private static final int TERM = 0;
	private static final int DAYSTIMESDATES = 6;
	private static final int NUM_FIELDS = 9;
	private static final int COURSE_TABLE = 5;
	private static final int TIMEOUT = 120 * 1000; // 2mins in milliseconds

	public CatalogScheduleProvider() {

	}

	@Override
	public Schedule getSchedule(String userId, Authentication authentication) {
		UserDAO user = MainFactory.getUserDAO();
		String professorName = user.findByID(userId).getProfessorName();
		if (professorName != null)
			return getSchedule(professorName, userId);
		else
			return null;
	}

	/**
	 * Retrieves the schedule of a given instructor.
	 * 
	 * @param instructorName
	 * @param userID
	 */
	private Schedule getSchedule(String instructorName, String userID) {
		CatalogDAO catalog = MainFactory.getCatalogDAO();
		List<Catalog> catalogList = catalog.findByName(instructorName);
		CalendarEvent event = null;
		List<CalendarEvent> calendarEvents = new ArrayList<>();

		/* Go through each event */
		for (Catalog cat : catalogList) {
			LocalDate dateStart = new LocalDate(cat.getDateStart());
			LocalDate dateEnd = new LocalDate(cat.getDateEnd());
			String[] days = cat.getClassDays().split(".");
			String[] catTimeStart = cat.getClassTimeStart().split("[0-9]{2}");
			String[] catTimeEnd = cat.getClassTimeEnd().split("[0-9]{2}");
			
			int catTimeStartHour = 0, catTimeStartMin = 0, catTimeEndHour = 0, catTimeEndMin = 0;
			int timezone = 0;
			
			while (catTimeStart.length == 2 && catTimeEnd.length == 2) {
				catTimeStartHour = Integer.parseInt(catTimeStart[0]);
				catTimeStartMin = Integer.parseInt(catTimeStart[1]);
				catTimeEndHour = Integer.parseInt(catTimeEnd[0]);
				catTimeEndMin = Integer.parseInt(catTimeEnd[1]);
			}
			
			LocalTime timezoneTime = new LocalTime(catTimeStartHour, catTimeStartMin);
			
			// Go through each day between the start date and end date of the class
			while (dateStart.isBefore(dateEnd)) {
				int dayOfWeek = dateStart.getDayOfWeek();
				
				// make sure this day is one of the class days of the week
				if (isDayOfWeekMatch(days, dayOfWeek)) {
					DateTime timezoneDate = dateStart.toDateTime(timezoneTime);
					timezone = timezoneDate.getZone().getOffset(timezoneDate.getMillis()) / (1000 * 60 * 60);
					event = new CalendarEvent(
							dateStart.getYear(),
							dateStart.getMonthOfYear(),
							dateStart.getDayOfMonth(), 
							catTimeStartHour,
							catTimeStartMin,
							dateStart.getYear(),
							dateStart.getMonthOfYear(),
							dateStart.getDayOfMonth(), 
							catTimeEndHour,
							catTimeEndMin,
							timezone,
							new String[]{userID}
					);
				}
				calendarEvents.add(event);
				
				// go to next day
				dateStart = dateStart.plusDays(1);
			}
		}
		
		CalendarEvent[] calendarEventsArr = new CalendarEvent[calendarEvents.size()];
		calendarEvents.toArray(calendarEventsArr);
		return new Schedule(calendarEventsArr);
	}

	
	/**
	 * Simple test to see if the given day is one of the days the class is scheduled
	 * 
	 * @param days
	 * @param day
	 * @return
	 */
	private boolean isDayOfWeekMatch(String[] days, int day) {
		for (int i = 0; i < days.length; i++) {
			if (day == getDayOfWeek(days[i]))
				return true;
		}
		return false;
	}

	/**
	 * Converts a one digit day of the week to an integer.
	 * 
	 * @param day
	 * @return
	 */
	private int getDayOfWeek(String day) {
		int numDay = 1;
		switch (day) {
		case "M":
			numDay = DateTimeConstants.MONDAY;
			break;
		case "T":
			numDay = DateTimeConstants.TUESDAY;
			break;
		case "W":
			numDay = DateTimeConstants.WEDNESDAY;
			break;
		case "R":
			numDay = DateTimeConstants.THURSDAY;
			break;
		case "F":
			numDay = DateTimeConstants.FRIDAY;
			break;
		}
		return numDay;
	}
	

	/**
	 * Calls upon the HTMLParser and stores the information via SQLUtility.
	 */
	public void parseSchedule() {
		String[] subjects = null;
		String[] courses = null;

		// create sql resource
		initDB();

		// check if table exists
		sql.createTable();

		subjects = getSubjectList().getItems();
		for (int i = 0; i < subjects.length; i++) {
			courses = getCourseNumbers(subjects[i]).getItems();

			for (int j = 0; j < courses.length; j++) {
				System.out.println("subject: " + subjects[i] + ", course: "
						+ courses[j]);
				storeCourseDetails(subjects[i], courses[j]);
			}
		}
	}

	/**
	 * Initializes the DB object with connection credentials
	 */
	private void initDB() {

		final String address = "localhost:3306";
		final String dbName = "schedule";
		final String username = "travis";
		final String password = "5TB232ayq34q";

		sql = new CatalogScheduleSQLProvider(username, password, address,
				dbName);
	}

	private void storeCourseDetails(String subject, String courseNum) {
		final String courseURI = "http://catalog.oregonstate.edu/CourseDetail.aspx?subjectcode="
				+ subject + "&coursenumber=" + courseNum;
		final String form = "aspnetForm";
		final String courseTable = "ctl00_ContentPlaceHolder1_SOCListUC1_gvOfferings";
		String linkStr = null, courseName = "";
		String[] linkStrSplit = null, whenSplit = null, dateSplit, timeSplit, valueList;
		java.awt.List toStoreValues = new java.awt.List();
		Element[] values = null;
		String[][] listArray = null;
		int listSize = 0;

		// create courseID (eg. MTH-251)
		String courseId = subject + " " + courseNum;
		System.out.println("courseID: " + courseId);

		try {
			Document doc = Jsoup.connect(courseURI).timeout(TIMEOUT).get();
			Element content = doc.getElementById(form);
			Elements links = content.getElementsByTag("h3");

			// get courseName
			// go through each H3 element and find course name
			for (Element link : links) {
				linkStr = link.text();
				// split string up to remove non a-Z characters
				linkStrSplit = linkStr.split("[^a-zA-Z]");

				// compare the line to confirm it matches the subject
				if (linkStrSplit[0].compareTo(subject) == 0) {
					for (int i = 6; i < linkStrSplit.length; i++) {
						if (i == 6)
							courseName = courseName.concat(linkStrSplit[i]);
						else
							courseName = courseName.concat(" "
									+ linkStrSplit[i]);
					}
				}
				if (courseName != "")
					break;
			}

			// goto the course information table
			content = doc.getElementById(courseTable);
			if (content != null) {
				content = content.child(0).child(1);
			}

			while ((content != null)) {
				// get column data of row
				links = content.children();
				values = links.toArray(new Element[links.size()]);
				// store course's ID
				toStoreValues.add(courseId);
				// store course's name now
				toStoreValues.add(courseName);
				// get instructor
				toStoreValues.add(values[INSTRUCTOR].text());
				// get term
				toStoreValues.add(values[TERM].text());
				// split next string up, holds many values
				whenSplit = values[DAYSTIMESDATES].text().split("\\s+");

				if (whenSplit.length < 3) {
					for (int i = 0; i < 4; i++)
						toStoreValues.remove(toStoreValues.getItemCount() - 1);
				} else {
					// get dateStart & dateEnd
					dateSplit = whenSplit[2].split("-");
					toStoreValues.add(convertDate(dateSplit[0]));
					toStoreValues.add(convertDate(dateSplit[1]));
					// get class days
					toStoreValues.add(whenSplit[0]);
					// get timeStart & timeEnd
					timeSplit = whenSplit[1].split("-");
					toStoreValues.add(timeSplit[0]);
					toStoreValues.add(timeSplit[1]);
				}

				// goto next row in table
				content = content.nextElementSibling();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// convert list to 2D array
		valueList = toStoreValues.getItems();
		listSize = valueList.length / NUM_FIELDS;
		listArray = new String[listSize][NUM_FIELDS];

		for (int i = 0, k = 0; i < listSize; i++) {
			for (int j = 0; k < (NUM_FIELDS * listSize) && j < NUM_FIELDS; j++, k++) {
				listArray[i][j] = valueList[k];
				// System.out.println("listArray["+ i + "][" + j +"] " +
				// "valuesList["+ k +"] " + valueList[k]);
			}
		}

		// store all information collected
		sql.addClassSchedule(listArray);

	}

	/**
	 * Gets the course number from a given hyperlink.
	 * 
	 * @param link
	 *            hyperlink with course number
	 * @return course number
	 */
	private java.awt.List getCourseNumbers(String subject) {
		final String courseNumURI = "http://catalog.oregonstate.edu/CourseList.aspx?subjectcode="
				+ subject + "&level=undergrad&campus=corvallis";
		java.awt.List courseNums = new java.awt.List();
		String courseNum = null;

		try {
			Document doc = Jsoup.connect(courseNumURI).get();
			Element content = doc.getElementsByTag("table").get(COURSE_TABLE);
			Elements links = content.getElementsByTag("a");

			for (Element link : links) {
				if (link.hasAttr("href")) {
					courseNum = getCourseNum(link.attr("href"));
					if (courseNum != null) {
						courseNums.add(courseNum);
						// System.out.println("courseNum: " + courseNum);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return courseNums;
	}

	/**
	 * Retrieves the list of subjects from the catalog.
	 * 
	 * @return List of subjects (ex. MTH, BIO, ZOO)
	 */
	private java.awt.List getSubjectList() {
		final String courseListURI = "http://catalog.oregonstate.edu/CourseDescription.aspx?level=all&campus=corvallis";
		final String subjTable = "ctl00_ContentPlaceHolder1_dlSubjects";
		java.awt.List list = new java.awt.List();

		try {
			Document doc = Jsoup.connect(courseListURI).get();
			Element content = doc.getElementById(subjTable);
			Elements links = content.getElementsByTag("a");

			for (Element link : links) {
				list.add(getSubjectCode(link.attr("href")));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Gets a course number from a string of text.
	 * 
	 * @param link
	 * @return
	 */
	private String getCourseNum(String link) {
		String[] split = link.split("(=|&)");
		if (split.length < 4)
			return null;

		return split[3];
	}

	/**
	 * This splits the hyperlink text and grabs the subject code from it.
	 * 
	 * @param link
	 *            hyperlink text
	 * @return subject code
	 */
	private String getSubjectCode(String link) {
		String[] split = link.split("(=|&)");
		return split[1];
	}

	/**
	 * Converts 9/24/13 date format into 2013-09-13 format.
	 * 
	 * @param rawDate
	 * @return
	 */
	private String convertDate(String rawDate) {
		String[] splitStr = rawDate.split("/");
		String newStr = "20" + splitStr[2];

		if (splitStr[0].length() < 2)
			newStr = newStr.concat("-0" + splitStr[0]);
		else
			newStr = newStr.concat("-" + splitStr[0]);

		if (splitStr[1].length() < 2)
			newStr = newStr.concat("-0" + splitStr[1]);
		else
			newStr = newStr.concat("-" + splitStr[1]);

		// System.out.println("date: " + newStr);
		return newStr;
	}
}
