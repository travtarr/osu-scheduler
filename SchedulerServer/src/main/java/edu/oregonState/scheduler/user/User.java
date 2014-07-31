package edu.oregonState.scheduler.user;

import javax.persistence.*;

@Entity
@Table(name = "userData")
@NamedQueries({
    @NamedQuery(
        name = "edu.oregonState.scheduler.user.findAll",
        query = "SELECT p FROM userData p"
    ),
    @NamedQuery(
        name = "edu.oregonState.scheduler.user.findById",
        query = "SELECT p FROM userData p WHERE p.userID = :userID"
    )
})

public class User {
	
	@Id
	@Column(name="userID",nullable=false)
	private String userID;
	
	@Column(name="googleID",nullable=false)
	private String googleID;
	@Column(name="googleToken",nullable=false)
	private String googleToken;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getGoogleID() {
		return googleID;
	}
	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}
	public String getGoogleToken() {
		return googleToken;
	}
	public void setGoogleToken(String googleToken) {
		this.googleToken = googleToken;
	}
	
	
}
