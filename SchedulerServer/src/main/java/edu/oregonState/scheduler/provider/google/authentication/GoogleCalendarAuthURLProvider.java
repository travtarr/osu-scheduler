package edu.oregonState.scheduler.provider.google.authentication;


import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

import edu.oregonState.scheduler.config.ConfigFactory;


public class GoogleCalendarAuthURLProvider {

	private final Properties properties;
	private final GoogleCalendarAuthorizationCodeRequestUrlWrapperFactory googleAuthorizationCodeRequestUrlFactory;
	
	public GoogleCalendarAuthURLProvider(Properties properties){
		this.properties = properties;
		googleAuthorizationCodeRequestUrlFactory = new GoogleCalendarAuthorizationCodeRequestUrlWrapperFactory();
	}	
	
	public GoogleCalendarAuthURLProvider(String clientID, String redirectURL, GoogleCalendarAuthorizationCodeRequestUrlWrapperFactory googleAuthorizationCodeRequestUrlFactory){
		this.properties = new Properties();
		properties.setProperty(ConfigFactory.clientID,clientID);
		properties.setProperty(ConfigFactory.redirectURI,redirectURL);
		this.googleAuthorizationCodeRequestUrlFactory = googleAuthorizationCodeRequestUrlFactory;
	}	
	public String getAuthURL(){
    // The clientId and clientSecret can be found in Google Developers Console
    String clientId = properties.getProperty(ConfigFactory.clientID);

    // Or your redirect URL for web based applications.
    String redirectUrl = properties.getProperty(ConfigFactory.redirectURI);      
    GoogleAuthorizationCodeRequestUrlWrapper authorizationUrl = 
    		googleAuthorizationCodeRequestUrlFactory.getGoogleAuthorizationCodeRequestURL(clientId, redirectUrl);
    return authorizationUrl.getURL();
	}
}
