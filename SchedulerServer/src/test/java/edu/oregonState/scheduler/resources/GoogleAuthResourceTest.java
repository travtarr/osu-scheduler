package edu.oregonState.scheduler.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.oregonState.scheduler.provider.google.authentication.GoogleCalendarAuthURLProvider;

public class GoogleAuthResourceTest {
	private GoogleCalendarAuthURLProvider mockProvider;
	private GoogleAuthResource target;	
	
	@Before
	public void setUp(){
		mockProvider = mock(GoogleCalendarAuthURLProvider.class);
		target = new GoogleAuthResource(mockProvider);
	}

	@Test
	public void getAuthURL_getsAuthURL_returnsSameURL() {
		//arrange
		String expected = "www.test.class";
		when(mockProvider.getAuthURL()).thenReturn(expected);
		
		//act
		String actual = target.getAuthURL().getAuthURL();
		
		//assert
		assertEquals(expected,actual);
	}

}
