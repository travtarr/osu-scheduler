package edu.oregonState.scheduler.user;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.config.ConfigFactory;

public class GoogleTokenProvider {
	private final String clientSecret;
	private final String clientID;
	private final String redirectUrl;
	
	public GoogleTokenProvider(ConfigFactory configFactory) throws ConfigException {
		super();
		Properties properties = configFactory.getProperties();
		this.clientSecret = properties.getProperty(ConfigFactory.secret);
		this.clientID = properties.getProperty(ConfigFactory.clientID);
		this.redirectUrl = properties.getProperty(ConfigFactory.redirectURI);
	}

		public String getRefreshToken(String googleToken) throws IOException{
			GoogleAuthorizationCodeFlow flow = getFlow();
			GoogleTokenResponse response = flow.newTokenRequest(googleToken).setRedirectUri(redirectUrl).execute();
			return response.getRefreshToken();
		}
		
		public String getAuthenticationToken(String googleToken) throws IOException{
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
			GoogleTokenResponse	response = new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
					clientID, clientSecret, googleToken, redirectUrl).execute();		
			return response.getAccessToken();
		}		
		

		//from https://developers.google.com/drive/web/credentials
		private GoogleAuthorizationCodeFlow getFlow(){
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(httpTransport, jsonFactory,
		    	      clientID, clientSecret, Arrays.asList("https://www.googleapis.com/auth/calendar"));
		    return flow;
		}
}
