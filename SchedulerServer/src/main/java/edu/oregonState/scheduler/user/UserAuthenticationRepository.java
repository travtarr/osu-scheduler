package edu.oregonState.scheduler.user;

import java.io.IOException;

import edu.oregonState.scheduler.core.UserDTO;
import edu.oregonState.scheduler.data.UserDAO;

public interface UserAuthenticationRepository {

	public Authentication getAuthentication(String userID) throws IOException;
}
