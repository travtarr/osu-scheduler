package edu.oregonState.scheduler;

import java.util.Properties;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.config.ConfigFactory;
import edu.oregonState.scheduler.data.CatalogDAO;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.model.ScheduleModel;
import edu.oregonState.scheduler.model.calculation.CalculationStrategyFactory;
import edu.oregonState.scheduler.provider.CatalogScheduleProvider;
import edu.oregonState.scheduler.provider.StubScheduleProvider;
import edu.oregonState.scheduler.provider.google.authentication.GoogleCalendarAuthURLProvider;
import edu.oregonState.scheduler.user.GoogleTokenProvider;
import edu.oregonState.scheduler.user.User;
import edu.oregonState.scheduler.user.UserAuthenticationRepository;

final public class MainFactory {
	public static String catalog = "gatherCatalog";
	
	public static ScheduleModel getScheduleModel(UserAuthenticationRepository repo) throws ConfigException{
		return new ScheduleModel(repo, new StubScheduleProvider(), new CalculationStrategyFactory());
	}
	
	public static GoogleCalendarAuthURLProvider getGoogleCalendarAuthURLProvider() throws ConfigException{
		return new GoogleCalendarAuthURLProvider(new ConfigFactory().getProperties());
	}
		
	public static HibernateBundle<SchedulerConfiguration> getHibernate(){
		if (hibernate == null)
		hibernate = new HibernateBundle<SchedulerConfiguration>(User.class) {
	        @Override
	        public DataSourceFactory getDataSourceFactory(SchedulerConfiguration configuration) {
	            return configuration.getDataSourceFactory();
	        }
	    };
		return hibernate;
	}
	
    private static HibernateBundle<SchedulerConfiguration> hibernate;
    
    private static UserDAO userDAO;
    public static UserDAO getUserDAO(){
    	if(userDAO == null)
    		userDAO = new UserDAO(getHibernate().getSessionFactory());
    	return userDAO;
    }
    
    private static CatalogDAO catalogDAO;
    public static CatalogDAO getCatalogDAO(){
    	if(catalogDAO == null)
    		catalogDAO = new CatalogDAO(getHibernate().getSessionFactory());
    	return catalogDAO;
    }

	public static GoogleTokenProvider getGoogleTokenProvider() throws ConfigException {
		return new GoogleTokenProvider(new ConfigFactory(),getGoogleCalendarAuthURLProvider());
	}

	public static void storeCatalogSchedule() throws ConfigException {
		Properties props = (new ConfigFactory().getProperties());
		if(props.getProperty(catalog) == "true"){
			CatalogScheduleProvider	csp = new CatalogScheduleProvider();
			csp.parseSchedule();
		}		
	}
}
