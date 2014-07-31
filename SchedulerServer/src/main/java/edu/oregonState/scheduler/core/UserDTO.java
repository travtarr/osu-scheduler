package edu.oregonState.scheduler.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	private final String userID;
	private final String googleID;
	private String professorName;
	private final String googleAuthentication;
	
	public UserDTO(String userID, String googleID,
			String googleAuthentication) {
		super();
		this.userID = userID;
		this.googleID = googleID;
		this.googleAuthentication = googleAuthentication;
	}
	
	public UserDTO(String userID, String googleID, String professorName,
			String googleAuthentication) {
		super();
		this.userID = userID;
		this.googleID = googleID;
		this.professorName = professorName;
		this.googleAuthentication = googleAuthentication;
	}

    @JsonProperty
	public String getProfessorName() {
		return professorName;
	}

    @JsonProperty
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

    @JsonProperty
	public String getUserID() {
		return userID;
	}

    @JsonProperty
	public String getGoogleID() {
		return googleID;
	}

    @JsonProperty
	public String getGoogleAuthentication() {
		return googleAuthentication;
	}	
	
	
}
