package edu.oregonState.scheduler.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Authentication {
	private String token;
	private String id;

	public Authentication() {
	}
	
	public Authentication(String token, String id) {
		super();
		this.token = token;
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
}