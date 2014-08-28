package edu.oregonState.scheduler.provider.google.authentication;

import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

public class GoogleAuthorizationCodeRequestUrlWrapper {
	private final GoogleAuthorizationCodeRequestUrl googleAuthorizationCodeRequestUrl;;
	
	public GoogleAuthorizationCodeRequestUrlWrapper(String clientId,
			String redirectUrl, List<String> scope) {
		this.googleAuthorizationCodeRequestUrl = new GoogleAuthorizationCodeRequestUrl(clientId,redirectUrl,scope);
		googleAuthorizationCodeRequestUrl.setAccessType("offline");
		googleAuthorizationCodeRequestUrl.setApprovalPrompt("force");
	}
	
	public String getURL(){
		return googleAuthorizationCodeRequestUrl.build();
	}

}
