package edu.oregonState.scheduler.user;

import edu.oregonState.scheduler.core.UserDTO;

public class UserAuthenticationRepository {
	public void addUser(UserDTO userData){}
	public Authentication getAuthentication(String userID) { return new Authentication(null);}
}
