package edu.oregonState.scheduler.provider;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OSUCatalogScheduleProviderTest {

	OSUCatalogScheduleProvider parser;
	
	@Before
	public void testOSUCatalogScheduleProvider() {
		parser = new OSUCatalogScheduleProvider();
	}

	@Test
	public void testGetScheduleStringAuthentication() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseSchedule() {
		assert(parser.parseSchedule());
	}

	@Test
	public void testGetScheduleString() {
		fail("Not yet implemented");
	}

}
