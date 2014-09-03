package edu.oregonState.scheduler.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	private String userID;
	private String googleID;
	private String googleAuthentication;
	
	public  UserDTO(){
		//dummy for jersey
	}
	public UserDTO(String userID, String googleID,
			String googleAuthentication) {
		super();
		this.userID = userID;
		this.googleID = googleID;
		this.googleAuthentication = googleAuthentication;
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

    @JsonProperty
	public void setUserID(String userID) {
		this.userID = userID;
	}

    @JsonProperty
	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}

    @JsonProperty
	public void setGoogleAuthentication(String googleAuthentication) {
		this.googleAuthentication = googleAuthentication;
	}	
    
    
	
    
	
}
