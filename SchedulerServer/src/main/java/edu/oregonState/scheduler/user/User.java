package edu.oregonState.scheduler.user;

import javax.persistence.*;

import edu.oregonState.scheduler.core.UserDTO;

@Entity
@Table(name = "userData")
@NamedQueries({
    @NamedQuery(
        name = "edu.oregonState.scheduler.user.findAll",
        query = "SELECT p FROM User p"
    ),
    @NamedQuery(
        name = "edu.oregonState.scheduler.user.findById",
        query = "SELECT p FROM User p WHERE p.userID = :userID"
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
	@Column(name="professorName",nullable=true)
	private String professorName;
	@Column(name="deptName",nullable=true)
	private String deptName;
	
	public User(){
		
	}
	
	public User(UserDTO userData) {
		setUserID(userData.getUserID());
		setGoogleID(userData.getGoogleID());
		setGoogleToken(userData.getGoogleAuthentication());
	}
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
	public void setProfessorName(String professorName){
		this.professorName = professorName;
	}
	public String getProfessorName(){
		return professorName;
	}
	public void setDeptName(String deptName){
		this.deptName = deptName;
	}
}
