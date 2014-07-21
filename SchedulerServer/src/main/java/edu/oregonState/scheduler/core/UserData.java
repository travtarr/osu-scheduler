package edu.oregonState.scheduler.core;

public class UserData {
	private final String userID;
	private final String googleID;
	private String professorName;
	private final String googleAuthentication;
	
	public UserData(String userID, String googleID,
			String googleAuthentication) {
		super();
		this.userID = userID;
		this.googleID = googleID;
		this.googleAuthentication = googleAuthentication;
	}
	
	public UserData(String userID, String googleID, String professorName,
			String googleAuthentication) {
		super();
		this.userID = userID;
		this.googleID = googleID;
		this.professorName = professorName;
		this.googleAuthentication = googleAuthentication;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getUserID() {
		return userID;
	}

	public String getGoogleID() {
		return googleID;
	}

	public String getGoogleAuthentication() {
		return googleAuthentication;
	}	
	
	
}
