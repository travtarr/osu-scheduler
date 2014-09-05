package edu.oregonState.scheduler.data;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserJDBIDAO {

	@SqlQuery("SELECT googleID FROM userData WHERE userID = :userID")
	String findGoogleID(@Bind("userID") String userID);
	
	@SqlQuery("SELECT googleToken FROM userData WHERE userID = :userID")
	String findGoogleToken(@Bind("userID") String userID);	
	
	@SqlQuery("SELECT professorName FROM userData WHERE userID = :userID")
	String findProfessorName(@Bind("userID") String userID);	
}
