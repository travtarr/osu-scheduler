package edu.oregonState.scheduler.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

public class ConfigFactory {
	private static Properties properties;
	private static Logger log;
	public static String secret = "googleClientSecret";
	public static String clientID = "googleClientID";
	public static String redirectURI = "gooogleRedirectURI";
	public static String catalog = "gatherCatalog";
	
	public Properties getProperties() throws ConfigException{
		if (properties != null)
			return properties;
		properties = new Properties();
		try{
			InputStream input = new FileInputStream("config.properties");			 
			// load a properties file
			properties.load(input);
		} catch (IOException e){
			log.error("Could not load config.properties");
			throw new ConfigException(e);
		}
		if(properties.containsKey(secret) && properties.containsKey(clientID) && properties.containsKey(redirectURI)
				&& properties.containsKey(catalog))
			return properties;
		else
			throw new ConfigException("Either clientID, redirectURI, secret or catalog was not found in config.properties");
	}
