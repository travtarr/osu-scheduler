package edu.oregonState.scheduler.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthURL {
	private String authURL;
	
    public AuthURL() {
        // Jackson deserialization
    }
    
    public AuthURL(String authURL){
    	setAuthURL(authURL);
    }
   
    
    @JsonProperty
    public String getAuthURL() {
        return authURL;
    }    

    @JsonProperty
    public void setAuthURL(String authURL){
    	this.authURL=authURL;
    }
}
