package edu.oregonState.scheduler.config;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigFactoryTest {

	@Before
	public void setUpBeforeClass() throws Exception {
	}

	@Test
	public void getConfig_currentSetUp_loads() throws ConfigException {
		//arrange
		ConfigFactory target = new ConfigFactory();
		//act
		Properties properties = target.getProperties();
		
		//assert
		assertNotNull(properties);
	}

}
