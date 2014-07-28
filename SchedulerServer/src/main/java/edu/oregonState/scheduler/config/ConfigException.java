package edu.oregonState.scheduler.config;

import java.io.IOException;

public class ConfigException extends Exception {

	public ConfigException(String string) {
		super(string);
	}

	public ConfigException(Exception e) {
		super(e);
	}

}
