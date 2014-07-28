package edu.oregonState.scheduler.provider.google.authentication;

import java.util.Arrays;
import java.util.Collection;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

public class GoogleCalendarAuthorizationCodeRequestUrlWrapperFactory {
	private static final String scope = "https://www.googleapis.com/auth/calendar";
	
	public GoogleAuthorizationCodeRequestUrlWrapper getGoogleAuthorizationCodeRequestURL(
			String clientId, String redirectUrl) {
		return new GoogleAuthorizationCodeRequestUrlWrapper(clientId,redirectUrl,Arrays.asList(scope));
	}

}
