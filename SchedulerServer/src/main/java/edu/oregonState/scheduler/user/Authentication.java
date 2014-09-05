package edu.oregonState.scheduler.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authentication {
	private String token;
	private String id;
	private String professorName;

	public Authentication() {
	}
	
	public Authentication(String token, String id, String professorName) {
		super();
		this.token = token;
		this.professorName = professorName;
	}

    @JsonProperty
	public String getToken() {
		return token;
	}

    @JsonProperty
	public void setToken(String token) {
		this.token = token;
	}

    @JsonProperty
	public String getId() {
		return id;
	}

    @JsonProperty
	public void setId(String id) {
		this.id = id;
	}
    
    @JsonProperty
    public String getProfessorName(){
    	return professorName;
    }
    
    @JsonProperty
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
        
}