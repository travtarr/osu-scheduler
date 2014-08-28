package edu.oregonState.scheduler.user;

import java.io.IOException;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
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
			return getResponse(googleToken).getRefreshToken();
		}
		
		public String getAuthenticationToken(String googleToken) throws IOException{
			return getResponse(googleToken).getAccessToken();
		}		
		
		private GoogleTokenResponse getResponse(String token) throws IOException{
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
			return new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
					clientID, clientSecret, token, redirectUrl).execute();			
		}
}
