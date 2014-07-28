package edu.oregonState.scheduler.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import edu.oregonState.scheduler.core.AuthURL;
import edu.oregonState.scheduler.core.Saying;
import edu.oregonState.scheduler.provider.google.authentication.GoogleCalendarAuthURLProvider;

@Path("/googleAuthURL")
@Produces(MediaType.APPLICATION_JSON)
public class GoogleAuthResource {
    private final GoogleCalendarAuthURLProvider authenticationURLProvider;
    
    public GoogleAuthResource(GoogleCalendarAuthURLProvider authenticationURLProvider){
    	this.authenticationURLProvider = authenticationURLProvider;
    }
    
    @GET
    @Timed
    public AuthURL getAuthURL() {
        return new AuthURL(authenticationURLProvider.getAuthURL());
    }
}
